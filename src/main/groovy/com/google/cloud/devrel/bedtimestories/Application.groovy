package com.google.cloud.devrel.bedtimestories

import io.micronaut.runtime.Micronaut
import groovy.transform.CompileStatic
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@CompileStatic
class Application {
    private static Logger logger = LoggerFactory.getLogger("StoryMaker.Applicaction");

    static void main(String[] args) {
        logger.info("Starting...")
        Micronaut.run(Application, args)
    }
}
