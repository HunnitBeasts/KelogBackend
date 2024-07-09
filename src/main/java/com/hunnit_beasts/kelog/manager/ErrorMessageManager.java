package com.hunnit_beasts.kelog.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ErrorMessageManager {

    private static Environment environment;

    @Autowired
    private ErrorMessageManager(Environment environment) {
        ErrorMessageManager.environment = environment;
    }

    public static String getMessages(String key){
        return environment.getProperty("error.messages." + key);
    }
}
