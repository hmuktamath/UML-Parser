package uml_parser.uml_parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.symbolsolver.model.declarations.ClassDeclaration;
import com.github.javaparser.symbolsolver.model.declarations.FieldDeclaration;
import com.github.javaparser.symbolsolver.model.declarations.TypeParameterDeclaration;
import com.github.javaparser.symbolsolver.model.methods.MethodUsage;

public class Main2 {

	String className = null;
	String variableName = null;
	String variableType = null;

	public Map<String, List<List>> getParsedData(String filePath) throws FileNotFoundException {
		File file2 = new File(filePath);
		CompilationUnit compilationUnit = JavaParser.parse(file2);
//		compilationUnit.getNodesByType(ClassOrInterfaceDeclaration.class).stream()
//				.forEach(f -> className = f.getNameAsString());
		// List<ClassOrInterfaceDeclaration> classdeclarations =
		// (List<ClassOrInterfaceDeclaration>) compilationUnit
		// .getNodesByType(ClassOrInterfaceDeclaration.class);

		List<ClassDeclaration> classdeclarations = (List<ClassDeclaration>) compilationUnit
				.getNodesByType((Class) ClassDeclaration.class);
		Map<String, List<List>> parsedData = new HashMap<String, List<List>>();
		// List<MethodDeclaration> methodList= new ArrayList<>();
		// List<VariableElement> variableList = new ArrayList<>();

		for (ClassDeclaration a : classdeclarations) {
			System.out.println("####################");
			// System.out.println("Class Name :: " + a.getNameAsString());

//			String parent = a.getName().getParentNode().get().getParentNode().toString();
//			System.out.println(a.getNameAsString() + "'s parent is " + parent);

			List<UMLMethod> methodList = new ArrayList<>();
			List<UMLVariable> variableList = new ArrayList<>();
			className = a.getName();
			List classContents = new ArrayList<>();

			// methodList.addAll(a.getMethods());

			for (FieldDeclaration vd : a.getAllFields()) {
				UMLVariable umlVar;
				umlVar = new UMLVariable();
				umlVar.varName = vd.getName();
				umlVar.varType = vd.getType().toString();
				// umlVar.accessSpecifier = vd.get;
				variableList.add(umlVar);
				System.out.println("#### " + variableList);

			}

			// for(FieldDeclaration vd :
			// a.getNodesByType(FieldDeclaration.class))
			// {
			// UMLVariable umlVar;
			// umlVar = new UMLVariable();
			// umlVar.varName = vd.getNameAsString();
			// umlVar.varType = vd.getType().toString();
			// umlVar.accessSpecifier = vd.get;
			// variableList.add(umlVar);
			// System.out.println("#### " +variableList);
			//
			// }

			for (MethodUsage md : a.getAllMethods())

			{
				UMLMethod umlMeth;
				umlMeth = new UMLMethod();
				umlMeth.name = md.getName();
				umlMeth.returnType = md.getDeclaration().getReturnType().toString();
				umlMeth.accessSpecifier = md.getDeclaration().accessLevel().toString();
				Map<String, String> arguments = new HashMap<String, String>();
				for (TypeParameterDeclaration parameter : md.getDeclaration().getTypeParameters()) {

					arguments.put(parameter.asField().getName(), parameter.asField().getType().toString());
				}
			//	umlMeth.arguments = arguments;
				methodList.add(umlMeth);
				// umlMeth.arguments = (String[]) parameterArray.toArray();

				// umlMeth.methAccessSpecifiers = md.getModifiers().;

			}
			// variableList.addAll(a.getNodesByType(VariableDeclarator.class));

			classContents.add(0, variableList);
			classContents.add(1, methodList);
			String parentClass = a.toString();
			// System.out.println("PArent class" + parentClass);

			// parsedData.put(a.getNameAsString(), classContents);
			// System.out.println("Complete MAp" + parsedData);

		}

		return parsedData;
	}
}
