package com.songming.sanitation.user.model;

import java.util.List;

public class ContactsBean {
	
	private Long id;
	private List<UserDto> list;
	
	public ContactsBean(Long id, List<UserDto> list) {
		super();
		this.id = id;
		this.list = list;
	}
	
	public ContactsBean() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<UserDto> getList() {
		return list;
	}
	public void setList(List<UserDto> list) {
		this.list = list;
	}

}
