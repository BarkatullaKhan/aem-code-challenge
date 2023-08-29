package com.anf.core.models;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ResourcePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anf.core.constants.AnfConstants;

/**
 * Begin Code - Barkatulla Khan
 */
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class NewsFeedModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsFeedModel.class);

    @ResourcePath(path = AnfConstants.NEWS_DATA_PATH)
    Resource newsFeedResource;

    List<Map<String, String>> newsFeeds = new LinkedList<>();
    Iterator<Resource> newsFeedList;

    /**
     * Retrive and return news lists
     */
    public List<Map<String, String>> getNewsData() {
        try {

            newsFeedList = newsFeedResource.listChildren();
            while (newsFeedList.hasNext()) {
                Resource currentResource = newsFeedList.next();
                PropertyIterator pItre = currentResource.adaptTo(Node.class).getProperties();
                Map<String, String> newsMap = new HashMap<>();
                while (pItre.hasNext()) {
                    Property prop = pItre.nextProperty();
                    newsMap.put(prop.getName(), prop.getValue().getString());
                }
                newsFeeds.add(newsMap);
            }
            LOGGER.info("newsFeeds :: {}", newsFeeds);

        } catch (Exception e) {
            LOGGER.error("NewsFeed error : {}", e.getMessage());
            return Collections.emptyList();
        }
        return newsFeeds;
    }

    public String getCurrentDate() {

        return "" + LocalDate.now();
    }
}
/**
 * END Code
 */
