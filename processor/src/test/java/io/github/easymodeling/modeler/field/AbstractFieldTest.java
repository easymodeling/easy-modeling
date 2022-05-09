package io.github.easymodeling.modeler.field;

import com.squareup.javapoet.TypeName;
import io.github.easymodeling.modeler.FieldCustomization;

public class AbstractFieldTest {

    public static final String CLASS_NAME = "packageName.className";

    public static final String FIELD_NAME = "fieldName";

    public static final String QUALIFIED_NAME = CLASS_NAME + "#" + FIELD_NAME;

    protected FieldCustomization fieldCustomization;

    protected TypeName typeName;

    protected ModelField modelField;

    protected String $(Class<?> clazz) {
        return clazz.getCanonicalName();
    }
}
