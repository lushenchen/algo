package com.alog.adt.tree.btree;

import com.alog.adt.tree.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
     *
     * @return
     */
    public void insert(K key, V value)
    {
        insertByLeafNode(key, value);
    }
    
    /**
     * 在树中寻找指定key的元素
     *
     * @param key 指定的key
     * @return
     */
    public Node<K, V> find(K key)
    {
        return find(this.root, key);
    }
    
    /**
     * 先序遍历2-3tree
     *
     * @return
     */
    public List<Pair<K, V>> preOrder()
    {
        List<Pair<K, V>> list = new ArrayList<>();
        preOrder(this.root, list);
        return list;
    }
    
    /**
     * 中序遍历2-3tree
     *
     * @return
     */
    public List<Pair<K, V>> inOrder()
    {
        List<Pair<K, V>> list = new ArrayList<>();
        inOrder(this.root, list);
        return list;
    }
    
    /**
     * 后序遍历2-3tree
     *
     * @return
     */
    public List<Pair<K, V>> postOrder()
    {
        List<Pair<K, V>> list = new ArrayList<>();
        postOrder(this.root, list);
        return list;
    }
    
    /**
     * 返回指定元素的中序前驱元素<br/>
     * PS:这边是只找到了前驱节点，忽略3-叶子节点本身的前驱是自己的情况
     *
     * @param key 指定的键
     * @return
     */
    public Pair<K, V> precursor(K key)
    {
        Node<K, V> node = precursor(this.root, key);
        if (node == null)
        {
            return null;
        }
        if (node.isTwoNode())
        {
            return node.less;
        }
        if (node.grater.key.compareTo(key) < 0)
        {
            return node.grater;
        }
        return node.less;
    }
    
    /**
     * 返回指定元素的中序后继元素<br/>
     * PS:这边是只找到了后继节点，忽略3-叶子节点本身的后继是自己的情况
     *
     * @param key 指定的键
     * @return
     */
    public Pair<K, V> successor(K key)
    {
        Node<K, V> node = successor(this.root, key);
        if (node == null)
        {
            return null;
        }
        if (node.isTwoNode())
        {
            return node.less;
        }
        if (node.less.key.compareTo(key) < 0)
        {
            return node.grater;
        }
        return node.less;
    }
    
    /**
     * 获取指定2-3tree中的最小节点
     * @param node 指定的2-3树
     * @return
     */
    public Node<K, V> getMin(Node<K, V> node)
    {
        if (node.isLeaf())
        {
            return node;
        }
        return getMin(node.left);
    }
    
    /**
     * 获取指定2-3tree中的最大节点
     * @param node 指定的2-3树
     * @return
     */
    public Node<K, V> getMax(Node<K, V> node)
    {
        if (node == null || node.isLeaf())
        {
            return node;
        }
        if (node.isTwoNode())
        {
            return getMax(node.center);
        }
        return getMax(node.right);
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
     * 寻找指定元素所在节点的中序前驱
     *
     * @param node 指定节点
     * @param key 指定的键
     * @return 存在即返回，不存在返回NULl
     */
    private Node<K, V> precursor(Node<K, V> node, K key)
    {
        Node<K, V> current = find(node, key);
        if (current == null)
        {
            return null;
        }
        // 非叶子2-节点
        if (current.isTwoNode() && !current.isLeaf())
        {
            return getMax(current.left);
            
        }else if (current.isThreeNode() && !current.isLeaf())
        {
            // 非叶子3-节点，且有右子树
            if (key.compareTo(current.less.key) == 0)
            {
                // 较小值
                return getMax(current.left);
            }else
            {
                // 较大值
                return getMax(current.center);
            }
            
        }else
        {
            // 从指定树的根节点遍历，走到当前节点，current节点一定是在祖先节点的中间子树或者右子树
            Node<K, V> ancestor = node, precursor = null;
            while(ancestor != current)
            {
                // 比较current的key和ancestor中较大键的大小
                // ancestor中的key较小，则需要往右移动
                if (ancestor.isTwoNode() && ancestor.less.key.compareTo(key) < 0)
                {
                    precursor = ancestor;
                    ancestor = ancestor.center;
                }else if (ancestor.isThreeNode())
                {
                    if (ancestor.less.key.compareTo(key) < 0 && ancestor.grater.key.compareTo(key) > 0)
                    {
                        precursor = ancestor;
                        ancestor = ancestor.center;
                    }else if (ancestor.grater.key.compareTo(key) < 0)
                    {
                        precursor = ancestor;
                        ancestor = ancestor.right;
                    }else {
                        ancestor = ancestor.left;
                    }
                }else
                {
                    ancestor = ancestor.left;
                }
            }
            return precursor;
        }
    }
    
    /**
     * 寻找指定元素所在节点的中序后继
     *
     * @param node 指定节点
     * @param key 指定的键
     * @return 存在即返回，不存在返回NULl
     */
    private Node<K, V> successor(Node<K, V> node, K key)
    {
        // 查找节点
        Node<K, V> current = find(node, key);
        if (current == null)
        {
            return null;
        }
        // 非叶子2-节点
        if (current.isTwoNode() && !current.isLeaf())
        {
            // 中间子树的最小值
            return getMin(current.center);
        }else if (current.isThreeNode() && !current.isLeaf())
        {
            // 判断值是较大值还是较小值
            if (key.compareTo(current.less.key) == 0)
            {
                return getMin(current.center);
            }
            return getMin(current.right);
        }else {
            // 找祖先节点，此时节点的后驱在最靠近节点的祖先的左子树或者中间子树上
            Node<K, V> ancestor = node, successor = null;
            while (ancestor != current)
            {
                if (ancestor.isTwoNode() && ancestor.less.key.compareTo(key) > 0)
                {
                    successor = ancestor;
                    ancestor = ancestor.left;
                }else if (ancestor.isThreeNode())
                {
                    if (ancestor.less.key.compareTo(key) > 0)
                    {
                        successor = ancestor;
                        ancestor = ancestor.left;
                    }else if (ancestor.less.key.compareTo(key) < 0 && ancestor.grater.key.compareTo(key) > 0)
                    {
                        successor = ancestor;
                        ancestor = ancestor.center;
                    }else {
                        ancestor = ancestor.right;
                    }
                }else {
                    if (ancestor.isTwoNode())
                    {
                        ancestor = ancestor.center;
                    }else
                    {
                        ancestor = ancestor.right;
                    }
                }
            }
            return successor;
        }
    }
    
    /**
     * 先序遍历辅助
     *
     * @param node 指定的节点
     * @param list 收集节点元素的集合
     * @return
     */
    private void preOrder(Node<K, V> node, List<Pair<K, V>> list)
    {
        if (node == null)
        {
            return;
        }
        list.add(node.less);
        preOrder(node.left, list);
        // 2-叶子节点终止条件
        if (node.isLeaf() && node.isTwoNode())
        {
            return;
        }
        preOrder(node.center, list);
        // 3-节点
        if (node.isThreeNode())
        {
            list.add(node.grater);
        }
        preOrder(node.right, list);
    }
    
    /**
     * 中序遍历辅助
     *
     * @param node 指定的节点
     * @param list 收集节点元素的集合
     * @return
     */
    private void inOrder(Node<K, V> node, List<Pair<K, V>> list)
    {
        if (node == null)
        {
            return;
        }
        inOrder(node.left, list);
        list.add(node.less);
        // 2-叶子节点终止条件
        if (node.isLeaf() && node.isTwoNode())
        {
            return;
        }
        inOrder(node.center, list);
        // 3-节点
        if (node.isThreeNode())
        {
            list.add(node.grater);
            if (!node.isLeaf())
            {
                inOrder(node.right, list);
            }
        }
    }
    
    /**
     * 后序遍历辅助
     *
     * @param node 指定的节点
     * @param list 收集节点元素的集合
     */
    private void postOrder(Node<K, V> node, List<Pair<K, V>> list)
    {
        if (node == null)
        {
            return;
        }
        postOrder(node.left, list);
        // 2-叶子节点终止条件
        if (node.isLeaf() && node.isTwoNode())
        {
            list.add(node.less);
            return;
        }
        postOrder(node.center, list);
        postOrder(node.right, list);
        list.add(node.less);
        // 3-节点
        if (node.isThreeNode())
        {
            list.add(node.grater);
        }
    }
    
    /**
     * 通过子节点插入元素
     *
     * @param key   待插入的元素Key
     * @param value 待插入的元素Value
     */
    private void insertByLeafNode(K key, V value)
    {
        Pair<K, V> kvPair = createPair(key, value);
        // 如果根节点为空，创建根节点
        if (root == null)
        {
            this.root = createTwoNode(kvPair, null, null);
            return;
        }
        // 根据Key找到适合插入该值的叶子节点
        Node<K, V> leafNode = findLeafByKey(key);
        // 创建一个新的2-节点
        Node<K, V> twoNode = createTwoNode(kvPair, null, null);
        // 向目标节点中合并2-节点
        merge(leafNode, twoNode);
    }
    
    /**
     * 合并节点
     *
     * @param target  需要合并的目标节点
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
            }
            else
            {
                KVPair<K, V> temp = target.less;
                // insertion移动到target的less元素
                target.less = insertion;
                // 将target的less元素移动到grater
                target.grater = temp;
            }
            // 如果目标节点是叶子节点，直接合并
            if (target.isLeaf())
            {
                return;
            }
            // target成为一个3-节点，重新调整节点的指向
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
            // 将临时4-节点分裂成2-节点
            Node<K, V> tempTwoNode = split(target, twoNode);
            // 合并目标节点的父节点和分裂出来的2-节点
            merge(target.parent, tempTwoNode);
        }
    }
    
    /**
     * 分裂节点
     *
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
        }
        else if (insertion.key.compareTo(target.grater.key) > 0)
        {
            upgrade = target.grater;
            target.grater = insertion;
        }
        else
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
     * 从指定节点开始寻找Key
     *
     * @param node 指定的节点
     * @param key  需要寻找的key
     * @return
     */
    private Node<K, V> find(Node<K, V> node, K key)
    {
        // 未找到指定的key
        if (node == null)
        {
            return null;
        }
        // 终止条件
        // 1.节点的较小值匹配
        if (key.compareTo(node.less.key) == 0)
        {
            return node;
        }
        // 2.如果是3-节点，节点的较大值匹配
        if (node.isThreeNode() && key.compareTo(node.grater.key) == 0)
        {
            return node;
        }
        if (key.compareTo(node.less.key) < 0)
        {
            return find(node.left, key);
        }
        else if (node.isTwoNode())
        {
            return find(node.center, key);
        }
        else if (key.compareTo(node.grater.key) < 0)
        {
            return find(node.center, key);
        }
        else
        {
            return find(node.right, key);
        }
    }
    
    /**
     * 通过Key找到一个符合条件的叶子节点
     *
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
                }
                else
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
                }
                else if (key.compareTo(curr.grater.key) > 0)
                {
                    curr = curr.right;
                }
                else
                {
                    curr = curr.center;
                }
            }
        }
        return curr;
    }
    
    /**
     * 创建一个有<b>左子树</b>和<b>中间子树</b>的2-节点
     *
     * @param less   2-节点的元素
     * @param left   2-节点的左子树
     * @param center 2-节点的中间子树
     * @return
     */
    private Node<K, V> createTwoNode(Pair<K, V> less, Node<K, V> left, Node<K, V> center)
    {
        return new Node<>(less, left, center);
    }
    
    /**
     * 创建键值对
     *
     * @param key   指定的Key
     * @param value 指定的Value
     * @return
     */
    private Pair<K, V> createPair(K key, V value)
    {
        return new KVPair<>(key, value);
    }
    
    public Node<K, V> getRoot()
    {
        return root;
    }
    
    /**
     * 节点类
     *
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
        
        public Node(Pair<K, V> less)
        {
            this.less = (KVPair<K, V>) less;
        }
        
        public Node(Pair<K, V> less, Node<K, V> left, Node<K, V> center)
        {
            this.less = (KVPair<K, V>) less;
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
     *
     * @param <K>
     * @param <V>
     */
    static class KVPair<K extends Comparable<? super K>, V> implements Pair<K, V>
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
