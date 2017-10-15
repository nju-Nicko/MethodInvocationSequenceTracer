package vo.common_obj;

public class ArrFieldInfo extends FieldInfo {
	
	public int f;
	
	/**
	 * 构造器
	 * @param fqn 字段所在类的全限定名
	 * @param fn 字段名
	 * @param ft 字段描述符，即字段全限定类型
	 * @param f 首地址
	 */
	public ArrFieldInfo(String fqn, String fn, String ft, int f){
		super(fqn, fn, ft);
		this.f=f;
	}

	/**
	 * 构造器
	 * @param ac 访问标志
	 * @param fqn 字段所在类的全限定名
	 * @param fn 字段名
	 * @param ft 字段名
	 * @param f 第一个单元的地址
	 */
	public ArrFieldInfo(int ac, String fqn, String fn, String ft, int f) {
		super(ac, fqn, fn, ft);
		// TODO Auto-generated constructor stub
		this.f=f;
	}

	public ArrFieldInfo(FieldInfo field_info, int i) {
		// TODO Auto-generated constructor stub
		super(field_info);
		this.f=i;
	}
	
	/**
	 * 复制数组类型字段
	 */
	@Override
	public ArrFieldInfo clone(){
		return new ArrFieldInfo(access_flags, fullQualifiedName, fieldName, fieldType, f);
	}

}
