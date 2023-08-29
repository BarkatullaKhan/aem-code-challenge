package com.anf.core.servlets;

import com.adobe.granite.ui.components.ds.DataSource;
import com.adobe.granite.ui.components.ds.SimpleDataSource;
import com.adobe.granite.ui.components.ds.ValueMapResource;
import com.day.crx.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceMetadata;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.api.wrappers.ValueMapDecorator;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.Servlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Begin Code - Barkatulla Khan
 */

@Component(service = Servlet.class, property = {
        Constants.SERVICE_DESCRIPTION + "=Countries Json Data in dynamic Dropdown",
        "sling.servlet.paths=" + "/bin/jsonDataDropdown", "sling.servlet.methods=" + HttpConstants.METHOD_GET
})
public class GetCountriesServlet extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetCountriesServlet.class);
    private static final String JSON_RESOURCE_PATH = "/content/dam/anf-code-challenge/exercise-1/countries.json/jcr:content/renditions/original";

    private ResourceResolver resourceResolver;
    private Resource pathResource;
    private ValueMap valueMap;
    private List<Resource> resourceList;

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {

        resourceResolver = request.getResourceResolver();
        pathResource = request.getResource();
        resourceList = new ArrayList<>();

        try {
            InputStream inputStream = request.getResourceResolver()
                    .getResource(JSON_RESOURCE_PATH)
                    .adaptTo(InputStream.class);

            StringBuilder stringBuilder = new StringBuilder();
            String eachLine;
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            while ((eachLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(eachLine);
            }

            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            Iterator<String> jsonKeys = jsonObject.keys();
            // Iterating JSON Objects over key
            while (jsonKeys.hasNext()) {
                String jsonKey = jsonKeys.next();
                String jsonValue = jsonObject.getString(jsonKey);
                valueMap = new ValueMapDecorator(new HashMap<>());
                valueMap.put("value", jsonValue);
                valueMap.put("text", jsonKey);
                resourceList.add(
                        new ValueMapResource(resourceResolver, new ResourceMetadata(), JcrConstants.NT_UNSTRUCTURED, valueMap));
            }

            DataSource dataSource = new SimpleDataSource(resourceList.iterator());
            request.setAttribute(DataSource.class.getName(), dataSource);

        } catch (JSONException | IOException e) {
            LOGGER.error("Error in Json Data Exporting : {}", e.getMessage());
        }
    }
}

/**
 * END Code
 */