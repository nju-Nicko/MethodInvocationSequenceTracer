package vo.memory_obj;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import vo.common_obj.MethodInfo;
import vo.common_obj.MethodName;

/**
 * 方法表
 * @author 倪陆章
 *
 */
public class MethodTable {
	
	public HashMap<MethodName, MethodInfo> methods;
	
	public MethodTable(){
		methods=new HashMap<MethodName, MethodInfo>();
	}
	
	/**
	 * 向方法表中插入一项
	 * @param m
	 */
	public void addOneMethod(MethodInfo m){
		methods.put(m.getMethodName(), m);
	}
	
	@Override
	public MethodTable clone(){
		MethodTable mt=new MethodTable();
		 Iterator<Map.Entry<MethodName, MethodInfo>> ite=methods.entrySet().iterator();
		 while(ite.hasNext()){
			 Map.Entry<MethodName, MethodInfo> entry=ite.next();
			 mt.addOneMethod(entry.getValue().clone());
		 }
		 return mt;
	}

}
