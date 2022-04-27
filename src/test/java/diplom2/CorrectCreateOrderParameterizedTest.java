package diplom2;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class CorrectCreateOrderParameterizedTest {
    private  List<String> ordersIngredients;
    private OrdersClient ordersClient;
    private Orders orders;
    private UserClient userClient;
    private User user;
    private int number;
    private String name;

    public CorrectCreateOrderParameterizedTest(List<String> ordersIngredients) {
        this.ordersIngredients = ordersIngredients;
    }

    @Parameterized.Parameters
    public static Object[][] getIngredients() {
        return new Object[][] {
                {List.of("61c0c5a71d1f82001bdaaa70")},
                {List.of("61c0c5a71d1f82001bdaaa71","61c0c5a71d1f82001bdaaa6f")},
                {List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f","61c0c5a71d1f82001bdaaa75")},
                        };
    }
    @Before
    public void setUp() {
        userClient = new UserClient();
        user = User.getDataFaker();
        userClient.createUser(user);
        ordersClient = new OrdersClient();
           }

    @Test
    @DisplayName("Выполнение запроса на создание заказа с корректными значениями")
    @Description("Выполнение запроса на создание заказа с корректными значениями. Корректные значения для создания заказа изменяется {color}")
    public void ordersCreateWithValidCredentials()     {
        Orders orders = Orders.builder()
                .ingredients(ordersIngredients)
                .build();
        ValidatableResponse loginResponse = userClient.loginUser(UserCredentials.from(user));
        String accessToken = loginResponse.extract().jsonPath().get("accessToken").toString().replace("Bearer ","");
        ValidatableResponse createResponse = ordersClient.createCorrectOrders(orders, accessToken);
        int statusCode = createResponse.extract().statusCode();
        boolean userSuccess = createResponse.extract().jsonPath().getBoolean("success");
        number = createResponse.extract().path("order.number");
        name = createResponse.extract().path("name");

        assertThat("Пользователь выполнил заказ, статус код:", statusCode, equalTo(200));
        assertThat("Номер заказа", number, is(not(0)));
        assertThat("Наименование заказа", name, notNullValue());
        assertTrue("Корреткное сообщение о создание пользовтаелем заказа Success", userSuccess);
    }

}
