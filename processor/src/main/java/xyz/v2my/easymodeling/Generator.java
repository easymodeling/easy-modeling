package xyz.v2my.easymodeling;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Generator {

    protected final Elements elementUtils;

    protected final Filer filer;

    public Generator(Elements elementUtils, Filer filer) {
        this.elementUtils = elementUtils;
        this.filer = filer;
    }

    public static final String BUILDER_TYPE_NAME_TEMPLATE = "EM%sBuilder";

    public abstract void generate(Element easyModelingConfig) throws ProcessingException;

    protected List<String> classOf(Element easyModelingConfig) {
        final Builder builder = easyModelingConfig.getAnnotation(Builder.class);
        try {
            return Arrays.stream(builder.classes()).map(Class::getCanonicalName).collect(Collectors.toList());
        } catch (MirroredTypesException mte) {
            return mte.getTypeMirrors().stream().map(this::typeMirrorName).collect(Collectors.toList());
        }
    }

    protected String typeMirrorName(TypeMirror typeMirror) {
        final DeclaredType declaredType = (DeclaredType) typeMirror;
        final TypeElement typeElement = (TypeElement) declaredType.asElement();
        return typeElement.getQualifiedName().toString();
    }
}
