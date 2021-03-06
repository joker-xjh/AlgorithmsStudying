package hackerrank;

import java.util.ArrayList;


import java.util.Arrays;
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
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;
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
	
	
	static class MedianUpdates_comparator implements Comparator<Long> {

		@Override
		public int compare(Long o1, Long o2) {
			if(o1 < o2)
				return 1;
			else if(o1 > o2)
				return -1;
			return 0;
		}
		
	}
	
	static void MedianUpdates(int[][] query) {
		PriorityQueue<Long> left = new PriorityQueue<>(new MedianUpdates_comparator());
        PriorityQueue<Long> right = new PriorityQueue<>();
        for(int[] q : query) {
        	long num = q[1];
        	if(q[0] == 0) {
        		if(left.isEmpty()) {
        			System.out.println("Wrong!");
            		continue;
        		}
        		double avg = 0;
            	if(left.size() > right.size()) {
            		avg = left.peek();
            	}
            	else {
            		avg = (left.peek() + right.peek()) / 2d;
            	}
            	boolean success = false;
            	if(num <= avg) {
            		success = left.remove(num);
            	}
            	else {
            		success = right.remove(num);
            	}
            	if(!success) {
            		System.out.println("Wrong!");
            		continue;
            	}
            	if(left.size() < right.size()) {
            		left.add(right.poll());
            	}
            	else if(left.size() - 2 == right.size()) {
            		right.add(left.poll());
            	}
            	
        	}
        	else {
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
            	
        	}
        	if(left.isEmpty()) {
    			System.out.println("Wrong!");
        		continue;
    		}
        	long avg = 0;
        	if(left.size() > right.size()) {
        		avg = left.peek();
        		System.out.println(avg);
        	}
        	else {
        		avg = (left.peek() + right.peek()) / 2;
        		if(avg * 2 == (left.peek() + right.peek())) {
            		System.out.println(avg);
        		}
        		else {
        			if(avg == 0) {
        				System.out.println((left.peek() + right.peek()) / 2d);
        			}
        			else {
        				System.out.println(avg+".5");
        			}
        		}
        	}
        	
        	
        }
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
	
	static int poisonousPlants(int[] p) {
		int day = 0;
		Stack<Integer> stack = new Stack<>();
		stack.push(0);
		int[] days = new int[p.length];
		for(int i=1; i<p.length; i++) {
			if(p[i] > p[i-1]) {
				days[i] = 1;
				day = Math.max(day, 1);
				continue;
			}
			if(p[i] > p[stack.peek()]) {
				days[i] = 2;
				day = Math.max(day, days[i]);
				stack.push(i);
				continue;
			}
			else if(p[i] <= p[stack.peek()]) {
				if(p[i] <= p[stack.get(0)]) {
					stack.clear();
					days[i] = 0;
					stack.push(i);
				}
				else {
					while(!stack.isEmpty() && p[i] <= p[stack.peek()]) {
						if(stack.size() == 1) {
							stack.pop();
							stack.push(i);
							days[i] = 0;
							break;
						}
						
						if(p[i] == p[stack.peek()]) {
							days[i] = Math.max(days[i], days[stack.peek()] + 1);
							day = Math.max(day, days[i]);
							stack.push(i);
							break;
						}
						
						days[i] = Math.max(days[i], days[stack.pop()] + 1);
						day = Math.max(day, days[i]);
					}
					if(!stack.isEmpty() && p[i] > p[stack.peek()]) {
						stack.push(i);
					}
				}
				
			}
		}
		
		return day;
    }
	
	
	static class ArrayPairs_Node {
		int eq;
		int higher;
		double val;
		ArrayPairs_Node left, right;
		
		ArrayPairs_Node(int eq, int higher, double val){
			this.higher = higher;
			this.eq = eq;
			this.val = val;
		}
		
		static ArrayPairs_Node insert(ArrayPairs_Node node, double val, int h) {
			if(node == null)
				return new ArrayPairs_Node(1, h, val);
			int cmp = Double.compare(node.val, val);
			if(cmp < 0) {
				h += node.eq + node.higher;
				node.left = insert(node.left, val, h);
			}
			else if(cmp > 0){
				node.higher++;
				node.right = insert(node.right, val, h);
			}
			else {
				node.eq++;
			}
			return node;
		}
		
		static long EH(ArrayPairs_Node node, double val) {
			if(node == null)
				return 0;
			int cmp = Double.compare(node.val, val);
			long ans = 0;
			if(cmp < 0) {
				ans = ans + node.eq + node.higher;
				ans += EH(node.left, val);
			}
			else if(cmp > 0) {
				ans = EH(node.right, val);
			}
			else {
				ans = node.eq + node.higher;
			}
			return ans;
		}
		
	}

	
	static long ArrayPairs(int[] arr) {
		return ArrayPairsHelp(arr, 0, arr.length-1);
    }
	
	static long ArrayPairsHelp(int[] array, int left, int right) {
		if(left >= right)
			return 0;
		long pairs = 0;
		int max_index = -1;
		int max = Integer.MIN_VALUE;
		for(int i=left; i<=right; i++) {
			if(array[i] > max) {
				max = array[i];
				max_index = i;
			}
		}
		
		if(max_index != left && max_index != right) {
			int[] right_part = new int[right - max_index];
			for(int i=max_index+1; i<=right; i++)
				right_part[i - max_index - 1] = array[i];
			Arrays.sort(right_part);
			for(int i=left; i<max_index; i++) {
				double target = (double)max / array[i];
				int L = 0, R = right_part.length-1;
				while(L < R) {
					int mid = (L + R) >>> 1;
					int cmp = Double.compare(target, right_part[mid]);
					if(cmp >= 0) {
						L = mid + 1;
					}
					else {
						R = mid;
					}
				}
				if(Double.compare(target, right_part[L]) < 0)
					L--;
				L++;
				pairs = pairs + L;
			}
		}
		else {
			for(int i=left; i<=right; i++)
				if(array[i] == 1)
					pairs++;
			if(max == 1)
				pairs--;
		}
		
		if(max_index == left) {
			while(left < right && array[left+1] == array[left])
				left++;
			pairs += ArrayPairsHelp(array, left+1, right);
		}
		else if(max_index == right) {
			while(left < right && array[right-1] == array[right])
				right--;
			pairs += ArrayPairsHelp(array, left, right-1);
		}
		else {
			pairs += ArrayPairsHelp(array, left, max_index);
			pairs += ArrayPairsHelp(array, max_index, right);
		}
		return pairs;
	}
	
	
	static long SubsequenceWeighting(int[] a, int[] w) {
		long answer = Long.MIN_VALUE;
		TreeMap<Integer, Long> map = new TreeMap<>();
		for(int i=0; i<a.length; i++) {
			Map.Entry<Integer, Long> lowEntry = map.lowerEntry(a[i]);
			long b = (lowEntry == null ? 0 : lowEntry.getValue()) + w[i];
			SortedMap<Integer, Long> sortedMap = map.tailMap(a[i]);
			List<Integer> delete = new ArrayList<>();
			for(Map.Entry<Integer, Long> entry : sortedMap.entrySet()) {
				if(entry.getValue() > b)
					break;
				delete.add(entry.getKey());
			}
			for(Integer dele : delete)
				map.remove(dele);
			if(!map.containsKey(a[i]))
				map.put(a[i], b);
			if(b > answer)
				answer = b;
		}
		return answer;
	}
	
	static void BIT_update(int[] BIT, int i, int val) {
		i++;
		while(i < BIT.length) {
			BIT[i] += val;
			i += i & -i;
		}
	}
	
	static long BIT_sum(int[] BIT, int i) {
		i++;
		long sum = 0;
		while(i > 0) {
			sum = sum + BIT[i];
			i -= i & -i;
		}
		return sum;
	}
	
	
	static void LibraryQuery(int[] libraiy, int[][] query) {
		PriorityQueue<Integer> pq = new PriorityQueue<>((a,b) -> b-a);
		for(int [] q : query) {
			if(q[0] == 0) {
				int x = q[1], y = q[2], k = q[3];
				for(int i=x-1; i<y; i++) {
					if(pq.size() < k)
						pq.add(libraiy[i]);
					else {
						if(libraiy[i] < pq.peek()) {
							pq.poll();
							pq.add(libraiy[i]);
						}
					}
				}
				System.out.println(pq.peek());
				pq.clear();
			}
			else {
				libraiy[q[1]-1] = q[2];
			}
		}
	}
	
	static void LibraryQuery2(int[] libraiy, int[][] query) {
		int[] help = new int[1001];
		for(int[] q : query) {
			if(q[0] == 1) {
				libraiy[q[1] - 1] = q[2];
			}
			else {
				int x = q[1]-1, y = q[2] - 1, k = q[3];
				for(int i=x; i<=y; i++) {
					help[libraiy[i]]++;
				}
				for(int i=1; i<=1000; i++) {
					k -= help[i];
					if(k <= 0) {
						System.out.println(i);
						break;
					}
				}
				Arrays.fill(help, 0);
			}
		}
	}

	
	static void LibraryQueryInput() {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			int T = scanner.nextInt();
			for(int t=0; t<T; t++) {
				int n = scanner.nextInt();
				int[] library = new int[n];
				for(int i=0; i<n; i++)
					library[i] = scanner.nextInt();
				n = scanner.nextInt();
				int[][] query = new int[n][4];
				for(int i=0; i<n; i++) {
					query[i][0] = scanner.nextInt();
					query[i][1] = scanner.nextInt();
					query[i][2] = scanner.nextInt();
					if(query[i][0] == 0) {
						query[i][3] = scanner.nextInt();
					}
				}
				LibraryQuery(library, query);
			}
		}
		scanner.close();
	}

	static int truckTour(int[][] petrolpumps) {
		int index = 0;
		long sum = 0;
		for(int i=0; i<petrolpumps.length; i++) {
			if(sum < 0) {
				sum = 0;
				index = i;
			}
			sum = sum + petrolpumps[i][0] - petrolpumps[i][1];
		}
		return index;
    }
	
	
	static int truckTour2(int[][] array) {
		Queue<Integer> queue = new LinkedList<>();
		int n = array.length;
		for(int i=0; i<n; i++) {
			queue.add(array[i][0] - array[i][1]);
		}
		for(int i=0; i<n; i++) {
			long sum = 0;
			boolean success = true;
			for(Integer temp : queue) {
				sum += temp;
				if(sum < 0) {
					success = false;
					break;
				}
			}
			if(success) {
				return i;
			}
			queue.add(queue.poll());
		}
		return -1;
	}
	
	
	static long Triplets(int[] d) {
		long triplets = 0;
		TreeSet<Integer> right = new TreeSet<>();
		Map<Integer, Integer> map = new HashMap<>();
		Map<Integer, Integer> small = new HashMap<>();
		TreeSet<Integer> left = new TreeSet<>();
		for(int i=1; i<d.length; i++) {
			right.add(d[i]);
			map.put(d[i], map.getOrDefault(d[i], 0) + 1);
		}
		left.add(d[0]);
		for(int i=1; i<d.length; i++) {
			int fre = map.get(d[i]);
			int pre_left_size = small.getOrDefault(d[i], 0);
			long left_size = left.headSet(d[i]).size() - pre_left_size;
			long right_size = right.tailSet(d[i], false).size();
			triplets += left_size * right_size;
			left.add(d[i]);
			if(fre == 1)
				right.remove(d[i]);
			else
				map.put(d[i], fre - 1);
			small.put(d[i], (int)left_size);
		}
		return triplets;
    }
	
	static long Triplets2(int[] d) {
		long triplets = 0;
		int[] clone = d.clone();
		Arrays.sort(clone);
		Map<Integer, Integer> index_map = new HashMap<>();
		int index = 0;
		index_map.put(clone[0], index++);
		for(int i=1; i<d.length; i++) {
			if(clone[i] == clone[i-1])
				continue;
			index_map.put(clone[i], index++);
		}
		index--;
		int[] right_BIT = new int[d.length+1];
		int[] left_BIT = new int[d.length+1];
		Map<Integer, Integer> used = new HashMap<>();
		for(int i=1; i<d.length; i++) {
			int fre = used.getOrDefault(d[i], 0);
			if(fre == 0) {
				BIT_update(right_BIT, index_map.get(d[i]), 1);
			}
			used.put(d[i], fre+1);
		}
		Set<Integer> left_used = new HashSet<>();
		BIT_update(left_BIT, index_map.get(d[0]), 1);
		left_used.add(d[0]);
		Map<Integer, Integer> preSmall = new HashMap<>();
		for(int i=1; i<d.length-1; i++) {
			int fre = used.get(d[i]);
			int pos = index_map.get(d[i]);
			int pre_left_size = preSmall.getOrDefault(d[i], 0);
			long left_size = BIT_sum(left_BIT, pos-1) - pre_left_size;
			long right_size =BIT_sum(right_BIT, index) - BIT_sum(right_BIT, pos);
			triplets += left_size * right_size;
			if(fre == 1) {
				BIT_update(right_BIT, pos, -1);
			}
			if(left_used.add(d[i])) {
				BIT_update(left_BIT, pos, 1);
			}
			preSmall.put(d[i], (int)left_size);
			used.put(d[i], fre-1);
		}
		
		return triplets;
	}
	
	static long countInversions(int[] arr) {
		countInversions = 0;
		countInversionsHelp = new int[arr.length];
		countInversionsmergeSort(arr, 0, arr.length-1);
		return countInversions;
    }
	
	static int[] countInversionsHelp;
	static long countInversions = 0;
	
	static void countInversionsmergeSort(int[] array, int left, int right) {
		if(left >= right)
			return;
		int mid = left + (right - left) / 2;
		countInversionsmergeSort(array, left, mid);
		countInversionsmergeSort(array, mid+1, right);
		countInversionsmergeSort(array, left, mid, right);
	}
	
	static void countInversionsmergeSort(int[] array, int left, int mid, int right) {
		int i=left, j = mid + 1;
		for(int k=left; k<=right; k++) {
			countInversionsHelp[k] = array[k];
		}
		for(int k=left; k<=right; k++) {
			if(i > mid) {
				array[k] = countInversionsHelp[j++];
			}
			else if(j > right) {
				array[k] = countInversionsHelp[i++];
				countInversions = countInversions + j - mid - 1;
			}
			else if(countInversionsHelp[i] > countInversionsHelp[j]) {
				array[k] = countInversionsHelp[j];
				j++;
			}
			else {
				array[k] = countInversionsHelp[i];
				i++;
				countInversions = countInversions + j - mid - 1;
			}
		}
	}
	
	
	static long minimumPasses(long m, long w, long p, long n) {
		long passes = 0;
		if(p >= n) {
			long mod = n % (m * n);
			if(mod != 0)
				mod = 1;
			else
				mod = 0;
			return n / (m*w) + mod;
		}
		if(m == 1 && w == 1 && p != 1) {
			passes += p;
			m++;
		}
		long cadnies_left = 0;
		while(true) {
			long candies = m * w + cadnies_left;
			passes++;
			if(candies >= n)
				break;
			long units = candies / p;
			long mod = candies % p;
			if(mod != 0)
				cadnies_left = mod;
			
			
			long max = Math.max(units / 2, units - units/2);
			long min = Math.min(units / 2, units - units/2);
			if(w < m) {
				w += max;
				m += min;
			}
			else {
				m += max;
				w += min;
			}
			
		}
		return passes;
    }
	
	
	static void shortestReach(int n, int[][] edges, int s) {
		long[] answer = new long[n+1];
		Arrays.fill(answer, -1);
		PriorityQueue<long[]> pq = new PriorityQueue<>(new Comparator<long[]>() {
			@Override
			public int compare(long[] o1, long[] o2) {
				if(o1[1] < o2[1])
					return -1;
				else if(o1[1] > o2[1])
					return 1;
				return 0;
			}
		});
		pq.add(new long[] {s, 0});
		while(!pq.isEmpty()) {
			long[] pair = pq.poll();
			int cur = (int) pair[0];
			long dis = pair[1];
			if(answer[cur] != -1)
				continue;
			answer[cur] = dis;
			for(int i=1; i<=n; i++) {
				if(edges[(int) cur][i] != -1) {
					pq.offer(new long[] {i, dis + edges[cur][i]});
				}
			}
		}
		for(int i=1; i<=n; i++) {
			if(i != s) {
				System.out.print(answer[i]+" ");
			}
		}
		System.out.println();
    }
	
	
	
	
	static long chiefHopper(int[] arr) {
		long limit = 1000000000000L;
		long left = 1, right = 1000000000000L;
		while(left < right) {
			long mid = left + (right - left) / 2;
			long energy = mid;
			for(int h : arr) {
				energy = Math.min(energy, limit);
				if(energy < h) {
					energy -= h - energy;
				}
				else {
					energy += energy - h;
				}
				if(energy < 0)
					break;
			}
			if(energy < 0) {
				left = mid+1;
			}
			else {
				right = mid;
			}
		}
		return left;
    }
	
	static void FloydCityofBlindingLights(int[][] edges, int n, int[][] queries) {
		int[][] shortest_path = new int[n+1][n+1];
		PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[1] - b[1]);
		for(int x=1; x<=n; x++) {
			int[] distance = new int[n+1];
			Arrays.fill(distance, -1);
			pq.clear();
			pq.add(new int[] {x, 0});
			while(!pq.isEmpty()) {
				int[] pair = pq.poll();
				int cur = pair[0];
				int dis = pair[1];
				if(distance[cur] != -1)
					continue;
				distance[cur] = dis;
				for(int i=1; i<=n; i++) {
					if(edges[cur][i] == -1)
						continue;
					pq.add(new int[] {i, dis + edges[cur][i]});
				}
			}
			shortest_path[x] = distance;
		}
		
		for(int[] query : queries) {
			System.out.println(shortest_path[query[0]][query[1]]);
		}
	}
	
	
	static long[] playingWithNumbers(long[] arr, int[] queries) {
		long[] answer = new long[queries.length];
		int n = arr.length;
		Arrays.sort(arr);
		long[] cum =new long[n+1];
		for(int i = 0;i < n;i++){
			cum[i+1] = cum[i] + arr[i];
		}
		long h = 0;
		for(int Q = 0;Q < queries.length;Q++){
			int x = queries[0];
			h -= x;
			int ind = Arrays.binarySearch(arr, h);
			if(ind < 0)ind = -ind-2;
			long ret = 0;
			ret += cum[n]-cum[ind+1]-h*(n-(ind+1));
			ret += -cum[ind+1]+h*(ind+1);
			answer[Q] = ret;
		}
		return answer;
    }
	
	
	static int findShortest(int N, Map<Integer, List<Integer>> graph, int[] ids, int val) {
		int[][] pairs = new int[N+1][2];
		Queue<Integer> queue = new LinkedList<>();
		for(int i=0; i<ids.length; i++) {
			if(ids[i] == val) {
				pairs[i+1] = new int[] {i+1, 0};
				queue.add(i+1);
			}
		}
		while(!queue.isEmpty()) {
			int size = queue.size();
			for(int t=0; t<size; t++) {
				int cur = queue.poll();
				List<Integer> list = graph.get(cur);
				if(list == null)
					continue;
				for(int next : list) {
					if(pairs[next][0] == 0) {
						pairs[next] = new int[] {pairs[cur][0], pairs[cur][1] + 1};
						queue.add(next);
					}
					else {
						if(pairs[next][0] == pairs[cur][0])
							continue;
						return pairs[next][1] + pairs[cur][1] + 1;
					}
				}
			}
		}
		
		return -1;
    }
	
	static int minTime(int[][] roads, int[] machines) {
		int time = 0;
		int N = roads.length + 1;
		int[] UF = new int[N];
		boolean[] is_machine = new boolean[N];
		for(int machine : machines) {
			is_machine[machine] = true;
		}
		for(int i=0; i<N; i++) {
			UF[i] = i;
		}
		Arrays.sort(roads, (a,b) -> (b[2] - a[2]));
		for(int i=0; i<roads.length; i++) {
			int[] road = roads[i];
			int a = road[0], b = road[1], cost = road[2];
			int parent_a = minTimeFind(UF, a);
			int parent_b = minTimeFind(UF, b);
			if(parent_a == parent_b)
				continue;
			if(is_machine[parent_a] && is_machine[parent_b]) {
				time += cost;
			}
			else {
				if(is_machine[parent_b]) {
					is_machine[parent_a] = true;
				}
				UF[parent_b] = parent_a;
			}
		}
		
		return time;
    }
	
	static int minTimeFind(int[] UF, int i) {
		while(UF[i] != i) {
			UF[i] = UF[UF[i]];
			i = UF[i];
		}
		return i;
	}
	
	
	
	@SuppressWarnings("unchecked")
	static long maximumPeople(long[][] city, long[][] cloud) {
		long people = 0;
		long answer = 0;
		long[] cloud_bucket = new long[cloud.length];
		List<Integer>[] city_diff = (ArrayList<Integer>[])new ArrayList[city.length+1];
		city_diff[city.length] = new ArrayList<>();
		Arrays.sort(city, new Comparator<long[]>() {

			@Override
			public int compare(long[] o1, long[] o2) {
				if(o1[1] < o2[1])
					return -1;
				else if(o1[1] > o2[1])
					return 1;
				return 0;
			}
		});
		TreeMap<Long, Integer> tree = new TreeMap<>();
		for(int i=0; i<city.length; i++) {
			if(i > 0 && city[i][1] == city[i-1][1]) {
				city[i][0] += city[i-1][0];
				city[i-1][0] = 0;
			}
			tree.put(city[i][1], i);
			city_diff[i] = new ArrayList<>();
		}
		for(int i=0; i<cloud.length; i++) {
			long[] pair = cloud[i];
			long start = pair[0] - pair[1];
			long end = pair[0] + pair[1];
			Map.Entry<Long, Integer> start_entry = tree.ceilingEntry(start);
			if(start_entry == null)
				continue;
			Map.Entry<Long, Integer> end_entry = tree.floorEntry(end);
			if(end_entry == null)
				continue;
			int start_index = start_entry.getValue();
			int end_index = end_entry.getValue();
			if(start_index > end_index)
				continue;
			city_diff[start_index].add(i+1);
			city_diff[end_index+1].add(-(i+1));
		}
		Set<Integer> set = new HashSet<>();
        for(int i=0; i<city.length; i++) {
            for(int temp : city_diff[i]) {
                if(temp > 0)
                    set.add(temp);
                else
                    set.remove(-temp);
            }
            if(set.size() == 1) {
                int index = set.iterator().next() - 1;
                cloud_bucket[index] = cloud_bucket[index] + city[i][0];
            }
            else if(set.isEmpty()) {
                people = people + city[i][0];
            }
        }
        answer = people;
        for(int i=0; i<cloud_bucket.length; i++) {
            answer = Math.max(answer, people + cloud_bucket[i]);
        }
        return answer;
	}
	
	
	static String roadsInHackerland(int n, int[][] roads) {
		Arrays.sort(roads, (a,b) -> (a[2]-b[2]));
		List<List<Integer>> tree = new ArrayList<>();
		for(int i=0; i<=n; i++) {
			tree.add(new ArrayList<>());
		}
		UF uf = new UF(n+1);
		Map<String, Integer> weight_map = new HashMap<>();
		for(int[] road : roads) {
			int a = road[0], b = road[1], weight = road[2];
			if(uf.isConnected(a, b))
				continue;
			uf.union(a, b);
			tree.get(a).add(b);
			tree.get(b).add(a);
			String key = Math.min(a, b) + "," + Math.max(a, b);
			weight_map.put(key, weight);
		}
		tree.get(1).add(1);
		List<Integer> binary = new ArrayList<>();
		List<Long> binary_counter = new ArrayList<>();
		boolean[] visited = new boolean[n+1];
		int[] child_counter = new int[n+1];
		visited[1] = true;
		roadsInHackerland_childCount(tree, 1, child_counter, visited, weight_map, binary, binary_counter, n);
		roadsInHackerlandBinaryFinalAdd(binary, binary_counter);
		StringBuilder sb = new StringBuilder();
		for(int i=binary.size()-1; i>=0; i--) {
			char c = binary.get(i) == 0 ? '0' : '1';
			sb.append(c);
		}
		return sb.toString();
    }
	
	
	
	
	static void roadsInHackerland_childCount(List<List<Integer>> tree, int cur, int[] child_counter, boolean[] visited, Map<String, Integer> weight_map, List<Integer> binary, List<Long> binary_counter, int n) {
		child_counter[cur] = 1;
		List<Integer> child = tree.get(cur);
		for(int next : child) {
			if(visited[next])
				continue;
			visited[next] = true;
			roadsInHackerland_childCount(tree, next, child_counter, visited,weight_map, binary, binary_counter, n);
			String path = Math.min(cur, next) + "," + Math.max(cur, next);
			int weight = weight_map.get(path);
			roadsInHackerlandBinaryAdd(binary, binary_counter, weight, ((long)n-child_counter[next]) * child_counter[next]);
			child_counter[cur] += child_counter[next];
		}
	}
	
	static void roadsInHackerlandBinaryFinalAdd(List<Integer> binary, List<Long> binary_counter) {
		for(int i=0; i<binary.size(); i++) {
			long count = binary_counter.get(i);
			if(count == 0)
				continue;
			int bit = binary.get(i);
			if(bit == 1) {
				binary.set(i, 0);
				binary_counter.set(i+1, binary_counter.get(i+1)+1);
				count--;
			}
			binary.set(i, (int)(count % 2));
			binary_counter.set(i, 0L);
			binary_counter.set(i+1, binary_counter.get(i+1) + count / 2);
		}
		while(binary_counter.get(binary_counter.size()-1) != 0) {
			long last = binary_counter.get(binary_counter.size()-1);
			binary.add((int)last % 2);
			binary_counter.set(binary_counter.size()-1, 0L);
			binary_counter.add(last / 2);
		}
	}
	
	
	static void roadsInHackerlandBinaryAdd(List<Integer> binary, List<Long> binary_counter, int i, long count) {
		while(binary.size() < i+1) {
			binary.add(0);
			binary_counter.add(0L);
		}
		if(binary_counter.size() < i+2) {
			binary_counter.add(0L);
		}
		if(binary.get(i) == 1) {
			binary.set(i, 0);
			binary_counter.set(i+1, binary_counter.get(i+1) + 1);
			count--;
		}
		binary.set(i, (int)(count % 2));
		binary_counter.set(i+1, binary_counter.get(i+1) + count / 2);
	}
	
	static class UF {
		int[] parent;
		public UF(int n) {
			parent = new int[n];
			for(int i=0; i<n; i++)
				parent[i] = i;
		}
		public int find(int i) {
			while(i != parent[i]) {
				parent[i] = parent[parent[i]];
				i = parent[i];
			}
			return i;
		}
		public void union(int i, int j) {
			int p_i = find(i);
			int p_j = find(j);
			if(p_i == p_j)
				return;
			parent[p_j] = p_i;
		}
		public boolean isConnected(int i, int j) {
			int p_i = find(i);
			int p_j = find(j);
			if(p_i == p_j)
				return true;
			return false;
		}
	}
	

	
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			
		}
		scanner.close();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
