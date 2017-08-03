package com.asiainfo.ocmanager.rest.resource;

import com.asiainfo.ocmanager.rest.DownloadUtils.GetFile;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by zhangfq on 2017/8/1.
 */

@Path("/kerberos")
public class KerberosResource {

    @GET
    @Path("getkeytab/{tanantId}/{username}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFiles(@PathParam("tanantId")String tanantid,@PathParam("username")String username){
        Map map = GetFile.getFile(tanantid,username);
        Response.ResponseBuilder responseBuilder = Response.ok(map.get("file"));
        responseBuilder.type("applicatoin/octet-stream");
        responseBuilder.header("Content-Disposition", "attachment; filename="+((File)map.get("file")).getName());
        responseBuilder.header("Content-Length", Long.toString(((File)map.get("file")).length()));
        Response response = responseBuilder.build();
        return response;
    }
}
