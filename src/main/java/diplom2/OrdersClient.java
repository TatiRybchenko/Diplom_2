package diplom2;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static diplom2.EndPoints.ORDERS;
import static diplom2.StellarBurgerRestClient.getBaseSpec;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class OrdersClient {

    @Step("Выполнение запроса на создание заказа с ингредиентами:{orders.ingredients}")
    public ValidatableResponse createCorrectOrders(Orders orders,String accessToken) {

        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .body(orders)
                .when()
                .post(ORDERS)
                .then()
                .statusCode(400);
    }

    @Step("Выполнение запроса на создание заказа без авторизации")
    public ValidatableResponse createCorrectOrdersNoAuth(Orders orders) {

        return given()
                .spec(getBaseSpec())
                .body(orders)
                .when()
                .post(ORDERS)
                .then()
                .statusCode(SC_OK);
    }

    @Step("Выполнение запроса на получение конкретного пользователя: без авторизации")
    public ValidatableResponse  orderListAllActiveNoAuth() {

        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDERS)
                .then();
    }

    @Step("Выполнение запроса на получение конкретного пользователя: c авторизации")
    public ValidatableResponse  orderListAllActiveAuth(String accessToken) {

        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .when()
                .get(ORDERS)
                .then();
    }
}
