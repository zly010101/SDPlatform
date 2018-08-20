/*    */ package cn.org.upthink.persistence.mybatis.dialect.db;
/*    */ 
/*    */ import cn.org.upthink.persistence.mybatis.dialect.Dialect;

/*    */
/*    */ public class HSQLDialect
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
/* 36 */     boolean hasOffset = offset > 0;
/*    */ 
/* 41 */     return new StringBuffer(sql
/* 38 */       .length() + 10)
/* 39 */       .append(sql)
/* 40 */       .insert(sql
/* 40 */       .toLowerCase().indexOf("select") + 6, " top " + limitPlaceholder)
/* 41 */       .toString();
/*    */   }
/*    */ }

/* Location:           /Users/rover/Downloads/mus-persistence.mybatis-1.0.1.jar
 * Qualified Name:     HSQLDialect
 * JD-Core Version:    0.6.2
 */