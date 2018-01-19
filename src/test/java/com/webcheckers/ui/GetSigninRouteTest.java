package com.webcheckers.ui;

import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by King David Lawrence on 10/30/2017.
 */
public class GetSigninRouteTest {

    private GetSigninRoute CuT;
    private Request request;
    private Response response;

    @Before
    public void testSetup() {
        CuT = new GetSigninRoute(mock(TemplateEngine.class));
        request = mock(Request.class);
        response = mock(Response.class);
    }

    @Test(expected = NullPointerException.class)
    public void ctor_Test() {
        final GetSigninRoute ctor = new GetSigninRoute(null);
    }

    @Test (expected = NullPointerException.class)
    public void handleTest_InvalidRequest() {
        CuT.handle(null, response);
    }

    @Test (expected = NullPointerException.class)
    public void handleTest_InvalidResponse() {
        CuT.handle(request, null);
    }
}