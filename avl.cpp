#include<iostream>
#include<vector>
using namespace std;


class TreeNode{
    public:
    // basic tree structure
    int val;
    TreeNode* left = NULL;
    TreeNode* right = NULL;

    // For AVL we will also store the height and Balance
    int height = 0;
    int balance = 0;

    TreeNode(int val){
        this->val = val;
        this->left = NULL;
        this->right = NULL;
        this->height = 0;
        this->balance = 0;
    }
};

// function to update the weight and the balance
void updateWeightAndBalanace(TreeNode* root){
    // finding the left and right height of the tree
    int lh = root->left != NULL?root->left->height:-1;
    int rh = root->right != NULL?root->right->height:-1;
    // balance is the subtraction of left height and right height
    root->balance = (lh-rh);
    // height is max of the lh and rh plus one
    root->height = max(lh,rh)+1;
}

// construct binary tree
TreeNode* constructBST(vector<int>& arr,int si,int ei){
    // if we get si greater than the ei we will stop
    if(si<=ei){
        // finding the mid of the array
        int mid = (si+ei)>>1;
        // creating new node
        TreeNode* node = new TreeNode(arr[mid]);
        // creating left and right subtree
        node->left = constructBST(arr,si,mid-1);
        node->right = constructBST(arr,mid+1,ei);
        // updating the weight ans balance
        updateWeightAndBalanace(node);
        return node;
    }
    return NULL;
}

//  displaying avl tree
void display(TreeNode* root){
    if(root==NULL){
        return;
    }
    string nodeVal = root->left!=NULL?to_string(root->left->val)+"["+to_string(root->left->balance)+","+to_string(root->left->height)+"]":".";
    nodeVal+=" <-- "+to_string(root->val)+"["+to_string(root->balance)+","+to_string(root->height)+"] --> ";
    nodeVal+= root->right!=NULL?to_string(root->right->val)+"["+to_string(root->right->balance)+","+to_string(root->right->height)+"]":".";
    cout<<nodeVal<<endl;
    display(root->left);
    display(root->right);
}

// left left 
TreeNode* ll(TreeNode* A){
    // assigning the left of a to b
    TreeNode* B = A->left;
    // assigning right of the b to left of a
    A->left = B->right;
    // then assigning the A to the right of the B
    B->right = A;
    // first updating weight of A
    updateWeightAndBalanace(A);
    // then updating the weights of B
    updateWeightAndBalanace(B);
    return B;
}

// right right 
TreeNode* rr(TreeNode* A){
    // making right of the A B
    TreeNode* B = A->right;
    // then assigning the left of B to the right of A
    A->right = B->left;
    // assigning the a to the left of B
    B->left = A;
    updateWeightAndBalanace(A);
    updateWeightAndBalanace(B);
    return B;
}

// rotation
TreeNode* getRotation(TreeNode* A){
    // first updating the weights anf balance
    updateWeightAndBalanace(A);
    // if balance is 2 that means it is going to left side
    if(A->balance==2){ // ll, lr
        if(A->left->balance==1){// ll
            return ll(A);
        }else{// lr
            A->left = rr(A->left);
            return ll(A);
        }
    }else if(A->balance==-2){// rr, rl
        if(A->right->balance==-1){//rr
            return rr(A);
        }else{//rl
            A->right = ll(A->right);
            return rr(A);
        }
    }
    return A;
}

int maximum(TreeNode* root){
    if(root==NULL){
        return -1e8;
    }
    int max_ = -1e8;
    max_ = max(max_,maximum(root->left));
    max_ = max(max_,maximum(root->right));
    return max(max_,root->val);
}

TreeNode* addNode(TreeNode* root,int val){
    if(root==NULL){
        return new TreeNode(val);
    }
    if(root->val>val){
        root->left = addNode(root->left,val);
    }else{
        root->right = addNode(root->right,val);
    }
    return getRotation(root);
}

TreeNode* removeNode(TreeNode* root,int val){
    if(root==NULL){
        return NULL;
    }
    if(root->val>val){
        root->left = removeNode(root->left,val);
    }else if(root->val<val){
        root->right = removeNode(root->right,val);
    }else{
        if(root->left==NULL || root->right==NULL){
            return root->left!=NULL?root->left:root->right;
        }
        int maxInLeft = maximum(root->left);
        root->val = maxInLeft;
        root->left = removeNode(root->left,maxInLeft);
    }
    return getRotation(root);
}

