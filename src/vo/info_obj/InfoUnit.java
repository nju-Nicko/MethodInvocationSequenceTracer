package vo.info_obj;

import java.util.ArrayList;
import java.util.HashMap;

import vo.common_obj.VariableInfo;

import business_logic.memory.SetManager;

/**
 * 信息单元对象，记录某PC下的各种信息
 * @author 倪陆章
 */
public class InfoUnit {
	public ConstraintInfo cons;
	public HashMap<String, VariableInfo> variables;
	public ArrayList<InvocationInfo> methods;  
	public ValueTypePair retval;  //方法在该PC下的返回值，未到达return语句时始终为null。即不为null时表示该路径已执行结束
    public SetManager mm; //该PC下的内存管理对象
	
	public InfoUnit(){
		variables=new HashMap<String, VariableInfo>();
		cons=new ConstraintInfo();
		methods=new ArrayList<InvocationInfo>();
		retval=null;
		mm=new SetManager();
	}
	
	/**
	 * 给符号表增加新变量
	 * @param vi 要新增到符号表中的变量信息
	 */
	public void addOneVariable(VariableInfo vi){
		variables.put(vi.name, vi);
	}
	
	/**
	 * 获取变量信息
	 * @param vname 变量名
	 * @return 变量的信息
	 */
	public VariableInfo getVariableInfo(String vname){
		return variables.get(vname);
	}
	
	/**
	 * 删除变量
	 * @param vname 变量名
	 */
	public void removeOneVariable(String vname){
		variables.remove(vname);
	}
	
}
