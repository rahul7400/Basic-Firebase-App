package in.macrocodes.basic.Models;

public class TransactionUpdateRequest {
    String name,unit,typeOfTransaction;
    int id,totalPrice,quantity,pricePerItem;

    TransactionUpdateRequest(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPricePerItem() {
        return pricePerItem;
    }

    public void setPricePerItem(int pricePerItem) {
        this.pricePerItem = pricePerItem;
    }

    public TransactionUpdateRequest(String name, String unit, String typeOfTransaction, int id, int totalPrice, int quantity, int pricePerItem) {
        this.name = name;
        this.unit = unit;
        this.typeOfTransaction = typeOfTransaction;
        this.id = id;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
    }
}
