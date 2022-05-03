package diplom2;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class FailedEditUserDataTest {
    private User user;
    private UserClient userClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getDataFaker();
        userClient.createUser(user);
        userClient.loginUser(UserCredentials.from(user));
    }

    @After
    public void tearDown(){
        ValidatableResponse loginResponse = userClient.loginUser(UserCredentials.from(user));
        String accessToken = loginResponse.extract().jsonPath().get("accessToken").toString().replace("Bearer ","");
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Редактирование данных пользователя.")
    @Description("Редактирование данных пользователя. Без авторизации")
    public void userFailedEditNoAuth() {
        ValidatableResponse editResponse = userClient.editUserNoAuth(user);

        String errorMessage = editResponse.extract().path("message");
        boolean userSuccess = editResponse.extract().jsonPath().getBoolean("success");
        assertFalse("Корреткное сообщение о невозможности редактирование данных о пользователе", userSuccess);
        assertEquals("You should be authorised", errorMessage);
    }

    @Test
    @DisplayName("Редактирование данных пользователя.")
    @Description("Редактирование данных пользователя. Без авторизации")
    public void userFailedEditMailUsedAuth() {
        ValidatableResponse loginResponse = userClient.loginUser(UserCredentials.from(user));
        String accessToken = loginResponse.extract().jsonPath().get("accessToken").toString().replace("Bearer ","");
        ValidatableResponse editResponse = userClient.editUserMailUsedAuth(accessToken, user);

        String errorMessage = editResponse.extract().path("message");
        boolean userSuccess = editResponse.extract().jsonPath().getBoolean("success");
        assertFalse("Корреткное сообщение о невозможности редактирование данных о пользователе", userSuccess);
        assertEquals("User with such email already exists", errorMessage);
    }

}
