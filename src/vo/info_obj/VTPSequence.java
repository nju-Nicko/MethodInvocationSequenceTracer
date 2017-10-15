package vo.info_obj;

import java.util.ArrayList;



/**
 * 相应PC下各个表达式的值
 * @author 倪陆章
 *
 */
public class VTPSequence {
	
	public ConstraintInfo pc;
	public ArrayList<ValueTypePair> seq;
	
	/**
	 * 构造器
	 * @param c 路径约束
	 * @param seq 该约束下各表达式的值
	 */
	public VTPSequence(ConstraintInfo c, ArrayList<ValueTypePair> seq){
		this.pc=c;
		this.seq=seq;
	}

}
