package com.lms.bean;

import java.util.Date;

public class Receipt {
    private int receiptId;
    private Date issueDate;
    private Date returnDate;

    public Receipt(int receiptId, Date issueDate, Date returnDate) {
        this.receiptId = receiptId;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}

