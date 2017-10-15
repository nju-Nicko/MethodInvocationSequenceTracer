package vo.common_obj;

import java.util.ArrayList;

/**
 * 方法所在类的全限定名和方法简单名、各个参数的全限定类型<br/>
 * 它唯一的描述了一个方法
 * @author 倪陆章
 *
 */
public class MethodName {
	public String fullQualifiedName;
	public String simpleName;
	public ArrayList<String> paramTypes;
	
	/**
	 * 构造器
	 */
	public MethodName(){
		fullQualifiedName=null;
		simpleName=null;
		paramTypes=new ArrayList<String>();
	}
	
	/**
	 * 构造器
	 * @param f 方法所在类的全限定名
	 * @param s 方法的简单名称
	 * @param pts 方法的参数的全限定类型
	 */
	public MethodName(String f, String s, ArrayList<String> pts){
		this.fullQualifiedName=f;
		this.simpleName=s;
		this.paramTypes=pts;
	}
}
