package cn.org.upthink.persistence.jpa.dto;

import java.util.LinkedHashMap;

/**
 * 排序Bean,继承自LinkedHashMap
 */
public class Order extends LinkedHashMap<String, String> {
	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 5303704014009245091L;
	/**
	 * 降序
	 */
	public static final String DESC = "DESC";
	/**
	 * 升序
	 */
	public static final String ASC = "ASC";
	
	public Order() {
		super();
	}
	public Order(LinkedHashMap<String, String> order) {
		super();
		if (null != order && !order.isEmpty()) {
			this.putAll(order);
		}
	}
}
