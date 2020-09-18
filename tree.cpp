#include<iostream>
#include<vector>
using namespace std;
typedef vector<int> vi;

//class to represent a node
class TreeNode{
    public:
    int val;
    TreeNode* left;
    TreeNode* right;

    TreeNode(int val){
        this->val = val;
        this->left = NULL;
        this->right = NULL;
    }
};
typedef vector<TreeNode*> vn;


// creating tree
int idx = 0;
//  function to create the tree
TreeNode* createTree(vi &arr){
    // base case
    if(idx>=arr.size() || arr[idx]==-1){
        // incrementing the index
        idx++;
        // returning the null
        return NULL;
    }
    // create new node
    TreeNode* node = new TreeNode(arr[idx]);
    // incrementing the index
    idx++;
    // calling recursion
    node->left = createTree(arr);
    node->right = createTree(arr);
    // returning the node
    return node;
}


//  displaying the tree
void display(TreeNode* root){
    // base case
    if(root==NULL){
        return;
    }
    // storing result in str
    string str = root->left!=NULL?to_string(root->left->val):".";
    str += " <-- "+to_string(root->val)+" --> ";
    str += root->right!=NULL?to_string(root->right->val):".";
    cout<<str<<endl;
    // calling the recursion
    display(root->left);
    display(root->right);
}


// finding the node in a tree
bool findNode(TreeNode* root,TreeNode* data){
    // this is the base case
    // if the root is null then returning the false
    if(root==NULL){
        return false;
    }
    // checking the current node is the required node or not and if it is then returning true
    if(root->val == data->val){
        return true;
    }
    // variable to store the res and initially it is false
    bool res = false;
    // calling the left and right recursion
    res = res || findNode(root->left,data);
    res = res || findNode(root->right,data);
    // return the res
    return res;
}

// finding the root to node path and we will return the path in the vector
// using the return type recursion
vi rootToNodePath(TreeNode* root,TreeNode* data){
    // base case when we gotthe root empty then returning the result as empty array
    if(root == NULL){
        return {};
    }
    // if we find that the current node as required node then we will return the array by adding the current node in it
    if(root->val == data->val){
        return {root->val};
    }
    // calling the reft recursion and fetching the result
    vi left = rootToNodePath(root->left,data);
    // if the size of the left ans is greater than 0 then we will add current node to it and then returning it
    if(left.size()>0){
        // adding the current node in it
        left.push_back(root->val);
        // returning the left
        return left;
    }
    //  calling the right recursion
    vi right = rootToNodePath(root->right,data);
    // if it's size is greater than 1 then returning array by adding the current node in it
    if(right.size()>0){
        // adding the current node to the left array
        right.push_back(root->val);
        // and returning the left array
        return right;
    }
    // if we didn't get any array greater than 0 it means we haven't find any result so returning the empty arr
    return {};
}

// now finding root to node path using void type recursion
// finding the root to node path using  return type as bool
bool rootToFindPathVoidType(TreeNode* root,TreeNode* data,vn& ans){
    // base case if root is null then returning the false
    if(root==NULL){
        return false;
    }
    // checking if the required node is the current node
    if(root->val == data->val){
        // adding the result to the answer
        ans.push_back(root);
        // returning the true
        return true;
    }
    // variable to store the res
    bool res = false;
    // calling the left and right recursion
    res = res || rootToFindPathVoidType(root->left,data,ans)||rootToFindPathVoidType(root->right,data,ans);
    // if the res is true that means we have found the path and then adding the current node to the result
    if(res){
        // adding current node value to the result
        ans.push_back(root);
    }
    // resturning the result
    return res;
}

// finding the lca  of two node
// first we will find the  root to node path of each of the two node then we will iterate over the path 
// until we get  the two different node in the root to node path array
int lca01(TreeNode* root,TreeNode* p,TreeNode* q){
    if(root == NULL){
        return -1;
    }
    // vector to store the root to node path for p
    vn pPath;
    // finding the path of the p from root
    rootToFindPathVoidType(root,p,pPath);
    // vector to store the root to node path for p
    vn qPath;
    // finding the path of the p from root
    rootToFindPathVoidType(root,q,qPath);
    // variable to store the lca which will be the previous one when we found different nodes in the path of p and q
    int prev = -1;
    // index to iterate over the paths from the back
    int i = pPath.size()-1;
    int j = qPath.size()-1;
    // iterating over the path array and finding the previous
    while(i>=0 && j>=0){
        // checking if they are different or not and if they did then we break from the loop until then we get the result 
        // in the prev variable
        if(pPath[i]->val!=qPath[j]->val){
            break;
        }
        // storing values in the prev
        prev = pPath[i]->val;
        // decrementing the indexes
        i--;
        j--;
    }
    // returning the prev which holds the lca
    return prev;

}

