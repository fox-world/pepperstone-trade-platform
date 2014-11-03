package com.elance.util;

import java.math.BigDecimal;

public class OrderUtil {
	
	
	public static double[] calculateOrderLots(double totalLots,double maxLots) {
		double tempLots=0;
		int num=0;
		while(true){
			if(tempLots>totalLots){
				break;
			}else{
				num++;
				tempLots=num*maxLots;
			}
		}
		
		double[] arrs=new double[num];
		for(int i=0;i<num;i++){
			if(i==(num-1)){
				BigDecimal totalLotsBig=new BigDecimal(Double.toString(maxLots)).multiply(new BigDecimal(i));
		    	arrs[i]=new BigDecimal(Double.toString(totalLots)).subtract(totalLotsBig).doubleValue();
			}else{
				arrs[i]=maxLots;
			}
		}
		
		return arrs;
	}
	
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
