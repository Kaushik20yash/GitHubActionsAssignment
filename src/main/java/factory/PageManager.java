package factory;

import pages.GooglePage;
import com.microsoft.playwright.*;

import java.util.Properties;

public class PageManager {
    private final Page page;
    private final Properties properties;
    private GooglePage googlePage;

    public PageManager(Page page, Properties props) {
        this.page = page;
        this.properties = props;
    }

    public GooglePage getGooglePage() {
        if(googlePage==null) googlePage = new GooglePage(page, properties);
        return googlePage;
    }
}
