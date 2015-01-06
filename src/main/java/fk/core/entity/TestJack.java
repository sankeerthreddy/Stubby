package fk.core.entity;

import fk.core.entity.entities.SampleEntity;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

/**
 * Created by sankeerth.reddy on 17/12/14.
 */
public class TestJack {
    public static void main(String[] args) {
        SampleEntity sampleEntity = new SampleEntity(123, "valueString1", "valueString2", 456);
        System.out.println(sampleEntity.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println(objectMapper.writeValueAsString(sampleEntity));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
