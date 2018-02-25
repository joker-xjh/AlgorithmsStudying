package hackerrank;

import java.math.BigInteger;

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
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		substringDiff("helloworld", "yellomarin", 3);
		
	}

}
