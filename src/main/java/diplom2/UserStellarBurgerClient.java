package diplom2;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.util.HashMap;
import java.util.Map;

import static diplom2.EndPoints.USER_CREATE;
import static diplom2.EndPoints.USER_LOGIN;
import static diplom2.StellarBurgerRestClient.getBaseSpec;
import static io.restassured.RestAssured.given;


public class UserStellarBurgerClient {

    @Step("Выполнение запроса на создание пользователя со всеми параметрами: имя, логин, пароль.")
    public ValidatableResponse createUserStellarBurger(UserStellarBurger userStellarBurger) {

        Map<String,String> requestBodyUserCreate = new HashMap<>();
        requestBodyUserCreate.put("email", userStellarBurger.getEmail());
        requestBodyUserCreate.put("password", userStellarBurger.getPassword());
        requestBodyUserCreate.put("name", userStellarBurger.getName());

        return given()
                .spec(getBaseSpec())
                .body(requestBodyUserCreate)
                .when()
                .post(USER_CREATE)
                .then();
    }

    @Step("Выполнение запроса на создание пользователя, у которого отсутствует один из параметров: емейл")
    public ValidatableResponse createFailedUserNoEmailAddress(UserStellarBurger userStellarBurger) {

        Map<String,String> requestBodyUserCreate = new HashMap<>();
        requestBodyUserCreate.put("password", userStellarBurger.getPassword());
        requestBodyUserCreate.put("name", userStellarBurger.getName());

        return given()
                .spec(getBaseSpec())
                .body(requestBodyUserCreate)
                .when()
                .post(USER_CREATE)
                .then();
    }

    @Step("Выполнение запроса на создание пользователя, у которого отсутствует один из параметров: пароль")
    public ValidatableResponse createFailedUserNoPassword(UserStellarBurger userStellarBurger) {

        Map<String,String> requestBodyUserCreate = new HashMap<>();
        requestBodyUserCreate.put("email", userStellarBurger.getEmail());
        requestBodyUserCreate.put("name", userStellarBurger.getName());

        return given()
                .spec(getBaseSpec())
                .body(requestBodyUserCreate)
                .when()
                .post(USER_CREATE)
                .then();
    }

    @Step("Выполнение запроса на создание пользователя, у которого отсутствует один из параметров: имя")
    public ValidatableResponse createFailedUserNoName(UserStellarBurger userStellarBurger) {

        Map<String,String> requestBodyUserCreate = new HashMap<>();
        requestBodyUserCreate.put("email", userStellarBurger.getEmail());
        requestBodyUserCreate.put("password", userStellarBurger.getPassword());

        return given()
                .spec(getBaseSpec())
                .body(requestBodyUserCreate)
                .when()
                .post(USER_CREATE)
                .then();
    }

    @Step("Выполнение запроса логина пользователя, логин {credentials.email} и пароль {credentials.password}")
    public  ValidatableResponse login(UserStellarBurgerCredentials credentials){
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(USER_LOGIN)
                .then();
    }
}
