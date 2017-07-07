package com.asiainfo.ocmanager.dacp.service;

import com.asiainfo.ocmanager.dacp.model.Team;
import com.asiainfo.ocmanager.persistence.model.Tenant;
import com.asiainfo.ocmanager.rest.resource.utils.TenantPersistenceWrapper;

import java.util.ArrayList;

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
            Team team = new  Team("",0,"",1,"","ON","/dacp-res/dps/img/team1.png");
            return team;
        }
        String xmlId = tenant.getId();
        int teamCode = tenant.getDacpTeamCode();
        int teamType = tenant.getLevel();
        String teamName = tenant.getName();
        Team team = new Team(xmlId,teamCode,teamName,teamType,"","ON","/dacp-res/dps/img/team1.png");
        return team;
    }

}