int main(){
    vector<int> arr = {10,20,30,40,50,60,70,80,90,100,110,120,130,140,150,160,170,180};
    // TreeNode* root = constructBST(arr,0,17);
    TreeNode* root = NULL;
    for(int i=0;i<18;i++){
        root = addNode(root,arr[i]);
        display(root);
        cout<<endl;
    }
    return 0;
}
























// #include<iostream>
// #include<vector>
// using namespace std;

// class TreeNode{
//     public:
//     int val;
//     TreeNode* left=NULL;
//     TreeNode* right = NULL;
//     int height = 0;
//     int bal = 0;

//     TreeNode(int val){
//         this->val = val;
//         this->height = 0;
//         this->bal = 0;
//     }
// };

// void display(TreeNode* root){
//     if(root==NULL){
//         return;
//     }
//     string node = root->left!=NULL?to_string(root->left->val)+"["+to_string(root->left->bal)+","+to_string(root->left->height)+"]":".";
//     node+=" <-- "+to_string(root->val)+"["+to_string(root->bal)+","+to_string(root->height)+"]"+" --> ";
//     node+=root->right!=NULL?to_string(root->right->val)+"["+to_string(root->right->bal)+","+to_string(root->right->height)+"]":".";
//     cout<<node<<endl;
//     display(root->left);
//     display(root->right);
// }

// // updating the balance and height
// void updateBalanceAndHeight(TreeNode* root){
//     int lh = root->left!=NULL?root->left->height:-1;
//     int rh = root->right!=NULL?root->right->height:-1;
//     root->bal = lh-rh;
//     root->height = max(lh,rh)+1;
// }

// TreeNode* constructTree(vector<int>& arr,int si,int ei){
//     if(si<=ei){
//         int mid = (si+ei)>>1;
//         TreeNode* node = new TreeNode(arr[mid]);
//         node->left = constructTree(arr,si,mid-1);
//         node->right = constructTree(arr,mid+1,ei);
//         updateBalanceAndHeight(node);
//         return node;
//     }
//     return NULL;
// }

// // rotating the ll tree
// TreeNode* ll(TreeNode* A){
//     TreeNode* B = A->left;
//     TreeNode* c = B->right;
//     B->right = A;
//     A->left = c;
//     updateBalanceAndHeight(A);
//     updateBalanceAndHeight(B);
//     return B;
// }

// // rotating the rr tree
// TreeNode* rr(TreeNode* A){
//     TreeNode* B = A->right;
//     TreeNode* c = B->left;
//     B->left = A;
//     A->right = c;
//     updateBalanceAndHeight(A);
//     updateBalanceAndHeight(B);
//     return B;
// }

// TreeNode* getRotation(TreeNode* root){
//     updateBalanceAndHeight(root);
//     if(root->bal==2){ // ll lr
//         if(root->left->bal==1){
//             return ll(root);
//         }else{
//             root->left = rr(root->left);
//             return ll(root);
//         }
//     }else if(root->bal==-2){ // rr rl
//         if(root->right->bal==-1){
//             return rr(root);
//         }else{
//             root->right = ll(root->right);
//             return rr(root);
//         }
//     }
//     return root;
// }

// int maximum(TreeNode* root){
//     if(root==NULL){
//         return -1e8;
//     }

//     int max_ = -1e8;
//     max_ = max(max_,maximum(root->left));
//     max_ = max(max_,maximum(root->right));
//     return max(max_,root->val);
// }

// TreeNode* removeData(TreeNode* root,int val){
//     if(root==NULL){
//         return NULL;
//     }
//     if(root->val > val){
//         root->left = removeData(root->left,val);
//     }else if(root->val<val){
//         root->right = removeData(root->right,val);
//     }else{
//         if(root->left==NULL || root->right==NULL){
//             return root->left==NULL?root->right:root->left;
//         }
//         int maxInLeft = maximum(root->left);
//         root->val = maxInLeft;
//         root->left = removeData(root->left,maxInLeft);
//     }
//     return getRotation(root);
// }

// TreeNode* addData(TreeNode* root,int val){
//     if(root==NULL){
//         return new TreeNode(val);
//     }
//     if(root->val>val){
//         root->left = addData(root->left,val);
//     }else{
//         root->right = addData(root->right,val);
//     }
//     return getRotation(root);
// }

// int main(){
//     vector<int> arr = {10,20,30,40,50,60,70,80,90,100,110,120,130};
//     // TreeNode* root = constructTree(arr,0,12);
//     TreeNode* root = NULL;
//     for(int i=0;i<arr.size();i++){
//         root = addData(root,arr[i]);
//         display(root);
//         cout<<endl;
//     }
//     return 0;
// }