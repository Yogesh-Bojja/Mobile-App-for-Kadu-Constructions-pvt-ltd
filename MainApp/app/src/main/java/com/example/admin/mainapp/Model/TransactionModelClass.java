package com.example.admin.mainapp.Model;

/**
 * Created by Admin on 21-01-2018.
 */

public class TransactionModelClass {
    private String nameOfCustomer,dateOfIsuue,dateOfReturn,employeePerson,timeOfSubmit,transactionID;
    private int noOfPlates,pricePerPlate,totalPrice,advanceAmount,remainingAmount;

    public TransactionModelClass(){

    }

    public TransactionModelClass(String nameOfCustomer, String dateOfIsuue, String dateOfReturn, String employeePerson,
                                 String timeOfSubmit, int noOfPlates, int pricePerPlate, int advanceAmount,String TID) {
        this.nameOfCustomer = nameOfCustomer;
        this.dateOfIsuue = dateOfIsuue;
        this.dateOfReturn = dateOfReturn;
        this.employeePerson = employeePerson;
        this.timeOfSubmit = timeOfSubmit;
        this.noOfPlates = noOfPlates;
        this.pricePerPlate = pricePerPlate;
        this.advanceAmount = advanceAmount;
        this.transactionID = TID;
        this.totalPrice = (noOfPlates*pricePerPlate);
        this.remainingAmount = (totalPrice-advanceAmount);
    }

    public String getNameOfCustomer() { return nameOfCustomer;}

    public String getTransactionID() {  return transactionID; }

    public String getDateOfIsuue() {
        return dateOfIsuue;
    }

    public String getDateOfReturn() {
        return dateOfReturn;
    }

    public String getEmployeePerson() {
        return employeePerson;
    }

    public String getTimeOfSubmit() {
        return timeOfSubmit;
    }

    public int getNoOfPlates() {
        return noOfPlates;
    }

    public int getPricePerPlate() {
        return pricePerPlate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getAdvanceAmount() {
        return advanceAmount;
    }

    public int getRemainingAmount() {
        return remainingAmount;
    }
}
