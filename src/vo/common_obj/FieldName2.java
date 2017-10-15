package vo.common_obj;

/**
 * 字段所在类的全限定名与字段的简单名<br/>
 * 它唯一的描述了一个字段
 * @author 倪陆章
 *
 */
public class FieldName2 {
    public String fullQualifiedName;
	public String fieldName;
	
	/**
	 * 构造器
	 * @param fqn 字段所在类的全限定名
	 * @param fieldName 字段名
	 */
	public FieldName2(String fqn, String fieldName){
		this.fullQualifiedName=fqn;
		this.fieldName=fieldName;
	}
	
	@Override
	public int hashCode(){
		return fieldName.hashCode();
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof FieldName2))
			return false;
		FieldName2 tmp=(FieldName2)o;
		if(tmp.fullQualifiedName.equals(fullQualifiedName)&&
				tmp.fieldName.equals(fieldName))
			return true;
		return false;
	}

}
