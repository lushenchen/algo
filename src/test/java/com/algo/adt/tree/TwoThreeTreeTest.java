package com.algo.adt.tree;

import com.alog.adt.tree.Pair;
import com.alog.adt.tree.btree.TwoThreeTree;

import java.util.List;

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
        List<Pair<Integer, Integer>> kvPairs = tree.inOrder();
        System.out.println(kvPairs);
    
        System.out.println(tree.getMin(tree.getRoot()));
        System.out.println(tree.getMax(tree.getRoot()));
    
        System.out.println(tree.precursor(0));
        System.out.println(tree.precursor(1));
        System.out.println(tree.precursor(2));
        System.out.println(tree.precursor(3));
        System.out.println(tree.precursor(4));
        System.out.println(tree.precursor(5));
        System.out.println(tree.precursor(6));
        System.out.println(tree.precursor(7));
        System.out.println(tree.precursor(8));
        System.out.println(tree.precursor(9));
        System.out.println(tree.precursor(10));
    
        System.out.println("-------------------------------");
    
        System.out.println(tree.successor(0));
        System.out.println(tree.successor(1));
        System.out.println(tree.successor(2));
        System.out.println(tree.successor(3));
        System.out.println(tree.successor(4));
        System.out.println(tree.successor(5));
        System.out.println(tree.successor(6));
        System.out.println(tree.successor(7));
        System.out.println(tree.successor(8));
        System.out.println(tree.successor(9));
        System.out.println(tree.successor(10));
        
    }
}
