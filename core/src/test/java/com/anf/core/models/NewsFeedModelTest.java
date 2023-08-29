package com.anf.core.models;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.apache.sling.testing.mock.sling.ResourceResolverType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

/**
 * Begin Code - Barkatulla Khan
 */

@ExtendWith(AemContextExtension.class)
public class NewsFeedModelTest {

    private final AemContext context = new AemContext(ResourceResolverType.JCR_MOCK);

    @BeforeEach
    public void setUp() throws Exception {

    }

    @Test
    public void testGetNewsData() throws Exception {
        context.create().resource("/var/commerce/products/anf-code-challenge/newsData");
        context.create().resource("/var/commerce/products/anf-code-challenge/newsData/mews_0", "jcr:title", "News0",
                "author", "Caroline Fox");
        context.create().resource("/var/commerce/products/anf-code-challenge/newsData/mews_1", "jcr:title", "News1",
                "author", "Leah Askarinam");

        NewsFeedModel newsFeedModel = context.request().adaptTo(NewsFeedModel.class);

        List<Map<String, String>> newsFeeds = newsFeedModel.getNewsData();
        assertAll(
                () -> assertEquals(2, newsFeeds.size()),
                () -> assertEquals("Caroline Fox", newsFeeds.get(0).get("author")),
                () -> assertEquals("Leah Askarinam", newsFeeds.get(1).get("author")));
    }

    @Test
    void testGetNewsData_Exception() throws Exception {
        NewsFeedModel newsFeedModel = context.request().adaptTo(NewsFeedModel.class);

        List<Map<String, String>> newsFeeds = newsFeedModel.getNewsData();
        assertEquals(0, newsFeeds.size());
    }

    @Test
    void testGetCurrentDate() throws Exception {
        NewsFeedModel newsFeedModel = context.request().adaptTo(NewsFeedModel.class);

        assertEquals(LocalDate.now().toString(), newsFeedModel.getCurrentDate());
    }

}

/**
 * END Code
 */
