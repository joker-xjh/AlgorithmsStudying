package hackerrank;

import java.io.BufferedReader;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;



public class Medium {
	
	static int powerSum(int X, int N) {
        int count = 0;
        List<Integer> list = new  ArrayList<>();
        for(int i=1; i<=X; i++) {
        	int temp = 1;
        	boolean add = true;
        	for(int j=0; j<N; j++) {
        		temp *= i;
        		if(temp > X) {
        			add = false;
        			break;
        		}
        	}
        	if(add) {
        		list.add(temp);
        	}
        	else
        		break;
        }
        boolean[] used = new boolean[list.size()];
        count += powerSumHelp(list, used, 0, X, new ArrayList<>(), 0);
        
        return count;
    }
	
	static int powerSumHelp(List<Integer> factor, boolean[] used, int sum, int target, List<Integer> list, int start) {
		if(sum == target) {
			return 1;
		}
		if(sum > target)
			return 0;
		int count = 0;
		for(int i=start; i<factor.size(); i++) {
			if(used[i])
				continue;
			used[i] = true;
			list.add(factor.get(i));
			count += powerSumHelp(factor, used, sum + factor.get(i), target, list, i + 1);
			used[i] = false;
			list.remove(list.size() - 1);
		}
		return count;
	}
	
	static int[][] dirs = {{-1,0}, {1,0}, {0,-1}, {0,1}};
	
	static String[] crosswordPuzzle(String[] crossword, String hints) {
        char[][] board = new char[10][10];
        for(int i=0; i<10; i++)
        	board[i] = crossword[i].toCharArray();
        String[] hints_array = hints.split(";");
        int count = 0;
        int[] slots = new int[1];
        for(String word : hints_array)
        	count += word.length();
        for(int i=0; i<10; i++)
        	for(int j=0; j<10; j++)
        		if(board[i][j] == '-')
        			slots[0]++;
        boolean[] used = new boolean[hints_array.length];
        Set<String> steps = new HashSet<>();
        for(int i=0; i<10; i++) {
        	for(int j=0; j<10; j++) {
        		if(board[i][j] == '-') {
        			for(int x =0; x<hints_array.length; x++) {
        				String word = hints_array[x];
        				used[x] = true;
        				String str = ""+i+j;
        				steps.add(str);
        			if(crosswordPuzzleHelp(board, i, j, count, hints_array, used, word, 0, steps, slots))
        					break;
        				used[x] = false;
        				steps.remove(str);
        			}
        			break;
        		}
        	}
        }
        
        String[] answer = new String[10];
        for(int i=0; i<10; i++)
        	answer[i] = new String(board[i]);
		
		return answer;
    }
	
	
	static boolean crosswordPuzzleHelp(char[][] board,int x, int y, int count, String[] hints, boolean[] hints_used, String hint, int length, Set<String> steps, int[] slots) {
		char c = hint.charAt(length);
		char old = board[x][y];
		if(old != '-' && old != c)
			return false;
		if(old == '-')
			slots[0]--;
		board[x][y] = c;
		if(count - 1 == 0 && slots[0] == 0)
			return true;
		if(length == hint.length() - 1) {
			for(int i=0; i<hints.length; i++) {
				if(hints_used[i])
					continue;
				hints_used[i] = true;
				for(int a=0; a<10; a++) {
					for(int b=0; b<10; b++) {
						if(board[a][b] == '-' || board[a][b] == hints[i].charAt(0)) {
							Set<String> temp = new HashSet<>();
							temp.add(""+a+b);
							if(crosswordPuzzleHelp(board, a, b, count - 1, hints, hints_used, hints[i], 0, temp, slots))
								return true;
						}
					}
				}
				hints_used[i] = false;
			}
		}
		else {
			for(int[] dir : dirs) {
				int a = x + dir[0];
				int b = y + dir[1];
				String str = ""+a+b;
				if(a < 0 || a >= 10 || b < 0 || b >= 10 || board[a][b] == '+' || steps.contains(str))
					continue;
				steps.add(str);
				if(crosswordPuzzleHelp(board, a, b, count - 1, hints, hints_used, hint, length + 1, steps, slots))
					return true;
				 steps.remove(str);
			}
		}
		board[x][y] = old;
		if(old == '-')
			slots[0]++;
		return false;
	}
	
	
	
	static String[] crosswordPuzzle2(String[] crossword, String hints) {
		char[][] board = new char[10][10];
		int[][] boardCounter = new int[10][10];
        for(int i=0; i<10; i++)
        	board[i] = crossword[i].toCharArray();
        String[] hints_array = hints.split(";");
        int count = hints_array.length;
        boolean[] used = new boolean[hints_array.length];
        for(int i=0; i<10; i++) {
        	for(int j=0; j<10; j++) {
        		if(board[i][j] == '-') {
        			for(int x =0; x<hints_array.length; x++) {
        				used[x] = true;
        				String word = hints_array[x];
        				if(crosswordPuzzle2Help(board, i, j, count, word, hints_array, used, boardCounter))
        					break;
        				used[x] = false;
        			}
        			break;
        		}
        	}
        }
        
        String[] answer = new String[10];
        for(int i=0; i<10; i++)
        	answer[i] = new String(board[i]);
		return answer;
	}
	
	
	static boolean crosswordPuzzle2Help(char[][] board,int x, int y, int count, String word,String[] words, boolean[] used, int[][] boardCounter) {
		for(int t=0; t<2; t++) {
			boolean rightOrDown = t == 0 ? true : false;
			if(crosswordPuzzleCanUsed(board, x, y, word, rightOrDown)) {
				crosswordPuzzleUsed(board, x, y, rightOrDown, word, boardCounter);
				if(count - 1 == 0)
					return true;
				for(int i=0; i<used.length; i++) {
					if(used[i])
						continue;
					used[i] = true;
					String nextWord = words[i];
					for(int a=0; a<10; a++) {
						for(int b=0; b<10; b++) {
							if(board[a][b] == '-' || board[a][b] == nextWord.charAt(0)) {
								if(crosswordPuzzle2Help(board, a, b, count - 1, nextWord, words, used, boardCounter))
									return true;
							}
						}
					}
					used[i] = false;
				}
				crosswordPuzzleUnused(board, x, y, rightOrDown, word, boardCounter);
			}
		}
		return false;
	}
	
	
	static boolean crosswordPuzzleCanUsed(char[][] board, int x, int y, String word, boolean rightOrDown) {
		int length = word.length();
		int count = length;
		if(rightOrDown) {
			if(y + length > 10)
				return false;
			for(int i=y; i<y+length; i++) {
				if(board[x][i] == '-' || board[x][i] == word.charAt(i-y))
					count--;
				else
					break;
			}
		}
		else {
			if(x + length > 10)
				return false;
			for(int i=x; i<x+length; i++) {
				if(board[i][y] =='-' || board[i][y] == word.charAt(i-x))
					count--;
				else
					break;
			}
		}
		return count == 0;
	}
	
	static void crosswordPuzzleUsed(char[][] board, int x, int y, boolean rightOrDown, String word, int[][] boardCounter) {
		int length = word.length();
		if(rightOrDown) {
			for(int i=y; i<y+length; i++) {
				board[x][i] = word.charAt(i-y);
				boardCounter[x][i]++;
			}
		}
		else {
			for(int i=x; i<x+length; i++) {
				board[i][y] = word.charAt(i-x);
				boardCounter[i][y]++;
			}
		}
	}
	
	static void crosswordPuzzleUnused(char[][] board, int x, int y, boolean rightOrDown, String word, int[][] boardCounter) {
		int length = word.length();
		if(rightOrDown) {
			for(int i=y; i<y+length; i++) {
				boardCounter[x][i]--;
				if(boardCounter[x][i] == 0)
					board[x][i] = '-';
			}
		}
		else {
			for(int i=x; i<x+length; i++) {
				boardCounter[i][y]--;
				if(boardCounter[i][y] == 0)
					board[i][y] = '-';
			}
		}
	}
	
	static int digitSum(String n, int k) {
        long sum = 0;
        for(char c : n.toCharArray())
        	sum += c - '0';
		sum *= k;
		while(sum >= 10) {
			long temp = 0;
			while(sum != 0) {
				temp += sum %10;
				sum /= 10;
			}
			sum = temp;
		}
        
		return (int) sum;
    }
	
	static class Trie {
		boolean word;
		String str;
		Trie[] next = new Trie[26];
	}
	
	static String passwordCracker(String[] pass, String attempt) {
		Trie root = new Trie();
		boolean[] preCheck = new boolean[26];
		for(String password : pass) {
			Trie node = root;
			for(char c : password.toCharArray()) {
				int i = c - 'a';
				preCheck[i] = true;
				if(node.next[i] == null)
					node.next[i] = new Trie();
				node = node.next[i];
			}
			node.word = true;
			node.str = password;
		}
		for(char c : attempt.toCharArray()) {
			if(!preCheck[c-'a'])
				return "WRONG PASSWORD";
		}
		StringBuilder sb = new StringBuilder();
		if(!passwordCrackerHelp2(root, attempt.toCharArray(), 0, sb))
			return "WRONG PASSWORD";
        return sb.toString();
    }
	
	static boolean passwordCrackerHelp(List<String> list, StringBuilder sb, Trie node, Trie root, char[] attempt, int index) {
		char c = attempt[index];
		int i = c - 'a';
		Trie nextNode = node.next[i];
		if(nextNode == null)
			return false;
		sb.append(c);
		if(index == attempt.length - 1) {
			if(!nextNode.word)
				return false;
			list.add(sb.toString());
			return true;
		}
		else {
			if(nextNode.word) {
				list.add(sb.toString());
				if(passwordCrackerHelp(list, new StringBuilder(), root, root, attempt, index + 1))
					return true;
				list.remove(list.size() - 1);
			}
			return passwordCrackerHelp(list, sb, nextNode, root, attempt, index + 1);
		}
	}
	
	static boolean passwordCrackerHelp2(Trie root, char[] attempt, int index, StringBuilder sb) {
		Trie node = root;
		for(int i=index; i<attempt.length; i++) {
			char c = attempt[i];
			if(node.next[c-'a'] == null)
				return false;
			node = node.next[c-'a'];
			if(node.word) {
				int length = sb.length();
				sb.append(node.str).append(' ');
				if(passwordCrackerHelp2(root, attempt, i+1, sb))
					return true;
				sb.delete(length, sb.length());
				if(i == attempt.length - 1) {
					sb.append(node.str);
					return true;
				}
				
			}
		}
		return false;
	}
	
	
	static void passwordCrackerFinal(String[] pass, String attempt) {
		Set<String> directory = new HashSet<>();
		for(String word : pass)
			directory.add(word);
		if(!passwordCrackerFinalHelp(attempt, directory, "", new HashMap<>()))
			System.out.println("WRONG PASSWORD");
	}
	
	static boolean passwordCrackerFinalHelp(String s, Set<String> directory, String path, Map<String, Boolean> memorization) {
		if(s.length() == 0) {
			System.out.println(path.trim());
			return true;
		}
		if(memorization.containsKey(s))
			return memorization.get(s);
		for(String word : directory) {
			if(!s.startsWith(word))
				continue;
			if(passwordCrackerFinalHelp(s.substring(word.length()), directory, path+word+" ", memorization)) {
				memorization.put(s, true);
				return true;
			}
		}
		memorization.put(s, false);
		return false;
	}
	
	
	static int[][][] magicSquares = {{{2, 7, 6}, {9, 5, 1}, {4, 3, 8}},
			{{2, 9, 4}, {7, 5, 3}, {6, 1, 8}},
					{{4, 3, 8}, {9, 5, 1}, {2, 7, 6}},
					{{4, 9, 2}, {3, 5, 7}, {8, 1, 6}},
					{{6, 1, 8}, {7, 5, 3}, {2, 9, 4}},
					{{6, 7, 2}, {1, 5, 9}, {8, 3, 4}},
					{{8, 1, 6}, {3, 5, 7}, {4, 9, 2}},
					{{8, 3, 4}, {1, 5, 9}, {6, 7, 2}}};
	
	static int formingMagicSquare(int[][] s) {
        int min = Integer.MAX_VALUE;
        
        for(int[][] magicSquare : magicSquares) {
        	int counter = 0;
        	for(int i=0; i<3; i++) {
        		for(int j=0; j<3; j++) {
        			counter += Math.abs(s[i][j] - magicSquare[i][j]);
        		}
        	}
        	min = Math.min(min, counter);
        }
        
		return min;
    }
	
	static void generateMagicSquare(int[][] array, boolean[] used, boolean[][] board, int counter, Set<String> set) {
		if(counter == 9) {
			if(checkMagicSquare(array)) {
				String key = Arrays.deepToString(array);
				if(set.contains(key))
					return;
				set.add(key);
				System.out.println(key);
			}
		}
		else {
			for(int i=0; i<3; i++) {
				for(int j=0; j<3; j++) {
					if(board[i][j])
						continue;
					board[i][j] = true;
					for(int x=1; x<10; x++) {
						if(used[x])
							continue;
						used[x] = true;
						array[i][j] = x;
						generateMagicSquare(array, used, board, counter+1, set);
						array[i][j] = 0;
						used[x] = false;
					}
					board[i][j] = false;
				}
			}
		}
	}
	
	
	static boolean checkMagicSquare(int[][] array) {
		if(array[0][0] + array[0][1] + array[0][2] != 15)
			return false;
		if(array[0][0] + array[1][0] + array[2][0] != 15)
			return false;
		if(array[2][0] + array[2][1] + array[2][2] != 15)
			return false;
		if(array[0][2] + array[1][2] + array[2][2] != 15)
			return false;
		if(array[0][0] + array[1][1] + array[2][2] != 15)
			return false;
		if(array[0][2] + array[1][1] + array[2][0] != 15)
			return false;
		if(array[0][1] + array[1][1] + array[2][1] != 15)
			return false;
		if(array[1][0] + array[1][1] + array[1][2] != 15)
			return false;
		
		return true;
	}
	
	static void extraLongFactorials(int n) {
        if(n <= 20) {
        	System.out.println(extraLongFactorialsLessThan20(n));
        	return;
        }
        long lessthan20 = extraLongFactorialsLessThan20(20);
        BigInteger num = new BigInteger(String.valueOf(lessthan20));
        for(int i=21; i<=n; i++) {
        	num = num.multiply(new BigInteger(String.valueOf(i)));
        }
        System.out.println(num);
    }
	
	static long extraLongFactorialsLessThan20(int n) {
		long num = 1;
		for(int i=1; i<=n; i++)
			num *= i;
		return num;
	}
	
	
	static String countLuck(String[] matrix, int k) {
        char[][] board = new char[matrix.length][matrix[0].length()];
        for(int i=0; i<matrix.length; i++)
        	board[i] = matrix[i].toCharArray();
        int[] ways = new int[1];
        for(int i=0; i<matrix.length; i++) {
        	for(int j=0; j<matrix[0].length(); j++) {
        		if(board[i][j] == 'M') {
        			countLuckHelp(board, i, j, ways);
        			break;
        		}
        	}
        }
        System.out.println(ways[0]);
        System.out.println(k);
		if(ways[0] == k)
			return "Impressed";
		return "Oops!";
    }
	
