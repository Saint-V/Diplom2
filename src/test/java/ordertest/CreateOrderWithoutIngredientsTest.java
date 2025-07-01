package ordertest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.junit.Assert.*;

@DisplayName("Тесты создания заказа без ингредиентов")
public class CreateOrderWithoutIngredientsTest extends BaseOrderTest {

    @Before
    public void setUp() {
        super.setUp();
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void shouldNotCreateOrderWithoutIngredients() {
        ValidatableResponse createOrderResponse = orderClient.createOrderWithoutIngredients(accessToken);

        int statusCode = createOrderResponse.extract().statusCode();
        boolean isOrderCreated = createOrderResponse.extract().path("success");
        String errorMessage = createOrderResponse.extract().path("message");

        assertEquals("Ожидается статус 400", SC_BAD_REQUEST, statusCode);
        assertFalse("Ожидается неуспешное создание заказа", isOrderCreated);
        assertEquals("Ожидается сообщение об ошибке", "Ingredient ids must be provided", errorMessage);
    }
}
