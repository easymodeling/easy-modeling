package xyz.v2my.easymodeling.log;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;
import java.util.concurrent.atomic.AtomicReference;

import static xyz.v2my.easymodeling.log.LogLevel.DEBUG;
import static xyz.v2my.easymodeling.log.LogLevel.ERROR;
import static xyz.v2my.easymodeling.log.LogLevel.INFO;
import static xyz.v2my.easymodeling.log.LogLevel.WARN;

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
        log(Diagnostic.Kind.NOTE, DEBUG, msg, args);
    }

    public void info(String msg, Object... args) {
        log(Diagnostic.Kind.NOTE, INFO, msg, args);
    }

    public void warning(String msg, Object... args) {
        log(Diagnostic.Kind.WARNING, WARN, msg, args);
    }

    public void error(String msg, Object... args) {
        log(Diagnostic.Kind.ERROR, ERROR, msg, args);
    }

    private void log(Diagnostic.Kind kind, LogLevel logLevel, String msg, Object... args) {
        if (logLevel.meets(allowedLevel)) {
            messager.get().printMessage(kind, String.format(msg, args));
        }
    }
}
