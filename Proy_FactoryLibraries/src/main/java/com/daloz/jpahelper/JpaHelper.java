package com.daloz.jpahelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JpaHelper
{
	@SuppressWarnings("unused")
	private String queryHql, declared, condition, nameClassJoin, nameAlliedClassJoin, nameClassMaster, nameAlliedClassMaster;
	
	private Object object;

	private List<BagItem> bagItems;
	
	public JpaHelper(String condition, Object object)
	{
		this.queryHql = condition;
		this.object = object;
		
		splitCondition();
		populateNameAttributesAndValueAttributes();
	}

	private void splitCondition()
	{
		declared = queryHql.split("WHERE")[0];
		condition = queryHql.split("WHERE")[1];

		nameClassJoin = declared.split("JOIN")[1].trim().split(" ")[0].substring(2);
		nameAlliedClassJoin = declared.split("JOIN")[1].trim().split(" ")[1];

		nameClassMaster = declared.split("FROM")[1].trim().split(" ")[0];
		nameAlliedClassMaster = declared.split("FROM")[0].trim().split(" ")[1];
	}

	private void populateNameAttributesAndValueAttributes()
	{
		try
		{
			//Se parte toda la condicion por el vacio en criterios
			String[] arrayCriteria = condition.trim().split(" ");
			
			//Instanciamos las lista.
			bagItems = new ArrayList<>();
			
			for (int i = 0; i < arrayCriteria.length; i++)
			{

				if (arrayCriteria[i].length() > 1 && arrayCriteria[i].substring(0, 2).equals(nameAlliedClassMaster + "."))
				{
					BagItem bagItem = new BagItem();
					
					// Ingresando nombre del atributo
					bagItem.setNameAttribute(arrayCriteria[i].substring(2));

					Field field = object.getClass().getDeclaredField(arrayCriteria[i].substring(2));
					field.setAccessible(true);

					bagItem.setValueAttribute(field.get(object));
					
					//Añadiendo a la lista
					bagItems.add(bagItem);
				} 
				else if (arrayCriteria[i].length() > 1 && arrayCriteria[i].substring(0, 2).equals(nameAlliedClassJoin + "."))
				{
					BagItem bagItem = new BagItem();
					
					// Ingresando nombre del atributo
					bagItem.setNameAttribute(arrayCriteria[i].substring(2));
					
					Field field = object.getClass().getDeclaredField(nameClassJoin);
					field.setAccessible(true);
					
					Object objectj = field.get(object);
					
					Field fieldj =objectj.getClass().getDeclaredField(arrayCriteria[i].substring(2));
					fieldj.setAccessible(true);
					
					bagItem.setValueAttribute(fieldj.get(objectj));
					
					//Añadiendo a la lista
					bagItems.add(bagItem);
				}

			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public List<BagItem> getBagItems()
	{
		return bagItems;
	}
}
