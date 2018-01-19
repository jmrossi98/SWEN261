package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.Null;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * @author Cole Hornbeck - cdh6380
 *         Date: 10/29/2017.
 */
public class GetGameRouteTest {
    private GetGameRoute CuT;
    private Request request;
    private Response response;

    @Before
    public void testSetup() {
        CuT = new GetGameRoute(mock(TemplateEngine.class), mock(PlayerLobby.class));
        request = mock(Request.class);
        response = mock(Response.class);
    }

    @Test (expected = NullPointerException.class)
    public void ctor_Test() {
        final GetGameRoute ctor = new GetGameRoute(null, null);
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