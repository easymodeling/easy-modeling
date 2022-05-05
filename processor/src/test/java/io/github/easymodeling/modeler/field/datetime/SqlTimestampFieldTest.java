package io.github.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.field.FieldTest;
import io.github.easymodeling.modeler.helper.FieldPatternFactory;
import io.github.easymodeling.randomizer.datetime.SqlTimestampRandomizer;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

class SqlTimestampFieldTest extends FieldTest {

    @Override
    @BeforeEach
    protected void setUp() {
        fieldCustomization = FieldPatternFactory.one(FIELD_NAME).after("1991-01-23T00:00:00Z").before("1991-02-22T00:00:00Z").build();
        typeName = ClassName.get(Timestamp.class);
        modelField = new SqlTimestampField().create(fieldCustomization);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer)
                .hasToString("new " + $(SqlTimestampRandomizer.class) + "(664588800000L, 667180800000L)");
    }
}
