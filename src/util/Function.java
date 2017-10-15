package util;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Type;

/**
 * 一些函数
 * @author 倪陆章
 *
 */
public class Function {
	
	/**
	 * 判断一个String是不是表示数组类型
	 * @param t 字符串
	 * @return true 是; false 不是
	 */
	public static boolean isArrayType(String t){
		if(t.contains("["))
			return true;
		return false;
	}
	
	/**
	 * 判断一个String是不是基本数据类型
	 * @param t 字符串
	 * @return true 是; false 不是
	 */
	public static boolean isPrimitiveType(String t){
		if(t.equals("int")||t.equals("byte")||t.equals("char")||t.equals("short")||t.equals("long")||
				t.equals("float")||t.equals("double")||t.equals("void")||t.equals("boolean"))
			return true;
		return false;
	}
	
	/**
	 * 判断一个字符串是不是Eclipse AST插件中的SimpleType
	 * @param t 字符串
	 * @return true 是; false 不是
	 */
	public static boolean isSimpleType(String t){
		if(!isPrimitiveType(t)&&!isArrayType(t))
			return true;
		return false;
	}
	
	/**
	 * 判断一个String是不是Eclipse AST插件中的QualifiedType
	 * @param t 字符串
	 * @return true 是; false 不是
	 */
	public static boolean isQualifiedType(String t){
		if(t.contains(".")&&!isArrayType(t))
			return true;
		return false;
	}
	

	/**
	 * 根据类型找到类源代码路径<br/>
	 * 本版本暂不处理静态导入
	 * @param type 类型，可能是简单类型也可能是全限定类型
	 * @param projectRootPath 项目根路径
	 * @param packageInfo 引用该类的类所在的包
	 * @param imports 引用该类的类的导入信息
	 * @return 类路径
	 */
	public static String resolveFilePath(String type, String projectRootPath, String packageInfo,
			ArrayList<ImportDeclaration> imports){
		String typeInStr=type;
		String path=null;
		if(typeInStr.contains(".")){  //限定类型
			typeInStr=typeInStr.replace('.', '\\');
			path=projectRootPath+typeInStr+".java";
		}
		else{  //简单类型，查找imports
			for(int i=0; i<=imports.size()-1; i++){
				ImportDeclaration imd=imports.get(i);
				if(imd.isStatic()){
					//抛出异常
				}
				else if(imd.isOnDemand()){
					//查找该目录下有没有符合条件的类
					String fullname=imd.getName().toString();
					fullname=fullname.replace('.', '\\');
					String directory=projectRootPath+fullname+"\\";  //要查找的目录
					File file=new File(directory);
					File[] tmpList=file.listFiles();
					for(File afile: tmpList){
						if(afile.isFile()){
							if(afile.getName().equals(typeInStr+".java")){
								path=directory+typeInStr+".java";
								break;
							}
						}
						else{
							//目录，不用继续搜索
						}
					}
				}
				else{
					String fullname=imd.getName().toString();
					int index=fullname.lastIndexOf('.');
					String cname=fullname.substring(index+1, fullname.length());
					if(cname.equals(typeInStr)){
						fullname=fullname.replace('.', '\\');
						path=projectRootPath+fullname+".java";
						break;
					}
				}
			}
			if(path==null){  //不是全限定名且没有通过import定位到，说明在同一个包中
				if(packageInfo!=null)
					path=projectRootPath+packageInfo+"\\"+typeInStr+".java";
				else
					path=projectRootPath+typeInStr+".java";
			}
		}
		return path;
	}
	
	/**
	 * 把类型转为全限定类型
	 * @param t 类型
	 * @param projectRootPath 项目根路径
	 * @param packageInfo 引用该类的类所在的包
	 * @param imports 引用该类的类的导入信息
	 * @return 全限定类型
	 */
	public static String toFullQualifiedName(Type t, String projectRootPath, String packageInfo,
			ArrayList<ImportDeclaration> imports){
		if(t==null)
			return null;
		String typeInStr=t.toString();
		return toFullQualifiedName(typeInStr, projectRootPath, packageInfo, imports);
	}
	
	/**
	 * 把类型转为全限定类型
	 * @param t 类型
	 * @param projectRootPath 项目根路径
	 * @param packageInfo 引用该类的类所在的包
	 * @param imports 引用该类的类的导入信息
	 * @return 全限定类型
	 */
	public static String toFullQualifiedName(String t, String projectRootPath, String packageInfo,
			ArrayList<ImportDeclaration> imports){
		if(t==null)
			return null;
		if(Function.isPrimitiveType(t)) return t;
		if(t.contains("."))  return t;   
		else{  //简单类型，查找imports
			for(int i=0; i<=imports.size()-1; i++){
				ImportDeclaration imd=imports.get(i);
				if(imd.isStatic()){}
				else if(imd.isOnDemand()){
					//查找该目录下有没有符合条件的类
					String fullname=imd.getName().toString();
					String fullname_p=fullname.replace('.', '\\');
					String directory=projectRootPath+fullname_p+"\\";  //要查找的目录
					File file=new File(directory);
					File[] tmpList=file.listFiles();
					for(File afile: tmpList){
						if(afile.isFile()){
							if(afile.getName().equals(t+".java")){
								return fullname+"."+t;
							}
						}
						else{
							//目录，不用继续搜索
						}
					}
				}
				else{
					String fullname=imd.getName().toString();
					int index=fullname.lastIndexOf('.');
					String cname=fullname.substring(index+1, fullname.length());
					if(cname.equals(t)){
						return fullname;
					}
				}
			}
			//不是全限定名且没有通过import定位到，说明在同一个包中
			if(packageInfo!=null)
				return packageInfo+"."+t;
			else
				return t;
		}	
	}

}
