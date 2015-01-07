package fk.utilities;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper on Apache excel reader library.
 * @author sankeerth.reddy
 */
public class ExcelSheetUitility implements IExcelSheetDriver {

	/**
	 * Instance of default logger.
	 */
	private Logger LOGGER =
			Logger.getLogger(ExcelSheetUitility.class);

	/**
	 * Reference to the actively selected work sheet.
	 */
	private XSSFSheet sheet;

	/**
	 * Reference to entire work book.
	 */
	private XSSFWorkbook workbook;

	/**
	 * Name of the excel workbook to read.
	 */
	private String fileName;

	/**
	 * Constructor.
	 * @param file File name and path.
	 */
	public ExcelSheetUitility(final String file) {
		this.fileName = file;
		try {
			InputStream is = new FileInputStream(file);
			this.workbook = new XSSFWorkbook(is);
			is.close();
		} catch (IOException e) {
			LOGGER.error("Error in reading file: " + file, e);
			System.exit(1);
		}
	}

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.utilities.IExcelSheetDriver
	 * #setSheet(java.lang.String)
	 */
	@Override
	public final void setSheet(final String sheetName) {
		this.sheet = this.workbook.getSheet(sheetName);
		try {
			this.sheet.getRow(0);
		} catch (NullPointerException e) {
			LOGGER.error("ERROR: Specified sheet not found: "
					+ sheetName, e);
		}
	}

	@Override
	public final List<String> getStringRow(final int row) {
		List<String> rowList = new ArrayList<String>();
		if (this.sheet == null) {
			throw new Error("Sheet is not set");
		}

		if (row > sheet.getPhysicalNumberOfRows()) {
			LOGGER.warn("Row out of Index");
			return null;
		}

		XSSFRow newRow = sheet.getRow(row);
		for (int i = 0; i <= newRow.getLastCellNum(); i++) {
			try {
				rowList.add(newRow.getCell(i)
						.getStringCellValue());
			} catch (NullPointerException e) {
				rowList.add(null);
			}
		}

		return rowList;
	}

	@Override
	public final List<Integer> getIntegerRow(final int row) {
		List<Integer> rowList = new ArrayList<Integer>();
		if (this.sheet == null) {
			throw new Error("Sheet is not set");
		}

		if (row > sheet.getPhysicalNumberOfRows()) {
			LOGGER.warn("Row out of Index");
			return null;
		}

		XSSFRow newRow = sheet.getRow(row);
		for (int i = 0; i <= newRow.getLastCellNum(); i++) {
			try {
				Double temp = newRow.getCell(i)
						.getNumericCellValue();
				rowList.add(temp.intValue());
			} catch (NullPointerException e) {
				rowList.add(-1);
			}
		}

		return rowList;
	}

	@Override
	public final String getStringCell(final int row, final int column) {
		if (this.sheet == null) {
			throw new Error("Sheet is not set");
		}

		if (row > sheet.getLastRowNum()) {
			LOGGER.warn("Row out of Index");
			return null;
		}

		XSSFRow newRow = sheet.getRow(row);
		if (newRow == null) {
			return null;
		}

		if (column >= newRow.getLastCellNum()) {
			LOGGER.warn("Columns out of Index");
			return null;
		}

		try {
			try {
				String cellValue = newRow.getCell(column)
						.getStringCellValue();
				return cellValue;
			} catch (IllegalStateException e) {
				LOGGER.warn("Cell at (" + row + ", " + column
						+ ") is not a string.");
				String cellValue = newRow.getCell(column)
						.getRawValue();
				return cellValue;
			}
		} catch (NullPointerException e) {
			LOGGER.error("Cell value is not set for "
					+ "row: " + row +" column "
					+ column);
			return null;
		}
	}

	@Override
	public final int getIntegerCell(final int row, final int column) {
		if (this.sheet == null) {
			throw new Error("Sheet is not set");
		}

		if (row > sheet.getLastRowNum()) {
			LOGGER.warn("Row out of Index");
			return -1;
		}

		XSSFRow newRow = sheet.getRow(row);
		if (column >= newRow.getLastCellNum()) {
			LOGGER.warn("Columns out of Index");
			return -1;
		}

		try {
			Double cellValue = newRow.getCell(column)
					.getNumericCellValue();
			return cellValue.intValue();
		} catch (NullPointerException e) {
			LOGGER.error("Cell value is not set for "
					+ "row: " + row + " column "
					+ column);
			return -1;
		}
	}

	@Override
	public final void setCell(final int row, final int column,
			final String cellValue){
		if (this.sheet == null) {
			throw new Error("Sheet is not set");
		}

		if (row > sheet.getLastRowNum()) {
			LOGGER.warn("Row out of Index and creating a row now");
			sheet.createRow(row);
		}

		XSSFRow newRow = sheet.getRow(row);
		try {
			newRow.getCell(column).setCellValue(cellValue);
		} catch (NullPointerException e) {
			LOGGER.warn("Cell is not created, Creating now");
			newRow.createCell(column);
			newRow.getCell(column).setCellValue(cellValue);
		}

		try {
			FileOutputStream fileOut = new FileOutputStream(
					this.fileName);
			this.workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} catch (IOException e) {
			LOGGER.error("Unable to write to file: "
					+ this.fileName, e);
		}
	}

	@Override
	public final void setAllCells(final String[][] inputArray) {
		int rows = inputArray.length;
		for (int i = 0; i < rows; i++) {
			int columns = inputArray[i].length;
			for (int j = 0; j < columns; j++) {
				setCell(i, j, inputArray[i][j]);
			}
		}
	}

}
