package com.leyunone.codex.util;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @author LeYunone
 * @create 2022/7/18
 */
public class UniqueSet<K,V> extends AbstractSet<V> implements Serializable {

    private static final long serialVersionUID = 1L;

    //set的底层是一个map，那么我们可以用其中的value，作为判断对象中的唯一属性的媒介
    private Map<K,V> map;

    private final Function<V,K> uniqueCondition;

    public UniqueSet(Function<V, K> uniqueCondition) {
        //建立一个插入有序的map集合
        map = new LinkedHashMap<>();
        this.uniqueCondition = uniqueCondition;
    }

    @Override
    public boolean add(V v){
        //如果V[对象] 申请出来的值[属性]，
        V put = map.put(this.uniqueCondition.apply(v), v);
        return null == put;
    }

    @Override
    public Iterator<V> iterator() {
        return map.values().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }
}