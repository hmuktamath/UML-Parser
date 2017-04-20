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
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.TypeParameter;

public class SourceCodeParser {

	String className = null;
	String variableName = null;
	String variableType = null;

	public Map<String, List<List>> getParsedData(File file) throws FileNotFoundException, ClassNotFoundException {
		// File file2 = new File(file);
		CompilationUnit compilationUnit = JavaParser.parse(file);

		List<ClassOrInterfaceDeclaration> classdeclarations = (List<ClassOrInterfaceDeclaration>) compilationUnit
				.getNodesByType(ClassOrInterfaceDeclaration.class);

		Map<String, List<List>> parsedData = new HashMap<String, List<List>>();

		for (ClassOrInterfaceDeclaration a : classdeclarations) {

			List<UMLMethod> methodList = new ArrayList<>();
			List<UMLMethod> constructorList = new ArrayList<>();
			List<UMLVariable> variableList = new ArrayList<>();
			className = a.getNameAsString();

			HashMap<String, Object> metadata = new HashMap<String, Object>();
			metadata.put("isInterface", a.isInterface());

			List classContents = new ArrayList<>();

			NodeList<ClassOrInterfaceType> c = a.getExtendedTypes();

			List<String> parentClassList = null;
			parentClassList = new ArrayList<>();
			for (ClassOrInterfaceType no : c) {
				parentClassList.add(no.getNameAsString());
			}

			NodeList<ClassOrInterfaceType> parentInterfaces = a.getImplementedTypes();

			List<String> parentInterfaceList = null;
			parentInterfaceList = new ArrayList<>();
			for (ClassOrInterfaceType no : parentInterfaces) {
				parentInterfaceList.add(no.getNameAsString());
			}

			for (FieldDeclaration vd : a.getNodesByType(FieldDeclaration.class)) {
				UMLVariable umlVar;
				umlVar = new UMLVariable();
				umlVar.varName = vd.getVariable(0).getName().toString();
				// umlVar.varType = vd.getElementType().toString();
				umlVar.varType = vd.getVariable(0).getType().toString();
				if (vd.isPublic()) {
					umlVar.accessSpecifier = "public";
				} else if (vd.isPrivate()) {
					umlVar.accessSpecifier = "private";
				} else if (vd.isProtected()) {
					umlVar.accessSpecifier = "protected";
				} else {
					umlVar.accessSpecifier = "public";
				}

				variableList.add(umlVar);
				System.out.println("#### " + variableList);

			}

			for (MethodDeclaration md : a.getNodesByType(MethodDeclaration.class))

			{
				UMLMethod umlMeth;
				umlMeth = new UMLMethod();
				umlMeth.name = md.getNameAsString();
				umlMeth.returnType = md.getType().toString();
				List<Argument> arguments = new ArrayList<Argument>();
				for (Parameter p : md.getParameters()) {
					arguments.add(new Argument(p.getName().toString(), p.getType().toString()));
				}
				umlMeth.arguments = arguments;
				EnumSet<Modifier> modifiers = md.getModifiers();
				umlMeth.accessSpecifier = modifiers.contains(Modifier.PRIVATE) ? "private" : "public";

				List<String> parameterArray = new ArrayList<String>();
				for (TypeParameter parameter : md.getTypeParameters()) {
					parameterArray.add(parameter.getElementType().toString());
				}

				methodList.add(umlMeth);

			}

			for (ConstructorDeclaration cd : a.getNodesByType(ConstructorDeclaration.class)) {

				UMLMethod umlConstructor;
				umlConstructor = new UMLMethod();
				umlConstructor.name = cd.getNameAsString();

				List<Argument> arguments = new ArrayList<Argument>();
				for (Parameter p : cd.getParameters()) {
					arguments.add(new Argument(p.getName().toString(), p.getType().toString()));
				}
				umlConstructor.arguments = arguments;
				EnumSet<Modifier> modifiers = cd.getModifiers();
				umlConstructor.accessSpecifier = modifiers.contains(Modifier.PRIVATE) ? "private" : "public";

				List<String> parameterArray = new ArrayList<String>();
				for (TypeParameter parameter : cd.getTypeParameters()) {
					parameterArray.add(parameter.getElementType().toString());
				}

				constructorList.add(umlConstructor);

			}

			// Indexing in a List
			classContents.add(0, variableList);
			classContents.add(1, methodList);
			classContents.add(2, parentClassList);
			classContents.add(3, parentInterfaceList);
			classContents.add(4, constructorList);
			classContents.add(5, metadata);

			parsedData.put(a.getNameAsString(), classContents);

		}

		return parsedData;
	}

	public Map<String, List<List>> getParsedData1(File file) {
		// TODO Auto-generated method stub
		return null;
	}
}
