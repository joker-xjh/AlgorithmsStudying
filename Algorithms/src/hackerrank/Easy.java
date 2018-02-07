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
	
	
	static int[] solve(int[] grades){
        int[] helper = new int[20];
        int begin = 5;
        for(int i=0; i<20; i++) {
        	helper[i] = begin;
        	begin += 5;
        }
        for(int i=0; i<grades.length; i++) {
        	if(grades[i] < 38)
        		continue;
        	for(int j=0; j<20; j++) {
        		if(helper[j] <= grades[i])
        			continue;
        		if(helper[j] - grades[i] < 3)
        			grades[i] = helper[j];
        		break;
        	}
        }
		
		return grades;
    }
	
	
	static void countApplesAndOranges(int s, int t, int a, int b, int[] apples, int[] oranges) {
        int lary = 0, rob = 0;
        for(int apple : apples) {
        	int point = a + apple;
        	if(point >= s && point <= t)
        		lary++;
        }
        for(int orange : oranges) {
        	int point = b + orange;
        	if(point >= s && point <= t)
        		rob++;
        }
        System.out.println(lary);
        System.out.println(rob);
    }
	
	
	static String kangaroo(int x1, int v1, int x2, int v2) {
		if(x1 == x2)
			return "YES";
		if(x1 < x2) {
			if(v1 > v2) {
				if((x2 - x1) % (v1 - v2) == 0)
					return "YES";
			}
		}
		else{
			if(v2 > v1) {
				if((x1 - x2) % (v2 - v1) == 0)
					return "YES";
			}
		}
		return "NO";
    }
	
	
	
	static int getTotalX(int[] a, int[] b) {
        int X = 0;
        int LCM = a[0];
        for(int i=0; i<a.length; i++) {
        	LCM = lcm(LCM, a[i]);
        }
        int GCD = b[0];
        for(int i=0; i<b.length; i++) {
        	GCD = gcd(GCD, b[i]);
        }
        int temp = LCM;
        while(temp <= GCD) {
        	if(GCD % temp == 0)
        		X++;
        	temp += LCM;
        }
        
        return X;
    }
	
	static int gcd(int x, int y)  
	 {
	  int temp;
	  
	  do{
	  temp = x % y;
	  x = y;
	  y = temp;
	  }while(temp != 0); 
	  
	  return x;
	 }
	
	static int lcm(int a, int b) {
		return a * b / gcd(a, b);
	}
	
	static int[] breakingRecords(int[] score) {
        int[] answer = new int[2];
        int highest =score[0], lowest = score[0]; 
        for(int i=1; i<score.length; i++) {
        	int s = score[i];
        	if(s > highest) {
        		highest = s;
        		answer[0]++;
        	}
        	else if(s < lowest) {
        		lowest = s;
        		answer[1]++;
        	}
        }
        return answer;
    }
	
	
	static int solve(int n, int[] s, int d, int m) {
        int answer = 0;
        if(m > n)
        	return answer;
        int sum = 0;
        for(int i=0; i<m; i++)
        	sum += s[i];
        if(sum == d)
        	answer++;
        sum -= s[0];
        for(int i=m; i<s.length; i++) {
        	sum += s[i];
        	if(sum == d)
        		answer++;
        	sum -= s[i-m+1];
        }
        return answer;
    }
	
	
	static int divisibleSumPairs(int n, int k, int[] ar) {
        int pairs = 0;
        for(int i=0; i<n; i++) {
        	int one = ar[i];
        	for(int j=i+1; j<n; j++) {
        		int two = ar[j];
        		if((one + two) % k == 0)
        			pairs++;
        	}
        }
        
        return pairs;
    }
	
	
	
	static int migratoryBirds(int n, int[] ar) {
        int type = -1;
        int[] counter = new int[6];
        for(int num : ar)
        	counter[num]++;
        int max = 0;
        for(int c : counter)
        	if(c > max)
        		max = c;
        for(int i=1; i<=5; i++) {
        	if(counter[i] == max) {
        		type = i;
        		break;
        	}
        }
        return type;
    }
	
	
	static int pickingNumbers(int[] a) {
        int size = 0;
        int[] bucket = new int[100];
        for(int i=0; i<a.length; i++) {
        	bucket[a[i]]++;
        }
        for(int i=2; i<100; i++) {
        	size = Math.max(size, bucket[i] + bucket[i-1]);
        }
        return size;
    }
	
	
	
	
	
	
	
	
	

	public static void main(String[] args) {
		

	}

}
