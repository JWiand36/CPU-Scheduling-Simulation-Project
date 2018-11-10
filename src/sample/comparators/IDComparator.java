package sample.comparators;

import sample.module.SimProcess;

import java.util.Comparator;

public class IDComparator implements Comparator<SimProcess> {

    @Override
    public int compare(SimProcess x, SimProcess y) {
        if(x.getProcessId() < y.getProcessId())
            return -1;
        else if(x.getProcessId() > y.getProcessId())
            return 1;
        else
            return 0;
    }
}
