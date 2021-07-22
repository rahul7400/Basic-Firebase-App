package in.macrocodes.basic;

public class Product {
    String name,date,time,price,unit,quantity,type;

    Product(){

    }
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Product(String name, String date, String time, String price, String unit, String quantity, String type) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.price = price;
        this.unit = unit;
        this.quantity = quantity;
        this.type = type;
    }
}
