package sample.comparators;

import sample.module.SimProcess;

import java.util.Comparator;

public class PriorityComparator implements Comparator<SimProcess>{

    @Override
    public int compare(SimProcess x, SimProcess y) {
        if(x.getPriority() < y.getPriority())
            return -1;
        else if(x.getPriority() > y.getPriority())
            return 1;
        else
            return 0;
    }
}
