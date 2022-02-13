package xyz.v2my.easymodeling.modeler;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.modeler.field.Container;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.modeler.field.OptionalField;
import xyz.v2my.easymodeling.modeler.field.PlainField;
import xyz.v2my.easymodeling.modeler.field.collection.ArrayListField;
import xyz.v2my.easymodeling.modeler.field.collection.HashMapField;
import xyz.v2my.easymodeling.modeler.field.collection.HashSetField;
import xyz.v2my.easymodeling.modeler.field.collection.LinkedListField;
import xyz.v2my.easymodeling.modeler.field.collection.ListField;
import xyz.v2my.easymodeling.modeler.field.collection.MapField;
import xyz.v2my.easymodeling.modeler.field.collection.SetField;
import xyz.v2my.easymodeling.modeler.field.collection.TreeMapField;
import xyz.v2my.easymodeling.modeler.field.collection.TreeSetField;
import xyz.v2my.easymodeling.modeler.field.datetime.DateField;
import xyz.v2my.easymodeling.modeler.field.datetime.InstantField;
import xyz.v2my.easymodeling.modeler.field.datetime.LocalDateField;
import xyz.v2my.easymodeling.modeler.field.datetime.LocalDateTimeField;
import xyz.v2my.easymodeling.modeler.field.datetime.LocalTimeField;
import xyz.v2my.easymodeling.modeler.field.datetime.SqlDateField;
import xyz.v2my.easymodeling.modeler.field.datetime.SqlTimestampField;
import xyz.v2my.easymodeling.modeler.field.datetime.ZonedDateTimeField;
import xyz.v2my.easymodeling.modeler.field.number.ByteField;
import xyz.v2my.easymodeling.modeler.field.number.DoubleField;
import xyz.v2my.easymodeling.modeler.field.number.FloatField;
import xyz.v2my.easymodeling.modeler.field.number.IntegerField;
import xyz.v2my.easymodeling.modeler.field.number.LongField;
import xyz.v2my.easymodeling.modeler.field.number.ShortField;
import xyz.v2my.easymodeling.modeler.field.primitive.BooleanField;
import xyz.v2my.easymodeling.modeler.field.primitive.CharField;
import xyz.v2my.easymodeling.modeler.field.stream.DoubleStreamField;
import xyz.v2my.easymodeling.modeler.field.stream.IntStreamField;
import xyz.v2my.easymodeling.modeler.field.stream.LongStreamField;
import xyz.v2my.easymodeling.modeler.field.stream.StreamField;
import xyz.v2my.easymodeling.modeler.field.string.StringBuilderField;
import xyz.v2my.easymodeling.modeler.field.string.StringField;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelFieldRegistry {

    private ModelFieldRegistry() {
    }

    static Optional<? extends PlainField<?>> plainField(TypeName type) {
        return Optional.ofNullable(ModelFieldRegistry.PLAIN_FIELDS.get(type.box()));
    }

    static Optional<Container> container(TypeName rawType) {
        return Optional.ofNullable(ModelFieldRegistry.CONTAINERS.get(rawType));
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
