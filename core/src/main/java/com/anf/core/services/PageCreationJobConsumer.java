package com.anf.core.services;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anf.core.constants.AnfConstants;
import com.anf.core.utils.ResourceResolverUtil;

/**
 * Begin Code - Barkatulla Khan
 */
@Component(service = JobConsumer.class, immediate = true, property = {
        JobConsumer.PROPERTY_TOPICS + "=anf/pageCreationJob"
})
public class PageCreationJobConsumer implements JobConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(PageCreationJobConsumer.class);

    @Reference
    private ResourceResolverFactory resolverFactory;
    
    Session session = null;
    ResourceResolver resolver = null;

    Resource resource;

    Node pageNode;

    @Override
    public JobResult process(Job job) {
        try {

            String path = (String) job.getProperty(AnfConstants.PATH);
            if (path.endsWith(AnfConstants.JCR_CONTENT)) {
                resolver = ResourceResolverUtil.getNewResolver(resolverFactory);
                session = resolver.adaptTo(Session.class);
                resource = resolver.getResource(path);
                pageNode = resource.adaptTo(Node.class);
                pageNode.setProperty(AnfConstants.PAGE_CREATED, true);
                session.save();
            }
            return JobResult.OK;
        } catch (Exception e) {
            LOG.info(AnfConstants.JOB_CONSUMER_ERROR, e.getMessage());
            return JobResult.FAILED;
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