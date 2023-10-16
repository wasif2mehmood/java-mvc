package Model;

public class SubscriptionPlan {
    private String name;
    private int id;
    private String description;
    private double price;
    private int data;
    private int talkTime;
    // Add additional attributes as needed

    public SubscriptionPlan(int id, String name, String description, int data, int talkTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.data = data;
        this.talkTime = talkTime;
        setPricesBasedOnDataAndTalkTime();
    }

    public SubscriptionPlan(int id, String name, String description, int data, int talkTime, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.data = data;
        this.talkTime = talkTime;
        this.price = price;

    }

    public void setPricesBasedOnDataAndTalkTime() {
        if (data == 0 && talkTime == 0) {
            price = 0;
        } else if (data == 0) {
            price = talkTime * 0.01;
        } else if (talkTime == 0) {
            price = data * 0.01;
        } else {
            price = (data * 0.01) + (talkTime * 0.01);
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public int getData() {
        return data;
    }

    public int getTalkTime() {
        return talkTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setData(int data) {
        this.data = data;
    }

    public void setTalkTime(int talkTime) {
        this.talkTime = talkTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // generate remaining getters and setters
    

}
