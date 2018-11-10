package sample.comparators;

import sample.module.SimProcess;

import java.util.Comparator;

public class AvailableComparator implements Comparator<SimProcess>{

    @Override
    public int compare(SimProcess x, SimProcess y) {
        if(x.getNextRunTime() < y.getNextRunTime())
            return -1;
        else if(x.getNextRunTime() > y.getNextRunTime())
            return 1;
        else
            return 0;
    }
}
