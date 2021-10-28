package payer.restservices.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import payer.restservices.modal.MerchantBO;

@Repository
public interface MerchantRepository extends JpaRepository<MerchantBO, Long> {

	Optional<MerchantBO> findByMerchantId(String username);

	MerchantBO findByEmail(String username);

}