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
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.TypeParameter;

public class SourceCodeParser {

	String className = null;
	String variableName = null;
	String variableType = null;

	public Map<String, List<List>> getParsedData(String filePath) throws FileNotFoundException, ClassNotFoundException {
		File file2 = new File(filePath);
		CompilationUnit compilationUnit = JavaParser.parse(file2);

		List<ClassOrInterfaceDeclaration> classdeclarations = (List<ClassOrInterfaceDeclaration>) compilationUnit
				.getNodesByType(ClassOrInterfaceDeclaration.class);

		Map<String, List<List>> parsedData = new HashMap<String, List<List>>();

		for (ClassOrInterfaceDeclaration a : classdeclarations) {

			List<UMLMethod> methodList = new ArrayList<>();
			List<UMLVariable> variableList = new ArrayList<>();
			className = a.getNameAsString();
			List classContents = new ArrayList<>();

			NodeList<ClassOrInterfaceType> c = a.getExtendedTypes();

			List<String> parentClassList = null;
			for (ClassOrInterfaceType no : c) {
				parentClassList = new ArrayList<>();
				parentClassList.add(no.getNameAsString());
			}

			NodeList<ClassOrInterfaceType> parentInterfaces = a.getImplementedTypes();

			List<String> parentInterfaceList = null;
			for (ClassOrInterfaceType no : parentInterfaces) {
				parentInterfaceList = new ArrayList<>();
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
				umlMeth.methName = md.getNameAsString();
				umlMeth.methReturnType = md.getType().toString();
				List<Argument> arguments = new ArrayList<Argument>();
				for (Parameter p : md.getParameters()) {
					arguments.add(new Argument(p.getName().toString(), p.getType().toString()));
				}
				umlMeth.arguments = arguments;
				EnumSet<Modifier> modifiers = md.getModifiers();
				umlMeth.methAccessSpecifier = modifiers.contains(Modifier.PRIVATE) ? "private" : "public";

				List<String> parameterArray = new ArrayList<String>();
				for (TypeParameter parameter : md.getTypeParameters()) {
					parameterArray.add(parameter.getElementType().toString());
				}

				methodList.add(umlMeth);

			}
			// Indexing in a List
			classContents.add(0, variableList);
			classContents.add(1, methodList);
			classContents.add(2, parentClassList);
			classContents.add(3, parentInterfaceList);
			

			parsedData.put(a.getNameAsString(), classContents);

		}

		return parsedData;
	}
}
