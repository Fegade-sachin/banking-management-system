package com.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bank.constants.AccountStatus;
import com.bank.constants.AccountType;

import jakarta.persistence.*;

@Entity
@Table(name = "ACCOUNTS")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_NUMBER")
    private long accountNumber;

    @Column(name = "CUSTOMER_ID", nullable = false)
    private long customerId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "CUSTOMER_ID",
            referencedColumnName = "CUSTOMER_ID",
            insertable = false,
            updatable = false
    )
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_TYPE", nullable = false)
    private AccountType accountType;

    @Column(name = "BALANCE", nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private AccountStatus status;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;

    public Account() {
        this.balance = BigDecimal.ZERO;
        this.status = AccountStatus.ACTIVE;
        this.createdDate = LocalDateTime.now();
    }

    public Account(long accountNumber, long customerId,
                   AccountType accountType, BigDecimal balance) {

        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.accountType = accountType;
        this.balance = balance;
        this.status = AccountStatus.ACTIVE;
        this.createdDate = LocalDateTime.now();
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", customerId=" + customerId +
                ", accountType=" + accountType +
                ", balance=" + balance +
                ", status=" + status +
                ", createdDate=" + createdDate +
                '}';
    }
}