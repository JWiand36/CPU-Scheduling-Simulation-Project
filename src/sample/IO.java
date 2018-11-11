package sample;

import sample.module.MainModule;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class IO {

    private MainModule module;

    IO(MainModule module){

        this.module = module;
    }

    void inputData(){
        try {
            File f = new File("input.txt");
            Scanner s = new Scanner(f);

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

            while(s.hasNext()){
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

                module.addProcess(name, arrival, cpu, io, process, priority);
            }

        }catch (FileNotFoundException e){e.printStackTrace();}
    }
}
