package vo.common_obj;

import java.util.ArrayList;

/**
 * 方法信息，参照class文件方法表格式进行存储
 * @author 倪陆章
 *
 */
public class MethodInfo {
	
	public String fullQualifiedName_class;
	public int access_flags;  
	public String method_name;  
	public ArrayList<String> param_types;
	public String fullQualifiedName_return;
	
	/**
	 * 构造器
	 * @param method_name 方法名
	 */ 
	public MethodInfo(String fullQualifiedName_class, String method_name){
		this.fullQualifiedName_class=fullQualifiedName_class;
		access_flags=0x0000;
		this.method_name=method_name;
		param_types=new ArrayList<String>();
		fullQualifiedName_return=null;
	}
	
	/**
	 * 构造器
	 * @param method_name 方法名
	 * @param list 方法参数类型列表
	 */
	public MethodInfo(String fullQualifiedName_class, String method_name, ArrayList<String> list){
		this.fullQualifiedName_class=fullQualifiedName_class;
		access_flags=0x0000;
		this.method_name=method_name;
		param_types=list;
		fullQualifiedName_return=null;
	}
	
	/**
	 * 构造器
	 * @param access_flags 访问标识
	 * @param method_name 方法名
	 * @param list 参数类型列表
	 */
	public MethodInfo(String fullQualifiedName_class, int access_flags, String method_name, ArrayList<String> list){
		this.fullQualifiedName_class=fullQualifiedName_class;	
		this.access_flags=access_flags;
		this.method_name=method_name;
		this.param_types=list;
	}

	/**
	 * 构造器
	 * @param fullQulifiedName_class 方法所在类的全限定名
	 * @param access_flags 访问标识
	 * @param method_name 方法名
	 * @param list 方法参数类型链表
	 * @param fullQualifiedName_return 方法返回类型的全限定名
	 */
	public MethodInfo(String fullQualifiedName_class,int access_flags, String method_name, 
			ArrayList<String> list, String fullQualifiedName_return){
		this.fullQualifiedName_class=fullQualifiedName_class;
		this.access_flags=access_flags;
		this.method_name=method_name;
		param_types=list;
		this.fullQualifiedName_return=fullQualifiedName_return;
	}
	
	/**
	 * 判断两个方法是否对应相同的方法
	 * @param mi 另一个方法
	 * @return true 相同; false 不同
	 */
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof MethodInfo))
			return false;
		MethodInfo mi=(MethodInfo)obj;
		if(!fullQualifiedName_class.equals(mi.fullQualifiedName_class))
			return false;
		boolean nameSame=method_name.equals(mi.method_name);
		boolean param_types_len_same=param_types.size()==mi.param_types.size();
		boolean paramNotSame=false;
		if(param_types_len_same){
			for(int i=0; i<=param_types.size()-1; i++){
				String t1=param_types.get(i);
				String t2=mi.param_types.get(i);
				if(!t1.equals(t2)){
					paramNotSame=true;
					break;
				}
			}
		}
		else{
			paramNotSame=true;
		}
		return nameSame&&(!paramNotSame);
	}
	
	@Override
	public String toString(){
		String result=method_name+"(";
		for(int i=0; i<=param_types.size()-2; i++)
			result=result+param_types.get(i)+", ";
		if(param_types.size()>=1)
			result=result+param_types.get(param_types.size()-1)+")";
		else
			result=result+")";
		return result;
	}
	
	@Override
	public MethodInfo clone(){
		ArrayList<String> ns=new ArrayList<String>();
		for(String s: param_types)
			ns.add(s);
		return new MethodInfo(fullQualifiedName_class, access_flags, method_name, ns, fullQualifiedName_return);
	}
	
	/**
	 * 获取方法的MehodName
	 * @return 该方法的MethodName
	 */
	public MethodName getMethodName(){
		return new MethodName(fullQualifiedName_class, method_name, param_types);
	}

}
