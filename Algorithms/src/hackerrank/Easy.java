package hackerrank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
	static String appendAndDelete(String s, String t, int k) {
        Map<String, Boolean> memorization = new HashMap<>();
		if(appendAndDeleteHelp(new StringBuilder(s), t, k, memorization))
			return "Yes";
		return "No";
    }
	
	static boolean appendAndDeleteHelp(StringBuilder sb, String target, int k, Map<String, Boolean> memorization) {
		if(k == 0)
			return sb.toString().equals(target);
		String key = sb.toString()+","+k;
		for(char c='a'; c<='z'; c++) {
			sb.append(c);
			if(appendAndDeleteHelp(sb, target, k-1, memorization)) {
				memorization.put(key, true);
				return true;
			}
			sb.deleteCharAt(sb.length()-1);
		}
		if(sb.length() > 0) {
			char last = sb.charAt(sb.length()-1);
			sb.deleteCharAt(sb.length()-1);
			if(appendAndDeleteHelp(sb, target, k-1, memorization)) {
				memorization.put(key, true);
				return true;
			}
			sb.append(last);
		}
		memorization.put(key, false);
		return false;
	}
	
	
	static String appendAndDelete2(String s, String t, int k) {
		int len1 = s.length();
		int len2 = t.length();
		if(Math.abs(len1-len2) > k)
			return "No";
		if(len1 > len2) {
			String temp = s;
			s = t;
			t = temp;
			
			int f = len1;
			len1 = len2;
			len2 = f;
		}
		int index = -1;
		for(int i=0; i<len1; i++) {
			char c1 = s.charAt(i);
			char c2 = t.charAt(i);
			if(c1 == c2)
				continue;
			index = i;
			break;
		}
		if(index == -1) {
			if(len1 != len2) {
				k -= len2 - len1;
				if(k < 0)
					return "No";
			}
			
			if((k & 1) == 0 || k >= len1 * 2)
				return "Yes";
			else
				return "No";
		}
		
		k -= len1 - index  + len2 - index;
		System.out.println(k);
		if(k < 0)
			return "No";
		if((k & 1) == 0 || k >= len1 * 2)
			return "Yes";
		if(index == 0)
			return "Yes";
		return "No";
	}
	
	
	static int squares(int a, int b) {
		if(a == b) {
			int one = (int)Math.sqrt(a);
			if(one * one == a)
				return 1;
			return 0;
		}
		int one = (int)Math.sqrt(a);
		int two = (int) Math.sqrt(b);
		boolean x = one * one == a;
		boolean y = two * two == b;
		if(x && y) {
			return two - one + 1;
		}
		if(x) {
			return two - one + 1;
		}
		if(y) {
			return two - one;
		}
		return two - one; 
    }
	
	
	static int libraryFine(int d1, int m1, int y1, int d2, int m2, int y2) {
        if(y1 < y2) {
        	return 0;
        }
        else if(y1 == y2) {
        	if(m1 < m2) {
        		return 0;
        	}
        	else if(m1 == m2) {
        		if(d1 < d2) {
        			return 0;
        		}
        		else if(d1 == d2) {
        			return 0;
        		}
        		else {
        			return (d1 - d2 ) * 15; 
        		}
        	}
        	else {
        		return 500 * (m1 - m2);
        	}
        }
        
		return 10000;
    }
	
	static int[] cutTheSticks(int[] arr) {
        List<Integer> list = new ArrayList<>();
        int n = arr.length;
		int[] bucket = new int[1001];
		for(int num : arr)
			bucket[num]++;
		for(int i=1; i<1001; i++) {
			if(bucket[i] > 0) {
				list.add(n);
				n -= bucket[i];
			}
		}
		int[] answer = new int[list.size()];
		for(int i=0; i<answer.length; i++) {
			answer[i] = list.get(i);
		}
		return answer;
    }
	
	
	static String[] cavityMap(String[] grid) {
        int n = grid.length;
        if(n < 3)
        	return grid;
		char[][] board = new char[n][n];
		boolean[][] used = new boolean[n][n];
		
		int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
		
		for(int i=0; i<n; i++)
			board[i] = grid[i].toCharArray();
		
		for(int i=1; i<n-1; i++) {
			for(int j=1; j<n-1; j++) {
				if(used[i][j])
					continue;
				boolean isslot = true;
				char num = board[i][j];
				for(int[] d : dirs) {
					int x = d[0] + i;
					int y = d[1] + j;
					if(num <= board[x][y]) {
						isslot = false;
						break;
					}
				}
				if(isslot) {
					board[i][j] = 'X';
					used[i][j+1] = true;
					used[i+1][j] = true;
				}
			}
		}
		
		String[] answer = new String[n];
		for(int i=0; i<n; i++)
			answer[i] = new String(board[i]);
		return answer;
    }
	
	
	static int[] stones(int n, int a, int b) {
        List<Integer> list = new ArrayList<>();
        Set<String> memorization = new HashSet<>();
        stonesHelp(n-1, a, b, 0, list, memorization);
		Collections.sort(list);
		int size = list.size();
		int[] answer = new int[size];
		for(int i=0; i<size; i++)
			answer[i] = list.get(i);
		return answer;
    }
	
	static void stonesHelp(int n, int a, int b, int sum, List<Integer> list, Set<String> memorization) {
		String key = ""+n+","+sum;
		if(memorization.contains(key))
			return;
		memorization.add(key);
		if(n == 0) {
			list.add(sum);
			return;
		}
		stonesHelp(n-1, a, b, sum+a, list, memorization);
		stonesHelp(n-1, a, b, sum+b, list, memorization);
	}
	
	static int[] permutationEquation(int[] p) {
        int n = p.length;
        int[] map = new int[n+1];
        int[] answer = new int[n];
        for(int i=0; i<n; i++)
        	map[p[i]] = i+1;
        for(int i=1; i<=n; i++) {
        	answer[i-1] = map[map[i]];
        }
        return answer;
    }
	
	
	static int utopianTree(int n) {
        boolean flag = true;
        int tree = 1;
        while(n > 0) {
        	if(flag)
        		tree *= 2;
        	else
        		tree += 1;
        	flag = !flag;
        	n--;
        }
        return tree;
    }
	
	
	static String angryProfessor(int k, int[] a) {
		int counter = 0;
		for(int p : a) {
			if(p <= 0)
				counter++;
		}
		if(counter >= k)
			return "NO";
		return "YES";
    }
	
	
	static int beautifulDays(int i, int j, int k) {
        int beautifulDays = 0;
        for(int num =i; num <= j; num++) {
        	int reverse = reverseInteger(num);
        	int abs = Math.abs(reverse - num);
        	if(abs % k == 0)
        		beautifulDays++;
        }
        return beautifulDays;
    }
	
	static int reverseInteger(int num) {
		int reverse = 0;
		while(num != 0) {
			int temp = num % 10;
			reverse = reverse * 10 + temp;
			num /= 10;
		}
		return reverse;
	}
	
	static int viralAdvertising(int n) {
        int like = 0;
        int people = 5;
        for(int i=1; i<=n; i++) {
        	int half = people/2;
        	like += half;
        	people = half * 3;
        }
        return like;
    }
	
	static int saveThePrisoner(int n, int m, int s){
		int answer = (int) (((long)s + (long)m-1) % n);
        return answer == 0 ? n : answer;
    }
	
	static int hurdleRace(int k, int[] height) {
        int max = -1;
        for(int h : height)
        	if(h > max)
        		max = h;
        if(max <= k)
        	return 0;
        return max - k;
    }
	
	
	
	
	static int[] kaprekarNumbers(int p, int q) {
        List<Integer> list = new ArrayList<>();
		
        for(int i=p; i<=q; i++) {
        	if(isKaprekarNumber(i, getNumberLength(i)))
        		list.add(i);
        }
        
		int[] array = new int[list.size()];
		for(int i=0; i<list.size();i++)
			array[i] = list.get(i);
		return array;
    }
	
	static int[] numberLengthHelp = {9, 99, 999, 9999, 99999, 999999, 9999999};
	
	static int getNumberLength(int num) {
		for(int i=0; i<numberLengthHelp.length; i++) {
			if(num <= numberLengthHelp[i])
				return i+1;
		}
		return -1;
	}
	
	static boolean isKaprekarNumber(long num, int length) {
		long power = num * num;
		long left = power, right = 0;
		long ten = 1;
		while(length > 0) {
			long digit = left % 10;
			right = digit * ten + right;
			left /= 10;
			length--;
			ten *= 10;
		}
		return left + right == num;
	}
	
	static int beautifulTriplets(int d, int[] arr) {
        int beautifulTriplets = 0;
        if(arr.length < 3)
        	return beautifulTriplets;
        Set<Integer> set = new HashSet<>();
        for(int num : arr)
        	set.add(num);
        for(int num : arr) {
        	int two = num + d;
        	int three = num + d * 2;
        	if(set.contains(two) && set.contains(three))
        		beautifulTriplets++;
        }
        return beautifulTriplets;
    }
	
	static int minimumDistances(int[] a) {
		int distance = Integer.MAX_VALUE;
		Map<Integer, List<Integer>> map = new HashMap<>();
		for(int i=0; i<a.length; i++) {
			int num = a[i];
			if(!map.containsKey(num))
				map.put(num, new ArrayList<>());
			map.get(num).add(i);
		}
		for(int num : map.keySet()) {
			List<Integer> list = map.get(num);
			for(int i=1; i<list.size(); i++) {
				int cur = list.get(i);
				int pre = list.get(i-1);
				distance = Math.min(distance, Math.abs(cur - pre));
			}
		}
		if(distance == Integer.MAX_VALUE)
			return -1;
		return distance;
    }
	
	
	static int findDigits(int n) {
        int digits = 0;
        int original = n;
        while(n != 0) {
        	int a = n % 10;
        	if(a != 0) {
        		if(original % a == 0)
        			digits++;
        	}
        	n /= 10;
        }
        return digits;
    }
	
	
	
	static int nonDivisibleSubset(int k, int[] arr) {
        int subset = 0;
        int[] bucket = new int[k];
        for(int num : arr) {
        	bucket[num % k]++;
        }
        int end = k / 2;
        for(int i=1; i<=end; i++) {
        	if(i != k-i) {
        		subset += Math.max(bucket[i], bucket[k-i]);
        	}
        	else {
        		if(bucket[i] > 0)
        			subset++;
        	}
        }
        subset += Math.min(bucket[0], 1);
        return subset;
    }
	
	
	
	static String hackerrankInString(String s) {
        String word = "hackerrank";
		if(s.length() < word.length())
			return "NO";
		int j = 0;
		for(int i=0; i<s.length(); i++) {
			if(j < word.length() && s.charAt(i) == word.charAt(j))
				j++;
		}
		if(j != word.length())
			return "NO";
		return "YES";
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static void main(String[] args) {
		System.out.println(isKaprekarNumber(77778, 5));

	}

}
