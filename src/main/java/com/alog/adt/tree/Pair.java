package com.alog.adt.tree;

/**
 * 键值对对象
 *
 * @author lushenchen 2024/1/3 22:35
 * @since 1.0.0
 */
public interface Pair<K, V>
{
    K getKey();
    
    V getValue();
    
    void setKey(K key);
    
    void setValue(V value);
}
