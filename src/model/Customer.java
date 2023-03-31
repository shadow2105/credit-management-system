package model;

public class Customer extends User {
    private String address;
    private String mobileNum;
    private String email;
    private String occupation;

    public Customer(String firstName, String lastName, String userId, String password) {
        super(firstName, lastName, userId, password);
    }

    public Customer(String firstName, String lastName, String email) {
        super(firstName, lastName);
        this.email = email;
    }

    public Customer(String firstName, String lastName, String address, String mobileNum, String email, String occupation) {
        super(firstName, lastName);
        this.address = address;
        this.mobileNum = mobileNum;
        this.email = email;
        this.occupation = occupation;
    }
    public Customer(String firstName, String lastName, String userId, String password, String address, String mobileNum, String email, String occupation) {
        super(firstName, lastName, userId, password);
        this.address = address;
        this.mobileNum = mobileNum;
        this.email = email;
        this.occupation = occupation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
