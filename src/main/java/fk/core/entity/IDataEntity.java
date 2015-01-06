package fk.core.entity;

/**
 * Created by sankeerth.reddy on 17/12/14.
 */
public interface IDataEntity {

    /**
     * @return data of the local or global variable
     */
    String getData();

    /**
     * @return datatype of the variable either NUMBER or STRING
     */
    String getDataType();

    /**
     * @return json path of the variable in the data generated string
     */
    String getJsonPath();

}
