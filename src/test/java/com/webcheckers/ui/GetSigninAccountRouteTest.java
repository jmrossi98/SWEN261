package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by kdl4u_000 on 11/27/2017.
 */
public class GetSigninAccountRouteTest {

    private GetSigninAccountRoute CuT;
    private Request request;
    private Response response;

    @Before
    public void testSetup() {
        CuT = new GetSigninAccountRoute (mock(TemplateEngine.class));
        request = mock(Request.class);
        response = mock(Response.class);
    }

    @Test (expected = NullPointerException.class)
    public void ctor_Test() {
        final GetSigninAccountRoute ctor = new GetSigninAccountRoute(null);
    }

    @Test (expected = NullPointerException.class)
    public void handleTest_InvalidRequest() {
        CuT.handle(null, response);
    }

    @Test (expected = NullPointerException.class)
    public void handleTest_InvalidResponse() {
        CuT.handle(request, null);
    }

    @Test (expected = NullPointerException.class)//If an exceptions is thrown, it should break
    public void test_handleVM() {
        CuT.handle(request, response);
    }

}