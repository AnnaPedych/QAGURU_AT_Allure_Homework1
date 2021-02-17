import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class VerifyIssueNameAnnotationsTest extends TestBase {

    Faker faker = new Faker();
    private static final String base_url = "https://github.com/";
    String login = "AnnaPedych-testaccount",
            password = "Qaguru123",
            repository = "AnnaPedych/QAGURU_AT_Allure_Homework1",
            issueName = faker.animal().name();

    @Test
    @Owner("AnnaPedych")
    @Tags({@Tag("web"), @Tag("homework")})
    @Link(name = "Base URL", value = base_url)

    @Feature("Issues")
    @Story("Issue creation security")
    @DisplayName("Issue created after login")

    public void createAndCheckIssueTest() {
        final BaseStepsForAnnotationStyle steps = new BaseStepsForAnnotationStyle();
        steps.openMainPage(base_url);
        steps.searchForRepository(repository);
        steps.openRepository(repository);
        steps.navigateToIssuesTab();
        steps.tryToCreateNewIssue();
        steps.logInViaPopup(login, password);
        steps.createIssue(issueName);
        steps.verifyIssueIsCreatedWithCorrectName(base_url, repository, issueName);
    }

    public static class BaseStepsForAnnotationStyle {
        @Step("Open main page")
        public void openMainPage(final String base_url) {
            open(base_url);
        }

        @Step("Search for repository {repository}")
        public void searchForRepository(String repository) {
            $(".header-search-input").setValue(repository).pressEnter();}

        @Step("Open repository {repository}")
        public void openRepository(String repository) {
            $(By.linkText(repository)).click();
        }

        @Step("Navigate to Issues tab")
        public void navigateToIssuesTab() {
            $(withText("Issues")).click();
        }

        @Step("Try to create new issue")
        public void tryToCreateNewIssue() {
            $(byText("New issue")).click();
        }

        @Step("Log in via pop-up")
        public void logInViaPopup(String login, String password) {
            $("[role=dialog]").$(withText("Sign in")).click();
            $("[name=login]").setValue(login);
            $("[name=password]").setValue(password);
            $("[value='Sign in']").click();}

        @Step("Create issue {issueName}")
        public void createIssue(String issueName) {
            $("#issue_title").setValue(issueName);
            $(withText("Submit new issue")).click();}

        @Step("Verify issue is created with correct name")
        public void verifyIssueIsCreatedWithCorrectName(final String base_url, String repository, String issueName) {
            open(base_url);
            $(".header-search-input").setValue(repository).pressEnter();
            $(By.linkText(repository)).click();
            $(withText("Issues")).click();
            $(withText(issueName)).should(Condition.exist);}
    }
}