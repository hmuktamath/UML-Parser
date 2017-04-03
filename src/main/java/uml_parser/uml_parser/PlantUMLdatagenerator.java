package uml_parser.uml_parser;

/* This code is for generating the UML diagrams using PlantUML
 * 
 */
import java.util.List;
import java.util.Map;

public class PlantUMLdatagenerator {

	public String generateUMLData(Map<String, List<List>> classData) {
		StringBuilder umldata = new StringBuilder();

		umldata.append("@startuml\n");
		for (String className : classData.keySet()) {
			umldata.append("class " + className + "{" + "\n");
			List<UMLVariable> variableNames = classData.get(className).get(0);
			for (UMLVariable umlVariable : variableNames) {
				/// "-str : String"+"\n"
				String asSymbol = "+";
				switch (umlVariable.accessSpecifier) {
				case "public":
					asSymbol = "+";
					break;

				case "private":
					asSymbol = "-";
					break;

				case "protected":
					asSymbol = "#";
					break;

				default:
					asSymbol = "+";

				}

				umldata.append(asSymbol + umlVariable.varName + " : " + umlVariable.varType + "\n");
			}

			List<UMLMethod> methodContents = classData.get(className).get(1);
			for (UMLMethod umlMethods : methodContents) {
				String accessSpecifier = umlMethods.methAccessSpecifier;
				String asSymbol = "+";
				switch (accessSpecifier) {
				case "public":
					asSymbol = "+";
					break;

				case "private":
					asSymbol = "-";
					break;

				case "protected":
					asSymbol = "#";
					break;

				default:
					asSymbol = "+";

				}

				umldata.append(asSymbol + umlMethods.methName + "(");
				int varCount = umlMethods.arguments.size();
				int count = 1;
				for (Argument a : umlMethods.arguments) {

					umldata.append(a.name + " : " + a.type);

					if (count != varCount)
						umldata.append(" , ");

					count++;

				}

				umldata.append(") :" + umlMethods.methReturnType + "\n");
			}

			umldata.append("} \n");
			List parentClassList = classData.get(className).get(2);
			if (parentClassList != null) {
				umldata.append(parentClassList.get(0) + " <|-- " + className + "\n");
			}
			
			List<String> parentInterfaceList = classData.get(className).get(3);
			if(parentInterfaceList != null)
			{
				for(String parentInterface : parentInterfaceList)
				{
					umldata.append(parentInterface + " <|.. " + className + "\n");	
				}
			}
			
		}

		umldata.append("@enduml\n");
		return umldata.toString();
	}

}
