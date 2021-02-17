import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.parameter;
import static io.qameta.allure.Allure.step;

public class VerifyIssueNameLambdaTest extends TestBase {
    Faker faker = new Faker();
    String login = "AnnaPedych-testaccount",
            password = "Qaguru123",
            repository = "AnnaPedych/QAGURU_AT_Allure_Homework1",
            issueName = faker.harryPotter().character();

    @Test
    @Owner("AnnaPedych")
    @Tags({@Tag("web"), @Tag("homework")})

    @Feature("Issues")
    @Story("Issue creation security")
    @DisplayName("Issue created after login")

    public void createAndCheckIssueTest() {
        parameter("User", login);
        parameter("Repository", repository);
        parameter("Issue name", issueName);

        step("Open main page", () -> open(baseUrl));
        step("Search for repository " + repository, () -> {
            $(".header-search-input").setValue(repository).pressEnter();
        });
        step("Open repository " + repository, () -> $(By.linkText(repository)).click());
        step("Navigate to Issues tab", () -> $(withText("Issues")).click());
        step("Try to create new issue", () -> $(byText("New issue")).click());
        step("Log in via pop-up", () -> {
            $("[role=dialog]").$(withText("Sign in")).click();
            $("[name=login]").setValue(login);
            $("[name=password]").setValue(password);
            $("[value='Sign in']").click();
        });
        step("Create issue " + issueName, () -> {
            $("#issue_title").setValue(issueName);
            $(withText("Submit new issue")).click();
        });
        step("Verify issue is created with correct name", () -> {
            open(baseUrl);
            $(".header-search-input").setValue(repository).pressEnter();
            $(By.linkText(repository)).click();
            $(withText("Issues")).click();
            $(withText(issueName)).should(Condition.visible);
        });
    }
}
