package payer.restservices.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import payer.restservices.modal.PayerBO;

@Repository
public interface PayerRepository extends JpaRepository<PayerBO,Long> {

//	@Query("SELECT b FROM t_payer b WHERE b.accountId = ?1 and b.status = ?2")
//	PayerBO findByAccountIdAndStatus(String accountId,String status);
	
//	@Query(value = "SELECT * FROM t_payer WHERE account_id = ?1 and status = ?2 ",nativeQuery=true )
//	PayerBO findByAccountIdAndStatus(String accountId,String status);
	
//	@Query("SELECT tp FROM t_payer tp WHERE tp.accountId =:accountId and tp.status =:status")
//	PayerBO findByAccountIdAndStatus(@Param("accountId") String accountId, @Param("status") String status);
	
	
//	@Query(value="SELECT * FROM t_payer WHERE account_id =:account_id and status =:status",nativeQuery = true)
//	PayerBO findByAccountIdAndStatus(@Param("account_id") String account_id, @Param("status") String status);
	

 


	//public PayerBO findByAccountIdAndStatus(String accountId, String status);
	
	
	
	Optional<PayerBO> findByAccountId(String username);

	//PayerBO findByLoginIdAndPasswdAndStatus(String username, String password, String string);
	
	//PayerBO findByMobileNoAndPasswdAndStatus(Long valueOf, String password, String string);

	PayerBO findByEmail(String username);

	PayerBO findByPhoneNumber(Long phoneNumber);

	PayerBO findByAadhaar(String aadhaar);

	PayerBO findByPan(String pan);

	PayerBO findByAccountIdAndStatus(String payerId, String string);

	
	
	//public PayerBO findByStatusAndAccountId(String status,String accountId);
	
	//List<PayerBO> getAllPayers();
	
	
	

}