	static int [][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};
	
	static boolean countLuckHelp(char[][] matrix, int x, int y, int[] ways) {
		System.out.println("x:"+x +" y:"+y+" 岔口:"+ways[0]);
		if(matrix[x][y] == '*')
			return true;
		int added = 0;
		for(int[] dir:directions) {
			int a = x + dir[0];
			int b = y + dir[1];
			if(a < 0 || a >= matrix.length || b < 0 || b >= matrix[0].length || matrix[a][b] == 'X' || matrix[a][b] == 'M')
				continue;
			added++;
		}
		
		for(int[] dir:directions) {
			int a = x + dir[0];
			int b = y + dir[1];
			if(a < 0 || a >= matrix.length || b < 0 || b >= matrix[0].length || matrix[a][b] == 'X' || matrix[a][b] == 'M')
				continue;
			if(matrix[a][b] !='*')
				matrix[a][b] = 'M';
			if(added > 1) {
				System.out.println("x:"+x+" y:"+y+" 岔口:"+added);
			}
			int temp = added > 1 ? 1 : 0;
			ways[0] += temp;
			if(countLuckHelp(matrix, a, b, ways))
				return true;
			ways[0] -= temp;
			
		}
		
		return false;
	}
	
	
	
	static int cutTheTree(int[] data, int[][] edges) {
        Map<Integer, List<Integer>> tree = new HashMap<>();
        int n = data.length;
        int[] treeSum = new int[n+1];
        for(int[] edge : edges) {
        	int v1 = edge[0], v2 = edge[1];
        	if(!tree.containsKey(v1))
        		tree.put(v1, new ArrayList<>());
        	if(!tree.containsKey(v2))
        		tree.put(v2, new ArrayList<>());
        	tree.get(v1).add(v2);
        	tree.get(v2).add(v1);
        }
		Set<Integer> visited = new HashSet<>();
		visited.add(1);
		int total = cutTheTreeSum(tree, treeSum, 1, visited, data);
		int min = Integer.MAX_VALUE;
		for(int i=2; i<=n; i++) {
			int one = treeSum[i];
			int other = total - one;
			min = Math.min(min, Math.abs(one - other));
		}
		return min;
    }
	
