package com.cloudstone.cloudhand.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cloudstone.cloudhand.data.Dish;

/**
 * 
 * @author xhc
 *
 */
public class DishBag implements Iterable<Dish> {
    private List<Dish> list = new ArrayList<Dish>();
    private Map<Integer, Dish> map = new HashMap<Integer, Dish>();
    
    public void put(Dish value) {
        list.add(value);
    }
    
    public void remove(int key, Dish value) {
        map.remove(key);
        list.remove(value);
    }
    
    public void clear() {
        map.clear();
        list.clear();
    }
    
    public int size() {
        return list.size();
    }
    
    public Dish getById(int dishId) {
        return map.get(dishId);
    }
    
    public Dish getByPos(int position) {
        return list.get(position);
    }

    @Override
    public Iterator<Dish> iterator() {
        return list.iterator();
    }

}
