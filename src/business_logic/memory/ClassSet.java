package business_logic.memory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import vo.memory_obj.ClassInfo;

/**
 * 内存方法区，存放已被加载的类的类信息、即时编译后的代码、常量、静态变量<br/>
 * 这里不用存储代码、常量和静态变量
 * @author 倪陆章
 *
 */
public class ClassSet {
	
	public HashMap<String, ClassInfo> cs;   //类的全限定名、该类的类信息
	
	public ClassSet(){
		cs=new HashMap<String, ClassInfo>();
	}
	
	/**
	 * 添加一个类信息对象到方法区
	 * @param c
	 */
	public void addOneClass(ClassInfo c){
		cs.put(c.qualifiedName, c);
	}
	
	/**
	 * 判断类是否已经被加载
	 * @param c 类
	 * @return true 已加载; false 未加载
	 */
	public boolean hasLoaded(ClassInfo c){
		String cname=c.qualifiedName;
		if(cs.get(cname)!=null)
			return true;
		return false;
	}
	
	/**
	 * 根据类的全限定名获取类信息
	 * @param fullQualifiedName 类的全限定名
	 * @return 类信息
	 */
	public ClassInfo getClassInfo(String fullQualifiedName){
		return cs.get(fullQualifiedName);
	}
	
	/**
	 * 复制方法区
	 */
	@Override
	public ClassSet clone(){
		ClassSet ma=new ClassSet();
		 Iterator<Map.Entry<String, ClassInfo>> ite=cs.entrySet().iterator();
		 while(ite.hasNext()){
			 Map.Entry<String, ClassInfo> entry=ite.next();
			 ma.addOneClass(entry.getValue().clone());
		 }
		return ma;
	}

}
