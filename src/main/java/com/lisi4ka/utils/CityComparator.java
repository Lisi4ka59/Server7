package com.lisi4ka.utils;

import com.lisi4ka.models.City;

import java.util.Comparator;

/**
 * 
 */
public class CityComparator implements Comparator<City> {
    /**
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     */
    @Override
    public int compare(City o1, City o2) {
        if (o1==null && o2 == null)
            return 0;
        else if (o1 != null && o2 ==null)
            return 1;
        else if (o1 == null)
            return -1;
        else
            return o1.getName().compareToIgnoreCase(o2.getName());
    }
}
