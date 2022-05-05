package io.github.easymodeling.modeler.helper;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.ModeledClass;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.ArrayList;

public class ModelWrapperFactory {

    private final ModeledClass modeledClass;

    private ModelWrapperFactory(Class<?> type) throws IllegalAccessException, InstantiationException {
        this.modeledClass = ModeledClass.class.newInstance();
        FieldUtils.writeField(modeledClass, "modelTypeName", ClassName.get(type), true);
        FieldUtils.writeField(modeledClass, "fields", new ArrayList<>(), true);
    }

    public static ModelWrapperFactory create(Class<?> type) {
        try {
            return new ModelWrapperFactory(type);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Cannot create @Model", e);
        }
    }

    public ModeledClass build() {
        return modeledClass;
    }
}
