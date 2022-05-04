package diplom2;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CorrectEditUserDataTest {
    private User user;
    private UserClient userClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getDataFaker();           }

    @Test
    @DisplayName("Редактирование данных пользователя.")
    @Description("Редактирование данных пользователя. С авторизацией.")
    public void userCorrectEditUsedAuth() {
        final String bodyPassword  = user.getPassword();

        ValidatableResponse createResponse = userClient.createUser(user);
        String accessToken = createResponse.extract().jsonPath().get("accessToken").toString().replace("Bearer ","");
        User user = User.builder()
                .password(bodyPassword)
                .email(User.getDataFaker().getEmail())
                .name(User.getDataFaker().getName())
                .build();
        ValidatableResponse editResponse = userClient.editUserAuth(accessToken, user);
        boolean userSuccess = editResponse.extract().jsonPath().getBoolean("success");
        userClient.deleteUser(accessToken);

        assertTrue("Корреткное сообщение о невозможности редактирование данных о пользователе", userSuccess);
    }
}
