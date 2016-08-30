package com.daloz.jpahelper;



import java.util.ArrayList;
import java.util.List;



/**
 * Esta clase sirve para almacenar como una bolsa 
 * - nombre del atributo.
 * - nombre del get del atributo.
 * - contenido en valor del atributo.
 * 
 * @author Mandraque
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BagItemCondition extends ArrayList<BagItemCondition>
{
	private String attributeName, methodName;
	private Object valueAttribute;
	private List<BagItemCondition> bagItemConditions = new ArrayList<>();

	//Constructor
	public BagItemCondition()
	{
	}
	
	// GET
    public String getAttributeName()
	{
		return attributeName;
	}

	public String getMethodName()
	{
		return methodName;
	}

	public Object getValueAttribute()
	{
		return valueAttribute;
	}

	// Methodos auxiliares
	
	@Override
	public BagItemCondition get(int index)
	{
		return bagItemConditions.get(index);
	}
	
	@Override
	public int size()
	{
		return bagItemConditions.size();
	}
	
	@Override
	public void clear()
	{
		bagItemConditions.clear();
	}
	
	
	public void addItems(String attributeName, String methodName, Object value)
	{
		BagItemCondition bagItemCondition = new BagItemCondition();
		bagItemCondition.attributeName = attributeName;
		bagItemCondition.methodName = methodName;
		bagItemCondition.valueAttribute = value;
		
		bagItemConditions.add(bagItemCondition);
	}
}
