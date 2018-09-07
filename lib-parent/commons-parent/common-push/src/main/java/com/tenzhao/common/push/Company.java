package com.tenzhao.common.push;

public class Company {
	private String companyId ;
	private String companyName ;
	private String imServiceNum ;
	public Company(){}
	public Company(String companyId,String companyName,String imServiceNum){
		this.companyId = companyId ;
		this.companyName = companyName ;
		this.imServiceNum = imServiceNum ;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getImServiceNum() {
		return imServiceNum;
	}
	public void setImServiceNum(String imServiceNum) {
		this.imServiceNum = imServiceNum;
	}
}
