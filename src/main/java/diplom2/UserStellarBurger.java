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

        Allure.addAttachment("emailAddress пользователя: ", faker.internet().emailAddress());
        Allure.addAttachment("Пароль пользователя:", faker.code().ean8());
        Allure.addAttachment("Имя пользователя", faker.name().fullName());

        return new UserStellarBurger(faker.internet().emailAddress(), faker.code().ean8(), faker.name().fullName());
    }

}

