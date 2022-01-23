package xyz.v2my.easymodeling.factory;

import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.factory.field.OptionalField;
import xyz.v2my.easymodeling.factory.field.collection.ArrayListField;
import xyz.v2my.easymodeling.factory.field.collection.HashMapField;
import xyz.v2my.easymodeling.factory.field.collection.HashSetField;
import xyz.v2my.easymodeling.factory.field.collection.LinkedListField;
import xyz.v2my.easymodeling.factory.field.collection.ListField;
import xyz.v2my.easymodeling.factory.field.collection.MapField;
import xyz.v2my.easymodeling.factory.field.collection.SetField;
import xyz.v2my.easymodeling.factory.field.collection.TreeMapField;
import xyz.v2my.easymodeling.factory.field.collection.TreeSetField;
import xyz.v2my.easymodeling.factory.field.datetime.DateField;
import xyz.v2my.easymodeling.factory.field.datetime.InstantField;
import xyz.v2my.easymodeling.factory.field.datetime.LocalDateField;
import xyz.v2my.easymodeling.factory.field.datetime.LocalDateTimeField;
import xyz.v2my.easymodeling.factory.field.datetime.LocalTimeField;
import xyz.v2my.easymodeling.factory.field.datetime.ZonedDateTimeField;
import xyz.v2my.easymodeling.factory.field.number.ByteField;
import xyz.v2my.easymodeling.factory.field.number.DoubleField;
import xyz.v2my.easymodeling.factory.field.number.FloatField;
import xyz.v2my.easymodeling.factory.field.number.IntegerField;
import xyz.v2my.easymodeling.factory.field.number.LongField;
import xyz.v2my.easymodeling.factory.field.number.ShortField;
import xyz.v2my.easymodeling.factory.field.primitive.BooleanField;
import xyz.v2my.easymodeling.factory.field.primitive.CharField;
import xyz.v2my.easymodeling.factory.field.stream.DoubleStreamField;
import xyz.v2my.easymodeling.factory.field.stream.IntStreamField;
import xyz.v2my.easymodeling.factory.field.stream.LongStreamField;
import xyz.v2my.easymodeling.factory.field.stream.StreamField;
import xyz.v2my.easymodeling.factory.field.string.StringBuilderField;
import xyz.v2my.easymodeling.factory.field.string.StringField;

public class ModelFieldRegistry {

    private ModelFieldRegistry() {
    }

    static final ModelField[] MODEL_FIELDS = {
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
}
