package sample;

import sample.display.DisplayController;
import sample.module.MainModule;
import sample.module.SimProcess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

class IO {

    private DisplayController display;

    IO(DisplayController display){
        this.display = display;
    }

    void inputData(){
        try {
            File f = new File("input.txt");
            Scanner s = new Scanner(f);

            ArrayList<SimProcess> processes = new ArrayList<>();

            String name;
            int arrival;
            int cpu;
            int io;
            int process;
            int priority;

            //These next lines are to bypass the info text given to the user.
            s.nextLine();
            s.nextLine();

            s.nextLine();
            s.nextLine();
            s.nextLine();
            s.nextLine();
            s.nextLine();
            s.nextLine();
            s.nextLine();

            while(s.hasNext() && processes.size() < 20){
                name = s.nextLine();
                arrival = Integer.parseInt(s.nextLine());
                cpu = Integer.parseInt(s.nextLine());
                io = Integer.parseInt(s.nextLine());
                process = Integer.parseInt(s.nextLine());
                priority = Integer.parseInt(s.nextLine());

                //This is to help space out each process
                if(s.hasNext()) {
                    s.nextLine();
                }

                processes.add(new SimProcess(processes.size()+1, name, arrival, cpu, io, process, priority));
            }

            display.setProcesses(processes);

        }catch (FileNotFoundException e){e.printStackTrace();}
    }

    void saveData(ArrayList<SimProcess> processes){
        try {
            PrintWriter writer = new PrintWriter("input.txt");

            writer.println("LeaveTextBelow");
            writer.println("ProcessName");
            writer.println("ArrivalTime");
            writer.println("CPUBurstTime");
            writer.println("IOTime");
            writer.println("ProcessTime");
            writer.println("Priority");
            writer.println("LeaveSpace");
            writer.println("LeaveTextAbove");

            for(SimProcess process: processes){
                writer.println(process.getName());
                writer.println(process.getArrivalTime());
                writer.println(process.getCpuTime());
                writer.println(process.getIoTime());
                writer.println(process.getProcessTime());
                writer.println(process.getPriority());
                writer.println("");
            }

            writer.close();

        }catch (FileNotFoundException nf){
            nf.printStackTrace();
        }
    }
}
