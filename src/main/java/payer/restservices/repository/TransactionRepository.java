package payer.restservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import payer.restservices.modal.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
