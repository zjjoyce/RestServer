package com.asiainfo.ocmanager.dacp.model;

/**
 *
 * @author zhaoyim
 *
 */
public class HadoopResource {
	private String team_code;
	private String type;
	private Object res_cfg;
	private String status;

	public String getTeam_code() {
		return team_code;
	}

	public void setTeam_code(String team_code) {
		this.team_code = team_code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getRes_cfg() {
		return res_cfg;
	}

	public void setRes_cfg(Object res_cfg) {
		this.res_cfg = res_cfg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
