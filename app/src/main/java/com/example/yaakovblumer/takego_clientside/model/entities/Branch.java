package com.example.yaakovblumer.takego_clientside.model.entities;


public class Branch
{
    protected String branchAddress;
    protected int capacityOfCar;
    protected String branchNum;
    protected String administratorName;

    public Branch(String address, int capacityOfCar, String branchNum, String administratorName)
    {
        this.branchAddress = address;
        this.capacityOfCar = capacityOfCar;
        this.branchNum = branchNum;
        this.administratorName=administratorName;
    }

    public Branch() { }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String address) {
        this.branchAddress = address;
    }

    public int getCapacityOfCar() {
        return capacityOfCar;
    }

    public void setCapacityOfCar(int capacityOfCar) { this.capacityOfCar = capacityOfCar; }

    public String getBranchNum() { return branchNum; }

    public void setBranchNum(String branchNum) {
        this.branchNum = branchNum;
    }

    public String getAdministratorName() {
        return administratorName;
    }

    public void setAdministratorName(String administratorName) { this.administratorName = administratorName; }


    public String ToString()
    {
        return  "\n"+branchAddress +"\n"+capacityOfCar+"\n"+branchNum+"\n"+administratorName+"\n";
    }


}
