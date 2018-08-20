/*    */ package cn.org.upthink.persistence.mybatis.dialect.db;
/*    */ 
/*    */ import cn.org.upthink.persistence.mybatis.dialect.Dialect;

/*    */
/*    */ public class MySQLDialect
/*    */   implements Dialect
/*    */ { @Override
/*    */   public String getLimitString(String sql, int offset, int limit)
/*    */   {
/* 14 */     return getLimitString(sql, offset, Integer.toString(offset), 
/* 15 */       Integer.toString(limit));
/*    */   }
/*    */   @Override
/*    */   public boolean supportsLimit()
/*    */   {
/* 19 */     return true;
/*    */   }
/*    */ 
/*    */   public String getLimitString(String sql, int offset, String offsetPlaceholder, String limitPlaceholder)
/*    */   {
/* 37 */     StringBuilder stringBuilder = new StringBuilder(sql);
/* 38 */     stringBuilder.append(" limit ");
/* 39 */     if (offset > 0)
/* 40 */       {stringBuilder.append(offsetPlaceholder).append(",").append(limitPlaceholder);}
/*    */     else {
/* 42 */       stringBuilder.append(limitPlaceholder);
/*    */     }
/* 44 */     return stringBuilder.toString();
/*    */   }
/*    */ }

/* Location:           /Users/rover/Downloads/mus-persistence.mybatis-1.0.1.jar
 * Qualified Name:     MySQLDialect
 * JD-Core Version:    0.6.2
 */