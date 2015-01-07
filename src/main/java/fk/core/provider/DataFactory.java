package fk.core.provider;

import fk.core.entity.IDataEntity;
import fk.core.provider.impl.*;
import fk.references.AutomationReferences;
import fk.utilities.ExcelSheetUitility;
import fk.utilities.IExcelSheetDriver;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Factory class for data providers and data entities
 * which initializes them reading configurations from
 * excel file and maintaining their instances.
 * @author sankeerth.reddy
 */
public class DataFactory {

	/**
	 * Private constructor.
	 */
	private DataFactory() { }

	/**
	 * Map of variable name to data provider.
	 */
	private static Map<String, IDataProvider> dataProviders;

	/**
	 * Map of variable name to data entity.
	 */
	private static Map<String, IDataEntity> dataEntities;

	/**
	 * Map of variable name to last generated value.
	 */
	private static Map<String, String> values;

	/**
	 * List of global variable names.
	 */
	private static Set<String> globalEntities;

	/**
	 * Instance of root logger.
	 */
	private static final Logger LOGGER =
			Logger.getLogger(DataFactory.class);

	/**
	 * Initializes the data providers.
	 */
	public static void init() {
		IExcelSheetDriver excel = new ExcelSheetUitility(
				AutomationReferences.FILE_EXCEL_CONFIG);
		excel.setSheet(AutomationReferences.SHEET_VARIABLES);

		dataProviders = new HashMap<String, IDataProvider>();
		dataEntities = new HashMap<String, IDataEntity>();
		values = new HashMap<String, String>();
		globalEntities = new HashSet<String>();

		int row = 1;
		while (excel.getStringCell(row, 0) != null) {
			String name = excel.getStringCell(row, 0);
			String dataType = excel.getStringCell(row, 1);
			String jsonPath = excel.getStringCell(row, 2);
			String providerType = excel.getStringCell(row, 3);
			String expression = excel.getStringCell(row, 4);
			String isGlobal = excel.getStringCell(row, 5);

			if (isGlobal.contains("Yes")) {
				globalEntities.add(name);
			}

			IDataProvider provider = instantiateDataProvider(
					providerType, expression);
			if (dataProviders != null) {
				dataProviders.put(name, provider);
			}

			/*
			if (jsonPath != null) {
				if (jsonPath.length() != 0) {
					IDataEntity entity = new ProviderDataEntity(
							name, dataType, jsonPath);
					dataEntities.put(name, entity);
				}
			}
			*/

			row++;
		}

		LOGGER.info("Data providers have been initialized.");
	}

	/**
	 * Initializes global variables and caches them.
	 */
	public static void initGlobalVariables() {
		Iterator<String> iter = globalEntities.iterator();
		while (iter.hasNext()) {
			String curr = iter.next();
			getGeneratedData(curr);
		}
	}

	/**
	 * @param name Name of the data entity.
	 * @return Data entity instance.
	 */
	public static IDataEntity getDataEntity(final String name) {
		if (dataEntities == null) {
			LOGGER.error("Data factory hasn't been initialized.");
			return null;
		}

		if (dataEntities.containsKey(name)) {
			return dataEntities.get(name);
		}

		LOGGER.warn("Data entity not found: " + name);
		return null;
	}

	/**
	 * @param name Name of the data provider.
	 * @return Data provider instance.
	 */
	public static IDataProvider getDataProvider(final String name) {
		if (dataProviders == null) {
			LOGGER.error("Data factory hasn't been initialized.");
			return null;
		}

		if (dataProviders.containsKey(name)) {
			return dataProviders.get(name);
		}

		LOGGER.warn("Data provider not found: " + name);
		return null;
	}

	/**
	 * @param name Name of variable
	 * @return Generated data for the variable.
	 */
	public static String getGeneratedData(final String name) {
		if (dataProviders == null) {
			LOGGER.error("Data factory hasn't been intialized.");
			return null;
		}

		if (!dataProviders.containsKey(name)) {
			LOGGER.error("Data provider not found: " + name);
			return null;
		}

		String value = dataProviders.get(name).getData();
		values.put(name, value);

		return value;
	}

	/**
	 * @param name Name of the variable.
	 * @return Last generated data cached by factory class.
	 */
	public static String getLastGeneratedData(final String name) {
		if (values == null) {
			LOGGER.error("Data factory hasn't been initialized.");
			return null;
		}

		if (!values.containsKey(name)) {
			LOGGER.error("Variable not found: " + name);
			return null;
		}

		return values.get(name);
	}

	/**
	 * Updates the context for a single variable.
	 * @param name Name of the variable.
	 * @param value Value of the variable.
	 */
	public static void updateContext(final String name,
			final String value) {
		values.put(name, value);
	}

	/**
	 * Resets context in all data providers.
	 */
	public static void resetContext() {
		values = new HashMap<String, String>();

		Iterator<IDataProvider> iter = dataProviders
				.values().iterator();
		while (iter.hasNext()) {
			IDataProvider provider = iter.next();
			provider.resetContext();
		}

		LOGGER.debug("All contexts have been reset.");
	}

	/**
	 * @param type Type of the data provider.
	 * @param expression Expression for the data provider, if applicable.
	 * @return Data provider instance.
	 */
	private static IDataProvider instantiateDataProvider(
			final String type, final String expression) {
		String dataType = type.toLowerCase();
		if (dataType.equals("timestamp")) {
			return new TimestampProvider(expression);
		} else if (dataType.equals("string"))
			return new RandomStringProvider(expression);
		else if (dataType.equals("array")) {
			return new ArrayProvider(expression);
		} else if (dataType.equals("constant")) {
			return new ConstantProvider(expression);
		} else if (dataType.equals("random")) {
			return new RandomNumberProvider(expression);
		} else if (dataType.equals("concatenated")) {
			return new ConcatenatedProvider(expression);
		} else if (dataType.equals("dependent")) {
			return new DependentProvider(expression);
		} else if (dataType.equals("date")) {
			return new DateProvider(expression);
		} else {
			LOGGER.warn("Unknown provider type: " + type
					+ ", ignoring.");
			return null;
		}
	}

}
