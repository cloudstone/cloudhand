package com.cloudstone.cloudhand.util;

import java.util.List;
import java.util.Map;

import com.cloudstone.cloudhand.data.Dish;

public class DishBag {
    private List<Dish> list;
    private Map<Integer, Dish> map;
    
    public void put(int key, Dish value) {
        map.put(key, value);
        list.add(value);
    }
    
    public void remove(int key, Dish value) {
        map.remove(key);
        list.remove(value);
    }
    public Dish getById(int key) {
        return map.get(key);
    }
    public Dish getByPos(int position) {
        return list.get(position);
    }
}
