package uml_parser.uml_parser;

import java.util.List;
import java.util.Map;

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
			// umldata.append("");
			List<UMLMethod> methodContents = classData.get(className).get(1);
			for (UMLMethod umlMethods : methodContents) {
				umldata.append(umlMethods.methName + "() :" +umlMethods.methReturnType  +"\n");
			}
			umldata.append("} \n");
		}
		umldata.append("@enduml\n");
		return umldata.toString();
	}

}
