import com.codeborne.selenide.Condition;
import data.ClientInfo;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

import utils.DataGenerator;


public class AuthTest {


    @Test
    void registValidClientActive() {

        ClientInfo info = DataGenerator.Authorization.clientInfo("us", "active");


        open("http://localhost:9999/");
        $("[name = \"login\"]").setValue(info.getLogin());
        $("[name = \"password\"]").setValue(info.getPassword());
        $("[data-test-id=\"action-login\"]").click();
        $(".heading").shouldBe(visible);

    }


    @Test
    void registValidClientBlocked() {

        ClientInfo info = DataGenerator.Authorization.clientInfo("us", "blocked");


        open("http://localhost:9999/");
        $("[name = \"login\"]").setValue(info.getLogin());
        $("[name = \"password\"]").setValue(info.getPassword());
        $("[data-test-id=\"action-login\"]").click();
        $("[data-test-id=\"error-notification\"]").shouldBe(visible);

    }


    @Test
    void registValidClientCheckWrongPassword() {

        ClientInfo info = DataGenerator.Authorization.clientInfo("us", "blocked");
        String invalidPassword = DataGenerator.Authorization.generateInvalidPassword("us");


        open("http://localhost:9999/");
        $("[name = \"login\"]").setValue(info.getLogin());
        $("[name = \"password\"]").setValue(invalidPassword);
        $("[data-test-id=\"action-login\"]").click();
        $(".notification__content").shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(visible);


    }


    @Test
    void registValidClientCheckWrongLogin() {

        ClientInfo info = DataGenerator.Authorization.clientInfo("us", "blocked");
        String invalidLogin = DataGenerator.Authorization.generateInvalidPassword("us");


        open("http://localhost:9999/");
        $("[name = \"login\"]").setValue(invalidLogin);
        $("[name = \"password\"]").setValue(info.getPassword());
        $("[data-test-id=\"action-login\"]").click();
        $(".notification__content").shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(visible);

    }


}


