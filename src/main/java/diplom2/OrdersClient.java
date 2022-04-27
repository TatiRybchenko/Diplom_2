package diplom2;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static diplom2.EndPoints.ORDERS_CREATE;
import static diplom2.StellarBurgerRestClient.getBaseSpec;
import static io.restassured.RestAssured.given;

public class OrdersClient {

    @Step("Выполнение запроса на создание заказ с ингредиентами:{orders.ingredients}")
    public ValidatableResponse createCorrectOrders(Orders orders,String accessToken) {

        return given()
                .spec(getBaseSpec())
                .auth().oauth2(accessToken)
                .body(orders)
                .when()
                .post(ORDERS_CREATE)
                .then();
    }
}
