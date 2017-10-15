package vo.common_obj;

/**
 * 引用类型变量信息类
 * @author 倪陆章
 *
 */
public class ReferenceVarInfo extends VariableInfo {

	public int handle;	

	/**
	 * 引用类型变量构造器
	 * @param ov 变量名
	 * @param isParam 是否参数
	 * @param t 全限定类型
	 * @param h 句柄
	 */
	public ReferenceVarInfo(String ov, boolean isParam, String t, int h) {
		super(ov, isParam, t);
		this.handle=h;
	}
	
	@Override
	public VariableInfo clone(){
		return new ReferenceVarInfo(name, isParam, type, handle);
	}

}
