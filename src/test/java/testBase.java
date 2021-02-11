import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

public class testBase {
    @BeforeAll
    static void setup() {Configuration.startMaximized = true;}}
