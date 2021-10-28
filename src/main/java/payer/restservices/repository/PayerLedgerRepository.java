package payer.restservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import payer.restservices.modal.PayerLedgerBO;

@Repository
public interface PayerLedgerRepository extends JpaRepository<PayerLedgerBO, Long> {

	//@Query(value = "select pl.* from t_payer p,t_payer_rate_of_interest pri,t_payer_ledger pl where p.payer_id = pri.payer_id and pri.rate_of_interset_id = pl.rate_of_interest_id and p.payer_id=:ID and DATE_FORMAT(emi_date,'%Y-%m-%d') between StartDate and EndDate", nativeQuery = true)
	//@Query(value = "select pl.* from t_payer p,t_payer_rate_of_interest pri,t_payer_ledger pl where  p.payer_id = pri.payer_id and pri.rate_of_interset_id = pl.rate_of_interest_id  and pl.rate_of_interest_id=?1 and DATE_FORMAT(emi_date,'%Y-%m-%d') between ?2 and ?3", nativeQuery = true)

	//List<PayerLedgerBO> findBypayerLedgers(Long id,String startDate, String endDate);
	//List<PayerLedgerBO> findBypayerLedgers(@Param("ID") Long id, @Param("StartDate")String startDate,@Param("EndDate") String endDate);
	
	//@Query(value = "select t_payer_ledger.rate_of_interest_id  from (( t_payer inner join t_payer_rate_of_interest on t_payer.payer_id=t_payer_rate_of_interest.payer_id) inner join t_payer_ledger on t_payer_rate_of_interest.rate_of_interset_id=t_payer_ledger.rate_of_interest_id) where t_payer_ledger.rate_of_interest_id=:ID and DATE_FORMAT(emi_date,'%Y-%m-%d') between '2021-07-10 00:00:00' and 2021-12-10 00:00:00",nativeQuery = true)
	//@Query(value = "select t_payer_ledger.* from (( t_payer inner join t_payer_rate_of_interest on t_payer.payer_id=t_payer_rate_of_interest.payer_id) inner join t_payer_ledger on t_payer_rate_of_interest.rate_of_interset_id=t_payer_ledger.rate_of_interest_id) where t_payer_ledger.rate_of_interest_id=?1 and DATE_FORMAT(emi_date,'%Y-%m-%d') between ?2 and ?3",nativeQuery = true)

	//@Query(value = "SELECT * FROM (( t_payer INNER JOIN t_payer_rate_of_interest ON t_payer.payer_id=t_payer_rate_of_interest.payer_id) INNER JOIN t_payer_ledger ON t_payer_rate_of_interest.rate_of_interset_id=t_payer_ledger.rate_of_interest_id) WHERE t_payer_ledger.rate_of_interest_id=?1 AND DATE_FORMAT(emi_date,'%Y-%m-%d') BETWEEN ?2 AND ?3",nativeQuery = true)

	//@Query(value = "SELECT t_payer_ledger.* FROM (( t_payer INNER JOIN t_payer_rate_of_interest ON t_payer.payer_id=t_payer_rate_of_interest.payer_id) INNER JOIN t_payer_ledger ON t_payer_rate_of_interest.rate_of_interset_id=t_payer_ledger.rate_of_interest_id) WHERE t_payer_ledger.rate_of_interest_id=?1 AND DATE_FORMAT(emi_date,'%Y-%m-%d') BETWEEN ?2 AND ?3",nativeQuery = true)
//	Select TableB.* 
//	From TableA 
//	Inner Join TableB 
//	    On TableB.ID = TableA.ID
	@Query(value="select t_payer_ledger.* from (( t_payer inner join t_payer_rate_of_interest on t_payer.payer_id=t_payer_rate_of_interest.payer_id) inner join t_payer_ledger on t_payer_rate_of_interest.rate_of_interest_id=t_payer_ledger.rate_of_interest_id) where t_payer_ledger.rate_of_interest_id=:id and DATE_FORMAT(emi_date,'%Y-%m-%d') between :startDate and :endDate ",nativeQuery=true)
//List<PayerLedgerBO> findBypayerLedgers(Long id,String startDate, String endDate);
	List<PayerLedgerBO> findBypayerLedgers(@Param("id") Long id, @Param("startDate") String startDate,@Param("endDate") String endDate);

	
	
	//select t_payer_ledger.* from (( t_payer inner join t_payer_rate_of_interest on t_payer.payer_id=t_payer_rate_of_interest.payer_id) inner join t_payer_ledger on t_payer_rate_of_interest.rate_of_interset_id=t_payer_ledger.rate_of_interest_id) where t_payer_ledger.rate_of_interest_id=51 and DATE_FORMAT(emi_date,'%Y-%m-%d') between '2021-07-10' and '2021-12-10'
	
	
}