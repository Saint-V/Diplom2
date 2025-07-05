package ordertest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.*;

@DisplayName("Тесты получения заказов неавторизованным пользователем")
public class GetOrdersWithoutAuthTest extends BaseOrderTest {

    @Before
    public void setUp() {
        super.setUp(); // Явно вызываем setUp() из родительского класса
    }

    @Test
    @DisplayName("Получение заказов неавторизованным пользователем")
    public void shouldNotGetOrdersWithoutAuth() {
        // Добавим проверку на null для отладки
        assertNotNull("orderClient не должен быть null", orderClient);

        ValidatableResponse getOrdersResponse = orderClient.getOrdersWithoutAuth();

        int statusCode = getOrdersResponse.extract().statusCode();
        boolean areOrdersRetrieved = getOrdersResponse.extract().path("success");
        String errorMessage = getOrdersResponse.extract().path("message");

        assertEquals("Ожидается статус 401", SC_UNAUTHORIZED, statusCode);
        assertFalse("Ожидается неуспешное получение заказов", areOrdersRetrieved);
        assertEquals("Ожидается сообщение об ошибке", "You should be authorised", errorMessage);
    }
}