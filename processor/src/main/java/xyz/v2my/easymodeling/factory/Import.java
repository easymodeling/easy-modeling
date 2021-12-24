package xyz.v2my.easymodeling.factory;

public class Import {

    private final Class<?> clazz;

    private final String name;

    public Import(Class<?> clazz, String name) {
        this.clazz = clazz;
        this.name = name;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getName() {
        return name;
    }
}
