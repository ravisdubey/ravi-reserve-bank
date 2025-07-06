package com.demo.reserve.bank.account.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.demo.reserve.bank.account.api.model.Account;

public interface AccountRepository extends MongoRepository<Account, String> {

}
