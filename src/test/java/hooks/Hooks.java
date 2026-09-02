package hooks;

import com.microsoft.playwright.Page;
import factory.PageManager;
import io.cucumber.java.*;
import factory.PlaywrightFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;

public class Hooks {
    private static PlaywrightFactory playwrightFactory;
    private static Properties properties;
    private static Page page;
    private static PageManager pageManager;
    protected static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @BeforeAll
    public static void beforeAll() throws Exception {
        logger.info("=== Suite Started ===");
    }

    @Before
    public void setUp(Scenario scenario) throws Exception {
        playwrightFactory = new PlaywrightFactory();
        properties = playwrightFactory.initProperties();
        page = playwrightFactory.initBrowser(properties);
        pageManager = new PageManager(page, properties);
        logger.info("Browser initialized successfully");
    }

    @After
    public void tearDown(Scenario scenario) throws Exception {
        playwrightFactory.close();
        logger.info("Browser successfully closed");
    }


    @AfterAll
    public static void afterAll() throws Exception {
        logger.info("=== Suite Completed ===");
    }

    public static PageManager getPageManager() {
        return pageManager;
    }
}