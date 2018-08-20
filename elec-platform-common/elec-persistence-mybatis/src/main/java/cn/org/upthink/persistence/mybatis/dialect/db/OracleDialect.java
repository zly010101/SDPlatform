/*    */ package cn.org.upthink.persistence.mybatis.dialect.db;
/*    */ 
/*    */ import cn.org.upthink.persistence.mybatis.dialect.Dialect;

/*    */
/*    */ public class OracleDialect
/*    */   implements Dialect
/*    */ { @Override
/*    */   public boolean supportsLimit()
/*    */   {
/* 12 */     return true;
/*    */   }
/*    */   @Override
/*    */   public String getLimitString(String sql, int offset, int limit) {
/* 16 */     return getLimitString(sql, offset, Integer.toString(offset), Integer.toString(limit));
/*    */   }
/*    */ 
/*    */   public String getLimitString(String sql, int offset, String offsetPlaceholder, String limitPlaceholder)
/*    */   {
/* 34 */     sql = sql.trim();
/* 35 */     boolean isForUpdate = false;
/* 36 */     if (sql.toLowerCase().endsWith(" for update")) {
/* 37 */       sql = sql.substring(0, sql.length() - 11);
/* 38 */       isForUpdate = true;
/*    */     }
/* 40 */     StringBuilder pagingSelect = new StringBuilder(sql.length() + 100);
/*    */ 
/* 42 */     if (offset > 0)
/* 43 */       {pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");}
/*    */     else {
/* 45 */       pagingSelect.append("select * from ( ");
/*    */     }
/* 47 */     pagingSelect.append(sql);
/* 48 */     if (offset > 0) {
/* 49 */       String endString = new StringBuilder().append(offsetPlaceholder).append("+").append(limitPlaceholder).toString();
/* 50 */       pagingSelect.append(new StringBuilder().append(" ) row_ where rownum <= ").append(endString).append(") where rownum_ > ").toString()).append(offsetPlaceholder);
/*    */     } else {
/* 52 */       pagingSelect.append(new StringBuilder().append(" ) where rownum <= ").append(limitPlaceholder).toString());
/*    */     }
/*    */ 
/* 55 */     if (isForUpdate) {
/* 56 */       pagingSelect.append(" for update");
/*    */     }
/*    */ 
/* 59 */     return pagingSelect.toString();
/*    */   }
/*    */ }

/* Location:           /Users/rover/Downloads/mus-persistence.mybatis-1.0.1.jar
 * Qualified Name:     OracleDialect
 * JD-Core Version:    0.6.2
 */