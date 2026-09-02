package stepdefs;

import factory.PageManager;
import hooks.Hooks;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoogleSteps {
    private static final Logger logger = LoggerFactory.getLogger(GoogleSteps.class);
    private final PageManager pageManager;

    public GoogleSteps(Hooks hooks) {
        this.pageManager = Hooks.getPageManager();
    }

    @Given("User Navigates to Google Homepage")
    public void navigateToGoogle() {
        try {
            logger.info("Trying to Navigate to Google");
            pageManager.getGooglePage().navigateToUrl();
            logger.info("Navigation to Google Failed!");
        } catch(Exception e) {
            logger.info("Unexpected Error Occured While Trying to Navigate to Google");
        }
    }

    @Then("User clicks a screenshot of the page")
    public void clickScreenshot() {
        try {
            logger.info("Trying to take a screenshot");
            pageManager.getGooglePage().takeScreenshot();
            logger.info("Screenshot taken successfully");
        } catch(Exception e) {
            logger.info("Unexpected Error Occured While Trying to Take Screenshot");
        }
    }
}
