package vo.common_obj;

/**
 * 局部变量信息类
 * @author 倪陆章
 *
 */
public class VariableInfo{
	
	public String name;  //变量名
	public boolean isParam=false;   //表示是否是参数，区别于局部变量。
	public String type;   //变量的外观类型
//	public String actualType; //变量的实际类型
	
	/**
	 * 未初始化变量的构造器
	 * @param ov 变量名
	 * @param isParam 变量是否是方法参数
	 * @param t 变量外观类型，是一个全限定类型
	 */
	public VariableInfo(String ov, boolean isParam, String t){
		name=ov;
		this.isParam =isParam;
		type=t;
	}
	
	@Override
	public VariableInfo clone(){
		return new VariableInfo(name, isParam, type);
	}
	
}
