package com.aide.financial.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by computer on 2018/7/5.
 */

public class MapUtils<K, V> {

    private static MapUtils instance = null;

    private MapUtils() {
    }

    public static<K, V> MapUtils<K, V> getInstance(){
        if(instance == null){
            synchronized (MapUtils.class) {
                if(instance == null){
                    instance = new MapUtils<K, V>();
                }
            }
        }
        return instance;
    }


    /**
     * 获取 value 集合
     */
    public List<V> getValues(Map<K, V> map){
        List<V> list = new ArrayList<>();
        for (K key : map.keySet()) {
            list.add(map.get(key));
        }
        return list;
    }

    /**
     * 根据 Entry 通过 value 来找 key
     */
    public K getKey(Map<K, V> map, V value){
        K key= null;
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if(value.equals(entry.getValue())){
                key = entry.getKey();
            }
        }
        return key;
    }

}
