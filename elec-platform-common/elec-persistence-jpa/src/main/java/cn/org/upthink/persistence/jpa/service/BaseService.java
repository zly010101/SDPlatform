package cn.org.upthink.persistence.jpa.service;

import cn.org.upthink.persistence.jpa.dto.QueryResult;
import cn.org.upthink.persistence.jpa.dto.Pager;

import java.io.Serializable;
import java.util.List;

import javax.persistence.LockModeType;

/**
 * service层基类，所有的service层接口都要继承自该接口
 */

public interface BaseService<T> extends Serializable{

	/**
	 * 保存实体
	 * 
	 * @param entity
	 *            实体
	 */
	public boolean save(T entity);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 *            实体
	 */
	public boolean update(T entity);

	/**
	 * 删除实体
	 * 
	 * @param entityIds
	 *            实体ids
	 */
	public void delete(Serializable... entityIds);

	/**
	 * 获得实体
	 * 
	 * @param id
	 *            实体id
	 */
	public T get(Serializable id);
	
	public T lockEntity(Serializable id, LockModeType lockModeType);

	/**
	 * 判断实体是否存在
	 * 
	 * @param entityId
	 *            实体id
	 * @return 存在返回true，不存在返回false。
	 */
	public boolean isEntityExist(Serializable entityId);

	/**
	 * 查询全部记录
	 * 
	 * @param pager
	 *            分页
	 * @return 如果pager为空，则返回null，否则返回QueryResult
	 */
	public QueryResult<T> query(Pager pager);
	/**
	 * 查询所有的实体
	 * @return 实体列表
	 */
	public List<T> all();

	public int updateSQL(String sql, Object... params);

	/**根据jdbc来查询List*/
	public List queryListByJdbc(String sql, String[] params);

	/**根据jdbc来查询Map*/
	public java.util.Map queryMapByJdbc(String sql, String[] params);

}
