package org.sid.ebankingbackend.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.ebankingbackend.entities.AccountOperation;
import org.sid.ebankingbackend.entities.Customer;
import org.sid.ebankingbackend.entities.enums.AccountStatus;

import java.util.Date;
import java.util.List;


@Data
public class SavingAccountDTO extends BankAccountDTO{
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRaTe;
}
