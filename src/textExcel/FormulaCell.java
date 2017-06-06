package textExcel;

public class FormulaCell extends RealCell {
//Cells storing formulas [Non int/double values]
	
	//Constructs formula
	public FormulaCell(String input) {
		super(input);
	}
	
	//Returns double value of formula
	public double getDoubleValue(){
		return 0.0;
		//Return any double value as FormulaCell does not need to parse formulas in checkpoint 3
	}
}
