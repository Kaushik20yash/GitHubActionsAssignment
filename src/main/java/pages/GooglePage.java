package pages;

import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import java.util.Properties;

public class GooglePage {
    private Page page;
    private Properties props;
    private static final Logger logger = LoggerFactory.getLogger(GooglePage.class);

    public GooglePage(Page page, Properties properties) {
        this.page = page;
        this.props = properties;
    }

    public void navigateToUrl() {
        logger.info("Reading base URL from properties");
        page.navigate(props.getProperty("baseUrl"));
    }

    public void takeScreenshot() {
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("GoogleScreenshot.png")));
    }

    public Page getPage() {
        return page;
    }
}
