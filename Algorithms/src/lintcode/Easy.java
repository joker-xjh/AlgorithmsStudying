package lintcode;

import java.util.ArrayList;
import java.util.List;

public class Easy {
	
	public int aplusb(int a, int b) {
	       return a + b;
	}
	
	
	public long trailingZeros(long n) {
       long count = 0;
       while(n > 0) {
    	   long temp = n / 5;
    	   count += temp;
    	   n = temp;
       }
       return count;
    }
	
	public int[] mergeSortedArray(int[] A, int[] B) {
        int n = A.length + B.length;
        int[] array = new int[n];
        int indexA = 0, indexB = 0, index = 0;
        while(indexA < A.length || indexB < B.length) {
        	if(indexA == A.length)
        		array[index++] = B[indexB++];
        	else if(indexB == B.length)
        		array[index++] = A[indexA++];
        	else {
        		if(A[indexA] < B[indexB]) {
        			array[index++] = A[indexA++];
        		}
        		else {
        			array[index++] = B[indexB++];
        		}
        	}
        }
        
        return array;
    }
	
	
	public void rotateString(char[] str, int offset) {
		if(str == null || str.length < 2)
			return;
		offset = offset % str.length;
        offset = str.length - offset;
        flipString(str, offset, str.length);
        flipString(str, 0, offset);
        flipString(str, 0, str.length);
    }
	
	private void flipString(char[] str, int left, int right) {
		int mid = left + (right - left) / 2;
		for(int i=left; i<mid; i++) {
			char c = str[i];
			str[i] = str[right - 1 - i + left];
			str[right - 1 - i + left] = c;
		}
	}
	
	public List<String> fizzBuzz(int n) {
        List<String> list = new ArrayList<>();
        String fizz = "fizz";
        String buzz = "buzz";
        for(int i=1; i<=n; i++) {
        	boolean three = i % 3 == 0;
        	boolean five = i % 5 == 0;
        	if(three && five)
        		list.add(fizz + " "+ buzz);
        	else if(three)
        		list.add(fizz);
        	else if(five)
        		list.add(buzz);
        	else
        		list.add(String.valueOf(i));
        }
        
        return list;
    }
	
	public int strStr(String source, String target) {
        if(source == null || target == null)
        	return -1;
        return source.indexOf(target);
    }
	
	
	
	
	
	
	
	
	
	

}
