package com.alog.adt.tree.bst;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 二叉搜索树(BST)
 *
 * @author lushenchen 2024/1/3 9:30
 * @since 1.0.0
 */
public class BinarySearchTree<E extends Comparable<? super E>>
{
    /**
     * 二叉树搜索树根节点
     */
    private Node<E> root;
    
    public BinarySearchTree()
    {
        this.root = null;
    }
    
    public BinarySearchTree(E Value)
    {
        this.root = new Node<>(Value);
    }
    
    /**
     * 获取树中最小的元素
     *
     * @return
     */
    public E getMin()
    {
        Node<E> node = findMin(this.root);
        if (node == null)
        {
            return null;
        }
        return node.value;
    }
    
    /**
     * 获取树中最大的元素
     *
     * @return
     */
    public E getMax()
    {
        Node<E> node = findMax(this.root);
        if (node == null)
        {
            return null;
        }
        return node.value;
    }
    
    /**
     * 获取指定元素的中序前驱元素
     *
     * @param target 指定元素
     * @return
     */
    public E precursor(E target)
    {
        Node<E> precursor = precursor(this.root, target);
        if (precursor == null)
        {
            return null;
        }
        return precursor.value;
    }
    
    /**
     * 获取指定元素的中序后继元素
     *
     * @param target 指定元素
     * @return
     */
    public E successor(E target)
    {
        Node<E> successor = successor(this.root, target);
        if (successor == null)
        {
            return null;
        }
        return successor.value;
    }
    
    /**
     * 获取指定元素所在节点的中序先驱
     *
     * @param root
     * @param target
     * @return
     */
    private Node<E> precursor(Node<E> root, E target)
    {
        // 在树中查找目标元素所在的节点
        Node<E> current = find(root, target);
        // 如果没有该节点则直接返回NULL
        if (current == null)
        {
            return null;
        }
        // 如果该节点有左子树
        if (current.left != null)
        {
            return findMax(current.left);
        }
        else
        {
            // 如果节点没有左子树
            Node<E> precursor = null;
            Node<E> ancestor = root;
            // 从根节点移动，直到找到当前节点
            while (ancestor != current)
            {
                // 如果当前节点的值大于祖先节点，往右移动
                // 当前节点一定位于最近祖先的右子树中
                if (current.value.compareTo(ancestor.right.value) > 0)
                {
                    precursor = ancestor;
                    ancestor = ancestor.right;
                }
                else
                {
                    // 往左移动，当前节点在该祖先的左子树中，不需要更新前驱指针
                    ancestor = ancestor.left;
                }
            }
            return precursor;
        }
    }
    
    /**
     * 获取指定元素所在节点的中序后继节点
     *
     * @param target 目标元素
     * @return
     */
    private Node<E> successor(Node<E> root, E target)
    {
        // 在树中查找目标元素所在的节点
        Node<E> current = find(root, target);
        // 如果没有该节点则直接返回NULL
        if (current == null)
        {
            return null;
        }
        // 如果该节点有右子树
        if (current.right != null)
        {
            // 后继节点时当前右子树的最小值
            return findMin(current.right);
        }
        else
        {
            // 如果节点没有右子树
            Node<E> ancestor = root;
            Node<E> successor = null;
            // 查找元素大小最靠近当前节点元素的祖先节点
            while (ancestor != current)
            {
                // 如果当前节点的值小于祖先节点的值，往左子树移动
                // 当前节点一定位于最近祖先的左子树中
                if (current.value.compareTo(ancestor.value) < 0)
                {
                    successor = ancestor;
                    ancestor = ancestor.left;
                }
                else
                {
                    // 往右移动，当前节点位于该祖先的右子树中，不需要更新后继指针
                    ancestor = ancestor.right;
                }
            }
            return successor;
        }
    }
    
    /**
     * 向二叉搜索树中插入元素
     *
     * @param value
     */
    public void insert(E value)
    {
        // NULL检测
        if (value == null)
        {
            return;
        }
        root = this.insert(this.root, value);
    }
    
    /**
     * 检测二叉搜索树中是否包含某个元素
     *
     * @param target
     * @return
     */
    public boolean contains(E target)
    {
        return find(this.root, target) != null;
    }
    
    /**
     * 先序遍历二叉搜索树
     *
     * @return
     */
    public List<E> preOrder()
    {
        List<E> list = new ArrayList<>();
        preOrder(this.root, list);
        return list;
    }
    
