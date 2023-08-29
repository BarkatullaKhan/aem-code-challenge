package com.anf.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anf.core.constants.AnfConstants;

@Component(service = Servlet.class, immediate = true, property = {
		Constants.SERVICE_DESCRIPTION
				+ "=JSON Servlet to read first 10 pages with property anfCodeChallenge  exists using JCR SQL 2",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=" + "/bin/sql2/search_pages_having_anfCodeChallenge",
		"sling.servlet.extensions=" + "json" })
public class FetchPagesWithJcrSQL2Servlet extends SlingAllMethodsServlet {
	JSONObject resultJson = new JSONObject();

	private static final long serialVersionUID = 1740240983848618567L;

	private static final Logger LOG = LoggerFactory.getLogger(FetchPagesWithJcrSQL2Servlet.class);

	private ResourceResolver resolver ;
	private Session session ;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		try {

			String queryString = createQuery();
			if (queryString == null) {
				LOG.info(AnfConstants.NULL_QUERY_STRING);
				return;
			}
			PrintWriter out = response.getWriter();
			resolver = request.getResourceResolver();
			session = resolver.adaptTo(Session.class);
			Query query = session.getWorkspace().getQueryManager().createQuery(queryString, Query.JCR_SQL2);
			query.setLimit(10);
			query.setOffset(0);
			QueryResult results = query.execute();
			NodeIterator nodes = results.getNodes();

			while (nodes.hasNext()) {
				Node nextNode = nodes.nextNode();
				resultJson.put(nextNode.getParent().getName(), nextNode.getParent().getPath());
			}
			out.println(resultJson);
			out.flush();
			out.close();
		} catch (Exception e) {
			LOG.error(AnfConstants.ERROR_IN_SEARCH_SERVLET, e.getMessage());
		} finally {
			if (session != null && session.isLive()) {
				session.logout();
			}
			if (resolver != null && resolver.isLive()) {
				resolver.close();
			}
		}

	}

	private String createQuery() throws IOException {
		StringBuilder query = new StringBuilder(
				"SELECT * FROM [cq:PageContent] AS page WHERE ISDESCENDANTNODE(page, '/content/anf-code-challenge/us/en') AND page.[anfCodeChallenge] IS NOT NULL ORDER BY page.[jcr:created] ASC ");

		LOG.info("Query: {}", query);
		return query.toString();
	}

}
