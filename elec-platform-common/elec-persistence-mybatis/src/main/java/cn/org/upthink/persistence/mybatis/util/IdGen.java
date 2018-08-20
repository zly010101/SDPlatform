/*    */ package cn.org.upthink.persistence.mybatis.util;
/*    */ 
/*    */ import java.security.SecureRandom;
/*    */ import java.util.UUID;
/*    */ 
/*    */ public class IdGen
/*    */ {
/* 13 */   private static SecureRandom random = new SecureRandom();
/*    */ 
/*    */   public static String uuid()
/*    */   {
/* 19 */     return UUID.randomUUID().toString().replaceAll("-", "");
/*    */   }
/*    */ 
/*    */   public static long randomLong()
/*    */   {
/* 26 */     return Math.abs(random.nextLong());
/*    */   }
/*    */ }

/* Location:           /Users/rover/Downloads/mus-persistence.mybatis-1.0.1.jar
 * Qualified Name:     IdGen
 * JD-Core Version:    0.6.2
 */