    /**
     * 中序遍历二叉搜索树
     *
     * @return
     */
    public List<E> inOrder()
    {
        List<E> list = new ArrayList<>();
        inOrder(this.root, list);
        return list;
    }
    
    /**
     * 后续遍历二叉搜索树
     *
     * @return
     */
    public List<E> postOrder()
    {
        List<E> list = new ArrayList<>();
        postOrder(this.root, list);
        return list;
    }
    
    /**
     * 层次打印二叉搜索树
     */
    public void print()
    {
        Queue<Node<E>> nodeQueue = new LinkedList<>();
        Node<E> curr = null;
        nodeQueue.offer(this.root);
        // start-记录从队列取出的节点数
        // end-记录每层节点的数量
        int start = 0, end = 1;
        while (!nodeQueue.isEmpty())
        {
            curr = nodeQueue.poll();
            System.out.print(curr.value + " ");
            start++;
            if (curr.left != null)
            {
                nodeQueue.offer(curr.left);
            }
            if (curr.right != null)
            {
                nodeQueue.offer(curr.right);
            }
            if (start == end)
            {
                start = 0;
                end = nodeQueue.size();
                System.out.println();
            }
        }
    }
    
    /**
     * 寻找元素最小的节点
     *
     * @param root 指定的树
     * @return
     */
    private Node<E> findMin(Node<E> root)
    {
        if (root.left == null)
        {
            return root;
        }
        return findMin(root.left);
    }
    
    /**
     * 寻找元素最大的节点
     *
     * @return
     */
    private Node<E> findMax(Node<E> root)
    {
        if (root.right == null)
        {
            return root;
        }
        return findMax(root.right);
    }
    
    /**
     * 在指定的树中查找包含指定元素的节点
     *
     * @param root   指定的树
     * @param target 目标元素
     * @return
     */
    private Node<E> find(Node<E> root, E target)
    {
        if (root == null)
        {
            return null;
        }
        if (target.compareTo(root.value) > 0)
        {
            return find(root.right, target);
        }
        else if (target.compareTo(root.value) < 0)
        {
            return find(root.left, target);
        }
        else
        {
            return root;
        }
    }
    
    /**
     * 向指定的树中插入指定的元素
     *
     * @param root  指定的树
     * @param value 指定的元素
     * @return
     */
    private Node<E> insert(Node<E> root, E value)
    {
        if (root == null)
        {
            return new Node<>(value);
        }
        if (value.compareTo(root.value) < 0)
        {
            root.left = insert(root.left, value);
        }
        else if (value.compareTo(root.value) > 0)
        {
            root.right = insert(root.right, value);
        }
        else
        {
            root.value = value;
        }
        return root;
    }
    
    /**
     * 先序遍历辅助
     *
     * @param root 指定树
     * @param list 收集节点元素的集合
     */
    private void preOrder(Node<E> root, List<E> list)
    {
        if (root == null)
        {
            return;
        }
        list.add(root.value);
        preOrder(root.left, list);
        preOrder(root.right, list);
    }
    
    /**
     * 中序遍历辅助
     *
     * @param root 指定树
     * @param list 收集节点元素的集合
     */
    private void inOrder(Node<E> root, List<E> list)
    {
        if (root == null)
        {
            return;
        }
        inOrder(root.left, list);
        list.add(root.value);
        inOrder(root.right, list);
    }
    
    /**
     * 后续遍历辅助
     *
     * @param root 指定树
     * @param list 收集节点元素的集合
     */
    private void postOrder(Node<E> root, List<E> list)
    {
        if (root == null)
        {
            return;
        }
        postOrder(root.left, list);
        postOrder(root.right, list);
        list.add(root.value);
    }
    
    /**
     * 二叉树节点对象
     *
     * @param <E>
     */
    static class Node<E extends Comparable<? super E>>
    {
        E value;
        
        /**
         * 左子树
         */
        Node<E> left;
        
        /**
         * 右子树
         */
        Node<E> right;
        
        public Node(E value)
        {
            this.value = value;
        }
        
        public E getValue()
        {
            return value;
        }
        
        public void setValue(E value)
        {
            this.value = value;
        }
        
        public Node<E> getLeft()
        {
            return left;
        }
        
        public void setLeft(Node<E> left)
        {
            this.left = left;
        }
        
        public Node<E> getRight()
        {
            return right;
        }
        
        public void setRight(Node<E> right)
        {
            this.right = right;
        }
        
        public String toString()
        {
            return "( " + value + " )";
        }
    }
}
