package io.github.easymodeling.log;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class LogLevelTest {

    @ParameterizedTest
    @ValueSource(strings = {"ERROR", "WARN", "INFO", "DEBUG"})
    void should_create_log_level_with_string(String name) {
        LogLevel level = LogLevel.of(name);

        assertThat(level.name()).isEqualTo(name);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "\t", "UNKNOWN"})
    void should_create_default_level_with_unknown_names(String unknownName) {
        LogLevel level = LogLevel.of(unknownName);

        assertThat(level).isEqualTo(LogLevel.DEFAULT_LEVEL);
    }
}
