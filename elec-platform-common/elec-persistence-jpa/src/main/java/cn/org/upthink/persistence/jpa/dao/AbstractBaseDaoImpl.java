package cn.org.upthink.persistence.jpa.dao;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import cn.org.upthink.persistence.jpa.dto.Order;
import cn.org.upthink.persistence.jpa.dto.QueryResult;
import cn.org.upthink.persistence.jpa.exception.DaoException;
import cn.org.upthink.persistence.jpa.util.EntityUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 基类dao抽象实现，所有的dao实现层都要继承该类，并为泛型参数赋值
 * @param <T>泛型参数
 */
@Repository
@Qualifier("abstractRepository")
public abstract class AbstractBaseDaoImpl<T extends Serializable> implements BaseDao<T> {//extends HibernateDaoSupport
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 215110020842063052L;

	// 如果在jboss中使用的话，则下面的这个换成这个
	@PersistenceContext
	protected EntityManager em;//在dubbo内,已经不支持事务操作了. 需要sessionFactory来完成.todo

	@Resource
	protected JdbcTemplate jdbcTemplate;

	@Resource(name="hbntSessionFactory")
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session openSession() {
		return sessionFactory.openSession();
	}

	/**
	 * 日志记录
	 */
//	protected Logger LOG = LoggerFactory.getLogger(getClass());
	/**
	 * 泛型class
	 */
	@SuppressWarnings("unchecked")
	protected Class<T> entityClass = (Class<T>) EntityUtils.getEntityClass(this.getClass());
	/**
	 * 实体名称
	 */
	protected String entityName = getEntityName();
	/**
	 * 实体id名称
	 */
	protected String entityIdName = getEntityIdName();

	
	@Override
	public T merge(T entity) {
		/*T result = null;
		try {
			result = getEntityManager().merge(entity);
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return result;*/
		T result = null;
		Session s = null;
		try {
			s = this.openSession();
			s.save(entity);
			s.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(s!=null){
				s.close();
			}
		}
		return result;
	}

	@Override
	public void persist(T entity) {
		//requestInfoDataSource.setCustomerType("dataSource1");
		/*try {
			getEntityManager().persist(entity);
		} catch (Exception e) {
			throw new DaoException(e);
		}*/
		Session s = null;
		try {
			s = this.openSession();
			s.save(entity);
			s.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(s!=null){
				s.close();
			}
		}
	}
	
	@Override
	public void remove(Serializable... entityIds) {
		/*EntityManager em = getEntityManager();
		try {
			for (Serializable entityId : entityIds) {
				em.remove(em.getReference(entityClass, entityId));
			}
		} catch (Exception e) {
			throw new DaoException(e);
		}*/
		Session s = null;
		try {
			s = this.openSession();
			for (Serializable entityId : entityIds) {
				s.delete(entityId);
			}
			s.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(s!=null){
				s.close();
			}
		}
	}
	
	@Override
	public T find(Serializable entityId) {
		//requestInfoDataSource.setCustomerType("dataSource2");
		return getEntityManager().find(entityClass, entityId);
	}
	
	@Override
	public T find(Serializable entityId, LockModeType lockModeType) {
		//requestInfoDataSource.setCustomerType("dataSource2");
		return getEntityManager().find(entityClass, entityId, lockModeType);
	}
	
