import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class leetcode {
    public static void main(String[] args) {
        int[] arr = { 3, 5, 6, -1, -1, 20, 7, -1, -1, 4, -1, -1, 1, 2, -1, -1, 8, -1, -1 };
        TreeNode root = createTree(arr);
        display(root);
        // boolean check = lca(root, new TreeNode(4), new TreeNode(7));
        // System.out.println(check);
        // List<Integer> li = distanceK(root, new TreeNode(5), 2);
        // System.out.println(li);
        // List<Integer> li = allNodesAtK2(root,new TreeNode(8),1);
        // System.out.println(li);
        // System.out.println(maxPathSum(root));
        List<List<Integer>> ans = verticalTraversal(root);
        for(List<Integer> l:ans){
            System.out.println(l);
        }
    }

    // class tree to store the node
    public static class TreeNode {
        int val;
        TreeNode left = null;
        TreeNode right = null;

        TreeNode() {
        }

        TreeNode(int data) {
            this.val = data;
        }
    }

    // variable to iterate over the array through which we will create the tree
    static int idx = 0;
    // we will not use idx inside the createTree function because if we do so then
    // on returning from child to parent the idx will leave all changes in the child
    // and stay on the value which we used to call the child from parent
    // ans we want to increase the index so we will use static variable

    //this function will create the tree
    public static TreeNode createTree(int[] arr) {

        // we will break this recursion when we get -1 in the array or when the idx becomes greater than
        // in size from the array
        if (idx >= arr.length || arr[idx] == -1) {
            // we will increase the array index when we return
            idx++;
            return null;
        }
        // creating the new node
        TreeNode node = new TreeNode(arr[idx]);
        // incrementing the index of the array
        idx++;
        // calling the recursion on the left subtree
        node.left = createTree(arr);
        // calling the recursion on the right subtree
        node.right = createTree(arr);
        // returning the result
        return node;
    }


    // function to display the graph
    public static void display(TreeNode root) {
        System.out.print(root.left != null ? root.left.val : ".");
        System.out.print(" <- " + root.val + " -> ");
        System.out.println(root.right != null ? root.right.val : ".");
        if (root.left != null) {
            display(root.left);
        }
        if (root.right != null) {
            display(root.right);
        }

    }

    // 236 leetcode lca
    // first approach to solve lca using root to node path arraylist
    // this is the first approach or basic approach in which we first we will find the
    // root to node path for each node p and q and then we will comapre there path
    // from root to node, when we get the different node in their path it means the node 
    // just above these node is the lowest common ancestor.

    // function to find the root to node path
    // in this first we will find the node and then adding it to the ans path
    // after that we will return true means we find the answer
    // now if we get true from he any of the call it means we need to include current node 
    // in the path
    public static boolean rootToNodePath(TreeNode node, TreeNode p, ArrayList<TreeNode> path) {
        // base case
        if (node == null) {
            return false;
        }
        // checking if the node is target node or not
        if (node.val == p.val) {
            path.add(node);
            return true;
        }
        // calling the recursion on the
        boolean res = rootToNodePath(node.left, p, path) || rootToNodePath(node.right, p, path);
        // if res becomes true it means we have found the target
        if (res) {
            // so we will add the node to the current node
            path.add(node);
        }
        // and returning the result
        return res;
    }

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // first finding the root to node path for p and aq
        // then comparing the element of the array of root to node path
        // we will store the element if they are same and
        // if they get different then it means the node stored in previous is actually
        // the
        // lca so we will return that

        // arraylist to store the  node pth root to node path
        ArrayList<TreeNode> left = new ArrayList<>();
        rootToNodePath(root, p, left);// finding the path
        // to store the root to node path of the qth node
        ArrayList<TreeNode> right = new ArrayList<>();
        rootToNodePath(root, q, right); // finding the path
        TreeNode prev = new TreeNode(); // creating the new node
        int i = left.size() - 1; // variable to iterate over pth root to node path
        int j = right.size() - 1; // variable to iterate over the qth root to node path
        while (i >= 0 && j >= 0 && left.get(i) == right.get(j)) { // iterating over paths
            prev = left.get(i); // setting prev to the lca
            i--; // decrementing the i
            j--; // decrementing the j
        }
        return prev; // returning lca stored in the prev
    }

    // now in the above program we used space to store the root to node path
    // now if we want to find lca without using any extra space
    // this variable will store the lca
    static TreeNode LCA = null;
    // function to find lca efficiently without using the space
    // at a node we will ask to child to return if they are the ancestor of the any given node
    // if they did they will return true
    // now we also need to consider the case if the current node is the required given node
    // so we need to check if the both child is true or if one child or parent is true
    // if it is true it means we have find the lca otherwise not
    public static boolean lca(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {// base case 
            return false;
        }
        //variable to store the current node status , false means it is not the required node
        boolean selfDone = false;
        // checking at the current node if it became equal to any of the node we will set it true
        if (root.val == p.val || root.val == q.val) {
            selfDone = true;
        }
        // calling the left recursion
        boolean leftDone = lca(root.left, p, q);
        // checking if we get lca or not if we got then we will return true and get back to it's parent
        if (LCA != null) {
            return true;
        }
        // calling the right recursion
        boolean rightDone = lca(root.right, p, q);
        // checking if we get lca or not if we got then we will return true and get back to it's parent
        if (LCA != null) {
            return true;
        }

        // now this is the main condition we need to check to set the lca
        // there will be three cases, first - when both child return true it means the current node is the 
        // is the lca ,second case - when the only right child is returning true and current node is true
        // then also the current node will be the lca, now third case - when right and current is true
        // then also the current node will be the lca so here we are checking these three condition
        // and if it is true we are setting the lca
        if ((selfDone && leftDone) || (selfDone && rightDone) || (rightDone && leftDone)) {
            LCA = root;
        }
        // now if only one of them is true then we need to return true
        // so here we are checking and also returning the true according to the true cas
        return selfDone || leftDone || rightDone;
    }

    // leetcode 863
    // all nodes at distance k
    // list to store the nodes at distance k
    // first basic approach
    // we can find the root to node path first and then using this path we can traverse the node at distance k
    // from the target node by traversing each node and then finding the node at distance k -i for that particular
    // node so basically in this approach we will use the o(n) space
    
    public static void kDown(TreeNode root,int level, TreeNode blockNode,List<Integer> ans){
        // first checking the base case and if it is equal to the block node
        if(root == null ||  root == blockNode){
            return;
        }
        // if level became zero then we will add the answer to the list and return
        if(level == 0){
            ans.add(root.val);
            return;
        }
        // now calling the left and right call
        kDown(root.left, level-1, blockNode,ans);
        kDown(root.right, level-1, blockNode,ans);
    }
    
    // this function is used to find the all node at k position
    public static List<Integer> allNodeAtK(TreeNode root,TreeNode target,int k){
        // declaring array list to store root to node path
        ArrayList<TreeNode> path = new ArrayList<>();
        // calling the root to node path function 
        rootToNodePath(root, target, path);
        // list to store the answer list
        List<Integer> ans = new ArrayList<>();
        // this will store the block node 
        TreeNode blockNode = null;
        // iterating over the path arraylist to call the kDown function to each node 
        for(int i=0;i<path.size();i++){
            if(k-i>=0)kDown(path.get(i), k-i, blockNode,ans); // calling function on each node
            // and setting the block node value
            blockNode = path.get(i);
        }
        // returning the result
        return ans;
    }

    // this is the second approach to find the all nodes at distance k
    // in this we will not ask for the array of the root to node path but we will call the kDown function at the places
    // where we are storing the node into the arraylist by doing this we will save the space
    public static int optimizedRootToNodePath(TreeNode root,TreeNode target, int k,List<Integer> ans){
        if(root == null){
            return -1;
        }
        if(root.val==target.val){
            kDown(root, k, null, ans);
            return 1;
        }
        int left = optimizedRootToNodePath(root.left,target,k,ans);
        if(left!=-1){
            kDown(root, k-left, root.left, ans);
            return left+1;
        }
        int right = optimizedRootToNodePath(root.right,target,k,ans);
        if(right!=-1){
            kDown(root,k-right,root.right,ans);
            return right+1;
        }
        return -1;
    }

    public static List<Integer> allNodesAtK2(TreeNode root,TreeNode target,int k){
        List<Integer> ans = new ArrayList<>();
        optimizedRootToNodePath(root,target,k,ans);
        return ans;
    }


    // leetcode 543
    // diameter of a tree
    // it is the distance between the two nodes which is deepest in the tree

    // we can do this by making sure at each node we are considering the maximum height from that node as well as the 
    // maximum diameter between two deepest nodes from that node
    public static void diameter(TreeNode root,ArrayList<Integer> ans){
        // this is the base case so we will return the array with value zero
        if(root==null){
            ans.add(0);
            ans.add(0);
            return;
        }
        // arrayList to store the answer of the left call
        ArrayList<Integer> left = new ArrayList<>();
        // arrayList to store the answer of the right call
        ArrayList<Integer> right = new ArrayList<>();
        // calling recursion to the right and left
        diameter(root.left, left);
        diameter(root.left, right);
         // now first finding the max height from the current root node
        int maxheight = Math.max(left.get(0),right.get(0))+1;
        // now finding the diameter from the particular rot node
        int maxDiameter = Math.max(left.get(0)+right.get(0)+2,Math.max(left.get(1),right.get(1)));
        // adding this to the answer
        ans.add(maxheight);
        ans.add(maxDiameter);
    }

     // now by observing above program we can see that actually we are basically using the functionality of the finding height
     // of the tree problem but we are  using an array to store both 
     // so now we are writing the code to find height and in this code we are going to find the 
     // diameter just we will now using return type
     // variable to store the diameter
    static int diam = 0;
    // function to find height and by using this function we are finding the 
    // diameter of the tree
     public static int diameter02(TreeNode root){
         // base case
        if(root == null){
            return -1; // -1 means height is not applicable at null node
        }
        // finding the height of the left subtree
        int lh = diameter02(root.left);
        // finding the height of the right subtree
        int rh = diameter02(root.right);
        // finding the diameter of the tree
        diam = Math.max(diam,lh+rh+2);
        // returning height w.r.t current root node
        return Math.max(lh,rh)+1;
    }

    public int diameterOfBinaryTree(TreeNode root) {
        ArrayList<Integer> ans = new ArrayList<>();
        diameter(root, ans);
        return ans.get(1);
    }
     // leetcode pathsum 112 easy
     // we need to determine if there is a path whose sum is equal to the given target sum

     // so basic idea is that at a particular node we ask our child to find the target
     // sum which is equal to the target-root.val if at end we get target zero at root equals to null
     // that means we have found the target

     // in this problem we need to make sure that the leaf node is leaf node
     // now if we subtract root.val from sum in recursion then it will not run for the negative 
     // numbers in the tree so we will use another variable s to sum and at last to check with sum
     // if it is equal or not
     public static boolean path(TreeNode root,int sum,int s){
         if(root == null){
            //  if(sum==0){ // if we check here then we are not able to check that it is a leaf node
            //      return true; // so we will check this condition at the leaf node outside the base case
            //  }
             return false;
         }
         // here we are checking that the node is leaf node only 
         if(root.left==null && root.right==null && sum == s+root.val){
             return true;
         }
         // variable to store result
         boolean res = false;
         // calling recursion
         res = res || path(root.left,sum,s+root.val) || path(root.right,sum,s+root.val);
         // returning the answer
         return res;
     }
     // has path to 
     public static boolean hasPathSum(TreeNode root, int sum) {
        if(root==null){
            return false;
        }
        return path(root,sum,0);
    }

    // leetcode 113 pathsum 2
    // in this we need to find all the paths from root to leaf whose sum is equal
    // to the target sum
    static List<List<Integer>> ans = new ArrayList<>();
    // the method basically same as above now just we need to track the node in the list
    // and we need to return the list of list of the path
    public static void pathSum2(TreeNode root,List<Integer> res,int sum,int s){
        // base case
        if(root == null){
            return ;
        }
        // check for the leaf node and the sum we got or not
        if(root.left==null && root.right==null &&sum == s+root.val){
            List<Integer> smallAns = new ArrayList(res); // copying list in another list
            smallAns.add(root.val); // adding current node to new list
            ans.add(res);// and adding it to the answer
            return; // returning
        }
        // adding the current node to the list
        res.add(root.val);
        // calling the recursion
        pathSum2(root.left, res, sum, s+root.val);
        pathSum2(root.right, res, sum, s+root.val);
        // removing the current node
        res.remove(res.size()-1);
    }
    // this function will call the pathsum2 function to get all path
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        // initializing new list
        List<Integer> res = new ArrayList<>();
        // calling the function
        pathSum2(root, res, sum, 0);
        // returning the result
        return ans;
    }

    // leetcode 124
    // in this we need to find the path which has maximum sum no matter where the node is

    // Now this variable will store the max sum between any node to any node
    static int maxSumFromAnyNodeToAnyNode = 0;
    // this function will find the max sum between current root to any of its children's at any level using this we
    // will find the max sum
    public static int maxSum(TreeNode root){
        // base case when we got the null
        if(root == null){
            return (int)-1e8;
        }
        // check for the  leaf node to not go to the null node
        if(root.left == null && root.right==null){
            // comparing the max sum with the root value
            maxSumFromAnyNodeToAnyNode = Math.max(maxSumFromAnyNodeToAnyNode,root.val);
            // and then returning the value of the root
            return root.val;
        }
        // calling the left and right recursion
        int left = maxSum(root.left);
        int right = maxSum(root.right);
        // now below are the possible sums for the answers
        // first there is a chance that we got the max sum by adding the value of the root
        // to the max of the left or the right subtree
        int  possibleMaxSum1 = Math.max(left,right)+root.val;
        // comparing the sum by including left right and current node value to the 
        // above case
        int possibleMaxSum2 = Math.max(possibleMaxSum1,left+right+root.val);
        // now comparing the above max sum only with the value of the current root
        int possibleMaxSum3 = Math.max(possibleMaxSum2,root.val);
        // max sum variable will contain the max sum between left and right subtree
        // so we are comparing this with the above possible sum
        maxSumFromAnyNodeToAnyNode = Math.max(maxSumFromAnyNodeToAnyNode,possibleMaxSum3);
        // now returning the max sum between possible case 1 and with the root value
        return Math.max(possibleMaxSum1,root.val);
    }

    // this will find the max sum using above function
    public static int maxPathSum(TreeNode root) {
        // calling the above function
        maxSum(root);
        // returning the max sum
        return maxSumFromAnyNodeToAnyNode;
    }

    // leetcode 1123
    // In this we need to find the lca of all the deepest level child
    // variable to store the lca of the deepest level
    static TreeNode lca = null;
    // variable to store the level of that lca
    static int lcaLevel = -1;
    // function to find the lca of the deepest leaf
    public static int lcaDeepestLeaf(TreeNode root,int level){
        // this is the base case when root is null we will return -1
        if(root==null){
            return -1;
        }
        // check for the leaf node
        if(root.left==null&&root.right==null){
            // now if level of the current node is greater than the level of lca then we will update lca
            if(lcaLevel<level){
                // updating the lca
                lca = root;
                // updating the level of the lca
                lcaLevel = level;
            }
            // returning the level of the leaf node
            return level;
        }
        // calling the recursion for both child
        int left = lcaDeepestLeaf(root.left, level+1);
        int right = lcaDeepestLeaf(root.right, level+1);
        // now if they both returning the same value that means this is the lca of the deepest level
        if(left==right){
             // checking the level of the lca
            if(lcaLevel<left){
                // updating the lca level
                lcaLevel = left;
                // updating the lca
                lca = root;
            }
        }
        // now returning the max level  between the left and right
        return Math.max(left,right);
    }

    // this function finding the lca of the deepest level using the above function
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        // calling the above function
       lcaDeepestLeaf(root, 0);
       // returning the result
       return lca;
    }


    // leetcode 987 
    // finding width of the tree 


    public static class pairVO implements Comparable<pairVO>{
        TreeNode node;
        int lvl;

        pairVO(TreeNode node,int lvl){
            this.node = node;
            this.lvl = lvl;
        }

        @Override
        public int compareTo(pairVO o){
            if(this.lvl==o.lvl){
                return this.node.val-o.node.val;
            }
            return this.lvl-o.lvl;
        }

    }

    public static void width(TreeNode root,int lvl,int[] leftMinValue,int[] rightMaxValue){
        // base case
        if(root==null){
            return;
        }
        // finding left min value
        leftMinValue[0] = Math.min(leftMinValue[0],lvl);
        // finding right max value
        rightMaxValue[0] = Math.max(rightMaxValue[0],lvl);
        // calling left and right recursion
        width(root.left, lvl-1, leftMinValue, rightMaxValue);
        width(root.right, lvl+1, leftMinValue, rightMaxValue);
    }

    // vertical order function
    public static void verticalOrderTraversal(TreeNode root, List<List<Integer>> ans){
        // base case
        if(root == null){
            return;
        }
        // finding the left min value and right max value  for width
        int[] leftMinValue = {0};
        int[] rightMaxValue =  {0};
        // finding the width of the tree
        width(root, 0, leftMinValue, rightMaxValue);
        // size of the tree
        int n = rightMaxValue[0]-leftMinValue[0]+1;
        // initializing the answer 
        for(int i=0;i<n;i++){
            ans.add(new ArrayList<>());
        }
        // initializing the parent queue and the child queue
        PriorityQueue<pairVO>  pque = new PriorityQueue<>();
        PriorityQueue<pairVO>  cque = new PriorityQueue<>();
        // adding the root node to the queue with their level
        pque.add(new pairVO(root,-leftMinValue[0]));
        // iterating over the parent queue
        while(!pque.isEmpty()){
            // finding the size of the parent queue
            int size = pque.size();
            // iterating over the size
            while(size-->0){
                // removing the node from the queue by their vertical level
                pairVO rPair = pque.poll();
                // adding the value to the queue
                ans.get(rPair.lvl).add(rPair.node.val);
                // adding child's to the child queue
                if(rPair.node.left !=null){
                    cque.add(new pairVO(rPair.node.left,rPair.lvl-1));
                }
                if(rPair.node.right !=null){
                    cque.add(new pairVO(rPair.node.right,rPair.lvl+1));
                }
            }
            // swapping the parent and child queue
            PriorityQueue<pairVO> temp = pque;
            pque = cque;
            cque = temp;
        }
    }
    // vertical traversal function which returns the list
    public static List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        verticalOrderTraversal(root, ans);
        return ans;
    }


}