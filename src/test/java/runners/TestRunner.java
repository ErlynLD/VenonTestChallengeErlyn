package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/java/features",
        glue = "stepdefinitions",
        tags = "@erlyn",
        plugin = {"html:target/cucumber-report/cucumber.html"}
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
