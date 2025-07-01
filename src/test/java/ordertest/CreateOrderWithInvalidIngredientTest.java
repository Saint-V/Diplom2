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

import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.junit.Assert.assertEquals;

@DisplayName("Тесты создания заказа с неверным ингредиентом")
public class CreateOrderWithInvalidIngredientTest extends BaseOrderTest {

    @Before
    public void setUp() {
        super.setUp();
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    @Test
    @DisplayName("Создание заказа с неверным ингредиентом")
    public void shouldNotCreateOrderWithInvalidIngredient() {
        Order order = new Order();
        List<String> ingredients = new ArrayList<>(Arrays.asList("invalid_ingredient_id", "61c0c5a71d1f82001bdaaa6f"));
        order.setIngredients(ingredients);

        ValidatableResponse createOrderResponse = orderClient.createOrderWithAuth(accessToken, order);

        int statusCode = createOrderResponse.extract().statusCode();
        assertEquals("Ожидается статус 500", SC_INTERNAL_SERVER_ERROR, statusCode);
    }
}