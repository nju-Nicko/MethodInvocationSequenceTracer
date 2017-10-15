package business_logic.execute;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;

import business_logic.memory.SetManager;

import util.Function;
import vo.common_obj.ArrayVarInfo;
import vo.common_obj.MethodInfo;
import vo.common_obj.PrimitiveVarInfo;
import vo.common_obj.ReferenceVarInfo;
import vo.common_obj.VariableInfo;
import vo.info_obj.ArrayValueTypePair;
import vo.info_obj.PrimitiveValueTypePair;
import vo.info_obj.ReferenceValueTypePair;
import vo.info_obj.ValueTypePair;
import vo.info_obj.InfoUnit;

/**
 * 负责符号执行一个方法调用
 * @author 倪陆章
 *
 */
public class MethodInvocationVisitor extends SEVisitor {

	public ArrayList<ValueTypePair> args; //实参的值-类型对，非静态方法会有一个this指针
	public SetManager mm; //内存区信息
	
	public MethodInvocationVisitor(MethodInfo mi, CompilationUnit root, ArrayList<MethodInfo> all, String projectRootPath
			, ArrayList<ImportDeclaration> imports, String packageInfo) {
		super(mi, root, all, projectRootPath, imports, packageInfo);
		// TODO Auto-generated constructor stub
		args=new ArrayList<ValueTypePair>();
		mm=new SetManager();
	}
	
	public MethodInvocationVisitor(MethodInfo mi, CompilationUnit root, ArrayList<ValueTypePair> args, ArrayList<MethodInfo> all, 
			String projectRootPath, ArrayList<ImportDeclaration> imports, String packageInfo, SetManager mm) {
		super(mi, root, all, projectRootPath, imports, packageInfo);
		// TODO Auto-generated constructor stub
		this.args=args;
		this.mm=mm;
	}
	
	@Override
	public boolean visit(MethodDeclaration node){
		//首先判断是否是被调用的方法。meet为true表示是，为false表示不是。
		boolean meet=false;
		
		SimpleName name=node.getName();
  	    String iden=name.getIdentifier();
  	    @SuppressWarnings("unchecked")
    	List<SingleVariableDeclaration> params=node.parameters();
	    ArrayList<String> paramtypes=new ArrayList<String>();
	    ArrayList<String> paramNames=new ArrayList<String>();
	    ArrayList<VariableInfo> paramInfs=new ArrayList<VariableInfo>();
	    for(int i=0; i<=params.size()-1; i++){
	    	SingleVariableDeclaration svd=params.get(i);
	    	Type t=svd.getType();
	    	paramtypes.add(t.toString());
	    	paramNames.add(svd.getName().getIdentifier());
	    }	    
	    MethodInfo cmi=new MethodInfo(inf.className, iden, paramtypes);
		if(mi.equals(cmi))
	 		  meet=true;	  
	    if(meet){
	    	if(args.size()==mi.param_types.size()){  //静态方法，没有this参数
	    		for(int i=0; i<=args.size()-1; i++){
	    			ValueTypePair vi=args.get(i);
	    			if(Function.isArrayType(vi.type)){
	    				paramInfs.add(new ArrayVarInfo(paramNames.get(i), true, vi.type, 
	    						((ArrayValueTypePair)(args.get(i))).first_address));
				  }
	    			else if(Function.isPrimitiveType(vi.type)){
	    				paramInfs.add(new PrimitiveVarInfo(paramNames.get(i), true, vi.type, 
	    						((PrimitiveValueTypePair)(args.get(i))).value));
	    			}
	    			else if(Function.isSimpleType(vi.type)||Function.isQualifiedType(vi.type)){
	    				paramInfs.add(new  ReferenceVarInfo(paramNames.get(i), true, vi.type, 
	    						((ReferenceValueTypePair)(args.get(i))).handle));
	    			}
	    			else{
	    				//抛出异常
	    			}
	    		}
	    	}
	    	else{
	    		//非静态方法，有一个this参数
	    		ReferenceValueTypePair this_pointer=(ReferenceValueTypePair)args.get(0);
	    		paramInfs.add(new ReferenceVarInfo("this", true, this_pointer.type, this_pointer.handle));
	    		for(int i=1; i<=args.size()-1; i++){
	    			ValueTypePair vi=args.get(i);
	    			if(Function.isArrayType(vi.type)){
	    				paramInfs.add(new ArrayVarInfo(paramNames.get(i-1), true, vi.type, 
	    						((ArrayValueTypePair)(args.get(i))).first_address));
				  }
	    			else if(Function.isPrimitiveType(vi.type)){
	    				paramInfs.add(new PrimitiveVarInfo(paramNames.get(i-1), true, vi.type, 
	    						((PrimitiveValueTypePair)(args.get(i))).value));
	    			}
	    			else if(Function.isSimpleType(vi.type)||Function.isQualifiedType(vi.type)){
	    				paramInfs.add(new  ReferenceVarInfo(paramNames.get(i-1), true, vi.type, 
	    						((ReferenceValueTypePair)(args.get(i))).handle));
	    			}
	    			else{
	    				//抛出异常
	    			}
	    		}
	    	}
		  //找到了要调用的方法
		  //创建初始符号表，一开始PC为null，表中只有方法参数，参数值为传递过来的符号值
		  InfoUnit vt=new InfoUnit();
		  vt.mm=mm;
		  for(int i=0; i<=args.size()-1; i++){
			  vt.addOneVariable(paramInfs.get(i));
		  }
		  ArrayList<InfoUnit> infos=new ArrayList<InfoUnit>();
		  infos.add(vt);
		  seBlock(node.getBody(), infos);
		  
		  for(InfoUnit unit: infos){
			  inf.infs.add(unit);
		  }
		  
		  return true;
	  }
	  else{
		  //不是要调用的方法，不访问其子树。
		  return false;
	     }
	}
}

