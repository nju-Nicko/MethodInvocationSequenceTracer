package vo.memory_obj;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import vo.common_obj.FieldInfo;
import vo.common_obj.FieldName;

/**
 * 字段表
 * @author 倪陆章
 *
 */
public class FieldTable {
	
	public HashMap<FieldName, FieldInfo> fields;
	
	/**
	 * 构造器
	 */
	public FieldTable(){
		fields=new HashMap<FieldName, FieldInfo>();
	}
	
	/**
	 * 向字段表中加入一项
	 * @param fi
	 */
	public void addOneField(FieldInfo fi){
		fields.put(fi.getFieldName(), fi);
	}
	
	@Override
	public FieldTable clone(){
		FieldTable ft=new FieldTable();
		Iterator<Map.Entry<FieldName, FieldInfo>> ite=fields.entrySet().iterator();
		while(ite.hasNext()){
			 Map.Entry<FieldName, FieldInfo> entry=ite.next();
			 ft.addOneField(entry.getValue().clone());
		 }
		 return ft;
	}

}
