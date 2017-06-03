package textExcel;

public class PercentCell extends RealCell{

	public PercentCell(String input) {
		super(input);
	}
	
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
	
	public String fullCellText(){
		return Double.toString(super.getDoubleValue()/100);
	}
	
	public double getDoubleValue(){
		return super.getDoubleValue();
	}
}
