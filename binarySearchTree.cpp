#include <iostream>
#include <vector>
using namespace std;
typedef vector<int> vi;

//class to represent a node
class TreeNode
{
public:
    int val;
    TreeNode *left;
    TreeNode *right;

    TreeNode(int val)
    {
        this->val = val;
        this->left = NULL;
        this->right = NULL;
    }
};
typedef vector<TreeNode *> vn;

//  displaying the tree
void display(TreeNode *root)
{
    // base case
    if (root == NULL)
    {
        return;
    }
    // storing result in str
    string str = root->left != NULL ? to_string(root->left->val) : ".";
    str += " <-- " + to_string(root->val) + " --> ";
    str += root->right != NULL ? to_string(root->right->val) : ".";
    cout << str << endl;
    // calling the recursion
    display(root->left);
    display(root->right);
}

// function to create the binary tree
TreeNode *constructBST(vi &arr, int si, int ei)
{
    // we willstop when the left index will became greater than the right index
    if (si > ei)
    {
        return NULL;
    }
    // finding the mid
    int mid = (si + ei) >> 1;
    // creating the new node
    TreeNode *node = new TreeNode(arr[mid]);
    // calling the left and right recursion with their parts in the array
    node->left = constructBST(arr, si, mid - 1);
    node->right = constructBST(arr, mid + 1, ei);
    // returning the node
    return node;
}

// basic =======================================================
int height(TreeNode *root)
{
    if (root == NULL)
    {
        return 0;
    }
    return max(height(root->left), height(root->right)) + 1;
}

int size(TreeNode *root)
{
    if (root == NULL)
    {
        return 0;
    }
    return size(root->left) + size(root->right) + 1;
}

bool find(TreeNode *root, TreeNode *data)
{
    TreeNode *curr = root;
    while (curr != NULL)
    {
        if (curr->val == data->val)
        {
            return true;
        }
        else if (curr->val < data->val)
        {
            curr = curr->right;
        }
        else
        {
            curr = curr->left;
        }
    }
    return false;
}

int maximum(TreeNode *root)
{
    if (root == NULL)
    {
        return -1e8;
    }
    TreeNode *curr = root;
    while (curr->right != NULL)
    {
        curr = curr->right;
    }
    return curr->val;
}
int minimum(TreeNode *root)
{
    if (root == NULL)
    {
        return 1e8;
    }
    TreeNode *curr = root;
    while (curr->left != NULL)
    {
        curr = curr->left;
    }
    return curr->val;
}

// leetcode 235 lca in bst===================================================
// lca in the bst is the node where p  and q either lies in the right and left or on the same side with current
// node matching with either p or q
TreeNode *lcaBST(TreeNode *root, TreeNode *p, TreeNode *q)
{
    // base case
    if (root == NULL)
    {
        return NULL;
    }
    // creating another node
    TreeNode *curr = root;
    // iterating untill  we have find the lca
    while (curr != NULL)
    {
        // if the p and q both lies on the left
        if ((curr->val > p->val && curr->val > q->val))
        {
            curr = curr->left;
        }
        else if (curr->val < p->val && curr->val < q->val)
        { // when p and q both lies on the right
            curr = curr->right;
        }
        else
        { // otherwise retuning current node
            return curr;
        }
    }
    // if we get paste the while means we didn't get the lca so it doesn't exist
    return NULL;
}

// returning the all number between a range
// inorder
void allDataInRange(TreeNode *root, TreeNode *p, TreeNode *q, vi &ans)
{
    // base case
    if (root == NULL)
    {
        return;
    }
    // calling left recursion
    allDataInRange(root->left, p, q, ans);
    // working in the inorder
    // checking if it belongs to the range
    if (root->val >= p->val && root->val <= q->val)
    {
        ans.push_back(root->val); // if it does the adding it to the answer
    }
    // calling the right recursion
    allDataInRange(root->right, p, q, ans);
}

