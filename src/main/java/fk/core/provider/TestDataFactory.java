package fk.core.provider;

import fk.core.entity.entities.SuperEntity;

import java.lang.reflect.Field;
import java.util.Iterator;

/**
 * Created by sankeerth.reddy on 07/01/15.
 */
public class TestDataFactory {
    public static void main(String[] args) {
        Class superClass = SuperEntity.class;
        String className = superClass.getSimpleName();
        System.out.println(className);
        Field[] fields = superClass.getDeclaredFields();

        for(Field field : fields ){
            System.out.println(field.getType().toString());
        }
        }

    }

