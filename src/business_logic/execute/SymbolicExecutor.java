package business_logic.execute;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ImportDeclaration;

import vo.common_obj.MethodInfo;
import vo.info_obj.FinalInfo;

/**
 * 符号执行器类，对指定AST的指定方法进行符号执行。
 * @author 倪陆章
 *
 */
public class SymbolicExecutor {
	
	CompilationUnit root;
	MethodInfo the_method;
	ArrayList<MethodInfo> all;
	String projectRootPath;
	ArrayList<ImportDeclaration> imports;
	String packageInfo;
	
	public SymbolicExecutor(CompilationUnit root, MethodInfo m, ArrayList<MethodInfo> all, String projectRootPath
			, ArrayList<ImportDeclaration> imports, String packageInfo){
		this.root=root;
		the_method=m;
		this.all=all;
		this.projectRootPath=projectRootPath;
		this.imports=imports;
		this.packageInfo=packageInfo;
	}

	public FinalInfo execute() {
		// TODO Auto-generated method stub
		SEVisitor cv=new SEVisitor(the_method, root, all, projectRootPath, imports, packageInfo);
		root.accept(cv);
		return cv.inf;
	}

}
