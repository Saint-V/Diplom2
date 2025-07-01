package usertest;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import user.User;
import user.UserClient;
import user.UserDataGenerator;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class UserCreationParameterizedNegativeTests {
    private UserClient userClient;
    private final User user;
    private final int expectedStatusCode;
    private final String expectedMessage;
    private final String testCaseDescription;

    public UserCreationParameterizedNegativeTests(
            User user,
            int expectedStatusCode,
            String expectedMessage,
            String testCaseDescription) {
        this.user = user;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedMessage = expectedMessage;
        this.testCaseDescription = testCaseDescription;
    }

    @Parameterized.Parameters(name = "{3}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {UserDataGenerator.createUserWithoutName(), SC_FORBIDDEN,
                        "Email, password and name are required fields", "Создание пользователя без имени"},
                {UserDataGenerator.createUserWithoutEmail(), SC_FORBIDDEN,
                        "Email, password and name are required fields", "Создание пользователя без email"},
                {UserDataGenerator.createUserWithoutPassword(), SC_FORBIDDEN,
                        "Email, password and name are required fields", "Создание пользователя без пароля"}
        };
    }

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Негативные тесты создания пользователя с пропущенными обязательными полями")
    public void shouldNotCreateUserWithMissingFields() {
        ValidatableResponse createUserResponse = userClient.createUser(user);
        int statusCode = createUserResponse.extract().statusCode();
        boolean isUserCreated = createUserResponse.extract().path("success");
        String actualMessage = createUserResponse.extract().path("message");

        assertEquals(testCaseDescription + ": ожидается статус " + expectedStatusCode,
                expectedStatusCode, statusCode);
        assertFalse(testCaseDescription + ": ожидается неуспешное создание пользователя",
                isUserCreated);
        assertEquals(testCaseDescription + ": ожидается сообщение об ошибке",
                expectedMessage, actualMessage);
    }
}
