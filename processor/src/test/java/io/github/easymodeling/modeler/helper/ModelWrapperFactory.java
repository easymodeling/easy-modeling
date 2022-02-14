package io.github.easymodeling.modeler.helper;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.FieldPattern;
import io.github.easymodeling.modeler.ModelWrapper;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.util.ArrayList;

public class ModelWrapperFactory {

    private final ModelWrapper modelWrapper;

    private ModelWrapperFactory(Class<?> type) throws IllegalAccessException, InstantiationException {
        this.modelWrapper = ModelWrapper.class.newInstance();
        FieldUtils.writeField(modelWrapper, "modelTypeName", ClassName.get(type), true);
        FieldUtils.writeField(modelWrapper, "fieldPatterns", new ArrayList<>(), true);
        FieldUtils.writeField(modelWrapper, "fieldDeclarations", new ArrayList<>(), true);
    }

    public static ModelWrapperFactory create(Class<?> type) {
        try {
            return new ModelWrapperFactory(type);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Cannot create @Model", e);
        }
    }

    public ModelWrapperFactory annotatedField(FieldPattern fieldPattern) {
        modelWrapper.getFields().add(fieldPattern);
        return this;
    }

    public ModelWrapper build() {
        return modelWrapper;
    }
}
