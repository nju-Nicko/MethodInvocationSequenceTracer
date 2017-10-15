package vo.common_obj;

/**
 * 类的一个字段的信息
 * @author 倪陆章
 *
 */
public class FieldInfo {
	
	public int access_flags;
	public String fullQualifiedName;
	public String fieldName;
	public String fieldType;
	
	/**
	 * 构造器
	 * @param fullQualifiedName 字段所在的类的全限定名
	 * @param fn 字段名
	 * @param ft 字段描述符，即字段全限定类型
	 */
	public FieldInfo(String fullQualifiedName, String fn, String ft){
		this.fullQualifiedName=fullQualifiedName;
		this.access_flags=0x0000;
		this.fieldName=fn;
		this.fieldType=ft;
	}
	
	/**
	 * 构造器
	 * @param ac 字段访问标识
	 * @param fqn 字段所在类的全限定名
	 * @param fn 字段名
	 * @param ft 字段描述符，即字段的全限定类型
	 */
	public FieldInfo(int ac, String fqn, String fn, String ft){
		this.access_flags=ac;
		this.fullQualifiedName=fqn;
		this.fieldName=fn;
		this.fieldType=ft;
	}
	
	/**
	 * 构造器
	 * @param fi 字段信息对象
	 */
	public FieldInfo(FieldInfo fi){
		this.access_flags=fi.access_flags;
		this.fullQualifiedName=fi.fullQualifiedName;
		this.fieldName=fi.fieldName;
		this.fieldType=fi.fieldType;
	}
	
	/**
	 * 获取FieldName
	 * @return 字段的FiedName
	 */
	public FieldName getFieldName(){
		return new FieldName(fieldName);
	}
	
	/**
	 * 获取FieldName2
	 * @return 字段的FieldName2
	 */
	public FieldName2 getFieldName2(){
		return new FieldName2(fullQualifiedName, fieldName);
	}
	
	@Override
	public int hashCode(){
		return fieldName.hashCode();
	}
	
	@Override
	public boolean equals(Object fi){
		if(!(fi instanceof FieldInfo))
			return false;
		FieldInfo tmp=(FieldInfo)fi;
		if(fullQualifiedName.equals(tmp.fullQualifiedName)&&
				fieldName.equals(tmp.fieldName)&&
				   fieldType.equals(tmp.fieldType))
			return true;
		return false;
	}
	
	@Override
	public FieldInfo clone(){
		return new FieldInfo(access_flags, fullQualifiedName, fieldName, fieldType);
	}
}
