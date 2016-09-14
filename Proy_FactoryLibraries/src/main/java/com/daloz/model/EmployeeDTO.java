package com.daloz.model;

public class EmployeeDTO
{
	private Long id;
	private String name;
	private	UserDTO userDTO;
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public UserDTO getUserDTO()
	{
		return userDTO;
	}
	public void setUserDTO(UserDTO userDTO)
	{
		this.userDTO = userDTO;
	}
	
	
}
