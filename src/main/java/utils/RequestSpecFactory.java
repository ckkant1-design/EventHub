package utils;

import config.ConfigManager;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecFactory {

    private RequestSpecFactory() {
    }

    public static RequestSpecification getRequestSpec() {

        return new RequestSpecBuilder()

                .setBaseUri(ConfigManager.get("base.url"))

                .setContentType(ContentType.JSON)

                .setAccept(ContentType.JSON)

                .build();
    }
}

