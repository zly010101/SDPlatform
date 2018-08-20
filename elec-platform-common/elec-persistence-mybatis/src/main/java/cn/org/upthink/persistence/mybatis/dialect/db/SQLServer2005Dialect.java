/*    */ package cn.org.upthink.persistence.mybatis.dialect.db;
/*    */ 
/*    */ import cn.org.upthink.persistence.mybatis.dialect.Dialect;

/*    */
/*    */ public class SQLServer2005Dialect
/*    */   implements Dialect
/*    */ { @Override
/*    */   public boolean supportsLimit()
/*    */   {
/* 12 */     return true;
/*    */   }
/*    */   @Override
/*    */   public String getLimitString(String sql, int offset, int limit) {
/* 16 */     return getLimitString(sql, offset, limit, 
/* 17 */       Integer.toString(limit));
/*    */   }
/*    */ 
/*    */   private String getLimitString(String querySqlString, int offset, int limit, String limitPlaceholder)
/*    */   {
/* 39 */     StringBuilder pagingBuilder = new StringBuilder();
/* 40 */     String orderby = getOrderByPart(querySqlString);
/* 41 */     String distinctStr = "";
/*    */ 
/* 43 */     String loweredString = querySqlString.toLowerCase();
/* 44 */     String sqlPartString = querySqlString;
/* 45 */     if (loweredString.trim().startsWith("select")) {
/* 46 */       int index = 6;
/* 47 */       if (loweredString.startsWith("select distinct")) {
/* 48 */         distinctStr = "DISTINCT ";
/* 49 */         index = 15;
/*    */       }
/* 51 */       sqlPartString = sqlPartString.substring(index);
/*    */     }
/* 53 */     pagingBuilder.append(sqlPartString);
/*    */ 
/* 56 */     if ((orderby == null) || ("".equals(orderby))) {
/* 57 */       orderby = "ORDER BY CURRENT_TIMESTAMP";
/*    */     }
/*    */ 
/* 60 */     StringBuilder result = new StringBuilder();
/* 61 */     result.append("WITH query AS (SELECT ")
/* 62 */       .append(distinctStr)
/* 63 */       .append("TOP 100 PERCENT ")
/* 64 */       .append(" ROW_NUMBER() OVER (")
/* 65 */       .append(orderby)
/* 66 */       .append(") as __row_number__, ")
/* 67 */       .append(pagingBuilder)
/* 68 */       .append(") SELECT * FROM query WHERE __row_number__ BETWEEN ")
/* 69 */       .append(offset + 1)
/* 69 */       .append(" AND ").append(offset + limit)
/* 70 */       .append(" ORDER BY __row_number__");
/*    */ 
/* 72 */     return result.toString();
/*    */   }
/*    */ 
/*    */   static String getOrderByPart(String sql) {
/* 76 */     String loweredString = sql.toLowerCase();
/* 77 */     int orderByIndex = loweredString.indexOf("order by");
/* 78 */     if (orderByIndex != -1)
/*    */     {
/* 81 */       return sql.substring(orderByIndex);
/*    */     }
/* 83 */     return "";
/*    */   }
/*    */ }

/* Location:           /Users/rover/Downloads/mus-persistence.mybatis-1.0.1.jar
 * Qualified Name:     SQLServer2005Dialect
 * JD-Core Version:    0.6.2
 */