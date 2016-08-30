package com.daloz.itextmutable.java7;

import java.lang.reflect.Field;

public class TextMutableInfo
{
	/**
	 * Este metodo es auxiliar ayudara a obtener el nombre de los
	 * atributos  y su contenido. Sera usado el el metodo a implementar.
	 * 
	 * @param object: Es la clase instanciada u objeto. 
	 * @return la informacion de la clase instanciada en cadena.
	 */
	public static String getStringInformation(Object object)
	{
		//Obtenemos la lista de campos del tipo de clase del objeto.
		Field[] fields = object.getClass().getDeclaredFields();
		
		//Obtenemos el nombre de la clase del objeto instanciado
		//Solo nombre simple concatenada con una abertura de llave.
		String textStart = object.getClass().getSimpleName() + " [ ";
		
		//cerradura de llave.
		String textEnd = " ]";
		
		//Va a contener el nombre e informaci�n de los atributos.
		String information = "";

		//Recorremos la lista de campos.
		for (int i = 0; i < fields.length; i++)
		{
			try
			{
				//Obtenemos el primer elemento de la lista.
				Field f = fields[i];
				
				//Activamos la suprecion de comprobaci�n de acceso a los
				// atributos en el lenguaje (private)
				f.setAccessible(true);
				
				//Obtenemos el nombre del atributo y su informacion. Por ultimo concatenamos.
				information = information + f.getName() + " = " + f.get(object) + ", ";
			} 
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				e.printStackTrace();
			}

		}
		return textStart + information + textEnd;
	}
}
