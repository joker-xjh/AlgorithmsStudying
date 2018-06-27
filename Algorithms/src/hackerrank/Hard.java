package hackerrank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
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
	
	
	static class Node {
		boolean end;
		Node[] next = new Node[10];
	}
	
	static void NoPrefixSet(String[] words) {
		Node root = new Node();
		for(String word : words) {
			Node node = root;
			for(char c : word.toCharArray()) {
				int i = c - 'a';
				if(node.next[i] == null) {
					node.next[i] = new Node();
				}
				node = node.next[i];
				if(node.end) {
					System.out.println("BAD SET");
					System.out.println(word);
					return;
				}
			}
			node.end = true;
			for(Node temp : node.next) {
				if(temp != null) {
					System.out.println("BAD SET");
					System.out.println(word);
					return;
				}
			}
		}
		
		System.out.println("GOOD SET");
	}

	
	static int[][][] cube;
	static Map<String, int[]> cube_map = new HashMap<>();
	
	static void CubeSummationUPDATE(int x, int y, int z, int W) {
		cube[x][y][z] = W;
		String key = x+","+y+","+z;
		cube_map.put(key, new int[] {x,y,z});
	}
	
	static void CubeSummationQUERY(int x1, int y1, int z1, int x2, int y2, int z2) {
		long sum = 0;
		for(Map.Entry<String, int[]> entry : cube_map.entrySet()) {
			int[] pos = entry.getValue();
			if(x1 <= pos[0] && pos[0] <= x2 &&
				y1 <= pos[1] && pos[1] <= y2 &&
				 z1 <= pos[2] && pos[2] <= z2) {
				sum = sum + cube[pos[0]][pos[1]][pos[2]];
			}
		}
		System.out.println(sum);
	}
	
	
	static int[] MergingCommunities_array;
	static int[] MergingCommunities_counter;
	
	static void MergingCommunities(int I, int J) {
		int pre_I = MergingCommunitiesUF(I, MergingCommunities_array);
		int pre_J = MergingCommunitiesUF(J, MergingCommunities_array);
		if(pre_I != pre_J) {
			MergingCommunities_array[pre_I] = MergingCommunities_array[pre_J];
			MergingCommunities_counter[pre_J] += MergingCommunities_counter[pre_I];
			MergingCommunities_counter[pre_I] = 0;
		}
	}
	
	static int MergingCommunitiesUF(int person, int[] array) {
		while(person != array[person]) {
			array[person] = array[array[person]];
			person = array[person];
		}
		return person;
	}
	
	
	static long largestValue(int[] A) {
        int n = A.length;
        int[] preSum = new int[n];
        int sum = 0;
        for(int i=0; i<n; i++) {
        	sum += A[i];
        	preSum[i] = sum;
        }
        long answer = largestValueMemo(A, n-1,  preSum, new Long[n]);
		return answer;
    }
	
	static long largestValueMemo(int[] A, int index, int[] preSum, Long[] dp) {
		if(index <= 0)
			return Long.MIN_VALUE;
		if(dp[index] != null)
			return dp[index];
		long max = Long.MIN_VALUE;
		for(int i=index; i>=0; i--) {
			long pre = 0;
			for(int j=index-1; j>0; j--) {
				int sum = preSum[i] - preSum[j];
				long temp = (long)A[j] * sum + pre;
				pre = temp;
				max = Math.max(max, temp);
				max = Math.max(max, largestValueMemo(A, j-1, preSum, dp));
			}
		}
		dp[index] = max;
		return max;
	}
	
	
	static int[] solve(int[] arr, int[] queries) {
		int[] answer = new int[queries.length];
		PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> b[0]-a[0]);
		for(int t=0; t<queries.length; t++) {
			int d = queries[t];
			int min = Integer.MAX_VALUE;
			pq.clear();
			for(int i=0; i<d; i++)
				pq.add(new int[] {arr[i], i});
			for(int i=d; i<arr.length; i++) {
				int[] pair = pq.peek();
				while(pair[1] < i - d) {
					pq.poll();
					pair = pq.peek();
				}
				min = Math.min(min, pq.peek()[0]);
				pq.add(new int[] {arr[i], i});
			}
			while(!(pq.peek()[1] >= arr.length-d)) {
				pq.poll();
			}
			min = Math.min(min, pq.peek()[0]);
			answer[t] = min;
		}
		return answer;
    }
	
	
	static long minimumAverage(int[][] customers) {
        long wait = 0;
        Arrays.sort(customers, (a,b) -> a[0]-b[0]);
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[1]-b[1]);
        long time = 0;
        int index = 0;
        while(index < customers.length || !pq.isEmpty()) {
        	if(pq.isEmpty()) {
        		int[] pair = customers[index++];
        		time = (long)pair[0] + pair[1];
        		wait = wait + pair[1];
        		for(int i=index; i<customers.length; i++) {
        			if(customers[i][0] > time)
        				break;
        			pq.add(new int[] {customers[i][0], customers[i][1], i});
        			index++;
        		}
        	}
        	else {
        		while(!pq.isEmpty()) {
        			int[] pair = pq.poll();
        			wait += time - pair[0] + pair[1];
        			time = time + pair[1];
        			for(int i=index; i<customers.length; i++) {
            			if(customers[i][0] > time)
            				break;
            			pq.add(new int[] {customers[i][0], customers[i][1], i});
            			index++;
            		}
        		}
        	}
        }
        return wait / customers.length;
    }
	
	static double[] runningMedian(int[] a) {
        int n = a.length;
        double[] answer = new double[n];
        PriorityQueue<Integer> left = new PriorityQueue<>((x,y)->y-x);
        PriorityQueue<Integer> right = new PriorityQueue<>();
        for(int i=0; i<n; i++) {
        	int num = a[i];
        	if(left.isEmpty()) {
        		left.add(num);
        	}
        	else if(left.size() == right.size()) {
        		if(num <= right.peek()) {
        			left.add(num);
        		}
        		else {
        			left.add(right.poll());
        			right.add(num);
        		}
        	}
        	else if(left.size() > right.size()) {
        		if(num < left.peek()) {
        			right.add(left.poll());
        			left.add(num);
        		}
        		else {
        			right.add(num);
        		}
        	}
        	
        	if(left.size() > right.size()) {
        		answer[i] = left.peek();
        	}
        	else {
        		answer[i] = (left.peek() + right.peek()) / 2d;
        	}
        }
        
        return answer;
    }
	
	
	static long arrayManipulation(int n, int[][] queries) {
		long max = 0;
		long[] diff = new long[n+2];
		for(int[] query : queries) {
			int left = query[0], right = query[1], sum = query[2];
			diff[left] = diff[left] + sum;
			diff[right+1] = diff[right+1] - sum;
		}
		long temp = 0;
		for(int i=1; i<=n; i++) {
			temp += diff[i];
			max = Math.max(max, temp);
		}
		return max;
    }
	
	
	static int andXorOr(int[] a) {
		int max = Integer.MIN_VALUE;
		Stack<Integer> stack = new Stack<>();
		stack.push(0);
		for(int i=1; i<a.length; i++) {
			if(a[stack.peek()] < a[i]) {
				max = Math.max(max, a[stack.peek()] ^ a[i]);
				stack.push(i);
			}
			else if(a[stack.peek()] > a[i]){
				while(!stack.isEmpty() && a[stack.peek()] > a[i]) {
					max = Math.max(max, a[i] ^ a[stack.pop()]);
				}
				if(!stack.isEmpty())
					max = Math.max(max, a[stack.peek()] ^ a[i]);
				stack.push(i);
			}
			
		}
		
		
		return max;
    }
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			
		}
		
		scanner.close();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
