/*    */ package cn.org.upthink.persistence.mybatis.dialect.db;
/*    */ 
/*    */ import cn.org.upthink.persistence.mybatis.dialect.Dialect;

/*    */
/*    */ public class SQLServerDialect
/*    */   implements Dialect
/*    */ {
    @Override
/*    */   public boolean supportsLimit()
/*    */   {
/* 13 */     return true;
/*    */   }
/*    */ 
/*    */   static int getAfterSelectInsertPoint(String sql) {
/* 17 */     int selectIndex = sql.toLowerCase().indexOf("select");
/* 18 */     int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");
/* 19 */     return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
/*    */   }
/*    */ @Override
/*    */   public String getLimitString(String sql, int offset, int limit) {
/* 23 */     return getLimit(sql, offset, limit);
/*    */   }
/*    */ 
/*    */   public String getLimit(String sql, int offset, int limit)
/*    */   {
/* 40 */     if (offset > 0) {
/* 41 */       throw new UnsupportedOperationException("sql server has no offset");
/*    */     }
/*    */ 
/* 46 */     return new StringBuffer(sql.length() + 8)
/* 44 */       .append(sql)
/* 45 */       .insert(getAfterSelectInsertPoint(sql), 
/* 45 */       " top " + limit)
/* 46 */       .toString();
/*    */   }
/*    */ }

/* Location:           /Users/rover/Downloads/mus-persistence.mybatis-1.0.1.jar
 * Qualified Name:     SQLServerDialect
 * JD-Core Version:    0.6.2
 */