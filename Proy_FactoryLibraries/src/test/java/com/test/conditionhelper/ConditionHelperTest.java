package com.test.conditionhelper;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.daloz.model.EmployeeDTO;
import com.daloz.model.UserDTO;



public class ConditionHelperTest
{

	@Test
	public void test()
	{
		
//		List<Object> objects = new ArrayList<>();
		
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setId(new Long(2));
		
		UserDTO user = new UserDTO();
		user.setCode("USER001");
		
		employeeDTO.setUserDTO(user);
		
		try
		{
			Field field = employeeDTO.getClass().getDeclaredField("user");
			
			System.out.println("Obteniendo el nombre del paquete: "+field.getType().getPackage().getName());
			
			field.setAccessible(true);
			
			System.out.println(field.get(employeeDTO));

		} catch (NoSuchFieldException | SecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
//		objects.add(employeeDTO);
//		
//		for (int i = 0; i < objects.size(); i++)
//		{
//			System.out.println("Mostrando clase: "+objects.get(i).getClass().getPackage().getName());
//		}
		
	}

}
