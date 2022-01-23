package xyz.v2my.easymodeling.factory.helper;

import com.squareup.javapoet.ClassName;
import org.apache.commons.lang3.reflect.FieldUtils;
import xyz.v2my.easymodeling.factory.FieldPattern;
import xyz.v2my.easymodeling.factory.ModelWrapper;

import java.util.ArrayList;

public class ModelAnnotationFactory {

    private final ModelWrapper modelWrapper;

    private ModelAnnotationFactory(Class<?> type) throws IllegalAccessException, InstantiationException {
        this.modelWrapper = ModelWrapper.class.newInstance();
        FieldUtils.writeField(modelWrapper, "modelTypeName", ClassName.get(type), true);
        FieldUtils.writeField(modelWrapper, "fieldPatterns", new ArrayList<>(), true);
        FieldUtils.writeField(modelWrapper, "fieldDeclarations", new ArrayList<>(), true);
    }

    public static ModelAnnotationFactory create(Class<?> type) {
        try {
            return new ModelAnnotationFactory(type);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Cannot create @Model", e);
        }
    }

    public ModelAnnotationFactory annotatedField(FieldPattern fieldPattern) {
        modelWrapper.getFields().add(fieldPattern);
        return this;
    }

    public ModelWrapper build() {
        return modelWrapper;
    }
}
