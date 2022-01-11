package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.field.string.StringField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.collection.ListRandomizer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ListFieldTest extends FieldTest {

    private StringField stringField;

    @BeforeEach
    @Override
    protected void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).string("").min(3.).max(9.).minSize(20).maxSize(50).build();
        this.stringField = new StringField(fieldWrapper);
        typeName = ParameterizedTypeName.get(List.class, String.class);
        modelField = new ListField(fieldWrapper, this.stringField);
    }

    @Test
    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer.toString())
                .isEqualTo("new " + $(ListRandomizer.class) + "<>(" + stringField.initializer() + ", 20, 50)");
    }
}
