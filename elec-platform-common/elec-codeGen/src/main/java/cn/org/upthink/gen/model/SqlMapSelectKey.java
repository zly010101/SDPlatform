package cn.org.upthink.gen.model;

 /**
  * selectkey实体，当指定主键生成策略为sequence时需要生成该类
  * <功能详细描述>
  * 
  * @see  [相关类/方法]
  * @since  [产品/模块版本]
  */
public class SqlMapSelectKey {
    
    /** 对应属性 */
    private String keyProperty;
    
    /** 对应属性类型 */
    private String resultType;
    
    /** 对应sequece名 */
    private String sequence;

    /**
     * @return 返回 keyProperty
     */
    public String getKeyProperty() {
        return keyProperty;
    }

    /**
     */
    public void setKeyProperty(String keyProperty) {
        this.keyProperty = keyProperty;
    }

    /**
     * @return 返回 resultType
     */
    public String getResultType() {
        return resultType;
    }

    /**
     */
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    /**
     * @return 返回 sequence
     */
    public String getSequence() {
        return sequence;
    }

    /**
     */
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }
}
