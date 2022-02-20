package io.github.easymodeling.modeler;

import com.squareup.javapoet.TypeName;
import io.github.easymodeling.modeler.field.Container;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.modeler.field.OptionalField;
import io.github.easymodeling.modeler.field.PlainField;
import io.github.easymodeling.modeler.field.collection.ArrayListField;
import io.github.easymodeling.modeler.field.collection.HashMapField;
import io.github.easymodeling.modeler.field.collection.HashSetField;
import io.github.easymodeling.modeler.field.collection.LinkedListField;
import io.github.easymodeling.modeler.field.collection.ListField;
import io.github.easymodeling.modeler.field.collection.MapField;
import io.github.easymodeling.modeler.field.collection.SetField;
import io.github.easymodeling.modeler.field.collection.TreeMapField;
import io.github.easymodeling.modeler.field.collection.TreeSetField;
import io.github.easymodeling.modeler.field.datetime.DateField;
import io.github.easymodeling.modeler.field.datetime.InstantField;
import io.github.easymodeling.modeler.field.datetime.LocalDateField;
import io.github.easymodeling.modeler.field.datetime.LocalDateTimeField;
import io.github.easymodeling.modeler.field.datetime.LocalTimeField;
import io.github.easymodeling.modeler.field.datetime.SqlDateField;
import io.github.easymodeling.modeler.field.datetime.SqlTimestampField;
import io.github.easymodeling.modeler.field.datetime.ZonedDateTimeField;
import io.github.easymodeling.modeler.field.number.ByteField;
import io.github.easymodeling.modeler.field.number.DoubleField;
import io.github.easymodeling.modeler.field.number.FloatField;
import io.github.easymodeling.modeler.field.number.IntegerField;
import io.github.easymodeling.modeler.field.number.LongField;
import io.github.easymodeling.modeler.field.number.ShortField;
import io.github.easymodeling.modeler.field.primitive.BooleanField;
import io.github.easymodeling.modeler.field.primitive.CharField;
import io.github.easymodeling.modeler.field.stream.DoubleStreamField;
import io.github.easymodeling.modeler.field.stream.IntStreamField;
import io.github.easymodeling.modeler.field.stream.LongStreamField;
import io.github.easymodeling.modeler.field.stream.StreamField;
import io.github.easymodeling.modeler.field.string.StringBuilderField;
import io.github.easymodeling.modeler.field.string.StringField;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelFieldRegistry {

    private ModelFieldRegistry() {
    }

    static Optional<PlainField<?>> plainField(TypeName type) {
        return Optional.ofNullable(ModelFieldRegistry.PLAIN_FIELDS.get(type.box()));
    }

    static Optional<Container> container(TypeName rawType) {
        return Optional.ofNullable(ModelFieldRegistry.CONTAINERS.get(rawType));
    }

    public static boolean basicTypeContains(String canonicalName) {
        return Arrays.stream(MODEL_FIELDS)
                .map(ModelField::type)
                .anyMatch(type -> type.toString().equals(canonicalName));
    }

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
            new LocalDateField(),
            new LocalTimeField(),
            new LocalDateTimeField(),
            new ZonedDateTimeField(),
            new DateField(),
            new SqlDateField(),
            new SqlTimestampField(),

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
            new LongStreamField(),
            new DoubleStreamField(),
    };

    private static final Map<TypeName, Container> CONTAINERS = Arrays.stream(MODEL_FIELDS)
            .filter(Container.class::isInstance)
            .distinct()
            .map(Container.class::cast)
            .collect(Collectors.toMap(ModelField::type, f -> f));

    private static final Map<TypeName, PlainField<?>> PLAIN_FIELDS = Arrays.stream(MODEL_FIELDS)
            .filter(PlainField.class::isInstance)
            .distinct()
            .map(f -> (PlainField<?>) f)
            .collect(Collectors.toMap(ModelField::type, f -> f));
}
