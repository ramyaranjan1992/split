package payer.restservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import payer.restservices.modal.PayerRateOfInterestBO;

@Repository
public interface PayerRateOfInterestRepository extends JpaRepository<PayerRateOfInterestBO, Long> {

}