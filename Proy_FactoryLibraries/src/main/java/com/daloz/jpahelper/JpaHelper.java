package com.daloz.jpahelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JpaHelper
{
	@SuppressWarnings("unused")
	private String queryHql, declared, condition, nameClassJoin, nameAlliedClassJoin, nameClassMaster, nameAlliedClassMaster;
	private Boolean isJoin = false;
	private Object objecMaster;

	private List<BagItem> bagItems;
	
	public JpaHelper(String condition, Object object)
	{
		this.queryHql = condition.trim();
		this.objecMaster = object;
		
		getIsJoin();
		splitCondition();
		populateNameAttributesAndValueAttributes();
	}
	
	private void getIsJoin()
	{
		declared = queryHql.split("WHERE")[0];
		
		String[] arrayDeclared = declared.split(" ");
		
		for (int i = 0; i < arrayDeclared.length; i++)
		{
			if(arrayDeclared[i].equals("JOIN"))
			{
				isJoin = true;
				break;
			}
		}
	}

	private void splitCondition()
	{
		declared = queryHql.split("WHERE")[0];
		condition = queryHql.split("WHERE")[1];

		if(isJoin)
		{
			nameClassJoin = declared.split("JOIN")[1].trim().split(" ")[0].substring(2);
			nameAlliedClassJoin = declared.split("JOIN")[1].trim().split(" ")[1];
		}

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
			
			//Recorremos el arreglo de criterios.
			for (int i = 0; i < arrayCriteria.length; i++)
			{

				Integer lengthCriteria = arrayCriteria[i].length();
				
				if ( lengthCriteria > 1 && arrayCriteria[i].substring(0, 2).equals(nameAlliedClassMaster+"."))
				{
					//Instanciamos la bolsa
					BagItem bagItem = new BagItem();
					
					// capturamos nombre del atributo
					String nameAttribute = arrayCriteria[i].substring(2);

					//Obtenemos el atributo mediante su nombre
					Field field = objecMaster.getClass().getDeclaredField(nameAttribute);
					field.setAccessible(true);

					Object objectValueAttribute = field.get(objecMaster);
					
					//Inyectando datos al objeto.
					bagItem.setNameAttribute(nameAttribute);
					bagItem.setValueAttribute(objectValueAttribute);
					
					//Añadiendo objeto a la lista.
					bagItems.add(bagItem);
				} 
				else if (isJoin == true && lengthCriteria > 1 && arrayCriteria[i].substring(0, 2).equals(nameAlliedClassJoin+"."))
				{
					//Instanciamos la bolsa
					BagItem bagItem = new BagItem();
					
					// capturamos nombre del atributo
					String nameAttribute = arrayCriteria[i].substring(2);
					
					//Obtenemos el atributo mediante su nombre
					Field field = objecMaster.getClass().getDeclaredField(nameClassJoin);
					field.setAccessible(true);
					
					//Obtenemos el valor del atributo con la instancia del objeto maestro.
					Object objectJoin = field.get(objecMaster);
					
					//Objeto que contendra el valor del atributo del objetoJoin
					Object objectValueAttribute = null;
					
					
					if(objectJoin != null)
					{
						Field fieldj =objectJoin.getClass().getDeclaredField(arrayCriteria[i].substring(2));
						fieldj.setAccessible(true);
						
						objectValueAttribute = fieldj.get(objectJoin);
					}
					
					
					//Inyectando datos al objeto.
					bagItem.setNameAttribute(nameAttribute);
					bagItem.setValueAttribute(objectValueAttribute);
					
					//Añadiendo objeto a la lista.
					bagItems.add(bagItem);
				}

			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public List<BagItem> getBagItems(Boolean conditionAttributeIsNotNutll)
	{
		if(conditionAttributeIsNotNutll)
		{
			for (int i = 0; i < bagItems.size(); i++)
			{
				if(bagItems.get(i).getValueAttribute() == null)
				{
					bagItems.remove(i);
				}
			}
			
			return bagItems;
		}
		else
		{
			return bagItems;
		}
	}
}
