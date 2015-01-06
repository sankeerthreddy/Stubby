package fk.utilities;

import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Utility to execute commands on command prompt.
 * @author sankeerth.reddy
 */
public class CommandPromptUtility {
	
	/**
	 * Root logger instance.
	 */
	private static Logger LOGGER = Logger.getLogger(CommandPromptUtility.class);

	/**
	 * @param command Command to be executed.
	 */
	public static Process startProcess(String command) {
		Process currProcess = null;
		try {
			LOGGER.info("Executing command: " + command);
			currProcess = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			LOGGER.error("Error executing command: " + command, e);
		}
		return currProcess;
	}

	/**
	 * Kills the process
	 * @param process
	 */
	public static void killProcess(Process process){
		if(process != null) {
			process.destroy();
		}
		else
			LOGGER.warn("No such process exists");
	}

	/**
	 * @param command Command to be executed.
	 * @return Return code of the command.
	 */
	public static int getExitValueOfCommand(String command) {
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(command);
			process.waitFor();
		} catch (IOException e) {
			LOGGER.error("Error executing command: " + command, e);
			return -1;
		} catch (InterruptedException e) {
			LOGGER.error("Error executing command: " + command, e);
			return -1;
		}

       return process.exitValue();
	}
}
