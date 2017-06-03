package textExcel;

public class TextCell implements Cell {
	String text;
	
	//Creates cell representing Text
	public TextCell(String val){
		this.text = val;
	}
	@Override
	//Returns TextValue represented in GridText [10 characters long]
	public String abbreviatedCellText() {
		if (text.length() > 10)
			return this.text.substring(0,10);
		else {
			String space = "";
			int numSpace = 10 - text.length();
			for (int i = 0; i < numSpace; i++)
				space += " ";
			return (this.text + space);
		}
			}

	@Override
	//Returns Actual TextValue [Represented in quotations]
	public String fullCellText() {
		return "\"" + this.text + "\"";
	}

}
