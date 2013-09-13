package com.cloudstone.cloudhand.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloudstone.cloudhand.data.Dish;

/**
 * 
 * @author xhc
 *
 */
public class DishBag {
    private List<Dish> list = new ArrayList<Dish>();
    private Map<Integer, Dish> map = new HashMap<Integer, Dish>();
    private int size;
    
    public void put(int key, Dish value) {
        map.put(key, value);
        list.add(value);
        size++;
    }
    
    public void remove(int key, Dish value) {
        map.remove(key);
        list.remove(value);
        size--;
    }
    
    public int getSize() {
        return size;
    }
    public Dish getById(int key) {
        return map.get(key);
    }
    public Dish getByPos(int position) {
        return list.get(position);
    }
}
