package textExcel;


public class Spreadsheet implements Grid
{
	Cell[][] sheet;
	int rows;
	int cols;
	//Creates 2D sheet of EmptyCells
	public Spreadsheet(){
		sheet = new Cell[21][12];
		for (int r = 0; r < sheet.length; r++){
			for (int c = 0; c < sheet[0].length; c++){
				sheet[r][c] = new EmptyCell();
			}
		}
	}
	//Helper method for clearing entire sheet
	private void clearAll(){
		for (int row = 0; row < 20; row++){
			for (int col = 0; col < 12; col++){
				sheet[row][col] = new EmptyCell();
			}
		}
	}
	//Helper method for clearing one cell
	private void clearCell(String command){
		String[] commandParts = command.split(" ");			
		SpreadsheetLocation temp = new SpreadsheetLocation(commandParts[1].toUpperCase());
		sheet[temp.getRow()][temp.getCol()] = new EmptyCell();
	}
	//Helper method for assigning value to one cell
	private void inputCellVal(String command){
		int indexE = command.indexOf("=");
		String[] commandParts = new String[2];
		//Splitting by '=' could split the text; instead, the command is split by the first '=' index
		commandParts[0] = command.substring(0, indexE - 1);
		commandParts[1] = command.substring(indexE + 2);
		if (commandParts[0].length() >= 2 && commandParts[1].length() > 0){
			SpreadsheetLocation temp = new SpreadsheetLocation(commandParts[0].toUpperCase());
			if (commandParts[1].contains("\"")){
				String val = commandParts[1].substring(1, commandParts[1].length()-1);
				sheet[temp.getRow()][temp.getCol()] = new TextCell(val);
			}
			else if (commandParts[1].contains("%")){
				String percentVal = commandParts[1];
				sheet[temp.getRow()][temp.getCol()] = new PercentCell(percentVal);
			}
			else {
				String mathVal = commandParts[1];
				sheet[temp.getRow()][temp.getCol()] = new RealCell(mathVal);
			}	
		}
	}
	//Helper method for returning value of one cell
	private String returnCellVal(String cellLocation){
		SpreadsheetLocation val = new SpreadsheetLocation(cellLocation.toUpperCase());
		String returnVal = sheet[val.getRow()][val.getCol()].fullCellText();
		return returnVal ;
	}
	//Processes commands by user [Utilizes Helper Methods above]
	public String processCommand(String command)
	{
		if (command.equals("")){
			return "";
		}
		else if (command.equalsIgnoreCase("clear")){
			clearAll();
			return getGridText();
		}
		else if (command.toLowerCase().startsWith("clear")){
			clearCell(command);
			return getGridText();
		}
		else if (command.contains("=")){
			inputCellVal(command);
			return getGridText();
		}
		else if (command.length() <= 3 && command.length() > 1) {
			String x = returnCellVal(command);
			if (x.equals(""))
				return "";
			else
				return returnCellVal(command);
		}
		else {
			System.out.println("Unknown command");
			return command;
		}
	}
	//Returns number of rows in sheet
	@Override
	public int getRows()
	{
		return sheet.length - 1;
	}
	//Returns number of columns in sheet
	@Override
	public int getCols()
	{
		return sheet[0].length;
	}
	//Returns cell at location in sheet
	@Override
	public Cell getCell(Location loc)
	{
		return sheet[loc.getRow()][loc.getCol()];
	}
	//Prints sheet in correct format with real-time values
	@Override
	public String getGridText(){
		String grid = "";
		for (int i = 0; i < sheet[0].length; i ++){
			if (i == 0)
				grid +=("   ");
			grid += ("|" + (char) (i + 65) + "         ");
			
		}
		grid+= ("|");
		grid+= ("\n");
		for (int i = 1; i < sheet.length; i++){
			if (i < 10)
				grid += (i + "  " + "|");
			else
				grid +=(i + " " + "|");
			for (int j = 0; j < sheet[0].length; j++){
				grid += sheet[i - 1][j].abbreviatedCellText() + "|";
				/*if (sheet[i][j].abbreviatedCellText().equals(""))
					grid += ("          |");
				else {
					grid += sheet[i][j] + "|";
				} */
			}
			grid += "\n";
		}
		return grid;
	}

}
