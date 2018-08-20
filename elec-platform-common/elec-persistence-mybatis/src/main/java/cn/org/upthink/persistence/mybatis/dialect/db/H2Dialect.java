/*    */ package cn.org.upthink.persistence.mybatis.dialect.db;
/*    */ 
/*    */ import cn.org.upthink.persistence.mybatis.dialect.Dialect;

/*    */
/*    */ public class H2Dialect
/*    */   implements Dialect
/*    */ { @Override
/*    */   public boolean supportsLimit()
/*    */   {
/* 14 */     return true;
/*    */   }
/*    */ 
/*    */   private String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder)
/*    */   {
/* 33 */     return new StringBuilder().append(sql).append(offset > 0 ? new StringBuilder().append(" limit ").append(limitPlaceholder).append(" offset ").append(offsetPlaceholder).toString() : new StringBuilder().append(" limit ").append(limitPlaceholder).toString()).toString();
/*    */   }
/*    */   @Override
/*    */   public String getLimitString(String sql, int offset, int limit)
/*    */   {
/* 38 */     return getLimitString(sql, offset, Integer.toString(offset), limit, Integer.toString(limit));
/*    */   }
/*    */ }

/* Location:           /Users/rover/Downloads/mus-persistence.mybatis-1.0.1.jar
 * Qualified Name:     H2Dialect
 * JD-Core Version:    0.6.2
 */