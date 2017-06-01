

//*******************************************************
//DO NOT MODIFY THIS FILE!!!
//*******************************************************

import static org.junit.Assert.*;
import textExcel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestsALL.A_Checkpoint1.class,
    TestsALL.A_Checkpoint2.class,
    TestsALL.A_Checkpoint3.class,
})

public class TestsALL
{
    public static class TestLocation implements Location
    {
        // Simple implementation of Location interface for use only by tests.
        private int row;
        private int col;

        public TestLocation(int row, int col)
        {
            this.row = row;
            this.col = col;
        }

        @Override
        public int getRow() {
            return row;
        }

        @Override
        public int getCol() {
            return col;
        }
    }

    public static class Helper
    {
        // For use only by test code, which uses it carefully.
        private String[][] items;

        public Helper()
        {
            items = new String[20][12];
            for (int i = 0; i < 20; i++)
                for (int j = 0; j < 12; j++)
                    items[i][j] = format("");
        }

        public static String format(String s)
        {
            return String.format(String.format("%%-%d.%ds", 10, 10),  s);
        }

        public void setItem(int row, int col, String text)
        {
            items[row][col] = format(text);
        }

        public String getText()
        {
            String ret = "   |";
            for (int j = 0; j < 12; j++)
                ret = ret + format(Character.toString((char)('A' + j))) + "|";
            ret = ret + "\n";
            for (int i = 0; i < 20; i++)
            {
                ret += String.format("%-3d|", i + 1);
                for (int j = 0; j < 12; j++)
                {
                    ret += items[i][j] + "|";
                }
                ret += "\n";
            }
            return ret;
        }
    }

    public static class A_Checkpoint1
    {
        // Tests for checkpoint 1.
        // Pass them all, plus ensure main loop until quit works, for full credit on checkpoint 1.
        // Note these must also pass for all subsequent checkpoints including final project.
        Grid grid;

        @Before
        public void initializeGrid()
        {
            grid = new Spreadsheet();
        }

        @Test
        public void testGetRows()
        {
            assertEquals("getRows", 20, grid.getRows());
        }

        @Test
        public void testGetCols()
        {
            assertEquals("getCols", 12, grid.getCols());
        }

        @Test
        public void testProcessCommand()
        {
            String str = grid.processCommand("");
            assertEquals("output from empty command", "", str);
        }

        @Test
        public void testLongShortStringCell()
        {
            SpreadsheetLocation loc = new SpreadsheetLocation("L20");
            assertEquals("SpreadsheetLocation column", loc.getCol(), 11);
            assertEquals("SpreadsheetLocation row", loc.getRow(), 19);

            loc = new SpreadsheetLocation("D5");
            assertEquals("SpreadsheetLocation column", loc.getCol(), 3);
            assertEquals("SpreadsheetLocation row", loc.getRow(), 4);

            loc = new SpreadsheetLocation("A1");
            assertEquals("SpreadsheetLocation column", loc.getCol(), 0);
            assertEquals("SpreadsheetLocation row", loc.getRow(), 0);
        }

        @Test
        public void testProcessCommandNonliteralEmpty()
        {
            String input = " ".trim();
            String output = grid.processCommand(input);
            assertEquals("output from empty command", "", output);
        }
    }

    public static class A_Checkpoint2
    {
        // Tests for checkpoint 2.
        // Note these must also pass for all subsequent checkpoints including final project.
        Grid grid;

        @Before
        public void initializeGrid()
        {
            grid = new Spreadsheet();
        }

        @Test
        public void testEmptyGridCells()
        {
            for (int i = 0; i < grid.getRows(); i++)
                for (int j = 0; j < grid.getCols(); j++)
                {
                    Cell cell = grid.getCell(new TestLocation(i, j));
                    assertEquals("empty cell text", Helper.format(""), cell.abbreviatedCellText());
                    assertEquals("empty inspection text", "", cell.fullCellText());
                }
        }

        @Test
        public void testEmptyGridText()
        {
            Helper helper = new Helper();
            assertEquals("empty grid", helper.getText(), grid.getGridText());
        }

