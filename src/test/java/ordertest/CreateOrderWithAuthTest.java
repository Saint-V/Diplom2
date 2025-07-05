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

@DisplayName("Тесты создания заказа авторизованным пользователем")
public class CreateOrderWithAuthTest extends BaseOrderTest {

    @Before
    public void setUp() {
        super.setUp();
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    @Test
    @DisplayName("Создание заказа авторизованным пользователем")
    public void shouldCreateOrderWithAuth() {
        Order order = new Order();
        List<String> ingredients = new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
        order.setIngredients(ingredients);
        ValidatableResponse createOrderResponse = orderClient.createOrderWithAuth(accessToken, order);

        int statusCode = createOrderResponse.extract().statusCode();
        boolean isOrderCreated = createOrderResponse.extract().path("success");

        assertEquals("Ожидается статус 200", SC_OK, statusCode);
        assertTrue("Ожидается успешное создание заказа", isOrderCreated);
    }
}
