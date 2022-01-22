package xyz.v2my.easymodeling.factory.field.datetime;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import org.junit.jupiter.api.BeforeEach;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.datetime.LocalDateRandomizer;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class LocalDateFieldTest extends FieldTest {

    @Override
    @BeforeEach
    protected void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).after("1991-01-23T00:00:00Z").before("1991-02-22T00:00:00Z").build();
        typeName = ClassName.get(LocalDate.class);
        modelField = new LocalDateField().create(fieldWrapper);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer).isEqualTo(CodeBlock.of("new " + $(LocalDateRandomizer.class) + "(664588800000L, 667180800000L)"));
    }
}