        @Test
        public void testShortStringCell()
        {
            String hello = "Hello";
            grid.processCommand("A1 = \"" + hello + "\"");
            Cell helloCell = grid.getCell(new TestLocation(0,0));
            assertEquals("hello cell text", Helper.format(hello), helloCell.abbreviatedCellText());
            assertEquals("hello inspection text", "\"" + hello + "\"", helloCell.fullCellText());
        }

        @Test
        public void testLongShortStringCell()
        {
            String greeting = "Hello, world!";
            grid.processCommand("L20 = \"" + greeting + "\"");
            Cell greetingCell = grid.getCell(new TestLocation(19,11));
            assertEquals("greeting cell text", Helper.format(greeting), greetingCell.abbreviatedCellText());
            assertEquals("greeting inspection text", "\"" + greeting + "\"", greetingCell.fullCellText());
        }

        @Test
        public void testEmptyStringCell()
        {
            grid.processCommand("B2 = \"\"");
            Cell emptyStringCell = grid.getCell(new TestLocation(1,1));
            assertEquals("empty string cell text", Helper.format(""), emptyStringCell.abbreviatedCellText());
            assertEquals("empty string inspection text", "\"\"", emptyStringCell.fullCellText());
        }

        @Test
        public void testDifferentCellTypes()
        {
            grid.processCommand("C11 = \"hi\"");
            Cell stringCell = grid.getCell(new TestLocation(10, 2));
            Cell emptyCell = grid.getCell(new TestLocation(0,0));
            assertTrue("string cell implementation class must be different from empty cell",
                    !emptyCell.getClass().equals(stringCell.getClass()));
        }

        @Test
        public void testClear()
        {
            grid.processCommand("A1 = \"first\"");
            grid.processCommand("D8 = \"second\"");
            grid.processCommand("clear");
            Cell cellFirst = grid.getCell(new TestLocation(0,0));
            Cell cellSecond = grid.getCell(new TestLocation(7, 3));
            assertEquals("cellFirst inspection text after clear", "", cellFirst.fullCellText());
            assertEquals("cellSecond inspection text after clear", "", cellSecond.fullCellText());
        }

        @Test
        public void testClearLocation()
        {
            grid.processCommand("A1 = \"first\"");
            grid.processCommand("D8 = \"second\"");
            grid.processCommand("clear A1");
            Cell cellFirst = grid.getCell(new TestLocation(0,0));
            Cell cellSecond = grid.getCell(new TestLocation(7, 3));
            assertEquals("cellFirst inspection text after clear", "", cellFirst.fullCellText());
            assertEquals("cellSecond inspection text after clear", "\"second\"", cellSecond.fullCellText());
        }

        @Test
        public void testProcessCommandInspection()
        {
            String empty = grid.processCommand("A1");
            assertEquals("inspection of empty cell", "", empty);
            grid.processCommand("A1 = \"first\"");
            String first = grid.processCommand("A1");
            assertEquals("inspection of string cell", "\"first\"", first);
        }

        @Test
        public void testProcessCommand()
        {
            Helper helper = new Helper();
            String gridOne = grid.processCommand("A1 = \"oNe\"");
            helper.setItem(0, 0, "oNe");
            assertEquals("grid with one string cell", helper.getText(), gridOne);
            String accessorOne = grid.getGridText();
            assertEquals("grid from accessor with one string cell", helper.getText(), accessorOne);
            String gridTwo = grid.processCommand("L20 = \"TWo\"");
            helper.setItem(19, 11, "TWo");
            assertEquals("grid from accessor with two string cells", helper.getText(), gridTwo);
            String gridOnlyTwo = grid.processCommand("clear A1");
            helper.setItem(0, 0, "");
            assertEquals("grid with only the second string cell", helper.getText(), gridOnlyTwo);
            String gridEmpty = grid.processCommand("clear");
            helper.setItem(19, 11, "");
            assertEquals("empty grid", helper.getText(), gridEmpty);
        }

