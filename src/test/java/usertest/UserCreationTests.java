package usertest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;
import user.UserClient;
import user.UserDataGenerator;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class UserCreationTests {
    private UserClient userClient;
    private User testUser;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        testUser = UserDataGenerator.createUniqueUser();
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.deleteUser(accessToken);
        }
    }

    @Test
    @DisplayName("Позитивный тест: успешное создание пользователя")
    public void shouldCreateUserSuccessfully() {
        ValidatableResponse createUserResponse = userClient.createUser(testUser);
        accessToken = createUserResponse.extract().path("accessToken");

        int statusCode = createUserResponse.extract().statusCode();
        boolean isUserCreated = createUserResponse.extract().path("success");

        assertEquals("Ожидается статус 200", SC_OK, statusCode);
        assertTrue("Ожидается успешное создание пользователя", isUserCreated);
    }

    @Test
    @DisplayName("Негативный тест: нельзя создать дубликат пользователя")
    public void shouldNotCreateDuplicateUser() {
        // Сначала создаем пользователя
        ValidatableResponse firstCreationResponse = userClient.createUser(testUser);
        accessToken = firstCreationResponse.extract().path("accessToken");

        // Пытаемся создать такого же пользователя повторно
        ValidatableResponse duplicateCreationResponse = userClient.createUser(testUser);

        int statusCode = duplicateCreationResponse.extract().statusCode();
        boolean isUserCreated = duplicateCreationResponse.extract().path("success");
        String actualMessage = duplicateCreationResponse.extract().path("message");

        assertEquals("Ожидается статус 403", SC_FORBIDDEN, statusCode);
        assertFalse("Ожидается неуспешное создание пользователя", isUserCreated);
        assertEquals("Ожидается сообщение об ошибке", "User already exists", actualMessage);
    }
}
