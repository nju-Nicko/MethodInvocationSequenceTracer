package vo.common_obj;

public class PrimitiveFieldInfo extends FieldInfo {
	
	public String value;
	
	/**
	 * 构造器
	 * @param fqn 字段所在类的全限定名
	 * @param fn 字段的简单名称
	 * @param ft 字段类型的全限定名
	 * @param v 值
	 */
	public PrimitiveFieldInfo(String fqn, String fn, String ft, String v){
		super(fqn, fn, ft);
		this.value=v;
	}

	/**
	 * 构造器
	 * @param ac 访问标识
	 * @param fqn 字段所在类的全限定名
	 * @param fn 字段名
	 * @param ft 字段类型的全限定名
	 * @param v 值
	 */
	public PrimitiveFieldInfo(int ac, String fqn, String fn, String ft, String v) {
		super(ac, fqn, fn, ft);
		// TODO Auto-generated constructor stub
		this.value=v;
	}

	public PrimitiveFieldInfo(FieldInfo field_info, String v) {
		// TODO Auto-generated constructor stub
		super(field_info);
		this.value=v;
	}
	
	/**
	 * 复制基本数据类型字段
	 */
	@Override
	public PrimitiveFieldInfo clone(){
		return new PrimitiveFieldInfo(access_flags, fullQualifiedName, fieldName, fieldType, value);
	}

}
