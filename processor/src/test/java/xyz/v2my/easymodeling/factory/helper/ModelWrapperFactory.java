package xyz.v2my.easymodeling.factory.helper;

import com.squareup.javapoet.ClassName;
import org.apache.commons.lang3.reflect.FieldUtils;
import xyz.v2my.easymodeling.factory.EnclosedField;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.ModelWrapper;

import java.util.ArrayList;

public class ModelWrapperFactory {

    private final ModelWrapper modelWrapper;

    private ModelWrapperFactory(Class<?> type) throws IllegalAccessException, InstantiationException {
        this.modelWrapper = ModelWrapper.class.newInstance();
        FieldUtils.writeField(modelWrapper, "modelTypeName", ClassName.get(type), true);
        FieldUtils.writeField(modelWrapper, "annotatedFields", new ArrayList<>(), true);
        FieldUtils.writeField(modelWrapper, "enclosedFields", new ArrayList<>(), true);
    }

    public static ModelWrapperFactory create(Class<?> type) {
        try {
            return new ModelWrapperFactory(type);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Cannot create @Model", e);
        }
    }

    public ModelWrapperFactory annotatedField(FieldWrapper fieldWrapper) {
        modelWrapper.getFields().add(fieldWrapper);
        return this;
    }

    public ModelWrapperFactory enclosedField(EnclosedField element) {
        modelWrapper.getEnclosedFields().add(element);
        return this;
    }

    public ModelWrapper build() {
        return modelWrapper;
    }
}
