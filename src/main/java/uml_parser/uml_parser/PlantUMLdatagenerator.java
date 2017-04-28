package uml_parser.uml_parser;

import java.util.HashMap;
import java.util.HashSet;
/* This code is for generating the UML diagrams using PlantUML
 * 
 */
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PlantUMLdatagenerator {

	public String generateUMLData(Map<String, List> classData) {

		Map<String, Set<String>> hasRelationShips = new HashMap<String, Set<String>>();
		Map<String, Set<String>> usesRelationShips = new HashMap<String, Set<String>>();
		StringBuilder umldata = new StringBuilder();

		Set<String> classes = classData.keySet();
		umldata.append("@startuml\n");
		for (String className : classes) {
			boolean isInterface = false;
			Map metadata = (Map) classData.get(className).get(5);
			if (metadata.get("isInterface").equals(true)) {
				isInterface = true;
				umldata.append("interface " + className + "{" + "\n");
			} else {
				umldata.append("class " + className + "{" + "\n");
			}

			List<UMLVariable> variableNames = (List<UMLVariable>) classData.get(className).get(0);
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
				if (asSymbol.equals("#")){
					continue;
				}
				

				umldata.append(asSymbol + umlVariable.varName + " : " + umlVariable.varType + "\n");

				String varType = umlVariable.varType;
				if (classes.contains(varType)) {
					if (hasRelationShips.containsKey(className)) {
						hasRelationShips.get(className).add(varType);
					} else {
						Set<String> relations = new HashSet<String>();
						relations.add(varType);
						hasRelationShips.put(className, relations);
					}

				}
			}

			// Creating PlantUML specs for methods.
			List<UMLMethod> methodContents = (List<UMLMethod>) classData.get(className).get(4);
			for (UMLMethod umlMethods : methodContents) {
				String accessSpecifier = umlMethods.accessSpecifier;
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
				if (!asSymbol.equals("+")){
					continue;
				}

				umldata.append(asSymbol + umlMethods.name + "(");
				int varCount = umlMethods.arguments.size();
				int count = 1;
				for (Argument a : umlMethods.arguments) {
					String argumentType = a.type;
					umldata.append(a.name + " : " + argumentType);

					if (classes.contains(argumentType) && !isInterface) {
						if (usesRelationShips.containsKey(className)) {
							usesRelationShips.get(className).add(argumentType);
						} else {
							Set<String> relations = new HashSet<String>();
							relations.add(argumentType);
							usesRelationShips.put(className, relations);
						}

					}

					if (count != varCount)
						umldata.append(" , ");

					count++;

				}

				umldata.append(") :" + umlMethods.returnType + "\n");
			}

			// Creating PlantUML specs for methods.
			List<UMLMethod> constructorContents = (List<UMLMethod>) classData.get(className).get(1);
			for (UMLMethod umlMethods : constructorContents) {
				String accessSpecifier = umlMethods.accessSpecifier;
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

				umldata.append(asSymbol + umlMethods.name + "(");
				int varCount = umlMethods.arguments.size();
				int count = 1;
				for (Argument a : umlMethods.arguments) {
					String argumentType = a.type;
					umldata.append(a.name + " : " + argumentType);

					if (classes.contains(argumentType) && !isInterface) {
						if (usesRelationShips.containsKey(className)) {
							usesRelationShips.get(className).add(argumentType);
						} else {
							Set<String> relations = new HashSet<String>();
							relations.add(argumentType);
							usesRelationShips.put(className, relations);
						}

					}

					if (count != varCount)
						umldata.append(" , ");

					count++;

				}

				umldata.append(") \n");
			}

			umldata.append("} \n");
			List parentClassList = (List) classData.get(className).get(2);
			if (parentClassList != null && parentClassList.size() > 0) {
				umldata.append(parentClassList.get(0) + " <|-- " + className + "\n");
			}

			List<String> parentInterfaceList = (List<String>) classData.get(className).get(3);
			if (parentInterfaceList != null) {
				for (String parentInterface : parentInterfaceList) {
					umldata.append(parentInterface + " <|.. " + className + "\n");
				}
			}

		}
		Set<String> relationClasses = hasRelationShips.keySet();

		for (Entry<String, Set<String>> e : hasRelationShips.entrySet()) {

			for (String c : e.getValue()) {
				umldata.append(e.getKey() + " -- " + c + "\n");
			}

		}

		for (Entry<String, Set<String>> e : usesRelationShips.entrySet()) {

			for (String c : e.getValue()) {
				umldata.append(e.getKey() + " ..> " + c + " : uses\n");
			}

		}


		umldata.append("@enduml\n");
		return umldata.toString();
	}

}
