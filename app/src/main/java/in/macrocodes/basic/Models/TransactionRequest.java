package in.macrocodes.basic.Models;

public class TransactionRequest {
    String name,date,time,unit,typeOfTransaction;
    int quantity,totalPrice,pricePerItem;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public void setTypeOfTransaction(String typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(int pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    public TransactionRequest(String name, String date, String time, String unit, String typeOfTransaction, int quantity, int totalPrice, int pricePerItem) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.unit = unit;
        this.typeOfTransaction = typeOfTransaction;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.pricePerItem = pricePerItem;
    }
}
