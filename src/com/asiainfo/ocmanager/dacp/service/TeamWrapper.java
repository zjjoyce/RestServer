package com.asiainfo.ocmanager.dacp.service;

import com.asiainfo.ocmanager.dacp.model.Team;
import com.asiainfo.ocmanager.persistence.model.Tenant;
import com.asiainfo.ocmanager.rest.resource.utils.TenantPersistenceWrapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;

/**
 * Created by yujin on 2017/7/4.
 */

public class TeamWrapper {

    /**
     * get Team from tenant
     * @param tenantId
     * @return
     */
    public static Team getTeamFromTenant(String tenantId){
        Tenant tenant = TenantPersistenceWrapper.getTenantById(tenantId);
        if(tenant.equals(null)){
            Team team = new  Team("","","","","","ON","/dacp-res/dps/img/team1.png","");
            return team;
        }
        String xmlId = tenant.getId();
        String teamCode = String.valueOf(tenant.getDacpTeamCode());
        String teamType = String.valueOf(tenant.getLevel());
        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        String teamName = tenant.getName();
        Team team = new Team(xmlId,teamCode,teamName,teamType,dateNowStr,"ON","/dacp-res/dps/img/team1.png","");
        return team;
    }

}
