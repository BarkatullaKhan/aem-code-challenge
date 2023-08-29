
package com.anf.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.anf.core.constants.AnfConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

/**
 * Begin Code - Barkatulla Khan
 */
@Component(service = Servlet.class, immediate = true, property = {
        Constants.SERVICE_DESCRIPTION
                + "=JSON Servlet to read first 10 pages with property anfCodeChallenge  exists using Query Builder",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths=" + "/bin/querybuilder/search_pages_having_anfCodeChallenge" })
public class FetchPagesWithQueryBuilderServlet extends SlingSafeMethodsServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetchPagesWithQueryBuilderServlet.class);

    @Reference
    private QueryBuilder queryBuilder;

    private ResourceResolver resolver ;
    private Session session ;

    public Map<String, String> getQueryPredicate() {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(AnfConstants.PATH, AnfConstants.QUERY_PATH);
        queryMap.put(AnfConstants.TYPE, AnfConstants.CQ_PAGE_TYPE);
        queryMap.put(AnfConstants.PROPERTY, AnfConstants.QERTY_PROPERTY_VALUE);
        queryMap.put(AnfConstants.PROPERTY_DOT_OPERATION, AnfConstants.OPERETION_EXISTS);
        queryMap.put(AnfConstants.ORDER_BY, AnfConstants.ORDER_BY_VALUE);
        queryMap.put(AnfConstants.ORDER_BY_SORT, AnfConstants.SORT_ASC);
        queryMap.put(AnfConstants.P_DOT_OFFSET, AnfConstants.P_DOT_OFFSET_VALUE_ZERO);
        queryMap.put(AnfConstants.P_DOT_LIMIT, AnfConstants.P_DOT_LIMIT_VALUE_TEN);
        return queryMap;
    }

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        JSONObject jsonResult = new JSONObject();

        try {
            PrintWriter out = response.getWriter();
            resolver = request.getResourceResolver();
            session = resolver.adaptTo(Session.class);

            Query query = queryBuilder.createQuery(PredicateGroup.create(getQueryPredicate()), session);

            SearchResult result = query.getResult();
            List<Hit> hits = result.getHits();

            for (Hit hit : hits) {

                Page page = hit.getResource().adaptTo(Page.class);
                jsonResult.put(page.getName(), page.getPath());
            }
            if (hits.isEmpty()) {
                response.getWriter().write(AnfConstants.NOT_FOUND);
                return;
            }
            out.println(jsonResult);
            out.flush();
            out.close();
        } catch (Exception e) {

            LOGGER.error(AnfConstants.ERROR_IN_SEARCH_SERVLET, e.getMessage());
            response.getWriter().write(AnfConstants.ERROR_IN_SEARCH_SERVLET + e.getMessage());
        } finally {
            if (session != null && session.isLive()) {
                session.logout();
            }
            if (resolver != null && resolver.isLive()) {
                resolver.close();
            }
        }
    }
}

/**
 * END Code
 */