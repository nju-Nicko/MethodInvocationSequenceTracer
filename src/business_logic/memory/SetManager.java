package business_logic.memory;

/**
 * 内存管理工具<br/>
 * 在这个工具中把内存分为方法区、堆区，缺少了JVM规范中的栈区和程序计数器，<br/>
 * 栈区信息用VariableTable对象存储，
 * 因为是遍历语法树，所以不用程序计数器。
 * @author 倪陆章
 *
 */
public class SetManager {
	public InstanceSet jh;
	public ClassSet ma;
	public ArrArea aa;
	
	public SetManager(){
		partition();
	}

	/**
	 * 划分内存
	 */
	public void partition(){
		jh=new InstanceSet();
		ma=new ClassSet();
		aa=new ArrArea();
	}
	
	@Override
	public SetManager clone(){
		SetManager mm=new SetManager();
		mm.jh=this.jh.clone();
		mm.ma=this.ma.clone();
		mm.aa=this.aa.clone();
		return mm;
	}

}
