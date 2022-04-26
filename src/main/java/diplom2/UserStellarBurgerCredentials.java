package diplom2;

public class UserStellarBurgerCredentials {

    public String email;
    public String password;

    public UserStellarBurgerCredentials(String email, String password) {
        this.email= email;
        this.password = password;
    }

    public static UserStellarBurgerCredentials from(UserStellarBurger userStellarBurger) {
        return new UserStellarBurgerCredentials(userStellarBurger.getEmail(), userStellarBurger.getPassword());
    }
}
