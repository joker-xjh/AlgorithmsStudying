package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class Tree {
	
	public class TreeNode {
		      int val;
		      TreeNode left;
		     TreeNode right;
		      TreeNode(int x) { val = x; }
		  }
	
	
    public boolean findTarget(TreeNode root, int k) {
    	List<Integer> list = new ArrayList<>();
    	findTarget(list, root);
    	int i = 0, j = list.size()-1;
    	while(i<j) {
    		int one = list.get(i);
    		int two = list.get(j);
    		int sum = one + two;
    		if(sum > k)
    			j--;
    		else if(sum<k)
    			i++;
    		else 
    			return true;
    	}
    	return false;
        
    }
    
    private void findTarget(List<Integer> list, TreeNode node) {
    	if (node == null) {
			return;
		}
    	findTarget(list, node.left);
    	list.add(node.val);
    	findTarget(list, node.right);
    }
    
   
    
    
    
    
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums==null || nums.length==0) {
			return null;
		}
    	
        return constructMaximumBinaryTree(nums,0,nums.length-1);
    	
    }
    
    private TreeNode constructMaximumBinaryTree(int[] nums, int start, int end) {
    	if(start > end)
    		return null;
    	int max = Integer.MIN_VALUE;
    	int index = -1;
    	for(int i=start; i<=end; i++) {
    		if(nums[i] > max) {
    			max = nums[i];
    			index = i;
    		}
    	}
    	
    	TreeNode root = new TreeNode(max);
    	root.left = constructMaximumBinaryTree(nums, start, index-1);
    	root.right = constructMaximumBinaryTree(nums, index+1, end);
    	return root;	
    }
    
    
    public List<List<String>> printTree(TreeNode root) {
    	
        List<List<String>> res = new ArrayList<>();
    	if(root == null)
    		return res;
    	int depth = getDepth(root);
    	int row = depth;
    	int column = 1;
    	for(int i=0; i<depth; i++)
    		column *= 2;
    	column -= 1;
    	for(int i=0; i< row; i++) {
    		List<String> list = new ArrayList<>(column);
    		for(int j=0; j< column; j++)
    			list.add("");
    		res.add(list);
    	}
    		
    	
    	printTree(root,res,0, column-1,0);
    	
    	return res;
    }
    
    
    private void printTree(TreeNode node, List<List<String>> res, int start, int end, int row) {
    	if(node == null)
    		return;
    	int column = start + (end - start) / 2;
    	res.get(row).set(column, String.valueOf(node.val));
    	printTree(node.left, res, start, column-1, row+1);
    	printTree(node.right, res, column+1,end,row+1);
    }
    
    
    
    
    private int getDepth(TreeNode node) {
    	if(node == null)
    		return 0;
    	return 1 + Math.max(getDepth(node.left), getDepth(node.right));
    }
    
    
   private boolean  checkEqualTree = false;
   private int[] sum= new int[1];
   public boolean checkEqualTree(TreeNode root) {
       if(root == null)
    	   return true;
       if(root.left==null && root.right == null)
    	   return false;
       Map<TreeNode, Integer> map = new HashMap<>();
       sum[0]= sumTree(root, map);
       if( (sum[0] & 1 )!= 0)
    	   return false;
       int half = sum[0]/2;
       checkEqualTree(root,map,half);
       
	   return checkEqualTree;
    }
   private void checkEqualTree(TreeNode node, Map<TreeNode, Integer> map,int half) {
	   if(node == null)
		   return;
	   if(checkEqualTree)
		   return;
	   int sum = map.get(node);
	   if(sum == half) {
		   checkEqualTree = true;
	   }
	   checkEqualTree(node.left,map,half);
	   checkEqualTree(node.right,map,half);
   }
   
   
   
   
   private int sumTree(TreeNode node,Map<TreeNode, Integer> map) {
	   if(node == null)
		   return 0;
	   int sum = node.val + sumTree(node.left, map) + sumTree(node.right, map);
	   map.put(node, sum);
	   return sum;
   }
    
   
   
   public int widthOfBinaryTree(TreeNode root) {
	   int width = 1;
       Map<TreeNode, Integer> position = new HashMap<>();
       Queue<TreeNode> queue = new LinkedList<>();
       queue.add(root);
       position.put(root, 1);
       while(queue.size() > 0) {
    	   int start = 0, end = 0;
    	   int size = queue.size();
    	   for(int i=0; i<size; i++) {
    		   TreeNode node = queue.poll();
    		   if(i==0)
    			   start = position.get(node);
    		   if( i== size-1)
    			   end = position.get(node);
    		   if(node.left!=null) {
    			   queue.add(node.left);
    			   position.put(node.left, position.get(node)*2);
    		   }
    		   if(node.right != null) {
    			   queue.add(node.right);
    			   position.put(node.right, position.get(node)*2+1);
    		   }
    	   }
    	   
    	   width = Math.max(width, end-start+1);
       }
       return width;
   }
   
  static class Connection{
	 
	   int val;
	   List<Integer> next;;
	   
	   public Connection(int val) {
		  this.val = val;
		  next = new ArrayList<>();
	}
	   
	 
	   
	   public void addChildren(int child) {
		   next.add(child);
	   }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + val;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Connection other = (Connection) obj;
		if (val != other.val)
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "Connection [val=" + val + ", next=" + next + "]";
	}
	
	 
   }
   
   
   public int[] findRedundantConnection(int[][] edges) {
	   if(edges == null || edges.length ==0)
		   return null;
       Map<Integer, Connection> tree = new HashMap<>(); 
	   for(int[] array : edges) {
		   int root = array[0];
		   if(!tree.containsKey(root)) {
			   Connection connection = new Connection(root);
			   tree.put(root, connection);
		   }
		   int next = array[1];
		   if(!tree.containsKey(next)) {
			   Connection connection = new Connection(next);
			   tree.put(next, connection);
		   }
		   Connection connection = tree.get(root);
		   connection.addChildren(next);
	   }
       
	   for(int i=edges.length-1; i>=0; i--) {
		   int[] array = edges[i];
		   Connection father = tree.get(array[0]);
		   Connection son = tree.get(array[1]);
		   if(findRedundantConnection(tree, father, son) && findRedundantConnection(tree, son, father)) {
			   return array;
		   }
		   
	   }
	   
       
	   return null;
   }
   
   private boolean findRedundantConnection(Map<Integer, Connection> tree, Connection target,Connection connection) {
	   if(connection.val == target.val) {
		   System.out.println("11111111");
		   return true;
	   }
		   
	   List<Integer> next = connection.next;
	   for(int i=0; i<next.size(); i++) {
		   int child = next.get(i);
		   Connection children = tree.get(child);
		   boolean b = findRedundantConnection(tree, target, children);
		   if(b)
			   return true;
	   }
	   
	   return false;
   }
   
   public int longestUnivaluePath(TreeNode root) {
       int[] res = new int[1];
       if(root != null)
    	   dfs(root, res);
       return res[0];
   }
   
   
   private int dfs(TreeNode node, int[] res) {
	   int l = node.left != null ? dfs(node.left, res) : 0;
	   int r = node.right != null ? dfs(node.right, res) : 0;
	   int resl = node.left != null && node.left.val == node.val ? l+1:0;
	   int resr = node.right != null && node.right.val == node.val ? r+1 : 0;
	   res[0] = Math.max(res[0], resl + resr);
       return Math.max(resl, resr);
   }
   
   
   public int findClosestLeaf(TreeNode root, int k) {
       Queue<TreeNode> queue = new LinkedList<>();
       queue.add(root);
       TreeNode targetNode = null;
       while(!queue.isEmpty()) {
    	   TreeNode node = queue.poll();
    	   if(node.val == k) {
    		   targetNode = node;
    		   break;
    	   }
    	   if(node.left != null)
    		   queue.offer(node.left);
    	   if(node.right != null)
    		   queue.offer(node.right);
       }
       queue.clear();
       queue.offer(targetNode);
       while(!queue.isEmpty()) {
    	   TreeNode node = queue.poll();
    	   if(node.left == null && node.right == null)
    		   return node.val;
    	   if(node.left != null)
    		   queue.offer(node.left);
    	   if(node.right != null)
    		   queue.offer(node.right);
       }
	   return -1;
   }
   
   public int findClosestLeaf2(TreeNode root, int k) {
       List<TreeNode> list = new ArrayList<>();
       Set<Integer> leaf = new HashSet<>();
	   findClosestLeafHelp(root, list, leaf);
	   if(leaf.contains(k))
		   return k;
	   int index = -1;
	   for(int i=0; i<list.size(); i++) {
		   TreeNode node = list.get(i);
		   if(node.val == k) {
			   index = i;
			   break;
		   }
	   }
	   int left = index-1, right = index+1;
	   
	   while(true) {
		   TreeNode left_node = left >=0 ? list.get(left) : null;
		   TreeNode right_node = right < list.size() ? list.get(right) : null;
		   int left_val = left_node != null ? left_node.val : -1;
		   int right_val = right_node != null ? right_node.val : -1;
		   if(leaf.contains(left_val))
			   return left_val;
		   if(leaf.contains(right_val))
			   return right_val;
		   left--;
		   right++;
	   }
   }
   
   private void findClosestLeafHelp(TreeNode node, List<TreeNode> list, Set<Integer> leaf) {
	   if(node == null)
		   return;
	   findClosestLeafHelp(node.left, list, leaf);
	   list.add(node);
	   if(node.left == null && node.right == null)
		   leaf.add(node.val);
	   findClosestLeafHelp(node.right, list, leaf);
   }
   
   public int findClosestLeaf3(TreeNode root, int k) {
	   Map<Integer, List<TreeNode>> graph = new HashMap<>();
	   Set<Integer> leaf = new HashSet<>();
	   findClosestLeafDFS(root, root, graph, leaf);
	   if(leaf.contains(k))
		   return k;
	   Set<Integer> visited = new HashSet<>();
	   visited.add(k);
	   Queue<TreeNode> queue = new LinkedList<>();
	   queue.addAll(graph.get(k));
	   while(!queue.isEmpty()) {
		   TreeNode node = queue.poll();
		   int val = node.val;
		   if(leaf.contains(val))
			   return val;
		   List<TreeNode> list = graph.get(val);
		   for(TreeNode temp : list) {
			   if(!visited.contains(temp.val)) {
				   visited.add(temp.val);
				   queue.offer(temp);
			   }
				 
		   }
	   }
	   
	   return -1;
   }
   
   private void findClosestLeafDFS(TreeNode node, TreeNode parent, Map<Integer, List<TreeNode>> graph, Set<Integer> leaf) {
	   if(node == null)
		   return;
	   List<TreeNode> list = new ArrayList<>();
	   list.add(parent);
	   if(node.left != null)
		   list.add(node.left);
	   if(node.right != null)
		   list.add(node.right);
	   graph.put(node.val, list);
	   if(node.left == null && node.right == null)
		   leaf.add(node.val);
	   findClosestLeafDFS(node.left, node, graph, leaf);
	   findClosestLeafDFS(node.right, node, graph, leaf);
   }
   
   //Time Limit Exceeded
   public List<Integer> findMinHeightTrees(int n, int[][] edges) {
	   if(edges.length == 0) {
		   List<Integer> answer = new ArrayList<>();
		   answer.add(0);
		   return answer;
	   }
       Map<Integer, List<Integer>> graph = new HashMap<>();
       for(int[] edge : edges) {
    	   int a = edge[0], b = edge[1];
    	   List<Integer> list = graph.get(a);
    	   if(list == null) {
    		   list = new ArrayList<>();
    		   graph.put(a, list);
    	   }
    	   list.add(b);
    	   
    	   list = graph.get(b);
    	   if(list == null) {
    		   list = new ArrayList<>();
    		   graph.put(b, list);
    	   }
    	   list.add(a);
       }
       int[] heights = new int[n];
       int min = Integer.MAX_VALUE;
       Queue<Integer> queue = new LinkedList<>();
       Set<Integer> visited = new HashSet<>();
       for(int i=0; i<n; i++) {
    	   queue.add(i);
    	   visited.add(i);
    	   int temp = 0;
    	   while(!queue.isEmpty()) {
    		   int size = queue.size();
    		   for(int j=0; j<size; j++) {
    			   int point = queue.poll();
    			   List<Integer> others = graph.get(point);
    			   for(int other : others) {
    				   if(visited.contains(other))
    					   continue;
    				   visited.add(other);
    				   queue.offer(other);
    			   }
    		   }
    		   temp++;
    	   }
    	   if(temp < min)
    		   min = temp;
    	   heights[i] = temp;
    	   queue.clear();
    	   visited.clear();
       }
       List<Integer> answer = new ArrayList<>();
       for(int i=0; i<n; i++) {
    	   int height = heights[i];
    	   if(height == min)
    		   answer.add(i);
       }
	   return answer;
   }
   
   public List<Integer> findMinHeightTrees2(int n, int[][] edges) {
	   if(n == 1)
		   return Collections.singletonList(0);
	   Map<Integer, Set<Integer>> graph = new HashMap<>();
	   for(int i=0; i<n; i++)
		   graph.put(i, new HashSet<>());
	   for(int[] edge : edges) {
		   graph.get(edge[0]).add(edge[1]);
		   graph.get(edge[1]).add(edge[0]);
	   }
	   List<Integer> leaves = new ArrayList<>();
	   for(int i=0; i<n; i++) {
		   if(graph.get(i).size() == 1)
			   leaves.add(i);
	   }
	   while(n > 2) {
		   n -= leaves.size();
		   List<Integer> newLeaves = new ArrayList<>();
		   for(int leaf : leaves) {
			   int adj = graph.get(leaf).iterator().next();
			   graph.remove(leaf);
			   graph.get(adj).remove(leaf);
			   if(graph.get(adj).size() == 1)
				   newLeaves.add(adj);
		   }
		   leaves = newLeaves;
	   }
	   
	   return leaves;
   }
   
    static class UndirectedGraphNode {
	        int label;
	        List<UndirectedGraphNode> neighbors;
	        UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
	        @Override
	        public String toString() {
	        	return "label:"+label +" neighbors:"+neighbors;
	        }
	}
   
    
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if(node == null)
        	return null;
    	Set<Integer> visited = new HashSet<>();
    	Map<Integer, UndirectedGraphNode> map = new HashMap<>();
    	UndirectedGraphNode graph = cloneGraphHelp(node, visited, map);
    	return graph;
    }
    
    private UndirectedGraphNode cloneGraphHelp(UndirectedGraphNode node, Set<Integer> visited, Map<Integer, UndirectedGraphNode> map) {
    	if(map.containsKey(node.label))
    		return map.get(node.label);
    	UndirectedGraphNode copy = new UndirectedGraphNode(node.label);
    	map.put(node.label, copy);
    	visited.add(node.label);
    	for(UndirectedGraphNode adj : node.neighbors) {
    		if(adj.label == copy.label) {
    			copy.neighbors.add(copy);
    			continue;
    		}
    		if(visited.contains(adj.label))
    			continue;
    		visited.add(adj.label);
    		UndirectedGraphNode temp = cloneGraphHelp(adj, visited, map);
    		visited.remove(adj.label);
    		copy.neighbors.add(temp);
    	}
    	visited.remove(node.label);
    	return copy;
    }
    
    
    public boolean canFinish(int numCourses, int[][] prerequisites) {
    	if(numCourses == 0 || prerequisites == null || prerequisites.length == 0)
    		return true;
        int[][] matrix = new int[numCourses][numCourses];
        int[] indegree = new int[numCourses];
        int count = 0;
        Queue<Integer> queue = new LinkedList<>();
        
        for(int i=0; i<prerequisites.length; i++) {
        	int cur = prerequisites[i][0];
        	int pre = prerequisites[i][1];
        	matrix[pre][cur] = 1;
        	indegree[cur]++;
        }
    	for(int i=0; i<numCourses; i++)
    		if(indegree[i] == 0)
    			queue.offer(i);
    	while(!queue.isEmpty()) {
    		int course = queue.poll();
    		count++;
    		for(int i=0; i<numCourses; i++) {
    			if(matrix[course][i] > 0) {
    				if(--indegree[i] == 0){
    					queue.offer(i);
    				}
    			}
    		}
    	}
        
    	return count == numCourses;
    }
    
    public boolean findCycle(int numCourses, int[][] prerequisites) {
    	if(numCourses == 0 || prerequisites == null || prerequisites.length == 0)
    		return false;
    	Map<Integer, List<Integer>> graph = new HashMap<>();
    	Set<Integer> visited = new HashSet<>();
    	
    	for(int i=0; i<numCourses; i++)
    		graph.put(i, new ArrayList<>());
    	for(int i=0; i<prerequisites.length; i++) {
    		int pre = prerequisites[i][1];
    		int cur = prerequisites[i][0];
    		graph.get(pre).add(cur);
    	}
    	for(int i=0; i<numCourses; i++) {
    		visited.add(i);
    		if(findCycle(graph, i, visited))
    			return true;
    		visited.remove(i);
    	}
    	return false;
    }
    
    private boolean findCycle(Map<Integer, List<Integer>> graph, int cur, Set<Integer> visited) {
    	List<Integer> adj = graph.get(cur);
    	for(int point : adj) {
    		if(visited.contains(point))
    			return true;
    		visited.add(point);
    		if(findCycle(graph, point, visited))
    			return true;
    		visited.remove(point);
    	}
    	return false;
    }
    
    
    
    
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        if(numCourses == 0 || prerequisites == null )
        	return new int[] {};
        if(prerequisites.length == 0) {
        	int[] order = new int[numCourses];
        	for(int i=0; i<numCourses; i++)
        		order[i] = i;
        	return order;
        }
        int[] order = new int[numCourses];
        boolean[][] matrix = new boolean[numCourses][numCourses];
        int[] indegree = new int[numCourses];
        Queue<Integer> queue = new LinkedList<>();
        int index = 0;
        
        for(int i=0; i<prerequisites.length; i++) {
        	int pre = prerequisites[i][1];
        	int cur = prerequisites[i][0];
        	if(matrix[pre][cur])
        		continue;
        	matrix[pre][cur] = true;
        	indegree[cur]++;
        }
        
        for(int i=0; i<numCourses; i++)
        	if(indegree[i] == 0)
        		queue.offer(i);
        
        while(!queue.isEmpty()) {
        	int course = queue.poll();
        	order[index++] = course;
        	for(int i=0; i<numCourses; i++) {
        		if(matrix[course][i]) {
        			if(--indegree[i] == 0) {
        				queue.offer(i);
        			}
        		}
        	}
        }
        if(index != numCourses)
        	return new int[] {};
        return order;
    }
    
    
    public List<String> findItinerary(String[][] tickets) {
        List<String> itinerary = new ArrayList<>();
        if(tickets == null || tickets.length == 0)
        	return itinerary;
        Map<String, TreeSet<String>> graph = new HashMap<>();
        Map<String, Map<String, Integer>> visited = new HashMap<>();
        for(String[] ticket : tickets) {
        	String from = ticket[0] , to = ticket[1];
        	if(!graph.containsKey(from)) {
        		graph.put(from, new TreeSet<>());
        		visited.put(from, new HashMap<>());
        	}
        	graph.get(from).add(to);
        	visited.get(from).put(to, visited.get(from).getOrDefault(to, 0)+1);
        }
        int end = tickets.length;
        itinerary.add("JFK");
        findItinerary(graph, visited, "JFK", itinerary, end);
    	return itinerary;
    }
    
    private boolean findItinerary(Map<String, TreeSet<String>> graph, Map<String, Map<String, Integer>> visited, String from, List<String> itinerary, int end) {
    	TreeSet<String> adjs = graph.get(from);
    	if(adjs == null) {
    		if(end == 0)
    			return true;
    		return false;
    	}
    	for(String adj : adjs) {
    		Integer counter = visited.get(from).get(adj);
    		if(counter == 0)
    			continue;
    		visited.get(from).put(adj, counter-1);
    		itinerary.add(adj);
        	end--;
    		if(findItinerary(graph, visited, adj, itinerary, end))
    			return true;
    		visited.get(from).put(adj, counter);
    		itinerary.remove(itinerary.size()-1);
    		end++;
    	}
    	if(end == 0)
			return true;
    	return false;
    }
    
    
    
    public int minDiffInBST(TreeNode root) {
        minDiffInBSTHelp(root);
    	return minDiff;
    }
    
    private int minDiff = Integer.MAX_VALUE;
    private TreeNode minDiffPre = null;
    
    private void minDiffInBSTHelp(TreeNode node) {
    	if(node == null)
    		return;
    	minDiffInBSTHelp(node.left);
    	System.out.println(node.val);
    	if(minDiffPre == null)
    		minDiffPre = node;
    	else {
    		minDiff = Math.min(minDiff, node.val - minDiffPre.val);
    	}
    	minDiffPre = node;
    	minDiffInBSTHelp(node.right);
    }
    
    
    public TreeNode pruneTree(TreeNode root) {
        if(root == null)
        	return null;
        root.left = pruneTree(root.left);
        root.right = pruneTree(root.right);
        if(root.left == null && root.right == null && root.val == 0)
        	return null;
        return root;
    }
    
    
    public int[] findRedundantDirectedConnection(int[][] edges) {
    	int[] can1 = {-1, -1};
    	int[] can2 = {-1, -1};
    	int N = edges.length;
    	int[] parent = new int[N+1];
    	for(int i=0; i<N; i++) {
    		int[] edge = edges[i];
    		int u = edge[0], v = edge[1];
    		if(parent[v] != 0) {
    			can2 = new int[]{u, v};
    			can1 = new int[]{parent[v], v};
    			edge[1] = 0;
    			continue;
    		}
    		parent[v] = u;
    	}
    	for(int i=1; i<=N; i++)
    		parent[i] = i;
    	for(int[] edge : edges) {
    		if(edge[1] == 0)
    			continue;
    		int child = edge[0], father = edge[1];
    		if(findRedundantDirectedConnectionUF(parent, father) == child) {
    			if(can1[0] == -1)
    				return edge;
    			return can1;
    		}
    		parent[child] = father;
    	}
    	
    	return can2;
    }
    
    private int findRedundantDirectedConnectionUF(int[] parent, int i) {
    	while(parent[i] != i) {
    		parent[i] = parent[parent[i]];
    		i = parent[i];
    	}
    	return i;
    }
    
    private int distance;
    public int[] sumOfDistancesInTree(int N, int[][] edges) {
    	if(N == 1)
    		return new int[1];
        Map<Integer, Set<Integer>> tree = new HashMap<>();
        int[] answer = new int[N];
        int[] subTree = new int[N];
        boolean[] used = new boolean[N];
        for(int[] edge : edges) {
        	int f = edge[0], s = edge[1];
        	if(!tree.containsKey(f))
        		tree.put(f, new HashSet<>());
        	if(!tree.containsKey(s))
        		tree.put(s, new HashSet<>());
        	tree.get(f).add(s);
        	tree.get(s).add(f);
        }
        used[0] = true;
        countSubTree(tree, subTree, used, 0, 1);
        Arrays.fill(used, false);
        used[0] = true;
        sumOfDistancesInTreeHelp(tree, 0, N, used, answer, distance + N, subTree);
    	return answer;
    }
    
    private int countSubTree(Map<Integer, Set<Integer>> tree, int[] subTree, boolean[] used, int node, int dis) {
    	subTree[node] = 1;
    	for(int next : tree.get(node)) {
    		if(used[next])
    			continue;
    		used[next] = true;
    		distance += dis;
    		subTree[node] += countSubTree(tree, subTree, used, next, dis+1);
    	}
    	return subTree[node];
    }
    
    private void sumOfDistancesInTreeHelp(Map<Integer, Set<Integer>> tree, int node, int N, boolean[] used, int[] distances, int dis, int[] subTree) {
    	distances[node] = dis - subTree[node] + N - subTree[node];
    	for(int next : tree.get(node)) {
    		if(used[next])
    			continue;
    		used[next] = true;
    		sumOfDistancesInTreeHelp(tree, next, N, used, distances, distances[node], subTree);
    	}
    }
    
    
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> list = new ArrayList<>();
        Map<Integer, List<Integer>> graph = new HashMap<>();
        distanceKBuildGraph(root, graph, -1);
        Set<Integer> visited = new HashSet<>();
        visited.add(target.val);
        distanceKDFS(graph, target.val, K, visited, list);
        return list;
    }
    
    private void distanceKDFS(Map<Integer, List<Integer>> graph, int node, int K, Set<Integer> visited, List<Integer> answer) {
    	if(K == 0) {
    		answer.add(node);
    		return;
    	}
    	for(int next : graph.get(node)) {
    		if(visited.contains(next))
    			continue;
    		visited.add(next);
    		distanceKDFS(graph, next, K-1, visited, answer);
    	}
    }
    
    private void distanceKBuildGraph(TreeNode node, Map<Integer, List<Integer>> graph, int father) {
    	List<Integer> list = new ArrayList<>();
    	if(father != -1)
    		list.add(father);
    	if(node.left != null) {
    		list.add(node.left.val);
    		distanceKBuildGraph(node.left, graph, node.val);
    	}
    	if(node.right != null) {
    		list.add(node.right.val);
    		distanceKBuildGraph(node.right, graph, node.val);
    	}
    	graph.put(node.val, list);
    }
    
    
    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        Map<Integer, Integer> map = new HashMap<>();
    	subtreeWithAllDeepestHelp(root, map, 0);
    	return subtreeWithAllDeepestHelp(root, map);
    }
    
    private void subtreeWithAllDeepestHelp(TreeNode root, Map<Integer, Integer> map, int deep) {
    	if(root == null)
    		return;
    	map.put(root.val, deep);
    	subtreeWithAllDeepestHelp(root.left, map, deep+1);
    	subtreeWithAllDeepestHelp(root.right, map, deep+1);
    }
    
    private TreeNode subtreeWithAllDeepestHelp(TreeNode root, Map<Integer, Integer> map) {
    	if(root.left == null && root.right == null)
    		return root;
    	else if(root.left == null) {
    		TreeNode right = subtreeWithAllDeepestHelp(root.right, map);
    		map.put(root.val, map.get(right.val));
    		return right;
    	}
    	else if(root.right == null) {
    		TreeNode left = subtreeWithAllDeepestHelp(root.left, map);
    		map.put(root.val, map.get(left.val));
    		return left;
    	}
    	else {
    		TreeNode left = subtreeWithAllDeepestHelp(root.left, map);
    		TreeNode right = subtreeWithAllDeepestHelp(root.right, map);
    		int left_deep = map.get(left.val);
    		int right_deep = map.get(right.val);
    		if(left_deep > right_deep) {
    			map.put(root.val, left_deep);
    			return left;
    		}
    		else if(left_deep < right_deep) {
    			map.put(root.val, right_deep);
    			return right;
    		}
    		else {
    			map.put(root.val, right_deep);
    			return root;
    		}
    	}
    }
    
    
    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val,List<Node> _children) {
            val = _val;
            children = _children;
        }
    };
    
    
    public int maxDepth(Node root) {
        int depth = 0;
    	if(root == null)
    		return 0;
    	Queue<Node> queue = new LinkedList<>();
    	queue.add(root);
    	while(!queue.isEmpty()) {
    		int size = queue.size();
    		depth++;
    		for(int t=0; t<size; t++) {
    			Node node = queue.poll();
    			for(Node child : node.children) {
    				if(child == null)
    					continue;
    				queue.add(child);
    			}
    		}
    	}
    	return depth;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
 

	public static void main(String[] args) {
		
	}

}
