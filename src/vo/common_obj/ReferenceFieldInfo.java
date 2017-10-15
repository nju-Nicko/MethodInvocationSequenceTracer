package vo.common_obj;

public class ReferenceFieldInfo extends FieldInfo {
	
	public int handle;
	
	/**
	 * 构造器
	 * @param fqn 字段所在类的全限定名
	 * @param fn 字段简单名称
	 * @param ft 字段全限定类型
	 * @param h 句柄
	 */
	public ReferenceFieldInfo(String fqn, String fn, String ft, int h){
		super(fqn, fn, ft);
		this.handle=h;
	}

	/**
	 * 构造器
	 * @param ac 访问标识
	 * @param fqn 字段所在类的全限定名
	 * @param fn 字段简单名称
	 * @param ft 字段全限定类型
	 * @param h 句柄
	 */
	public ReferenceFieldInfo(int ac, String fqn, String fn, String ft, int handle) {
		super(ac, fqn, fn, ft);
		// TODO Auto-generated constructor stub
		this.handle=handle;
	}

	public ReferenceFieldInfo(FieldInfo field_info, int i) {
		// TODO Auto-generated constructor stub
		super(field_info);
		this.handle=i;
	}
	
	/**
	 * 复制引用类型字段
	 */
	@Override
	public ReferenceFieldInfo clone(){ 
		return new ReferenceFieldInfo(access_flags, fullQualifiedName, fieldName, fieldType, handle);
	}

}
