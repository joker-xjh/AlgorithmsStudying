package hackerrank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Hard {
	
	static void arithmeticExpressions(int[] arr) {
        List<String> expression = new ArrayList<>();
        expression.add(String.valueOf(arr[0]));
        Map<String, Boolean> memorization = new HashMap<>();
        arithmeticExpressionsHelp(arr, arr[0], expression, 1, memorization);
    }
	
	static boolean arithmeticExpressionsHelp(int[] nums, int target, List<String> expression, int index, Map<String, Boolean> memorization) {
		String key = "num:"+target+",i:"+index;
		if(index == nums.length) {
			if(target % 101 == 0) {
				for(int i=0; i<expression.size(); i++) {
					System.out.print(expression.get(i));
				}
				System.out.println();
				return true;
			}
			
			return false;
		}
		if(memorization.containsKey(key))
			return memorization.get(key);
		int num = nums[index];
		for(int j=0; j<3; j++) {
			String operator = j == 0 ? "+" : (j == 1 ? "-" : "*");
			expression.add(operator);
			expression.add(String.valueOf(num));
			int nextTarget = (j == 0 ? target + num : (j == 1 ? target - num : target * num)) % 101;
			if(arithmeticExpressionsHelp(nums, nextTarget, expression, index+1, memorization)) {
				memorization.put(key, true);
				return true;
			}
			expression.remove(expression.size()-1);
			expression.remove(expression.size()-1);
		}
		memorization.put(key, false);
		return false;
	}
	
	
	static void similarPair(int n, int[][] edges, int k) {
		boolean[] visited = new boolean[n+1];
		Map<Integer, Set<Integer>> tree = new HashMap<>();
		for(int[] edge : edges) {
			int v = edge[0], p = edge[1];
			if(!tree.containsKey(v))
				tree.put(v, new HashSet<>());
			tree.get(v).add(p);
		}
		TreeSet<Integer> values = new TreeSet<>();
		visited[edges[0][0]] = true;
		int count = similarPairHelp(tree, edges[0][0], visited, values, k);
		System.out.println(count);
	}
	
	static int similarPairHelp(Map<Integer, Set<Integer>> tree, int v, boolean[] visited, TreeSet<Integer> values, int k) {
		int count = 0;
		count += similarPairCount(values, v, k, visited.length-1);
		Set<Integer> adj = tree.get(v);
		if(adj != null && adj.size() > 0) {
			values.add(v);
			for(int p : tree.get(v)) {
				if(visited[p])
					continue;
				visited[p] = true;
				count += similarPairHelp(tree, p, visited, values, k);
			}
			values.remove(v);
		}
		return count;
	}
	
	
	
	static int similarPairCount(TreeSet<Integer> values, int v, int k, int n) {
		if(k == n)
			return values.size();
		int count = 0;
		int temp = v;
		while(true) {
			Integer pre = values.lower(temp);
			if(pre == null || Math.abs(pre - v) > k)
				break;
			//System.out.println("["+pre+", "+v+"]");
			count++;
			temp = pre;
		}
		temp = v;
		while(true) {
			Integer next = values.higher(temp);
			if(next == null || Math.abs(next - v) > k)
				break;
			//System.out.println("["+next+", "+v+"]");
			count++;
			temp = next;
		}
		return count;
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = scanner.nextInt();
		int k = scanner.nextInt();
		int[][] edges = new int[n-1][2];
		for(int i=0; i<n-1; i++) {
			int[] array = new int[2];
			array[0] = scanner.nextInt();
			array[1] = scanner.nextInt();
			edges[i] = array;
		}
		similarPair(n, edges, k);
		scanner.close();
	}
	
	
	static void matrixRotation(int[][] matrix, int R) {
        int M = matrix.length, N = matrix[0].length;
        List<Integer> list = new ArrayList<>();
        for(int i=0; M-i*2>0 && N-i*2>0; i++) {
        	matrixRotationCollect(matrix, i, i, M-i*2, N-i*2, list);
        	matrixRotationHelp(list, R);
        	matrixRotationSet(matrix, i, i, M-i*2, N-i*2, list);
        	list.clear();
        }
    }
	
	static void matrixRotationPrint(int[][] matrix) {
		int M = matrix.length, N = matrix[0].length;
		for(int i=0; i<M; i++) {
			for(int j=0; j<N; j++) {
				if(j == N-1)
					System.out.println(matrix[i][j]);
				else
					System.out.print(matrix[i][j]+" ");
			}
		}
	}
	
	static void matrixRotationCollect(int[][] matrix, int i, int j, int M, int N, List<Integer> list) {
		for(int a=j; a<j+N; a++) {
			list.add(matrix[i][a]);
		}
		for(int a=i+1; a<i+M; a++) {
			list.add(matrix[a][N-1+j]);
		}
		for(int a=N-2+j; a>=j; a--) {
			list.add(matrix[M-1+i][a]);
		}
		for(int a=M-2+i; a>i; a--) {
			list.add(matrix[a][j]);
		}
	}
	
	static void matrixRotationSet(int[][] matrix, int i, int j, int M, int N, List<Integer> list) {
		int index = 0;
		for(int a=j; a<j+N; a++) {
			matrix[i][a] = list.get(index++);
		}
		for(int a=i+1; a<i+M; a++) {
			matrix[a][N-1+j] = list.get(index++);
		}
		for(int a=N-2+j; a>=j; a--) {
			matrix[M-1+i][a] = list.get(index++);
		}
		for(int a=M-2+i; a>i; a--) {
			matrix[a][j] = list.get(index++);
		}
	}
	
	static void matrixRotationHelp(List<Integer> list, int R) {
		int n = list.size();
		R = R % n;
		matrixRotationReverse(list, 0, R-1);
		matrixRotationReverse(list, R, n-1);
		matrixRotationReverse(list, 0, n-1);
	}
	
	static void matrixRotationReverse(List<Integer> list, int left, int right) {
		while(left < right) {
			int temp = list.get(left);
			list.set(left, list.get(right));
			list.set(right, temp);
			left++;
			right--;
		}
	} 
	
	
	
	
	
	
	
	
	
	

}
