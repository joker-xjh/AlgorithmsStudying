package lintcode;

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

}
