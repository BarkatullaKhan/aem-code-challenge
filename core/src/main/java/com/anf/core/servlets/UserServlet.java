/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.anf.core.servlets;

import com.anf.core.services.ContentService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Begin Code - Barkatulla Khan
 */

@Component(service = { Servlet.class })
@SlingServletPaths(value = "/bin/saveUserDetails")
public class UserServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServlet.class);

    @Reference
    private ContentService contentService;

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        BufferedReader bufferedReader = request.getReader();

        StringBuilder stringBuilder = new StringBuilder();
        String eachLine;

        while ((eachLine = bufferedReader.readLine()) != null) {
            stringBuilder.append(eachLine);
        }
        LOGGER.info(" stringBuilder {} ", stringBuilder);
        try {
            JSONObject payloadJson = new JSONObject(stringBuilder.toString());
            LOGGER.info(" jsonObject {} ", payloadJson);
            if (contentService.validateUserAge((int) payloadJson.get("age"))) {
                out.print(new JSONObject(
                        "{'successMessage':'" + contentService.commitUserDetails(payloadJson) + "'}"));
            } else {
                out.print(new JSONObject("{'errorMessage':'You are not eligible'}"));
            }

        } catch (Exception e) {
            LOGGER.error("error in UserServlet {}", e.getMessage());
        }

    }
}

/**
 * END Code
 */
