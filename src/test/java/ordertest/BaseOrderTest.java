package ordertest;

import io.restassured.response.ValidatableResponse;
import order.OrderClient;
import user.User;
import user.UserClient;
import user.UserDataGenerator;

public class BaseOrderTest {
    protected OrderClient orderClient;
    protected String accessToken;
    protected UserClient userClient;
    protected User testUser;

    public void setUp() {
        userClient = new UserClient();
        orderClient = new OrderClient();
        testUser = UserDataGenerator.createUniqueUser();
        ValidatableResponse createUserResponse = userClient.createUser(testUser);
        accessToken = createUserResponse.extract().path("accessToken");
    }

    public void tearDown() {
        userClient.deleteUser(accessToken);
    }
}
