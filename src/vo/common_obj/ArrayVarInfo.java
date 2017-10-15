package vo.common_obj;

/**
 * 数组型变量信息类
 * @author 倪陆章
 *
 */
public class ArrayVarInfo extends VariableInfo{

	public int first_address;
	
	/**
	 * 数组型变量构造器
	 * @param ov 变量名
	 * @param isParam 是否是参数
	 * @param t 类型
	 * @param f 数组首单元地址
	 */
	public ArrayVarInfo(String ov, boolean isParam, String t, int f) {
		super(ov, isParam, t);
		first_address=f;
	}
	
	@Override
	public VariableInfo clone(){
		ArrayVarInfo avi=new ArrayVarInfo(name, isParam, type, first_address);
		return avi;
	}

}
