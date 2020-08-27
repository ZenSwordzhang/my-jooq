package com.zsx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.logging.LogLevel;

@Data
@AllArgsConstructor(staticName = "of")
public class LogMessage {

    private String env;

    private String msg;

    private LogLevel logLevel;
}
