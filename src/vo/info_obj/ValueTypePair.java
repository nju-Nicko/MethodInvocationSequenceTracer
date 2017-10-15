package vo.info_obj;

/**
 * 表达式的值-类型对或者方法调用返回的值-类型对
 * @author 倪陆章
 *
 */
public class ValueTypePair {
	
	public String type;
	
	/**
	 * 构造器
	 * @param t 全限定类型
	 */
	public ValueTypePair(String t){
		type=t;
	}
	
	/**
	 * 判断是否相等
	 * @param o 另一个值-类型对
	 * @return true 相等；false 不等
	 */
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof ValueTypePair))
			return false;
		ValueTypePair o=(ValueTypePair)obj;
		if(o.type.toString().equals(this.type.toString())) return true;
		return false;
	}
	
	/**
	 * 复制
	 */
	@Override
	public ValueTypePair clone(){
		return new ValueTypePair(type);
	}

}
