package de.famst.jens.remotesensors.http;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by jens on 30/08/14.
 */
public class HttpServer extends NanoHTTPD
{
    public HttpServer(int port) throws IOException
    {
        super(port);
    }

    @Override
    public Response serve(IHTTPSession session)
    {
        final String html = "<html><head><head><body><h1>Hello, World</h1></body></html>";
        return new Response(html);
    }

}
