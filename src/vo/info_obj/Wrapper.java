package vo.info_obj;

import java.util.ArrayList;

/**
 * 包装值类型对类型和返回信息列表
 * @author 倪陆章
 *
 */
public class Wrapper {
	
	public ValueTypePair vtp;
	public ArrayList<ReturnInfo> ret;
	public int type; //为0表示使用值类型对，为1表示使用返回信息列表
	
	public Wrapper(){
		this.vtp=null;
		this.ret=null;
		type=-1;
	}
	
	/**
	 * 构造器
	 * @param vtp 待包装的值-类型对
	 */
	public Wrapper(ValueTypePair vtp){
		this.vtp=vtp;
		ret=null;
		type=0;
	}
	
	/**
	 * 构造器
	 * @param ret 待包装的方法返回信息列表
	 */
	public Wrapper(ArrayList<ReturnInfo> ret){
		this.ret=ret;
		vtp=null;
		type=1;
	}

}
