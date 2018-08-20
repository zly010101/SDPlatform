package cn.org.upthink.common.util.uuid;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.*;

/**
 * Created by rover on 2018/3/14.
 * 可以生成如下格式的UUID: 285BAAC4-181C-42E3-8C40-2C6AAC7D2676
 */
public final class UUIDGenerator {

    private final static UUIDGenerator sSingleton = new UUIDGenerator();

    private Random mRnd = null;

    private final Object mDummyAddressLock = new Object();
    private EthernetAddress mDummyAddress = null;
    private final Object mTimerLock = new Object();
    private UUIDTimer mTimer = null;

    private MessageDigest mHasher = null;

    private UUIDGenerator() { }

    public static UUIDGenerator getInstance() {
        return sSingleton;
    }

    public EthernetAddress getDummyAddress() {
        synchronized (mDummyAddressLock) {
            if (mDummyAddress == null) {
                Random rnd = getRandomNumberGenerator();
                byte[] dummy = new byte[6];
                rnd.nextBytes(dummy);
                dummy[0] |= (byte) 0x01;
                try {
                    mDummyAddress = new EthernetAddress(dummy);
                } catch (NumberFormatException nex) {
                    //
                }
            }
        }

        return mDummyAddress;
    }

    public Random getRandomNumberGenerator() {
        if (mRnd == null) {
            mRnd = new SecureRandom();
        }
        return mRnd;
    }

    public void setRandomNumberGenerator(Random r) {
        mRnd = r;
    }

    public MessageDigest getHashAlgorithm() {
        if (mHasher == null) {
            try {
                mHasher = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException nex) {
                throw new Error("Couldn't instantiate an MD5 MessageDigest instance: "+nex.toString());
            }
        }
        return mHasher;
    }

    public UUID generateRandomBasedUUID() {
        return generateRandomBasedUUID(getRandomNumberGenerator());
    }

    public UUID generateRandomBasedUUID(Random randomGenerator) {
        byte[] rnd = new byte[16];

        randomGenerator.nextBytes(rnd);

        return new UUID(UUID.TYPE_RANDOM_BASED, rnd);
    }

    public UUID generateTimeBasedUUID() {
        return generateTimeBasedUUID(getDummyAddress());
    }

    public UUID generateTimeBasedUUID(EthernetAddress addr) {
        byte[] contents = new byte[16];

        addr.toByteArray(contents, 10);

        synchronized (mTimerLock) {
            if (mTimer == null) {
                mTimer = new UUIDTimer(getRandomNumberGenerator());
            }

            mTimer.getTimestamp(contents);
        }

        return new UUID(UUID.TYPE_TIME_BASED, contents);
    }

    public UUID generateNameBasedUUID(UUID nameSpaceUUID, String name, MessageDigest digest){
        digest.reset();
        if (nameSpaceUUID != null) {
            digest.update(nameSpaceUUID.asByteArray());
        }
        digest.update(name.getBytes());
        return new UUID(UUID.TYPE_NAME_BASED, digest.digest());
    }

    public UUID generateNameBasedUUID(UUID nameSpaceUUID, String name) {
        MessageDigest hasher = getHashAlgorithm();
        synchronized (hasher) {
            return generateNameBasedUUID(nameSpaceUUID, name, getHashAlgorithm());
        }
    }

    /**
     * 测试
     * @param args
     */
    public static void main(String[] args){
        String uuidStr = UUIDGenerator.getInstance().generateTimeBasedUUID().toPlainString();
        System.out.println("uuidStr="+uuidStr);
    }

}
