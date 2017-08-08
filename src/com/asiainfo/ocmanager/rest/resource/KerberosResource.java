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
    @Path("getkeytab/{tenantId}/{username}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_OCTET_STREAM})
    public Response getFiles(@PathParam("tenantId")String tenantId,@PathParam("username")String username){
        Map map = GetFile.getFile(tenantId,username);
        String status = (String)map.get("status");
        if(status.equals("false")){
            Response.ResponseBuilder responseBuilder = Response.ok(map.get("msg"));
            responseBuilder.type("application/json");
            Response response = responseBuilder.build();
            return response;
        }else{
            Response.ResponseBuilder responseBuilder = Response.ok(map.get("file"));
            responseBuilder.type("applicatoin/octet-stream");
            responseBuilder.header("Content-Disposition", "attachment; filename="+((File)map.get("file")).getName());
            responseBuilder.header("Content-Length", Long.toString(((File)map.get("file")).length()));
            Response response = responseBuilder.build();
            return response;
        }
    }
}
