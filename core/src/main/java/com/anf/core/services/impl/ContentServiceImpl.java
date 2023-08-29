package com.anf.core.services.impl;

import com.anf.core.constants.AnfConstants;
import com.anf.core.services.ContentService;
import com.anf.core.utils.ResourceResolverUtil;
import com.day.cq.commons.jcr.JcrUtil;
import com.day.crx.JcrConstants;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Begin Code - Barkatulla Khan
 */
@Component(immediate = true, service = ContentService.class)
public class ContentServiceImpl implements ContentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContentServiceImpl.class);

    @Reference
    private ResourceResolverFactory resolverFactory;

    ResourceResolver resolver;
    Session session;
    Node node;

    @Override
    public String commitUserDetails(JSONObject userDetails) {
        String responseMsg = StringUtils.EMPTY;
        try {
            resolver = ResourceResolverUtil.getNewResolver(resolverFactory);
            session = resolver.adaptTo(Session.class);
            node = JcrUtil.createPath(AnfConstants.BASE_PATH,
                    JcrConstants.NT_UNSTRUCTURED, session);
            node.setProperty(AnfConstants.PROP_NAME_F_NAME,
                    userDetails.get(AnfConstants.USER_FIRST_NAME_KEY).toString());
            node.setProperty(AnfConstants.PROP_NAME_L_NAME,
                    userDetails.get(AnfConstants.USER_LAST_NAME_KEY).toString());
            node.setProperty(AnfConstants.PROP_NAME_AGE, userDetails.get(AnfConstants.USER_AGE_KEY).toString());
            node.setProperty(AnfConstants.PROP_NAME_COUNTRY, userDetails.get(AnfConstants.USER_COUNTRY_KEY).toString());
            session.save();
            responseMsg = AnfConstants.SAVE_USER_SUCCESS_MESSAGE;
        } catch (Exception e) {
            LOGGER.error(AnfConstants.SAVE_USER_ERROR_MESSAGE, e.getMessage());
        } finally {
            if (session != null && session.isLive()) {
                session.logout();
            }
            if (resolver != null && resolver.isLive()) {
                resolver.close();
            }
        }
        return responseMsg;
    }

    @Override
    public boolean validateUserAge(int age) {
        boolean isUserEligible = false;
        try {
            resolver = ResourceResolverUtil.getNewResolver(resolverFactory);
            Resource ageResource = resolver.getResource(AnfConstants.AGE_RESOURCE_PATH);
            Node ageNode = ageResource.adaptTo(Node.class);
            int maxAge = Integer.parseInt(ageNode.getProperty(AnfConstants.MAX_AGE_KEY).getString());
            int minAge = Integer.parseInt(ageNode.getProperty(AnfConstants.MIN_AGE_KEY).getString());
            if (age >= minAge && age <= maxAge) {
                isUserEligible = true;
            }

        } catch (Exception e) {
            LOGGER.error(AnfConstants.VALIDATE_USER_AGE_ERROR_MESSAGE, e.getMessage());
        } finally {
            if (resolver != null && resolver.isLive()) {
                resolver.close();
            }
        }
        return isUserEligible;
    }
}

/**
 * END Code
 */
