package io.github.easymodeling.log;

public enum LogLevel {
    ERROR(4),
    WARN(3),
    INFO(2),
    DEBUG(1),
    ;

    public static final LogLevel DEFAULT_LEVEL = WARN;

    private final int level;

    LogLevel(int level) {
        this.level = level;
    }

    boolean meets(LogLevel givenLevel) {
        return this.level >= givenLevel.level;
    }

    public static LogLevel of(String name) {
        for (LogLevel level : LogLevel.values()) {
            if (level.name().equalsIgnoreCase(name)) {
                return level;
            }
        }
        return DEFAULT_LEVEL;
    }
}
