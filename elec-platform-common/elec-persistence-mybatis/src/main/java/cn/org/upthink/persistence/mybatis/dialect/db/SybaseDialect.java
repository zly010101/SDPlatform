/*    */ package cn.org.upthink.persistence.mybatis.dialect.db;
/*    */ 
/*    */ import cn.org.upthink.persistence.mybatis.dialect.Dialect;

/*    */
/*    */ public class SybaseDialect
/*    */   implements Dialect
/*    */ { @Override
/*    */   public boolean supportsLimit()
/*    */   {
/* 15 */     return false;
/*    */   }
/*    */  @Override
/*    */   public String getLimitString(String sql, int offset, int limit) {
/* 19 */     return null;
/*    */   }
/*    */ 
/*    */   public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder)
/*    */   {
/* 38 */     throw new UnsupportedOperationException("paged queries not supported");
/*    */   }
/*    */ }

/* Location:           /Users/rover/Downloads/mus-persistence.mybatis-1.0.1.jar
 * Qualified Name:     SybaseDialect
 * JD-Core Version:    0.6.2
 */