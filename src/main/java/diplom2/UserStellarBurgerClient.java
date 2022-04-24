package diplom2;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import java.util.HashMap;
import java.util.Map;

import static diplom2.EndPoints.USER_CREATE;
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

}
