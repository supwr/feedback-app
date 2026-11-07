package com.feedback;

import io.quarkus.runtime.Quarkus;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Application {
    public static void main(String[] args) {
        Quarkus.run(args);
    }
}
