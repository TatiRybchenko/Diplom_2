package diplom2;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CorrectLoginUserStellarBurgerTest {
    private UserStellarBurgerClient userStellarBurgerClient;
    private UserStellarBurger userStellarBurger;


    @Before
    public void setUp() {
        userStellarBurgerClient = new UserStellarBurgerClient();
        userStellarBurger = UserStellarBurger.getDataFaker();
        userStellarBurgerClient.createUserStellarBurger(userStellarBurger);
    }

    @Test
    @DisplayName("Выполнение запроса на выполнение логина пользователя с корректными значениями")
    @Description("Выполнение запроса на выполнение логина пользователя с корректными значениями. Корректные значения для создания и входа генерируется рандомно.")
    public void userCanLoginWithValidCredentials() {
        final String expectedBodyEmail = userStellarBurger.getEmail();
        final String expectedBodyName = userStellarBurger.getName();

        ValidatableResponse loginResponse = userStellarBurgerClient.login(UserStellarBurgerCredentials.from(userStellarBurger));
        int statusCode = loginResponse.extract().statusCode();
        boolean userSuccess = loginResponse.extract().jsonPath().getBoolean("success");
        String bodyEmail = loginResponse.extract().jsonPath().getString("user.email");
        String bodyName = loginResponse.extract().jsonPath().getString("user.name");
        String bodyAccessToken = loginResponse.extract().jsonPath().getString("accessToken");
        String bodyRefreshToken = loginResponse.extract().jsonPath().getString("refreshToken");

        assertThat("Курьер выполних логин, статус код:", statusCode, equalTo(SC_OK));
        assertTrue("Корреткное сообщение о завершение авторизации пользователя Success", userSuccess);
        assertEquals("Ожидаемое значение отличается от актуального", bodyEmail, expectedBodyEmail);
        assertEquals("Ожидаемое значение отличается от актуального", bodyName, expectedBodyName);
        assertThat("Значение возвращаемого AccessToken не пустое", bodyAccessToken, notNullValue());
        assertThat("Значение возвращаемого RefreshToke не пустое", bodyRefreshToken, notNullValue());

    }


}
