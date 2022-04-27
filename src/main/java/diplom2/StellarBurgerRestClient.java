package diplom2;

import configuration.UrlConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;

import static io.restassured.http.ContentType.JSON;

public class StellarBurgerRestClient {
    public static final String BASE_URL = ConfigFactory.create(UrlConfig.class).baseUrl();

    protected static RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .setContentType(JSON)
                .setBaseUri(BASE_URL)
                .build();
    }

}

