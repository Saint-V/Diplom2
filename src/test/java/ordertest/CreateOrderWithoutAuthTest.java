package ordertest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.Order;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.*;

@DisplayName("Тесты создания заказа неавторизованным пользователем")
public class CreateOrderWithoutAuthTest extends BaseOrderTest {

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    @DisplayName("Создание заказа неавторизованным пользователем")
    public void shouldNotCreateOrderWithoutAuth() {
        Order order = new Order();
        List<String> ingredients = new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f"));
        order.setIngredients(ingredients);

        // Добавим проверку на null для отладки
        assertNotNull("orderClient не должен быть null", orderClient);
        assertNotNull("order не должен быть null", order);

        ValidatableResponse createOrderResponse = orderClient.createOrderWithoutAuth(order);

        int statusCode = createOrderResponse.extract().statusCode();
        boolean isOrderCreated = createOrderResponse.extract().path("success");

        assertEquals("Ожидается статус 401", SC_UNAUTHORIZED, statusCode);
        assertFalse("Ожидается неуспешное создание заказа", isOrderCreated);
    }
}