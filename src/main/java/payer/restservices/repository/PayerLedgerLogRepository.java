package payer.restservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import payer.restservices.modal.PayerLedgerLogBO;

@Repository
public interface PayerLedgerLogRepository extends JpaRepository<PayerLedgerLogBO, Long> {

}