package com.daloz.jpahelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * Esta clase me va a permitir obtener:
 * 
 * - nombre del atributo.
 * - get del atributo (Metodo de acceso).
 * - invocacion del metodo del acceso.
 * 
 * @author Mandraque
 * @version 1.0
 */
public final class ReaperConditions
{
	String condition;
	Boolean isLike;
	Object object;
	List<String> listAttributeNames, listMethodNames;
	List<Object> listValueAttribute;
	
	/**
	 * Se privatiza el constructor para
	 * evitar que se instancie...
	 */
	public ReaperConditions(String condition, Object object, Boolean isLike)
	{
		this.condition = condition;
		this.object = object;
		this.isLike = isLike;
	}
	
	
	public BagItemCondition getBagItems()
	{
		BagItemCondition bagItems = new BagItemCondition();
		
		injectListAttributeNames();
		injectListMethodNames();
		injectListValueAttributes();
		
		for (int i = 0; i < listAttributeNames.size(); i++)
		{
			bagItems.addItems(listAttributeNames.get(i), listMethodNames.get(i), listValueAttribute.get(i));
		}
		
		return bagItems;
	}
	
	
	
	/**
	 * Obtenemos el nombre de cada atributo en la condicion
	 * @param condition: Es la condicion para la consulta
	 * @return
	 */
	private void injectListAttributeNames()
	{
		String[] arrayCondition = getArrayCodition(condition);
		
		listAttributeNames = new ArrayList<String>();

		for (int i = 0; i < arrayCondition.length; i++)
		{
			if (arrayCondition[i].substring(0, 1).equals(":"))
			{
				listAttributeNames.add(arrayCondition[i-2]);
			}
		}		
	}
	
	/**
	 * 
	 * @param condition: Es la condición para la consulta.
	 * @return una lista de los nombres de los metodos.
	 */
	private void injectListMethodNames()
	{
		String[] arrayCondition = getArrayCodition(condition);
		
		//Se crea e inicializa una lista de String
		listMethodNames = new ArrayList<String>();
		
		//Recorremos la condición transformada en array.
		for(int i = 0; i < arrayCondition.length; i++)
		{
			//Si un elemento del array empieza por ":"
			if(arrayCondition[i].substring(0, 1).equals(":"))
			{
				//Cortamos, capitalizamos y convertimos en char la primera letra del elemento.
				char firstCapitaLetter = Character.toUpperCase(arrayCondition[i].charAt(1));
				
				//Concantenamos para crear el nombre completo del metodo.
				String methodName = "get" + firstCapitaLetter+ arrayCondition[i].substring(2, arrayCondition[i].length());
				
				//Añadimos el item a la lista.
				listMethodNames.add(methodName);
			}
		}		
	}
	
	/**
	 * Metodo que se encarga de ejecutar los metodos de una objeto a travez
	 * del proceso de java reflect.
	 * 
	 * @param condition: Es la condicion en cadena.
	 * @param _object: Es la clase instanciada.
	 * 
	 * @return una lista con los valores que devuelve el metodo.
	 */
	private void injectListValueAttributes()
	{
		try
		{
			listValueAttribute = new ArrayList<>();
			for (int i = 0; i < listMethodNames.size(); i++)
			{
				Method method = object.getClass().getMethod(listMethodNames.get(i));
				Object objectValue = method.invoke(object, new Object[]{});			
				
				if(isLike)
				{
					listValueAttribute.add(objectValue.toString()+"%");
				}
				else
				{
					listValueAttribute.add(objectValue);
				}
			}
		} 
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			e.printStackTrace();
		}		
	}
	
	private String[] getArrayCodition(String condition)
	{
		String[] arrayConditions = condition.split("\\s+");
		return arrayConditions;
	}
}
