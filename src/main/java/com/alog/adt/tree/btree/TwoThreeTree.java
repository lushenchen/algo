package com.alog.adt.tree.btree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 2-3树
 *
 * @author lushenchen 2023/12/30 13:56
 * @since 1.0.0
 */
public class TwoThreeTree<K extends Comparable<? super K>, V>
{
    /**
     * 根节点
     */
    private Node<K, V> root;
    
    /**
     * 返回插入的元素
     * @return
     */
    public void insert(K key, V value)
    {
        insertByLeafNode(key, value);
    }
    
    /**
     * 通过子节点
     * @param key 待插入的元素Key
     * @param value 待插入的元素Value
     */
    private void insertByLeafNode(K key, V value)
    {
        KVPair<K, V> kvPair = createPair(key, value);
        // 如果根节点为空，创建根节点
        if (root == null)
        {
            this.root = createTwoNode(kvPair, null, null);
            return;
        }
        // 根据Key找到适合插入该值的叶子节点
        Node<K, V> leafNode = findLeafByKey(key);
        // 创建2-节点
        Node<K, V> twoNode = createTwoNode(kvPair, null, null);
        // 向目标节点中合并2-节点
        merge(leafNode, twoNode);
    }
    
    /**
     * 合并节点
     * @param target 需要合并的目标节点
     * @param twoNode 需要合并到树中的2-节点
     */
    private void merge(Node<K, V> target, Node<K, V> twoNode)
    {
        // 如果目标节点为空，说明树的高度加1，此时重新设置根节点
        if (target == null)
        {
            this.root = twoNode;
            return;
        }
        KVPair<K, V> insertion = twoNode.less;
        // 目标节点是2-节点，直接合并，将2-节点升级成3-节点
        if (target.isTwoNode())
        {
            // 比较target的less元素和twoNode的less元素的大小
            if (insertion.key.compareTo(target.less.key) > 0)
            {
                // 将值合并到目标节点的较大值（grater)
                target.grater = insertion;
            }else {
                KVPair<K, V> temp = target.less;
                // insertion移动到target的less元素
                target.less = insertion;
                // 将target的less元素移动到grater
                target.grater = temp;
            }
            if (target.isLeaf())
            {
                return;
            }
            // 重新调整节点的指向
            // 1.target元素的center子树指向twoNode的left
            target.center = twoNode.left;
            // 2.target元素的right子树执行twoNode的center
            target.right = twoNode.center;
            // 3.重新调父towNode元素的parent指向
            target.center.parent = target;
            target.right.parent = target;
            return;
        }
        // 目标节点是3-节点
        if (target.isThreeNode())
        {
            Node<K, V> tempTwoNode = split(target, twoNode);
            merge(target.parent, tempTwoNode);
        }
    }
    
    /**
     * 分裂节点
     * @param target
     * @param twoNode
     */
    private Node<K, V> split(Node<K, V> target, Node<K, V> twoNode)
    {
        // 需要根据情况分解节点，重新调整节点，以达到平衡的状态
        // 1.找出需要升级的entry
        KVPair<K, V> upgrade = null;
        KVPair<K, V> insertion = twoNode.less;
        if (insertion.key.compareTo(target.less.key) < 0)
        {
            upgrade = target.less;
            target.less = insertion;
        }else if (insertion.key.compareTo(target.grater.key) > 0)
        {
            upgrade = target.grater;
            target.grater = insertion;
        }else
        {
            upgrade = insertion;
        }
        // 2.分裂节点
        // 1）构建一个临时的2-节点，作为分裂之后的左子树
        Node<K, V> tempLeft = createTwoNode(target.less, target.left, target.center);
        // 如果目标节点不是叶子节点，需要更新他左和中间子树的parent指向
        if (!target.isLeaf())
        {
            tempLeft.left.parent = tempLeft;
            tempLeft.left.center = tempLeft;
        }
        // 2）新的父节点
        Node<K, V> newTwoNode = createTwoNode(upgrade, tempLeft, twoNode);
        tempLeft.parent = newTwoNode;
        twoNode.parent = newTwoNode;
       return newTwoNode;
    }
    
    /**
     * 通过Key找到一个符合条件的叶子节点
     * @param key
     * @return
     */
    private Node<K, V> findLeafByKey(K key)
    {
        /**
         * 当前节点指针
         */
        Node<K, V> curr = root;
        
        while (curr != null && !curr.isLeaf())
        {
            // 2-节点
            if (curr.isTwoNode())
            {
                if (key.compareTo(curr.less.key) < 0)
                {
                    curr = curr.left;
                }else
                {
                    curr = curr.center;
                }
                continue;
            }
            
            // 3-节点
            if (curr.isThreeNode())
            {
                if (key.compareTo(curr.less.key) < 0)
                {
                    curr = curr.left;
                }else if (key.compareTo(curr.grater.key) > 0)
                {
                    curr = curr.right;
                }else
                {
                    curr = curr.center;
                }
                continue;
            }
        }
        return curr;
    }
    
