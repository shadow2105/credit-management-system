package model;

import java.sql.Date;
import java.text.DecimalFormat;

public class Transaction {
    private String transactionId;
    private String transactionDate;
    private Date postDate;
    private String description;
    private char type;
    private float amount;
    private String accountNum;
    private float closingBalance;

    public Transaction(String transactionId, String transactionDate, Date postDate, String description, char type, float amount, String accountNum, float closingBalance) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.postDate = postDate;
        this.description = description;
        this.type = type;
        this.amount = amount;
        this.accountNum = accountNum;
        this.closingBalance = closingBalance;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    @Override
    public String toString() {
        DecimalFormat myFormatter = new DecimalFormat("###,###,###.00");
        return "Transaction Id          = " + transactionId +
                "\nAccount Number          = " + accountNum +
                "\nTransaction Date        = " + transactionDate.substring(0, 10) +
                "\nDescription             = " + description +
                "\nAmount                  = $" + myFormatter.format(amount) +
                "\nClosing Account Balance = $" + myFormatter.format(closingBalance);
    }
}
