package com.asiainfo.ocmanager.dacp.service;

import com.asiainfo.ocmanager.dacp.model.UserInfo;
import com.asiainfo.ocmanager.persistence.model.Tenant;
import com.asiainfo.ocmanager.persistence.model.User;
import com.asiainfo.ocmanager.persistence.model.UserRoleView;
import com.asiainfo.ocmanager.rest.resource.utils.UserRoleViewPersistenceWrapper;

import java.util.*;

/**
 * Created by yujin on 2017/7/4.
 */
public class UserWrapper {

    /**
     * get UserInfo from tenant
     * @param tenantId
     * @return
     */
    public static List<UserInfo> getUserInfoFromUserRoleView(String  tenantId){
        //get userRoleViews from tenantId
        List<UserRoleView> userRoleViews = UserRoleViewPersistenceWrapper.getUsersInTenant(tenantId);

        Map userInfoMap = new HashMap<String,UserInfo>();
        // iterator userRoleViews in the same tenant

        Iterator<UserRoleView> iterator = userRoleViews.iterator();
        while(iterator.hasNext()){
            UserRoleView iter = iterator.next();
            String userId = iter.getUserId();
            String ocdpRoleName = iter.getRoleName();
            String roleName = "";
            if(ocdpRoleName.equals("project.admin")){
                roleName = "TeamMgr";
            }else if(ocdpRoleName.equals("team.member")){
                roleName = "TeamDev";
            }
            //if result already has this user
            if(userInfoMap.containsKey(userId)){
                UserInfo userInfo = (UserInfo)userInfoMap.get(userId);
                List<String> userInfoRole = userInfo.getRole();
                userInfoRole.add(roleName);
                userInfo.setRole(userInfoRole);
                userInfoMap.put(userId,userInfo);
            }else{
                List<String> role = new ArrayList<String>();
                role.add(roleName);
                UserInfo userInfo = new UserInfo(iter.getUserName(),iter.getUserDescription(),iter.getUserPassword(),iter.getUserPhone(),"",iter.getUserEmail(),"",role);
                userInfoMap.put(userId,userInfo);
            }

        }
        List<UserInfo> userInfos = new ArrayList<UserInfo>(userInfoMap.values());
        return userInfos;
    }
}
