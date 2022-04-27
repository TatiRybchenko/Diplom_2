package diplom2;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.util.HashMap;
import java.util.Map;

import static diplom2.EndPoints.*;
import static diplom2.StellarBurgerRestClient.getBaseSpec;
import static io.restassured.RestAssured.given;


public class UserClient {

    @Step("Выполнение запроса на создание пользователя со всеми параметрами: имя, логин, пароль.")
    public ValidatableResponse createUser(User user) {

        Map<String,String> requestBodyUserCreate = new HashMap<>();
        requestBodyUserCreate.put("email", user.getEmail());
        requestBodyUserCreate.put("password", user.getPassword());
        requestBodyUserCreate.put("name", user.getName());

        return given()
                .spec(getBaseSpec())
                .body(requestBodyUserCreate)
                .when()
                .post(USER_CREATE)
                .then();
    }

    @Step("Выполнение запроса на создание пользователя, у которого отсутствует один из параметров: емейл")
    public ValidatableResponse createFailedUserNoEmailAddress(User user) {

        Map<String,String> requestBodyUserCreate = new HashMap<>();
        requestBodyUserCreate.put("password", user.getPassword());
        requestBodyUserCreate.put("name", user.getName());

        return given()
                .spec(getBaseSpec())
                .body(requestBodyUserCreate)
                .when()
                .post(USER_CREATE)
                .then();
    }

    @Step("Выполнение запроса на создание пользователя, у которого отсутствует один из параметров: пароль")
    public ValidatableResponse createFailedUserNoPassword(User user) {

        Map<String,String> requestBodyUserCreate = new HashMap<>();
        requestBodyUserCreate.put("email", user.getEmail());
        requestBodyUserCreate.put("name", user.getName());

        return given()
                .spec(getBaseSpec())
                .body(requestBodyUserCreate)
                .when()
                .post(USER_CREATE)
                .then();
    }

    @Step("Выполнение запроса на создание пользователя, у которого отсутствует один из параметров: имя")
    public ValidatableResponse createFailedUserNoName(User user) {

        Map<String,String> requestBodyUserCreate = new HashMap<>();
        requestBodyUserCreate.put("email", user.getEmail());
        requestBodyUserCreate.put("password", user.getPassword());

        return given()
                .spec(getBaseSpec())
                .body(requestBodyUserCreate)
                .when()
                .post(USER_CREATE)
                .then();
    }

    @Step("Выполнение запроса логина пользователя, логин {credentials.email} и пароль {credentials.password}")
    public  ValidatableResponse loginUser(UserCredentials credentials){
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(USER_LOGIN)
                .then();
    }

    @Step("Выполнение запроса на удаление пользователя c авторизацией ")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .when()
                .delete(USER_AUTH)
                .then();
    }
}
