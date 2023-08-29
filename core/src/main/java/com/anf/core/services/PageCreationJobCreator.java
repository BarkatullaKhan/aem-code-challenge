package com.anf.core.services;

import org.apache.sling.api.SlingConstants;
import org.apache.sling.event.jobs.JobManager;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anf.core.constants.AnfConstants;

import java.util.HashMap;
import java.util.Map;
/**
 * Begin Code - Barkatulla Khan
 */
@Component(service = { EventHandler.class }, immediate = true, property = {
        EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/ADDED",
        EventConstants.EVENT_FILTER + "=(path=/content/anf-code-challenge/us/en*)",

})
public class PageCreationJobCreator implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(PageCreationJobCreator.class);

    @Reference
    JobManager jobManager;

    public void handleEvent(final Event event) {
        try {
            Map<String, Object> jobProperties = new HashMap<>();

            jobProperties.put(AnfConstants.EVENT_STRING, event.getTopic());
            jobProperties.put(AnfConstants.PATH, event.getProperty(SlingConstants.PROPERTY_PATH));

            for (String name : event.getPropertyNames()) {
                jobProperties.put(name, event.getProperty(name));
            }

            jobManager.addJob(AnfConstants.PAGE_CREATE_JOB_NAEM, jobProperties);
            LOG.info(AnfConstants.JOB_ADDED_STRING, jobProperties);

        } catch (Exception e) {
            LOG.error(AnfConstants.JOB_CREATOR_ERROR, e.getMessage());
        }
    }
}

/**
 * END Code
 */