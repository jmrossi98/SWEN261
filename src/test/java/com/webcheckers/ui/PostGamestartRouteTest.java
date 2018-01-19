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
 * Created by King David Lawrence on 11/8/2017.
 */
public class PostGamestartRouteTest {
    private PostGamestartRoute CuT;
    private Request request;
    private Response response;
    private PlayerLobby playerLobby;

    @Before
    public void testSetup() {
        playerLobby = mock(PlayerLobby.class);
        CuT = new PostGamestartRoute(mock(TemplateEngine.class), playerLobby);
        request = mock(Request.class);
        response = mock(Response.class);
    }

    @Test (expected = NullPointerException.class)
    public void ctor_Test() {
        final PostGamestartRoute ctor = new PostGamestartRoute(null, playerLobby);
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