package fk.core.entity.entities;

import lombok.Data;

/**
 * Created by sankeerth.reddy on 06/01/15.
 */
@Data
public class SuperEntity {

    public SuperEntity(){};

    private String variable1;

    private double variable2;

    private int variable3;

    private String variable4;

    @Override
    public String toString() {
        return "SuperEntity{" +
                "variable1='" + variable1 + '\'' +
                ", variable2=" + variable2 +
                ", variable3=" + variable3 +
                ", variable4='" + variable4 + '\'' +
                '}';
    }
}
