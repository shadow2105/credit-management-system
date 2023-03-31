package model;

import java.sql.Date;

public class CreditStatement {
    private String statementId;
    private Date statementDate;
    private float amountDue;
    private float minimumPayment;
    private Date dueDate;
    private String accountNum;

    public CreditStatement(String statementId, Date statementDate, float amountDue, float minimumPayment, Date dueDate, String accountNum) {
        this.statementId = statementId;
        this.statementDate = statementDate;
        this.amountDue = amountDue;
        this.minimumPayment = minimumPayment;
        this.dueDate = dueDate;
        this.accountNum = accountNum;
    }

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public Date getStatementDate() {
        return statementDate;
    }

    public void setStatementDate(Date statementDate) {
        this.statementDate = statementDate;
    }

    public float getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(float amountDue) {
        this.amountDue = amountDue;
    }

    public float getMinimumPayment() {
        return minimumPayment;
    }

    public void setMinimumPayment(float minimumPayment) {
        this.minimumPayment = minimumPayment;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }
}
