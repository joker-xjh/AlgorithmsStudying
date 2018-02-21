package hackerrank;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
	
	
	static String pangram(String str) {
		boolean[] map = new boolean[26];
		for(char c : str.toCharArray()) {
			if(c == ' ')
				continue;
			if(c >= 'A' && c <= 'Z')
				c += 32;
			map[c-'a'] = true;
		}
		for(boolean b : map)
			if(!b)
				return "not pangram";
		return "pangram";
	}
	
	
	static void WeightedUniformStrings(Set<Integer> set, String str) {
		int weight = 0;
		char c = str.charAt(0);
		weight += c - 'a' + 1;
		set.add(weight);
		for(int i=1; i<str.length(); i++) {
			c = str.charAt(i);
			char pre = str.charAt(i-1); 
			if(c != pre) {
				weight = 0;
			}
			weight += c - 'a'+1;
			set.add(weight);
		}
	}
	
	
	static int stringConstruction(String s) {
        Set<Character> set = new HashSet<>();
        for(char c : s.toCharArray())
        	set.add(c);
        return set.size();
    }
	
	
	static String funnyString(String s){
        for(int i=1,j=s.length()-1; i<s.length(); i++,j--) {
        	int one = Math.abs(s.charAt(i) - s.charAt(i-1));
        	int two = Math.abs(s.charAt(j) - s.charAt(j-1));
        	if(one != two)
        		return "Not Funny";
        }
		return "Funny";
    }
	
	static int gemstones(String[] arr){
        int values = 0;
        int[] counter = new int[26];
        boolean[] used = new boolean[26];
        for(String s : arr) {
        	Arrays.fill(used, false);
        	for(char c : s.toCharArray()) {
        		if(!used[c-'a']) {
        			counter[c-'a']++;
        		}
        		used[c-'a'] = true;
        	}
        }
        for(int i=0; i<26; i++) {
        	if(counter[i] == arr.length)
        		values++;
        }
        return values;
    }
	
	
	static int sockMerchant(int n, int[] ar) {
        int pairs = 0;
        int[] bucket = new int[101];
        for(int i=0; i<n; i++)
        	bucket[ar[i]]++;
        for(int i=1; i<=100; i++)
        	pairs += bucket[i] / 2;
        return pairs;
    }

	static long repeatedString(String s, long n) {
        long counter = 0;
        long temp = 0;
        int length = s.length();
        for(char c : s.toCharArray())
        	if(c == 'a')
        		temp++;
        counter += temp * (n / length);
        int mod = (int) (n % length);
        for(int i=0; i<mod; i++)
        	if(s.charAt(i) == 'a')
        		counter++;
        return counter;
    }
	
	
	static int jumpingOnClouds(int[] c) {
        int n = c.length;
		int[] dp = new int[n];
		dp[1] = c[1] == 1 ? 10000 : 1;
		for(int i=2; i<n; i++) {
			dp[i] = Math.min(dp[i-1]+1, dp[i-2]+1);
			if(c[i] == 1)
				dp[i] += 10000;
		}
		return dp[n-1];
    }
	
	
	static int equalizeArray(int[] arr) {
        int options = 0;
        int[] bucket = new int[101];
        for(int num : arr)
        	bucket[num]++;
        Arrays.sort(bucket);
        for(int i=0; i<100; i++)
        	options += bucket[i];
        return options;
    }
	
	 static int[] circularArrayRotation(int[] a, int[] m, int k) {
	        int[] answer = new int[m.length];
	        flipArray(a, k, a.length);
	        System.out.println(Arrays.toString(a));
	        flipArray(a, 0, k);
	        System.out.println(Arrays.toString(a));
	        flipArray(a, 0, a.length);
	        System.out.println(Arrays.toString(a));
	        for(int i=0; i<m.length; i++) {
	        	answer[i] = a[m[i]];
	        }
	        return answer;
	 }
	
	 static void flipArray(int[] array, int left, int right) {
			int mid = left + (right - left) / 2;
			for(int i=left; i<mid; i++) {
				int c = array[i];
				array[i] = array[right - 1 - i + left];
				array[right - 1 - i + left] = c;
			}
		}
	
	
	 static int jumpingOnClouds(int[] c, int k) {
	        int energe = 100;
	        int index = 0;
	        int n = c.length;
	        do {
				energe -= 1;
				index = (index + k) % n;
				if(c[index] == 1)
					energe -= 2;
			} while (index != 0);
	        
	        return energe;
	 }
	 
	 static int[] acmTeam(String[] topic, int m) {
	       int[] answer = new int[2];
	       int[] bucket = new int[m+1];
	       int n = topic.length;
	       int max = -1;
	       for(int i=0; i<n; i++) {
	    	   String one = topic[i];
	    	   for(int j=i+1; j<n; j++) {
	    		   String other = topic[j];
	    		   int counter = 0;
	    		   for(int k=0; k<m; k++) {
	    			   char c1 = one.charAt(k);
	    			   char c2 = other.charAt(k);
	    			   if(c1 == '1' || c2 =='1' )
	    				   counter++;
	    		   }
	    		   bucket[counter]++;
	    		   if(counter > max)
	    			   max = counter;
	    	   }
	       }
	       answer[0] = max;
	       answer[1] = bucket[max];
	       return answer;
	 }
	 
	 
	 
	 static int designerPdfViewer(int[] h, String word) {
	        int area = 0;
	        int width = word.length();
	        int height = 0;
	        for(char c : word.toCharArray()) {
	        	height = Math.max(height, h[c-'a']);
	        }
	        area = width * height;
	        return area;
	 }
	 
	 static long taumBday(long b, long w, long x, long y, long z) {
	        long cost = 0;
	        if(x+z-y < 0) {
	        	cost += b * x + w * (x+z);
	        }
	        else if(y+z-x < 0) {
	        	cost += b * (y+z) + w * y;
	        }
	        else {
	        	cost += b * x + w * y;
	        }
	        return cost;
	 }
	 
	 
	 static int[] closestNumbers(int[] arr) {
	     Map<Integer, Integer> negative = new HashMap<>();
	     for(int i=0; i<arr.length; i++) {
	    	 int num = arr[i];
	    	 if(num <0) {
	    		 negative.put(-num, num);
	    		 arr[i] = -arr[i];
	    	 }
	     }
	     Arrays.sort(arr);
	     int diff = Integer.MAX_VALUE;
	     for(int i=1; i<arr.length; i++) {
	    	 diff = Math.min(diff, arr[i] - arr[i-1]);
	     }
	     List<Integer> list = new ArrayList<>();
	     for(int i=1; i<arr.length; i++) {
	    	 int pre = arr[i-1];
	    	 int cur = arr[i];
	    	 if(cur - pre != diff)
	    		 continue;
	    	 if(pre == cur) {
	    		 list.add(-pre);
	    		 list.add(cur);
	    		 continue;
	    	 }
	    	 
	    	 Integer reverse = negative.get(pre);
	    	 if(reverse == null)
	    		 list.add(pre);
	    	 else
	    		 list.add(-pre);
	    	 
	    	 reverse = negative.get(cur);
	    	 if(reverse == null)
	    		 list.add(cur);
	    	 else
	    		 list.add(-cur);
	     }
		 int[] answer = new int[list.size()];
	     for(int i=0; i<answer.length; i++)
	    	 answer[i] = list.get(i);
	     Arrays.sort(answer);
		 return answer;
	 }
	 
	 
	 static int[] closestNumbers2(int[] arr) {
		 Arrays.sort(arr);
		 int diff = Integer.MAX_VALUE;
		 for(int i=1; i<arr.length; i++) {
			 diff = Math.min(diff, arr[i] - arr[i-1]);
		 }
		 List<Integer> list = new ArrayList<>();
		 for(int i=1; i<arr.length; i++) {
			 int cur = arr[i];
			 int pre = arr[i-1];
			 if(cur - pre == diff) {
				 list.add(pre);
				 list.add(cur);
			 }
		 }
		 int[] answer = new int[list.size()];
		 for(int i=0; i<answer.length; i++) {
			 answer[i] = list.get(i);
		 }
		 return answer;
	 }
	 
	 
	 static int theLoveLetterMystery(String s){
	      int steps = 0;
	      int length = s.length();
	      for(int i=0; i<length/2; i++) {
	    	  char left = s.charAt(i);
	    	  char right = s.charAt(length - 1 - i);
	    	  steps += Math.abs(left - right);
	      }
	      return steps;
	 }
	 
	 static long strangeCode(long t) {
	      long demo = 3;
	      while(t > demo) {
	    	  t -= demo;
	    	  demo *= 2;
	      }
	      return demo - t + 1;
	 }
	 
	 
	 static int workbook(int n, int k, int[] arr) {
	     int special = 0;
	     int page = 1;
	     int index = 0;
	     int num = 1;
	     while(index < arr.length) {
	    	 if(num + k-1 >= arr[index] ) {
	    		 if(num <= page && page <= arr[index])
	    			 special++;
	    		 num = 1;
	    		 index++;
	    	 }
	    	 else {
	    		 if(num<= page && page <= num + k - 1)
	    			 special++;
	    		 num += k;
	    	 }
	    	 page++;
	     }
	     
	     return special;
	 }
	 
	 
	 static int flatlandSpaceStations(int n, int[] c) {
	       int max = 0;
	       TreeSet<Integer> set = new TreeSet<>();
	       for(int index : c)
	    	   set.add(index);
	       for(int i=0; i<n; i++) {
	    	   Integer pre = set.floor(i);
	    	   int temp = Integer.MAX_VALUE;
	    	   if(pre != null)
	    		   temp = Math.min(temp, i - pre);
	    	   Integer next = set.ceiling(i);
	    	   if(next != null)
	    		   temp = Math.min(temp, next - i);
	    	   max = Math.max(max, temp);
	       }
	       return max;
	 }
	 
	 
	 
	 static void fairRations(int[] B) {
	     int breads = 0;
	     for(int i=0; i<B.length; i++) {
	    	 int num = B[i];
	    	 if((num & 1) == 0)
	    		 continue;
	    	 if(i == B.length-1) {
	    		 System.out.println("NO");
	    		 return;
	    	 }
	    	 B[i] += 1;
	    	 B[i+1] += 1;
	    	 breads += 2;
	     }
	     
	     System.out.println(breads);
	 }
	
	 
	 static void happyLadybugs(String s) {
		 int letters = 0;
		 int underscores = 0;
		 char[] array = s.toCharArray();
		 for(char c : array)
			 if(c >= 'A' && c <= 'Z')
				 letters++;
			 else
				 underscores++;
		 if(letters == 0) {
			 System.out.println("YES");
			 return;
		 }
		 if(underscores != 0)
			 Arrays.sort(array);
		 for(int i=0; i<array.length; i++) {
			 char c = array[i];
			 if(c == '_')
				 continue;
			 char pre = i > 0 ? array[i-1] : '\0';
			 char next = i < array.length-1 ? array[i+1] : '\0';
			 if(!(c == pre || c == next)) {
				 System.out.println("NO");
				 return;
			 }
		 }
		 System.out.println("YES");
	 }
	 
	 
	 static String[] bigSorting(String[] arr) {
	     Arrays.sort(arr, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				int len1 = o1.length();
				int len2 = o2.length();
				if(len1 < len2)
					return -1;
				else if(len1 > len2)
					return 1;
				for(int i=0; i<len1; i++) {
					char c1 = o1.charAt(i);
					char c2 = o2.charAt(i);
					if(c1 < c2)
						return -1;
					else if(c1 > c2)
						return 1;
				}
				return 0;
			}
		});
		 
		 return arr;
	 }
	 
	 
	 static String super_reduced_string(String s){
	     int length = s.length();
	     while(length > 0) {
	    	 for(int i=0; i<length; i++) {
	    		 char c = s.charAt(i);
	    		 if(i < length - 1) {
	    			 char next = s.charAt(i+1);
	    			 if(c == next) {
	    				 String sub = s.substring(0, i) + s.substring(i+2);
	    				 s = sub;
	    				 break;
	    			 }
	    		 }
	    	 }
	    	 
	    	 if(s.length() == length) {
	    		 System.out.println(s);
	    		 return s;
	    	 }
	    	 length = s.length();
	     }
		 
		 return "Empty String";
	 }
	 
	 
	 static int camelcase(String s) {
	     int words = 1;
	     for(char c : s.toCharArray())
	    	 if(c >= 'A' && c <= 'Z')
	    		 words++;
	     return words;
	 }
	 
	 static int twoCharaters(String s) {
	     boolean[] used = new boolean[26];
	     int count = 0;
	     for(char c : s.toCharArray()) {
	    	 int i = c - 'a';
	    	 if(!used[i]) {
	    		 count++;
	    	 }
	    	 used[i] = true;
	     }
	     if(count < 2)
	    	 return 0;
		 return twoCharatersHelp(s, used, new HashMap<>(), count);
	 }
	 
	 static int twoCharatersHelp(String s, boolean[] used, Map<String, Integer> memoization, int count) {
		 if(count == 2) {
			 if(twoCharatersCheck(s))
				 return s.length();
			 return 0;
		 }
		 if(memoization.containsKey(s))
			 return memoization.get(s);
		 int max = 0;
		 for(int i=0; i<26; i++) {
			 if(!used[i])
				 continue;
			 used[i] = false;
			 max = Math.max(max, twoCharatersHelp(twoCharatersCreateStr(s, (char)('a'+i)), used, memoization, count-1));
			 used[i] = true;
		 }
		 
		 memoization.put(s, max);
		 return max;
	 }
	 
	 static String twoCharatersCreateStr(String s, char c) {
		 StringBuilder sb = new StringBuilder();
		 for(char ch : s.toCharArray()) {
			 if(ch != c)
				 sb.append(ch);
		 }
		 return sb.toString();
	 }
	 
	 static boolean twoCharatersCheck(String s) {
		 for(int i=1; i<s.length(); i++) {
			 char c = s.charAt(i);
			 char pre = s.charAt(i-1);
			 if(pre == c)
				 return false;
		 }
		 return true;
	 }
	 
	 static int twoCharaters2(String s) {
		 int max = 0;
		 int[] counter = new int[26];
		 for(char c : s.toCharArray()) {
			 int i = c - 'a';
			 counter[i]++;
		 }
		 for(int i=0; i<26; i++) {
			 for(int j=i+1; j<26; j++) {
				 if(Math.abs(counter[i] - counter[j]) > 1)
					 continue;
				 char a = '\0';
				 char b = '\0';
				 if(counter[i] > counter[j]) {
					 a = (char) ('a' + i);
					 b = (char) ('a' + j);
				 }
				 else if(counter[i] < counter[j]) {
					 b = (char) ('a' + i);
					 a = (char) ('a' + j);
				 }
				 else {
					 a = (char) ('a' + i);
					 b = (char) ('a' + j);
				 }
				 
				 max = Math.max(max, twoCharatersLength(s, a, b));
			 }
		 }
		 if(max == 1)
			 return 0;
		 
		 return max;
	 }
	 
	 static int twoCharatersLength(String s, char a, char b) {
		 int max = 0;
		 char temp = '\0';
		 for(int i=0; i<s.length(); i++) {
			 char c = s.charAt(i);
			 if(c == a || c == b) {
				 if(temp == c) {
					 max = 0;
					 return max;
				 }
				 temp = c;
				 max++;
			 }
		 }
		 
		 return max;
	 }
	 
	 
	 static String caesarCipher(String s, int k) {
	      StringBuilder sb = new StringBuilder();
	      k = k % 26;
	      for(char c : s.toCharArray()) {
	    	  if((c >= 'a' && c <= 'z') ) {
	    		  c += k;
	    		  if(c > 'z') {
	    			  c = (char) ('a' + c - 'z'-1);
	    		  }
	    	  }
	    	  if(c >= 'A' && c <= 'Z') {
	    		  c += k;
	    		  if(c > 'Z') {
	    			  c = (char) ('A' + c - 'Z'-1);
	    		  }
	    	  }
	    	  sb.append(c);
	      }
	      return sb.toString();
	 }
	 
	 
	 static int marsExploration(String s) {
	     int change = 0;
	     for(int i=0; i<s.length(); i+=3) {
	    	 char c1 = s.charAt(i);
	    	 char c2 = s.charAt(i+1);
	    	 char c3 = s.charAt(i+2);
	    	 if(c1 != 'S')
	    		 change++;
	    	 if(c2 != 'O')
	    		 change++;
	    	 if(c3 != 'S')
	    		 change++;
	     }
	     return change;
	 }
	 
	 static int alternatingCharacters(String s){
	      int delete = 0;
	      for(int i=1; i<s.length(); i++) {
	    	  char c = s.charAt(i);
	    	  char pre = s.charAt(i-1);
	    	  if(c == pre)
	    		  delete++;
	      }
	      
	      return delete;
	 }
	 
	 
	 static int beautifulBinaryString(String b) {
	      int steps = 0;
	      char[] array = b.toCharArray();
	      for(int i=2; i<array.length; i++) {
	    	  char c1 = array[i];
	    	  char c2 = array[i-1];
	    	  char c3 = array[i-2];
	    	  if(c3 == '0' && c2 == '1' && c1 == '0') {
	    		  steps++;
	    		  array[i] = '1';
	    	  }
	      }
	      return steps;
	 }
	 
	 
	 static int makingAnagrams(String s1, String s2){
	      int steps = 0;
	      int[] map = new int[26];
	      for(char c : s1.toCharArray())
	    	  map[c-'a']++;
	      for(char c : s2.toCharArray())
	    	  map[c-'a']--;
	      for(int i=0; i<26; i++) {
	    	  steps += Math.abs(map[i]);
	      }
	      return steps;
	 }
	 
	 static String gameOfThrones(String s){
	     int[] counter = new int[26];
		 for(char c : s.toCharArray())
			 counter[c-'a']++;
		 int odds = 0;
		 for(int i=0; i<26; i++) {
			 if((counter[i] & 1) == 1)
				 odds++;
		 }
		 if(odds > 1)
			 return "NO";
		 return "YES";
	 }
	 
	 
	 static String twoStrings(String s1, String s2){
	       boolean[] map = new boolean[26];
	       for(char c : s1.toCharArray())
	    	   map[c - 'a'] = true;
	       for(char c : s2.toCharArray()) {
	    	   if(map[c - 'a'])
	    		   return "YES";
	       }
	       return "NO";
	 }
	 
	 
	 
	 static int anagram(String s){
	     int length = s.length();
	     if((length&1) == 1)
	    	 return -1;
	     int half = length / 2;
	     int steps = 0;
	     String s1 = s.substring(0, half);
	     String s2 = s.substring(half);
	     int[] counter = new int[26];
		 for(int i=0; i<half; i++) {
			 char c1 = s1.charAt(i);
			 char c2 = s2.charAt(i);
			 counter[c1-'a']--;
			 counter[c2-'a']++;
		 }
		 for(int i=0; i<26; i++) {
			 if(counter[i] > 0)
				 steps += counter[i];
		 }
		 return steps;
	 }
	 
	 
	 static int palindromeIndex(String s){
	     int[] index = palindromeIndexHelp(s, 0, s.length()-1);
		 if(index == null)
			 return -1;
		 int left = index[0], right = index[1];
		 index = palindromeIndexHelp(s, left+1, right);
		 if(index == null)
			 return left;
		 return right;
	 }
	 
	 static int[] palindromeIndexHelp(String s, int left, int right) {
		 int[] index = new int[2];
		 while(left < right) {
			 char c1 = s.charAt(left);
			 char c2 = s.charAt(right);
			 if(c1 != c2) {
				 index[0] = left;
				 index[1] = right;
				 return index;
			 }
			 left++;
			 right--;
		 }
		 
		 return null;
	 }
	 
	 
	 static int minimumAbsoluteDifference(int n, int[] arr) {
	      int min = Integer.MAX_VALUE;
	      Arrays.sort(arr);
	      for(int i=1; i<n; i++) {
	    	  min = Math.min(min, arr[i]-arr[i-1]);
	      }
	      return min;
	 }
	 
	 static String chessboardGame(int x, int y) {
	    if(chessboardGameHelp(x, y))
	    	return "Second";
	    return "First";
	 }
	 
	 static boolean chessboardGameHelp(int x, int y) {
		 boolean lose = true;
		 for(int[] dir : chessboardGameDir) {
			 int a = x + dir[0];
			 int b = y + dir[1];
			 if(a > 0 && a < 16 && b > 0 && b < 16) {
				 lose = false;
				 break;
			 }
		 }
		 if(lose) {
			 return true;
		 }
		 for(int[] dir : chessboardGameDir) {
			 int a = x + dir[0];
			 int b = y + dir[1];
			 if(a > 0 && a < 16 && b > 0 && b < 16) {
				 lose = lose | !chessboardGameHelp(a, b);
			 }
		 }
		 return lose;
	 }
	 
	 static int[][] chessboardGameDir = {{-2,1}, {-2,-1}, {1,-2}, {-1,-2}};
	 
	 
	 static long flippingBits(long N) {
	     long num =  N;
	     num = -num - 1;
	     long answer = num;
	     answer &= 0x00000000FFFFFFFFL;
		 return answer;
	 }
	 
	 static String gridChallenge(String[] grid) {
	     int n = grid.length;
	     char[][] board = new char[n][];
		 for(int i=0; i<n; i++) {
			 board[i] = grid[i].toCharArray();
			 Arrays.sort(board[i]);
		 }
		 for(int i=0; i<n; i++) {
			 for(int j=1; j<n; j++) {
				 if(board[i][j-1] > board[i][j])
					 return "NO";
			 }
		 }
		 for(int i=0; i<n; i++) {
			 for(int j=1; j<n; j++) {
				 if(board[j-1][i] > board[j][i])
					 return "NO";
			 }
		 }
		 
		 return "YES";
	 }
	 
	 static int luckBalance(int n, int k, int[][] contests) {
	       int luck = 0;
	       List<Integer> important = new ArrayList<>();
	       for(int[] contest : contests) {
	    	   if(contest[1] == 0) 
	    		   luck += contest[0];
	    	   else
	    		   important.add(contest[0]);
	       }
	       Collections.sort(important);
	       for(int i=important.size()-1; i>=important.size()-k && i>=0; i--)
	    	  luck += important.get(i);
	       for(int i=0; i<important.size()-k; i++)
	    	   luck -= important.get(i);
	       return luck;
	 }
	 
	 
	 static int[] missingNumbers(int[] arr, int[] brr) {
	     Arrays.sort(arr);
	     Arrays.sort(brr);
	     int n = arr.length, m = brr.length;
	     List<Integer> missing = new ArrayList<>();
	     int i=0, j=0;
	     while(i<n && j < m) {
	    	 int a = arr[i];
	    	 int b = brr[j];
	    	 if(a == b) {
	    		 i++;
	    		 j++;
	    	 }
	    	 else {
	    		 if(missing.isEmpty() || missing.get(missing.size()-1) != b)
	    			 missing.add(b);
	    		 j++;
	    	 }
	     }
	     while(j < m)
	    	 missing.add(brr[j++]);
	     int[] answer = new int[missing.size()];
	     for(int x=0; x<answer.length; x++) {
	    	 answer[x] = missing.get(x);
	     }
	     
		 return answer;
	 }
	 
	 
	 static String solve2(int[] a){
	     int n = a.length;
	     int[] left = new int[n];
	     int[] right = new int[n];
	     int sum = 0;
	     for(int i=0; i<n; i++) {
	    	 sum += a[i];
	    	 left[i] = sum;
	     }
	     sum = 0;
	     for(int i=n-1; i>=0; i--) {
	    	 sum += a[i];
	    	 right[i] = sum;
	     }
	     for(int i=0; i<n; i++) {
	    	 if(left[i] == right[i])
	    		 return "YES";
	     }
		 return "NO";
	 }
	 
	 static String solve2OP(int[] a){
		 int n = a.length;
	     int[] left = new int[n];
	     int sum = 0;
	     for(int i=0; i<n; i++) {
	    	 sum += a[i];
	    	 left[i] = sum;
	     }
	     sum = 0;
	     for(int i=n-1; i>=0; i--) {
	    	 sum += a[i];
	    	 if(sum == left[i])
	    		 return "YES";
	     }
		 return "NO";
	 }
	 
	 static long marcsCakewalk(int[] calorie) {
	     long miles = 0;
	     long two = 1;
	     Arrays.sort(calorie);
	     int n = calorie.length;
	     for(int i=n-1; i>=0; i--) {
	    	 miles += calorie[i] * two;
	    	 two *= 2;
	     }
	     return miles;
	 }
	 
	 static void maximumPerimeterTriangle(int[] l) {
	     Arrays.sort(l);
	     int n =l.length;
	     for(int i=n-3; i>=0; i--) {
	    	 int one = l[i];
	    	 int two = l[i+1];
	    	 int three = l[i+2];
	    	 if(one + two > three) {
	    		 System.out.println(one+" "+two+" "+three);
	    		 return;
	    	 }
	     }
		 
		 System.out.println(-1);
	 }
	 
	 
	 static void SherlockandTheBeast(int n){
	       int count3_1 = 0;
	       int count5_1 = 0;
	       int count3_2 = 0;
	       int count5_2 = 0;
	       int temp = n;
	       while(temp > 0) {
	    	   if(temp % 3 == 0) {
	    		   count3_1 = temp;
	    		   break;
	    	   }
	    	   temp -= 5;
	    	   count5_1 += 5;
	       }
	       temp = n;
	       while(temp > 0) {
	    	   if(temp % 5 == 0) {
	    		   count5_2 = temp;
	    		   break;
	    	   }
	    	   temp -= 3;
	    	   count3_2 += 3;
	       }
	       
	       if(count3_1 == 0 && count5_2 == 0) {
	    	   System.out.println(-1);
	    	   return;
	       }
    	   StringBuilder sb = new StringBuilder();
	       if(count3_1 > count3_2) {
	    	   for(int i=0; i<count3_1; i++)
	    		   sb.append(5);
	    	   for(int i=0; i<count5_1; i++)
	    		   sb.append(3);
	       }
	       else {
	    	   for(int i=0; i<count3_2; i++)
	    		   sb.append(5);
	    	   for(int i=0; i<count5_2; i++)
	    		   sb.append(3);
	       }
	       
    	   System.out.println(sb);

	 }
	 
	 
	 
	
	

	public static void main(String[] args) {
		
	}

}
