package diplom2;

import com.github.javafaker.Faker;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.Builder;
import lombok.Data;

import java.util.Locale;

@Data
@Builder
public class UserStellarBurger {
    private String email;
    private String password;
    private String name;

    public UserStellarBurger(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
}
    @Step("Генерация  значений (emailAddress, пароля, имени) для создания акаунта пользователя")
    public static UserStellarBurger getDataFaker(){
        Faker faker = new Faker(Locale.ENGLISH);
        final String userEmailAddress = faker.internet().emailAddress();
        final String userPassword = faker.code().ean8();
        final String userName = faker.name().fullName();

        Allure.addAttachment("emailAddress пользователя: ", userEmailAddress);
        Allure.addAttachment("Пароль пользователя:", userPassword);
        Allure.addAttachment("Имя пользователя", userName);

        return new UserStellarBurger(userEmailAddress, userPassword, userName);
    }

}

