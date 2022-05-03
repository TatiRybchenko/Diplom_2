package diplom2;

import com.github.javafaker.Faker;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;
import java.util.Locale;

@Data
@Builder
public class User {
    private String email;
    private String password;
    private String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
}
    @Step("Генерация  значений (email адрес, пароля, имени) для создания акаунта пользователя")
    public static User getDataFaker(){
        Faker faker = new Faker(Locale.ENGLISH);
        final String userEmailAddress = faker.internet().emailAddress();
        final String userPassword = faker.code().ean8();
        final String userName = faker.name().fullName();

        Allure.addAttachment("email адрес пользователя:", userEmailAddress);
        Allure.addAttachment("Пароль пользователя:", userPassword);
        Allure.addAttachment("Имя пользователя:", userName);

        return new User(userEmailAddress, userPassword, userName);
    }

}

