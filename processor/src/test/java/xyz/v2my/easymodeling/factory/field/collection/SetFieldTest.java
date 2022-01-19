package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.v2my.easymodeling.factory.field.FieldTest;
import xyz.v2my.easymodeling.factory.field.string.StringField;
import xyz.v2my.easymodeling.factory.helper.FieldWrapperFactory;
import xyz.v2my.easymodeling.randomizer.collection.SetRandomizer;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SetFieldTest extends FieldTest {

    private StringField stringField;

    @BeforeEach
    @Override
    protected void setUp() {
        fieldWrapper = FieldWrapperFactory.one(FIELD_NAME).minSize(30).maxSize(50).build();
        typeName = ParameterizedTypeName.get(Set.class, String.class);
        stringField = new StringField().create(fieldWrapper);
        modelField = new SetField().create(fieldWrapper, stringField);
    }

    @Override
    protected void should_generate_initializer() {
        final CodeBlock initializer = modelField.initializer();

        assertThat(initializer.toString())
                .isEqualTo("new " + $(SetRandomizer.class) + "<>(" + stringField.initializer() + ", 30, 50)");
    }
}
