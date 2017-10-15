package business_logic.tested_class_info;

import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * 被测类信息获取工具
 * @author 倪陆章
 *
 */
public class TestedClassInfoGetter {
	
	public TestedClassInfoVisitor getMethods(CompilationUnit root){
		TestedClassInfoVisitor mv=new TestedClassInfoVisitor();
		root.accept(mv);
		return mv;
	}

}
