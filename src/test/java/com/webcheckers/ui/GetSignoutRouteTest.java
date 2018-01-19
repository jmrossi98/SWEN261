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
 * @author Jakob M Rossi - jmr6558@rit.edu
 *         Date: 10/30/2017.
 */
public class GetSignoutRouteTest {
    private GetSignoutRoute CuT;
    private Request request;
    private Response response;

    @Before
    public void testSetup() {
        CuT = new GetSignoutRoute(mock(PlayerLobby.class), mock(TemplateEngine.class));
        request = mock(Request.class);
        response = mock(Response.class);
    }

    @Test(expected = NullPointerException.class)
    public void ctor_Test() {
        final GetSignoutRoute ctor = new GetSignoutRoute(null, null);
    }

    @Test(expected = NullPointerException.class)
    public void handleTest_InvalidRequest() {
        CuT.handle(null, response);
    }

    @Test(expected = NullPointerException.class)
    public void handleTest_InvalidResponse() {
        CuT.handle(request, null);
    }
}