// leetcode 98 validate binary tree
// so we can do simple inorder and check that it must be in increasing order
bool isValidBST(TreeNode *root, long &minimum)
{
    // base case
    if (root == NULL)
    {
        return true;
    }
    // calling the left recursion
    bool left = isValidBST(root->left, minimum);
    // working in the inorder
    // if the value of the root is greater than minimum then we will update the minimum
    if (root->val > minimum)
    {
        minimum = root->val; // updating the minimum 
    }
    else // else we will return false
    {
        return false;
    }
    // right recursion
    bool right = isValidBST(root->right, minimum);
    // now returning the true if and only if the both left and right are true
    return left && right;
}
// function to check the validity of bst
bool isValidBST(TreeNode *root)
{
    long minimum = INT32_MAX - 1e8; // declaring minimum
    return isValidBST(root, minimum); // returning the result
}

// leetcode 99 =====================================
// variable store the previous in prev, first wrong node into f and second wrong node in s
TreeNode *prev1 = NULL, *f = NULL, *s = NULL;
// variable which store whether the swapped happen or not
bool done = false;
// recover the function
void recover(TreeNode *root)
{
    // base case
    if (root == NULL || done)
    {
        return; 
    }
    // calling the  left recursion
    recover(root->left);
    // working in the inorder
    // we work only if the prev is not null
    if (prev1 != NULL)
    {
        // now if the first is not null
        if (f != NULL)
        {
            // then we check if the value in the prev1 is greater than the root value
            if (root->val < prev1->val)
            {
                // then we updating the second variable to store the second wrong variable
                s = root;
                // and making the done true
                done = true;
            }
        }
        else // now if the f is NULL then 
        {
            // if the prev calue is greater than 
            if (root->val < prev1->val)
            {
                // then we will update the first to the prev
                f = prev1;
                // and we will set the s to the root because there may be chances that the second wrong node is
                // the root node itself
                s = root;
            }
        }
    }// updating the previous to the next prev node
    prev1 = root;
    // and then calling the right recursion
    recover(root->right);
}
// calling the function to recover the tree
void recoverTree(TreeNode *root)
{
    // calling the function
    recover(root);
    // swapping the wrong value
    if (prev1 != NULL)
    {
        int temp = f->val;
        f->val = s->val;
        s->val = temp;
    }
}

// predecessor for BST
void predAndSucc(TreeNode* root,TreeNode* data,vn& predSucc){
    if(root==NULL){
        return;
    }
    TreeNode* curr = root;
    while(curr!=NULL){
        
    }
}

// given inorder of the binary tree create binary tree using this
// we will make sure at each node that the curr node lie between the it's range  if not then we will return null
TreeNode* createBSTfromPreOrder(vector<int>& arr,int& idx,int ll,int rl){
    // base case when index of the array exceeds the size of the array
    if(idx>=arr.size()){
        return NULL;
    }
    // checking that the node is lying in the range or not if not then we will return null
    if(arr[idx]<ll || arr[idx]>rl){
        return NULL;
    }
    // creating the node
    TreeNode* node = new TreeNode(arr[idx]);
    // incrementing the idx
    idx++;
    // creating the right and left sub tree
    node->left = createBSTfromPreOrder(arr,idx,ll,node->val);
    node->right = createBSTfromPreOrder(arr,idx,node->val,rl);
    // return the  node
    return node;
}

int heightTreeUsingPreOrder(vi& arr,int& idx,int ll,int rl){
    // base case when index of the array exceeds the size of the array
    if(idx>=arr.size()){
        return -1;
    }
    // checking that the node is lying in the range or not if not then we will return null
    if(arr[idx]<ll || arr[idx]>rl){
        return -1;
    }
    // rest code same just we don't need to create node
    int ele = arr[idx];
    idx++;
    int l = heightTreeUsingPreOrder(arr,idx,ll,ele);
    int r = heightTreeUsingPreOrder(arr,idx,ele,rl);
    return max(l,r)+1;
}

