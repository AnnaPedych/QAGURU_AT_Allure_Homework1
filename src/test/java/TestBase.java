import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
    @BeforeAll
    static void setup() {
        Configuration.startMaximized = true;
        Configuration.baseUrl = "https://github.com/";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
}
