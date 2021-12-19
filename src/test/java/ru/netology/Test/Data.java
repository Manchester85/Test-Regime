package ru.netology.Test;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import lombok.Value;
import static io.restassured.RestAssured.given;

public class Data {
    private static final Faker faker = new Faker();

    private Data() {
    }

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(io.restassured.http.ContentType.JSON)
            .setContentType(io.restassured.http.ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void sendRequest(Registration.User user) {
        // сам запрос
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static class Registration {
        private Registration() {
        }

        public static String generateLogin() {
            return faker.name().username();
        }

        public static String generatePassword() {
            return faker.internet().password();
        }

        public static User generateValidUser() {
            User user = new User(generateLogin(), generatePassword(), "active");
            sendRequest(user);
            return user;
        }

        public static User generateBlockedUser() {
            User user = new User(generateLogin(), generatePassword(), "blocked");
            sendRequest(user);
            return user;
        }

        public static User generateInvalidPassword(String status) {
            String login = generateLogin();
            sendRequest(new User(login, generatePassword(), status));
            return new User(login, generatePassword(), status);
        }

        public static User generateInvalidLogin(String status) {
            String password = generatePassword();
            sendRequest(new User(generateLogin(), password, status));
            return new User(generateLogin(), password, status);
        }

        @Value
        public static class User {
            String login;
            String password;
            String status;
        }
    }
}


