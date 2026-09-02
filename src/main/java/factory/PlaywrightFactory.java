package factory;

import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class PlaywrightFactory {

    protected static final Logger logger = LoggerFactory.getLogger(PlaywrightFactory.class);
    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Properties prop;

    public Properties initProperties() throws IOException {
        FileInputStream inputStream = new FileInputStream("src/test/resources/config.properties");
        prop = new Properties();
        prop.load(inputStream);
        return prop;
    }

    public Page initBrowser(Properties properties) {
        String browserName = System.getProperty("browser", properties.getProperty("browser", "chromium"));
        boolean enableTracing = Boolean.parseBoolean(
                System.getProperty("enableTracing", properties.getProperty("enableTracing", "false"))
        );
        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", properties.getProperty("headless", "true"))
        );

        logger.info("Launching Browser: {}", browserName);
        logger.info("Headless Mode: {}", headless);

        playwright = Playwright.create();

        browser = switch (browserName.toLowerCase()) {
            case "chromium" -> playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setExecutablePath(Paths.get("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe"))
                    .setHeadless(headless)
                    .setSlowMo(headless ? 0 : 500));

            case "chrome" -> playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setExecutablePath(Paths.get("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe"))
                    .setHeadless(headless)
                    .setSlowMo(headless ? 0 : 500)
                    .setChannel("chrome"));

            case "msedge" -> playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setExecutablePath(Paths.get("C:\\Program Files (x86)\\Microsoft\\Edge\\Application\\msedge.exe"))
                    .setHeadless(headless)
                    .setSlowMo(headless ? 0 : 500)
                    .setChannel("msedge"));

            case "firefox" -> playwright.firefox().launch(new BrowserType.LaunchOptions()
                    .setHeadless(headless)
                    .setSlowMo(headless ? 0 : 500));

            case "webkit", "safari" -> playwright.webkit().launch(new BrowserType.LaunchOptions()
                    .setHeadless(headless)
                    .setSlowMo(headless ? 0 : 500));

            default -> throw new IllegalStateException("Unexpected browser: " + browserName);
        };

        browserContext = browser.newContext(new Browser.NewContextOptions()
                        .setViewportSize(1366, 768)
                        .setLocale("en-US")
                        .setIgnoreHTTPSErrors(true));

        if (enableTracing) {
            browserContext.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));
        }

        return browserContext.newPage();
    }

    public BrowserContext getBrowserContext() {
        return browserContext;
    }

    public void close() {
        if (browserContext != null) browserContext.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