	static int cutTheTreeSum(Map<Integer, List<Integer>> tree, int[] treeSum, int v, Set<Integer> visited, int[] data) {
		int sum = data[v-1];
		for(int other : tree.get(v)) {
			if(visited.contains(other))
				continue;
			visited.add(other);
			sum += cutTheTreeSum(tree, treeSum, other, visited, data);
		}
		treeSum[v] = sum;
		return sum;
	}
	
	
	static String encryption(String s) {
        int L = s.length();
        double sqrt = Math.sqrt(L);
		int floor = (int) Math.floor(sqrt);
		int ceil = (int) Math.ceil(sqrt);
		System.out.println(floor  +" "+ceil);
		if(floor * ceil < L)
			floor++;
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<ceil; i++) {
			for(int j=0; j<floor; j++) {
				int index = i + j * ceil;
				if(index >= L)
					continue;
				sb.append(s.charAt(index));
			}
			if(i != ceil-1)
				sb.append(' ');
		}
		return sb.toString();
    }
	
	
	static String biggerIsGreater(String w) {
		if(w.length() < 2)
			return "no answer";
        char[] array = w.toCharArray();
		int i = array.length - 2;
		while(i >= 0 && array[i] >= array[i+1])
			i--;
		if(i < 0)
			return "no answer";
		int j = array.length-1;
		while(array[j] <= array[i])
			j--;
		char c = array[i];
		array[i] = array[j];
		array[j] = c;
		Arrays.sort(array, i+1, array.length);
		String s = new String(array);
		return s;
    }
	
	
	static int connectedCell(int[][] matrix) {
        int answer = 0;
        int[] counter = new int[1];
        for(int i=0; i<matrix.length; i++) {
        	for(int j=0; j<matrix[0].length; j++) {
        		if(matrix[i][j] == 1) {
        			matrix[i][j] = 0;
        			counter[0] = 1;
        			connectedCellSearch(matrix, i, j, counter);
        			answer = Math.max(answer, counter[0]);
        		}
        	}
        }
        
        return answer;
    }
	
	static int [][] dir = {{-1,0}, {1,0}, {0,1}, {0,-1}, {-1,-1}, {-1,1}, {1,-1},{1,1}};
	
	static void connectedCellSearch(int[][] matrix, int x, int y, int[] counter) {
		int n = matrix.length, m = matrix[0].length;
		for(int[] d : dir) {
			int a = d[0] + x;
			int b = d[1] + y;
			if(a < 0 || a >= n || b < 0 || b >= m || matrix[a][b] == 0)
				continue;
			counter[0]++;
			matrix[a][b] = 0;
			connectedCellSearch(matrix, a, b, counter);
		}
	}
	
	
	static int pairs(int k, int[] arr) {
        int pairs = 0;
        Set<Long> set = new HashSet<>();
        for(int num : arr)
        	set.add(new Long(num));
        Set<String> used = new HashSet<>();
        for(int i=0; i<arr.length; i++) {
        	long num = arr[i];
        	for(int j=0; j<2; j++) {
        		long other = j == 0 ? num - k : num + k;
        		if(set.contains(other)) {
            		String key = num + "," + other;
            		String otherKey = other + ","+ num;
            		if(!used.contains(key) && !used.contains(otherKey)) {
            			used.add(key);
            			used.add(otherKey);
            			pairs++;
            		}
            	}
        	}
        }
        return pairs;
    }
	
	
	
	static String isValid(String s){
        int[] map = new int[26];
		for(char c : s.toCharArray())
			map[c-'a']++;
		Arrays.sort(map);
		int index = -1;
		for(int i=0; i<26; i++) {
			if(map[i] != 0) {
				index = i;
				break;
			}
		}
		boolean valid = true;
		for(int i=index+1; i<26; i++) {
			if(map[i] != map[i-1]) {
				valid = false;
				break;
			}
		}
		if(valid)
			return "YES";
		for(char c='a'; c<='z'; c++) {
			map[c-'a']--;
			valid = true;
			int temp = map[index] == 0 ? index+1 : index;
			for(int i=temp+1; i<26; i++) {
				if(map[i] != map[i-1]) {
					valid = false;
					break;
				}
			}
			map[c-'a']++;
			if(valid)
				return "YES";
		}
		return "NO";
    }
	
	
	static void separateNumbers(String s) {
        if(s.length() < 2) {
        	System.out.println("NO");
        	return;
        }
        String[] first = new String[1];
        if(separateNumbersHelp(s, true, first, 0)) {
        	System.out.println("YES "+first[0]);
        }
        else {
        	System.out.println("NO");
        }
    }
	
	static boolean separateNumbersHelp(String s, boolean isFirst, String[] first, long num) {
		if(s.length() == 0)
			return true;
		if(isFirst) {
			for(int i=0; i<s.length()/2; i++) {
				String sub = s.substring(0, i+1);
				long number = strToLong(sub);
				first[0] = sub;
				if(separateNumbersHelp(s.substring(sub.length()), false, first, number+1))
					return true;
			}
		}
		else {
			String value = String.valueOf(num);
			if(s.startsWith(value)) {
				if(separateNumbersHelp(s.substring(value.length()), false, first, num+1))
					return true;
			}
		}
		return false;
	}
	
	static long strToLong(String s) {
		long num = 0;
		for(int i=0; i<s.length(); i++) {
			num = num * 10 + s.charAt(i) - '0';
		}
		return num;
	}
	
	static int queensAttack(int n, int k, int r_q, int c_q, int[][] obstacles) {
        int attack = 0;
        Set<String> obs = new HashSet<>();
        for(int[] obstacle : obstacles) {
        	String pos = obstacle[0] + ","+obstacle[1];
        	obs.add(pos);
        }
        for(int i=r_q-1; i>0; i--) {
        	String ops = i+","+c_q;
        	if(obs.contains(ops))
        		break;
        	attack++;
        }
        for(int i=r_q+1; i<=n; i++) {
        	String ops = i+","+c_q;
        	if(obs.contains(ops))
        		break;
        	attack++;
        }
        for(int i=c_q-1; i>0; i--) {
        	String ops = r_q +","+i;
        	if(obs.contains(ops))
        		break;
        	attack++;
        }
        for(int i=c_q+1; i<=n; i++) {
        	String ops = r_q +","+i;
        	if(obs.contains(ops))
        		break;
        	attack++;
        }
        for(int i=r_q-1, j=c_q-1; i>0 && j >0; i--,j--) {
        	String ops = i+","+j;
        	if(obs.contains(ops))
        		break;
        	attack++;
        }
        for(int i=r_q+1, j=c_q+1; i<=n && j<=n; i++,j++) {
        	String ops = i+","+j;
        	if(obs.contains(ops))
        		break;
        	attack++;
        }
        for(int i=r_q-1,j=c_q+1; i>0 && j<=n; i--,j++) {
        	String ops = i+","+j;
        	if(obs.contains(ops))
        		break;
        	attack++;
        }
        for(int i=r_q+1,j=c_q-1; i<=n && j>0; i++,j--) {
        	String ops = i+","+j;
        	if(obs.contains(ops))
        		break;
        	attack++;
        }
        
        return attack;
    }
	
	static int getMoneySpent(int[] keyboards, int[] drives, int s){
        int money = -1;
        if(keyboards.length < drives.length) {
        	int[] temp = keyboards;
        	keyboards = drives;
        	drives = temp;
        }
        TreeSet<Integer> bst = new TreeSet<>();
        for(int price : drives)
        	bst.add(price);
        
        for(int i=0; i<keyboards.length; i++) {
        	int price = keyboards[i];
        	int other = s - price;
        	if(other < 0)
        		continue;
        	Integer num = bst.floor(other);
        	if(num == null)
        		continue;
        	money = Math.max(money, price + num);
        }
        return money;
    }
	
	static String larrysArray(int[] A) {
        int inversions = 0;
		for(int i=0; i<A.length; i++) {
			int num = A[i];
			for(int j=i+1; j<A.length; j++) {
				if(A[j] < num)
					inversions++;
			}
		}
		if(inversions % 2 == 0)
			return "YES";
		return "NO";
    }
	
	
	static int[] climbingLeaderboard(int[] scores, int[] alice) {
        int m = alice.length;
        int[] answer = new int[m];
        int[] temp = new int[scores.length];
        int index = 0;
        Set<Integer> set = new HashSet<>();
        for(int score : scores) {
        	if(set.contains(score))
        		continue;
        	set.add(score);
        	temp[index++] = score;
        }
        for(int i=0; i<m; i++) {
        	int level = climbingLeaderboardHelp(temp, 0, index-1, alice[i]);
            answer[i] = level+1;
        }
        
        return answer;
    }
	
	static int climbingLeaderboardHelp(int[] scores, int left, int right, int target) {
		while(left <= right) {
			int mid = left + (right - left) / 2;
			if(target == scores[mid])
				return mid;
			else if(target < scores[mid])
				left = mid + 1;
			else
				right = mid - 1;
		}
		
		return left;
	}
	
	
	static long getWays(long n, long[] c){
        return getWaysHelp(n, c, c.length, new HashMap<>());
    }
	
	static long getWaysHelp(long n, long[] coins, int m, Map<String, Long> memorization) {
		if(n == 0)
			return 1;
		if(n < 0)
			return -0;
		if(m <= 0 && n > 0)
			return 0;
		String key = "" + n+","+m;
		if(memorization.containsKey(key))
			return memorization.get(key);
		long sum = 0;
		sum = getWaysHelp(n, coins, m-1, memorization) + getWaysHelp(n - coins[m-1], coins, m, memorization);
		memorization.put(key, sum);
		return sum;
	}
	
	
	static void fibonacciModified(int t1, int t2, int n) {
        BigInteger one = new BigInteger(String.valueOf(t1));
        BigInteger two = new BigInteger(String.valueOf(t2));
        for(int i=3; i<=n; i++) {
        	BigInteger next = one.add(two.pow(2));
        	one = two;
        	two = next;
        }
        System.out.println(two);
    }
	
	
	static String abbreviation(String a, String b) {
        int lengthA = a.length();
        int lengthB = b.length();
        if(lengthB > lengthA)
        	return "NO";
        boolean[][] dp = new boolean[lengthB+1][lengthA+1];
        dp[0][0] = true;
        for(int i=1; i<=lengthA; i++) {
        	char c = a.charAt(i-1);
        	if(c >= 'a' && c <= 'z')
        		dp[0][i] = true;
        	else
        		break;
        }
        
        for(int i=1; i<=lengthA; i++) {
        	char A = a.charAt(i-1);
        	for(int j=1; j<=lengthB; j++) {
        		char B = b.charAt(j-1);
        		if(A >= 'a' && A <= 'z') {
        			if(A == B) {
        				dp[j][i] = dp[j-1][i-1]; 
        			}
        			else {
        				dp[j][i] = dp[j][i-1];
        			}
        			if(B + 32 == A)
        				dp[j][i] = dp[j-1][i-1] || dp[j][i-1]; 
        		}
        		else {
        			if(A == B) {
        				dp[j][i] = dp[j-1][i-1]; 
        			}
        			else {
        				dp[j][i] = false;
        			}
        		}
        	}
        }
        
        if(dp[lengthB][lengthA])
        	return "YES";
		return "NO";
    }
	
	
	static int candies(int n, int[] arr) {
        int sum = 0;
        int[] candies = new int[n];
        Arrays.fill(candies, 1);
        for(int i=1; i<n; i++) {
        	if(arr[i] > arr[i-1])
        		candies[i] = candies[i-1] + 1;
        }
        for(int j=n-2; j>=0; j--) {
        	if(arr[j] > arr[j+1])
        		candies[j] = Math.max(candies[j], (candies[j + 1] + 1));
        }
        for(int candy : candies)
        	sum += candy;
        
        return sum;
    }
	
	
	static long substrings(String balls) {
        int mod = 1000000007;
		long f = 1;
		long answer = 0;
		int length = balls.length();
		for(int i=length-1; i>=0; i--) {
			answer = (answer + (balls.charAt(i) - '0') * (i+1) * f ) % mod;
			f = (f*10 + 1) % mod;
		}
		return answer;
    }
	
	
	static void AbsolutePermutation(int n, int k) {
		Set<Integer> used = new HashSet<>();
		List<Integer> head = new ArrayList<>(n);
		List<Integer> tail = new ArrayList<>();
		int[] help = {-k, k};
		int left = 1, right = n;
		for(;left-k<1; left++) {
			int num = left + k;
			if(num > n) {
				System.out.println(-1);
				return;
			}
			head.add(num);
			used.add(num);
		}
		for(; right+k>n && right>left; right--) {
			int num = right - k;
			if(num < 1 || used.contains(num)) {
				System.out.println(-1);
				return;
			}
			tail.add(num);
			used.add(num);
		}
		
		for(int i=left; i<=right; i++) {
			boolean end = true;
			for(int j : help) {
				int num = i + j;
				if(num >= 1 && num <= n) {
					if(!used.contains(num)) {
						used.add(num);
						head.add(num);
						end = false;
					}
				}
			}
			if(end) {
				System.out.println(-1);
				return;
			}
		}
		
		for(int i=tail.size()-1; i>=0; i--)
			head.add(tail.get(i));
		
		for(int i=0; i<head.size(); i++) {
			if(i == head.size()-1) {
				System.out.println(head.get(i));
			}
			else {
				System.out.print(head.get(i)+" ");
			}
		}
	}
	
	
	static void AbsolutePermutation2(int n, int k) {
		List<Integer> list = new ArrayList<>();
		boolean flag = true;
		int counter = 0;
		for(int i=1; i<=n; i++) {
			int num = flag ? i + k : i - k;
			if(!(num >= 1 && num <= n)) {
				System.out.println(-1);
				return;
			}
			list.add(num);
			counter++;
			if(counter == k) {
				counter = 0;
				flag = !flag;
			}
		}
		
		for(int i=0; i<n; i++) {
			if(i == list.size()-1) {
				System.out.println(list.get(i));
			}
			else {
				System.out.print(list.get(i)+" ");
			}
		}
	}
	
	
	static long sherlockAndAnagrams(String s){
        long anagrams = 0;
        int length = s.length();
        Map<String, Integer> counter = new HashMap<>();
        for(int j=0; j<length; j++) {
        	for(int i=j; i<length; i++) {
            	String sub = s.substring(j, i+1);
            	char[] array = sub.toCharArray();
            	Arrays.sort(array);
            	String key = new String(array);
            	counter.put(key, counter.getOrDefault(key, 0)+1);
            }
        }
        for(String key : counter.keySet()) {
        	int fre = counter.get(key);
        	if(fre > 1) {
        		System.out.println(key + " : "+ fre );
        		anagrams += fre;
        	}
        	anagrams += sherlockAndAnagramsHelp(fre);
        }
        
        return anagrams;
    }
	
	static long sherlockAndAnagramsHelp(int num) {
		long result = 1;
		for(int i=1; i<=num; i++) {
			result *= i;
		}
		return result / 2;
	}
	
	
	static int cost(int[] arr) {
		int n = arr.length;
		int[] low = new int[n];
		int[] high = new int[n];
		int[] dp = new int[n];
		for(int i=1; i<n; i++) {
			low[i] = Math.max(low[i-1], high[i-1] + Math.abs(arr[i-1] - 1));
			high[i] = Math.max(high[i-1] + Math.abs(arr[i] - arr[i-1]),  low[i-1] + Math.abs(arr[i] - 1));
			dp[i] = Math.max(low[i], high[i]);
		}
		return dp[n-1];
    }
	
	
	static int twoPluses(String[] grid) {
        int N = grid.length, M = grid[0].length();
        char[][] board = new char[N][M];
        for(int i=0; i<N; i++)
        	board[i] = grid[i].toCharArray();
        int[][] up = new int[N][M];
        int[][] down = new int[N][M];
        int[][] left = new int[N][M];
        int[][] right = new int[N][M];
        twoPlusesHelp(board, up, down, left, right);
        int area1 = twoPlusesArea(board, up, down, left, right);
        print(board);
        for(int i=0; i<N; i++) {
        	Arrays.fill(up[i], 0);
        	Arrays.fill(down[i], 0);
        	Arrays.fill(left[i], 0);
        	Arrays.fill(right[i], 0);
        }
        twoPlusesHelp(board, up, down, left, right);
        int area2 = twoPlusesArea(board, up, down, left, right);
        print(board);
		return area1 * area2;
    }
	
	static int twoPluses2(String[] grid) {
		int N = grid.length, M = grid[0].length();
        char[][] board = new char[N][M];
        for(int i=0; i<N; i++)
        	board[i] = grid[i].toCharArray();
        int[][] up = new int[N][M];
        int[][] down = new int[N][M];
        int[][] left = new int[N][M];
        int[][] right = new int[N][M];
		int area = twoPlusesSearch(board, up, down, left, right);
		return area;
	}
	
	static int twoPlusesSearch(char[][] board, int[][] up, int[][] down, int[][] left, int[][] right) {
		int N = board.length, M = board[0].length;
		int maxArea = -1;
		int[] pos = new int[2];
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(board[i][j] == 'B')
					continue;
				twoPlusesHelp(board, up, down, left, right);
				int temp = Math.min(up[i][j], Math.min(down[i][j], Math.min(left[i][j], right[i][j])));
				int area1 = temp * 4 + 1;
				System.out.println("x:"+i +" y:"+j+" area:"+area1+" len:"+temp);
				pos[0] = i; pos[1] = j;
				fillBlank(board, pos, temp, 'B');
				fillZero(up, down, left, right);
				twoPlusesHelp(board, up, down, left, right);
				int area2 = twoPlusesArea(board, up, down, left, right);
				int area = area1 * area2;
				maxArea = Math.max(maxArea, area);
				fillBlank(board, pos, temp, 'G');
				fillZero(up, down, left, right);
				
				while(temp > 0) {
					temp--;
					area1 = temp * 4 + 1;
					pos[0] = i; pos[1] = j;
					fillBlank(board, pos, temp, 'B');
					fillZero(up, down, left, right);
					twoPlusesHelp(board, up, down, left, right);
					area2 = twoPlusesArea(board, up, down, left, right);
					area = area1 * area2;
					maxArea = Math.max(maxArea, area);
					fillBlank(board, pos, temp, 'G');
					fillZero(up, down, left, right);
					
				}
				
			}
		}
		
		return maxArea;
	}
	
	static void fillBlank(char[][] board, int[] pos, int len, char c) {
		for(int i=pos[0]; i>=pos[0]-len; i--) {
			board[i][pos[1]] = c;
		}
		for(int i=pos[0]; i<=pos[0]+len; i++) {
			board[i][pos[1]] = c;
		}
		for(int i=pos[1]; i>=pos[1]-len; i--) {
			board[pos[0]][i] = c;
		}
		for(int i=pos[1]; i<=pos[1]+len; i++) {
			board[pos[0]][i] = c;
		}
	}
	
	static void fillZero(int[][] up, int[][] down, int[][] left, int[][] right) {
		int N = up.length;
		for(int i=0; i<N; i++) {
        	Arrays.fill(up[i], 0);
        	Arrays.fill(down[i], 0);
        	Arrays.fill(left[i], 0);
        	Arrays.fill(right[i], 0);
        }
	}
	
	static int twoPlusesArea(char[][] board, int[][] up, int[][] down, int[][] left, int[][] right) {
		int area = 0;
		int N = board.length, M = board[0].length;
		int max = -1;
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(board[i][j] == 'B')
					continue;
				int temp = Math.min(up[i][j], Math.min(down[i][j], Math.min(left[i][j], right[i][j])));
				max = Math.max(max, temp);
			}
		}
		area = max * 4 + 1;
		return area;
	}
	
	static void twoPlusesHelp(char[][] board, int[][] up, int[][] down, int[][] left, int[][] right) {
		int N = board.length, M = board[0].length;
		for(int i=0; i<M; i++) {
			for(int j=0; j<N-1; j++) {
				if(board[j][i] == 'B')
					continue;
				up[j+1][i] += up[j][i] + 1;
			}
		}
		for(int i=0; i<M; i++) {
			for(int j=N-1; j>0; j--) {
				if(board[j][i] == 'B')
					continue;
				down[j-1][i] += down[j][i] + 1;
			}
		}
		for(int i=0; i<N; i++) {
			for(int j=0; j<M-1; j++) {
				if(board[i][j] == 'B')
					continue;
				left[i][j+1] += left[i][j] + 1;
			}
		}
		for(int i=0; i<N; i++) {
			for(int j=M-1; j>0; j--) {
				if(board[i][j] == 'B')
					continue;
				right[i][j-1] += right[i][j] + 1;
			}
		}
	}
	
	static void print(char[][] board) {
		System.out.println();
		for(int i=0; i<board.length; i++) {
			System.out.println(Arrays.toString(board[i]));
		}
	}
	
	
	static void almostSorted(int[] arr) {
		int n = arr.length;
		int index = isSorted(arr);
        if(index == n) {
        	System.out.println("yes");
        	return;
        }
        int swapIndex = getSwapNextIndex(arr, index);
        swap(arr, index, swapIndex);
        if(isSorted(arr) == n) {
        	System.out.println("yes");
        	System.out.println("swap "+(index+1)+" "+(swapIndex+1));
        	return;
        }
        swap(arr, index, swapIndex);
        reverse(arr, index, swapIndex);
        if(isSorted(arr) == n) {
        	System.out.println("yes");
        	System.out.println("reverse "+(index+1)+" "+(swapIndex+1));
        	return;
        }
        System.out.println("no");
    }
	
	static void reverse(int[] array, int left, int right) {
		while(left < right) {
			int temp = array[left];
			array[left] = array[right];
			array[right] = temp;
			left++;
			right--;
		}
	}
	
	static void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
	
	static int getSwapNextIndex(int[] array, int index) {
		int n = array.length;
		for(int i=index+1; i<n; i++) {
			if(array[i] > array[index])
				return i-1;
		}
		return n-1;
	}
	
	static int isSorted(int[] array) {
		int n = array.length;
		for(int i=1; i<n; i++) {
			int pre = array[i-1];
			int cur = array[i];
			if(pre > cur)
				return i-1;
		}
		return n;
	}
	
	
	static String richieRich(String s, int n, int k){
		char[] array = s.toCharArray();
		int count = richieRichHelp(array, 0, array.length-1);
		if(count > k)
			return "-1";
		Set<String> set = new HashSet<>();
		richieRichChange(array, 0, array.length-1, set);
		k -= count;
		int left = 0, right = array.length-1;
		while(left <= right && k > 0) {
			char c1 = array[left];
			if(c1 != '9') {
				String key = left+","+right;
				if(set.contains(key)) {
					array[left] = '9';
					array[right] = '9';
					k--;
				}
				else {
					if(left == right) {
						array[left] = '9';
						k--;
					}
					else {
						if(k > 1) {
							array[left] = '9';
							array[right] = '9';
							k -= 2;
						}
					}
				}
			}
			
			left++;
			right--;
		}
		
		return new String(array);
    }
	
	static void richieRichChange(char[] array, int left, int right, Set<String> set) {
		while(left < right) {
			char c1 = array[left];
			char c2 = array[right];
			if(c1 == c2) {
				left++;
				right--;
				continue;
			}
			if(c1 < c2) {
				char temp = c1;
				c1 = c2;
				c2 = temp;
			}
			array[left] = c1;
			array[right] = c1;
			set.add(left+","+right);
			left++;
			right--;
		}
	}
	
	static int richieRichHelp(char[] array, int left, int right) {
		int count = 0;
		while(left < right) {
			char c1 = array[left];
			char c2 = array[right];
			if(c1 != c2)
				count++;
			left++;
			right--;
		}
		return count;
	}
	
	static int lilysHomework(int[] arr) {
        int swap = 0;
        int n = arr.length;
        for(int i=0; i<n; i++) {
        	int min = arr[i];
        	int index = -1;
        	for(int j=i+1; j<n; j++) {
        		if(arr[j] < min) {
        			min = arr[j];
        			index = j;
        		}
        	}
        	if(index != -1) {
        		swap++;
        		int temp = arr[i];
        		arr[i] = arr[index];
        		arr[index] = temp;
        	}
        }
        
        return swap;
    }
	
	
	static int lilysHomework2(int[] arr) {
		int swap = 0;
		int swap2 = 0;
		int n = arr.length;
		Map<Integer, Integer> map = new HashMap<>();
		Map<Integer, Integer> map2 = new HashMap<>();
		for(int i=0; i<arr.length; i++) {
			map.put(arr[i], i);
			map2.put(arr[i], i);
		}
		int[] copy = new int[n];
		int[] copy2 = arr.clone();
		for(int i=0; i<n; i++)
			copy[i] = arr[i];		
		Arrays.sort(arr);
		for(int i=0; i<arr.length; i++) {
			int num1 = arr[i];
			int num2 = copy[i];
			if(num1 != num2) {
				swap++;
				System.out.println("num1 : "+num1+" num2:"+num2+" i:"+i);
				int index = map.get(num1);
				int temp = copy[index];
				copy[index] = copy[i];
				copy[i] = temp;
				map.put(num2, map.get(num1));
			}
		}
		
		for(int i=n-1, j=0; i>=0; i--,j++) {
			int num1 = arr[j];
			int num2 = copy2[i];
			if(num1 != num2) {
				swap2++;
				int index = map2.get(num1);
				int temp = copy2[index];
				copy2[index] = copy2[i];
				copy2[i] = temp;
				map2.put(num2, map2.get(num1));
			}
		}
		
		
		return Math.min(swap, swap2);
	}
	
	
	static int commonChild(String s1, String s2){
        int length = s1.length();
        int[][] dp = new int[length+1][length+1];
        for(int i=1; i<=length; i++) {
        	char c1 = s1.charAt(i-1);
        	for(int j=1; j<=length; j++) {
        		char c2 = s2.charAt(j-1);
        		if(c1 == c2)
        			dp[i][j] = dp[i-1][j-1] + 1;
        		else
        			dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
        	}
        }
        return dp[length][length];
    }
	
	
	static int angryChildren(int k, int[] arr) {
        int min = Integer.MAX_VALUE;
        Arrays.sort(arr);
        for(int i=k-1; i<arr.length; i++) {
        	int right = arr[i];
        	int left = arr[i-k-1];
        	min = Math.min(min, right - left);
        }
        return min;
    }
	
	static int hackerlandRadioTransmitters(int[] x, int k) {
        int transmitters = 0;
        TreeSet<Integer> set = new TreeSet<>();
        for(int hourse : x)
        	set.add(hourse);
        Integer base = set.first();
        while(base != null) {
        	transmitters++;
        	Integer mid = set.floor(base + k);
        	base = set.higher(mid + k);
        }
        return transmitters;
    }
	
	
	static void gridlandMetro(int n, int m, int k, int[][] track) {
        long total = (long)n * (long)m;
        Map<Integer, List<int[]>> map = new HashMap<>();
        for(int i=0; i<k; i++) {
        	int row = track[i][0];
        	if(!map.containsKey(row))
        		map.put(row, new ArrayList<>());
        	map.get(row).add(track[i]);
        }
        gridlandMetroSorter sorter = new gridlandMetroSorter();
        for(int key : map.keySet()) {
        	List<int[]> list = map.get(key);
        	Collections.sort(list, sorter);
        	long temp = 0;
        	int left = list.get(0)[1], right = list.get(0)[2];
        	for(int i=1; i<list.size(); i++) {
        		int[] array = list.get(i);
        		if(right < array[1]) {
        			temp += right - left + 1;
        			left = array[1];
        			right = array[2];
        			continue;
        		}
        		right = array[2];
        		if(array[1] < left)
        			left = array[1];
        	}
        	temp += right - left + 1;
        	total -= temp;
        }
        System.out.println(total);
    }
	
	static class gridlandMetroSorter implements Comparator<int[]> {

		@Override
		public int compare(int[] o1, int[] o2) {
			int temp = o1[2] - o2[2];
			return temp != 0 ? temp : o1[1] - o2[1];
		}
		
	}
	
	static void minimumBribes(int[] q) {
       int n = q.length;
       int bribes = 0;
       for(int i=n-1; i>=0; i--) {
    	   int num = q[i];
    	   if(num == i + 1)
    		   continue;
    	   if(i > 0 && q[i-1] == i+1) {
    		   int temp = q[i-1];
    		   q[i-1] = q[i];
    		   q[i] = temp;
    		   bribes++;
    		   i++;
    		   continue;
    	   }
    	   
    	   if(i > 1 && q[i-2] == i+1) {
    		   int temp = q[i-2];
    		   q[i-2] = q[i-1];
    		   q[i-1] = q[i];
    		   q[i] = temp;
    		   bribes += 2;
    		   i++;
    		   continue;
    	   }
    	   System.out.println("Too chaotic");
    	   return;
       }
       System.out.println(bribes);
    }
	
	
	static void summingPieces(int[] array) {
		long[] sum = new long[1];
		summingPiecesHelp(array, 0, 0, sum);
		System.out.println(sum[0]);
	}
	
	static void summingPiecesHelp(int[] array, int index, long sum, long[] answer) {
		if(index == array.length) {
			answer[0] = (answer[0] + sum) % 1000000007;
			return;
		}
		long temp = 0;
		for(int i=index; i<array.length; i++) {
			temp = (temp + array[i])  % 1000000007;
			summingPiecesHelp(array, i+1, (sum+temp * (i-index + 1) %  1000000007)%1000000007, answer);
		}
	}
	
	
	static void summingPieces2(int[] array) {
		Map<Integer, Long> memoization = new HashMap<>();
		long answer = summingPieces(array, 0, 0, memoization);
		System.out.println(memoization);
		System.out.println(answer);
	}
	
	static long summingPieces(int[] array,int index, long sum, Map<Integer, Long> memoization) {
		if(index == array.length)
			return sum % 1000000007;
		long answer = 0, temp = 0;
		long right = sum*superPower(2, array.length - index - 1);
		if(memoization.containsKey(index))
			return (memoization.get(index) + right) % 1000000007;
		for(int i=index; i<array.length; i++) {
			temp = (temp + array[i])  % 1000000007;
			answer = (answer + summingPieces(array, i+1, (temp * (i-index + 1) %  1000000007), memoization)) % 1000000007;
		}
		memoization.put(index, answer);
		return (answer + right) % 1000000007;
	}
	
	static long superPower(long a,int n) {
		if(n == 0)
			return 1;
		if(n == 1)
			return a % 1000000007;
		if((n&1) == 1)
			return a * superPower(a*a % 1000000007,n/2) % 1000000007;
		return superPower(a*a % 1000000007, n/2) % 1000000007;
	}
	
	static void summingPiecesDP(int[] array) {
		int n = array.length;
		if(n == 1) {
			System.out.println(array[0]);
			return;
		}
		long[] dp = new long[n+1];
		long[] powerTwo = new long[n+1];
		dp[n] = 0;
		powerTwo[n] = 1;
		dp[n-1] = array[n-1];
		powerTwo[n-1] = 1;
		for(int i=n-2; i>=0; i--) {
			long sum = 0;
			long local = 0;
			for(int j=i; j<n; j++) {
				sum = (sum + array[j])  % 1000000007 ;
				local =((local + (sum* (j-i+1) % 1000000007) * powerTwo[j+1] % 1000000007) % 1000000007 + dp[j+1]) % 1000000007;
			}
			powerTwo[i] = (powerTwo[i+1] * 2) % 1000000007;
			dp[i] = local;
		}
		System.out.println(dp[0]);
	}
	
	static void summingPiecesDP2(int[] array) {
		int n = array.length;
		if(n == 1) {
			System.out.println(array[0]);
			return;
		}
		long[] times = new long[n];
		times[0] = superPower(2, n)-1;
		int y = n-2;
		long two = 1;
		int end = n % 2 == 0 ? n/2-1 : n/2;
		for(int i=1; i<=end; i++) {
			long pre = times[i-1];
			times[i] = ((pre + superPower(2, y) % 1000000007) - two) % 1000000007;
			two = (two * 2) % 1000000007;
			y--;
			times[n-1-i] = times[i];
		}
		times[n-1] = times[0];
		long sum = 0;
		for(int i=0; i<n; i++) {
			sum = (sum + times[i] * array[i] % 1000000007) % 1000000007;
		}
		System.out.println(Arrays.toString(times));
		System.out.println(sum);
	}
	
	
	static int substringDiff(String s1, String s2, int k) {
		int diff = 0;
		int n = s1.length();
		int[][] dp = new int[n+1][n+1];
		int[][] reverse = new int[n+1][n+1];
		int max = 0;
		int[] index = new int[2];
		for(int i=1; i<=n; i++) {
			for(int j=1; j<=n; j++) {
				if(s1.charAt(i-1) == s2.charAt(j-1))
					dp[i][j] = dp[i-1][j-1] + 1;
				if(dp[i][j] > max) {
					max = dp[i][j];
					index[0] = i;
					index[1] = j;
				}
			}
		}
		
		for(int i=n-1; i>=0; i--) {
			for(int j=n-1; j>=0; j--) {
				if(s1.charAt(i) == s2.charAt(j))
					reverse[i][j] = reverse[i+1][j+1] + 1;
			}
		}
		
		diff = substringDiffHelp(dp, reverse, new int[] {index[0] - max, index[1] - max},  new int[] {index[0]+1, index[1]+1}, k, max);
		
		return diff;
	}
	
	static int substringDiffHelp(int[][] dp,int[][] Rdp, int[]leftTop, int[] rightDown, int k, int diff) {
		int n = dp.length;
		int x = leftTop[0], y = leftTop[1];
		int copy = k;
		int max1 = diff;
		int max2 = diff;
		while(x > 0 && y > 0 && copy > 0) {
			int val = dp[x][y];
			if(val == 0)
				copy--;
			else {
				max1 = substringDiffHelp(dp, Rdp,new int[] {leftTop[0] - val, leftTop[1] - val}, new int[] {rightDown[0], rightDown[1]}, copy, max1+val);
				break;
			}
			x--;y--;
			max1++;
		}
		copy = k;
		x = rightDown[0];
		y = rightDown[1];
		while(x < n && y < n && copy > 0) {
			int val = Rdp[x-1][y-1];
			if(val == 0)
				copy--;
			else {
				max2 = substringDiffHelp(dp,Rdp, new int[] {leftTop[0], leftTop[1]}, new int[] {rightDown[0]+val, rightDown[1] + val}, copy, max2+val);
				break;
			}
			x++; y++;
			max2++;
		}
		System.out.println("max1:"+max1+" max2:"+max2+" S:"+k);
		diff = Math.max(max1, max2);
		return diff;
	}
	
	
	
	
	static String counterGame(long n) {
        List<Long> powerTwo = new ArrayList<>();
		powerTwo.add(1L);
		Set<Long> set = new HashSet<>();
		set.add(1L);
		for(int i=1; i<= 62; i++) {
			long pre = powerTwo.get(i-1);
			long num = pre * 2;
			powerTwo.add(num);
			set.add(num);
		}
		System.out.println(powerTwo);
		boolean turn = true;
		while(n != 1) {
			if(set.contains(n)) {
				n /= 2;
				turn = !turn;
				continue;
			}
			long decrease = counterGameHelp(n, powerTwo);
			n -= decrease;
			turn = !turn;
		}
		if(turn)
			return "Richard";
		return "Louise";
    }
	
	static long counterGameHelp(long n, List<Long> power) {
		int size = power.size();
		for(int i=0; i<size; i++) {
			long num = power.get(i);
			if(num > n)
				return power.get(i-1);
		}
		return power.get(size-1);
	}
	
	
	static String cipher(int k, String s) {
        int length = s.length();
        int len = length - k + 1;
        char[] array = new char[len];
        array[0] = s.charAt(0);
        array[len-1] = s.charAt(length-1);
        for(int i=1; i<len-1; i++) {
        	if(i < k)
        		array[i] = (char) ((s.charAt(i-1) - '0') ^ (s.charAt(i) - '0') + '0');
        	else
        		array[i] = (char) ((s.charAt(i-1) - '0') ^ (s.charAt(i) - '0') ^ (array[i-k] - '0')+ '0');
        }
        return new String(array);
    }
	
	static int sansaXor(int[] arr) {
        int xor = 0;
        if(arr.length % 2 == 0)
        	return 0;
        for(int i=0; i<arr.length; i+=2) {
        	xor ^= arr[i];
        }
        return xor;
    }
	
	
	static int evenTree(int n, int m, int[][] tree) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for(int[] edge : tree) {
        	int v = edge[0], p = edge[1];
        	if(!graph.containsKey(v))
        		graph.put(v, new HashSet<>());
        	graph.get(v).add(p);
        	if(!graph.containsKey(p))
        		graph.put(p, new HashSet<>());
        	graph.get(p).add(v);
        }
        int[] counter = new int[n+1];
        boolean[] visited = new boolean[n+1];
        visited[1] = true;
        evenTreeCountChild(graph, 1, visited, counter);
        Arrays.fill(visited, false);
        visited[1] = true;
        int edges = evenTreeCutEdge(graph, 1, visited, counter);
		return edges;
    }
	
	static int evenTreeCutEdge(Map<Integer, Set<Integer>> graph, int p, boolean[] visited, int[] counter) {
		int edge = 0;
		if(counter[p] <= 3)
			return edge;
		for(int adj : graph.get(p)) {
			if(visited[adj])
				continue;
			visited[adj] = true;
			int sum = counter[p];
			int part = counter[adj];
			counter[adj] = sum;
			edge += evenTreeCutEdge(graph, adj, visited, counter);
			counter[adj] = part;
			if((part % 2 == 0) && ((sum-part) % 2 == 0)) {
				edge++;
				counter[p] -= part;
			}
		}
		
		return edge;
	}
	
	
	static int evenTreeCountChild(Map<Integer, Set<Integer>> graph, int p, boolean[] visited, int[] counter) {
		int count = 0;
		for(int adj : graph.get(p)) {
			if(visited[adj])
				continue;
			visited[adj] = true;
			count += evenTreeCountChild(graph, adj, visited, counter);
		}
		counter[p] = count+1;
		return count+1;
	}
	
	
	static void bfs(int n, int m, int[][] edges, int s) {
        int[] answer = new int[n+1];
        Arrays.fill(answer, -1);
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for(int[] edge : edges) {
        	int p = edge[0], v = edge[1];
        	if(!graph.containsKey(p))
        		graph.put(p, new HashSet<>());
        	graph.get(p).add(v);
        	if(!graph.containsKey(v))
        		graph.put(v, new HashSet<>());
        	graph.get(v).add(p);
        }
        boolean[] visited = new boolean[n+1];
        visited[s] = true;
        Queue<Integer> queue = new LinkedList<>();
        if(graph.get(s) != null) {
        	for(int p : graph.get(s)) {
            	visited[p] = true;
            	queue.add(p);
            }
        }
        
        int distance = 0;
        while(!queue.isEmpty()) {
        	distance++;
        	int size = queue.size();
        	for(int i=0; i<size; i++) {
        		int p = queue.poll();
        		answer[p] = distance * 6;
        		for(int v : graph.get(p)) {
        			if(visited[v])
        				continue;
        			visited[v] = true;
        			queue.add(v);
        		}
        	}
        }
        
        
        
        for(int i=1; i<=n; i++) {
        	if(i == n) {
        		if(i != s) {
        			System.out.print(answer[i]);
        		}
        	}
        	else {
        		if(s == n) {
        			System.out.print(answer[i]);
        		}
        		else if(i!=s) {
        			System.out.print(answer[i]+" ");
        		}
        	}
        		
        }
        System.out.println();
    }
	
	
	static long theGreatXor(long x){
        long xor = 0;
        String binary = Long.toBinaryString(x);
        for(int i=0; i<binary.length(); i++) {
        	char c = binary.charAt(i);
        	if(c == '0') {
        		xor += theGreatXorHelp(binary.length()-i-1);
        	}
        }
        return xor;
    }
	
	static long theGreatXorHelp(int power) {
		return (long) Math.pow(2, power);
	}
	
	static void printShortestPath(int n, int i_start, int j_start, int i_end, int j_end) {
        boolean[][] visited = new boolean[n][n];
        int[][] dirs = {{-2,-1},{-2,1},{0,2},{2,1},{2,-1},{0,-2},{}};
        String[] name = {"UL","UR","R","LR","LL","L"};
        Queue<int[]> queue = new LinkedList<>();
        visited[i_start][j_start] = true;
        if(visited[i_end][j_end]) {
        	System.out.println(0);
        	System.out.println();
        	return;
        }
        Map<String, Object[]> path = new HashMap<>();
        path.put(i_start+","+j_start, null);
        queue.add(new int[] {i_start, j_start});
        int count = 0;
        while(!queue.isEmpty()) {
        	count++;
        	int size = queue.size();
        	for(int i=0; i<size; i++) {
        		int[] array = queue.poll();
        		String father = array[0] + ","+array[1];
        		for(int j=0; j<dirs.length; j++) {
        			int[] dir = dirs[j];
        			String moveName = name[j];
        			int x = array[0] + dir[0];
        			int y = array[1] + dir[1];
        			if(x < 0 || x >= n || y < 0 || y >= n || visited[x][y])
        				continue;
        			visited[x][y] = true;
        			String son = x+","+y;
        			path.put(son, new Object[] {father, moveName});
        			if(x == i_end && y == j_end) {
        				System.out.println(count);
        				Stack<String> stack = new Stack<>();
        				String key = son;
        				while(path.get(key) != null) {
        					Object[] temp = path.get(key);
        					stack.add(temp[1].toString());
        					key = temp[0].toString();
        				}
        				while(!stack.isEmpty()) {
        					String move = stack.pop();
        					if(stack.isEmpty())
        						System.out.println(move);
        					else
        						System.out.print(move+" ");
        				}
        				return;
        			}
        			queue.add(new int[] {x,y});
        		}
        	}
        }
        
        
        System.out.println("Impossible");
    }
	
	
	
	static int substringDiff2(String s1, String s2, int k) {
		int answer = 0;
		int n = s1.length();
		int[][] dp = new int[n][n];
		for(int i=0; i<n-answer; i++) {
			for(int j=0; j<n-answer; j++) {
				int mismatch = 0;
				int l_ij = 0;
				if(i == 0 || j == 0) {
					
				}
				else if(s1.charAt(i-1) == s2.charAt(j-1)) {
					mismatch = k;
					l_ij = dp[i-1][j-1];
				}
				else {
					mismatch = k-1;
					l_ij = dp[i-1][j-1];
				}
				
				while(mismatch <= k && i+l_ij < n && j + l_ij < n) {
					if(s1.charAt(i+l_ij) != s2.charAt(j+l_ij))
						mismatch++;
					if(mismatch == k+1)
						break;
					l_ij++;
				}
				
				dp[i][j] = l_ij;
				answer = Math.max(answer, l_ij);
				
			}
		}
		
		return answer;
	}
	
	
	static int unboundedKnapsack(int k, int[] arr) {
		Arrays.sort(arr);
        boolean[] dp = new boolean[k+1];
        dp[0] = true;
        for(int i=0; i<k; i++) {
        	if(dp[i]) {
        		for(int j=0; j<arr.length; j++) {
        			int num = arr[j] + i;
        			if(num > k)
        				break;
        			dp[num] = true;
        		}
        	}
        }
        
        for(int i=k; i>0; i--)
        	if(dp[i])
        		return i;
		return 0;
    }
	
	
	
	static long journeyToMoon(int n, int[][] astronaut) {
        long ways = 0;
        Map<Integer, Set<Integer>> map = new HashMap<>();
        Set<Integer> used = new HashSet<>();
        List<Set<Integer>> list = new ArrayList<>();
        for(int[] array : astronaut) {
        	int one = array[0];
        	int two = array[1];
        	used.add(one);
        	used.add(two);
        	Set<Integer> set_one = map.get(one);
        	Set<Integer> set_two = map.get(two);
        	if(set_one == null && set_two == null) {
        		Set<Integer> temp = new HashSet<>();
        		temp.add(one);
        		temp.add(two);
        		map.put(one, temp);
        		map.put(two, temp);
        		list.add(temp);
        	}
        	else if(set_one == null) {
        		set_two.add(one);
        		map.put(one, set_two);
        	}
        	else if(set_two == null) {
        		set_one.add(two);
        		map.put(two, set_one);
        	}
        	else {
        		if(set_one == set_two)
        			continue;
        		Set<Integer> small = set_one.size() < set_two.size() ? set_one : set_two;
        		Set<Integer> big = small == set_one ? set_two : set_one;
        		big.addAll(small);
        		for(Integer num : small)
        			map.put(num, big);
        		list.remove(small);
        			
        	}
        }
        int people_include = 0;
        int people_left = n - used.size();
        for(int i=0; i<list.size(); i++) {
        	Set<Integer> set = list.get(i);
        	people_include += set.size();
        	int size = 0;
        	for(int j=i+1; j<list.size(); j++)
        		size += list.get(j).size();
        	ways += size * set.size();
        }
        for(int i=0; i<people_left; i++) {
        	ways += people_include + people_left - 1 - i;
        }
        
        
        return ways;
    }
	
	static class Node {
		boolean end;
		int count;
		Node[] next = new Node[26];
	}
	
	static Node addName(Node root, String name) {
		Node node = root;
		for(char c : name.toCharArray()) {
			int i = c - 'a';
			if(node.next[i] == null) {
				node.next[i] = new Node();
			}
			node = node.next[i];
			node.count++;
			
		}
		return root;
	}
	
	static Node getNode(Node root, String name) {
		Node node = root;
		for(char c : name.toCharArray()) {
			int i = c - 'a';
			node = node.next[i];
			if(node == null)
				return node;
		}
		return node;
	}
	
	static int getCountStartingOf(Node root, String partial) {
		Node node = getNode(root, partial);
		if(node != null)
			return node.count;
		return 0;
	}
	
	static void contacts(String[][] queries, BufferedWriter writer) throws IOException {
		Node root = new Node();
        for(String[] query : queries) {
        	String command = query[0];
        	String name = query[1];
        	if(command.equals("add")) {
        		root = addName(root, name);
        	}
        	else {
        		int count = getCountStartingOf(root, name);
        		writer.write(count+"\n");
        	}
        }
    }
	
	
	static int downToZero(int n) {        
        return dp[n];
    }
	static  int[] dp = new int[100001];
    
    
	static void init(){
        for(int i=1; i<=1000000; i++){
            dp[i] = Integer.MAX_VALUE;
            int limit = (int)Math.sqrt(i);
            for(int j=2; j<=limit; j++){
                if(i % j != 0)
                    continue;
                dp[i] = Math.min(dp[i], dp[i/j] + 1);
            }
            dp[i] = Math.min(dp[i], dp[i-1] + 1);
        }
    }
	
	
	static long solve(int[] arr) {
		long indexProduct = 0;
		TreeMap<Integer, List<Integer>> map = new TreeMap<>();
		for(int i=0; i<arr.length; i++) {
			int num = arr[i];
			List<Integer> list = map.get(num);
			if(list == null) {
				list = new ArrayList<>();
				map.put(num, list);
			}
			list.add(i);
		}
		for(int i=1; i<arr.length-1; i++) {
			int num = arr[i];
			long left = 0, right = Integer.MAX_VALUE;
			Map.Entry<Integer, List<Integer>> entry = map.higherEntry(num);
			while(entry != null) {
				List<Integer> list = entry.getValue();
				for(int index : list) {
					if(index < i)
						left = Math.max(left, index);
					else if(index > i)
						right = Math.min(right, index);
				}
				num = entry.getKey();
				entry = map.higherEntry(num);
			}
			left++;right++;
			indexProduct = Math.max(indexProduct, left * right);
		}
		return indexProduct;
    }
	
	
	static void SimpleTextEditor(){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Stack<String> stack = new Stack<>();
        String S = "";
        stack.push(S);
        for(int i=0; i<n; i++) {
        	int op = scanner.nextInt();
        	if(op == 1) {
        		S += scanner.next();
        		stack.push(S);
        	}
        	else if(op == 2) {
        		S = S.substring(0, S.length() - scanner.nextInt());
        		stack.push(S);
        	}
        	else if(op == 3) {
        		System.out.println(S.charAt(scanner.nextInt() - 1));
        	}
        	else {
        		stack.pop();
        		S = stack.peek();
        	}
        }
        scanner.close();
    }
	
	
	
	static String isBalanced(String s) {
		if(s.isEmpty())
			return "YES";
		if(s.charAt(0) == ')' || s.charAt(0) == ']' || s.charAt(0) == '}')
			return "NO";
		int n = s.length();
		int[][] array = new int[n][3];
		Stack<Integer> one_stack = new Stack<>();
		Stack<Integer> two_stack = new Stack<>();
		Stack<Integer> three_stack = new Stack<>();
		int one = 0;
		int two = 0;
		int three = 0;
		for(int i=0; i<s.length(); i++) {
			char c = s.charAt(i);
			boolean isRight = false;
			if(c == '(') {
				one++;
				one_stack.add(i);
			}
			else if(c == '[') {
				two++;
				two_stack.add(i);
			}
			else if(c == '{') {
				three++;
				three_stack.add(i);
			}
			else if(c == ')') {
				one--;
				isRight = true;
			}
			else if(c == ']') {
				two--;
				isRight = true;
			}
			else if(c == '}') {
				three--;
				isRight = true;
			}
			
			if(one < 0 || two < 0 || three < 0)
				return "NO";
			
			if(isRight) {
				int pre_i = (c == ')' ? one_stack.pop() : ( c == ']' ? two_stack.pop() : three_stack.pop()))-1;
				if(pre_i == -1) {
					if(one != 0 || two != 0 || three != 0)
						return "NO";
					continue;
				}
				int[] pre_arr = array[pre_i];
				if(one - pre_arr[0] != 0)
					return "NO";
				if(two - pre_arr[1] != 0)
					return "NO";
				if(three - pre_arr[2] != 0)
					return "NO";
			}
			array[i] = new int[] {one, two, three};
		}
		if(one != 0 || two != 0 || three != 0)
			return "NO";
		
		return "YES";
    }
	
	
	static void twoStacks(int x, int[] a, int[] b) {
        System.out.println(twoStacksDP(x, a, b, 0, 0, new HashMap<>(), 0));
    }
	static int twoStacksDP(int limit, int[] a, int[] b, int i_a, int i_b, Map<String, Integer> memo, long sum) {
		if(sum > limit)
			return -1;
		String key = i_a+","+i_b;
		int count = 0;
		if(memo.containsKey(key))
			return memo.get(key);
		if(i_a < a.length) {
			int temp = twoStacksDP(limit, a, b, i_a+1, i_b, memo, sum + a[i_a]);
			if(temp != -1) {
				count = Math.max(temp + 1, count);
			}
		}
		if(i_b < b.length) {
			int temp = twoStacksDP(limit, a, b, i_a, i_b+1, memo, sum + b[i_b]);
			if(temp != -1) {
				count = Math.max(temp + 1, count);
			}
		}
		memo.put(key, count);
		return count;
	}
	
	
	static void twoStacksGreedy(int x, int[] a, int[] b) {
		int index_a = 0;
		int index_b = 0;
		long sum = 0;
		int count = 0;
		while(index_a < a.length || index_b < b.length) {
			if(index_a == a.length) {
				if(sum + b[index_b] > x)
					break;
				sum += b[index_b++];
			}
			else if(index_b == b.length) {
				if(sum + a[index_a] > x)
					break;
				sum += a[index_a++];
			}
			else {
				if(a[index_a] < b[index_b]) {
					if(sum + a[index_a] > x)
						break;
					sum += a[index_a];
					index_a++;
				}
				else {
					if(sum + b[index_b] > x)
						break;
					sum += b[index_b];
					index_b++;
				}
			}
			count++;
		}
		System.out.println(count);
	}
	
	static void twoStacks2(int x, int[] a, int[] b) {
		long sum = 0;
		int max = 0;
		TreeMap<Long, Integer> map_a = new TreeMap<>();
		TreeMap<Long, Integer> map_b = new TreeMap<>();
		for(int i=0; i<a.length; i++) {
			sum += a[i];
			if(sum > x)
				break;
			map_a.put(sum, i+1);
		}
		sum = 0;
		for(int i=0; i<b.length; i++) {
			sum += b[i];
			if(sum > x)
				break;
			map_b.put(sum, i+1);
		}
		
		sum = 0;
		int temp = 0;
		for(int i=0; i<a.length; i++) {
			sum += a[i];
			if(sum > x)
				break;
			temp++;
			Map.Entry<Long, Integer> entry = map_b.floorEntry(x - sum);
			if(entry != null) {
				max = Math.max(max, temp + entry.getValue());
			}
			
		}
		max = Math.max(max, temp);
		temp = 0;
		sum = 0;
		for(int i=0; i<b.length; i++) {
			sum += b[i];
			if(sum > x)
				break;
			temp++;
			Map.Entry<Long, Integer> entry = map_a.floorEntry(x - sum);
			if(entry != null) {
				max = Math.max(max, temp + entry.getValue());
			}
		}
		max = Math.max(max, temp);
		System.out.println(max);
	}
	
	static int minimumMoves(String[] grid, int startX, int startY, int goalX, int goalY) {
		int moves = 0;
		if(startX == goalX && startY == goalY)
			return moves;
		int m = grid.length, n = grid[0].length();
		int[][] dp = new int[m][n];
		char[][] plane = new char[m][n];
		for(int i=0; i<m; i++) {
			plane[i] = grid[i].toCharArray();
			Arrays.fill(dp[i], Integer.MAX_VALUE);
		}
		dp[startX][startY] = 0;
		Queue<int[]> queue = new LinkedList<>();
		queue.add(new int[] {startX, startY});
		while(!queue.isEmpty()) {
			int size = queue.size();
			for(int i=0; i<size; i++) {
				int[] pos = queue.poll();
				
				int up = pos[0] - 1;
				while(true) {
					if(up < 0) {
						up = 0;
						break;
					}
					if(plane[up][pos[1]] == 'X') {
						up++;
						break;
					}
					if(up == goalX && pos[1] == goalY)
						return moves + 1;
					if(dp[up][pos[1]] > moves + 1) {
						dp[up][pos[1]] = moves + 1;
						queue.add(new int[] {up, pos[1]});
					}
					up--;
				}
				
				int down = pos[0] + 1;
				while(true) {
					if(down >= m) {
						down = m-1;
						break;
					}
					if(plane[down][pos[1]] == 'X') {
						down--;
						break;
					}
					if(down == goalX && pos[1] == goalY)
						return moves + 1;
					if(dp[down][pos[1]] > moves + 1) {
						dp[down][pos[1]] = moves + 1;
						queue.add(new int[] {down, pos[1]});
					}
					down++;
				}
				
				
				int left = pos[1] - 1;
				while(true) {
					if(left < 0) {
						left = 0;
						break;
					}
					if(plane[pos[0]][left] == 'X') {
						left++;
						break;
					}
					if(pos[0] == goalX && left == goalY)
						return moves + 1;
					if(dp[pos[0]][left] > moves + 1) {
						dp[pos[0]][left] = moves + 1;
						queue.add(new int[] {pos[0], left});
					}
					left--;
				}
				
				
				int right = pos[1] + 1;
				while(true) {
					if(right >= n) {
						right = n-1;
						break;
					}
					if(plane[pos[0]][right] == 'X') {
						right--;
						break;
					}
					if(pos[0] == goalX && right == goalY)
						return moves + 1;
					if(dp[pos[0]][right] > moves + 1) {
						dp[pos[0]][right] = moves + 1;
						queue.add(new int[] {pos[0], right});
					}
					right++;
				}
				
			}
			moves++;
		}
		return -1;
    }
	
	static class JimandtheSkyscrapersNode {
		int val;
		JimandtheSkyscrapersNode left, right;
		PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> a-b);
		JimandtheSkyscrapersNode(int val, int index){
			this.val = val;
			pq.add(index);
		}
	}
	
	static JimandtheSkyscrapersNode build(JimandtheSkyscrapersNode node, int val, int index, int[] minIndex) {
		if(node == null) {
			return new JimandtheSkyscrapersNode(val, index);
		}
		if(val < node.val) {
			minIndex[0] = Math.min(minIndex[0], node.pq.peek());
			node.left = build(node.left, val, index, minIndex);
		}
		else if(val > node.val) {
			node.pq.add(index);
			node.right = build(node.right, val, index, minIndex);
		}
		else {
			node.pq.add(index);
		}
		return node;
	}
	
	static long JimandtheSkyscrapers(int[] arr) {
		long pairs = 0;
		int[] max = new int[arr.length];
		Stack<Integer> stack = new Stack<>();
		Map<Integer, List<Integer>> map = new HashMap<>();
		for(int i=0; i<arr.length; i++) {
			List<Integer> list = map.get(arr[i]);
			if(list == null) {
				list = new ArrayList<>();
				map.put(arr[i], list);
			}
			list.add(i);
			
			if(stack.isEmpty() || arr[stack.peek()] >= arr[i]) {
				stack.push(i);
			}
			else {
				while(!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
					max[stack.pop()] = i;
				}
				stack.push(i);
			}
			
		}
		while(!stack.isEmpty()) {
			max[stack.pop()] = arr.length;
		}
		
		for(Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
			List<Integer> list = entry.getValue();
			for(int i=0; i<list.size(); i++) {
				int left = list.get(i);
				if(max[left] == arr.length) {
					pairs += ((1 + (long)(list.size() - i - 1)) * ((long)list.size() - i - 1)) / 2;
					break;
				}
				for(int j=i+1; j<list.size(); j++) {
					int right = list.get(j);
					if(max[left] > right) {
						pairs++;
					}
					else {
						break;
					}
				}
			}
		}
		
		return pairs * 2;
    }
	
	
	
	static List<Integer> getPrime(int to){
		List<Integer> primes = new ArrayList<>();
		boolean[] table = new boolean[to+1];
		for(int i=2; i<=to; i++) {
			if(!table[i]) {
				for(int j=2; i*j<=to; j++) {
					table[i*j] = true;
				}
			}
		}
		for(int i=2; i<=to; i++)
			if(!table[i])
				primes.add(i);
		return primes;
	}
	
	
	static void waiter(int[] number, int q) {
		List<Integer> primes = getPrime(10000);
		int counter = 0;
		while(counter < q) {
			if((counter & 1 ) == 0) {
				for(int j=0; j<number.length; j++) {
					if(number[j] == -1)
						continue;
					if(number[j] % primes.get(counter) == 0) {
						System.out.println(number[j]);
						number[j] = -1;
					}
				}
			}
			else {
				for(int j=number.length-1; j>=0; j--) {
					if(number[j] == -1)
						continue;
					if(number[j] % primes.get(counter) == 0) {
						System.out.println(number[j]);
						number[j] = -1;
					}
				}
			}
			counter++;
		}
		if(counter % 2 == 1) {
			for(int num : number) {
				if(num != -1)
					System.out.println(num);
			}
		}
		else {
			for(int i=number.length-1; i>=0; i--) {
				if(number[i] != -1)
					System.out.println(number[i]);
			}
		}
    }
	
	static int flippingMatrix(int[][] matrix) {
        int sum = 0;
        int n = matrix.length / 2;
        for(int i=0; i<n; i++) {
        	for(int j=0; j<n; j++) {
        		int max =  Math.max(matrix[i][n*2 - 1 - j], Math.max(matrix[n*2 - i - 1][j], Math.max(matrix[i][j], matrix[n*2 - 1 -i][n*2 - 1 - j])));
        		sum += max;
        	}
        }
        return sum;
    }
	
	static void GamingArray(int[] array, BufferedWriter bufferedWriter) throws IOException {
		int winner = 1;
		PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> b-a);
		Map<Integer, Integer> index = new HashMap<>();
		for(int i=0; i<array.length; i++) {
			pq.add(array[i]);
			index.put(array[i], i);
		}
		Integer right = null;
		while(true) {
			Integer max = pq.poll();
			if(max == null)
				break;
			int i = index.get(max);
			if(right == null || i < right) {
				right = i;
				winner = winner ^ 1;
			}
		}
		
		if(winner == 1) {
			bufferedWriter.write("ANDY");
		}
		else {
			bufferedWriter.write("BOB");
		}
	}
	
	
	static long andProduct(long a, long b) {
		long answer = 0;
		String bit = Long.toBinaryString(a);
		String bit_b = Long.toBinaryString(b);
		long two = 1;
		for(int i=bit.length()-1; i>=0; i--) {
			char c = bit.charAt(i);
			if(c == '0' ||(c == '1' && a + two <= b) || bit_b.charAt(i) == '0') {
				two *= 2;
				continue;
			}
			answer += two;
			two *= 2;
		}
		return answer;
    }
	
	static void whatsNext(long[] arr, List<String> debug) {
        List<Long> list = new ArrayList<>();
        int n = arr.length;
    	int index = n-1;
        if(n % 2 == 0) {
        	index--;
        	if(n == 2) {
        		if(arr[n-2] > 1)
        			list.add(arr[n-2] - 1);
        		list.add(arr[n-1]+1);
        		
        		list.add(1L);
        		index--;
        	}
        	else {
        		if(arr[n-2] > 1)
        			list.add(arr[n-2] - 1);
        		list.add(arr[n-1]+1);
        		
        		if(arr[n-3] > 1) {
        			list.add(1L);
        			list.add(arr[n-3] - 1);
        			index = n-4;
        		}
        		else {
        			list.add(arr[n-4] + 1);
        			index = n-5;
        		}
        	}
        	
        }
        else {
        	arr[index]--;
        	if(arr[index] == 0) {
        		if(list.isEmpty()) {
        			//list.add(arr[index]);
        		}
        		else {
        			list.set(0, list.get(0)+1);
        		}
        	}
        	else {
        		list.add(arr[index]);
        	}
        	list.add(1L);
        	index--;
        	if(index >= 0) {
        		if(arr[index] - 1 == 0) {
            		list.add(arr[index-1] + 1);
            		index -= 2;
            	}
            	else {
            		list.add(1L);
            		list.add(arr[index] - 1);
            		index--;
            	}
        	}
        	else {
        		list.add(1L);
        	}
        }
    	
    	for(int i=index; i>=0; i--)
    		list.add(arr[i]);
    	debug.add(list.size()+"");

//    	System.out.println(list.size());
//        for(int i=list.size()-1; i>=0; i--) {
//        	if(i == 0)
//        		System.out.println(list.get(i));
//        	else
//        		System.out.print(list.get(i) + " ");
//        }
    	StringBuilder sb = new StringBuilder();
        for(int i=list.size()-1; i>=0; i--) {
        	if(i == 0)
        		sb.append(list.get(i)+"");
        	else
        		sb.append(list.get(i) + " ");
        }
        debug.add(sb.toString());
    }
	
	
	 static void debug(List<String> list) {
		File file = new File("F:\\hackerrank\\input02.txt");
		try (BufferedReader reader = new BufferedReader(new FileReader(file));){
			String line = "";
			int case_counter = 1;
			List<String> temp = new ArrayList<>();
			//reader.readLine();
			while((line = reader.readLine()) != null) {
				temp.add(line);
			}
			System.out.println(temp);
			for(int i=0; i<temp.size(); i+=2) {
				if(!list.get(i).equals(temp.get(i))) {
					System.out.println(case_counter+"大小错误");
					System.out.println("正确:"+temp.get(i)+" 我的:"+list.get(i));
					return;
				}
				if(!list.get(i+1).equals(temp.get(i+1))) {
					System.out.println(case_counter+"错误");
					System.out.println("正确:"+temp.get(i+1));
					System.out.println(" 我的:"+list.get(i+1));
					return;
				}
				
				
				case_counter++;
			}
			System.out.println("pass");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	static long winningLotteryTicket(String[] tickets) {
        long count=0;
        int[] masks = new int[tickets.length];
        char[] chs = null;
        int mask = 0;
        int mask_full =  0b1111111111;
        for(int i=0;i<tickets.length;i++){
            chs = tickets[i].toCharArray();
            mask = 0;
            for(char ch:chs){
                int digit = ch - '0';
                mask |= (1 << digit);
            }
            masks[i] = mask;
        }
        
        for(int i=0;i<tickets.length-1;i++){
            int mask_i = masks[i];
           for(int j=i+1;j<tickets.length;j++){
               int mask_j = masks[j];
               int mask_i_j_concatenated =  (mask_i | mask_j);
            if (mask_i_j_concatenated == mask_full) {
                count++;
            }
           }
       }
        return count;

    }
	
	static long winningLotteryTicket2(String[] tickets) {
		long count = 0;
		int[] masks = new int[1024];
		for(String ticket : tickets) {
			int mask = 0;
			for(char c : ticket.toCharArray()) {
				mask |= 1 << (c - '0');
			}
			masks[mask]++;
		}
		for(int i=1; i<1024; i++) {
			if(masks[i] == 0)
				continue;
			for(int j=i+1; j<1024; j++) {
				if(masks[j] == 0 || (i | j )!= 0b1111111111)
					continue;
				count += (long)masks[i] * masks[j];
			}
		}
		if(masks[1023] > 1) {
			count += (1L + masks[1023] - 1) * (masks[1023] - 1) / 2;
		}
		
		return count;
	}
	
	static void permutation(int[] array) {
		Arrays.sort(array);
		permutation(array,new ArrayList<>(), new boolean[array.length]);
	}
	
	static void permutation(int[] array, List<Integer> list, boolean[] used) {
		if(list.size() == array.length) {
			int xor = Integer.MIN_VALUE;
			for(int i=1; i<list.size(); i++) {
				xor = Math.max(xor, list.get(i) ^ list.get(i-1));
			}
			System.out.println(list+":"+xor);
		}
		else {
			for(int i=0; i<array.length; i++) {
				if(used[i])
					continue;
				used[i] = true;
				list.add(array[i]);
				permutation(array, list, used);
				list.remove(list.size()-1);
				used[i] = false;
			}
		}
	}
	
	
	
	static int anotherMinimaxProblem(int[] a) {
		int min = Integer.MAX_VALUE;
		int commen = 100;
		for(int i=1; i<a.length; i++) {
			int temp = 0;
			for(int j=31; j>=0; j--) {
				int b1 = a[i-1] & (1 << j);
				int b2 = a[i] & (1 << j);
				if(b1 != b2)
					break;
				temp++;
			}
			commen = Math.min(commen, temp);
		}
		List<Integer> A = new ArrayList<>();
		List<Integer> B = new ArrayList<>();
		for(int num : a) {
			int bit = (1 << (31 - commen)) & num;
			if(bit == 0)
				A.add(num);
			else
				B.add(num);
		}
		
		if(A.isEmpty()) {
			for(int i=0; i<B.size(); i++) {
				for(int j=i+1; j<B.size(); j++) {
					min = Math.min(min, B.get(i) ^ B.get(j));
				}
			}
		}
		else if(B.isEmpty()) {
			for(int i=0; i<A.size(); i++) {
				for(int j=i+1; j<A.size(); j++) {
					min = Math.min(min, A.get(i) ^ A.get(j));
				}
			}
		}
		else {
			for(int num1 : A) {
				for(int num2 : B) {
					min = Math.min(min, num1 ^ num2);
				}
			}
		}
		
		return min;
    }
	
	static int[] maxSubarray(int[] arr) {
        int[] answer = new int[2];
        int max_subarray_sum = 0;
        int max = Integer.MIN_VALUE;
        int max_subarray = Integer.MIN_VALUE, max_subsequence = 0;
        for(int num : arr) {
        	max_subarray_sum += num;
        	max_subarray = Math.max(max_subarray, max_subarray_sum);
        	if(max_subarray_sum < 0)
        		max_subarray_sum = 0;
        	if(num > 0)
        		max_subsequence += num;
        	max = Math.max(max, num);
        }
        answer[0] = max_subarray;
        if(max_subsequence > 0)
        	answer[1] = max_subsequence;
        else
        	answer[1] = max;
        return answer;
    }
	
	//public static long[] dp;

	public static long maxScore(int[] arr,int i,int n){

	    if(i == n)
	        return 0;

	    if(i>n)
	        return Long.MIN_VALUE;

	    if(n-i<=4){
	        long ans = 0;
	        for(int j=i;j<n&&j<i+3;j++)
	            ans = ans + arr[j];
	        return ans;
	    }

	    if(dp[i] != 0)
	        return dp[i];

	    long ans1 = Long.MAX_VALUE;

	                        // player 1 take 1 brick
	    // player 2 also take 1 brick
	    long a = maxScore(arr,i+2,n);
	    if(a!=Long.MIN_VALUE)
	        ans1 = Math.min(ans1,arr[i]+a);
	    //player 2 take 2 bricks
	    a = maxScore(arr,i+3,n);
	    if(a!=Long.MIN_VALUE)
	        ans1 = Math.min(ans1,arr[i]+a);
	    // player 2 take 3 bricks
	    a = maxScore(arr,i+4,n);
	    if(a!=Long.MIN_VALUE)
	        ans1 = Math.min(ans1,arr[i]+a);

	                    // player 1 take 2 bricks
	    long ans2 = Long.MAX_VALUE;
	    // player 2 take 1 brick
	    a = maxScore(arr,i+3,n);
	    if(a!=Long.MIN_VALUE)
	        ans2 = Math.min(ans2,arr[i]+arr[i+1]+a);
	    // player 2 take 2 bricks
	    a = maxScore(arr,i+4,n);
	    if(a!=Long.MIN_VALUE)
	        ans2 = Math.min(ans2,arr[i]+arr[i+1]+a);
	    // player 2 take 3 bricks
	    a = maxScore(arr,i+5,n);
	    if(a!=Long.MIN_VALUE)
	        ans2 = Math.min(ans2,arr[i]+arr[i+1]+a);

	                    // player 1 take 3 bricks
	    long ans3 = Long.MAX_VALUE;
	    // player 2 take 1 brick
	    a = maxScore(arr,i+4,n);
	    if(a!=Long.MIN_VALUE)
	        ans3 = Math.min(ans3,arr[i]+arr[i+1]+arr[i+2]+a);
	    // player 2 take 2 bricks
	    a = maxScore(arr,i+5,n);
	    if(a!=Long.MIN_VALUE)
	        ans3 = Math.min(ans3,arr[i]+arr[i+1]+arr[i+2]+a);
	    // player 2 take 3 bricks
	    a = maxScore(arr,i+6,n);
	    if(a!=Long.MIN_VALUE)
	        ans3 = Math.min(ans3,arr[i]+arr[i+1]+arr[i+2]+a);

	    long ans = Math.max(ans1,Math.max(ans2,ans3));

	    dp[i] = (int) ans;

	    return ans;

	}
	
	
	static int arraySplitting(int[] arr) {
		long[] preSum = new long[arr.length];
		long sum = 0;
		for(int i=0; i<arr.length; i++) {
			sum += arr[i];
			preSum[i] = sum;
		}
		int score = arraySplittingDP(arr, preSum, 0, arr.length-1, new HashMap<>());
		return score;
    }
	
	static int arraySplittingDP(int[] arr, long[] preSum, int left, int right, Map<String, Integer> dp) {
		if(left == right)
			return 0;
		String key = left + "," +right;
		if(dp.containsKey(key))
			return dp.get(key);
		int score = 0;
		for(int i=left; i<right; i++) {
			if(preSum[i] - preSum[left] + arr[left] != preSum[right] - preSum[i])
				continue;
			int left_score = arraySplittingDP(arr, preSum, left, i, dp);
			int right_score = arraySplittingDP(arr, preSum, i+1, right, dp);
			score = Math.max(score, 1+Math.max(left_score, right_score));
			break;
		}
		dp.put(key, score);
		return score;
	}
	
	
	
	static int playWithWords(String s) {
        int n = s.length();
		int[][] dp = new int[n][n];
		for(int i=n-1; i>=0; i--) {
			dp[i][i] = 1;
			char c1 = s.charAt(i);
			for(int j=i+1; j<n; j++) {
				char c2 = s.charAt(j);
				if(c1 == c2) {
					dp[i][j] = dp[i+1][j-1] + 2;
				}
				else {
					dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
				}
			}
		}
		int score = 1;
		
		for(int i=n-2; i>0; i--) {
			score = Math.max(score, dp[0][i] * dp[i+1][n-1]);
		}
		
		return score;
    }
	
	
	
	static long mandragora(int[] H) {
		long P = 0;
		long[] preSum = new long[H.length];
		long sum = 0;
		Arrays.sort(H);
		for(int i=0; i<H.length; i++) {
			sum += H[i];
			preSum[i] = sum;
		}
		int S = 1;
		for(int i=0; i<H.length; i++) {
			P = Math.max(P, S * (preSum[H.length-1] - preSum[i] + H[i]));
			S++;
		}
		return P;
    }
	
	static long mandragora(int[] H, int index, int S, Map<String, Long> dp) {
		if(index == H.length)
			return 0;
		String key = index + ","+ S;
		if(dp.containsKey(key))
			return dp.get(key);
		long P = 0;
		P = Math.max(P, mandragora(H, index+1, S+1, dp));
		P = Math.max(P, mandragora(H, index+1, S, dp)+ H[index] * S);		
		dp.put(key, P);
		return P;
	}
	
	
	static String gridSearch(String[] G, String[] P) {
		int m = P.length, n = P[0].length();
		int M = G.length, N = G[0].length();
		for(int i=0; i<=M-m; i++) {
			for(int j=0; j<=N-n; j++) {
				boolean end = true;
				outer:
				for(int a=i; a<i+m; a++) {
					for(int b=j; b<j+n; b++) {
						if(G[a].charAt(b) != P[a-i].charAt(b-j)) {
							end = false;
							break outer;
						}
					}
				}
				if(end)
					return "YES";
			}
		}
		
		return "NO";
    }
	
	static int beautifulPath(int[][] edges, int A, int B, int N) {
		Map<Integer, List<int[]>> graph = new HashMap<>();
		for(int[] edge : edges) {
			int p = edge[0], v = edge[1], cost = edge[2];
			List<int[]> list_p = graph.get(p);
			if(list_p == null) {
				list_p = new ArrayList<>();
				graph.put(p, list_p);
			}
			List<int[]> list_v = graph.get(v);
			if(list_v == null) {
				list_v = new ArrayList<>();
				graph.put(v, list_v);
			}
			list_p.add(new int[] {v, cost});
			list_v.add(new int[] {p, cost});
		}
		boolean[][] visited = new boolean[N+1][1024];
		visited[A][0] = true;
		Queue<int[]> queue = new LinkedList<>();
		queue.add(new int[] {A, 0});
		while(!queue.isEmpty()) {
			int[] array = queue.poll();
			int p = array[0], cost = array[1];
			List<int[]> list = graph.get(p);
			if(list == null)
				continue;
			for(int[] next : list) {
				if(visited[next[0]][cost | next[1]])
					continue;
				visited[next[0]][cost | next[1]] = true;
				queue.add(new int[] {next[0], cost | next[1]});
			}
			
		}
		for(int i=0; i<1024; i++) {
			if(visited[B][i])
				return i;
		}
		return -1;
    }
	static String funGame(int[] a, int[] b) {
		int points_a = 0, points_b = 0;
		int n = a.length;
		int[][] array = new int[n][2];
		for(int i=0; i<n; i++) {
			array[i][0] = a[i] + b[i];
			array[i][1] = i;
		}
		Arrays.sort(array, (A, B) -> A[0] - B[0]);
		boolean turn = true;
		for(int i=n-1; i>=0; i--) {
			if(turn) {
				points_a += a[array[i][1]];
			}
			else {
				points_b += b[array[i][1]];
			}
			turn = !turn;
		}
		
		if(points_a == points_b)
			return "Tie";
		else if(points_a < points_b)
			return "Second";
		return "First";
    }
	
	static String permutationGame(int[] arr) {
		boolean result = permutationGame(arr, new boolean[arr.length], new HashMap<>());
		if(!result)
			return "Bob";
		return "Alice";
    }
	
	static boolean permutationGame(int[] array, boolean[] used, Map<String, Boolean> dp) {
		String key = Arrays.toString(used);
		boolean win = true;
		int pre = -1;
		for(int i=0; i<array.length; i++) {
			if(used[i])
				continue;
			if(array[i] <= pre) {
				win = false;
				break;
			}
			pre = array[i];
		}
		if(win) {
			return false;
		}
		if(dp.containsKey(key))
			return dp.get(key);
		for(int i=0; i<array.length; i++) {
			if(used[i])
				continue;
			used[i] = true;
			if(!permutationGame(array, used, dp)) {
				win = true;
				used[i] = false;
				break;
			}
			used[i] = false;
		}
		dp.put(key, win);
		return win;
	}
	
	
	
	static int[] swapPermutation(int n, int k) {
        int[] answer = new int[2];
        int[][] dp = new int[n+1][k+1];
        int mod = 1000000007;
        for(int i=1; i<=n; i++) {
        	dp[i][0] = 1;
        	for(int j=1; j<=k; j++) {
        		for(int a=0; a<i && a <=j; a++) {
        			dp[i][j] = (dp[i-1][j-a] + dp[i][j]) % mod;
        		}
        	}
        }
        for(int i=k; i>=0; i-=2) {
        	answer[0] = (answer[0] + dp[n][i]) % mod;
        }
        
        dp = new int[n+1][k+1];
        for(int i=1; i<=n; i++) {
        	dp[i][0] = 1;
        	for(int j=1; j<=k; j++) {
        		dp[i][j] = (int) (((dp[i-1][j] + dp[i][j]) % mod + (i-1L) * dp[i-1][j-1]) % mod);
        	}
        }
        for(int i=k; i>=0; i--)
        	answer[1] = (answer[1] + dp[n][i]) % mod;
        
        return answer;
    }
	
	static int MrXandHisShots(int[][] shots, int[][] players) {
		int sum = 0;
		TreeMap<Integer, Integer> tree = new TreeMap<>();
		Map<String, Integer> map = new HashMap<>();
		outer:
		for(int[] shot : shots) {
			Map.Entry<Integer, Integer> floorEntry = tree.floorEntry(shot[0]);
			if(floorEntry != null) {
				if(floorEntry.getKey() == shot[0]) {
					if(floorEntry.getValue() < shot[0]) {
						shot[0] = floorEntry.getValue();
					}
					else if(floorEntry.getValue() > shot[0]) {
						tree.put(shot[0], shot[1]);
						tree.put(shot[1], floorEntry.getValue());
						continue;
					}
					else {
						String key = shot[0] + "," + shot[1];
						map.put(key, map.getOrDefault(key, 0)+1);
						continue;
					}
				}
				else {
					if(floorEntry.getValue() > shot[0] && floorEntry.getValue() < shot[1]) {
						tree.put(floorEntry.getKey(), shot[0]);
						tree.put(shot[0], floorEntry.getValue());
						String key = shot[0] + "," + floorEntry.getValue();
						map.put(key, map.getOrDefault(key, 0)+1);
						shot[0] = floorEntry.getValue();
					}
					else if(floorEntry.getValue() == shot[1]) {
						tree.put(floorEntry.getKey(), shot[0]);
						tree.put(shot[0], shot[1]);
						String key = shot[0] + "," + floorEntry.getValue();
						map.put(key, map.getOrDefault(key, 0)+1);
						continue;
					}
					else if(floorEntry.getValue() > shot[1]) {
						tree.put(floorEntry.getKey(), shot[0]);
						tree.put(shot[0], shot[1]);
						String key = shot[0] + "," + shot[1];
						map.put(key, map.getOrDefault(key, 0)+1);
						tree.put(shot[1], floorEntry.getValue());
						continue;
					}
				}
			}
			
			
			Map.Entry<Integer, Integer> ceilEntry = tree.ceilingEntry(shot[0]);
			while(ceilEntry != null && ceilEntry.getKey() < shot[1]) {
				if(ceilEntry.getKey() == shot[0]) {
					if(ceilEntry.getValue() < shot[1]) {
						String key = ceilEntry.getKey()+","+ceilEntry.getValue();
						map.put(key, map.getOrDefault(key, 0)+1);
						shot[0] = ceilEntry.getValue();
					}
					else if(ceilEntry.getValue() > shot[1]) {
						tree.put(ceilEntry.getKey(), shot[1]);
						tree.put(shot[1], ceilEntry.getValue());
						String key = shot[0] + "," + shot[1];
						map.put(key, map.getOrDefault(key, 0)+1);
						continue outer;
					}
					else {
						String key = shot[0] + "," + shot[1];
						map.put(key, map.getOrDefault(key, 0)+1);
						continue outer;
					}
				}
				
				else if(ceilEntry.getKey() < shot[1]) {
					tree.put(shot[0], ceilEntry.getKey());
					shot[0] = ceilEntry.getKey();
					
					if(ceilEntry.getValue() < shot[1]) {
						String key = ceilEntry.getKey() +"," + ceilEntry.getValue();
						map.put(key, map.getOrDefault(key, 0)+1);
						shot[0] = ceilEntry.getValue();
					}
					else if(ceilEntry.getValue() > shot[1]) {
						tree.put(shot[0], shot[1]);
						String key = shot[0] +"," + shot[1];
						map.put(key, map.getOrDefault(key, 0)+1);
						tree.put(shot[1], ceilEntry.getValue());
						continue outer;
					}
					else {
						String key = shot[0] + "," + shot[1];
						map.put(key, map.getOrDefault(key, 0)+1);
						continue outer;
					}
					
				}
				System.out.println(Arrays.toString(shot));
				ceilEntry = tree.ceilingEntry(shot[0]);
			}
			
			tree.put(shot[0], shot[1]);
		}
		
		for(int[] player : players) {
			Map.Entry<Integer, Integer> low = tree.lowerEntry(player[0]);
			if(low != null) {
				if(low.getValue() >= player[0]) {
					String key = low.getKey() + "," + low.getValue();
					sum += 1 + map.getOrDefault(key, 0);
					low = tree.lowerEntry(low.getKey());
				}
			}
			Map.Entry<Integer, Integer> high = tree.ceilingEntry(player[0]);
			while(high != null && high.getKey() <= player[1]) {
				String key = high.getKey() + "," + high.getValue();
				sum += 1 + map.getOrDefault(key, 0);
				high = tree.higherEntry(high.getKey());
			}
		}
		return sum;
    }
	
	
	static boolean MrXandHisShots_isOverlap(int[] a, int[] b) {
		if(a[0] > b[1] || a[1] < b[0])
			return false;
		return true;
	}
	
	static int MrXandHisShots2(int[][] shots, int[][] players) {
		int sum = 0;
		Arrays.sort(shots, (a,b) -> a[0] - b[0]);
		Arrays.sort(players, (a,b) -> a[0] - b[0]);
		int shot_index = 0, player_index = 0;
		while(shot_index < shots.length && player_index < players.length) {
			int[] shot = shots[shot_index];
			int[] player = players[player_index];
			if(shot[1] < player[0]) {
				shot_index++;
			}
			else if(player[1] < shot[0]) {
				player_index++;
			}
			else {
				int i = shot_index;
				while(i < shots.length && player[1] >= shots[i][0]) {
					if(MrXandHisShots_isOverlap(shots[i], player)) {
						sum++;
					}
					i++;
				}
				player_index++;
			}
		}
		return sum;
	}
	
	static class MrXandHisShots {
		
		static void BIT_update(int[] BIT, int i, int val) {
			i++;
			while(i < BIT.length) {
				BIT[i] += val;
				i += i & -i;
			}
		}
		
		static int BIT_sum(int[] BIT, int i) {
			int sum = 0;
			i++;
			while(i > 0) {
				sum += BIT[i];
				i -= i & -i;
			}
			return sum;
		}
		
		
		static int MrXandHisShots2(int[][] shots, int[][] players) {
			int sum = 0;
			int[] BIT_left = new int[100001];
			int[] BIT_right = new int[100001];
			for(int[] shot : shots) {
				BIT_update(BIT_left, shot[0], 1);
				BIT_update(BIT_right, shot[1], 1);
			}
			for(int[] player : players) {
				sum += BIT_sum(BIT_left, player[1]) - BIT_sum(BIT_right, player[0] - 1 );
			}
			return sum;
		}
		
		
		
		
		
		
		
		
	}
	
	
	static int[] componentsInGraph(int[][] gb, int N) {
        int[] answer = {Integer.MAX_VALUE, Integer.MIN_VALUE};
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int[] visited = new int[N*2+1];
        for(int[] edge : gb) {
        	List<Integer> list = graph.get(edge[0]);
        	if(list == null) {
        		list = new ArrayList<>();
        		graph.put(edge[0], list);
        	}
        	list.add(edge[1]);
        	list = graph.get(edge[1]);
        	if(list == null) {
        		list = new ArrayList<>();
        		graph.put(edge[1], list);
        	}
        	list.add(edge[0]);
        }
        for(int i=1; i<=N*2; i++) {
        	if(visited[i] != 0 || !graph.containsKey(i))
        		continue;
        	int v = vertices(graph, i, visited, i);
        	answer[0] = Math.min(v, answer[0]);
        	answer[1] = Math.max(v, answer[1]);
        }
		return answer;
    }
	
	static int vertices(Map<Integer, List<Integer>> graph, int cur, int[] visited, int index) {
		visited[cur] = index;
		int counter = 1;
		for(int next : graph.get(cur)) {
			if(visited[next] != 0)
				continue;
			counter += vertices(graph, next, visited, index);
		}
		return counter;
	}
	
	static class AVL {
		static class Node{
			int val;
			int ht;
			Node left, right;
		}
		
		static int height(Node node) {
			if(node == null)
				return -1;
			return node.ht;
		}
		
		static int setHeight(Node node) {
			if(node == null)
				return -1;
			return 1 + Math.max(height(node.left), height(node.right));
		}
		
		static Node leftRotation(Node node) {
			Node newNode = node.right;
			node.right = newNode.left;
			newNode.left = node;
			node.ht = setHeight(node);
			newNode.ht = setHeight(newNode);
			return newNode;
		}
		
		static Node rightRotation(Node node) {
			Node newNode = node.left;
			node.left = newNode.right;
			newNode.right = node;
			node.ht = setHeight(node);
			newNode.ht = setHeight(newNode);
			return newNode;
		}
		
		static Node insert(Node root, int val) {
			if(root == null) {
				root = new Node();
				root.val = val;
				root.ht = setHeight(root);
				return root;
			}
			if(val <= root.val) {
				root.left = insert(root.left, val);
			}
			else {
				root.right = insert(root.right, val);
			}
			int blance = height(root.left) - height(root.right);
			
			if(blance > 1) {
				if(height(root.left.left) >= height(root.left.right)) {
					root = rightRotation(root);
				}
				else {
					root.left = leftRotation(root.left);
					root = rightRotation(root);
				}
			}
			else if(blance < -1) {
				if(height(root.right.right) >= height(root.right.left)) {
					root = leftRotation(root);
				}
				else {
					root.right = rightRotation(root.right);
					root = leftRotation(root);
				}
			}
			else {
				root.ht = setHeight(root);
			}
			return root;
		}
		
		
		
	}
	
	static String[] timeInWords_h = {"","one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve"};
	static String[] timeInWords_m = {"","one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve",
										"thirteen", "fourteen", "quarter", "sixteen", "seventeen", "eighteen", "nineteen", "twenty", 
										"twenty one", "twenty two", "twenty three", "twenty four", "twenty five", "twenty six", "twenty seven", 
										"twenty eight", "twenty nine", "half"};
	static String timeInWords(int h, int m) {
		if(m == 0) {
			return timeInWords_h[h] +" o' clock";
		}
		if(m >= 1 && m < 30) {
			if(m == 15) {
				return timeInWords_m[m] + " past " + timeInWords_h[h];
			}
			if(m == 1) {
				return timeInWords_m[m] + " minute past " + timeInWords_h[h];
			}
			return timeInWords_m[m] + " minutes past " + timeInWords_h[h];
		}
		if(m == 30) {
			return "half past "+timeInWords_h[h];
		}
		String hour = h == 12 ? "one" : timeInWords_h[h+1];
		m = 60 - m;
		if(m == 15) {
			return timeInWords_m[m] + " to " + hour;
		}
		if(m == 59) {
			return "one minute to " + hour;
		}
		return timeInWords_m[m] + " minutes to " + hour;
    }
	
	static int surfaceArea(int[][] A) {
		int area = 0;
		for(int i=0; i<A[0].length; i++) {
			area += 2 + A[0][i] * 4;
			if(i > 0)
				area -= 2 * (Math.min(A[0][i], A[0][i-1]));
		}
		for(int i=1; i<A.length; i++) {
			for(int j=0; j<A[0].length; j++) {
				area += 2 + A[i][j] * 4;
				area -= 2 * Math.min(A[i-1][j], A[i][j]);
				if(j > 0)
					area -= 2 * Math.min(A[i][j-1], A[i][j]);
			}
		}
		return area;
    }
	
	static String[] bomberMan(int n, String[] grid) {
		int m = grid.length;
		n = grid[0].length();
		int[][] help = new int[m][n];
		int[][] dir = {{-1,0}, {1,0}, {0,-1}, {0,1}};
		char[][] matrix = new char[m][n];
		for(int i=0; i<m; i++)
			matrix[i] = grid[i].toCharArray();
		int time = 0;
		time++;
		for(int t=0; t<10; t++) {
			time++;
			for(int i=0; i<m; i++) {
				for(int j=0; j<n; j++) {
					if(matrix[i][j] != '.')
						continue;
					matrix[i][j] = 'O';
					help[i][j] = time;
				}
			}
			System.out.println(time+"秒后*************************");
			for(char[] temp : matrix)
				System.out.println(temp);
			time++;
			for(int i=0; i<m; i++) {
				for(int j=0; j<n; j++) {
					if(matrix[i][j] == '.' || time < help[i][j] + 3)
						continue;
					matrix[i][j] = '.';
					help[i][j] = 0;
					for(int[] d : dir) {
						int x = i + d[0];
						int y = j + d[1];
						if(x < 0 || x >= m || y<0 || y>=n)
							continue;
						if(matrix[x][y] == 'O' && time >= help[x][y]+3)
							continue;
						matrix[x][y] = '.';
						help[i][j] = 0;
					}
				}
			}
			System.out.println(time+"秒后************");
			for(char[] temp : matrix)
				System.out.println(temp);
		}
		
		
		return null;
    }
	
	
	static String[] bomberMan2(int s, String[] grid) {
		int m = grid.length;
		int n = grid[0].length();
		int[][] help = new int[m][n];
		int[][] dir = {{-1,0}, {1,0}, {0,-1}, {0,1}};
		char[][] matrix = new char[m][n];
		for(int i=0; i<m; i++)
			matrix[i] = grid[i].toCharArray();
		int time = 0;
		time++;
		if(s == 1)
			return grid;
		time++;
		for(int i=0; i<m; i++) {
			for(int j=0; j<n; j++) {
				if(matrix[i][j] != '.')
					continue;
				matrix[i][j] = 'O';
				help[i][j] = time;
			}
		}
		String[] two = new String[m];
		for(int i=0; i<m; i++)
			two[i] = new String(matrix[i]);
		
		time++;
		for(int i=0; i<m; i++) {
			for(int j=0; j<n; j++) {
				if(matrix[i][j] == '.' || time < help[i][j] + 3)
					continue;
				matrix[i][j] = '.';
				help[i][j] = 0;
				for(int[] d : dir) {
					int x = i + d[0];
					int y = j + d[1];
					if(x < 0 || x >= m || y<0 || y>=n)
						continue;
					if(matrix[x][y] == 'O' && time >= help[x][y]+3)
						continue;
					matrix[x][y] = '.';
					help[x][y] = 0;
				}
			}
		}
		
		String[] three = new String[m];
		for(int i=0; i<m; i++)
			three[i] = new String(matrix[i]);
		
		time++;
		for(int i=0; i<m; i++) {
			for(int j=0; j<n; j++) {
				if(matrix[i][j] != '.')
					continue;
				matrix[i][j] = 'O';
				help[i][j] = time;
			}
		}
		
		String[] four = new String[m];
		for(int i=0; i<m; i++)
			four[i] = new String(matrix[i]);
		
		time++;
		for(int i=0; i<m; i++) {
			for(int j=0; j<n; j++) {
				if(matrix[i][j] == '.' || time < help[i][j] + 3)
					continue;
				matrix[i][j] = '.';
				help[i][j] = 0;
				for(int[] d : dir) {
					int x = i + d[0];
					int y = j + d[1];
					if(x < 0 || x >= m || y<0 || y>=n)
						continue;
					if(matrix[x][y] == 'O' && time >= help[x][y]+3)
						continue;
					matrix[x][y] = '.';
					help[x][y] = 0;
				}
			}
		}
		
		String[] one = new String[m];
		for(int i=0; i<m; i++)
			one[i] = new String(matrix[i]);
		
		
		int mod = s % 4;
		if(mod == 1)
			return one;
		else if(mod == 2 || mod == 0)
			return two;
		else if(mod == 3)
			return three;
		
		return null;
	}
	
	static long sherlockAndAnagrams2(String s){
		long anagrams = 0;
		int n = s.length();
		Map<String, Integer> map = new HashMap<>();
		for(int i=0; i<n; i++) {
			for(int j=i+1; j<=n; j++) {
				char[] temp = s.substring(i, j).toCharArray();
				Arrays.sort(temp);
				String sub = new String(temp);
				map.put(sub, map.getOrDefault(sub, 0)+1);
			}
		}
		for(Map.Entry<String, Integer> entry : map.entrySet()) {
			int count = entry.getValue();
			anagrams = anagrams + count * (count-1) / 2;
		}
		return anagrams;
	}
	
	
	static int steadyGene(String gene) {
		int min = Integer.MAX_VALUE;
		Map<Character, Integer> map = new HashMap<>();
		map.put('C', 0);
		map.put('G', 0);
		map.put('A', 0);
		map.put('T', 0);
		for(char c : gene.toCharArray())
			map.put(c, map.get(c)+1);
		int left = 0, right = 0, n = gene.length();
		while(right < n) {
			char r = gene.charAt(right++);
			map.put(r, map.get(r)-1);
			while(steadyGeneHelp(map, n)) {
				min = Math.min(min, right - left);
				char l = gene.charAt(left++);
				map.put(l, map.get(l)+1);
			}
		}
		return min;
    }
	
	static boolean steadyGeneHelp(Map<Character, Integer> map, int n) {
		for(Integer i : map.values()) {
			if(i > n / 4)
				return false;
		}
		return true;
	}
	
	static int[][] knightlOnAChessboard(int n) {
		int[][] answer = new int [n-1][n-1];
		for(int i=1; i<n; i++) {
			for(int j=1; j<n; j++) {
				if(answer[i-1][j-1] != 0)
					continue;
				int temp = knightlOnAChessboardHelp(n, i, j);
				answer[i-1][j-1] = temp;
				answer[j-1][i-1] = temp;
			}
		}
		return answer;
    }
	
	static int knightlOnAChessboardHelp(int n, int a, int b) {
		int[][] chess = new int[n][n];
		int[][] dirs = {{a,b}, {a,-b}, {-a,b}, {-a,-b}, {b,a}, {b,-a}, {-b,a}, {-b,-a}};
		Queue<int[]> queue = new LinkedList<>();
		queue.add(new int[] {0, 0});
		while(!queue.isEmpty()) {
			int size = queue.size();
			for(int t=0; t<size; t++) {
				int[] pos = queue.poll();
				for(int[] d:dirs) {
					int x = pos[0] + d[0];
					int y = pos[1] + d[1];
					if(x < 0 || x >= n || y < 0 || y >= n || (x == 0 && y == 0) || chess[x][y] != 0)
						continue;
					chess[x][y] = chess[pos[0]][pos[1]] + 1;
					if(x == n -1 && y == n-1)
						return chess[x][y];
					queue.add(new int[] {x, y});
				}
			}
		}
		return -1;
	}
	
	
	static int minimumLoss(long[] price) {
		long min = Integer.MAX_VALUE;
		TreeSet<Long> tree = new TreeSet<>();
		tree.add(price[price.length-1]);
		for(int i=price.length-2; i>=0; i--) {
			Long low = tree.floor(price[i]);
			if(low != null) {
				min = Math.min(min, price[i] - low);
			}
			tree.add(price[i]);
		}
		return (int) min;
    }
	
	static int shortPalindrome(String s) {
		int sp = 0;
		int mod = 1000000007;
		int n = s.length();
		int[][] dp = new int[n][n];
		for(int i=0; i<n; i++) {
			char c1 = s.charAt(i);
			for(int j=i-1; j>=0; j--) {
				char c2 = s.charAt(j);
				dp[i][j] = (dp[i][j] + dp[i-1][j]) % mod;
				dp[i][j] = (dp[i][j] + dp[i][j+1]) % mod;
				if(i - j > 2)
					dp[i][j] = (dp[i][j] - dp[i-1][j+1]) % mod;
				if(c1 == c2) {
					dp[i][j] = (dp[i][j] + 1) % mod;
					if(i - j > 2)
						sp = (sp + dp[i-1][j+1]) % mod;
				}
			}
		}
		return sp;
    }
	
	static int shortPalindrome2(String s) {
		int count = 0;
		int mod = 1000000007;
		int[][][][] array4 = new int[26][26][26][26];
		int[][][] array3 = new int[26][26][26];
		int[][] array2 = new int[26][26];
		int[] array1 = new int[26];
		for(int i=0; i<s.length(); i++) {
			int index = s.charAt(i) - 'a';
			for(int j=0; j<26; j++) {
				array4[index][j][j][index] = (array4[index][j][j][index] + array3[index][j][j]) % mod;
				array3[j][index][index] =  (array3[j][index][index] + array2[j][index]) % mod;
				array2[j][index] = (array2[j][index] + array1[j]) % mod;
			}
			array1[index] = (array1[index] + 1) % mod;
		}
		for(int i=0; i<26; i++) {
			for(int j=0; j<26; j++) {
				count = (count + array4[i][j][j][i]) % mod;
			}
		}
		return count;
	}
	
	
	static long roadsAndLibraries(int n, int c_lib, int c_road, int[][] cities) {
		long cost = 0;
		Map<Integer, List<Integer>> graph = new HashMap<>();
		for(int[] city : cities) {
			List<Integer> list = graph.get(city[0]);
			if(list == null) {
				list = new ArrayList<>();
				graph.put(city[0], list);
			}
			list.add(city[1]);
			list = graph.get(city[1]);
			if(list == null) {
				list = new ArrayList<>();
				graph.put(city[1], list);
			}
			list.add(city[0]);
		}
		boolean[] visited = new boolean[n+1];
		int library = 0;
		int road = 0;
		int single = 0;
		for(int i=1; i<=n; i++) {
			if(visited[i])
				continue;
			visited[i] = true;
			if(!graph.containsKey(i)) {
				single++;
				continue;
			}
			road += roadsAndLibrariesDFS(graph, i, visited) - 1;
			library++;
		}
		if(c_lib < c_road) {
			cost = cost + (long)n * c_lib; 
		}
		else {
			cost = cost + (long)road * c_road + ((long)library + single) * c_lib; 
		}
		return cost;
    }

	static int roadsAndLibrariesDFS(Map<Integer, List<Integer>> graph, int city, boolean[] visited) {
		int count = 1;
		List<Integer> list = graph.get(city);
		for(int next : list) {
			if(visited[next])
				continue;
			visited[next] = true;
			count += roadsAndLibrariesDFS(graph, next, visited);
		}
		return count;
	}
	
	
	static int activityNotifications(int[] expenditure, int d) {
		int notification = 0;
		int[] bucket = new int[201];
		for(int i=0; i<d; i++) {
			bucket[expenditure[i]]++;
		}
		for(int i=d; i<expenditure.length; i++) {
			int median_sum = activityNotifications_median(bucket, d);
			if(median_sum <= expenditure[i])
				notification++;
			bucket[expenditure[i-d]]--;
			bucket[expenditure[i]]++;
		}
		
		return notification;
    }
	
	static int activityNotifications_median(int[] bucket, int d) {
		boolean is_odd = (d & 1) == 1;
		int count = 0;
		int mid_index = (d+1) / 2;
		for(int i=0; i<=200; i++) {
			if(bucket[i] == 0)
				continue;
			count += bucket[i];
			if(is_odd) {
				if(count >= mid_index)
					return i + i;
			}
			else {
				if(count >= mid_index && count < mid_index + 1) {
					for(int j=i+1; j<=200; j++) {
						if(bucket[j] != 0) {
							return i + j;
						}
					}
				}
				if(count >= mid_index+1)
					return i + i;
			}
		}
		return 0;
	}
	
	static int quickestWayUp(int[][] ladders, int[][] snakes) {
		int[] board = new int[101];
		int rolls = 0;
		boolean[] visited = new boolean[101];
		for(int[] ladder : ladders) {
			int from = ladder[0], to = ladder[1];
			board[from] = to;
		}
		for(int[] snake : snakes) {
			int from = snake[0], to = snake[1];
			board[from] = -to;
		}
		Queue<Integer> queue = new LinkedList<Integer>();
		visited[1] = true;
		queue.add(1);
		while(!queue.isEmpty()) {
			int size = queue.size();
			rolls++;
			for(int t=0; t<size; t++) {
				int point = queue.poll();
				for(int i=1; i<=6; i++) {
					int next = point + i;
					if(next == 100)
						return rolls;
					if(visited[next])
						continue;
					visited[next] = true;
					if(board[next] > 0)
						next = board[next];
					else if(board[next] < 0)
						next = -board[next];
					visited[next] = true;
					if(next == 100)
						return rolls;
					queue.add(next);
				}
			}
		}
		return -1;
    }
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
        	
        }
        scanner.close();
    }
	

}

