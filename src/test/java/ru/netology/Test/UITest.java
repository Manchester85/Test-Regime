package ru.netology.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.Test.Data.UserUser.*;

public class UITest {

    @BeforeEach
    void setup() {
        validUser();
        open("http://localhost:9999");
    }

    @Test
    void shouldHaveValidData() {
        $("[data-test-id='login'] .input__control").setValue(getUsername());
        $("[data-test-id='password'] .input__control").setValue(getPassword());
        $("button[data-test-id=action-login]").click();
        $(".App_appContainer__3jRx1").shouldBe(visible).shouldHave(text("Интернет Банк"));
    }

    @Test
    void shouldHaveBlockedUser() {
        $("[data-test-id='login'] .input__control").setValue(getUsername());
        $("[data-test-id='password'] .input__control").setValue(getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldBe(visible).shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    void shouldFakeUsername() {
        invalidUser();
        $("[data-test-id='login'] .input__control").setValue(getAnotherUsername());
        $("[data-test-id='password'] .input__control").setValue(getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }

    @Test
    void shouldFakePassword() {
        invalidUser();
        $("[data-test-id='login'] .input__control").setValue(getUsername());
        $("[data-test-id='password'] .input__control").setValue(getAnotherPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);
    }
}


