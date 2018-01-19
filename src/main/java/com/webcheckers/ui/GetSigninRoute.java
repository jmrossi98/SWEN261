package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import spark.*;

/**
 * @author Cole Hornbeck - cdh6380
 *         Date: 10/1/2017.
 * Builds the Signin page
 */
public class GetSigninRoute implements Route {
    private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());

    private final TemplateEngine templateEngine;
    static final String SIGNIN_PARAM = "signinName";

    /**
     * Create the Spark Route (UI controller) for the
     * {@code GET / signin} HTTP request.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public GetSigninRoute(final TemplateEngine templateEngine) {
        Objects.requireNonNull(templateEngine, "templateEngine must not be null");
        this.templateEngine = templateEngine;
        LOG.config("GetSigninRoute is initialized.");
    }

    /**
     * Render the WebCheckers Sign-In page.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   the rendered HTML for the Sign-In page
     */
    @Override
    public Object handle(Request request, Response response) {
        Session session = request.session();
        LOG.finer("GetSigninRoute is invoked");
        Map<String, Object> vm = new HashMap<>();
        if (session.attributes().contains(WebServer.SIGNIN_ERROR)) {
            vm.put("title",session.attribute(WebServer.SIGNIN_ERROR));
        } else {
            vm.put("title", "Time to Sign in!");
        }
        return templateEngine.render(new ModelAndView(vm, "signin.ftl"));
    }

}
