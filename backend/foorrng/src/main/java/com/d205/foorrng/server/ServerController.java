package com.d205.foorrng.server;


import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/server")
public class ServerController {

    private final Environment env;

    @GetMapping("")
    public String health() {
        return "done";
    }

    @GetMapping("/test")
    public String check() {
        String systEnv = System.getenv("TEMP_HOME");
        return systEnv;
    }

}
