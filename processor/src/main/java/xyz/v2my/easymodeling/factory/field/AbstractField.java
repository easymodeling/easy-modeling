package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractField {

    private static final AtomicLong FIELD_COUNTER = new AtomicLong(0);

    protected TypeName type;

    protected String name;

    protected String identifier;

    protected FieldWrapper field;

    protected AbstractField() {
    }

    public String identity() {
        return String.format("%s_%s", name, identifier);
    }

    protected AbstractField(TypeName type, FieldWrapper field) {
        this.type = type;
        this.name = field.name();
        this.field = field;
        this.identifier = String.valueOf(FIELD_COUNTER.getAndIncrement());
    }
}
