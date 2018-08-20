/***/
package cn.org.upthink.persistence.mybatis.interceptor;

import cn.org.upthink.persistence.mybatis.dialect.db.*;
import cn.org.upthink.persistence.mybatis.dialect.Dialect;
import cn.org.upthink.persistence.mybatis.dto.Page;
import cn.org.upthink.persistence.mybatis.util.Reflections;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.plugin.Interceptor;

import java.io.Serializable;
import java.util.Properties;

/**
 * Mybatis分页拦截器基类
 */
public abstract class BaseInterceptor implements Interceptor, Serializable {
	
	private static final long serialVersionUID = 1L;

    protected static final String PAGE = "page";
    
    protected static final String DELEGATE = "delegate";

    protected static final String MAPPED_STATEMENT = "mappedStatement";

    protected Log log = LogFactory.getLog(this.getClass());

    protected Dialect dialect;

//    /**
//     * 拦截的ID，在mapper中的id，可以匹配正则
//     */
//    protected String _SQL_PATTERN = "";

    /**
     * 对参数进行转换和检查
     * @param parameterObject 参数对象
     * @param page            分页对象
     * @return 分页对象
     * @throws NoSuchFieldException 无法找到参数
     */
    @SuppressWarnings("unchecked")
	protected static Page<Object> convertParameter(Object parameterObject, Page<Object> page) {
    	try{
            if (parameterObject instanceof Page) {
                return (Page<Object>) parameterObject;
            } else {
                return (Page<Object>) Reflections.getFieldValue(parameterObject, PAGE);
            }
    	}catch (Exception e) {
			return null;
		}
    }

    /**
     * 设置属性，支持自定义方言类和制定数据库的方式
     * <code>dialectClass</code>,自定义方言类。可以不配置这项
     * <ode>dbms</ode> 数据库类型，插件支持的数据库
     * <code>sqlPattern</code> 需要拦截的SQL ID
     * @param p 属性
     */
    protected void initProperties(Properties p) {
    	Dialect vDialect = null;
        String dbType = "mysql";//Global.getConfig("jdbc.type"); oracle
        if ("db2".equals(dbType)){
            vDialect = new DB2Dialect();
        }else if("derby".equals(dbType)){
            vDialect = new DerbyDialect();
        }else if("h2".equals(dbType)){
            vDialect = new H2Dialect();
        }else if("hsql".equals(dbType)){
            vDialect = new HSQLDialect();
        }else if("mysql".equals(dbType)){
            vDialect = new MySQLDialect();
        }else if("oracle".equals(dbType)){
            vDialect = new OracleDialect();
        }else if("postgre".equals(dbType)){
            vDialect = new PostgreSQLDialect();
        }else if("mssql".equals(dbType) || "sqlserver".equals(dbType)){
            vDialect = new SQLServer2005Dialect();
        }else if("sybase".equals(dbType)){
            vDialect = new SybaseDialect();
        }
        if (vDialect == null) {
            throw new RuntimeException("mybatis dialect error.");
        }
        dialect = vDialect;
//        _SQL_PATTERN = p.getProperty("sqlPattern");
//        _SQL_PATTERN = Global.getConfig("mybatis.pagePattern");
//        if (StringUtils.isEmpty(_SQL_PATTERN)) {
//            throw new RuntimeException("sqlPattern property is not found!");
//        }
    }
}
