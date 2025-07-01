package ordertest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;

@DisplayName("Тесты получения заказов авторизованным пользователем")
public class GetOrdersWithAuthTest extends BaseOrderTest {

    @Before
    public void setUp() {
        super.setUp();
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    @Test
    @DisplayName("Получение заказов авторизованным пользователем")
    public void shouldGetOrdersWithAuth() {
        Order order = new Order();
        List<String> ingredients = new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
        order.setIngredients(ingredients);
        orderClient.createOrderWithAuth(accessToken, order);

        ValidatableResponse getOrdersResponse = orderClient.getOrdersWithAuth(accessToken);

        int statusCode = getOrdersResponse.extract().statusCode();
        boolean areOrdersRetrieved = getOrdersResponse.extract().path("success");

        assertEquals("Ожидается статус 200", SC_OK, statusCode);
        assertTrue("Ожидается успешное получение заказов", areOrdersRetrieved);
    }
}