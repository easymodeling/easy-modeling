package xyz.v2my.easymodeling;

import org.jeasy.random.EasyRandom;

public class EasyModeling {

    public static <T> T generate(Class<T> clazz) {
        return new EasyRandom().nextObject(clazz);
    }
}
