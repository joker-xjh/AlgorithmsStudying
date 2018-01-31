package hackerrank;

public class Easy {
	
	static int[] CompareTheTriplets(int a0, int a1, int a2, int b0, int b1, int b2){
        int[] result = new int[2];
        
        if(a0 > b0)
        	result[0]++;
        else if(a0 < b0)
        	result[1]++;
        
        if(a1 > b1)
        	result[0]++;
        else if(a1 < b1)
        	result[1]++;
        
        if(a2 > b2)
        	result[0]++;
        else if(a2 < b2)
        	result[1]++;
        
        return result;
    }
	
	
	
	static long aVeryBigSum(int n, long[] ar) {
        long sum = 0;
        for(long num : ar)
        	sum += num;
        return sum;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static void main(String[] args) {
		

	}

}
