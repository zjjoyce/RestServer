package com.asiainfo.ocmanager.rest.resource.dacp;

import com.asiainfo.ocmanager.dacp.DacpAllResult;
import com.asiainfo.ocmanager.monitor.client.RestClient;
import com.asiainfo.ocmanager.monitor.entity.AppExtraEntity;
import com.asiainfo.ocmanager.persistence.model.ServiceInstance;
import com.asiainfo.ocmanager.persistence.model.Tenant;
import com.asiainfo.ocmanager.persistence.model.TenantUserRoleAssignment;
import com.asiainfo.ocmanager.persistence.model.UserRoleView;
import com.asiainfo.ocmanager.rest.bean.AdapterResponseBean;
import com.asiainfo.ocmanager.rest.constant.Constant;
import com.asiainfo.ocmanager.rest.resource.executor.TenantResourceAssignRoleExecutor;
import com.asiainfo.ocmanager.rest.resource.executor.TenantResourceCreateInstanceBindingExecutor;
import com.asiainfo.ocmanager.rest.resource.executor.TenantResourceUnAssignRoleExecutor;
import com.asiainfo.ocmanager.rest.resource.executor.TenantResourceUpdateRoleExecutor;
import com.asiainfo.ocmanager.rest.resource.quotaUtils.quotaQuery;
import com.asiainfo.ocmanager.rest.resource.utils.ServiceInstancePersistenceWrapper;
import com.asiainfo.ocmanager.rest.resource.utils.TURAssignmentPersistenceWrapper;
import com.asiainfo.ocmanager.rest.resource.utils.TenantPersistenceWrapper;
import com.asiainfo.ocmanager.rest.resource.utils.UserRoleViewPersistenceWrapper;
import com.asiainfo.ocmanager.rest.utils.DFPropertiesFoundry;
import com.asiainfo.ocmanager.rest.utils.SSLSocketIgnoreCA;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.io.Closeable;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.asiainfo.ocmanager.dacp.DacpAllResult;
/**
 *
 * @author zhaoyim
 *
 */

@Path("/tenant")
public class DacpResource {

	private static Logger logger = Logger.getLogger(DacpResource.class);



	/**
	 * dacp sync
	 *
	 * @param tenant
	 *            tenant obj json
	 * @return new tenant info
	 */
	@GET
    @Path("/dacp")
	@Produces((MediaType.APPLICATION_JSON + ";charset=utf-8"))

	public Response syncDacp(Tenant tenant, @Context HttpServletRequest request) {

        String tenantString = request.getParameter("tenants");
       String[] tenants = tenantString.split(",");
       for(int i=0;i<tenants.length;i++){
           DacpAllResult.getAllResult(tenants[0]);
       }
        return Response.ok().entity("yeah,sync ok!").build();
    }


}
