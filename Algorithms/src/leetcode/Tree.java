package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

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
   
   
   
    
    

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
