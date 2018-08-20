package cn.org.upthink.common.util.uuid;

import java.io.Serializable;

/**
 * EthernetAddress encapsulates the 6-byte Mac address defined in IEEE 802.1 standard.
 */
public class EthernetAddress implements Serializable, Cloneable, Comparable {

    private final static String kHexChars = "0123456789abcdefABCDEF";

    private final byte[] mAddress = new byte[6];

    public EthernetAddress(String addrStr) throws NumberFormatException {
        byte[] addr = mAddress;
        int len = addrStr.length();

        for (int i = 0, j = 0; j < 6; ++j) {
            if (i >= len) {
                // Is valid if this would have been the last byte:
                if (j == 5) {
                    addr[5] = (byte) 0;
                    break;
                }
                throw new NumberFormatException("Incomplete ethernet address (missing one or more digits");
            }

            char c = addrStr.charAt(i);
            ++i;
            int value;

            // The whole number may be omitted (if it was zero):
            if (c == ':') {
                value = 0;
            } else {
                // No, got at least one digit?
                if (c >= '0' && c <= '9') {
                    value = (c - '0');
                } else if (c >= 'a' && c <= 'f') {
                    value = (c - 'a' + 10);
                } else if (c >= 'A' && c <= 'F') {
                    value = (c - 'A' + 10);
                } else {
                    throw new NumberFormatException("Non-hex character '"+c+"'");
                }

                // How about another one?
                if (i < len) {
                    c = addrStr.charAt(i);
                    ++i;
                    if (c != ':') {
                        value = (value << 4);
                        if (c >= '0' && c <= '9') {
                            value |= (c - '0');
                        } else if (c >= 'a' && c <= 'f') {
                            value |= (c - 'a' + 10);
                        } else if (c >= 'A' && c <= 'F') {
                            value |= (c - 'A' + 10);
                        } else {
                            throw new NumberFormatException("Non-hex character '"+c+"'");
                        }
                    }
                }
            }

            addr[j] = (byte) value;

            if (c != ':') {
                if (i < len) {
                    if (addrStr.charAt(i) != ':') {
                        throw new NumberFormatException("Expected ':', got ('"
                                + addrStr.charAt(i)
                                +"')");
                    }
                    ++i;
                } else if (j < 5) {
                    throw new NumberFormatException("Incomplete ethernet address (missing one or more digits");
                }
            }
        }
    }

    public EthernetAddress(byte [] addr) throws NumberFormatException {
        if (addr.length != 6) {
            throw new NumberFormatException("Ethernet address has to consist of 6 bytes");
        }
        for (int i = 0; i < 6; ++i) {
            mAddress[i] = addr[i];
        }
    }

    public EthernetAddress(long addr) {
        for (int i = 0; i < 6; ++i) {
            mAddress[5 - i] = (byte) addr;
            addr >>>= 8;
        }
    }

    EthernetAddress() {
        byte z = (byte) 0;
        for (int i = 0; i < 6; ++i) {
            mAddress[i] = z;
        }
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // shouldn't happen
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EthernetAddress)) {
            return false;
        }
        byte[] otherAddress = ((EthernetAddress) o).mAddress;
        byte[] thisAddress = mAddress;
        for (int i = 0; i < 6; ++i) {
            if (otherAddress[i] != thisAddress[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {
        byte[] thatA = ((EthernetAddress) o).mAddress;
        byte[] thisA = mAddress;

        for (int i = 0; i < 6; ++i) {
            int cmp = (((int) thisA[i]) & 0xFF)
                    - (((int) thatA[i]) & 0xFF);
            if (cmp != 0) {
                return cmp;
            }
        }

        return 0;
    }

    @Override
    public String toString() {
        StringBuffer b = new StringBuffer(17);
        byte[] addr = mAddress;

        for (int i = 0; i < 6; ++i) {
            if (i > 0) {
                b.append(":");
            }
            int hex = addr[i] & 0xFF;
            b.append(kHexChars.charAt(hex >> 4));
            b.append(kHexChars.charAt(hex & 0x0f));
        }
        return b.toString();
    }

    public byte[] asByteArray() {
        byte[] result = new byte[6];

        toByteArray(result);

        return result;
    }

    public byte[] toByteArray() { return asByteArray(); }

    public void toByteArray(byte[] array) { toByteArray(array, 0); }

    public void toByteArray(byte[] array, int pos) {
        for (int i = 0; i < 6; ++i) {
            array[pos+i] = mAddress[i];
        }
    }

    public long toLong() {
        byte[] addr = mAddress;
        int hi = (((int) addr[0]) & 0xFF) << 8
                | (((int) addr[1]) & 0xFF);
        int lo = ((int) addr[2]) & 0xFF;
        for (int i = 3; i < 6; ++i) {
            lo = (lo << 8) | (((int) addr[i]) & 0xFF);
        }

        return ((long) hi) << 32 | (((long) lo) & 0xFFFFFFFFL);
    }

    public static EthernetAddress valueOf(byte[] addr) throws NumberFormatException {
        return new EthernetAddress(addr);
    }

    public static EthernetAddress valueOf(int[] addr) throws NumberFormatException {
        byte[] bAddr = new byte[addr.length];

        for (int i = 0; i < addr.length; ++i) {
            bAddr[i] = (byte) addr[i];
        }
        return new EthernetAddress(bAddr);
    }

    public static EthernetAddress valueOf(String addrStr) throws NumberFormatException {
        return new EthernetAddress(addrStr);
    }

    public static EthernetAddress valueOf(long addr) {
        return new EthernetAddress(addr);
    }

}

