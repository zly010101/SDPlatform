/*    */ package cn.org.upthink.persistence.mybatis.dialect.db;
/*    */ 
/*    */ import cn.org.upthink.persistence.mybatis.dialect.Dialect;

/*    */
/*    */ public class DB2Dialect
/*    */   implements Dialect
/*    */ {
    @Override
/*    */   public boolean supportsLimit()
/*    */   {
/* 14 */     return true;
/*    */   }
/*    */ 
/*    */   private static String getRowNumber(String sql)
/*    */   {
/* 19 */     StringBuilder rownumber = new StringBuilder(50)
/* 19 */       .append("rownumber() over(");
/*    */ 
/* 21 */     int orderByIndex = sql.toLowerCase().indexOf("order by");
/*    */ 
/* 23 */     if ((orderByIndex > 0) && (!hasDistinct(sql))) {
/* 24 */       rownumber.append(sql.substring(orderByIndex));
/*    */     }
/*    */ 
/* 27 */     rownumber.append(") as rownumber_,");
/*    */ 
/* 29 */     return rownumber.toString();
/*    */   }
/*    */ 
/*    */   private static boolean hasDistinct(String sql) {
/* 33 */     return sql.toLowerCase().contains("select distinct");
/*    */   }
/*    */  @Override
/*    */   public String getLimitString(String sql, int offset, int limit) {
/* 37 */     return getLimitString(sql, offset, Integer.toString(offset), Integer.toString(limit));
/*    */   }
/*    */ 
/*    */   public String getLimitString(String sql, int offset, String offsetPlaceholder, String limitPlaceholder)
/*    */   {
/* 55 */     int startOfSelect = sql.toLowerCase().indexOf("select");
/*    */ 
/* 60 */     StringBuilder pagingSelect = new StringBuilder(sql.length() + 100)
/* 58 */       .append(sql
/* 58 */       .substring(0, startOfSelect))
/* 59 */       .append("select * from ( select ")
/* 60 */       .append(getRowNumber(sql));
/*    */ 
/* 62 */     if (hasDistinct(sql)) {
/* 63 */       pagingSelect.append(" row_.* from ( ")
/* 64 */         .append(sql
/* 64 */         .substring(startOfSelect))
/* 65 */         .append(" ) as row_");
/*    */     }
/*    */     else {
/* 67 */       pagingSelect.append(sql.substring(startOfSelect + 6));
/*    */     }
/*    */ 
/* 70 */     pagingSelect.append(" ) as temp_ where rownumber_ ");
/*    */ 
/* 73 */     if (offset > 0)
/*    */     {
/* 75 */       String endString = new StringBuilder().append(offsetPlaceholder).append("+").append(limitPlaceholder).toString();
/* 76 */       pagingSelect.append("between ").append(offsetPlaceholder)
/* 77 */         .append("+1 and ")
/* 77 */         .append(endString);
/*    */     } else {
/* 79 */       pagingSelect.append("<= ").append(limitPlaceholder);
/*    */     }
/*    */ 
/* 82 */     return pagingSelect.toString();
/*    */   }
/*    */ }

/* Location:           /Users/rover/Downloads/mus-persistence.mybatis-1.0.1.jar
 * Qualified Name:     DB2Dialect
 * JD-Core Version:    0.6.2
 */