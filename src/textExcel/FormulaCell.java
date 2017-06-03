package textExcel;

public class FormulaCell extends RealCell {

	public FormulaCell(String input) {
		super(input);
	}
	public double getDoubleValue(){
		return 0.0;
		//Return any double value as FormulaCell does not need to parse formulas in checkpoint 3
	}
}
