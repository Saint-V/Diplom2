package user;

import java.util.Random;

public abstract class UserDataGenerator {
    private static final Random random = new Random();

    private static int getRandomNumber() {
        return random.nextInt(100000);
    }

    public static String getRandomEmail() {
        return "user" + getRandomNumber() + "@test.com";
    }

    private static String getRandomPassword() {
        return "password" + getRandomNumber();
    }

    private static String getRandomName() {
        return "user" + getRandomNumber();
    }

    public static User createUniqueUser() {
        return new User(getRandomEmail(), getRandomPassword(), getRandomName());
    }

    public static User createUserWithEmailOnly() {
        return new User(getRandomEmail(), "", getRandomName());
    }

    public static User createUserWithPasswordOnly() {
        return new User("", getRandomPassword(), getRandomName());
    }

    public static User createUserWithoutName() {
        return new User(getRandomEmail(), getRandomPassword(), "");
    }

    public static User createUserWithoutEmail() {
        return new User("", getRandomPassword(), getRandomName());
    }

    public static User createUserWithoutPassword() {
        return new User(getRandomEmail(), "", getRandomName());
    }
}
