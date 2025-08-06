package com.example.singletonscheduler.routes;

import com.example.singletonscheduler.processor.SchedulerProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SchedulerProcessorRoute extends RouteBuilder {

    @Autowired
    SchedulerProcessor processor;
    @Override
    public void configure() throws Exception {
        from("timer://SchedulerProcessor?delay=1000&period=5000&fixedRate=true&synchronous=true")
                .autoStartup(true)
                .routeId("SchedulerProcessorRoute")
                .log(LoggingLevel.INFO, "Scheduler Processor Started")
                .process(processor)
                .log(LoggingLevel.INFO, "Scheduler Processor End");

    }
}
