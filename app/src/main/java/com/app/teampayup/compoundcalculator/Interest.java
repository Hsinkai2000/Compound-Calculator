package com.app.teampayup.compoundcalculator;

import java.io.Serializable;

public class Interest implements Serializable{
    private double iterateInterest;
    private double totalInterest;
    private double finalBalance;

    public Interest(double iterateInterest, double totalInterest, double finalBalance) {
        this.iterateInterest = iterateInterest;
        this.totalInterest = totalInterest;
        this.finalBalance = finalBalance;
    }

    public double getIterateInterest() {
        return iterateInterest;
    }

    public void setIterateInterest(double iterateInterest) {
        this.iterateInterest = iterateInterest;
    }

    public double getTotalInterest() {
        return totalInterest;
    }

    public void setTotalInterest(double totalInterest) {
        this.totalInterest = totalInterest;
    }

    public double getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(double finalBalance) {
        this.finalBalance = finalBalance;
    }

    @Override
    public String toString(){

        return String.valueOf(iterateInterest) + " " + String.valueOf(totalInterest) + " " + String.valueOf(finalBalance);
    }
}