// adding node to the tree
void addNodeInBST(TreeNode* root,TreeNode* p){
    // temp variables
    TreeNode* temp = root,*prev=NULL;
    // iterating untill we get the end
    while(temp!=NULL){
        // storing current node into previous
        prev = temp;
        // checking conditions
        if(temp->val>p->val){
            temp = temp->left;
        }else{
            temp = temp->right;
        }
    }
    // adding node
    if(prev->val>p->val){
        prev->left = p;
    }else{
        prev->right = p;
    }
}

// removing the node using iterative approach
TreeNode* removeNode(TreeNode* root,int p){
    // creating temporary variable
    TreeNode* curr = root,*prev = NULL;
    // finding the node that needs to be removed
    while(curr->val!=p){
        // if we didn't get the node then we will return the root as it is
        if(curr==NULL){
            return root;
        }
        // maitaining the parent in the prev
        prev =  curr;
        // if p is greater than the root then we will move right other wise left
        if(curr->val<p){
            curr = curr->right;
        }else{
            curr = curr->left;
        }
    }  
    // now if the right child of curr is null then we just need to add the left to the parent of the curr
    if(curr->right==NULL){
        // finding where to connect the left on right or left
        if(prev->val>p){
            prev->left = curr->left;;
        }else{
            prev->right = curr->left;
        }
    }else{
        // if the right is not null then we are trying to find the max of the right and then
        // replacing it with node that needs to be removed
        TreeNode* temp = curr->right,*prev2=curr;
        // finding the max of the right child of the current
        while(temp->left!=NULL){
            prev2 = temp;
            temp = temp->left;
        }
        // now assigning the right of the max child to the left of the parent of the max
        prev2->left = temp->right;
        // now assigning the childs of the removing node to the max node
        temp->left = curr->left;
        temp->right = curr->right;
        // finding where to attach the max node from the parent
        if(prev->val>p){
            prev->left = temp;
        }else{
            prev->right = temp;
        }
    }
}

// recursive approaches of the adding and removing the node
TreeNode* addNodeInBstRecursive(TreeNode* root,int p){
    // root null means we reach the place where we need to add the new node
    if(root==NULL){
        return new TreeNode(p);
    }
    // if p has value greater than the root than going right otherwise left
    if(root->val>p){
        root->left = addNodeInBstRecursive(root->left,p);
    }else{
        root->right = addNodeInBstRecursive(root->right,p);
    }
    // returning the root
    return root;
}

// removing node using recursion
TreeNode* removeNodeInBstRecursive(TreeNode* root,int p){
    if(root == NULL){
        return NULL;
    }

    if(root->val>p){
        root->left = removeNodeInBstRecursive(root->left,p);
    }else if(root->val<p){
        root->right = removeNodeInBstRecursive(root->right,p);
    }else{
        if(root->left==NULL || root->right==NULL){
            return root->left!=NULL?root->left:root->right;
        }
        int maxInLeft = maximum(root->left);
        root->val = maxInLeft;
        root->left = removeNodeInBstRecursive(root->left,maxInLeft);
    }
    return root;
}
void bst(){
    vi arr = {50,17,9,14,12,23,19,76,54,72,67};
    int idx = 0;
    TreeNode* root = createBSTfromPreOrder(arr,idx,-1e8,1e8);
    display(root);
    idx = 0;
    cout<<"height : "<<heightTreeUsingPreOrder(arr,idx,-1e8,1e8)<<endl;
    // addNodeInBST(root,new TreeNode(69));
    removeNode(root,17);
    display(root);
}


void solve()
{
    vi arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    TreeNode *root = constructBST(arr, 0, 15);
    display(root);
    cout << endl;
    cout << size(root) << endl;
    cout << height(root) << endl;
    cout << find(root, new TreeNode(18)) << endl;
    cout << maximum(root) << endl;
    cout << minimum(root) << endl;
    cout << lcaBST(root, new TreeNode(14), new TreeNode(15))->val << endl;
    vi ans;
    allDataInRange(root, new TreeNode(5), new TreeNode(11), ans);
    for (int i : ans)
    {
        cout << i << " ";
    }
}

int main()
{
    // solve();
    bst();
    return 0;
}