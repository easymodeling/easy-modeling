package xyz.v2my.easymodeling.factory.field.string;

import com.google.common.collect.Sets;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.Import;
import xyz.v2my.easymodeling.factory.field.AbstractField;
import xyz.v2my.easymodeling.randomizer.StringRandomizer;

import java.util.Set;

public class StringField extends AbstractField {

    public StringField(TypeName type, String name) {
        super(type, name);
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("aString()");
    }

    @Override
    public Set<Import> imports() {
        return Sets.newHashSet(new Import(StringRandomizer.class, "aString"));
    }
}
