package com.tenzhao.common.criterion;

public enum LockMode {
	NONE( 0, "none" ),
	LOCK( 5, "read" );
	
	private final int level;
	private final String mode;

	private LockMode(int level, String mode) {
		this.level = level;
		this.mode = mode;
	}
	
	public String toString(){
		String lock = "" ;
		switch(this) {
		case LOCK :
			lock = " for update" ;
			break;
		default :
			break ;
		}
		return lock;
	}

	public int getLevel() {
		return level;
	}

	public String getMode() {
		return mode;
	}
}
