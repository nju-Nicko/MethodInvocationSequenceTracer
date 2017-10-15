package vo.info_obj;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import util.Function;
import vo.common_obj.ArrFieldInfo;
import vo.common_obj.ArrayVarInfo;
import vo.common_obj.FieldInfo;
import vo.common_obj.FieldName2;
import vo.common_obj.PrimitiveFieldInfo;
import vo.common_obj.PrimitiveVarInfo;
import vo.common_obj.ReferenceFieldInfo;
import vo.common_obj.ReferenceVarInfo;
import vo.common_obj.VariableInfo;

/**
 * 最终对程序符号执行所得的所有信息
 * @author 倪陆章
 *
 */
public class FinalInfo {
	
	public String className;
	
	public ArrayList<InfoUnit> infs;
	
	public FinalInfo(){
		this.className=null;
		this.infs=new ArrayList<InfoUnit>();
	}
	
	@Override
	public String toString(){
		String result="";
		for(InfoUnit table: infs){
			if(table.cons.info!=null)
				result=result+"路径约束为"+table.cons.toString()+"时：\n";
			if(table.methods.size()!=0){
				result=result+"方法调用序列为：\n";
				for(int i=0; i<= table.methods.size()-2; i++){
					InvocationInfo m=table.methods.get(i);
					result=result+m.toString()+", ";	
				}
				result=result+table.methods.get(table.methods.size()-1)+"。";
				result=result+"\n";
			}
			result=result+"变量修改信息为：\n";
			
			List<Map.Entry<String, VariableInfo>> e=new ArrayList<Map.Entry<String, VariableInfo>>(table.variables.entrySet());
			Collections.sort(e, new Comparator<Map.Entry<String, VariableInfo>>(){

				@Override
				public int compare(Entry<String, VariableInfo> arg0,
						Entry<String, VariableInfo> arg1) {
					// TODO Auto-generated method stub
					return (arg0.getKey()).toString().compareTo(arg1.getKey());
				}
				
			});
			
			for (Map.Entry<String, VariableInfo> entry: e) {
			        String vname=entry.getKey();
			        VariableInfo vi=entry.getValue();
				    if(vi.isParam){
				        String vvalue=varToString(table, vi);
				    	result=result+vname+"的符号表达式："+vvalue+"\n";
				    }
				}
		}
		return result;
	}
	
	
	/**
	 * 把变量信息转为字符串形式
	 * @return 变量信息的字符串形式
	 */
	public String varToString(InfoUnit table, VariableInfo vi){
		String str="";
		if(Function.isPrimitiveType(vi.type)){
			//基本数据类型，直接返回其符号值
			PrimitiveVarInfo pvi=(PrimitiveVarInfo)vi;
			str=str+(String)pvi.value.peek();
			return str;
		}
		else if(Function.isArrayType(vi.type)){
			//数组类型，递归输出他的每个单元的符号值
			ArrayVarInfo avi=(ArrayVarInfo)vi;
			str=str+"[";
			for(int i=0; i<=table.mm.aa.arr[avi.first_address].length-2; i++){
				VariableInfo unit=table.mm.aa.arr[avi.first_address][i];
				String unitstr=varToString(table, unit);
				str=str+unitstr+", ";
			}
			VariableInfo unitlast=table.mm.aa.arr[avi.first_address][table.mm.aa.arr[avi.first_address].length-1];
			str=str+varToString(table, unitlast)+"]";
			return str;
		}
		else if(Function.isQualifiedType(vi.type)||Function.isSimpleType(vi.type)){
			//引用类型，递归输出他现在指向的对象的每个字段的符号值
			ReferenceVarInfo rvi=(ReferenceVarInfo)vi;
			str=str+"[";
			Iterator<Map.Entry<FieldName2, FieldInfo>> ite=table.mm.jh.objs[rvi.handle].field.entrySet().iterator();
			while(ite.hasNext()){
				Map.Entry<FieldName2, FieldInfo> entry = ite.next();
				if(ite.hasNext())
					str=str+entry.getKey().fieldName+": "+fieldToString(table, entry.getValue())+", ";
				else
					str=str+entry.getKey().fieldName+": "+fieldToString(table, entry.getValue());
			}
			str=str+"]";
			return str;
		}
		return null;
	}
	
	/**
	 * 把字段信息转换为String形式
	 * @param table 符号表
	 * @param fi 字段信息
	 * @return 字段的String形式
	 */
	public String fieldToString(InfoUnit table, FieldInfo fi){
		String str="";
		if(Function.isPrimitiveType(fi.fieldType)){
		//	System.out.println(fi.getClass());
			PrimitiveFieldInfo pfi=(PrimitiveFieldInfo)fi;
			str=str+pfi.value;
			return str;
		}
		else if(Function.isArrayType(fi.fieldType)){
			ArrFieldInfo afi=(ArrFieldInfo)fi;
			str=str+"[";
			for(int i=0; i<=table.mm.aa.arr[afi.f].length-2; i++){
				VariableInfo unit=table.mm.aa.arr[afi.f][i];
				String unitstr=varToString(table, unit);
				str=str+unitstr+", ";
			}
			VariableInfo unitlast=table.mm.aa.arr[afi.f][table.mm.aa.arr[afi.f].length-1];
			str=str+varToString(table, unitlast)+"]";
			return str;
		}
		else if(Function.isQualifiedType(fi.fieldType)||Function.isSimpleType(fi.fieldType)){
			ReferenceFieldInfo rvi=(ReferenceFieldInfo)fi;
			str=str+"[";
			Iterator<Map.Entry<FieldName2, FieldInfo>> ite=table.mm.jh.objs[rvi.handle].field.entrySet().iterator();
			while(ite.hasNext()){
				Map.Entry<FieldName2, FieldInfo> entry = ite.next();
				if(ite.hasNext())
					str=str+entry.getKey().fieldName+": "+fieldToString(table, entry.getValue())+", ";
				else
					str=str+entry.getKey().fieldName+": "+fieldToString(table, entry.getValue());
			}
			str=str+"]";
			return str;
		}
		return null;
	}

}
