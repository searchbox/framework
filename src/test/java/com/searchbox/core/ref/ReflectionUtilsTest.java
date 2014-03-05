package com.searchbox.core.ref;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ReflectionUtilsTest {
	
	@Test
	public void testPermutations(){
		
	Object[][] parameters = new Object[3][];
		
		List<Object> firstParam = new ArrayList<Object>();
		firstParam.add(new Integer(10));
		firstParam.add(new Integer(20));
		parameters[0] = firstParam.toArray(new Object[0]);
		

		List<Object> secondParam = new ArrayList<Object>();
		secondParam.add(new Boolean(true));
		secondParam.add(new Boolean(false));
		parameters[1] = secondParam.toArray(new Object[0]);
		
		List<Object> thirdParam = new ArrayList<Object>();
		thirdParam.add(new String("P1"));
		thirdParam.add(new String("P2"));
		thirdParam.add(new String("P3"));
		parameters[2] = thirdParam.toArray(new Object[0]);
		
		List<Object[]> argumentBags = ReflectionUtils.findAllArgumentPermutations(parameters);
		
		for(Object[] bag:argumentBags){
			System.out.println("["+bag[0]+", "+bag[1]+", "+bag[2]+"]");
		}
	}
}
