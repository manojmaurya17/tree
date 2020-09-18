// importing array
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class basic{

    public static void main(String[] args){
        solve();
    }
    // Class Node 
    public static class Node{
        int data;
        Node left = null;
        Node right = null;
        
        Node(int data){
            this.data = data;
        }
    }
    // variable to index the array
    static int idx = 0;
    // constructing  tree from the array
    public static Node constructTree(int[] arr){
        //  base condition checking if the value at this index is -1 or idx is out of bound
        if(idx>=arr.length || arr[idx]==-1){
            // then incrementing the index
            idx++;
            // and returning null
            return null;
        }
        // creating new node 
        Node node = new Node(arr[idx++]);
        // calling create tree to their right and left child
        node.left = constructTree(arr);
        node.right = constructTree(arr);
        // and then returning the node
        return node;
    }

    // displaying the tree
    public static void display(Node root){
        System.out.print(root.left!=null?root.left.data:".");
        System.out.print(" <- "+root.data+" -> ");
        System.out.println(root.right!=null?root.right.data:".");
        if(root.left!=null){
            display(root.left);
        }
        if(root.right!=null){
            display(root.right);
        }
    }

    // basic questions ====================================================================
    // we will do the basic questions

    // finding the number of nodes in the tree
    // we will have the faith that the left and right child will give the number of nodes in their subtree and
    // I will add one to it and return it which is the number of nodes in the current root tree
    public static int size(Node node){
        // base case returning the zero 
        if(node==null){
            return 0;
        }  
        //  calling left and right recursion for the left and right child
        int leftTreeSize = size(node.left);
        int rightTreeSize = size(node.right);
        // we will return the sum of the right  plus left plus one
        return leftTreeSize+rightTreeSize+1;
    }

    // finding the heigh of the tree 
    // In this the child will give their heights and we will find the max height between them
    // and we will; return our height by adding one to the max height
    public static int height(Node node){
        // base case we returning 0 to start height from 1 and  returning -1 to start height from 0
        if(node==null){
            return 0; // return -1 for height w.r.t to edge and  return 0 for height w.r.t to node 
        }
        // calling left recursion and right recursion
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);
        // finding max height and then returning height from current root by adding one to the max height
        return Math.max(leftHeight,rightHeight)+1;
    }

    // we can find the maximum by two method one by using a static variable with void type recursion, in this case we will compare this static variable
    // with each and every node and update static variable if it is smaller than the current node
    // now second approach can be return type recursion, in this we will return the max of the subtrees of both the child
    // and then we will compare the left max, right max and the current value and we will return the max of these three
    // below is onl;y one approach
    public static int maximum(Node node){
        // base case returning negative infinity
        if(node==null){
            return (int)-1e6;
        }
        // calling left and right recursion
        int leftMax = maximum(node.left);
        int rightMax = maximum(node.right);
        // finding the max of these three and returning it
        return Math.max(Math.max(leftMax,rightMax),node.data);
    }
    
    // here we are finding the minimum value in the tree
    // now to find minimum also we have the two approach first is using static variable with the void type
    // and the second one is using the return type recursion then finding minimum between them
    // below is the only one approach
    public static int minimum(Node node){
        // this is the base case in which we are retuning the positive infinity
        if(node==null){
            return (int)1e6;
        }
        // calling left and right recursion
        int leftMax = minimum(node.left);
        int rightMax = minimum(node.right);
        // finding minimum among them
        return Math.min(Math.min(leftMax,rightMax),node.data);
    }


    // finding node in the tree
    // In this function we are particularly wants the true or false that node is present or not
    // so in this function first we will check if it is the current node or not
    // if not then we will use return type and ask our child if they have this value in their subtree
    // if they have they will return true and as any one of the child return true then we will not call any recursion further
    // and if they don't have then they will return the false
    public static boolean find(Node node,int data){
        // this is the base case in which we will return false that means we didn't get the result
        if(node==null){
            return false;
        }
        // now in this case we are checking if the required value matches with me or not
        // if it matches then retuning true
        if(node.data==data){
            return true;
        }
        // now it means required value doesn't match with the current root so we will now ask the child
        // if they have this value in their subtree and if they do have they will return the true other wise they
        // will return the  false
        // variable to store the  answer of the child call, initially it is false
        boolean res = false;
        // calling left recursion and applying or operation with the res
        // Note - OR operation have the property that if the first value is true then it will not bother to check the second
        //        condition if it is true or not so if we get true from any of the child then it will make res true
        //        and then it will check OR operation between res variable and any right or left call in any stage of recursion
        //        and since res became true then it will never call any recursion further
        res = res || find(node.left,data);
        // same with the right recursion
        res = res || find(node.right,data);
        // and then returning the res
        return res;
    }
    
    // Traversal ===================================================
     // Pre order, Post order and In order traversal are very  easy we just need to check the base case and then 
     // we just need to call the recursion for the child with printing the current node according to the traversal we are  using
     // for pre we will print the node before the recursion call, for in order traversal we will print the node after calling 
     // the left recursion and before calling the right recursion and for post order we will print the value after calling
     // the both recursion

     // this is the preorder traversal
     public static void preOrder(Node node){
         if(node == null){
             return;
            }
            System.out.print(node.data+" ");
            preOrder(node.left);
            preOrder(node.right);
        }
    // this is the Inorder traversal
    public static void inOrder(Node node){
        if(node == null){
            return;
        }
        inOrder(node.left);
        System.out.print(node.data+" ");
        inOrder(node.right);
    }
    // this is the Postorder traversal
    public static void postOrder(Node node){
        if(node == null){
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.data+" ");
    }

    // Finding the root to node path for a target node we will return the path in the array list
    // 
    public static ArrayList<Node> rootToNodePath(Node node, int data){
        if(node==null){
            ArrayList<Node> base = new ArrayList<>();
            return base;
        }
        ArrayList<Node> myAns = new ArrayList<>();
        if(node.data==data){
            myAns.add(node);
            return myAns;
        }
        ArrayList<Node> leftPath = rootToNodePath(node.left,data);
        if(leftPath.size()>0){
            myAns.add(node);
            for(Node n:leftPath){
                myAns.add(n);
            }
            return myAns;
        }

        ArrayList<Node> rightPath = rootToNodePath(node.right,data);
        if(rightPath.size()>0){
            myAns.add(node);
            for(Node n:rightPath){
                myAns.add(n);
            }
            
        }
        return myAns;
        }
    
    public static boolean rootToNodePathVoid(Node node,int data,ArrayList<Node> path){
        if(node==null){
            return false;
        }

        if(node.data == data){
            path.add(node);
            return true;
        }
        boolean res = rootToNodePathVoid(node.left,data,path) || rootToNodePathVoid(node.right,data,path);
        if(res){
            path.add(node);
        }
        return res;
    }

    public static void set1(Node root){
        ArrayList<Node> res = new ArrayList<>();
        // ArrayList<Node> res = rootToNodePath(root,90);
        rootToNodePathVoid(root,90,res);
        for(Node n:res){
            System.out.print(n.data+" ");
        }
    }

    // leaf to leaf node max sum
    // we need to find the max sum between leaf to leaf node in a tree

    // variable to store the max sum between the a leaf to leaf node
    static int maxSum = Integer.MIN_VALUE;
    // function to find the max sum from a particular root to leaf
    // and by using this function we are finding the max sum between two leaf nodes 
    public static int leafToLeafNodeMaxSUm(Node root){
        // this is the base case means when we have root is null then max sum from root to leaf
        // will be negative infinity
        if(root==null){
            return Integer.MIN_VALUE;
        }
        // this is the check that it is a leaf node we are considering
        if(root.left==null && root.right==null){
            return root.data;
        }
        // calling the left tree and right tree
        int left = leafToLeafNodeMaxSUm(root.left);
        int right = leafToLeafNodeMaxSUm(root.right);
        // now checking if we got the result from the both child's
        // that means we have sum from child node to their leaf which has max sum
        // so we just need to add them with the value of current node
        // which will store the max sum in the variable maxSum
        if(left!=Integer.MIN_VALUE && right!=Integer.MIN_VALUE){ // checking leaf to leaf
            maxSum = Math.max(maxSum,left+right+root.data); // finding max sum
        }
        // returning the max sum between the current root to its leaf which has max sum
        return Math.max(left,right)+root.data;
    }


    /// level order traversal =========================================================
    //  basic algorithm:
    //  1. Create a queue
    //  2. add root to queue
    //  3. iterate over queue untill it's size is zero
    //    3.1 remove first node and process it
    //    3.2 add all of it's children into the  queue

    // level order traversal basic one
    // In this we are printing the level order traversal this is the basic approach, first we will create the queue and add the root into this queue,
    // now we will iterate over the queue untill it gets empty then in the loop first we will remove the first element
    // in the queue then we check if their child exist or not and if they exist then we will add them to the queue
    public static void levelOrder01(Node root){
        // initializing the queue using the linked list
        LinkedList<Node> que = new LinkedList<>();
        // adding the root node to the queue
        que.addLast(root);
        // iterating over the queue untill it gets empty
        while(!que.isEmpty()){
            // removing first value from the queue
            Node rmNode = que.removeFirst();
            // printing the current node
            System.out.print(rmNode.data+" ");
            // if left exist then adding it to the queue
            if(rmNode.left!=null){
                que.addLast(rmNode.left);
            }
            // if right exist then adding it to the queue
            if(rmNode.right!=null){
                que.addLast(rmNode.right);
            }
        }
    }

    // level order traversal with two queues to print with level
    // In this we are going to print the level order using two queues, first queue is the parent queue and second queue is the
    // child queue, now first we are adding the root to the parent and then iterating over the queue untill it gets empty,
    // first we remove the first node from the queue and then if the child of the removed node exist then we will
    // add the child of the root node to the child queue, now in the loop itself if we got the parent queue empty then
    // we will swap the parent queue with the child queue
    public static void levelOrder02(Node root){
        // creating parent queue
        LinkedList<Node> pQue = new LinkedList<>();
        // creating child queue
        LinkedList<Node> cQue = new LinkedList<>();
        //  adding root to the parent queue
        pQue.addLast(root);
        // level count
        int count = 0;
        // printing on which level we are
        System.out.print("\nLevel - "+count+" --> ");
        // iterating over the queue
        while(!pQue.isEmpty()){
            // removing first node from the parent queue
            Node rNode = pQue.removeFirst();
            // printing the node data
            System.out.print(rNode.data+" ");
            // if left child is not null then add it to the child queue
            if(rNode.left!=null){
                cQue.addLast(rNode.left);
            }
            // if right child is not null then add it to the child queue
            if(rNode.right!=null){
                cQue.addLast(rNode.right);
            }
            // checking if parent queue is empty or not and if it is empty then we will swap the 
            // parent queue with the child queue
            if(pQue.isEmpty()){
                // swapping the queue
                LinkedList<Node> temp = pQue;
                pQue = cQue;
                cQue = temp;
                // printing the next level
                System.out.print("\nLevel - "+count+" --> ");
                // incrementing the level count
                count++;
            }
        }
    }
    // level order traversal with one queues with level using null

    // In this we are printing the level order traversal along with it's level, in this we are adding null  after the one level 
    // ends like in the first level root is the only node so we will add null after this then we will adding it's child
    // to queue and then after second level end we will add null and so on , rest of the approach is same as above
    public static void levelOrder03(Node root){
        // creating queue to store the node
        LinkedList<Node> pQue = new LinkedList<>();
        // adding root to the queue
        pQue.addLast(root);
        // adding null to the queue
        pQue.addLast(null);
        // level count
        int count = 0;
        // printing the level
        System.out.print("\nLevel - "+count+" --> ");
        // iterating untill we get the queue empty
        while(pQue.size()!=1){
            // removing the first node from the queue
            Node rNode = pQue.removeFirst();
            // printing the node data
            System.out.print(rNode.data+" ");
            // checking left child and if it is not empty then adding it to the queue
            if(rNode.left!=null){
                pQue.addLast(rNode.left);
            }
            // checking right child and if it is not empty then adding it to the queue
            if(rNode.right!=null){
                pQue.addLast(rNode.right);
            }
            // checking if the current node at first place is null or not
            // and if it is null then removing it from first place and adding it to the last 
            pQue.removeFirst();
                if(pQue.getFirst()==null){
                pQue.addLast(null);
                // printing the next level
                System.out.print("\nLevel - "+count+" --> ");
                // incrementing the level count
                count++;
            }
        }
    }

    // In this we are printing the level order traversal using two while loop one for the level and one for the nodes in the level
    public static void levelOrder04(Node root){
        // creating the queue
        LinkedList<Node> que = new LinkedList<>();
        // adding root to the queue
        que.addLast(root);
        // level count
        int count = 0;
        // iterating over the queue
        while(!que.isEmpty()){
            // finding current size of the queue
            int size = que.size();
            // printing the level
            System.out.print("\nLevel - "+count+"-->");
            // incrementing the count of the level
            count++;
            // iterating over the size of the queue
            while(size-->0){
                // removing first node from queue
                Node rNode = que.removeFirst();
                // printing it
                System.out.print(rNode.data+" ");
                // if child exist then adding them to the queue
                if(rNode.left!=null){
                    que.addLast(rNode.left);
                }
                if(rNode.right!=null){
                    que.addLast(rNode.right);
                }

            }
        }
    }
    
    public static void levelOrder(Node root){
        
        levelOrder01(root);
        levelOrder02(root);
        levelOrder03(root);
        levelOrder04(root);
    }



    // views ======================================================

    // left view of the tree All the nodes that can be seen from the left
    public static void leftView(Node root){
        // initializing the que
        LinkedList<Node> que = new LinkedList<>();
        // adding root to the queue
        que.add(root);
        // iterating over the queue until it gets empty
        while(!que.isEmpty()){
            // getting the size of the queue
            int size = que.size();
            // we can print the node at this position or in the starting of the while loop but
            // at this position it's good
            System.out.print(que.getFirst().data+" ");
            // iterating over the size
            while(size-->0){
                // removing the first node from the queue
                Node rNode = que.removeFirst();
                // checking the if left and right tree exist and if they exist then we add them in the queue
                if(rNode.left!=null){
                    que.add(rNode.left);
                }
                if(rNode.right!=null){
                    que.add(rNode.right);
                }
            }
        }
    }
    
    // this right view is same we just need to reverse the  call such that last node becomes first node
    // and we can use the above code as it is by just reversing the line which adds child to the queue
    // means first we will add the right child then we will add the left
    public static void rightView01(Node root){
        // initializing the que
        LinkedList<Node> que = new LinkedList<>();
        // adding root to the queue
        que.add(root);
        // iterating over the queue until it gets empty
        while(!que.isEmpty()){
            // getting the size of the queue
            int size = que.size();
            // we can print the node at this position or in the starting of the while loop but
            // at this position it's good
            System.out.print(que.getFirst().data+" ");
            // iterating over the size
            while(size-->0){
                // removing the first node from the queue
                Node rNode = que.removeFirst();
                // checking the if left and right tree exist and if they exist then we add them in the queue
                if(rNode.right!=null){
                    que.add(rNode.right);
                }
                if(rNode.left!=null){
                    que.add(rNode.left);
                }
            }
        }
    }
    
    // left view of the tree All the nodes that can be seen from the left
    public static void rightView02(Node root){
        // initializing the que
        LinkedList<Node> que = new LinkedList<>();
        // adding root to the queue
        que.add(root);
        // iterating over the queue until it gets empty
        while(!que.isEmpty()){
            // getting the size of the queue
            int size = que.size();
            // iterating over the size
            // variable perv to store the last node of the level
            Node prev = null;
            while(size-->0){
                // removing the first node from the queue
                Node rNode = que.removeFirst();
                // checking the if left and right tree exist and if they exist then we add them in the queue
                if(rNode.left!=null){
                    que.add(rNode.left);
                }
                if(rNode.right!=null){
                    que.add(rNode.right);
                }
                prev = rNode;
            }
            // printing the last node of the level
            System.out.print(prev.data+" ");
        }
    }

    // vertical level order traversal
    // In this we will find the level order traversal in vertical direction
    // we need to store level with each node so we will create another class which stores node and level
    public static class pairVO{
        Node n; // node
        int lvl; // vertical level of the node
        pairVO(Node n,int lvl){
            this.n = n;
            this.lvl = lvl;
        }
    }
    // calculating the width of the tree using variable leftMinValue and rightMaxValue
    // leftMinValue will store the vertical level of the left most node with respect to the root
    // rightMaxValue will store the vertical level of the right most node with respect to the root
    public static void width(Node root,int lvl,int[] leftMinValue,int[] rightMaxValue){
        // base case
        if(root == null){
            return;
        }
        // calculating leftMinValue by comparing it with the level
        leftMinValue[0] = Math.min(leftMinValue[0],lvl);
        // calculating rightMaxValue by comparing it with the level
        rightMaxValue[0] = Math.max(rightMaxValue[0],lvl);
        // calling left and right recursion 
        width(root.left, lvl-1, leftMinValue, rightMaxValue);
        width(root.right, lvl+1, leftMinValue, rightMaxValue);
    }

    // vertical level order function
    public static void VerticalOrder(Node root){
        // creating leftMinValue variable
        int[] leftMinValue = {0};
        // creating rightMaxValue variable
        int[] rightMaxValue = {0};
        // calculating width of the tree
        width(root, 0, leftMinValue, rightMaxValue);
        // creating arraylist of arraylist to store the vertical order of the tree
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        // initializing the arraylist
        for(int i=0;i<rightMaxValue[0]-leftMinValue[0]+1;i++){
            ans.add(new ArrayList<>());
        }
        // initializing the queue
        LinkedList<pairVO> que = new LinkedList<>();
        // adding the root to the queue
        que.addLast(new pairVO(root,-leftMinValue[0]));
        // iterating over the queue
        while(!que.isEmpty()){
            // removing the first node from the queue
            pairVO rPair = que.removeFirst();
            // if left or right of the node exist then we will add them to the queue
            if(rPair.n.left!=null){
                que.addLast(new pairVO(rPair.n.left,rPair.lvl-1));
            }
            if(rPair.n.right!=null){
                que.addLast(new pairVO(rPair.n.right,rPair.lvl+1));
            }
            // adding node in the arraylist according to its level
            ans.get(rPair.lvl).add(rPair.n.data);
        }

        // printing the answer
        for(ArrayList<Integer> a:ans){
            for(int v:a){
                System.out.print(v+" ");
            }
            System.out.println();
        }
    }

    // function to find the vertical level order sum
    public static void VerticalOrderSum(Node root){
        // variable to store the left minimum value and right maximum value
        int[] leftMinValue = {0};
        int[] rightMaxValue = {0};
        // finding width of the tree
        width(root, 0, leftMinValue, rightMaxValue);
        // initializing array of the length of the width of the tree
        int[] ans = new int[rightMaxValue[0]-leftMinValue[0]+1];
        // initializing the queue
        LinkedList<pairVO> que = new LinkedList<>();
        // adding root node to the queue
        que.addLast(new pairVO(root,-leftMinValue[0]));
        // iterating over the queue until it gets empty
        while(!que.isEmpty()){
            // removing first node from the queue
            pairVO rPair = que.removeFirst();
            // if left and right of the node exist then adding them to the queue
            if(rPair.n.left!=null){
                que.addLast(new pairVO(rPair.n.left,rPair.lvl-1));
            }
            if(rPair.n.right!=null){
                que.addLast(new pairVO(rPair.n.right,rPair.lvl+1));
            }
            // adding value to their vertical level
            ans[rPair.lvl]+=rPair.n.data;
        }

        // printing the sum of each level
        for(Integer a:ans){
            System.out.println(a+" ");
        }
    }

    // bottom view of the tree
    // In which we will store the last element of the vertical order at each  level
    public static void bottomView(Node root){
        // base case
        if(root==null){
            return;
        }
        // finding leftMinValue and rightMaxValue using width function
        int[] leftMinValue = {0};
        int[] rightMaxValue = {0};
        // finding values
        width(root, 0, leftMinValue, rightMaxValue);
        // n is the width of the tree
        int n = rightMaxValue[0]-leftMinValue[0]+1;
        // initializing array
        int[] ans = new int[n];
        // initializing the queue
        LinkedList<pairVO> que = new LinkedList<>();
        // adding first element to the queue with it's vertical order
        que.addLast(new pairVO(root,-leftMinValue[0]));
        // iterating over the queue until it gets empty
        while(!que.isEmpty()){
            // removing first node from the queue
            pairVO rPair = que.removeFirst();
            // updating the ans, at the end it will contain the last value of the each vertical order
            ans[rPair.lvl] = rPair.n.data;
            // adding left and right child
            if(rPair.n.left!=null){
                que.addLast(new pairVO(rPair.n.left,rPair.lvl-1));
            }
            if(rPair.n.right!=null){
                que.addLast(new pairVO(rPair.n.right,rPair.lvl+1));
            }
        }
        // printing the answer
        for(int i:ans){
            System.out.print(i+" ");
        }

    }

    // top view of the tree , printing all the node which we can see from the top of the tree
    public static void topView(Node root){
        // base case
        if(root==null){
            return;
        }
        // calculating leftMinValue and rightMaxValue using width function
        int[] leftMinValue = {0};
        int[] rightMaxValue = {0};
        width(root, 0, leftMinValue, rightMaxValue);
        // this is the width of the tree
        int n = rightMaxValue[0]-leftMinValue[0]+1;
        // initializing the answer array
        int[] ans = new int[n];
        // filling the array with the negative infinity
        Arrays.fill(ans,(int)-1e8);
        // initializing the queue
        LinkedList<pairVO> que = new LinkedList<>();
        // add root with it's level to the queue
        que.addLast(new pairVO(root,-leftMinValue[0]));
        // iterating over the  queue until it gets empty
        while(!que.isEmpty()){
            // removing first node from the queue
            pairVO rPair = que.removeFirst();
            // updating answer only if it's not updated  before
            if(ans[rPair.lvl]==(int)-1e8){
                ans[rPair.lvl] = rPair.n.data;
            }
            // adding left and right child to the queue
            if(rPair.n.left!=null){
                que.addLast(new pairVO(rPair.n.left,rPair.lvl-1));
            }
            if(rPair.n.right!=null){
                que.addLast(new pairVO(rPair.n.right,rPair.lvl+1));
            }
        }
        // printing the answer 
        for(int i:ans){
            System.out.print(i+" ");
        }

    }

    // diagonal view of the tree
    // for the diagonal we should have new width function which give us the exact width diagonal wise
    public static void diagonalWidth(Node root,int lvl,int[] leftMinValue){
        // base case
        if(root==null){
            return;
        }
        // updating minimum left value
        leftMinValue[0] = Math.min(leftMinValue[0],lvl);
        // calling left with decreasing from 1
        diagonalWidth(root.left, lvl-1, leftMinValue);
        // calling right without changing lvl
        diagonalWidth(root.right, lvl, leftMinValue);

    }
    public static void diagonalView(Node root){
        // base case
        if(root == null){
            return;
        }
        // finding leftMinValue and  using width function
        int[] leftMinValue = {0};
        //  calculating the width
        diagonalWidth(root, 0, leftMinValue);
        // this is the length of the answer
        int n = -leftMinValue[0]+1;
        // initializing the answer
        ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
        // initializing the arraylist
        for(int i=0;i<n;i++){
            ans.add(new ArrayList<>());
        }
        // initializing the queue
        LinkedList<pairVO> que = new LinkedList<>();
        // adding the root to the queue with it's level
        que.addLast(new pairVO(root,n-1));
        // iterating over the queue untill it gets empty
        while(!que.isEmpty()){
            // removing first node from the queue
            pairVO rPair = que.removeFirst();
            // adding current node to ans with it's level
            ans.get(rPair.lvl).add(rPair.n.data);
            // adding left and right child
            if(rPair.n.left!=null){
                que.addLast(new pairVO(rPair.n.left,rPair.lvl-1));
            }
            if(rPair.n.right!=null){
                que.addLast(new pairVO(rPair.n.right,rPair.lvl));
            }
        }
        // printing the answer
        for(ArrayList<Integer> ar:ans){
            for(int ele:ar){
                System.out.print(ele+" ");
            }
            System.out.println();
        }
    }

    // finding the diagonal view
    public static void diagonalSum(Node root){
        // base case
        if(root == null){
            return;
        }
        // finding leftMinValue and  using width function
        int[] leftMinValue = {0};
        //  calculating the width
        diagonalWidth(root, 0, leftMinValue);
        // this is the length of the answer
        int n = -leftMinValue[0]+1;
        // initializing the answer
        int[] ans = new int[n];
        // initializing the arraylist
        // initializing the queue
        LinkedList<pairVO> que = new LinkedList<>();
        // adding the root to the queue with it's level
        que.addLast(new pairVO(root,n-1));
        // iterating over the queue untill it gets empty
        while(!que.isEmpty()){
            // removing first node from the queue
            pairVO rPair = que.removeFirst();
            // adding current node to ans with it's level
            ans[rPair.lvl] += rPair.n.data;
            // adding left and right child
            if(rPair.n.left!=null){
                que.addLast(new pairVO(rPair.n.left,rPair.lvl-1));
            }
            if(rPair.n.right!=null){
                que.addLast(new pairVO(rPair.n.right,rPair.lvl));
            }
        }
        // printing the answer
        for(int ele:ans){
            System.out.println(ele+" ");
        }
    }

    /// =============================================================
    // linearized the tree means attaching all nodes in the single left column of the root node
    public static Node linearisingTree(Node root){
        // base case 
        if(root == null){
            return null;
        }
        // if we get the root node we will return it as it is
        if(root.left==null && root.right==null){
            return root;
        }
        // soliciting the linearized tree from the left and right subtree
        Node left = linearisingTree(root.left);
        Node right = linearisingTree(root.right);
        // moving to the left most node of the left tree
        while(left.left!=null){
            left = left.left;
        }
        // and finally assigning the right to the left of left tree when it is null
        left.left = right;
        // making the right child null because we want a linear child
        root.right = null;
        return root;
    }

    public static Node doublyLinedListOfTree(Node root){
        // base case 
        if(root == null){
            return null;
        }
        // if we get the root node we will return it as it is
        if(root.left==null && root.right==null){
            return root;
        }
        // soliciting the linearized tree from the left and right subtree
        Node left = doublyLinedListOfTree(root.left);
        Node right = doublyLinedListOfTree(root.right);
        // moving to the left most node of the left tree
        while(left.left!=null){
            left = left.left;
        }
        // and finally assigning the right to the left of left tree when it is null
        left.left = right;
        // and assigning right of th right to the left to make it doubly linked list
        if(right!=null)
        right.right = left;
        // adding back edge
        root.left.right = root;
        root.right = null;
        return root;
    }

    public static Node displayDoublyLeft(Node root){
        if(root.left==null){
            System.out.println(root.data+" ");
            return root;
        }
        System.out.println(root.data+" ");
        return displayDoublyLeft(root.left);
    }

    public static void displayDoublyRight(Node root){
        if(root==null){
            return;
        }
        System.out.println(root.data+" ");
        displayDoublyRight(root.right);
    }



    public static void linear(Node root){
        // Node ans = linearisingTree(root);
        // System.out.println();
        // display(ans);
        Node ans = doublyLinedListOfTree(root);
        System.out.println();
        Node right = displayDoublyLeft(ans);
        System.out.println();
        displayDoublyRight(right);

    }


    // successor and predessor
    public static void predAndSucc(Node root,Node data,Node[] predSucc){
        if(root==null){
            return;
        }
        predAndSucc(root.left,data,predSucc);
        if(predSucc[0]!=null && root.data==data.data){
            predSucc[1] = predSucc[0];
        }
        if(predSucc[0]!=null && predSucc[0].data==data.data){
            predSucc[2] = root;
        }
        predSucc[0] = root;
        predAndSucc(root.right,data,predSucc);
    }

    public static void view(Node root){
        // leftView(root);
        // System.out.println();
        // rightView01(root);
        // System.out.println();
        // rightView02(root);
        // System.out.println();
        // VerticalOrder(root);
        // System.out.println();
        // VerticalOrderSum(root);
        // System.out.println();
        // bottomView(root);
        // System.out.println("\n");
        // topView(root);
        // System.out.println("\n");
        // diagonalView(root);
        // System.out.println("\n");
        // diagonalSum(root);
        Node[] pred = {null,null,null};
        predAndSucc(root, new Node(6), pred);
        System.out.println((pred[1]!=null?pred[1].data:"null")+" "+(pred[2]!=null?pred[2].data:"null"));
    }


    public static void solve(){
        // int[] arr = {10,20,40,-1,-1,50,80,-1,-1,90,-1,-1,30,60,100,-1,-1,-1,70,110,-1,-1,120,-1,-1};
        int[] arr = {1,2,3,-1,-1,4,5,-1,-1,-1,6,7,-1,8,9,10,-1,-1,-1,-1,11,12,-1};
        Node root = constructTree(arr);
        display(root);

        // basic question's
        // System.out.println(size(root));
        // System.out.println(height(root));
        // System.out.println(maximum(root));
        // System.out.println(minimum(root));
        // System.out.println(find(root,11));

        // traversal
        // preOrder(root);
        // System.out.println();
        // inOrder(root);
        // System.out.println();
        // postOrder(root);
        // set1(root);
        // levelOrder(root);
        view(root);
        // linear(root);
    }
}