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

    public HttpServer(int port, DataModel model) throws IOException
    {
        super(port);
        this.model = model;
    }

    @Override
    public Response serve(IHTTPSession session)
    {
        Orientation orientation = model.getOrientation();

        String html = "";

        html += "<html><head><head><body>";
        html += orientation.toString();
        html += "</body></html>";

        return new Response(html);
    }

}
