package com.unsolvedwa.unsolvedwa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class UnsolvedWaApplication {

  public static void main(String[] args) {
    SpringApplication.run(UnsolvedWaApplication.class, args);
  }
}
