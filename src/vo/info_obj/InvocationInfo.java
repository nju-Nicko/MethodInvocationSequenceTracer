package vo.info_obj;

import vo.common_obj.MethodInfo;

/**
 * 保存方法与方法之间的调用与返回信息
 * @author 倪陆章
 *
 */
public class InvocationInfo {
	
	public MethodInfo m1;
	public MethodInfo m2;
	public int relationship; //表示方法之间的调用关系，用0表示m1调用m2，用1表示从m1返回到m2
	
	/**
	 * 构造器
	 * @param m1 方法1
	 * @param m2 方法2
	 * @param rel 方法关系
	 */
	public InvocationInfo(MethodInfo m1, MethodInfo m2, int rel){
		this.m1=m1;
		this.m2=m2;
		relationship=rel;
	}
	
	public String toString(){
		String str1=m1.fullQualifiedName_class+"."+m1.toString();
		String str2=m2.fullQualifiedName_class+"."+m2.toString();
		String str3=(relationship==0?"调用了":"返回到");
		return str1+str3+str2;
	}

	@Override
	public InvocationInfo clone(){
		return new InvocationInfo(m1.clone(), m2.clone(), relationship);
	}
}
