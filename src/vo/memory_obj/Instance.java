package vo.memory_obj;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import vo.common_obj.FieldInfo;
import vo.common_obj.FieldName;
import vo.common_obj.FieldName2;

/**
 * 对象实例
 * @author 倪陆章
 *
 */
public class Instance {
	public String type; //对象头中实例所属的类型的全限定名
	public HashMap<FieldName2, FieldInfo> field;    //对象的实例数据，K为字段名，V为字段信息
	
	/**
	 * 构造器
	 * @param t 实例所属的类的全限定名
	 */
	public Instance(String t){
		this.type=t;
		field=new HashMap<FieldName2, FieldInfo>();
	}
	
	/**
	 * 增加一个字段信息
	 * @param fi 字段信息
	 */
	public void addOneField(FieldInfo fi){
		field.put(fi.getFieldName2(), fi);
	}
	
	/**
	 * 获取一个字段的信息
	 * @param simpleName 要获取的字段的FieldName
	 * @return 字段信息
	 */
	public FieldInfo getOneField(FieldName simpleName){
		return field.get(new FieldName2(type, simpleName.fieldName));
		
	}
	
	@Override
	public Instance clone(){
		Instance ins=new Instance(type);
		 Iterator<Map.Entry<FieldName2, FieldInfo>> ite=field.entrySet().iterator();
		 while(ite.hasNext()){
			 Map.Entry<FieldName2, FieldInfo> entry=ite.next();
			 ins.addOneField(entry.getValue().clone());
		 }
		 return ins;
	}
}
