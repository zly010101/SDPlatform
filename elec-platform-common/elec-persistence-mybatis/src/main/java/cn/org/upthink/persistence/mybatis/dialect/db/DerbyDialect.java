/*    */ package cn.org.upthink.persistence.mybatis.dialect.db;
/*    */ 
/*    */ import cn.org.upthink.persistence.mybatis.dialect.Dialect;

/*    */
/*    */ public class DerbyDialect
/*    */   implements Dialect
/*    */ {
           @Override
/*    */   public boolean supportsLimit()
/*    */   {
/* 11 */     return false;
/*    */   }
/*    */   @Override
/*    */   public String getLimitString(String sql, int offset, int limit)
/*    */   {
/* 16 */     throw new UnsupportedOperationException("paged queries not supported");
/*    */   }
/*    */ 
/*    */   public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder)
/*    */   {
/* 35 */     throw new UnsupportedOperationException("paged queries not supported");
/*    */   }
/*    */ }

/* Location:           /Users/rover/Downloads/mus-persistence.mybatis-1.0.1.jar
 * Qualified Name:     DerbyDialect
 * JD-Core Version:    0.6.2
 */