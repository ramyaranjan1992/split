package payer.restservices.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import payer.restservices.annotation.UniquePassword;
import payer.restservices.annotation.UniqueUsername;



public class User {
	
	@NotBlank(message = "username can't be empty!!")
	@Size(max = 10,message = "username must be of 10 characters")
	@UniqueUsername(message = "Invalid Username !!!")
	private String merchant;
	
	@NotBlank(message = "password can't be empty!!")
	@Size(max = 6,message = "password must be of 6 characters")
	@UniquePassword(message = "Invalid password !!!")
	private String pwd;
	
	
	

	private String token;

	private String errorMsg;
	private String response;
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	@Override
	public String toString() {
		return "User [merchant=" + merchant + ", pwd=" + pwd + ", token=" + token + ", errorMsg=" + errorMsg
				+ ", response=" + response + "]";
	}

	



}
