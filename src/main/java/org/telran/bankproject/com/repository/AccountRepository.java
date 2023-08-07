package org.telran.bankproject.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telran.bankproject.com.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
//
}