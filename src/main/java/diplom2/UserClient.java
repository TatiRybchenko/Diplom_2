package diplom2;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;


import java.util.HashMap;
import java.util.Map;

import static diplom2.EndPoints.*;
import static diplom2.StellarBurgerRestClient.getBaseSpec;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;


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
                .then()
                .statusCode(SC_OK);
    }

    @Step("Выполнение запроса на создание пользователя, который существует со всеми параметрами: имя, логин, пароль.")
    public ValidatableResponse createUserExist(User user) {

        Map<String,String> requestBodyUserCreate = new HashMap<>();
        requestBodyUserCreate.put("email", "test-data@yandex.ru");
        requestBodyUserCreate.put("password", "password");
        requestBodyUserCreate.put("name", "Username");

        return given()
                .spec(getBaseSpec())
                .body(requestBodyUserCreate)
                .when()
                .post(USER_CREATE)
                .then()
                .statusCode(403);
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
                .then()
                .statusCode(403);
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
                .then()
                .statusCode(403);
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
                .then()
                .statusCode(403);
    }

    @Step("Выполнение запроса логина пользователя, логин {credentials.email} и пароль {credentials.password}")
    public  ValidatableResponse loginUser(UserCredentials credentials){
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(USER_LOGIN)
                .then()
                .statusCode(SC_OK);
    }
    @Step("Выполнение запроса логина пользователя: нет одного из параметров, логин {credentials.email} и пароль {credentials.password}")
    public  ValidatableResponse loginUserNoValidCredentials(UserCredentials credentials){
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(USER_LOGIN)
                .then()
                .statusCode(401);
    }

    @Step("Выполнение запроса на удаление пользователя c авторизацией")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .when()
                .delete(USER)
                .then()
                .statusCode(202);
    }

    @Step("Выполнение запроса на получение и редактирование данных о пользователе c авторизацией {user.email} и {user.name}")
    public ValidatableResponse editUserAuth(String accessToken, User user) {

        Map<String,String> requestBodyUserEdit = new HashMap<>();
        requestBodyUserEdit.put("email", user.getEmail());
        requestBodyUserEdit.put("name", user.getName());

        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .body(requestBodyUserEdit)
                .when()
                .patch(USER)
                .then();
    }
    @Step("Выполнение запроса на получение и редактирование данных о пользователе c авторизацией, {user.email} и {user.name}")
    public ValidatableResponse editUserMailUsedAuth(String accessToken, User user) {

        Map<String,String> requestBodyUserEdit = new HashMap<>();
        requestBodyUserEdit.put("email","test-data@yandex.ru");
        requestBodyUserEdit.put("name", user.getName());

        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .body(requestBodyUserEdit)
                .when()
                .patch(USER)
                .then()
                .statusCode(403);
    }

    @Step("Выполнение запроса на получение и редактирование данных о пользователе БЕЗ авторизацией, {user.email} и {user.name}")
    public ValidatableResponse editUserNoAuth(User user) {

        Map<String,String> requestBodyUserEdit = new HashMap<>();
        requestBodyUserEdit.put("email", user.getEmail());
        requestBodyUserEdit.put("name", user.getName());

        return given()
                .spec(getBaseSpec())
                .body(requestBodyUserEdit)
                .when()
                .patch(USER)
                .then()
                .statusCode(401);
    }
}
