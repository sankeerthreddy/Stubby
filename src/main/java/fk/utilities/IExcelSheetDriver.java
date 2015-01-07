package fk.utilities;

import java.util.List;

/**
 * Interface for performing read/write operations on
 * Excel workbooks.
 * @author sankeerth.reddy
 */
public interface IExcelSheetDriver {

	/**
	 * Sets the sheet reference to the specified name.
	 * @param sheetName Name of the sheet.
	 */
	void setSheet(String sheetName);

	/**
	 * @param row Row index, based on 0.
	 * @param column column index, based on 0.
	 * @return Value of the cell contained at the specified indices.
	 */
	String getStringCell(int row, int column);

	/**
	 * @param row Row index, based on 0.
	 * @param column column index, based on 0.
	 * @return Value of the cell contained at the specified indices.
	 */
	int getIntegerCell(int row, int column);

	/**
	 * @param row Row index, based on 0.
	 * @return Sequential list of row entries.
	 */
	List<String> getStringRow(int row);

	/**
	 * @param row Row index, based on 0.
	 * @return Sequential list of row entries.
	 */
	List<Integer> getIntegerRow(int row);

	/**
	 * @param row Row index, based on 0.
	 * @param column Column index, based on 0.
	 * @param cellValue Value to set.
	 */
	void setCell(int row, int column, String cellValue);

	/**
	 * @param inputArray Double indexed array to write to excel file.
	 * First index denotes the row, based on 0.
	 * Seconds index denotes the column, based on 0.
	 */
	void setAllCells(String[][] inputArray);

}
