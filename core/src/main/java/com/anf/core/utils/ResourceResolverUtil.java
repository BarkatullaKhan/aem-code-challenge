package com.anf.core.utils;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import com.anf.core.constants.AnfConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Begin Code - Barkatulla Khan
 */
public final class ResourceResolverUtil {

    private ResourceResolverUtil() {
    }

    public static ResourceResolver getNewResolver(ResourceResolverFactory resolverFactory) throws LoginException {
        final Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(ResourceResolverFactory.SUBSERVICE, AnfConstants.ANF_SERVICE_USER);
        return resolverFactory.getServiceResourceResolver(paramMap);
    }

}
/**
 * END Code
 */