        @Test
        public void testProcessCommandSpecialStrings()
        {
            String stringSpecial1 = "A1 = ( avg A2-A3 )";
            String stringSpecial2 = "A1 = ( 1 * 2 / 1 + 3 - 5 )";
            Helper helper = new Helper();
            String grid1 = grid.processCommand("B7 = \"" + stringSpecial1 + "\"");
            helper.setItem(6, 1, stringSpecial1);
            assertEquals("grid with one special string", helper.getText(), grid1);
            String grid2 = grid.processCommand("F13 = \"" + stringSpecial2 + "\"");
            helper.setItem(12, 5, stringSpecial2);
            assertEquals("grid with two special strings", helper.getText(), grid2);
            String inspectedSpecial1 = grid.getCell(new TestLocation(6,1)).fullCellText();
            assertEquals("inspected first special string", "\"" + stringSpecial1 + "\"", inspectedSpecial1);
            String inspectedSpecial2 = grid.getCell(new TestLocation(12,5)).fullCellText();
            assertEquals("inspected second special string", "\"" + stringSpecial2 + "\"", inspectedSpecial2);
        }

        @Test
        public void testLongStringCellNoSpaces()
        {
            String greeting = "ThisIsALongString";
            grid.processCommand("L2 = \"" + greeting + "\"");
            Cell greetingCell = grid.getCell(new TestLocation(1,11));
            assertEquals("greeting cell text", Helper.format(greeting), greetingCell.abbreviatedCellText());
            assertEquals("greeting inspection text", "\"" + greeting + "\"", greetingCell.fullCellText());
        }

        @Test
        public void testLowerCaseCellAssignment()
        {
            String text = "Cell";
            grid.processCommand("b5 = \"" + text + "\"");
            Cell cell = grid.getCell(new TestLocation(4, 1));
            assertEquals("cell text", Helper.format(text), cell.abbreviatedCellText());
            assertEquals("inspection text", "\"" + text + "\"", cell.fullCellText());
            String processText = grid.processCommand("b5");
            assertEquals("processed inspection text", "\"" + text + "\"", processText);
            String processText2 = grid.processCommand("B5");
            assertEquals("processed inspection text 2", "\"" + text + "\"", processText2);
        }

        @Test
        public void testLowerCaseCellProcessInspection()
        {
            grid.processCommand("B2 = \"\"");
            String processText = grid.processCommand("b2");
            assertEquals("processed inspection text", "\"\"", processText);
            grid.processCommand("c18 = \"3.1410\"");
            String processText2 = grid.processCommand("c18");
            assertEquals("processed inspection text 2", "\"3.1410\"", processText2);
        }

        @Test
        public void testMixedCaseClear()
        {
            grid.processCommand("A1 = \"first\"");
            grid.processCommand("D8 = \"second\"");
            grid.processCommand("CleaR");
            Cell cellFirst = grid.getCell(new TestLocation(0,0));
            Cell cellSecond = grid.getCell(new TestLocation(7, 3));
            assertEquals("cellFirst inspection text after clear", "", cellFirst.fullCellText());
            assertEquals("cellSecond inspection text after clear", "", cellSecond.fullCellText());
        }

        @Test
        public void textNonliteralClear()
        {
            String clear = " clear ".trim();
            grid.processCommand("A1 = \"first\"");
            grid.processCommand("D8 = \"second\"");
            grid.processCommand(clear);
            Cell cellFirst = grid.getCell(new TestLocation(0,0));
            Cell cellSecond = grid.getCell(new TestLocation(7, 3));
            assertEquals("cellFirst inspection text after clear", "", cellFirst.fullCellText());
            assertEquals("cellSecond inspection text after clear", "", cellSecond.fullCellText());
            String finalGrid = grid.getGridText();
            Helper th = new Helper();
            String emptyGrid = th.getText();
            assertEquals("empty grid", emptyGrid, finalGrid);
        }

