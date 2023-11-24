package vtp.crm.common.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelHelper {

//    @Autowired
//    ResourceLoader resourceLoader;

	public static final String DDMMYYYY_FORMAT = "dd/mm/yyyy";

	public static final String EXCEL_EXTEND_FILE = ".xlsx";

	/**
	 * openExcel: open excel file from resource
	 *
	 * @param resourceLoader
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static Workbook openResourceExcel(String pathInResource) throws IOException {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream(pathInResource);
		assert is != null;
		return new XSSFWorkbook(is);
	}

	/**
	 * openExcel: open excel file from resource
	 *
	 * @param file as InputStream
	 * @return
	 * @throws IOException
	 */
	public static Workbook openExcel(InputStream file) throws IOException {
		return new XSSFWorkbook(file);
	}

	/**
	 * Open excel file from folder
	 *
	 * @param fileLocation
	 * @return
	 * @throws IOException
	 */
	public static Workbook openExcel(String fileLocation) {
		try {
			FileInputStream file = new FileInputStream(new File(fileLocation));
			return new XSSFWorkbook(file);
		} catch (Exception ex) {
			throw new RuntimeException("FILE_INVALID");
		}
	}

	/**
	 * Save excel file
	 *
	 * @param workbook
	 * @param filePath
	 * @throws IOException
	 */
	public static void saveExcel(Workbook workbook, String filePath) throws IOException {
		Paths.get(filePath).getParent().toFile().mkdirs();
		FileOutputStream out = new FileOutputStream(filePath);
		workbook.write(out);
		out.close();
	}

	/**
	 * createStyle: <p> Create cell style</p>
	 *
	 * @param workbook
	 * @param isBoldStyle
	 * @param cellColor
	 * @return
	 */
	public static CellStyle createStyleBoldAndBackground(Workbook workbook, boolean isBoldStyle, IndexedColors cellColor) {
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBold(isBoldStyle);
		style.setFont(font);
		style.setFillBackgroundColor(cellColor.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		return style;
	}

	public static CellStyle createStyleBorder(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		return style;
	}

	/**
	 * Text alignment center
	 *
	 * @param workbook
	 * @return
	 */
	public static CellStyle createCellStyleTextAlignmentCenter(Workbook workbook) {
		// text align center
		CellStyle styleTextAlign = ExcelHelper.createStyleBorder(workbook);
		styleTextAlign.setAlignment(HorizontalAlignment.CENTER);
		return styleTextAlign;
	}

	/**
	 * createCell:
	 * <p>create new cell of row</p>
	 *
	 * @param row
	 * @param cellType
	 * @param position
	 * @param cellValue
	 * @param style
	 * @return
	 */
	public static Cell createCell(Row row, CellType cellType, int position, String cellValue, CellStyle style) {
		//Cell cell = row.createCell(position, CellType.STRING);
		Cell cell = row.createCell(position, cellType);
		cell.setCellValue(cellValue);
		cell.setCellStyle(style);
		return cell;
	}

	/**
	 * Text alignment center
	 *
	 * @param workbook
	 * @return
	 */
	public static CellStyle createCellStyleTextRed(Workbook workbook) {
		// text align center
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
		style.setFont(font);
		style.setAlignment(HorizontalAlignment.LEFT);
		return style;
	}

	/**
	 * generateListBox: generate select box to cell
	 *
	 * @param workbook       current workbook
	 * @param activeSheet    main sheet
	 * @param sheetDataName  data sheet
	 * @param rangeDataStart start cell data of list; eg: $A$1
	 * @param rangeDataEnd   End cell data of list; eg: $A$5
	 * @param formulaName    Naming of formula
	 * @param firstRow       Start index of row apply list
	 * @param lastRow        End index of row apply list
	 * @param firstCol       Start column apply list
	 * @param lastCol        End column apply list
	 */
	public static void generateListBox(Workbook workbook, Sheet activeSheet, String sheetDataName,
									   String rangeDataStart, String rangeDataEnd, String formulaName,
									   int firstRow, int lastRow, int firstCol, int lastCol) {
		Name namedCell = workbook.createName();
		namedCell.setNameName("SourceList" + formulaName);
		String reference = sheetDataName + "!" +
				rangeDataStart +
				":" +
				rangeDataEnd;
		namedCell.setRefersToFormula(reference);

		DataValidationHelper dvHelper = activeSheet.getDataValidationHelper();
		DataValidationConstraint dvConstraint = dvHelper.createFormulaListConstraint("SourceList" + formulaName);
		//CellRangeAddressList addressList = new CellRangeAddressList(1, 1, 1, 1);
		CellRangeAddressList addressList = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
		DataValidation validation = dvHelper.createValidation(dvConstraint, addressList);
		validation.setShowErrorBox(true);
		activeSheet.addValidationData(validation);
	}

	/**
	 * cellStyleDate
	 *
	 * @param workbook
	 * @return
	 */
	public static CellStyle cellStyleDate(Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		CreationHelper createHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(
				createHelper.createDataFormat().getFormat(DDMMYYYY_FORMAT));
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		return cellStyle;
	}

	/**
	 * readExcel
	 *
	 * @param fileLocation
	 * @return map of data
	 * @throws IOException
	 */
	public static Map<Integer, List<String>> readExcel(String fileLocation, String sheetName) throws Exception {
		Map<Integer, List<String>> data = new HashMap<>();
		FileInputStream file = new FileInputStream(new File(fileLocation));
		Workbook workbook = new XSSFWorkbook(file);
		Sheet sheet = workbook.getSheet(sheetName); //.getSheetAt(0);
		int i = 0;
		List<String> rowData;
		for (Row row : sheet) {
			rowData = new ArrayList<>();
			for (Cell cell : row) {
				switch (cell.getCellType()) {
					case STRING:
						rowData.add(cell.getRichStringCellValue()
								.getString());
						break;
					case NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							rowData.add(String.valueOf(cell.getDateCellValue()));
						} else {
							rowData.add(String.valueOf((int) cell.getNumericCellValue()));
						}
						break;
					case BOOLEAN:
						rowData.add(String.valueOf(cell.getBooleanCellValue()));
						break;
					case FORMULA:
						switch (cell.getCachedFormulaResultType()) {
							case BOOLEAN:
								rowData.add(String.valueOf(cell.getBooleanCellValue()));
								break;
							case NUMERIC:
								if (DateUtil.isCellDateFormatted(cell)) {
									rowData.add(String.valueOf(cell.getDateCellValue()));
								} else {
									rowData.add(String.valueOf((int) cell.getNumericCellValue()));
								}

								break;
							case STRING:
								rowData.add(cell.getRichStringCellValue()
										.getString());

								//data.get(i)
								//       .add(cell.getCellFormula() + "");
								break;
							default:
								rowData.add(null);
						}
						break;
					default:
						rowData.add(null);
				}
			}
			if (!ObjectUtils.isEmpty(rowData)) {
				data.put(i, rowData);
			}
			i++;
		}
		if (workbook != null) {
			workbook.close();
		}
		return data;
	}

	public static void addComment(Workbook workbook, Sheet sheet, int rowIdx, int colIdx,
								  String author, String commentText) {
		CreationHelper factory = workbook.getCreationHelper();
		Cell cell = getCell(sheet, rowIdx, colIdx);

		ClientAnchor anchor = factory.createClientAnchor();
		anchor.setCol1(cell.getColumnIndex() + 1);
		anchor.setCol2(cell.getColumnIndex() + 3);
		anchor.setRow1(rowIdx + 1);
		anchor.setRow2(rowIdx + 5);

		Drawing drawing = sheet.createDrawingPatriarch();
		Comment comment = drawing.createCellComment(anchor);
		comment.setString(factory.createRichTextString(commentText));
		comment.setAuthor(author);
		cell.setCellComment(comment);
	}

	public static Cell getCell(Sheet sheet, int rowIdx, int colIdx) {
		Row row = sheet.getRow(rowIdx);
		if (ObjectUtils.isEmpty(row)) {
			row = sheet.createRow(rowIdx);
		}
		Cell cell = row.getCell(colIdx);
		if (ObjectUtils.isEmpty(cell)) {
			cell = row.createCell(colIdx);
		}
		return cell;
	}

	public static void removeRow(Sheet sheet, int rowIndex) {
		int lastRowNum = sheet.getLastRowNum();
		if (rowIndex >= 0 && rowIndex < lastRowNum) {
			sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
		}
		if (rowIndex == lastRowNum) {
			Row removingRow = sheet.getRow(rowIndex);
			if (removingRow != null) {
				sheet.removeRow(removingRow);
			}
		}
	}

	public static void cloneRow(Sheet sheet, int rowIdxToClone, int rowIdxToInsert) {

		if (sheet.getRow(rowIdxToInsert) != null) {
			sheet.shiftRows(rowIdxToInsert, sheet.getLastRowNum(), 1);
		}

		Row newRow = sheet.createRow(rowIdxToInsert);

		// Get the row that you want to clone
		Row rowToClone = sheet.getRow(rowIdxToClone);

		// Loop through the cells in the original row and clone each one
		Workbook workbook = sheet.getWorkbook();
		for (int i = 0; i < rowToClone.getLastCellNum(); i++) {
			Cell oldCell = rowToClone.getCell(i);
			Cell newCell = newRow.createCell(i);
			if (oldCell != null) {
				CellStyle cellStyle = workbook.createCellStyle();
				cellStyle.cloneStyleFrom(oldCell.getCellStyle());
				newCell.setCellStyle(cellStyle);

				switch (oldCell.getCellType()) {
					case BOOLEAN -> newCell.setCellValue(oldCell.getBooleanCellValue());
					case NUMERIC -> newCell.setCellValue(oldCell.getNumericCellValue());
					case STRING -> newCell.setCellValue(oldCell.getStringCellValue());
					case FORMULA -> newCell.setCellFormula(oldCell.getCellFormula());
					default -> newCell.setCellValue("");
				}
			}
		}
	}

	public static Cell setCellValue(Sheet sheet, int row, int col, Object value) {
		Cell cell = getCell(sheet, row, col);
		setCellValue(cell, value);
		return cell;
	}

	public static void setCellValue(Cell cell, Object value) {
		if (cell == null) {
			return;
		}

		if (value instanceof String s) {
			cell.setCellValue(s);
		} else if (value instanceof Number n) {
			cell.setCellValue(Double.parseDouble(n.toString()));
		} else {
			cell.setCellValue(value == null ? null : value.toString());
		}
	}

	public static void setCellFormat(Sheet sheet, Cell cell, String pattern) {
		DataFormat format = sheet.getWorkbook().createDataFormat();
		cell.getCellStyle().setDataFormat(format.getFormat(pattern));
	}

	/**
	 * writeExcel
	 *
	 * @param sheetName
	 * @param limitedRow
	 * @param limitedColumn
	 * @throws IOException
	 */
	public void writeExcel(String sheetName, int limitedRow, int limitedColumn) throws IOException {
		Workbook workbook = new XSSFWorkbook();

		try {
			Sheet sheet = workbook.createSheet(sheetName);
			for (int i = 0; i < limitedRow; i++) {
				sheet.setColumnWidth(i, limitedRow);
				sheet.setColumnWidth(1, limitedRow);
			}

			Row header = sheet.createRow(0);

			CellStyle headerStyle = workbook.createCellStyle();

			headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
			headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			XSSFFont font = ((XSSFWorkbook) workbook).createFont();
			font.setFontName("Arial");
			font.setFontHeightInPoints((short) 16);
			font.setBold(true);
			headerStyle.setFont(font);

			Cell headerCell = header.createCell(0);
			headerCell.setCellValue("Name");
			headerCell.setCellStyle(headerStyle);

			headerCell = header.createCell(1);
			headerCell.setCellValue("Age");
			headerCell.setCellStyle(headerStyle);

			CellStyle style = workbook.createCellStyle();
			style.setWrapText(true);

			Row row = sheet.createRow(2);
			Cell cell = row.createCell(0);
			cell.setCellValue("John Smith");
			cell.setCellStyle(style);

			cell = row.createCell(1);
			cell.setCellValue(20);
			cell.setCellStyle(style);

			File currDir = new File(".");
			String path = currDir.getAbsolutePath();
			String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

			FileOutputStream outputStream = new FileOutputStream(fileLocation);
			workbook.write(outputStream);
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
	}

	public static boolean isRowEmpty(Row row) {
		if (row == null) {
			return true;
		}
		if (row.getLastCellNum() <= 0) {
			return true;
		}
		for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
			Cell cell = row.getCell(cellNum);
			if (!isCellEmpty(cell)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isCellEmpty(Cell cell) {
		return cell == null || cell.getCellType() == CellType.BLANK || StringUtils.isBlank(cell.toString());
	}

	private static Sheet removeRedundantTailRows(Sheet worksheet) {
		boolean nonBlankRowFound;
		short cellIdx;
		Row lastRow = null;
		Cell cell = null;

		while (true) {
			nonBlankRowFound = false;
			lastRow = worksheet.getRow(worksheet.getLastRowNum());
			cellIdx = lastRow.getFirstCellNum();
			while (cellIdx <= lastRow.getLastCellNum() && lastRow.getLastCellNum() != -1) {
				cell = lastRow.getCell(cellIdx);
				if (cell != null && cell.getCellType() != CellType.BLANK) {
					nonBlankRowFound = true;
					break;
				}
				cellIdx++;
			}
			if (nonBlankRowFound) {
				break;
			}
			worksheet.removeRow(lastRow);
		}
		return worksheet;
	}

}
