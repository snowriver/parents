package com.tenzhao.common.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Test {

	private  List<Test> lstTest ;
	private String user;
	public static void main(String[] args) {
		Test test = Test.getInstance();
		test.setUser("sd");

	}
	class SubTest extends Test{
		
	}
	
	public List<Test> getLstTest() {
		return lstTest;
	}
	public void setLstTest(List<Test> lstTest) {
		this.lstTest = lstTest;
	}

	public static Test getInstance() {
		Test test = new Test();
		if(Objects.isNull(test.lstTest)) {
			test.setLstTest( new ArrayList<Test>());
		}
		test.getLstTest().add(test);
		return test;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
