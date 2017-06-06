package textExcel;

public class PercentCell extends RealCell{
//Cell storing Percent Values
	
	//Constructs percent value
	public PercentCell(String input) {
		super(input);
	}
	
	//Returns string of percent value of length 10; Excess zeroes in doubles truncated
	public String abbreviatedCellText(){
		if (super.fullCellText().length() > 10)
			return super.fullCellText().substring(0,10);
		else {
			String space = "";
			int dotIndex = super.fullCellText().indexOf(".");
			String temp = super.fullCellText().substring(0, dotIndex);
			int numSpace = 10 - (temp.length() + 1);
			for (int i = 0; i < numSpace; i++)
				space += " ";
			return (temp + "%" + space);
		}
	}
	
	//Returns full, unedited string of percent value
	public String fullCellText(){
		return Double.toString(super.getDoubleValue()/100);
	}
	
	//Returns double value of percent value
	public double getDoubleValue(){
		return super.getDoubleValue();
	}
}
