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

}
