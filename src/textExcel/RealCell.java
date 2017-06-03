package textExcel;

public class RealCell implements Cell {

	private double val;
	private String string;
	
	public RealCell(String inputVal){
		if (inputVal.contains("%")){
			val = Double.parseDouble((inputVal.substring(0,inputVal.length()-1)));
			this.string = inputVal;
		}
		else{
			this.string = inputVal;
			this.val = Double.parseDouble(string);
		}
	}
	
	@Override
	public String abbreviatedCellText() {
		String temp = "";
		if (string.length() > 10)
			return this.string.substring(0,10);
		else {
			if (string.contains(".")){
				int pointIndex = string.indexOf(".");
				String afterPoint = string.substring(pointIndex + 1, string.length());
				if (afterPoint.contains("0") && afterPoint.length() > 1 && afterPoint.endsWith("0")){
					int nonZero = -1;
					for (int i = string.length()-1; i > pointIndex; i--){
						if (this.string.charAt(i) != '0'){
							nonZero = i;
							break;
						}
					}
					if (nonZero == -1)
						nonZero = pointIndex + 1;
					temp = string.substring(0, nonZero+1);
					String space = "";
					int numSpace = 10 - (temp.length());
					for (int i = 0; i < numSpace; i++)
						space += " ";
					return (temp + space);
				}
				String space = "";
				int numSpace = 10 - (this.string.length());
				for (int i = 0; i < numSpace; i++)
					space += " ";
				return (this.string + space);
			}
			else if (!(string.contains("."))){
				String space = "";
				int numSpace = 10 - (string.length()+ 2);
				for (int i = 0; i < numSpace; i++)
					space += " ";
				return (this.string + ".0" + space);
			}
			return "";
		}
	}

	@Override
	public String fullCellText() {
		return string;
	}
	
	public double getDoubleValue(){
		return val;
		
	}

}
