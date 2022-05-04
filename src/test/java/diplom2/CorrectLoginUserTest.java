package diplom2;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CorrectLoginUserTest {
    private UserClient userClient;
    private User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getDataFaker();
        userClient.createUser(user);     }

    @After
    public void tearDown(){
        ValidatableResponse loginResponse = userClient.loginUser(UserCredentials.from(user));
        String accessToken = loginResponse.extract().jsonPath().get("accessToken").toString().replace("Bearer ","");
        userClient.deleteUser(accessToken);     }

    @Test
    @DisplayName("Выполнение запроса на выполнение логина пользователя с корректными значениями")
    @Description("Выполнение запроса на выполнение логина пользователя с корректными значениями. Корректные значения для создания и входа генерируется рандомно.")
    public void userCanLoginWithValidCredentials() {
        final String expectedBodyEmail = user.getEmail();
        final String expectedBodyName = user.getName();

        ValidatableResponse loginResponse = userClient.loginUser(UserCredentials.from(user));
        boolean userSuccess = loginResponse.extract().jsonPath().getBoolean("success");
        String bodyEmail = loginResponse.extract().jsonPath().getString("user.email");
        String bodyName = loginResponse.extract().jsonPath().getString("user.name");
        String bodyAccessToken = loginResponse.extract().jsonPath().getString("accessToken");
        String bodyRefreshToken = loginResponse.extract().jsonPath().getString("refreshToken");

        assertTrue("Корреткное сообщение о завершение авторизации пользователя Success", userSuccess);
        assertEquals("Ожидаемое значение отличается от актуального", bodyEmail, expectedBodyEmail);
        assertEquals("Ожидаемое значение отличается от актуального", bodyName, expectedBodyName);
        assertThat("Значение возвращаемого AccessToken не пустое", bodyAccessToken, notNullValue());
        assertThat("Значение возвращаемого RefreshToke не пустое", bodyRefreshToken, notNullValue());

    }


}
