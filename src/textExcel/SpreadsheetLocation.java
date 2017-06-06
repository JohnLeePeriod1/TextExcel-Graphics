package textExcel;

public class SpreadsheetLocation implements Location
{
	String location;
	
	//Creates Location within Sheet (as String)
	public SpreadsheetLocation(String loc){
		this.location = loc;
	}
    @Override
    //Returns Row Number of Location
    public int getRow()
    {
        int row =  Integer.parseInt(location.substring(1));
        return row - 1;
    }

    @Override
    //Returns Column Number of Location
    public int getCol()
    {
    	location.toUpperCase();
        char letter = location.charAt(0);           
        int col = (int)letter;
        return col - (int)'A';
    }
    
}
