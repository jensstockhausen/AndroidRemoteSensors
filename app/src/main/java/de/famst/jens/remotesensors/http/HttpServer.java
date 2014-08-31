package de.famst.jens.remotesensors.http;

import java.io.IOException;

import de.famst.jens.remotesensors.biz.DataModel;
import de.famst.jens.remotesensors.biz.Orientation;
import fi.iki.elonen.NanoHTTPD;

/**
 * Created by jens on 30/08/14.
 */
public class HttpServer extends NanoHTTPD
{
    private DataModel model;

    private static final String MIME_JSON = "application/json";

    public HttpServer(int port, DataModel model) throws IOException
    {
        super(port);
        this.model = model;
    }


    @Override
    public Response serve(IHTTPSession session)
    {
        if (session.getUri().startsWith("/orientation"))
        {
            Orientation orientation = model.getOrientation();
            return new Response(Response.Status.OK, MIME_JSON, orientation.toJSON());
        }

        return new Response("<html><head></head><body>online</body></html>");
    }

}
