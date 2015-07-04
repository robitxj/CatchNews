package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ScriptConfig {
	public static final String projectName = "CatchNews";
	private static final String prefix = ScriptConfig.class.getCanonicalName();
	private static final String defaultConfigFile = "/script.properties";
	private static Properties props;
	
	static {
		init();
	}

	// /////////////////////////////////////////

	private ScriptConfig() {
		// should not call
	}

	private static void init() {
		props = loadConfig();
	}


	public static Properties loadConfig() {
		final Properties p = new Properties();
		loadDefaultConfig(p);
		return p;
	}

	private static void loadDefaultConfig(final Properties props) {
		final InputStream input = ScriptConfig.class
				.getResourceAsStream(defaultConfigFile);
		if (input == null) {
			System.err.println("default Config File not found: "
					+ defaultConfigFile);
			System.exit(1);
		}

		try {
			props.load(input);
		} catch (final IOException e) {
			e.printStackTrace();
			System.err.println("load default Config File error: "
					+ e.getMessage());
			System.exit(1);
		} finally {
			try {
				input.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 *
	 * @param key
	 * @return trimed property of the key, defaultValue for null
	 */
	public static String getProperty(final String key, final String defaultValue) {
		final String value = props.getProperty(key);
		if (value == null) {
			return defaultValue;
		}
		return value.trim();
	}

	/**
	 *
	 * @param key
	 * @return trimed property of the key, "" for null
	 */
	public static String getProperty(final String key) {
		return getProperty(key, "");
	}

	/**
	 *
	 * @param key
	 * @param delimiter
	 * @return string[] properties, split using delimiter
	 */
	public static String[] getProperties(final String key,
			final String delimiter) {
		final String value = getProperty(key);
		final String[] values = value.split(delimiter);
		for (int i = 0; i < values.length; ++i) {
			values[i] = values[i].trim();
		}
		return values;
	}

	public static int getIntProperty(final String key) {
		final String value = getProperty(key);
		if (value.length() <= 0) {
			return 0;
		}
		return Integer.parseInt(value);
	}

	public static boolean getBooleanProperty(final String key) {
		final String value = getProperty(key);
		if (value.length() <= 0) {
			return false;
		}
		return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes")
				|| value.equalsIgnoreCase("on");
	}

	public static String getProjectName() {
		return projectName;
	}

	
	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		System.out.println(ScriptConfig.loadConfig().get("Email"));
	}
}
