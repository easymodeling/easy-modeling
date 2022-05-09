package io.github.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.field.ModelFieldTest;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.datetime.SqlDateRandomizer;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;

class SqlDateFieldTest extends ModelFieldTest {

    @Override
    @BeforeEach
    protected void setUp() {
        fieldCustomization = FieldPatternFactory.one(FIELD_NAME).after("1991-01-23T00:00:00Z").before("1991-02-22T00:00:00Z").build();
        typeName = ClassName.get(Date.class);
        modelField = new SqlDateField().create(fieldCustomization);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer)
                .hasToString("new " + $(SqlDateRandomizer.class) + "(664588800000L, 667180800000L)");
    }
}
