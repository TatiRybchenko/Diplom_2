package diplom2;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class GetListOrdersFromUserTest {
    private OrdersClient ordersClient;
    private UserClient userClient;
    private User user;
    private String ordersBody;


   @Before
    public void setUp() {
       ordersClient = new OrdersClient();
       userClient = new UserClient();
       user = User.getDataFaker();
       userClient.createUser(user);
    }

    @After
    public void tearDown(){
        ValidatableResponse loginResponse = userClient.loginUser(UserCredentials.from(user));
        String accessToken = loginResponse.extract().jsonPath().get("accessToken").toString().replace("Bearer ","");
        userClient.deleteUser(accessToken);
            }

    @Test
    @DisplayName("Выполнение запроса на получение списка заказа: БЕЗ авторизации пользователя")
    @Description("Выполнение запроса на получение списка заказа: БЕЗ авторизации пользователя")
    public void getOrderListNoAuth()     {
        ValidatableResponse createResponse = ordersClient.orderListAllActiveNoAuth();
        boolean userSuccess = createResponse.extract().jsonPath().getBoolean("success");
        String errorMessage = createResponse.extract().path("message");

        assertEquals("You should be authorised", errorMessage);
        assertFalse("Корреткное сообщение на получение списка заказа Success", userSuccess);
    }

    @Test
    @DisplayName("Выполнение запроса на получение списка заказа: авторизованного пользователя")
    @Description("Выполнение запроса на получение списка заказа: авторизованного пользователя")
    public void getOrderListAuth()     {
        Orders orders = Orders.builder()
                .ingredients(List.of(new String[]{"61c0c5a71d1f82001bdaaa70"}))
                .build();
        ValidatableResponse loginResponse = userClient.loginUser(UserCredentials.from(user));
        String accessToken = loginResponse.extract().jsonPath().get("accessToken").toString().replace("Bearer ","");
        ValidatableResponse createOrdersResponse = ordersClient.createCorrectOrders(orders, accessToken);
        ValidatableResponse createResponse = ordersClient.orderListAllActiveAuth(accessToken);
        boolean userSuccess = createResponse.extract().jsonPath().getBoolean("success");
        ordersBody = createOrdersResponse.extract().jsonPath().getString("orders");


        assertTrue("Корреткное сообщениена получение списка заказа Success", userSuccess);
      assertThat("Номер заказа", ordersBody, is(not(0)));


    }


}
