package com.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bank.constants.TransactionType;

import jakarta.persistence.*;

@Entity
@Table(name = "TRANSACTIONS")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    private long transactionId;

    @Column(name = "ACCOUNT_NUMBER", nullable = false)
    private long accountNumber;

    /*
     * Many transactions belong to one account.
     *
     * accountNumber remains responsible for writing
     * the ACCOUNT_NUMBER column.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "ACCOUNT_NUMBER",
            referencedColumnName = "ACCOUNT_NUMBER",
            insertable = false,
            updatable = false
    )
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRANSACTION_TYPE", nullable = false)
    private TransactionType transactionType;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Column(name = "BALANCE_AFTER", nullable = false)
    private BigDecimal balanceAfter;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TRANSACTION_DATE", nullable = false)
    private LocalDateTime transactionDate;

    // Hibernate requires no-argument constructor
    public Transaction() {
        this.transactionDate = LocalDateTime.now();
    }

    public Transaction(long transactionId,
                       long accountNumber,
                       TransactionType transactionType,
                       BigDecimal amount,
                       BigDecimal balanceAfter,
                       String description) {

        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.description = description;
        this.transactionDate = LocalDateTime.now();
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", accountNumber=" + accountNumber +
                ", transactionType=" + transactionType +
                ", amount=" + amount +
                ", balanceAfter=" + balanceAfter +
                ", description='" + description + '\'' +
                ", transactionDate=" + transactionDate +
                '}';
    }
}