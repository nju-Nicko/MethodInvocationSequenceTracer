package vo.common_obj;

/**
 * 字段的简单名
 * @author 倪陆章
 *
 */
public class FieldName {
	public String fieldName;
	
	/**
	 * 构造器
	 * @param fieldName 字段名
	 */
	public FieldName(String fieldName){
		this.fieldName=fieldName;
	}
	
	@Override
	public int hashCode(){
		return fieldName.hashCode();
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof FieldName))
			return false;
		FieldName tmp=(FieldName)o;
		if(tmp.fieldName.equals(fieldName))
			return true;
		return false;
	}
}
