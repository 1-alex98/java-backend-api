package org.grouporga.java.back.end.api.config.swagger;

import com.google.common.base.Predicate;
import io.swagger.annotations.Api;
import org.grouporga.java.back.end.api.config.OrgaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.inject.Inject;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@Api(value = "Data API", tags = {"foo", "bar"})
public class SwaggerConfig {

  private final OrgaProperties orgaProperties;

  @Inject
  public SwaggerConfig(OrgaProperties orgaProperties) {
    this.orgaProperties = orgaProperties;
  }

  @Bean
  public Docket newsApi() {
    return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo())
      .select().paths(paths())
      .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("Group orga API")
      .description("The official API for the orga app")
      .contact(new Contact("Alexander von Trostorff", "https://github.com/axel1200", "alexander.von.trostorff@gmail.com"))
      .license("MIT")
      .licenseUrl("https://choosealicense.com/licenses/mit/")
      .version(orgaProperties.getVersion())
      .build();
  }

  private Predicate<String> paths() {
    return or(
      regex("/oauth/(.*token.*|.*authorize)"),
      regex("/data/.*"),
      regex("/version"),
      regex("/users/.*"));
  }
}
