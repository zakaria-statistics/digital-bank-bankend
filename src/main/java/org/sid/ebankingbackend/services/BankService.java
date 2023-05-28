package org.sid.ebankingbackend.services;

import org.sid.ebankingbackend.entities.BankAccount;
import org.sid.ebankingbackend.entities.CurrentAccount;
import org.sid.ebankingbackend.entities.SavingAccount;
import org.sid.ebankingbackend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankService {
    @Autowired
    BankAccountRepository bankAccountRepository;

    public void consult(){
        BankAccount bankAccount= bankAccountRepository.findById("1aa5fbea-d1a6-4c93-ae8d-b4bad4799841").orElse(null);
        if (bankAccount!=null){
            System.out.println("\t------Account details------");
            System.out.println(bankAccount.getId()+
                    "\n"+bankAccount.getStatus()+
                    "\n"+bankAccount.getBalance()+
                    "\n"+bankAccount.getCreatedAt()+
                    "\n"+bankAccount.getCustomer().getName()+
                    "\n"+bankAccount.getClass().getSimpleName());
            if (bankAccount instanceof CurrentAccount){
                System.out.println("OverDraft= "+((CurrentAccount) bankAccount).getOverDraft());
            }
            else if (bankAccount instanceof SavingAccount){
                System.out.println("InterestRate= "+((SavingAccount) bankAccount).getInterestRate());
            }
            System.out.println("\t------Operations------");
            System.out.println("Type\tDate\t\t\t\t\t\tAmount");
            bankAccount.getAccountOperations().forEach(op -> {

                System.out.println(op.getType()+"\t"+op.getOperationDate()+"\t"+op.getAmount());
            });
        }

    }
}
