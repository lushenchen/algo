package com.algo.adt.tree;

import com.alog.adt.tree.btree.TwoThreeTree;

/**
 * 2-3树测试
 *
 * @author lushenchen 2023/12/30 14:43
 * @since 1.0.0
 */
public class TwoThreeTreeTest
{
    public static void main(String[] args)
    {
        TwoThreeTree<Integer, Integer> tree = new TwoThreeTree<>();
        for (int i = 0; i < 10; i++)
        {
            tree.insert(i, i);
        }
        tree.print();
        System.out.println(tree.preOrder());
        System.out.println(tree.inOrder());
        System.out.println(tree.postOrder());
    }
}
