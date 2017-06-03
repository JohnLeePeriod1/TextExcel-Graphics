package textExcel;

public class EmptyCell implements Cell {

	@Override
	//Returns blank string 10 characters long [Placeholder]
	public String abbreviatedCellText() {
		return "          ";
	}

	@Override
	//Returns empty string
	public String fullCellText() {
		return "";
	}
	
	public EmptyCell(){
		//Empty constructor for null value (Only needed to fill array with EmptyCell objects)  
	}
//Travis CI Y
}
