package com.test.jpahelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.daloz.jpahelper.JpaHelper;
import com.daloz.model.EmployeeDTO;
import com.daloz.model.UserDTO;

public class Probando
{
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		
		List<HashMap<String, Object>> hashMaps = new ArrayList<>();
	
		
		
		
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setId(new Long(2));
		employeeDTO.setName("Cristhian");
		
		UserDTO user = new UserDTO();
		user.setCode("USER001");
		
		employeeDTO.setUserDTO(user);
		
		new JpaHelper("SELECT x FROM EmployeeDTO x WHERE x.id = :id AND x.name = :name", employeeDTO);
	}
}
