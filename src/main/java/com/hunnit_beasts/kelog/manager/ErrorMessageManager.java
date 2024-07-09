package com.hunnit_beasts.kelog.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ErrorMessageManager {

    private final Environment environment;

    public String getMessages(String key){
        return environment.getProperty("error.messages." + key);
    }
}
