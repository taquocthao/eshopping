package com.tathao.eshopping.model.command;


import com.tathao.eshopping.model.dto.UserDTO;

public class UserCommand extends AbstractCommand<UserDTO> {

	private static final long serialVersionUID = 1L;

	private String re_password;

	public String getRe_password() {
		return re_password;
	}

	public void setRe_password(String re_password) {
		this.re_password = re_password;
	}
}
