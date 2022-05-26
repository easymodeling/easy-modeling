package io.github.easymodeling.log;

import io.github.easymodeling.ReflectionUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class ProcessorLoggerTest {

    @Mock
    private Messager messager;

    @BeforeEach
    void setUp() {
        ProcessorLogger.log.setMessager(messager, "WARN");
    }

    @Test
    void should_not_print_debug_log() {
        ProcessorLogger.log.debug("some message");

        verifyNoInteractions(messager);
    }

    @Test
    void should_print_warning_log() {
        ProcessorLogger.log.warning("some message");

        verify(messager).printMessage(Diagnostic.Kind.WARNING, "some message");
    }

    @Test
    void should_print_error_log() {
        ProcessorLogger.log.error("some message");

        verify(messager).printMessage(Diagnostic.Kind.ERROR, "some message");
    }

    @AfterEach
    void tearDown() {
        ReflectionUtil.setField(ProcessorLogger.log, ProcessorLogger.class.getCanonicalName() + "#messager", new AtomicReference<>());
    }
}
