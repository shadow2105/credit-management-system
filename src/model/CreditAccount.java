package model;

import java.text.DecimalFormat;

public class CreditAccount {
    private String userId;
    private String accountNum;
    private float creditLimit;
    private float currentBalance;

    public CreditAccount(String accountNum, float creditLimit, String userId) {
        this.accountNum = accountNum;
        this.creditLimit = creditLimit;
        this.currentBalance = 0.0F;
        this.userId = userId;
    }

    public CreditAccount(String userId, String accountNum, float creditLimit, float currentBalance) {
        this.userId = userId;
        this.accountNum = accountNum;
        this.creditLimit = creditLimit;
        this.currentBalance = currentBalance;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }
    public float getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(float creditLimit) {
        this.creditLimit = creditLimit;
    }
    public float getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(float currentBalance) {
        this.currentBalance = currentBalance;
    }

    @Override
    public String toString() {
        DecimalFormat myFormatter = new DecimalFormat("###,###,###.00");
        return "[ " +
                "Account Number = " + accountNum +
                ", Credit Limit = $" + myFormatter.format(creditLimit) +
                ", Current Balance = $" + myFormatter.format(currentBalance) +
                " ]";
    }
}
