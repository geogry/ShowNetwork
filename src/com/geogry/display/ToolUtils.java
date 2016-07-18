package com.geogry.display;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToolUtils {

	public static int[] unique(int[] number){
		List<Integer> results = new ArrayList<Integer>();
		results.add(number[0]);
		int j;
		for(int i = 1; i < number.length; i++){
			for(j = 0; j < results.size(); j++)
				if(number[i] == results.get(j))
					break;
			if(j == results.size())
				results.add(number[i]);
		}
		int [] b = new int[results.size()];
		for(int i = 0; i < b.length; i++)
			b[i] = results.get(i);
		Arrays.sort(b);
		return b;
	}
}
