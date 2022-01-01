package xyz.v2my.easymodeling;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

public class ProcessorLogger {

    public static final ProcessorLogger log = new ProcessorLogger();

    private ProcessorLogger() {
    }

    private Messager messager;

    public void setMessager(Messager messager) {
        this.messager = messager;
    }

    public void note(String msg) {
        log(Diagnostic.Kind.NOTE, msg);
    }

    public void warning(String msg) {
        log(Diagnostic.Kind.WARNING, msg);
    }

    public void error(String msg) {
        log(Diagnostic.Kind.ERROR, msg);
    }

    private void log(Diagnostic.Kind kind, String msg) {
        messager.printMessage(kind, msg);
    }
}
