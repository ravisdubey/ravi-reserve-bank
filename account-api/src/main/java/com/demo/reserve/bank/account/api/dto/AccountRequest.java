package com.demo.reserve.bank.account.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;

import com.demo.reserve.bank.account.api.serializer.CustomBigDecimalSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Data Transfer Object (DTO) class that represents the request payload for a bank account holder.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
    private String accountHolderName;
    private BigDecimal balance;
    private Currency currency;
}	