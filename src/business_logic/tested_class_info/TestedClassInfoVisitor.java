package business_logic.tested_class_info;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import vo.common_obj.MethodInfo;

/**
 * 获取被测类的相应信息，用于界面展示等。
 * @author 倪陆章
 *
 */
public class TestedClassInfoVisitor extends ASTVisitor {
	  private String packageAndName;  //包名连接“\”符再连接类文件名，带.java后缀
	  private String packageInfo; //包路径
	  private ArrayList<ImportDeclaration> imports=new ArrayList<ImportDeclaration>(); //导入信息
      private ArrayList<MethodInfo> methods=new ArrayList<MethodInfo>();  //类方法信息
      
      @Override
      public boolean visit(MethodDeclaration node){ 
    	  SimpleName name=node.getName();
    	  String iden=name.getIdentifier();
    	  
    	 @SuppressWarnings("unchecked")
	   	  List<SingleVariableDeclaration> params=node.parameters();
    	  ArrayList<String> paramtypes=new ArrayList<String>();
    	  for(SingleVariableDeclaration svd: params){
    		 Type t=svd.getType(); 
    		 paramtypes.add(t.toString());
    	  }   
    	 int modifiers=node.getModifiers();
    	 String tmp1=packageAndName.substring(0, packageAndName.length()-5);
		 String tmp2=tmp1.replace("\\", ".");
    	 MethodInfo mi=new MethodInfo(tmp2, modifiers, iden, paramtypes);
         methods.add(mi);  
    	 return false;
      }
      
      @Override
      public boolean visit(PackageDeclaration node){
    	  Name name=node.getName();
    	  String fullQualifiedName=name.getFullyQualifiedName();
    	  fullQualifiedName=fullQualifiedName.replace('.', '\\');
    	  packageAndName=fullQualifiedName;
    	  packageInfo=fullQualifiedName;
    	  return false;
      }
      
      @Override
      public boolean visit(TypeDeclaration node){
    	  SimpleName sn=node.getName();
    	  String className=sn.getIdentifier();
    	  if(packageAndName==null)   //default包
    		   packageAndName=className+".java";
    	  else{
    		  packageAndName=packageAndName+"\\"+className+".java";
    	  }
    	  return true;
      }
      
      @Override
      public boolean visit(ImportDeclaration node){
    	  imports.add(node);
    	  return false;
      }
      
      public String getPackageAndName(){
    	  return packageAndName;
      }
      
      public String getPackageInfo(){
    	  return packageInfo;
      }
      
      public ArrayList<ImportDeclaration> getImports(){
    	  return imports;
      }
      
      public ArrayList<MethodInfo> getMethods(){
    	  return methods;
      }
}