// finding lca in another way in which we don't need to find the root to node path first
bool lca02(TreeNode* root,TreeNode* p, TreeNode* q,int& ans){
    // checking if the root is null or not and if it is then returning the false
    if(root == NULL){
        return false;
    }
    // variable to store the information of the current node that it matches with the queue or not
    bool current = false;
    // checking if the current node matches with it or not if did then changing current value to true
    if(root->val == p->val || root->val == q->val){
        current = true;
    }
    // calling the left and right recursion and storing return value in the variable left and right resp.
    bool left = lca02(root->left,p,q,ans);
    bool right = lca02(root->right,p,q,ans);
    // this is the main if condition which checks if any of these condition concludes to true then we will update our ans
    if((left&&right) || (left&&current) || (right&&current)){
        ans = root->val;
    }
    // returning the true if any of them is true otherwise false
    return left||right||current;
}

// all nodes at position k from a node
void kDown(TreeNode* root,int level,TreeNode* blocked,vi& ans){
    // checking if we get null or the path is blocked or not
    if(root==NULL ||(blocked!=NULL && root->val == blocked->val)){
        return ;
    }
    // when level became zero then that node will be participate in the answer
    if(level == 0){
        ans.push_back(root->val);
        return;
    }
    // and then calling the right and the left answer
    kDown(root->left,level-1,blocked,ans);
    kDown(root->right,level-1,blocked,ans);
}

// function to find  the all node at postion k
void allNodeAtK(TreeNode* root,TreeNode* data,int k,vi& ans){
    // base case
    if(root==NULL){
        return;
    }
    // vector to store the path from root to node
    vn path;
    // function to find the root to node path
    rootToFindPathVoidType(root,data,path);
    // this will decide which path is blocked
    TreeNode* blocked = NULL;
    // iterating over the path vector and calling kDown to get the all nodes at k distance
    for(int i=0;i<path.size();i++){
        // condition needs to be true
        if(k-i>=0){
            // calling the kDown
            kDown(path[i],k-i,blocked,ans);
            // changing the blocked paths
            blocked = path[i];
        }
    }
}

// In  this we are going to call kDown in the root to node path function itself
// so we are rewriting this function
int optimizedRootToNodePath(TreeNode* root,TreeNode* data,int k,vi& ans){
    // base case
    if(root==NULL){
        return -1;
    }
    // when we get the current value as the required value then we will call the kDown
    if(root->val == data->val){
        // calling the kDown
        kDown(root,k,NULL,ans);
        // returning the answer
        return 1;
    }
    // calling the left recursion
    int left = optimizedRootToNodePath(root->left,data,k,ans);
    // if it gets value greater than 0 it means it has the path so we call kDown
    if(left>-1){
        // checking if k-left is greater than zero or not
        if(k-left>=0){
            // calling kDown
            kDown(root,k-left,root->left,ans);
            return left+1;
        }
    }
    // calling the  right recursion
    int right = optimizedRootToNodePath(root->right,data,k,ans);
    // same as above
    if(right!=-1){
        if(k-right>=0){
            kDown(root,k-right,root->right,ans);
            return right+1;
        }
    }
    // if we reach till here that means we didn't get any
    return -1;
}
// finding the all node at postion k
void allNodeAtK02(TreeNode* root,TreeNode* data,int k,vi& ans){
    if(root==NULL){
        return;
    }
    optimizedRootToNodePath(root,data,k,ans);
}

// diameter 
void solve(){
    vector<int> arr = {10,20,40,-1,-1,50,80,-1,-1,90,-1,-1,30,60,100,-1,-1,-1,70,110,-1,-1,120,-1,-1};
    TreeNode* root = createTree(arr);
    display(root);
    // cout<<boolalpha<<findNode(root, new TreeNode(45))<<endl;
    // vi res = rootToNodePath(root,new TreeNode(120));
    // rootToFindPathVoidType(root,new TreeNode(110));
    // cout<<lca01(root,new TreeNode(30),new TreeNode(100))<<endl;
    // int ans = -1;
    // lca02(root,new TreeNode(90),new TreeNode(100),ans);
    // cout<<ans<<endl;
    // vi ans;
    // allNodeAtK(root,new TreeNode(30),2,ans);
    vi ans;
    allNodeAtK02(root,new TreeNode(50),2,ans);
    for(int r:ans){
        cout<<r<<" ";
    }
}




int main(){
    solve();
    return 0;
}