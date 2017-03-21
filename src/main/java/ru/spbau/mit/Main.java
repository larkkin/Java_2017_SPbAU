package ru.spbau.mit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    static final Logger mainLogger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        mainLogger.trace("this trace goes nowhere");
        mainLogger.info("this info goes to file");
        mainLogger.warn("this warn goes to file");
        mainLogger.error("this error should go both to console and file");
        mainLogger.fatal("this fatal goes to file");
    }
}
