package com.elance.util;

public class OrderUtil {
	
	public static void quickSort(Object[][] array,int start,int end) 
	{ 
		if(start<end) 
		{ 
			Object key=array[start][0];  
			int i=start;
			for(int j=start+1;j<=end;j++) {
				
				if(Integer.parseInt(String.valueOf(array[j][0]))<Integer.parseInt(String.valueOf(key)))
				{ 
					Object[] temp=array[j]; 
					array[j]=array[i+1];
					array[i+1]=temp; 
					i++; 
				} 
				
			} 
			
			Object[] temp=array[start];
			array[start]=array[i];
			array[i]=temp;
			
			array[i][0]=key; 
			quickSort(array, start, i-1);
			quickSort(array, i+1, end); 
			
		} 
		
	} 
}
