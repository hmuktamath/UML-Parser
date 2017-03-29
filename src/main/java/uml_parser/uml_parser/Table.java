package uml_parser.uml_parser;

// This is just an example class. 

public class Table {
	
	public static final String TABLE = "table_name";
	private int tableID = 1;
	
	
	 }
class Row{
	public static final String ROW = "row_name";
	public int rowId = 1;
	private void setRowId(int num){
	}
	
	public int getRowId() {
		return 999;
	}
	}	


class Column extends Row implements java.io.Serializable{
}