package cn.org.upthink.persistence.jpa.exception;

/**
 * DAO异常
 * <p>创建时间: 2013-2-13 下午7:52:52</p>
 */
public class DaoException extends BaseException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7425043077070328061L;

	public DaoException() {
		super();
	}

	public DaoException(String arg0) {
		super(arg0);
	}

	public DaoException(Throwable arg0) {
		super(arg0);
	}

	public DaoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DaoException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
