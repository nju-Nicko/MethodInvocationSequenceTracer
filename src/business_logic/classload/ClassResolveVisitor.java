package business_logic.classload;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

import util.Function;
import vo.common_obj.ArrFieldInfo;
import vo.common_obj.FieldInfo;
import vo.common_obj.MethodInfo;
import vo.common_obj.PrimitiveFieldInfo;
import vo.common_obj.ReferenceFieldInfo;
import vo.memory_obj.ClassInfo;

/**
 * 类解析工具，收集类信息ClassInfo
 * 
 * @author 倪陆章
 * 
 */
public class ClassResolveVisitor extends ASTVisitor {
	private String projectRootPath;
	private ClassInfo cl;
	private String packageinfo;
	private ArrayList<ImportDeclaration> imports;

	public ClassResolveVisitor(String prp) {
		projectRootPath = prp;
		cl = new ClassInfo();
		imports = new ArrayList<ImportDeclaration>();
	}

	@Override
	public boolean visit(PackageDeclaration node) {
		Name name = node.getName();
		packageinfo = name.toString();
		return false;
	}

	@Override
	public boolean visit(ImportDeclaration node) {
		imports.add(node);
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean visit(TypeDeclaration node) {
		// 获取这个类的全限定名
		SimpleName classname = node.getName();
		cl.qualifiedName = Function.toFullQualifiedName(classname.toString(),
				projectRootPath, packageinfo, imports);
		String the_full_name = cl.qualifiedName;
		// 获取这个类的父类信息，即父类的全限定名
		Type fatherType = node.getSuperclassType();
		cl.father = new ClassInfo(Function.toFullQualifiedName(fatherType,
				projectRootPath, packageinfo, imports));
		// 获取该类实现的接口的信息
		List<Type> il = node.superInterfaceTypes();
		for (Type t : il) {
			cl.interfaces.add(new ClassInfo(Function.toFullQualifiedName(t,
					projectRootPath, packageinfo, imports)));
		}
		// 获取字段表
		FieldDeclaration[] fields = node.getFields();
		for (FieldDeclaration fd : fields) {
			Type type = fd.getType();
			int modifier = fd.getModifiers();
			List<VariableDeclarationFragment> frag = fd.fragments();
			for (VariableDeclarationFragment vdf : frag) {
				SimpleName field_name = vdf.getName();
				FieldInfo field_info = new FieldInfo(modifier, the_full_name,
						field_name.toString(), Function.toFullQualifiedName(
								type, projectRootPath, packageinfo, imports));
				if (vdf.getInitializer() == null) {
					// 置默认初值
					if (type.isPrimitiveType()) {
						if (type.toString().equals("boolean")) {
							PrimitiveFieldInfo pfi = new PrimitiveFieldInfo(
									field_info, "false");
							field_info = pfi;
						} else {
							PrimitiveFieldInfo pfi = new PrimitiveFieldInfo(
									field_info, "0");
							field_info = pfi;
						}
					} else if (type.isQualifiedType() || type.isSimpleType()) {
						ReferenceFieldInfo rfi = new ReferenceFieldInfo(
								field_info, -1);
						field_info = rfi;
					} else if (type.isArrayType()) {
						ArrFieldInfo afi = new ArrFieldInfo(field_info, -1);
						field_info = afi;
					}
				} else {
					Expression ini = vdf.getInitializer();
					if (ini instanceof ArrayInitializer) {
					} else if (ini instanceof BooleanLiteral) {
						BooleanLiteral bl = (BooleanLiteral) ini;
						PrimitiveFieldInfo pfi = new PrimitiveFieldInfo(
								field_info, bl.toString());
						field_info = pfi;
					} else if (ini instanceof CharacterLiteral) {
						CharacterLiteral cl = (CharacterLiteral) ini;
						PrimitiveFieldInfo pfi = new PrimitiveFieldInfo(
								field_info, cl.toString());
						field_info = pfi;
					} else if (ini instanceof NullLiteral) {
						if (type.isQualifiedType() || type.isSimpleType()) {
							ReferenceFieldInfo rfi = new ReferenceFieldInfo(
									field_info, -1);
							field_info = rfi;
						} else if (type.isArrayType()) {
							ArrFieldInfo afi = new ArrFieldInfo(field_info, -1);
							field_info = afi;
						}
					} else if (ini instanceof NumberLiteral) {
						NumberLiteral nl = (NumberLiteral) ini;
						PrimitiveFieldInfo pfi = new PrimitiveFieldInfo(
								field_info, nl.toString());
						field_info = pfi;
					} else {
						// ...
					}
				}
				cl.ft.addOneField(field_info);
			}
		}
		// 获取方法表
		MethodDeclaration[] methods = node.getMethods();
		for (MethodDeclaration md : methods) {
			String mname = md.getName().getIdentifier();
			Type rt = md.getReturnType2();
			String returnTypeInStr = Function.toFullQualifiedName(rt,
					projectRootPath, packageinfo, imports);
			List<SingleVariableDeclaration> ps = md.parameters();
			ArrayList<String> psInStr = new ArrayList<String>();
			for (SingleVariableDeclaration svd : ps) {
				Type t = svd.getType();
				psInStr.add(Function.toFullQualifiedName(t, projectRootPath,
						packageinfo, imports));
			}
			MethodInfo mi = new MethodInfo(the_full_name, md.getModifiers(),
					mname, psInStr, returnTypeInStr);
			cl.mt.addOneMethod(mi);
		}
		return false;
	}

	public ClassInfo getCl() {
		return cl;
	}

}
