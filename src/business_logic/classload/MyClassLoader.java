package business_logic.classload;

import org.eclipse.jdt.core.dom.CompilationUnit;

import business_logic.ast.ASTCreator;
import business_logic.memory.SetManager;
import vo.memory_obj.ClassInfo;

/**
 * 类加载器，负责加载类<br/>
 * JVM规范要求类加载包括：加载、验证、准备、解析、初始化等阶段，<br/>
 * 这里不需要验证阶段、解析阶段。
 * @author 倪陆章
 *
 */
public class MyClassLoader {
	
	public SetManager mm;
	
	public MyClassLoader(SetManager mm){
		this.mm=mm;
	}
	
	/**
	 * 加载类<br/>
	 * 执行JVM规范中的加载、准备和初始化阶段
	 * @param path 源文件路径
	 * @return 类信息
	 */
	public ClassInfo loadClass(String path, String projectRootPath){
		 ASTCreator ac=new ASTCreator();
		 CompilationUnit root=ac.createAST(path);
		 ClassResolveVisitor crv=new ClassResolveVisitor(projectRootPath);
		 root.accept(crv);
		 
		 ClassInfo cl=crv.getCl();
		 if(mm.ma.hasLoaded(cl)){
			 //do nothing
		 }
		 else{
			 mm.ma.addOneClass(cl);
		 }
		 return cl;
	}

}