	@Override
	public T find(String whereSql, Object... params) {
		////requestInfoDataSource.setCustomerType("dataSource2");
		StringBuilder jpqlBuilder = new StringBuilder();
		jpqlBuilder.append("select o from ").append(entityName).append(" o ");
		buildWhereSql(whereSql, jpqlBuilder);
		TypedQuery<T> query = getQueryWithDefaultType(jpqlBuilder.toString());
		setParameters(query, params);
		T t = null;
		try {
			List<T> results = query.getResultList();
			if (null != results && !results.isEmpty()) {
				if (results.size() == 1) {
					t = results.get(0);
				} else {
					throw new NonUniqueResultException();
				}
			}
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		return t;
	}

	@Override
	public QueryResult<T> query(int firstResult, int maxResults, String whereSql, Object... params) {
		return query(null, firstResult, maxResults, whereSql, params);
	}

	@Override
	public QueryResult<T> query(int firstResult, int maxResults) {
		return query(null, firstResult, maxResults, null, new Object[0]);
	}

	@Override
	public long getCount(String whereSql, Object... params) {
		//requestInfoDataSource.setCustomerType("dataSource2");
		StringBuilder jpqlBuilder = new StringBuilder("select count(o) from ");
		jpqlBuilder.append(entityName).append(" o ");
		buildWhereSql(whereSql, jpqlBuilder);
		TypedQuery<Long> query = getQuery(jpqlBuilder.toString(), Long.class);
		setParameters(query, params);
		Long singleResult = query.getSingleResult();
		return singleResult == null ? 0L : (Long) singleResult;
	}

	/* (non-Javadoc)
	 * @see com.blackants.core.dao.BaseDao#update(java.lang.String, java.lang.Object[])
	 */
	@Override
	public int update(String jpql, Object... params) {
		/*Query query = getQuery(jpql);
		setParameters(query, params);
		return query.executeUpdate();*/
		Session session = null;
		try {
			session = this.openSession();
			org.hibernate.Query query = session.createQuery(jpql);
			setParameters2(query, params);
			return query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return 0;
	}

	@Override
	public Object queryForProperty(String property, Serializable entityId) {
		//requestInfoDataSource.setCustomerType("dataSource2");
		String jpql = "select o." + property + " from " + entityName + " o where o." + entityIdName + "=?1";
		try {
			return getQuery(jpql).setParameter(1, entityId).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void updateProperty(String property, Object value, Serializable entityId) {
		//requestInfoDataSource.setCustomerType("dataSource1");
		updateProperties(new String[] { property }, new Object[] { value }, entityId);
	}

	@Override
	public void updateProperties(String[] properties, Object[] values, Serializable entityId) {
		//requestInfoDataSource.setCustomerType("dataSource1");
		String jpql = "update " + entityName + " o set " + buildProperties(properties) + " where o." + entityIdName + "=?";
		/*Query query = getQuery(jpql);
		setParameters(query, values);
		query.setParameter(values.length + 1, entityId);
		int count = query.executeUpdate();
		if (count != 1)
			throw new DaoException("更新失败");*/
		Session session = null;
		try {
			session = this.openSession();
			org.hibernate.Query query = session.createQuery(jpql);
			setParameters2(query, values);
			query.setParameter(values.length + 1, entityId);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session!=null){
				session.close();
			}
		}
	}

	@Override
	public boolean isEntityExist(Serializable entityId) {
		return isEntityExist("o."+entityIdName+"=?1", entityId);
	}

	@Override
	public boolean isEntityExist(String whereSql, Object... params) {
		return getCount(whereSql, params) > 0L;
	}

	@Override
	public boolean contain(String jpql, Serializable entityId, Object obj) {
		//requestInfoDataSource.setCustomerType("dataSource2");
		Query query = getQuery("select count(o) from " + entityName + " o where o."+entityIdName+"=?1 and ?2 member of " + jpql, Long.class);
		setParameters(query, new Object[] { entityId, obj });
		return ((Long) query.getSingleResult() > 0);
	}

	@Override
	public List<T> list(Order order, String whereSql, Object... params) {
		return list(order, -1, -1, whereSql, params);
	}

	@Override
	public List<T> list(Order order, int firstResult, int maxResults,
			String whereSql, Object... params) {
		//requestInfoDataSource.setCustomerType("dataSource2");
		StringBuilder jpql = new StringBuilder("select ");
		jpql.append(" o from ");
		jpql.append(entityName).append(" o ");
		buildWhereSql(whereSql, jpql);
		buildOrderBy(order, jpql);
		TypedQuery<T> query = getQueryWithDefaultType(jpql.toString());
		
		// 设置参数
		setParameters(query, params);
		if (firstResult != -1 && maxResults != -1) {
			query.setFirstResult(firstResult).setMaxResults(maxResults);
		}
		return query.getResultList();
	}

	@Override
	public List<T> list(String whereSql, Object... params) {
		return list(null, -1, -1, whereSql, params);
	}

	@Override
	public List<T> list(int firstResult, int maxResults, String whereSql,
			Object... params) {
		return list(null, firstResult, maxResults, whereSql, params);
	}

	/* (non-Javadoc)
	 * @see com.blackants.core.dao.BaseDao#listJoin(com.blackants.core.dto.Order, java.lang.String, int, int, java.lang.String, java.lang.Object[])
	 */
	@Override
	public List<T> listJoin(Order order, String join, int firstResult,
			int maxResults, String whereSql, Object... params) {
		//requestInfoDataSource.setCustomerType("dataSource2");
		boolean flag = (null != join && !"".equals(join.trim()));
		StringBuilder jpql = new StringBuilder("select ");
		if (flag) {
			jpql.append(" distinct ");
		}
		jpql.append(" o from ");
		jpql.append(entityName).append(" o ");
		if (flag) {
			jpql.append(" ").append(join).append(" ");
		}
		buildWhereSql(whereSql, jpql);
		buildOrderBy(order, jpql);
		TypedQuery<T> query = getQueryWithDefaultType(jpql.toString());
		// 设置参数
		setParameters(query, params);
		if (firstResult != -1 && maxResults != -1){
			query.setFirstResult(firstResult).setMaxResults(maxResults);
		}
		return query.getResultList();
	}

	@Override
	public QueryResult<T> query(Order order, int firstResult, int maxResults,
			String whereSql, Object... params) {
		//requestInfoDataSource.setCustomerType("dataSource2");
		StringBuilder countBuilder = new StringBuilder();
		countBuilder.append("select count(o) from ").append(entityName).append(" o ");
		StringBuilder followBuilder = new StringBuilder();
		// 拼凑where语句
		buildWhereSql(whereSql, followBuilder);
		
		countBuilder.append(followBuilder);
		TypedQuery<Long> countQuery = getQuery(countBuilder.toString(), Long.class);
		setParameters(countQuery, params);
		long count = (Long) countQuery.getSingleResult();
		// 如果没有记录数为0则不必继续查了，直接返回。
		if (count < 1) {
			return new QueryResult<T>(null, 0);
		}
		
		StringBuilder resultBuilder = new StringBuilder();
		resultBuilder.append("select ");
		resultBuilder.append(" o from ").append(entityName).append(" o ");
		// 拼凑orderBy语句
		buildOrderBy(order, followBuilder);
		resultBuilder.append(followBuilder);
		TypedQuery<T> resultQuery = getQueryWithDefaultType(resultBuilder.toString());
		setParameters(resultQuery, params);
		if (firstResult > -1 && maxResults > -1) {
			resultQuery.setFirstResult(firstResult).setMaxResults(maxResults);
		}
		List<T> results = resultQuery.getResultList();
		return new QueryResult<T>(results, count);
	}

	/* (non-Javadoc)
	 * @see com.blackants.core.dao.BaseDao#queryJoin(com.blackants.core.dto.Order, java.lang.String, java.lang.String, int, int, java.lang.String, java.lang.Object[])
	 */
	@Override
	public QueryResult<T> queryJoin(Order order, String countJoin, String join,
			int firstResult, int maxResults, String whereSql, Object... params) {
		//requestInfoDataSource.setCustomerType("dataSource2");
		StringBuilder countBuilder = new StringBuilder();
		countBuilder.append("select count(distinct o) from ").append(entityName).append(" o ");
		if (null != countJoin && !"".equals(countJoin.trim())) {
			countBuilder.append(" ").append(countJoin).append(" ");
		}
		StringBuilder followBuilder = new StringBuilder();
		// 拼凑where语句
		buildWhereSql(whereSql, followBuilder);
		
		countBuilder.append(followBuilder);
		TypedQuery<Long> countQuery = getQuery(countBuilder.toString(), Long.class);
		setParameters(countQuery, params);
		long count = (Long) countQuery.getSingleResult();
		// 如果没有记录数为0则不必继续查了，直接返回。
		if (count < 1) {
			return new QueryResult<T>(null, 0);
		}
		
		StringBuilder resultBuilder = new StringBuilder();
		boolean flag = (null != join && !"".equals(join.trim()));
		resultBuilder.append("select ");
		if (flag) {
			resultBuilder.append(" distinct ");
		}
		resultBuilder.append(" o from ").append(entityName).append(" o ");
		if (flag) {
			resultBuilder.append(" ").append(join).append(" ");
		}
		// 拼凑orderBy语句
		buildOrderBy(order, followBuilder);
		resultBuilder.append(followBuilder);
		TypedQuery<T> resultQuery = getQueryWithDefaultType(resultBuilder.toString());
		setParameters(resultQuery, params);
		if (firstResult > -1 && maxResults > -1){
			resultQuery.setFirstResult(firstResult).setMaxResults(maxResults);
		}
		List<T> results = resultQuery.getResultList();
		return new QueryResult<T>(results, count);
	}

	@Override
	public QueryResult<T> query(Order order, int firstResult, int maxResults) {
		return query(order, firstResult, maxResults, null);
	}

	@Override
	public QueryResult<T> query(Order order, String whereSql, Object... params) {
		return query(order, -1, -1, whereSql, params);
	}
	@Override
	public QueryResult<T> query(String whereSql, Object... params) {
		return query(null, -1, -1, whereSql, params);
	}
	

	@Override
	public void detach(T entity) {
		//requestInfoDataSource.setCustomerType("dataSource2");
		getEntityManager().detach(entity);
	}

	@Override
	public void detach(T[] entities) {
		//requestInfoDataSource.setCustomerType("dataSource2");
		EntityManager em = getEntityManager();
		for (T entity : entities) {
			em.detach(entity);
		}
	}

	@Override
	public void flush() {
		//requestInfoDataSource.setCustomerType("dataSource2");
		getEntityManager().flush();
	}

	@Override
	public void refresh(T entity) {
		try {
			//requestInfoDataSource.setCustomerType("dataSource2");
			getEntityManager().refresh(entity);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public void refresh(T entity, LockModeType lockModeType) {
		try {
			//requestInfoDataSource.setCustomerType("dataSource2");
			getEntityManager().refresh(entity, lockModeType);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	@Override
	public void clear() {
		//requestInfoDataSource.setCustomerType("dataSource2");
		getEntityManager().clear();
	}

	@Override
	public Long getSum(String field, String whereSql, Object[] params) {
		//requestInfoDataSource.setCustomerType("dataSource2");
		StringBuilder sb = new StringBuilder("");
		buildWhereSql(whereSql, sb);
		String jpql = "select sum("+ field +") from " + entityName + " o " + sb.toString();
		Query query = getQuery(jpql);
		setParameters(query, params);
		Object singleResult = query.getSingleResult();
		return singleResult == null ? 0 : (Long) singleResult;
	}

	@Override
	public <E> List<E> list(String jpql, Object[] params, int firstResult, int maxResults, Class<E> resultClass) {
		//requestInfoDataSource.setCustomerType("dataSource2");
		TypedQuery<E> query = getQuery(jpql, resultClass);
		setParameters(query, params);
		if (firstResult > -1 && maxResults > -1) {
			query.setFirstResult(firstResult).setMaxResults(maxResults);
		}
		return query.getResultList();
	}

	@Override
	public <E> List<E> list(String jpql, Object[] params, Class<E> resultClass) {
		return list(jpql, params, -1, -1, resultClass);
	}

	/* (non-Javadoc)
	 * @see com.kangmei.core.dao.BaseDao#updateSQL(java.lang.String, java.lang.Object[])
	 */
	@Override
	public int updateSQL(String sql, Object... params) {
		/*Query query = getEntityManager().createNativeQuery(sql);
		setParameters(query, params);
		return query.executeUpdate();*/
		Session session = null;
		try {
			session = this.openSession();
			org.hibernate.Query query = session.createSQLQuery(sql);
			setParameters2(query, params);
			return query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.kangmei.core.dao.BaseDao#batchSave(java.util.List)
	 */
	@Override
	public void batchSave(List<T> entities) {
		/*EntityManager em = getEntityManager();
		int count = 0;
		for (T entity : entities) {
			em.persist(entity);
			count++;
			if (count % 200 == 0) {
				em.flush();
				em.clear();
			}
		}
		em.flush();
		em.clear();*/
		Session session = null;
		try {
			session = this.openSession();
			int count = 0;
			for (T entity : entities) {
				session.persist(entity);
				count++;
				if (count % 200 == 0) {
					session.flush();
					session.clear();
				}
			}
			session.flush();
			session.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(session!=null){
				session.close();
			}
		}
	}


	/* (non-Javadoc)
	 * @see com.kangmei.core.dao.BaseDao#queryForSingle(java.lang.Class, java.lang.String, java.lang.Object[])
	 */
	@Override
	public <E> E queryForSingle(Class<E> resultType, String jpql, Object... params) {
		//requestInfoDataSource.setCustomerType("dataSource2");
		TypedQuery<E> query = getQuery(jpql, resultType);
		setParameters(query, params);
		List<E> results = query.getResultList();
		if (null == results || results.isEmpty()){
			return null;
		}
		return results.get(0);
	}

	/* (non-Javadoc)
	 * @see com.kangmei.core.dao.BaseDao#queryForList(java.lang.Class, java.lang.String, java.lang.Object[])
	 */
	@Override
	public <E> List<E> queryForList(Class<E> resultType, String jpql, Object... params) {
		return queryForList(resultType, jpql, -1, -1, params);
	}

	/* (non-Javadoc)
	 * @see com.kangmei.core.dao.BaseDao#queryForList(java.lang.Class, java.lang.String, int, int, java.lang.Object[])
	 */
	@Override
	public <E> List<E> queryForList(Class<E> resultType, String jpql, int firstResult, int maxResults, Object... params) {
		//requestInfoDataSource.setCustomerType("dataSource2");
		TypedQuery<E> query = getQuery(jpql, resultType);
		setParameters(query, params);
		if (firstResult > -1 && maxResults > -1) {
			query.setFirstResult(firstResult).setMaxResults(maxResults);
		}
		return query.getResultList();
	}

	/**
	 * 将属性连接起来
	 * @param properties 属性数组
	 * @return 如果属性数组为空，或者长度为0，则返回""空字符串，否则返回"o.username=?,o.password=?"
	 */
	protected static String buildProperties(String[] properties) {
		if (null == properties || properties.length == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (String property : properties) {
			sb.append("o.").append(property).append("=?,");
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * 拼凑orderBy语句（当order不为null，且包含最少一个元素时才会进行拼凑）
	 * @param order 排序
	 * @param sb 包含jpql语句的StringBuilder
	 */
	protected static void buildOrderBy(Order order, StringBuilder sb) {
		if (null != order && !order.isEmpty()) {
			sb.append(" order by ");
			for (String key : order.keySet()) {
				sb.append("o.").append(key).append(" ").append(order.get(key)).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
	}

	/**
	 * 拼凑where语句（当whereSql不为空的且不为空字符串的时候，才有进行拼凑）
	 * @param whereSql where语句
	 * @param sb 包含jpql语句的StringBuilder
	 */
	protected static void buildWhereSql(String whereSql, StringBuilder sb) {
		if (null != whereSql && !"".equals(whereSql.trim())) {
			sb.append(" where ").append(whereSql.trim()).append(" ");
		}
	}

	/**
	 * 设置参数
	 * @param query 查询query
	 * @param params 参数数组
	 */
	protected static void setParameters(Query query, Object[] params) {
		if (null != query && null != params && params.length > 0) {
			for (int i = 1; i <= params.length; i++) {
				query.setParameter(i, params[i - 1]);
			}
		}
	}

	/**注意与上面的差异*/
	protected static void setParameters2(org.hibernate.Query query, Object[] params) {
		if (null != query && null != params && params.length > 0) {
			for (int i = 1; i <= params.length; i++) {
				query.setParameter(i, params[i - 1]);
			}
		}
	}

	/**
	 * 获得实体ID名称，主键
	 * <p>
	 * 先看看字段有没有注解Id,如果字段上没有注解，则在getter方法上面找.
	 * </p>
	 * @return
	 */
	private String getEntityIdName() {
		if (null != entityIdName && !"".equals(entityIdName)) {
			return entityIdName;
		}
		Field[] fields = entityClass.getDeclaredFields();
		Id id = null;
		String entityId = null;
		// 先看看字段有没有注解Id
		for (Field field : fields) {
			id = field.getAnnotation(Id.class);
			if (null != id) {
				entityId = field.getName();
				break;
			}
		}
		// 如果字段上没有注解，则在getter方法上面找.
		if (null == id) {
			try {
				PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(entityClass).getPropertyDescriptors();
				Method readMethod = null;
				for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
					readMethod = propertyDescriptor.getReadMethod();
					id = readMethod.getAnnotation(Id.class);
					if (null != id) {
						entityId = propertyDescriptor.getName();
						break;
					}
				}
			} catch (IntrospectionException e) {
				throw new DaoException(e);
			}
		}

		if (entityId == null) {
			throw new DaoException("主键没有设置");
		}

		return entityId;
	}

	/**
	 * 获得实体的类名
	 * @return 如果注解上面没有写name属性，则直接使用类名，否则返回注解上面的name值
	 */
	protected String getEntityName() {
		String entityName = entityClass.getSimpleName();
		Entity entity = entityClass.getAnnotation(Entity.class);
		String name = null;
		if (null != entity) {
			name = entity.name();
		}
		return null == name || "".equals(name.trim()) ? entityName : name.trim();
	}

	/**
	 * 获取EntityManager对象
	 * @return 返回当前的线程的EntityManager对象
	 */
	protected EntityManager getEntityManager() {
		return em;
	}
	/**
	 * 默认是实体作为类别，获取query对象
	 * @param jpql jpql语句
	 * @return 返回TypedQuery
	 * @see TypedQuery
	 */
	protected TypedQuery<T> getQueryWithDefaultType(String jpql) {
		return getEntityManager().createQuery(jpql, entityClass);
	}
	/**
	 * 自动识别类别，获取query对象
	 * @param jpql jpql语句
	 * @return 返回query
	 * @see Query
	 */
	protected Query getQuery(String jpql) {
		return getEntityManager().createQuery(jpql);
	}
	
	/**
	 * 通过返回类型获取query对象
	 * @param jpql jpql语句
	 * @param resultClass 返回类型
	 * @return  返回TypedQuery
	 * @see TypedQuery
	 */
	protected <X> TypedQuery<X> getQuery(String jpql, Class<X> resultClass) {
		return getEntityManager().createQuery(jpql, resultClass);
	}

	/**
	 *
	 * @param sql
	 * @param params
     * @return
     */
	@Override
	public List queryListByJdbc(String sql, String[] params){
		//EntityManager em = getEntityManager();
		Query query = getEntityManager().createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (null != query && null != params && params.length > 0) {
			for (int i = 1; i <= params.length; i++) {
				query.setParameter(i, params[i - 1]);
			}
		}
		return query.getResultList();
	}

	/**
	 * 根据jdbc来查询Map
	 * @param sql
	 * @param params
     * @return
     */
	@Override
	public Map queryMapByJdbc(String sql, String[] params){
		Query query = getEntityManager().createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		if (null != query && null != params && params.length > 0) {
			for (int i = 1; i <= params.length; i++) {
				query.setParameter(i, params[i - 1]);
			}
		}
		List list = query.getResultList();
		if(list!=null && !list.isEmpty()){
			Map objMap = (Map)list.get(0);
			return objMap;
		}else{
			return null;
		}
	}
}
