package xyz.v2my.easymodeling;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Generator {

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
