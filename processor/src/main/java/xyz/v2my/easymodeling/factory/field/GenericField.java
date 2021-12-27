package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.Import;

import java.util.HashSet;
import java.util.Set;

public class GenericField extends AbstractField {

    public GenericField(TypeName type, String name) {
        super(type, name);
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("null");
    }

    @Override
    public Set<Import> imports() {
        return new HashSet<>();
    }
}
