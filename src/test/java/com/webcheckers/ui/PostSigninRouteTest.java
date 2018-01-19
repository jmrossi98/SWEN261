package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.PlayerLobby;
import org.junit.Before;
import org.junit.Test;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by jennamacbeth on 11/6/17.
 */
public class PostSigninRouteTest {

    private PostSigninRoute CuT;
    private Request request;
    private Response response;
    private PlayerLobby playerLobby;

    @Before
    public void testSetup() {
        CuT = new PostSigninRoute (playerLobby,mock(TemplateEngine.class));
        request = mock(Request.class);
        response = mock(Response.class);
    }

    @Test(expected = NullPointerException.class)
    public void ctor_Test() {
        final PostSigninRoute ctor = new PostSigninRoute(null, null);
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