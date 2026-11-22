package com.bezkoder.spring.jwt.mongodb.payload.response;

public class SuccessResponse {

	public Boolean success ;

	
	public SuccessResponse(Boolean success) {
		this.success = success;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}
	
}
