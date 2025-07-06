package com.demo.reserve.bank.account.api.model;

import java.math.BigDecimal;
import java.util.Currency;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.demo.reserve.bank.account.api.serializer.CustomBigDecimalSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(value = "account")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Account {

    @Id
    private String id;
    private String accountNumber;
    private String accountHolderName;
    private BigDecimal balance;
    private Currency currency;
}
