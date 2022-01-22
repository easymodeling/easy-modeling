package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.field.Container;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.factory.field.OptionalField;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.factory.field.UnknownField;
import xyz.v2my.easymodeling.factory.field.array.ArrayField;
import xyz.v2my.easymodeling.factory.field.array.PrimitiveArrayField;
import xyz.v2my.easymodeling.factory.field.collection.ArrayListField;
import xyz.v2my.easymodeling.factory.field.collection.HashMapField;
import xyz.v2my.easymodeling.factory.field.collection.HashSetField;
import xyz.v2my.easymodeling.factory.field.collection.LinkedListField;
import xyz.v2my.easymodeling.factory.field.collection.ListField;
import xyz.v2my.easymodeling.factory.field.collection.MapField;
import xyz.v2my.easymodeling.factory.field.collection.SetField;
import xyz.v2my.easymodeling.factory.field.collection.TreeMapField;
import xyz.v2my.easymodeling.factory.field.collection.TreeSetField;
import xyz.v2my.easymodeling.factory.field.datetime.InstantField;
import xyz.v2my.easymodeling.factory.field.number.ByteField;
import xyz.v2my.easymodeling.factory.field.number.DoubleField;
import xyz.v2my.easymodeling.factory.field.number.FloatField;
import xyz.v2my.easymodeling.factory.field.number.IntegerField;
import xyz.v2my.easymodeling.factory.field.number.LongField;
import xyz.v2my.easymodeling.factory.field.number.ShortField;
import xyz.v2my.easymodeling.factory.field.primitive.BooleanField;
import xyz.v2my.easymodeling.factory.field.primitive.CharField;
import xyz.v2my.easymodeling.factory.field.stream.IntStreamField;
import xyz.v2my.easymodeling.factory.field.stream.StreamField;
import xyz.v2my.easymodeling.factory.field.string.StringBuilderField;
import xyz.v2my.easymodeling.factory.field.string.StringField;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelFieldProvider {

    private static final ModelField[] MODEL_FIELDS = {
            // primitive
            new ByteField(),
            new ShortField(),
            new IntegerField(),
            new LongField(),
            new FloatField(),
            new DoubleField(),
            new BooleanField(),
            new CharField(),

            // string
            new StringField(),
            new StringBuilderField(),

            // datetime
            new InstantField(),

            // containers
            new OptionalField(),

            // collection
            new ListField(),
            new ArrayListField(),
            new LinkedListField(),
            new SetField(),
            new HashSetField(),
            new TreeSetField(),
            new MapField(),
            new HashMapField(),
            new TreeMapField(),

            // stream
            new StreamField(),
            new IntStreamField(),
    };

    private static final Map<TypeName, PlainField<?>> PLAIN_FIELDS = Arrays.stream(MODEL_FIELDS)
            .filter(PlainField.class::isInstance).map(f -> (PlainField<?>) f).collect(Collectors.toMap(ModelField::type, f -> f));

    private static final Map<TypeName, Container> CONTAINERS = Arrays.stream(MODEL_FIELDS)
            .filter(Container.class::isInstance).map(Container.class::cast).collect(Collectors.toMap(ModelField::type, f -> f));

    public ModelField provide(TypeName type, FieldWrapper field) {
        try {
            return findField(type, field);
        } catch (FieldNotSupportedException e) {
            return new UnknownField(type, field);
        }
    }

    private ModelField findField(TypeName type, FieldWrapper field) {
        if (type instanceof ArrayTypeName) {
            return arrayField((ArrayTypeName) type, field);
        }
        if (type instanceof ParameterizedTypeName) {
            return containerField((ParameterizedTypeName) type, field);
        }
        return plainField(type, field);
    }

    private ModelField nestedField(TypeName type, FieldWrapper field) {
        final ModelField nestedField = findField(type, field);
        if (nestedField instanceof PrimitiveArrayField) {
            throw new FieldNotSupportedException();
        }
        return nestedField;
    }

    private Container arrayField(ArrayTypeName type, FieldWrapper field) {
        final TypeName rawType = rawType(type);
        if (rawType.isPrimitive()) {
            return new PrimitiveArrayField(type, field, plainField(rawType, field));
        }
        return new ArrayField(type, field, nestedField(type.componentType, field));
    }

    private Container containerField(ParameterizedTypeName parameterizedTypeName, FieldWrapper field) {
        final ClassName rawType = parameterizedTypeName.rawType;
        final ModelField[] modelFields = parameterizedTypeName.typeArguments.stream()
                .map(type -> nestedField(type, field))
                .toArray(ModelField[]::new);
        try {
            return Optional.ofNullable(CONTAINERS.get(rawType)).orElseThrow(FieldNotSupportedException::new)
                    .create(field, modelFields);
        } catch (ArrayIndexOutOfBoundsException obe) {
            throw new FieldNotSupportedException();
        }
    }

    private PlainField<?> plainField(TypeName type, FieldWrapper field) {
        TypeName boxedType = type.isPrimitive() ? type.box() : type;
        return Optional.ofNullable(PLAIN_FIELDS.get(boxedType))
                .orElseThrow(FieldNotSupportedException::new)
                .create(field);
    }

    private TypeName rawType(TypeName type) {
        if (type instanceof ArrayTypeName) {
            return rawType(((ArrayTypeName) type).componentType);
        } else {
            return type;
        }
    }
}
