package xyz.v2my.easymodeling.randomizer;

import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Random;

public abstract class RandomizerTest {

    @BeforeEach
    void baseSetUp() {
        try {
            final Field field = GenericRandomizer.class.getDeclaredField("random");
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            field.set(null, new Random(0));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
