package com.algo.adt.tree;

import com.alog.adt.tree.bst.BinarySearchTree;

/**
 * 二叉查找树测试
 *
 * @author lushenchen 2024/1/4 15:06
 * @since 1.0.0
 */
public class BinarySearchTreeTest
{
    public static void main(String[] args)
    {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.insert(8);
        bst.insert(4);
        bst.insert(12);
        bst.insert(2);
        bst.insert(6);
        bst.insert(10);
        bst.insert(14);
        bst.insert(1);
        bst.insert(3);
        bst.insert(5);
        bst.insert(7);
        bst.insert(9);
        bst.insert(11);
        bst.insert(13);
        bst.insert(15);
        
        bst.print();
        System.out.println(bst.getMin());
        System.out.println(bst.getMax());
        System.out.println(bst.preOrder());
        System.out.println(bst.inOrder());
        System.out.println(bst.postOrder());
        System.out.println(bst.contains(8));
        System.out.println(bst.precursor(8));
        System.out.println(bst.successor(8));
    }
}
