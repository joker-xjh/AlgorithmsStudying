package hackerrank;

import java.util.Arrays;

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
	
	
	
	static int diagonalDifference(int[][] a) {
        long sum1 = 0, sum2 = 0;
        int n = a.length;
        for(int i=0, j= n-1; i<n; i++, j--) {
        	sum1 += a[i][i];
        	sum2 += a[i][j];
        }
        return (int) Math.abs(sum1 - sum2);
    }
	
	
	
	static void plusMinus(int[] arr) {
        int n = arr.length;
        double positive = 0, negative = 0, zero = 0;
        for(int num : arr) {
        	if(num > 0)
        		positive++;
        	else if(num < 0)
        		negative++;
        	else 
        		zero++;
        }
        System.out.println(positive / n);
        System.out.println(negative / n);
        System.out.println(zero / n);
    }
	
	
	static void staircase(int n) {
        int space = n-1;
        for(int i=0; i<n; i++) {
        	for(int j=0; j<space; j++)
        		System.out.print(' ');
        	for(int j=0; j<n-space; j++)
        		System.out.print('#');
        	space--;
        	System.out.println();
        }
    }
	
	
	
	static void miniMaxSum(int[] arr) {
        Arrays.sort(arr);
        long min = 0;
        long max = 0;
        for(int i=0; i<4; i++)
        	min += arr[i];
        for(int i=4; i>0; i--)
        	max += arr[i];
        System.out.println(min +" "+ max);
    }
	
	static int birthdayCakeCandles(int n, int[] ar) {
        int candles = 0;
        int max = -1;
        for(int i=0; i<n; i++)
        	if(ar[i] > max)
        		max = ar[i];
        for(int i=0; i<n; i++)
        	if(ar[i] == max)
        		candles++;
        return candles;
    }
	
	
	static String timeConversion(String s) {
        String time = "";
        String head = s.substring(0, 2);
        if(s.charAt(s.length()-2) == 'A') {
        	if(head.equals("12")) {
            	return "00" + s.substring(2, s.length()-2);
            }
        	return s.substring(0, s.length()-2);
        }
        
        if(head.equals("12")) {
        	return s.substring(0, s.length()-2);
        }
        
        String trans = String.valueOf(Integer.parseInt(head) + 12);
        time = trans + s.substring(2, s.length()-2);
        return time;
    }
	
	
	

	public static void main(String[] args) {
		

	}

}
