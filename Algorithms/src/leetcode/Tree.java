package leetcode;

import java.util.ArrayList;

import java.util.List;

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
    
    
    
    
    

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
