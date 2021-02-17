import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class VerifyIssueNameSelenideTest extends TestBase {
    Faker faker = new Faker();
    String login = "AnnaPedych-testaccount",
            password = "Qaguru123",
            repository = "AnnaPedych/QAGURU_AT_Allure_Homework1",
            issueName = faker.harryPotter().character();

    @Test
    public void createAndCheckIssueTest() {
        //Issue creation
        open(baseUrl);
        $(".header-search-input").setValue(repository).pressEnter();
        $(By.linkText(repository)).click();
        $(withText("Issues")).click();
        $(byText("New issue")).click();
        $("[role=dialog]").$(withText("Sign in")).click();
        $("[name=login]").setValue(login);
        $("[name=password]").setValue(password);
        $("[value='Sign in']").click();
        $("#issue_title").setValue(issueName);
        $(withText("Submit new issue")).click();
        //Issue name verification
        open(baseUrl);
        $(".header-search-input").setValue(repository).pressEnter();
        $(By.linkText(repository)).click();
        $(withText("Issues")).click();
        $(withText(issueName)).should(Condition.visible);
    }
}
