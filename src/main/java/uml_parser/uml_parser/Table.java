package uml_parser.uml_parser;

// This is just an example class. 

public class Table implements DB {

	public static final String TABLE = "table_name";
	private int tableID = 1;

}

interface DB {

	String dbNAme = null;
}

class Row implements DB {
	public static final String ROW = "row_name";
	public int rowId = 1;

	private void setRowId(int num, String str) {
	}

	public int getRowId() {
		return 999;
	}
}

class Shape extends Row {

}

class Column extends Shape implements java.io.Serializable {
}