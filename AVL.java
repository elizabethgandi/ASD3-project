public class AVL {

    private kdTree pointer ;
    private int valueOfTheBalance;
    private int valueOfTheHeight;
    private boolean isEmpty;
    private AVL rightSon;
    private AVL leftSon;


    public AVL(kdTree pointer, int valueOfTheHeight){
        this.valueOfTheHeight  = valueOfTheHeight;
        this.pointer = pointer;
    }

    public AVL(){
      this.isEmpty = true;
    }

        /* Method which print AVL *//*
    public void printAVL(AVL nodeOfAVLTest) {

        if (nodeOfAVLTest != null) {
            printAVL(nodeOfAVLTest.getLeftSon());
            System.out.print(nodeOfAVLTest.getInformation() + " ");
            printAVL(nodeOfAVLTest.getRightSon());
        }
    }*/



    /* Method who return the maximum of two values enter in parameters */
    public int maximumOfTwoValues(int firstValue, int secondValue){

        if(firstValue > secondValue)
            return firstValue;

            return secondValue;
    }

        /* Method who return the height of a node */
    public int heightValueOfANode(AVL node) {
        if (node == null)
            return 0;

        return node.getValueOfTheHeight();
    }

    /* Method who make a right rotation */
    public AVL makeARightRotate(AVL nodeRotation){
            //
        AVL subTreeLeft               = nodeRotation.getLeftSon();
        AVL subTreeRightOfSubTreeLeft = subTreeLeft.getRightSon();

        subTreeLeft.setRightSon(nodeRotation);
        nodeRotation.setLeftSon(subTreeRightOfSubTreeLeft);

        nodeRotation.setValueOfTheHeight(maximumOfTwoValues(heightValueOfANode(nodeRotation.leftSon), heightValueOfANode(nodeRotation.rightSon)) + 1);
        subTreeLeft.setValueOfTheHeight(maximumOfTwoValues(heightValueOfANode(subTreeLeft.leftSon) , heightValueOfANode(subTreeLeft.rightSon))  + 1);

        /* subTreeLeft is now the new root */
        return subTreeLeft;
    }

    /* Method who make a left rotation */
    public AVL makeALeftRotate(AVL nodeRotation){
        AVL subTreeLeft               = nodeRotation.getRightSon();
        AVL subTreeRightOfSubTreeLeft = subTreeLeft.getLeftSon();

        subTreeLeft.setLeftSon(nodeRotation);
        nodeRotation.setRightSon(subTreeRightOfSubTreeLeft);

        nodeRotation.setValueOfTheHeight (maximumOfTwoValues(heightValueOfANode(nodeRotation.leftSon), heightValueOfANode(nodeRotation.rightSon)) + 1);
        subTreeLeft.setValueOfTheHeight (maximumOfTwoValues(heightValueOfANode(subTreeLeft.leftSon) , heightValueOfANode(subTreeLeft.rightSon))  + 1);

        /* subTreeLeft is now the new root */
        return subTreeLeft;
    }

    public AVL sensRotationAVL(int balanceValue, AVL A){
        if (balanceValue == 2){
            if(A.getRightSon().getValueOfTheBalance()>= 0){
                /* ROTG */
                return makeALeftRotate(A);
            }
            else{ /*DROTG */
                A.setRightSon(makeARightRotate(A.getRightSon()));
                return makeALeftRotate(A);
            }
        }
        if (balanceValue == -2){
            if (A.getLeftSon().getValueOfTheBalance() <= 0){
                /* ROTD */
                return makeARightRotate(A);
            }
            else{ /*DROTD */
                A.setRightSon(makeALeftRotate(A.getLeftSon()));
                return makeALeftRotate(A);
            }
        }

        return A;
    }

  /*  public AVL insertNode(kdTree nodeAdd){
        if (nodeAdd == null) {
            AVL newNode = new AVL(new kdTree(), informationAdd,0);
            return newNode;
        }
        if (informationAdd>nodeAdd.getWeight()){
            nodeAdd.setRight(addNewNode(nodeAdd.getRight()));
        }
        else if (informationAdd<=nodeAdd.getWeight()) {
            nodeAdd.setLeftSon(addNewNode(nodeAdd.getLeft()));
        }
        else {
            return nodeAdd;
        }
        return nodeAdd;
    }*/

    public AVL addNewNode(kdTree nodeAdd){
      return addNewNode2(nodeAdd,this);
    }

    public AVL addNewNode2(kdTree nodeAdd, AVL A) {

        if (A.getIsEmpty()){
            return new AVL(nodeAdd, 0);

        /*Insert the node into the AVL tree with a simple way*/

        } else if (A.getPointer().getWeight()>nodeAdd.getWeight()){
          A.setLeftSon(addNewNode2(nodeAdd.getLeft(),A.getLeftSon()));
        }

        else if (A.getPointer().getWeight()<=nodeAdd.getWeight()) {
          A.setRightSon(addNewNode2(nodeAdd.getRight(),A.getRightSon()));
        }

        /*Update heights of parent's nodeAdd*/

        A.setValueOfTheHeight( 1 + maximumOfTwoValues(heightValueOfANode(A.getLeftSon()),heightValueOfANode(A.getRightSon())));

        /*Update balances of nodes in the tree*/

        int balanceValue = A.getValueOfTheBalance();

        /* If the balance = 2 or -2 it's mean that the tree is unbalanced */

        return sensRotationAVL(balanceValue, A);
    }

    public AVL max(){
        return this.maximumOfRightSubTree(this);
      }


    public AVL minimumInRightSubTree(AVL root){
        AVL minNodeValue = root;

        /* Commme l'AVL est trié on est sur que la plus petite valeur est dans la branche tout à gauche*/
        while(minNodeValue.getLeftSon() != null )
            minNodeValue = minNodeValue.getLeftSon();

        return minNodeValue;
    }

    public AVL maximumOfRightSubTree(AVL root){
        AVL maxNodeValue = root;

        /* Commme l'AVL est trié on est sur que la plus grande valeur est dans la branche tout à droite*/
        while(maxNodeValue.getRightSon() != null )
            maxNodeValue = maxNodeValue.getRightSon();

        return maxNodeValue;
    }

    public AVL removeNodeGiven (AVL rootNodeRemove, double valueToRemove){

        AVL newNode = null;

        // way into the tree
        //value < value of the root
        if (valueToRemove < rootNodeRemove.getPointer().getWeight())
            rootNodeRemove.setLeftSon(removeNodeGiven(rootNodeRemove.getLeftSon(), valueToRemove));

            //value > value of the root
        else if (valueToRemove > rootNodeRemove.getPointer().getWeight())
            rootNodeRemove.setRightSon(removeNodeGiven(rootNodeRemove.getRightSon(), valueToRemove));

            // the node deleted is the root
            // the value who has to be deleted is the value of the root of a tree (1), of a leaf (2)
        else {

            /*
            if the node has one child (1)
                  4
                /
               1
             */

            if ((rootNodeRemove.getLeftSon() == null) || (rootNodeRemove.getRightSon() == null)) {

               if (rootNodeRemove.getLeftSon() == null)
                    newNode = rootNodeRemove.getRightSon();

               if (rootNodeRemove.getRightSon() == null)
                    newNode = rootNodeRemove.getLeftSon();

                    /* if the node is a leaf
                            4
                           / \
                          ^   ^
                    */
                if (newNode == null) {
                    newNode = rootNodeRemove;
                    rootNodeRemove = null;

                } else
                    rootNodeRemove = newNode;
                }
            else {
                //prend le fils droit du fils supprimer pour le coller au pere du fils supprimer
                AVL newRoot = minimumInRightSubTree(rootNodeRemove.getRightSon());

                //rootNodeRemove.setInformation(newRoot.getInformation());
                //rootNodeRemove.setRightSon(removeNodeGiven(rootNodeRemove.getRightSon(), newRoot.getInformation()));
                rootNodeRemove.setRightSon(removeNodeGiven(rootNodeRemove.getRightSon(), newRoot.getPointer().getWeight()));
            }
        }
        return rootNodeRemove;
    }


    public double convertBiggestNodeToDoubleNode(AVL B){
        AVL A = biggestNodeValueInAvl(B);
        return A.getPointer().getWeight();
    }

    //enlever le noeud le plus gros
    public AVL removeBiggestNode(AVL A){

      AVL B = maximumOfRightSubTree(A);

      if (B.getRightSon().getIsEmpty() == true){
        return B;
      }else{
        return removeBiggestNode(B.getRightSon());
      }
      //  return removeNode(A, A.convertBiggestNodeToDoubleNode(A));
    }

    public AVL biggestNodeValueInAvl(AVL A){

        AVL B = A;

        if (B.getIsEmpty())
            return null;

        else{
            B = biggestNodeValueInAvl(B.getRightSon());
        }
        return B;//B.getPointer().getWeight();
    }

    //enlver un noeud interne

    public AVL removeNode (AVL rootNodeRemove, double valueToRemove){

        if (rootNodeRemove == null)
            return null; // because rootNodeRemove is always equals to null

           // replace the older root by the new one
        rootNodeRemove = removeNodeGiven(rootNodeRemove, valueToRemove);

        if (rootNodeRemove == null)
            return null; // because rootNodeRemove is always equals to null

        // modifie the height of the root in take the maximum of the height of his two sons
        rootNodeRemove.setValueOfTheHeight(maximumOfTwoValues(rootNodeRemove.getLeftSon().getValueOfTheHeight(), rootNodeRemove.getRightSon().getValueOfTheHeight()) + 1);

        //gérer la balance
        int balanceValue = rootNodeRemove.getValueOfTheBalance();

        rootNodeRemove = sensRotationAVL(balanceValue, rootNodeRemove);

        return rootNodeRemove;
    }


public kdTree getPointer() {
    return this.pointer;
}

public boolean getIsEmpty(){
  return this.isEmpty;
}

public int getValueOfTheBalance() {
    return this.valueOfTheBalance;
}

public void setValueOfTheBalance(int valueOfTheBalance) {
    this.valueOfTheBalance = valueOfTheBalance;
}

public int getValueOfTheHeight() {
    return this.valueOfTheHeight;
}

public void setValueOfTheHeight(int valueOfTheHeight) {
    this.valueOfTheHeight = valueOfTheHeight;
}

public AVL getRightSon() {
    return this.rightSon;
}

public void setRightSon(AVL rightSon) {
    this.rightSon = rightSon;
}

public AVL getLeftSon() {
    return this.leftSon;
}
public void setLeftSon(AVL leftSon) {
    this.leftSon = leftSon;
}

}
