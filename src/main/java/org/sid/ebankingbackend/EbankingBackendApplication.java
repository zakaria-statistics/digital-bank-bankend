package org.sid.ebankingbackend;

import org.sid.ebankingbackend.entities.*;
import org.sid.ebankingbackend.entities.enums.AccountStatus;
import org.sid.ebankingbackend.entities.enums.operationType;
import org.sid.ebankingbackend.exceptions.BalanceNotSufficientException;
import org.sid.ebankingbackend.exceptions.BankAccountNotFoundException;
import org.sid.ebankingbackend.exceptions.CustomerNotFoundException;
import org.sid.ebankingbackend.repositories.AccountOperationRepository;
import org.sid.ebankingbackend.repositories.BankAccountRepository;
import org.sid.ebankingbackend.repositories.CustomerRepository;
import org.sid.ebankingbackend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBackendApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
		return args -> {
			Stream.of("Hassan", "Ayman", "Hiba").forEach(
					name->{
						Customer customer= new Customer();
						customer.setName(name);
						customer.setEmail(name+"@gmail.com");
						bankAccountService.saveCustomer(customer);
					});
			bankAccountService.listCustomers().forEach(
					customer -> {
						try {
							CurrentAccount currentAccount= bankAccountService.saveCurrentBankAccount(100000*Math.random(), 9000, customer.getId());
							SavingAccount savingAccount= bankAccountService.saveSavingBankAccount(100000*Math.random(), 5.5, customer.getId());

						} catch (CustomerNotFoundException e) {
							e.printStackTrace();
						}
					}
			);
			List<BankAccount> bankAccountList = bankAccountService.bankAccountList();
			for (BankAccount bankAccount:bankAccountList) {
				for (int i=0; i<10; i++){
					try {
						bankAccountService.credit(bankAccount.getId(), 10000+Math.random()*100000,"Credit");
						bankAccountService.debit(bankAccount.getId(), 9000+Math.random()*1000,"Debit");
					} catch (BankAccountNotFoundException | BalanceNotSufficientException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	//@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
							BankAccountRepository bankAccountRepository,
							AccountOperationRepository accountOperationRepository){
		return args -> {
			Stream.of("Hassna","Adam","Sarah").forEach(name->{
				Customer customer= new Customer();
				customer.setName(name);
				customer.setEmail(name+"@outlook");
				customerRepository.save(customer);
			});

			customerRepository.findAll().forEach(customer -> {
				CurrentAccount currentAccount= new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setCreatedAt(new Date());
				currentAccount.setCustomer(customer);
				currentAccount.setBalance(Math.random()*9000);
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setOverDraft(5000);
				bankAccountRepository.save(currentAccount);

				SavingAccount savingAccount= new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setCreatedAt(new Date());
				savingAccount.setCustomer(customer);
				savingAccount.setBalance(Math.random()*9000);
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setInterestRate(5.5);
				bankAccountRepository.save(savingAccount);
			});
			bankAccountRepository.findAll().forEach(account->{
				for (int i=0;i<10;i++) {
					AccountOperation accountOperation = new AccountOperation();
					accountOperation.setOperationDate(new Date());
					accountOperation.setAmount(1000 * Math.random());
					accountOperation.setType(Math.random() > 0.5 ? operationType.CREDIT : operationType.DEBIT);
					accountOperation.setBankAccount(account);
					accountOperationRepository.save(accountOperation);
				}
			});
		};
	}

}
