package uml_parser.uml_parser;

import java.util.List;

//Class for Methods
public class UMLMethod {

	public String methName;
	public String methAccessSpecifier;
	public String methReturnType;

	public List<Argument> arguments;

}

class Argument {

	String name;
	String type;

	public Argument(String name, String type) {
		super();
		this.name = name;
		this.type = type;
	}
}
