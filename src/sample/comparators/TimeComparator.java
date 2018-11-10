package sample.comparators;

import sample.module.SimProcess;

import java.util.Comparator;

public class TimeComparator implements Comparator<SimProcess> {

    @Override
    public int compare(SimProcess x, SimProcess y) {
        if(x.getProcessTime() < y.getProcessTime())
            return -1;
        else if(x.getProcessTime() > y.getProcessTime())
            return 1;
        else
            return 0;
    }
}
