package diplom2;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class FailedCreateUserTest {
    private UserClient userClient;
    private User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
          }

    @Test
    @DisplayName("Создание пользователя,который уже зарегистрирован")
    @Description("Создание пользователя,который уже зарегистрирован: данные для создания пользователя постоянные")
    public void userFailedCredentialExistEmailPasswordName() {
        final String EMAIL = "test-data@yandex.ru";
        final String USERPASSWORD = "password";
        final String USERNAME = "Username";

        User userStellarBurger = User.builder()
                .email(EMAIL)
                .password(USERPASSWORD)
                .name(USERNAME)
                .build();

        ValidatableResponse createResponse = userClient.createUser(userStellarBurger);
        int statusCode = createResponse.extract().statusCode();
        boolean userSuccess = createResponse.extract().jsonPath().getBoolean("success");
        String errorMessage = createResponse.extract().path("message");

        assertThat("Создание пользователя не выполнилось, статус код:", statusCode, equalTo(403));
        assertFalse("Корреткное сообщение о завершение создания пользователя Success", userSuccess);
        assertEquals("User already exists", errorMessage);
    }

    @Test
    @DisplayName("Создание пользователя, у которого отсутствует один из передаваемых параметров для создания: емейл")
    @Description("При создание пользователя отсутствует параметр емейл, данные генерируются рандомно.")
    public void userFailedCredentialsNoAccountsEmail() {
        user = User.getDataFaker();

        ValidatableResponse createResponse = userClient.createFailedUserNoEmailAddress(user);
        int statusCode = createResponse.extract().statusCode();
        boolean userSuccess = createResponse.extract().jsonPath().getBoolean("success");
        String errorMessage = createResponse.extract().path("message");

        assertThat("Создание пользователя не выполнилось, статус код:",statusCode,equalTo(403));
        assertFalse("Корреткное сообщение о завершение создания пользователя Success", userSuccess);
        assertEquals("Email, password and name are required fields", errorMessage);
    }

    @Test
    @DisplayName("Создание пользователя, у которого отсутствует один из передаваемых параметров для создания: пароль")
    @Description("При создание пользователя отсутствует параметр пароль, данные генерируются рандомно.")
    public void userFailedCredentialsNoAccountsPassword() {
        user = User.getDataFaker();

        ValidatableResponse createResponse = userClient.createFailedUserNoPassword(user);
        int statusCode = createResponse.extract().statusCode();
        boolean userSuccess = createResponse.extract().jsonPath().getBoolean("success");
        String errorMessage = createResponse.extract().path("message");

        assertThat("Создание пользователя не выполнилось, статус код:",statusCode,equalTo(403));
        assertFalse("Корреткное сообщение о завершение создания пользователя Success", userSuccess);
        assertEquals("Email, password and name are required fields", errorMessage);
    }

    @Test
    @DisplayName("Создание пользователя, у которого отсутствует один из передаваемых параметров для создания: имя")
    @Description("При создание пользователя отсутствует параметр имя, данные генерируются рандомно.")
    public void userFailedCredentialsNoAccountsName() {
        user = User.getDataFaker();

        ValidatableResponse createResponse = userClient.createFailedUserNoName(user);
        int statusCode = createResponse.extract().statusCode();
        boolean userSuccess = createResponse.extract().jsonPath().getBoolean("success");
        String errorMessage = createResponse.extract().path("message");

        assertThat("Создание пользователя не выполнилось, статус код:",statusCode,equalTo(403));
        assertFalse("Корреткное сообщение о завершение создания пользователя Success", userSuccess);
        assertEquals("Email, password and name are required fields", errorMessage);
    }
}
