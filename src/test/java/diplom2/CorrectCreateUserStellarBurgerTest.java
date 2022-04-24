package diplom2;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CorrectCreateUserStellarBurgerTest {

    private UserStellarBurgerClient userStellarBurgerClient;
    private UserStellarBurger userStellarBurger;

    @Before
    public void setUp() {
        userStellarBurgerClient = new UserStellarBurgerClient();
        userStellarBurger = UserStellarBurger.getDataFaker();
    }

    @Test
    @DisplayName("Создание пользователя с валидными значениями в параметрах: почта, пароль, имя.")
    @Description("Создание пользователя с валидными значениями (почта, пароль, имя). Корректные значения генерируются рандомно.")
    public void userCanCreateWithValidCredentials() {
        final String expectedBodyEmail = userStellarBurger.getEmail();
        final String expectedBodyName = userStellarBurger.getName();

        ValidatableResponse createResponse = userStellarBurgerClient.createUserStellarBurger(userStellarBurger);

        int statusCode = createResponse.extract().statusCode();
        boolean userSuccess = createResponse.extract().jsonPath().getBoolean("success");

        String bodyEmail = createResponse.extract().jsonPath().getString("user.email");
        String bodyName = createResponse.extract().jsonPath().getString("user.name");
        String bodyAccessToken = createResponse.extract().jsonPath().getString("accessToken");
        String bodyRefreshToken = createResponse.extract().jsonPath().getString("refreshToken");

        assertThat("Создание пользователя выполнилось без ошибок, статус код:",statusCode,equalTo(200));
        assertTrue("Корреткное сообщение о завершение создания пользователя Success", userSuccess);
        assertThat("Значение возвращаемого Email не пустое", bodyEmail, notNullValue());
        assertThat("Значение возвращаемого Name не пустое", bodyName, notNullValue());
        assertThat("Значение возвращаемого AccessToken не пустое", bodyAccessToken, notNullValue());
        assertThat("Значение возвращаемого RefreshToke не пустое", bodyRefreshToken, notNullValue());
        assertEquals("Ожидаемое значение отличается от актуального", bodyEmail, expectedBodyEmail);
        assertEquals("Ожидаемое значение отличается от актуального", bodyName, expectedBodyName);
    }

    }
