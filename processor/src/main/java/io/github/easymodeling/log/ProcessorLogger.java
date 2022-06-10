package io.github.easymodeling.log;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;
import java.util.concurrent.atomic.AtomicReference;

public class ProcessorLogger {

    public static final ProcessorLogger log = new ProcessorLogger();

    private ProcessorLogger() {
        this.messager = new AtomicReference<>();
    }

    private final AtomicReference<Messager> messager;

    private LogLevel allowedLevel = LogLevel.DEFAULT_LEVEL;

    public void setMessager(Messager messager, String logLevel) {
        this.messager.compareAndSet(null, messager);
        this.allowedLevel = LogLevel.of(logLevel);
    }

    public void debug(String msg, Object... args) {
        log(Diagnostic.Kind.NOTE, LogLevel.DEBUG, msg, args);
    }

    public void info(String msg, Object... args) {
        log(Diagnostic.Kind.NOTE, LogLevel.INFO, msg, args);
    }

    public void warning(String msg, Object... args) {
        log(Diagnostic.Kind.WARNING, LogLevel.WARN, msg, args);
    }

    public void error(String msg, Object... args) {
        log(Diagnostic.Kind.ERROR, LogLevel.ERROR, msg, args);
    }

    private void log(Diagnostic.Kind kind, LogLevel logLevel, String msg, Object... args) {
        try {
            if (logLevel.meets(allowedLevel)) {
                messager.get().printMessage(kind, String.format(msg, args));
            }
        } catch (Exception ignored) {
        }
    }
}
