package uml_parser.uml_parser;

public class UMLVariable {

	public String varName;
	public String varType;
	public String accessSpecifier;

	@Override
	public String toString() {
		return varName + " " + varType+" "+accessSpecifier;
	}

}
