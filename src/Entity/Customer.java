package Entity;

public class Customer {
    private String PIN;
    private String name;
    private String numOfAcc;
    private float surplus;

    public String getPIN() { return PIN; }

    public void setPIN(String PIN) { this.PIN = PIN; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getNumOfAcc() { return numOfAcc; }

    public void setNumOfAcc(String numOfAcc) { this.numOfAcc = numOfAcc; }

    public float getSurplus() { return surplus; }

    public void setSurplus(long surplus) { this.surplus = surplus; }
}
