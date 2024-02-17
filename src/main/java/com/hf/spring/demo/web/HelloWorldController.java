package com.hf.spring.demo.web;


import com.hf.spring.demo.domain.Greeting;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Demo Controller to return the message which an incremental count.
 */
@Controller
public class HelloWorldController {
    public static final String template = "Hello, %s";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/hello-world")
    // Tell Spring to return object directly rather than rely on a view (Ex: JSP)
    @ResponseBody
    public Greeting sayHello(@RequestParam(
                name="name",
                required = false,
                defaultValue = "Stranger")
            String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
