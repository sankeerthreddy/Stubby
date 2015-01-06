package fk.core.entity;

/**
 * Data types present in JSON response.
 * @author sankeerth.reddy
 */
public enum DataType {

    /**
     * String data type, which means it is to be
     * enclosed within double quotes in the JSON.
     */
    STRING,

    /**
     * Includes integers, long, double and all
     * types of numbers.
     */
    NUMBER;

    /**
     * @param dataType Data type specified as a string.
     * @return Data type enumeration object.
     */
    public static DataType parseDataType(final String dataType) {
        String s = dataType.toLowerCase();
        if (s.equals("string")) {
            return STRING;
        } else if (s.equals("number")) {
            return NUMBER;
        } else if (s.equals("integer")) {
            return NUMBER;
        } else if (s.equals("double")) {
            return NUMBER;
        } else if (s.equals("long")) {
            return NUMBER;
        } else {
            return STRING;
        }
    }

}
