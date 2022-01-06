package xyz.v2my.easymodeling.randomizer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Random;

public class GenericRandomizerDecorator {

    public static final void decorateSeed(long seed) {
        try {
            final Field field = GenericRandomizer.class.getDeclaredField("random");
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            field.set(null, new Random(seed));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
