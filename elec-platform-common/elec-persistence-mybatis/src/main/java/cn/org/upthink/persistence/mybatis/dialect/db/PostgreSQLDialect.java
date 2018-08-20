/*    */ package cn.org.upthink.persistence.mybatis.dialect.db;
/*    */ 
/*    */ import cn.org.upthink.persistence.mybatis.dialect.Dialect;

/*    */
/*    */ public class PostgreSQLDialect
/*    */   implements Dialect
/*    */ { @Override
/*    */   public boolean supportsLimit()
/*    */   {
/* 13 */     return true;
/*    */   }
/*    */   @Override
/*    */   public String getLimitString(String sql, int offset, int limit) {
/* 17 */     return getLimitString(sql, offset, Integer.toString(offset), 
/* 18 */       Integer.toString(limit));
/*    */   }
/*    */ 
/*    */   public String getLimitString(String sql, int offset, String offsetPlaceholder, String limitPlaceholder)
/*    */   {
/* 37 */     StringBuilder pageSql = new StringBuilder().append(sql);
/*    */ 
/* 40 */     pageSql = offset <= 0 ? pageSql
/* 39 */       .append(" limit ")
/* 39 */       .append(limitPlaceholder) : pageSql
/* 40 */       .append(" limit ")
/* 40 */       .append(limitPlaceholder).append(" offset ").append(offsetPlaceholder);
/* 41 */     return pageSql.toString();
/*    */   }
/*    */ }

/* Location:           /Users/rover/Downloads/mus-persistence.mybatis-1.0.1.jar
 * Qualified Name:     PostgreSQLDialect
 * JD-Core Version:    0.6.2
 */