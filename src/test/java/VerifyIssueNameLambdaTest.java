import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.parameter;
import static io.qameta.allure.Allure.step;

public class VerifyIssueNameLambdaTest extends TestBase {
    Faker faker = new Faker();
    private static final String base_url = "https://github.com/";
                         String login = "AnnaPedych-testaccount",
                                password = "Qaguru123",
                                repository = "AnnaPedych/QAGURU_AT_Allure_Homework1",
                                issue_name = faker.harryPotter().character();

    @Test
    @Owner("AnnaPedych")
    @Tags({@Tag("web"), @Tag("homework")})
    @Link(name = "Base URL", value = base_url)

    @Feature("Issues")
    @Story("Issue creation security")
    @DisplayName("Issue created with correct name after login")

    public void CreateAndCheckIssueTest() {
        parameter("User", login);
        parameter("Repository", repository);
        parameter("Issue name", issue_name);

        step("Open main page", () -> open(base_url));
        step("Search for repository", () -> {$(".header-search-input").setValue(repository).pressEnter();});
        step("Open repository", () -> $(By.linkText(repository)).click());
        step("Navigate to Issues tab", () -> $(withText("Issues")).click());
        step("Try to create new issue", () -> $(byText("New issue")).click());
        step("Log in", () -> {$("[role=dialog]").$(withText("Sign in")).click();
                                    $("[name=login]").setValue(login);
                                    $("[name=password]").setValue(password);
                                    $("[value='Sign in']").click();});
        step("Create issue", () -> {$("#issue_title").setValue(issue_name);
                                          $(withText("Submit new issue")).click();});
        step("Verify issue is created with correct name", () -> {open(base_url);
                                                    $(".header-search-input").setValue(repository).pressEnter();
                                                    $(By.linkText(repository)).click();
                                                    $(withText("Issues")).click();
                                                    $(withText(issue_name)).should(Condition.exist);});
    }
}
