package vo.common_obj;

import java.util.Stack;

/**
 * 基本数据类型变量信息类
 * @author 倪陆章
 *
 */
public class PrimitiveVarInfo extends VariableInfo{

	public Stack<Object> value;
	
	/**
	 * 基本数据类型变量构造器
	 * @param ov 变量名
	 * @param isParam 是否参数
	 * @param t 类型
	 * @param nv 变量值
	 */
	public PrimitiveVarInfo(String ov, boolean isParam, String t, String nv) {
		super(ov, isParam, t);
		value=new Stack<Object>();
	    value.push(nv);
	}

	@Override
	public VariableInfo clone(){
		return new PrimitiveVarInfo(name, isParam, type, (String)value.peek());
	}
}
