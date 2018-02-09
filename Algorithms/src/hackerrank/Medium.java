package hackerrank;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
	}

}
