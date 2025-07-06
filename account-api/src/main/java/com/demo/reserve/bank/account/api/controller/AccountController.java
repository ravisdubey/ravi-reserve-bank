package com.demo.reserve.bank.account.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.reserve.bank.account.api.dto.AccountRequest;
import com.demo.reserve.bank.account.api.dto.AccountResponse;
import com.demo.reserve.bank.account.api.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping
	public ResponseEntity<String> createAccount(@RequestBody AccountRequest accountRequest) {
		accountService.createAccount(accountRequest);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body("Successfully set up a bank account for " + accountRequest.getAccountHolderName());
	}

	@GetMapping
	@ResponseStatus(value = HttpStatus.OK)
	public List<AccountResponse> getAllAccounts() {
		return accountService.getAllAccounts();
	}
	
    /**
     * Deletes a bank account based on the account holder name.
     *
     * @param accountRequest The account request containing the account holder name.
     * @return A ResponseEntity with a success message and HTTP status code 200 if the account was deleted successfully,
     *         or a ResponseEntity with an error message and HTTP status code 404 if the account was not found.
     */
    @DeleteMapping
    public ResponseEntity<String> deleteAccount(@RequestBody AccountRequest accountRequest) {
        try {
            accountService.deleteAccountByAccountHolderName(accountRequest.getAccountHolderName());
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted " +
                    accountRequest.getAccountHolderName() + "'s account.");
        } catch (RuntimeException runtimeException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(runtimeException.getMessage());
        }
    }
}
