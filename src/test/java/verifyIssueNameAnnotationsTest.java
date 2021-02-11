import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.javafaker.Faker;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class verifyIssueNameAnnotationsTest extends testBase {

    Faker faker = new Faker();
    String base_url = "https://github.com/",
            login = "AnnaPedych-testaccount",
            password = "Qaguru123",
            repository = "AnnaPedych/QAGURU_AT_Allure_Homework1",
            issue_name = faker.harryPotter().character();

    @Test
    public void createAndCheckIssueTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        //Issue creation
        open(base_url);
        $(".header-search-input").setValue(repository).pressEnter();
        $(By.linkText(repository)).click();
        $(withText("Issues")).click();
        $(byText("New issue")).click();
        $("[role=dialog]").$(withText("Sign in")).click();
        $("[name=login]").setValue(login);
        $("[name=password]").setValue(password);
        $("[value='Sign in']").click();
        $("#issue_title").setValue(issue_name);
        $(withText("Submit new issue")).click();
        //Issue name verification
        open(base_url);
        $(".header-search-input").setValue(repository).pressEnter();
        $(By.linkText(repository)).click();
        $(withText("Issues")).click();
        $(withText(issue_name)).should(Condition.exist);
    }
}
