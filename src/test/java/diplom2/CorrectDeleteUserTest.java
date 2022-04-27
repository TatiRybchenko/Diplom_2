package diplom2;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CorrectDeleteUserTest {
    private UserClient userClient;
    private User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getDataFaker();
        userClient.createUser(user);                   }

    @Test
    @DisplayName("Выполнение запроса на удаление пользователя с корректными значениями")
    @Description("Выполнение запроса на удаление пользователя с корректными значениями. Данные по созданию пользователя генерируются рандомно.")
    public void userCanDeleteWithValidCredentials() {
        ValidatableResponse loginResponse = userClient.loginUser(UserCredentials.from(user));
        String accessToken = loginResponse.extract().jsonPath().get("accessToken").toString().replace("Bearer ","");
        ValidatableResponse deleteResponse = userClient.deleteUser(accessToken);
        int statusCode = deleteResponse.extract().statusCode();
        boolean userSuccess = deleteResponse.extract().jsonPath().getBoolean("success");
        String errorMessage = deleteResponse.extract().path("message");

        assertThat("Удаление пользователя выполнилось без ошибок, статус код:", statusCode, equalTo(202));
        assertTrue("Корреткное сообщение о завершение создания пользователя Success", userSuccess);
        assertEquals("User successfully removed", errorMessage);

    }

}
