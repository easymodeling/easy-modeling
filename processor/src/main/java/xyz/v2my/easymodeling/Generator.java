package xyz.v2my.easymodeling;

import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public abstract class Generator {

    protected final Elements elementUtils;

    public Generator(Elements elementUtils) {
        this.elementUtils = elementUtils;
    }

    public abstract void generate(TypeElement clazz, TypeSpec.Builder factoryBuilder);

}