    /**
     * 打印树结构
     */
    public void print()
    {
        // 双队列完成树的打印
        Queue<Node<K, V>> top = new LinkedList<>();
        Queue<Node<K, V>> low = new LinkedList<>();
        top.offer(this.root);
        while (!top.isEmpty() || !low.isEmpty())
        {
            // 如果上层节点遍历完了，换行，同时将下一层的节点全部放入上层
            if (top.isEmpty())
            {
                while (!low.isEmpty())
                {
                    top.offer(low.poll());
                }
                System.out.println();
            }
            Node node = top.poll();
            System.out.print(node + "    ");
            if (node.isLeaf())
            {
                continue;
            }
            low.offer(node.left);
            low.offer(node.center);
            if (node.isThreeNode())
            {
                low.offer(node.right);
            }
        }
        System.out.println();
    }
    
    /**
     * 创建一个有<b>左子树</b>和<b>中间子树</b>的2-节点
     * @param less 2-节点的元素
     * @param left 2-节点的左子树
     * @param center 2-节点的中间子树
     * @return
     */
    private Node<K, V> createTwoNode(KVPair<K, V> less, Node<K, V> left, Node<K, V> center)
    {
        return new Node<>(less, left, center);
    }
    
    /**
     * 创建键值对
     * @param key 指定的Key
     * @param value 指定的Value
     * @return
     */
    private KVPair<K, V> createPair(K key, V value)
    {
        return new KVPair<>(key, value);
    }
    
    /**
     * 节点类
     * @param <K> 节点中存储的Key类型
     * @param <V> 节点中存储的Value类型
     */
    static class Node<K extends Comparable<? super K>, V>
    {
        /**
         * 节点中的小值元素
         */
        KVPair<K, V> less;
    
        /**
         * 节点中的大值元素
         */
        KVPair<K, V> grater;
    
        /**
         * 左子树
         */
        Node<K, V> left;
    
        /**
         * 中间子树
         */
        Node<K, V> center;
    
        /**
         * 右子树
         */
        Node<K, V> right;
    
        /**
         * 父节点
         */
        Node<K, V> parent;
    
        public Node(KVPair<K, V> less)
        {
            this.less = less;
        }
        
        public Node(KVPair<K, V> less, Node<K, V> left, Node<K, V> center)
        {
            this.less = less;
            this.left = left;
            this.center = center;
        }
    
        public Node(KVPair<K, V> less, KVPair<K, V> grater)
        {
            this.less = less;
            this.grater = grater;
        }
        
        public boolean isLeaf()
        {
            return this.left == null;
        }
        
        public boolean isTwoNode()
        {
            return this.grater == null;
        }
        
        public boolean isThreeNode()
        {
            return this.grater != null;
        }
    
        public KVPair<K, V> getLess()
        {
            return less;
        }
    
        public void setLess(KVPair<K, V> less)
        {
            this.less = less;
        }
    
        public KVPair<K, V> getGrater()
        {
            return grater;
        }
    
        public void setGrater(KVPair<K, V> grater)
        {
            this.grater = grater;
        }
    
        public Node<K, V> getLeft()
        {
            return left;
        }
    
        public void setLeft(Node<K, V> left)
        {
            this.left = left;
        }
    
        public Node<K, V> getCenter()
        {
            return center;
        }
    
        public void setCenter(Node<K, V> center)
        {
            this.center = center;
        }
    
        public Node<K, V> getRight()
        {
            return right;
        }
    
        public void setRight(Node<K, V> right)
        {
            this.right = right;
        }
    
        public Node<K, V> getParent()
        {
            return parent;
        }
    
        public void setParent(Node<K, V> parent)
        {
            this.parent = parent;
        }
        
        public String toString()
        {
            return "(" + less + " , " + grater + ")";
        }
    }
    
    /**
     * 键值对对象
     * @param <K>
     * @param <V>
     */
    static class KVPair<K extends Comparable<? super K>, V>
    {
        /**
         * Key对象
         */
        K key;
        
        /**
         * Value对象
         */
        V value;
        
        public KVPair(K key, V value)
        {
            this.key = key;
            this.value = value;
        }
    
        public K getKey()
        {
            return this.key;
        }
        
        public V getValue()
        {
            return this.value;
        }
        
        public void setKey(K key)
        {
            this.key = key;
        }
        
        public void setValue(V value)
        {
            this.value = value;
        }
        
        @Override
        public String toString()
        {
            return "{" + key + " = " + value + "}";
        }
    }
}