        @Test
        public void testMixedCaseClearLocation()
        {
            grid.processCommand("A18 = \"first\"");
            grid.processCommand("D8 = \"second\"");
            grid.processCommand("clEAr a18");
            Cell cellFirst = grid.getCell(new TestLocation(17,0));
            Cell cellSecond = grid.getCell(new TestLocation(7, 3));
            assertEquals("cellFirst inspection text after clear", "", cellFirst.fullCellText());
            assertEquals("cellSecond inspection text after clear", "\"second\"", cellSecond.fullCellText());
            String processedCleared = grid.processCommand("A18");
            assertEquals("processed inspection after clear", "", processedCleared);
        }

        @Test
        public void testProcessCommandMoreSpecialStrings()
        {
            String[] specialStrings = new String[] { "clear", "(", " = ", "5", "4.3", "12/28/1998", "A1 = ( 1 / 1 )", "A20 = 1/1/2000", "A9 = 4.3", "abcdefgh", "abcdefghi", "abcdefghijk" };

            Helper helper = new Helper();
            for (int col = 0; col < specialStrings.length; col++)
            {
                for (int row = 5; row < 20; row += 10)
                {
                    String cellName = Character.toString((char)('A' + col)) + (row + 1);
                    helper.setItem(row,  col, specialStrings[col]);
                    String sheet = grid.processCommand(cellName + " = \"" + specialStrings[col] + "\"");
                    assertEquals("grid after setting cell " + cellName, helper.getText(), sheet);
                    String inspected = grid.getCell(new TestLocation(row, col)).fullCellText();
                    assertEquals("inspected cell " + cellName, "\"" + specialStrings[col] + "\"", inspected);
                }
            }
            assertEquals("final sheet", helper.getText(), grid.getGridText());
        }
    }

    public static class A_Checkpoint3
    {
        // Tests for checkpoint 3.
        // Note these must also pass for all subsequent checkpoints including final project.
        Grid grid;

        @Before
        public void initializeGrid()
        {
            grid = new Spreadsheet();
        }

        @Test
        public void testPercentCell()
        {
            String percent = "11.25%";
            grid.processCommand("A1 = " + percent);
            Cell dateCell = grid.getCell(new TestLocation(0,0));
            assertEquals("date cell text", "11%", dateCell.abbreviatedCellText().trim());
            assertEquals("date inspection text", "0.1125", dateCell.fullCellText());
        }

        @Test
        public void testBasicRealCell()
        {
            String real = "3.14";
            grid.processCommand("D18 = " + real);
            Cell realCell = grid.getCell(new TestLocation(17, 3));
            assertEquals("real cell text", Helper.format(real), realCell.abbreviatedCellText());
            assertEquals("real inspection text", real, realCell.fullCellText());
        }

        @Test
        public void testMoreRealCells()
        {
            String zero = "0.0";
            grid.processCommand("A1 = " + zero);
            Cell zeroCell = grid.getCell(new TestLocation(0, 0));
            assertEquals("real cell 0", Helper.format(zero), zeroCell.abbreviatedCellText());
            assertEquals("real inspection 0", zero, zeroCell.fullCellText());
            String negativeTwo = "-2.0";
            grid.processCommand("B1 = " + negativeTwo);
            Cell negativeTwoCell = grid.getCell(new TestLocation(0, 1));
            assertEquals("real cell -2", Helper.format(negativeTwo), negativeTwoCell.abbreviatedCellText());
            assertEquals("real inspection -2", negativeTwo, negativeTwoCell.fullCellText());
        }

        @Test
        public void testDifferentCellTypes()
        {
            grid.processCommand("H4 = 12.281998%");
            grid.processCommand("G3 = \"5\"");
            grid.processCommand("F2 = -123.456");
            Cell dateCell = grid.getCell(new TestLocation(3, 7));
            Cell stringCell = grid.getCell(new TestLocation(2, 6));
            Cell realCell = grid.getCell(new TestLocation(1, 5));
            Cell emptyCell = grid.getCell(new TestLocation(0, 4));
            Cell[] differentCells = { dateCell, stringCell, realCell, emptyCell };
            for (int i = 0; i < differentCells.length - 1; i++)
            {
                for (int j = i + 1; j < differentCells.length; j++)
                {
                    assertTrue("percent, string, real, empty cells must all have different class types",
                            !differentCells[i].getClass().equals(differentCells[j].getClass()));
                }
            }
        }

