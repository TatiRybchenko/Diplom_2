package diplom2;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FailedLoginUserTest {

    private UserClient userClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Выполнение логина пользователя с некорректными значениями, нет email")
    @Description("Выполнение логина пользователя с некорректными значениями. Отсутствует параметр для входа: email")
    public void userFailedLoginCredentialsNoLogin() {
        User user = User.builder()
                .password(User.getDataFaker().getPassword())
                .build();
        ValidatableResponse loginResponse = userClient.loginUserNoValidCredentials(UserCredentials.from(user));
        String errorMessage = loginResponse.extract().path("message");
        assertEquals("email or password are incorrect", errorMessage);
    }

    @Test
    @DisplayName("Выполнение логина пользователя с некорректными значениями, нет пароля")
    @Description("Выполнение логина пользователя с некорректными значениями. Отсутствует параметр для входа: пароль")
    public void userFailedLoginCredentialsNoPassword() {
        User user= User.builder()
                .email(User.getDataFaker().getEmail())
                .build();
        ValidatableResponse loginResponse = userClient.loginUserNoValidCredentials(UserCredentials.from(user));
        String errorMessage = loginResponse.extract().path("message");

        assertEquals("email or password are incorrect", errorMessage);
    }

    @Test
    @DisplayName("Выполнение логина пользователя с некорректными значениями: несуществующий логин и несуществующий пароль")
    @Description("Выполнение логина пользователя с некорректными значениями. Отсутствует параметр для входа: пароль, логин")
    public void userFailedLoginCredentialsNoPasswordNoLogin() {
        User user = User.builder()
                .password(User.getDataFaker().getPassword())
                .email(User.getDataFaker().getEmail())
                .build();
        ValidatableResponse loginResponse = userClient.loginUserNoValidCredentials(UserCredentials.from(user));
        String errorMessage = loginResponse.extract().path("message");

        assertEquals("email or password are incorrect", errorMessage);
    }

}
