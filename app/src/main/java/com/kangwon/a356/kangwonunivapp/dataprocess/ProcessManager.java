package com.kangwon.a356.kangwonunivapp.dataprocess;

public class ProcessManager {
    public static ProcessManager processManager;


    private ProcessManager()
    {}


    public ProcessManager getInstance()
    {
        if(processManager == null)
            processManager= new ProcessManager();
        return processManager;
    }





}
