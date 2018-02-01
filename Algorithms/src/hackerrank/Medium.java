package hackerrank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	
	static int crosswordPuzzleCountSlot(char[][] board, int x, int y) {
		int row = 0;
		for(int i=y+1; i<10; i++) {
			if(board[x][i] == '-')
				row++;
			else
				break;
		}
		int col = 0;
		for(int i=x+1; i<10; i++) {
			if(board[i][y] == '-')
				col++;
			else
				break;
		}
		return Math.max(row, col) + 1;
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
        			int max = crosswordPuzzleCountSlot(board, i, j);
        			for(int x =0; x<hints_array.length; x++) {
        				String word = hints_array[x];
        				if(word.length() != max)
        					continue;
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
	
	
	
	static boolean crosswordPuzzleHelp2(char[][] board,int x, int y, int count, String[] hints, boolean[] hints_used, String hint, int length, Set<String> steps, int[] slots) {
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
				loop:
				for(int a=0; a<10; a++) {
					for(int b=0; b<10; b++) {
						if(board[a][b] == '-' || board[a][b] == hints[i].charAt(0)) {
							int max = crosswordPuzzleCountSlot(board, a, b);
							if(max != hints[i].length())
								break loop;
							Set<String> temp = new HashSet<>();
							temp.add(""+a+b);
							if(crosswordPuzzleHelp2(board, a, b, count - 1, hints, hints_used, hints[i], 0, temp, slots))
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
				if(crosswordPuzzleHelp2(board, a, b, count - 1, hints, hints_used, hint, length + 1, steps, slots))
					return true;
				 steps.remove(str);
			}
		}
		board[x][y] = old;
		if(old == '-')
			slots[0]++;
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		
	}

}
