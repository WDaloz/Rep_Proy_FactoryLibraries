package com.test.jpahelper;


import java.util.List;

import com.daloz.jpahelper.BagItem;
import com.daloz.jpahelper.JpaHelper;
import com.daloz.model.EmployeeDTO;
import com.daloz.model.UserDTO;

public class Probando
{
	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
				
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setId(new Long(2));
		employeeDTO.setName("Cristhian");
		
		UserDTO user = new UserDTO();
		user.setCode("USER001");
		
//		employeeDTO.setUserDTO(user);
		
		
		String conditionA = "   SELECT x FROM EmployeeDTO INNER JOIN x.userDTO u WHERE x.id = :id AND u.code = :code AND x.name = :name ";
		String conditionB = "SELECT e FROM EmployeeDTO e WHERE e.name = :name ";
		
		List<BagItem> b =new JpaHelper(conditionA, employeeDTO).getBagItems(false);
		
		for (int i = 0; i < b.size(); i++)
		{
			System.out.println(b.get(i).getNameAttribute() +" --- "+b.get(i).getValueAttribute());
		}
	}
}