        @Test
        public void testProcessCommand()
        {
            Helper helper = new Helper();
            String first = grid.processCommand("A1 = 1.021822%");
            helper.setItem(0, 0, "1%");
            assertEquals("grid with date", helper.getText(), first);
            String second = grid.processCommand("B2 = -5");
            helper.setItem(1, 1, "-5.0");
            assertEquals("grid with date and number", helper.getText(), second);
            String third = grid.processCommand("C3 = 2.718");
            helper.setItem(2, 2, "2.718");
            assertEquals("grid with date and two numbers", helper.getText(), third);
            String fourth = grid.processCommand("D4 = 0");
            helper.setItem(3, 3, "0.0");
            assertEquals("grid with date and three numbers", helper.getText(), fourth);
        }

        @Test
        public void testRealCellFormat()
        {
            // NOTE spec not totally consistent on inspection format, allow anything that parses to within epsilon of as entered
            String[] realsEntered = { "3.00", "-74.05000", "400", "400.0" };
            String[] realsFormatted = { "3.0       ", "-74.05    ", "400.0     ", "400.0     " };
            Helper helper = new Helper();
            for (int col = 0; col < realsEntered.length; col++)
            {
                for (int row = 6; row < 20; row += 10)
                {
                    String cellName = Character.toString((char)('A' + col)) + (row + 1);
                    String sheet = grid.processCommand(cellName + " = " + realsEntered[col]);
                    helper.setItem(row,  col, realsFormatted[col]);
                    assertEquals("sheet after setting cell " + cellName, helper.getText(), sheet);
                    String inspected = grid.getCell(new TestLocation(row, col)).fullCellText();
                    double expected = Double.parseDouble(realsEntered[col]);
                    double actual = Double.parseDouble(inspected);
                    assertEquals("inspected real value", actual, expected, 1e-6);
                }
            }
            assertEquals("final sheet", helper.getText(), grid.getGridText());
        }

        @Test
        public void testRealCellTruncation()
        {
            String big = "-9876543212345";
            grid.processCommand("A1 = " + big);
            Cell bigCell = grid.getCell(new TestLocation(0, 0));
            assertEquals("real big cell length", 10, bigCell.abbreviatedCellText().length());
            assertEquals("real big inspection ", Double.parseDouble(big), Double.parseDouble(bigCell.fullCellText()), 1e-6);

            String precise = "3.14159265358979";
            grid.processCommand("A2 = " + precise);
            Cell preciseCell = grid.getCell(new TestLocation(1, 0));
            assertEquals("real precise cell length", 10, preciseCell.abbreviatedCellText().length());
            assertEquals("real precise cell", Double.parseDouble(precise), Double.parseDouble(preciseCell.abbreviatedCellText()), 1e-6);
            assertEquals("real precise inspection ", Double.parseDouble(precise), Double.parseDouble(preciseCell.fullCellText()), 1e-6);

            String moderate = "123456";
            grid.processCommand("A3 = " + moderate);
            Cell moderateCell = grid.getCell(new TestLocation(2, 0));
            assertEquals("real moderate cell length", 10, moderateCell.abbreviatedCellText().length());
            assertEquals("real moderate cell", moderate + ".0", moderateCell.abbreviatedCellText().trim());
            assertEquals("real moderate inspection", moderate, moderateCell.fullCellText());

            String precisePerc = "7.87878%";
            grid.processCommand("A4 = " + precisePerc);
            Cell precisePerCell = grid.getCell(new TestLocation(3, 0));
            assertEquals("real precise percent cell length", 10, precisePerCell.abbreviatedCellText().length());
            assertEquals("real precise percent cell", "7%", precisePerCell.abbreviatedCellText().trim());
            assertEquals("real precise percent inspection", "0.0787878", precisePerCell.fullCellText());
        }        
    }
    
}
