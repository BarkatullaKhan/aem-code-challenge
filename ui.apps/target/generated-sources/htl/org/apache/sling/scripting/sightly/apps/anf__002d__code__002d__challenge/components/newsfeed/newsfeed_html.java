/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 ******************************************************************************/
package org.apache.sling.scripting.sightly.apps.anf__002d__code__002d__challenge.components.newsfeed;

import java.io.PrintWriter;
import java.util.Collection;
import javax.script.Bindings;

import org.apache.sling.scripting.sightly.render.RenderUnit;
import org.apache.sling.scripting.sightly.render.RenderContext;

public final class newsfeed_html extends RenderUnit {

    @Override
    protected final void render(PrintWriter out,
                                Bindings bindings,
                                Bindings arguments,
                                RenderContext renderContext) {
// Main Template Body -----------------------------------------------------------------------------

Object _global_clientlib = null;
Object _global_feeds = null;
Collection var_collectionvar2_list_coerced$ = null;
_global_clientlib = renderContext.call("use", "/libs/granite/sightly/templates/clientlib.html", obj());
{
    Object var_templatevar0 = renderContext.getObjectModel().resolveProperty(_global_clientlib, "all");
    {
        Object var_templateoptions1_field$_categories = (new Object[] {"anf-code-challenge.base"});
        {
            java.util.Map var_templateoptions1 = obj().with("categories", var_templateoptions1_field$_categories);
            callUnit(out, renderContext, var_templatevar0, var_templateoptions1);
        }
    }
}
out.write("\n\n<h1>News Feed</h1>\n");
_global_feeds = renderContext.call("use", com.anf.core.models.NewsFeedModel.class.getName(), obj());
out.write("\n  ");
{
    Object var_collectionvar2 = renderContext.getObjectModel().resolveProperty(_global_feeds, "newsData");
    {
        long var_size3 = ((var_collectionvar2_list_coerced$ == null ? (var_collectionvar2_list_coerced$ = renderContext.getObjectModel().toCollection(var_collectionvar2)) : var_collectionvar2_list_coerced$).size());
        {
            boolean var_notempty4 = (var_size3 > 0);
            if (var_notempty4) {
                {
                    long var_end7 = var_size3;
                    {
                        boolean var_validstartstepend8 = (((0 < var_size3) && true) && (var_end7 > 0));
                        if (var_validstartstepend8) {
                            out.write("<div class=\"newsListContainer\">");
                            if (var_collectionvar2_list_coerced$ == null) {
                                var_collectionvar2_list_coerced$ = renderContext.getObjectModel().toCollection(var_collectionvar2);
                            }
                            long var_index9 = 0;
                            for (Object news : var_collectionvar2_list_coerced$) {
                                {
                                    boolean var_traversal11 = (((var_index9 >= 0) && (var_index9 <= var_end7)) && true);
                                    if (var_traversal11) {
                                        out.write("\n    <div class=\"news\">\n      <a");
                                        {
                                            Object var_attrvalue12 = renderContext.getObjectModel().resolveProperty(news, "url");
                                            {
                                                Object var_attrcontent13 = renderContext.call("xss", var_attrvalue12, "uri");
                                                {
                                                    Object var_shoulddisplayattr15 = ((renderContext.getObjectModel().toBoolean(var_attrcontent13) ? var_attrcontent13 : ("false".equals(var_attrvalue12))));
                                                    if (renderContext.getObjectModel().toBoolean(var_shoulddisplayattr15)) {
                                                        out.write(" href");
                                                        {
                                                            boolean var_istrueattr14 = (var_attrvalue12.equals(true));
                                                            if (!var_istrueattr14) {
                                                                out.write("=\"");
                                                                out.write(renderContext.getObjectModel().toString(var_attrcontent13));
                                                                out.write("\"");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        out.write(" class=\"newsLink\">\n        <div class=\"imgcontainer\">\n          <img");
                                        {
                                            Object var_attrvalue16 = renderContext.getObjectModel().resolveProperty(news, "urlImage");
                                            {
                                                Object var_attrcontent17 = renderContext.call("xss", var_attrvalue16, "uri");
                                                {
                                                    Object var_shoulddisplayattr19 = ((renderContext.getObjectModel().toBoolean(var_attrcontent17) ? var_attrcontent17 : ("false".equals(var_attrvalue16))));
                                                    if (renderContext.getObjectModel().toBoolean(var_shoulddisplayattr19)) {
                                                        out.write(" src");
                                                        {
                                                            boolean var_istrueattr18 = (var_attrvalue16.equals(true));
                                                            if (!var_istrueattr18) {
                                                                out.write("=\"");
                                                                out.write(renderContext.getObjectModel().toString(var_attrcontent17));
                                                                out.write("\"");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        {
                                            Object var_attrvalue20 = renderContext.getObjectModel().resolveProperty(news, "title");
                                            {
                                                Object var_attrcontent21 = renderContext.call("xss", var_attrvalue20, "attribute");
                                                {
                                                    Object var_shoulddisplayattr23 = ((renderContext.getObjectModel().toBoolean(var_attrcontent21) ? var_attrcontent21 : ("false".equals(var_attrvalue20))));
                                                    if (renderContext.getObjectModel().toBoolean(var_shoulddisplayattr23)) {
                                                        out.write(" alt");
                                                        {
                                                            boolean var_istrueattr22 = (var_attrvalue20.equals(true));
                                                            if (!var_istrueattr22) {
                                                                out.write("=\"");
                                                                out.write(renderContext.getObjectModel().toString(var_attrcontent21));
                                                                out.write("\"");
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        out.write(" class=\"feedimage\"/>\n        </div>\n        <div class=\"contents\">\n          <h2>");
                                        {
                                            Object var_24 = renderContext.call("xss", renderContext.getObjectModel().resolveProperty(news, "title"), "text");
                                            out.write(renderContext.getObjectModel().toString(var_24));
                                        }
                                        out.write("</h2>\n          <div>\n            <p>");
                                        {
                                            String var_25 = ((renderContext.getObjectModel().toString(renderContext.call("xss", renderContext.getObjectModel().resolveProperty(_global_feeds, "currentDate"), "text")) + " || ") + renderContext.getObjectModel().toString(renderContext.call("xss", renderContext.getObjectModel().resolveProperty(news, "author"), "text")));
                                            out.write(renderContext.getObjectModel().toString(var_25));
                                        }
                                        out.write("</p>\n          </div>\n          <div class=\"description\">\n            <p>");
                                        {
                                            Object var_26 = renderContext.call("xss", renderContext.getObjectModel().resolveProperty(news, "description"), "text");
                                            out.write(renderContext.getObjectModel().toString(var_26));
                                        }
                                        out.write("</p>\n          </div>\n        </div>\n      </a>\n    </div>\n  ");
                                    }
                                }
                                var_index9++;
                            }
                            out.write("</div>");
                        }
                    }
                }
            }
        }
    }
    var_collectionvar2_list_coerced$ = null;
}
out.write("\n\n");


// End Of Main Template Body ----------------------------------------------------------------------
    }



    {
//Sub-Templates Initialization --------------------------------------------------------------------



//End of Sub-Templates Initialization -------------------------------------------------------------
    }

}

