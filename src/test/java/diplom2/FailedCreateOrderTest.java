package diplom2;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class FailedCreateOrderTest {
    private OrdersClient ordersClient;
    private UserClient userClient;
    private User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getDataFaker();
        userClient.createUser(user);
        ordersClient = new OrdersClient();
    }
    @After
    public void tearDown(){
        ValidatableResponse loginResponse = userClient.loginUser(UserCredentials.from(user));
        String accessToken = loginResponse.extract().jsonPath().get("accessToken").toString().replace("Bearer ","");
        userClient.deleteUser(accessToken);
    }

    @Test
    @DisplayName("Выполнение запроса на создание заказа с некорректными значениями")
    @Description("Выполнение запроса на создание заказа с некорректными значениями. без ингредиентов")
    public void acceptFailedOrderWithCredentialsNoIngredients()     {
        Orders orders = Orders.builder()
                .ingredients(List.of(new String[]{}))
                .build();
        ValidatableResponse loginResponse = userClient.loginUser(UserCredentials.from(user));
        String accessToken = loginResponse.extract().jsonPath().get("accessToken").toString().replace("Bearer ","");
        ValidatableResponse createResponse = ordersClient.createCorrectOrders(orders, accessToken);
        int statusCode = createResponse.extract().statusCode();
        boolean userSuccess = createResponse.extract().jsonPath().getBoolean("success");
        String errorMessage = createResponse.extract().path("message");

        assertThat("Создание заказа не выполнилось, статус код:",statusCode,equalTo(400));
        assertFalse("Корреткное сообщение о завершение создания заказа Success", userSuccess);
        assertEquals("Ingredient ids must be provided", errorMessage);
    }

    @Test
    @DisplayName("Выполнение запроса на создание заказа с некорректными значениями")
    @Description("Выполнение запроса на создание заказа с некорректными значениями. невалидный хеш ингредиента")
    public void acceptFailedOrderWithCredentialsNonExistentIngredients()     {
        Orders orders = Orders.builder()
                .ingredients(List.of(new String[]{""}))
                .build();
        ValidatableResponse loginResponse = userClient.loginUser(UserCredentials.from(user));
        String accessToken = loginResponse.extract().jsonPath().get("accessToken").toString().replace("Bearer ","");
        ValidatableResponse createResponse = ordersClient.createCorrectOrders(orders, accessToken);
        int statusCode = createResponse.extract().statusCode();

        assertThat("Создание заказа не выполнилось, статус код:",statusCode,equalTo(500));
    }

  }
