package vo.memory_obj;

import java.util.ArrayList;

/**
 * 已被加载的类的内存class对象，按照JVM规范中class文件格式存储相关信息
 * @author 倪陆章
 *
 */
public class ClassInfo {
	
	public int access_flags;
	public String qualifiedName; //全限定名，this类
	public ClassInfo father; //父类
	public ArrayList<ClassInfo> interfaces; //实现的接口
	public FieldTable ft;
	public MethodTable mt;
	
	/**
	 * 构造器
	 */
    public ClassInfo(){
    	access_flags=0x0000;
    	qualifiedName=null;
    	father=null;
    	interfaces=new ArrayList<ClassInfo>();
    	ft=new FieldTable();
    	mt=new MethodTable();
    }
    
    /**
     * 构造器
     * @param qn 类的全限定名
     */
    public ClassInfo(String qn){
    	access_flags=0x0000;
    	qualifiedName=qn;
    	father=null;
    	interfaces=new ArrayList<ClassInfo>();
    	ft=new FieldTable();
    	mt=new MethodTable();
    }
    
    /**
     * 构造器
     * @param ac 访问标识
     * @param qn 类的全限定名
     * @param f 父类信息
     * @param is 接口信息
     * @param ft 字段表
     * @param mt 方法表
     */
    public ClassInfo(int ac, String qn, ClassInfo f, ArrayList<ClassInfo> is, FieldTable ft,
    		MethodTable mt){
    	this.access_flags=ac;
    	this.qualifiedName=qn;
    	this.father=f;
    	this.interfaces=is;
    	this.ft=ft;
    	this.mt=mt;
    }
    
    @Override
    public ClassInfo clone(){
    	ArrayList<ClassInfo> is=new ArrayList<ClassInfo>();
    	for(int i=0; i<=interfaces.size()-1; i++)
    		is.add(interfaces.get(i).clone());
    	ClassInfo cl=new ClassInfo(access_flags, qualifiedName, father==null?null:father.clone(), is, ft.clone(), mt.clone());
    	return cl;
    }

}
