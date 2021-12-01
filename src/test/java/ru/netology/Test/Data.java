package ru.netology.Test;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import lombok.Value;
import static io.restassured.RestAssured.given;

public class Data {
    private Data() {
    }
public static class UserUser{
    private static final Faker faker = new Faker();
    private static final String username = faker.name().username();
    private static final String password = faker.internet().password();

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(io.restassured.http.ContentType.JSON)
            .setContentType(io.restassured.http.ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void validUser() {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(new Gson().toJson(new User(username, password, "active"))) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static void invalidUser() {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(new Gson().toJson(new User(username, password, "blocked"))) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }
        public static String getUsername() {
            return username;
        }

        public static String getPassword() {
            return password;
        }

        public static String getAnotherUsername() {
            return faker.name().username();
        }

        public static String getAnotherPassword() {
            return faker.internet().password();
        }
    }
        @Value
        private static class User {
            String login;
            String password;
            String status;
        }
    }