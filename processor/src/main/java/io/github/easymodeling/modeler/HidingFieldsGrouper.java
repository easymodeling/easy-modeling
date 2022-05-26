package io.github.easymodeling.modeler;

import io.github.easymodeling.modeler.field.ModelField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class HidingFieldsGrouper {

    private HidingFieldsGrouper() {
    }

    public static final Collector<ModelField, ?, Map<String, List<ModelField>>> GROUPER =
            Collectors.groupingBy(
                    ModelField::fieldName,
                    Collector.of(ArrayList::new, List::add, (l, r) -> null, hiddenFieldsMarker()));

    private static Function<List<ModelField>, List<ModelField>> hiddenFieldsMarker() {
        return fields -> {
            if (fields.size() > 1) {
                fields.stream().filter(ModelField::isInherited).forEach(ModelField::setHidden);
            }
            return fields;
        };
    }
}
