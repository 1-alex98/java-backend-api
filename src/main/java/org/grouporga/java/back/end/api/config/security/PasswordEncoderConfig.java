package org.grouporga.java.back.end.api.config.security;

import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

@Configuration
public class PasswordEncoderConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    String idForEncode = "bcrypt";
    Map<String, PasswordEncoder> encoders = ImmutableMap.of(
      idForEncode, new BCryptPasswordEncoder(),
      "noop", NoOpPasswordEncoder.getInstance()
    );

    return new DelegatingPasswordEncoder(idForEncode, encoders);
  }
}
