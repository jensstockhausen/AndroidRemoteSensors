package de.famst.jens.remotesensors.http;

import java.io.IOException;

import de.famst.jens.remotesensors.biz.Orientation;
import fi.iki.elonen.NanoHTTPD;

/**
 * Created by jens on 30/08/14.
 */
public class HttpServer extends NanoHTTPD
{
    private Orientation orientation;

    public HttpServer(int port) throws IOException
    {
        super(port);
    }

    public void setOrientation(Orientation orientation)
    {
        this.orientation = orientation;
    }

    @Override
    public Response serve(IHTTPSession session)
    {
        String html = "";

        html += "<html><head><head><body>";
        if (orientation != null)
        {
            html += orientation.toString();
        } else
        {
            html += "-- -- --";
        }
        html += "</body></html>";

        return new Response(html);
    }

}
