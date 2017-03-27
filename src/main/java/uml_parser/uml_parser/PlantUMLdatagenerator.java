package uml_parser.uml_parser;

import java.util.List;
import java.util.Map;


// this is for generating objects
public class PlantUMLdatagenerator {

	public String generateUMLData(Map<String, List<List>> classData) {
		StringBuilder umldata = new StringBuilder();
		;
		umldata.append("@startuml\n");
		for (String className : classData.keySet()) {
			umldata.append("class " + className + "{" + "\n");
			List<UMLVariable> variableNames = classData.get(className).get(0);
			for (UMLVariable umlVariable : variableNames) {
				/// "-str : String"+"\n"

				umldata.append(umlVariable.varName + " : " + umlVariable.varType + "\n");
			}
		
			List<UMLMethod> methodContents = classData.get(className).get(1);
			for (UMLMethod umlMethods : methodContents) {
				String accessSpecifier = umlMethods.methAccessSpecifier;
				
				umldata.append(accessSpecifier+" "+umlMethods.methName + "() :" + umlMethods.methReturnType + "\n");
			}

			
			umldata.append("} \n");
			List parentClassList = classData.get(className).get(2);
			if (parentClassList != null) {
				umldata.append(parentClassList.get(0) + " <|-- " + className+"\n");
			}
		}
		
		umldata.append("@enduml\n");
		return umldata.toString();
	}

}
