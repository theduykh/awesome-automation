package pages;

import org.theduykh.ata.test.AtaPage;

public class LoginPage extends AtaPage {

    public LoginPage openLoginPage() {
        I.navigate("https://the-internet.herokuapp.com/login");
        return this;
    }

    public LoginPage doLogin(String username, String password) {
        enterUsername(username).enterPassword(password).clickLoginButton();
        return this;
    }

    public LoginPage enterUsername(String username) {
        I.fillText("#login_form_username", username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        I.fillText("#login_form_password", password);
        return this;
    }

    public LoginPage clickLoginButton() {
        I.click("//button[@type='submit']");
        return this;
    }

}
