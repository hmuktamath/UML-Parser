package uml_parser.uml_parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.VariableElement;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;

import net.sourceforge.plantuml.SourceStringReader;

/**
 * Hello world!
 *
 */
public class App {
	
	static String className = null;
	static String variableName = null;
	static String variableType = null;
	/*
	public static void main1(String[] args) throws IOException {
	
		System.out.println("Hello World!");
		String javaFile = "class A{ public int a}";
		File file = new File("/tmp/newfile1.png");
		OutputStream png = new FileOutputStream(file);
		String source = "@startuml\n";
		source += "Bob -> Alice : hello\n";
		source += "@enduml\n";

		SourceStringReader reader = new SourceStringReader(source);
		// Write the first image to "png"
		String desc = reader.generateImage(png);
		// Return a null string if no generation

	}
  */
	public static void main(String[] args) throws ParseException, IOException {

		File file = new File("/Users/harsha.muktamath/college/repo/Hello1.java");
		String path = "/Users/harsha.muktamath/college/repo/Hello1.java";
		String path2 = "/Users/harsha.muktamath/Documents/college/uml-parser/src/main/java/uml_parser/uml_parser/Table.java";
		
		File file2 = new File(path2);

		CompilationUnit compilationUnit = JavaParser.parse(file2);

		compilationUnit.getNodesByType(ClassOrInterfaceDeclaration.class).stream().
         forEach(f -> className = f.getNameAsString());
		
		List<ClassOrInterfaceDeclaration> classdeclarations = (java.util.List<ClassOrInterfaceDeclaration>) compilationUnit.getNodesByType(ClassOrInterfaceDeclaration.class);
		
		Map<String, List<List>> classMap = new HashMap<String, List<List>>();
//		List<MethodDeclaration>  methodList= new ArrayList<>();
//		List<VariableElement> variableList = new ArrayList<>();
		
		
		for ( ClassOrInterfaceDeclaration a : classdeclarations)
		{
			
			System.out.println("Class Name :: "+a.getNameAsString());
			
		//	List<MethodDeclaration> methods = a.getMethods();
			
//			if(methods != null && !methods.isEmpty()) {
//				classMap.put(a.getNameAsString(), new ArrayList<MethodDeclaration>(methods));
//			} else {
//				classMap.put(a.getNameAsString(), null);
//			}
			List<MethodDeclaration>  methodList= new ArrayList<>();
			List<VariableDeclarator> variableList = new ArrayList<>();
			className = a.getNameAsString();
			List classContents = new ArrayList<>();
			
		    methodList.addAll(a.getMethods());
		    variableList.addAll(a.getNodesByType(VariableDeclarator.class));
			classContents.add(0, variableList);
			classContents.add(1, methodList);
			
			classMap.put(a.getNameAsString(), classContents);
			System.out.println("Complete MAp"+classMap);
			
		}
		
		for(String key : classMap.keySet()){
			
				List<List> contents = classMap.get(key);
				
				System.out.println(key+" Contents -- ");
				for(List content : contents) {
					
					for(Object declarator : content)
					{
						if(declarator instanceof MethodDeclaration)
						{
						   System.out.println(key+" --- > "+((MethodDeclaration) declarator).getNameAsString());
						}
						else{
							 System.out.println(key+" --- > "+((VariableDeclarator) declarator).getNameAsString());
						}
					}
					
				}
		
		}
		
		//System.out.println(className);
		
	//	compilationUnit.getNodesByType(VariableDeclarator.class).stream().
   //     forEach(f -> {variableName = f.getNameAsString(); variableType = f.getType().toString();});
		//System.out.println(variableName);
		//ßßßSystem.out.println(variableType);
		
		
		
		/*
		 * Object <|-- ArrayList

Object : equals()
ArrayList : Object[] elementData
ArrayList : size()
		 */
		StringBuilder sb = new StringBuilder();
		sb.append("@startuml\n");
		//sb.append(className+" : "+variableType+" "+variableName+"\n");
		//sb.append(variableType+" : "+className+" : "+variableName+"\n");
		//sb.append("@enduml\n");
		//System.out.println(sb.toString());
		
		sb.append("class Hello1 {"+"\n");
		sb.append("-str : String"+"\n");
		sb.append("}"+"\n");
		sb.append("@enduml\n");
		File pngFile = new File("/tmp/"+className+".png");
		OutputStream png = new FileOutputStream(pngFile);
		
		SourceStringReader reader = new SourceStringReader(sb.toString());
		// Write the first image to "png"
		String desc = reader.generateImage(png);
		
		
		
		
		/*
		 * @startuml

           class Dummy {
              -field1
              #field2
              ~method1()
              +method2()
            }
           @enduml
		 */
		 
	}
	
	public static String getFileContent(String path) {

		BufferedReader reader = null;
		try {

			reader = new BufferedReader(new FileReader(path));
			String line = null;

			StringBuffer appender = new StringBuffer();
			appender.append("@startuml\n");
			while( (line = reader.readLine()) != null) {
				appender.append(line+"\n");
			}
			appender.append("@enduml\n");
			return appender.toString();

		} catch(Exception e) {
			return null;
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
