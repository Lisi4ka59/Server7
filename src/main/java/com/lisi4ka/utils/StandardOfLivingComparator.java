package com.lisi4ka.utils;

import com.lisi4ka.models.City;
import java.util.Comparator;
public class StandardOfLivingComparator implements Comparator<City> {
    @Override
    public int compare(City o1, City o2) {
        if (o1==null && o2 == null)
            return 0;
        else if (o1 != null && o2 ==null)
            return 1;
        else if (o1 == null)
            return -1;
        else if (o1.getStandardOfLiving()==null && o2.getStandardOfLiving()==null)
            return 0;
        else if (o1.getStandardOfLiving() != null && o2.getStandardOfLiving() ==null)
            return 1;
        else if (o1.getStandardOfLiving() ==null && o2.getStandardOfLiving() != null)
            return -1;
        else
            return - o1.getStandardOfLiving().compareTo(o2.getStandardOfLiving());
    }
}