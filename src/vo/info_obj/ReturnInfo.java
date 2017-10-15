package vo.info_obj;

/**
 * 方法调动在相应路径约束下的返回值
 * @author 倪陆章
 *
 */
public class ReturnInfo {
	public ConstraintInfo pc;
	public ValueTypePair retvalue;
	
	/**
	 * 构造器
	 * @param p 值-类型对
	 */
	public ReturnInfo(ValueTypePair p){
		retvalue=p;
		pc=new ConstraintInfo();
	}
	
	/**
	 * 构造器
	 * @param pc 路径约束
	 * @param p 值-类型对
	 */
	public ReturnInfo(ConstraintInfo pc, ValueTypePair p){
		this.pc=pc;
		retvalue=p;
	}
}
