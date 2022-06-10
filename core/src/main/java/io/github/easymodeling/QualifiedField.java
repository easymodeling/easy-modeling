package io.github.easymodeling;

public class QualifiedField {

    private static final String SPLITTER = "#";

    private final String className;

    private final String fieldName;

    QualifiedField(String qualifiedFieldName) throws NoSuchFieldException {
        final int splitter = qualifiedFieldName.lastIndexOf(SPLITTER);
        if (splitter == -1 || splitter == qualifiedFieldName.length() - 1 || splitter == 0) {
            throw new NoSuchFieldException(qualifiedFieldName);
        }
        this.className = qualifiedFieldName.substring(0, splitter);
        this.fieldName = qualifiedFieldName.substring(splitter + 1);
    }

    String getClassName() {
        return className;
    }

    String getFieldName() {
        return fieldName;
    }

    @Override
    public String toString() {
        return toQualifiedFieldName(className, fieldName);
    }

    public static String toQualifiedFieldName(String className, String fieldName) {
        return className + SPLITTER + fieldName;
    }
}
