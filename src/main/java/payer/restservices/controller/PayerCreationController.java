package payer.restservices.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.mongodb.BasicDBObject;

import io.swagger.annotations.Api;
import payer.restservices.dto.Payer;
import payer.restservices.dto.PayerInformation;
import payer.restservices.modal.PayerBO;
import payer.restservices.modal.PayerLedgerBO;
import payer.restservices.modal.PayerRateOfInterestBO;
import payer.restservices.repository.PayerLedgerLogRepository;
import payer.restservices.repository.PayerLedgerRepository;
import payer.restservices.repository.PayerRateOfInterestRepository;
import payer.restservices.repository.PayerRepository;
import payer.restservices.util.Constants;
import payer.restservices.util.DU;

@Controller
@RequestMapping("/payer")
@Api(value = "Merchant", description = "REST API for Payer", tags = { "Merchant" })
public class PayerCreationController<V> {

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private PayerRepository payerRepository;

	@Autowired
	private PayerRateOfInterestRepository payerRateOfInterestRepository;

	@Autowired
	private PayerLedgerRepository payerLedgerRepository;

	@Autowired
	private PayerLedgerLogRepository payerLedgerLogRepository;

	@Autowired
	private Environment env;

	private String endDate;

	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/creation", method = RequestMethod.POST, produces =MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView creation(@RequestBody Payer payer, Principal principal) {
		
		System.out.println(payer);
		

		ModelAndView modelAndView = new ModelAndView("output1");
		
		HashMap<String,Object> hashMap = new HashMap<String,Object>();

		try {
			
			System.out.println("payer Creation....................................\n\n");
			
			PayerBO payerbo = payerRepository.findByPhoneNumber(payer.getPhoneNumber());

			if (payerbo != null) {
				hashMap.put("Invalid","Error : "+payerbo.getPhoneNumber()+" already assigned to a Payer. So, Please "
						+ "enter an other phone number.");
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;
			}//done
			
			payerbo = payerRepository.findByEmail(payer.getEmail());

			if (payerbo != null) {
				hashMap.put("Invalid","Error : "+payerbo.getEmail()+" already assigned to a Payer. So, Please enter"
						+ " an other email id.");
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;
			}//done

			payerbo = payerRepository.findByAadhaar(payer.getAadhaar());

			if (payerbo != null) {
				hashMap.put("Invalid","Error : "+payerbo.getAadhaar()+" already assigned to a Payer. So, Please enter "
						+ "an other Aadhaar number.");
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;
			}//done
			
			payerbo = payerRepository.findByPan(payer.getPan());

			if (payerbo != null) {
				hashMap.put("Invalid","Error : "+payerbo.getPan()+" already assigned to a Payer. So, Please enter an "
						+ "other Pan number.");
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;
			}//done
			
			
			if (payer.getFullName() == "" || payer.getFullName().length() <= 4) {
				if(payer.getFullName()=="") {
					hashMap.put("Invalid","Error : Payer fullname should not have empty.");
				}else {
					hashMap.put("Invalid","Error : Payer fullname length should be more than 4 characters.");
				}
				
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}//done
			
			if (payer.getEmail() == "" || verifyEmail(payer.getEmail())) {
				if (payer.getEmail()=="") {
					hashMap.put("Invalid", "Error : Payer email should not have empty");
				} else {
					hashMap.put("Invalid", "Error : Payer email is invalid");
				}

				
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}//done
			
			if (payer.getPan() == "" || payer.getPan().length()<=2) {
				
				if(payer.getPan()=="") {
					hashMap.put("Invalid", "Error : Payer pan cannot be empty");
				}else {
					hashMap.put("Invalid","Error : Payer pan length should be more than 2 characters.");
				}

				
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}//done
			
			if (payer.getParentName() == "" || payer.getParentName().length() <= 4) {
				
				if(payer.getPan()=="") {
					hashMap.put("Invalid", "Error : Payer Parent name cannot be empty");
				}else {
					hashMap.put("Invalid","Error : Payer Parent Name length should be more than 2 characters.");
				}

				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}//done
			
			System.out.println("dob = "+payer.getDob());
			
			if (payer.getDob() == "") {

				hashMap.put("Invalid", "Error : Payer DOB cannot be empty");
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}//done
			
			System.out.println("loanEMIDate = "+payer.getLoanEmiDate());
			
			if (payer.getLoanEmiDate()==""|| isValidDate(payer.getLoanEmiDate())) {
				
				if (payer.getLoanEmiDate()=="") {
					hashMap.put("Invalid", "Error : Payer Loan EMI Date can't be empty");
					
				} else {
					hashMap.put("Invalid", "Error : Payer Loan EMI Date is invalid and only future date will be enter");

				}

				
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}//done
			
			if (payer.getGender()=="") {

				hashMap.put("Invalid", "Error : Payer gender connot be null");
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}//done
			if (payer.getAadhaar() == null || verifyAdhar(payer.getAadhaar())) {
				
				if (payer.getAadhaar()=="") {
					hashMap.put("Invalid", "Error : Payer Aadhaar Number can't be null");
				} else {
					hashMap.put("Invalid", "Error : Payer Aadhaar Number is invalid");
				}

				
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}//done
			
			System.out.println("Phone Number = "+payer.getPhoneNumber());
			
			if (payer.getPhoneNumber() == null || verifyPhoneNumber(payer.getPhoneNumber())) {
				
				if (payer.getPhoneNumber()==null) {
					
					hashMap.put("Invalid", "Error : Payer Phone Number can't be null");
				} else {
					hashMap.put("Invalid", "Error : Payer Phone Number is not valid");
				}
	
				
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}//done

			if (payer.getAlternativePhoneNumber() == null || verifyPhoneNumber(payer.getAlternativePhoneNumber())) {
				
				if (payer.getPhoneNumber()==null) {
					
					hashMap.put("Invalid", "Error : Payer Alternative Phone Number can't be null");
				} else {
					hashMap.put("Invalid", "Error : Payer Alternative Phone Number is not valid");
				}

				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}//done
			
			if (payer.getCity() =="") {

				hashMap.put("Invalid", "Error : Payer City name can't be null");
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}//done
			
			if (payer.getState() == "") {

				hashMap.put("Invalid", "Error : Payer State name can't be null");
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}//done
			
			if (payer.getAddress() == "") {

				hashMap.put("Invalid", "Error : Payer Address can't be null");
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}//done
			
			System.out.println("Pin Number = "+payer.getPin());
			
			if (payer.getPin() <= 0) {

				hashMap.put("Invalid", "Error : Payer PIN can't be null ");
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}//done
			
			if (payer.getSchemeName() == "") {

				hashMap.put("Invalid", "Error : Payer Scheme Name can't be null");
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}//done
			
			if (payer.getRateOfInterest() <= 0.0) {

				hashMap.put("Invalid", "Error : Payer Rate of Interest is invalid");
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}//done
			
			if (payer.getGst() <= 0.0) {

				hashMap.put("Invalid", "Error : Payer should have valid Gst");
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}//done
			
			if (payer.getLoanTenure() <= 0) {

				hashMap.put("Invalid", "Error : Payer Loan Tenure should be valid");
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}//done
			
			if (payer.getLoanAmount() <= 0) {

				hashMap.put("Invalid", "Error : Payer Alternative Phone Number is invalid");
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}//done

			System.out.println("Success");
			
			final PayerBO payerBO = new PayerBO();

			payerBO.setAadhaar(payer.getAadhaar());
			payerBO.setAlternativePhoneNumber(payer.getAlternativePhoneNumber());
			
			payerBO.setDob(DU.parse(payer.getDob(), "dd-MM-yyy"));
			System.out.println(payerBO.getDob());
			
			payerBO.setEmail(payer.getEmail());
			payerBO.setFullName(payer.getFullName());
			payerBO.setGender(payer.getGender());
			payerBO.setPan(payer.getPan());
			payerBO.setOrganisation(payer.getOrganisation());
			payerBO.setParentName(payer.getParentName());
			payerBO.setCity(payer.getCity());
			payerBO.setAddress(payer.getAddress());
			payerBO.setState(payer.getState());
			payerBO.setPin(payer.getPin());
			payerBO.setPhoneNumber(payer.getPhoneNumber());
			payerBO.setMerchantId(principal.getName());
			
			payerBO.setPasswd(getMd5(String.valueOf(payer.getPhoneNumber())));
			System.out.println("Password = "+payerBO.getPasswd());
			
			payerBO.setStatus("CURRENT");
			
			payerRepository.save(payerBO);

			payerBO.setAccountId(this.getUniqueAccountID(payerBO));

			PayerRateOfInterestBO payerRateOfInterest = new PayerRateOfInterestBO();

			payerRateOfInterest.setGst(payer.getGst());
			payerRateOfInterest.setPenalGst(payer.getPenalGst());
			payerRateOfInterest.setRateOfInterest(payer.getRateOfInterest());
			payerRateOfInterest.setLoanTenure(payer.getLoanTenure());
			payerRateOfInterest.setLoanAmount(payer.getLoanAmount());
			payerRateOfInterest.setPenalInterest(payer.getPenalInterest());
			payerRateOfInterest.setSchemeName(payer.getSchemeName());

//Note: M (capital M) represents month and m (small m) represents minute in java.
			payerRateOfInterest.setLoanEmiDate(DU.parse(payer.getLoanEmiDate(), "dd-MM-yyy"));
			
			payerRateOfInterest.setPayer(payerBO);

			payerRateOfInterestRepository.save(payerRateOfInterest);

			this.calcEmiAllMonths(payer.getLoanAmount(), payer.getRateOfInterest(), payer.getLoanTenure(),
					payer.getGst(), payerRateOfInterest,
					DU.formatStrYMD(DU.parse(payer.getLoanEmiDate(), "dd-MM-yyy")));

			hashMap.put("Success",
					"Payer Creation Successfully Done.\n\n Your Account Id = " + payerBO.getAccountId());
			modelAndView.addObject("hashMapObject", hashMap);
			return modelAndView;

		} catch (Exception e) {
			e.printStackTrace();

			hashMap.put("Exception", e.getMessage());
			modelAndView.addObject("hashMapObject", hashMap);
			return modelAndView;

		}
	}

	public Boolean verifyPhoneNumber(Long phoneNumber) {

		String regex = "(0/91)?[7-9][0-9]{9}";
		if (String.valueOf(phoneNumber).matches(regex)) {
			return false;
		}
		return true;
	}
	

	public Boolean verifyAdhar(String phoneNumber) {

		String regex = "^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$";
		if (phoneNumber.matches(regex)) {
			return false;
		}
		return true;
	}

	public Boolean verifyEmail(String email) {

		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";
		if (email.matches(emailRegex)) {
			return false;
		}
		return true;
	}
	
	
	public boolean isValidDate(String pDateString) throws ParseException {
		Date date = new SimpleDateFormat("dd-MM-yyyy").parse(pDateString);
		
		if (new Date().before(date)) {//new Date().before(date) : tests if current date is before the given date.
			
			System.out.println("Current Date = "+new Date());
			
			return false;
		}
		
		System.out.println(new Date());
		System.out.println("true");
		
		return true;
	}
	
	

	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/monthlyPayment", method = RequestMethod.POST, produces ="application/json")
//	public ModelAndView monthlyPayment(@RequestBody(required = false) Payer payer, Principal principal) {
	
	public ModelAndView monthlyPayment(@RequestBody(required = false) String payerId, Principal principal) {
		
		ModelAndView modelAndView = new ModelAndView("output2");
		
		HashMap<String,Object> hashMap = new HashMap<String,Object>();

		Payer payer = new Payer();
		
		try {
			if (payerId == null) {
				
				hashMap.put("Invalid", "Error : You have not entered payerId");
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;

			}
			
			System.out.println((payerId instanceof String));
			
			//System.out.println(payerRepository.findByAccountIdAndStatus(payerId, "CURRENT"));
			
			PayerBO payerBO = payerRepository.findByAccountIdAndStatus(payerId,"CURRENT");
			
			//System.out.println(payerBO);
			
			//PayerBO payerBO = payerRepository.findByAccountId(payer.getPayerId()).get();

			if (payerBO == null) {

				hashMap.put("Invalid",payerId+" is not being match. Please provide valid Payer Account Id");
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;
			}
			
			payer.setAadhaar(payerBO.getAadhaar());
			payer.setAddress(payerBO.getAddress());
			payer.setAlternativePhoneNumber(payerBO.getAlternativePhoneNumber());
			payer.setCity(payerBO.getCity());
			payer.setDob(DU.formatStr(payerBO.getDob()));
			payer.setEmail(payerBO.getEmail());
			payer.setFullName(payerBO.getFullName());
			payer.setGender(payerBO.getGender());
			payer.setPan(payerBO.getPan());
			payer.setPin(payerBO.getPin());
			payer.setOrganisation(payerBO.getOrganisation());
			payer.setParentName(payerBO.getParentName());
			payer.setPhoneNumber(payerBO.getPhoneNumber());
			payer.setState(payerBO.getState());
			payer.setSchemeName(payerBO.getPayerRateOfInterest().getSchemeName());
			payer.setAccountId(payerBO.getAccountId());

			payer.setMerchantId(principal.getName());

			if (payerBO != null && payerBO.getMerchantId().equalsIgnoreCase(principal.getName())) {

				List<PayerLedgerBO> payerLedgers = payerBO.getPayerRateOfInterest().getPayerLedgers().stream()
						.filter(payerLedgerBO -> payerLedgerBO.getPayStatus().equalsIgnoreCase("PENDING"))
						.collect(Collectors.toList());

				for (PayerLedgerBO payerLedgerBO : payerLedgers) {
					payer.setPaymentConfirmation(payerLedgerBO.getId());
					payer.setLoanAmount(payerBO.getPayerRateOfInterest().getLoanAmount());
					payer.setLoanTenure(payerBO.getPayerRateOfInterest().getLoanTenure());
					payer.setLoanEmiDate(DU.formatStr(payerBO.getPayerRateOfInterest().getLoanEmiDate()));
					
					payer.setRateOfInterest(payerLedgerBO.getRateOfInterest());
					System.out.println();
					
					payer.setInterest(payerLedgerBO.getInterest());
					payer.setPrincipal(payerLedgerBO.getPrincipal());
					payer.setGst(payerLedgerBO.getGst());
					payer.setGstOnPrincipal(payerLedgerBO.getGstOnPrincipal());
					payer.setTotalPayble(payerLedgerBO.getTotalPayble());
					payer.setPaybleEmi(payerLedgerBO.getPaybleEmi());
					payer.setPenalGst(payerLedgerBO.getPenalGst());
					payer.setPenalInterest(payerLedgerBO.getPenalInterest());
					payer.setEmiDate(DU.parse(DU.formatStr(payerLedgerBO.getEmiDate()), "dd-MM-yyyy"));
					payer.setPayMode(Constants.PaymentMode.MONTHLY_PAYMENT);
					payer.setPayStatus(Constants.PayStatus.PENDING);
					payer.setBalance(payerLedgerBO.getBalance());
					break;
				}

			}
			
			System.out.println(payer);
			hashMap.put("Success", payer);
			modelAndView.addObject("hashMapObject", hashMap);
			return modelAndView;
			
		} catch (Exception e) {
			System.out.println("Exception");
			e.printStackTrace();
			hashMap.put("Exception", e.getMessage());
			modelAndView.addObject("hashMapObject", hashMap);
			return modelAndView;
		}

	}
	
	
	


	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/getPayer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView getPayer(@RequestBody(required = false) String payerId, Principal principal) {
		
		ModelAndView modelAndView = new ModelAndView("output3");
		
		HashMap<String,Object> hashMap = new HashMap<String,Object>();

		Payer payer = new Payer();
		
		System.out.println(payerId);
		
		try {
			if (payerId == null) {
				
				
				hashMap.put("Invalid", "Error : You have not entered payer id.");
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;
				
			}
			PayerBO payerBO = payerRepository.findByAccountIdAndStatus(payerId, "CURRENT");

			if (payerBO == null) {
				
				hashMap.put("Invalid", payerId+" is not being match. Please provide valid Payer Account Id");
				modelAndView.addObject("hashMapObject", hashMap);
				return modelAndView;
			}

			payer.setFullName(payerBO.getFullName());
			payer.setEmail(payerBO.getEmail());
			payer.setOrganisation(payerBO.getOrganisation());
			payer.setPan(payerBO.getPan());
			payer.setPin(payerBO.getPin());
			payer.setParentName(payerBO.getParentName());
			payer.setDob(DU.formatStr(payerBO.getDob()));
			payer.setGender(payerBO.getGender());
			payer.setAadhaar(payerBO.getAadhaar());
			payer.setAccountId(payerBO.getAccountId());
			payer.setPhoneNumber(payerBO.getPhoneNumber());
			payer.setAlternativePhoneNumber(payerBO.getAlternativePhoneNumber());
			payer.setCity(payerBO.getCity());
			payer.setState(payerBO.getState());
			payer.setAddress(payerBO.getAddress());
			payer.setSchemeName(payerBO.getPayerRateOfInterest().getSchemeName());
			payer.setMerchantId(payerBO.getMerchantId());

			if (payerBO != null && payerBO.getMerchantId().equalsIgnoreCase(principal.getName())) {

				List<PayerLedgerBO> payerLedgers = payerBO.getPayerRateOfInterest().getPayerLedgers().stream()
						.filter(payerLedgerBO -> payerLedgerBO.getPayStatus().equalsIgnoreCase("PENDING")).collect(Collectors.toList());

				for (PayerLedgerBO payerLedgerBO : payerLedgers) {
					payer.setPaymentConfirmation(payerLedgerBO.getId());
					payer.setLoanAmount(payerBO.getPayerRateOfInterest().getLoanAmount());
					payer.setLoanTenure(payerBO.getPayerRateOfInterest().getLoanTenure());
					payer.setLoanEmiDate(DU.formatStr(payerBO.getPayerRateOfInterest().getLoanEmiDate()));
					payer.setRateOfInterest(payerLedgerBO.getRateOfInterest());
					payer.setInterest(payerLedgerBO.getInterest());
					payer.setPrincipal(payerLedgerBO.getPrincipal());
					payer.setGst(payerLedgerBO.getGst());
					payer.setGstOnPrincipal(payerLedgerBO.getGstOnPrincipal());
					payer.setTotalPayble(payerLedgerBO.getTotalPayble());
					payer.setPaybleEmi(payerLedgerBO.getPaybleEmi());
					payer.setEmiDate(DU.parse(DU.formatStr(payerLedgerBO.getEmiDate()), "dd-MM-yyyy"));
					payer.setPayStatus(payerLedgerBO.getPayStatus());
					payer.setLoanEmiDate(DU.formatStr(payerLedgerBO.getEmiDate()));
					payer.setBalance(payerLedgerBO.getBalance());
					break;
				}

			}
			
			System.out.println(payer);
			hashMap.put("Success", payer);
			modelAndView.addObject("hashMapObject", hashMap);
			return modelAndView;
			
		} catch (Exception e) {
			
			System.out.println("Exception");
			e.printStackTrace();
			hashMap.put("Exception", e.getMessage());
			modelAndView.addObject("hashMapObject", hashMap);
			return modelAndView;
		}

	}

	
	
	
	
	
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/getAllPayer", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView getAllPayer(Principal principal,HttpServletRequest request,HttpServletResponse response) {
	//public void getAllPayer(Principal principal,HttpServletRequest request,HttpServletResponse response) {
		
		System.out.println("getAllpayer");
		
	
		
//		Enumeration<String> headerNames = request.getHeaderNames();  
//        while (headerNames.hasMoreElements()) {  
//        String headerName = (String) headerNames.nextElement();  
//        String headerValue = request.getHeader(headerName);  
//        System.out.println(headerName+" = "+headerValue);  
//      
//		
//        }
		
		//System.out.println(request.getHeader("Authorization"));
		
		//System.out.println(response.getHeader("Authorization"));
        
       // String name = request.getUserPrincipal().getName();
        //System.out.println(name);
		
	
		
		
		ModelAndView modelAndView = new ModelAndView("output4");
		

		ArrayList<Payer> list = new ArrayList<Payer>();

		List<PayerBO> allPayer = payerRepository.findAll();

		for (PayerBO payerBO : allPayer) {
			
			Payer payer = new Payer();
			
			System.out.println("deepali");

			payer.setFullName(payerBO.getFullName());
			payer.setEmail(payerBO.getEmail());
			payer.setOrganisation(payerBO.getOrganisation());
			payer.setPan(payerBO.getPan());
			payer.setPin(payerBO.getPin());
			payer.setParentName(payerBO.getParentName());
			payer.setDob(DU.formatStr(payerBO.getDob()));
			payer.setGender(payerBO.getGender());
			payer.setAadhaar(payerBO.getAadhaar());
			payer.setAccountId(payerBO.getAccountId());
			payer.setPhoneNumber(payerBO.getPhoneNumber());
			payer.setAlternativePhoneNumber(payerBO.getAlternativePhoneNumber());
			payer.setCity(payerBO.getCity());
			payer.setState(payerBO.getState());
			payer.setAddress(payerBO.getAddress());
			payer.setSchemeName(payerBO.getPayerRateOfInterest().getSchemeName());
			payer.setMerchantId(payerBO.getMerchantId());
			
			System.out.println("before");
			//System.out.println(principal.getName());
			System.out.println("After");

			//if (payerBO != null && payerBO.getMerchantId().equalsIgnoreCase(principal.getName())) {

			List<PayerLedgerBO> payerLedgers = payerBO.getPayerRateOfInterest().getPayerLedgers().stream()
					.filter(payerLedgerBO -> payerLedgerBO.getPayStatus().equalsIgnoreCase("PENDING"))
					.collect(Collectors.toList());

			for (PayerLedgerBO payerLedgerBO : payerLedgers) {
				
				System.out.println("Nasir");
				
				payer.setPaymentConfirmation(payerLedgerBO.getId());
				payer.setLoanAmount(payerBO.getPayerRateOfInterest().getLoanAmount());
				payer.setLoanTenure(payerBO.getPayerRateOfInterest().getLoanTenure());
				payer.setLoanEmiDate(DU.formatStr(payerBO.getPayerRateOfInterest().getLoanEmiDate()));
				payer.setRateOfInterest(payerLedgerBO.getRateOfInterest());
				payer.setInterest(payerLedgerBO.getInterest());
				payer.setPrincipal(payerLedgerBO.getPrincipal());
				payer.setGst(payerLedgerBO.getGst());
				payer.setGstOnPrincipal(payerLedgerBO.getGstOnPrincipal());
				payer.setTotalPayble(payerLedgerBO.getTotalPayble());
				payer.setPaybleEmi(payerLedgerBO.getPaybleEmi());
				payer.setEmiDate(DU.parse(DU.formatStr(payerLedgerBO.getEmiDate()), "dd-MM-yyyy"));
				payer.setPayStatus(payerLedgerBO.getPayStatus());
				payer.setLoanEmiDate(DU.formatStr(payerLedgerBO.getEmiDate()));
				payer.setBalance(payerLedgerBO.getBalance());
				break;
				
			} // end of child forEach loop

	//	}// end of if-block

			list.add(payer);

		} // end of parent forEach loop

//		HttpSession session = request.getSession();
//		session.setAttribute("name", "nasir");
		
		
//		String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
//		System.out.println(sessionId);
		
		//return list;
		
		
//			HttpSession session=request.getSession(false); 
//			System.out.println("third = "+session.isNew());
//			session.setAttribute("Payers", list);
//			session.setAttribute("abc", "xyz");
		
		
//		    System.out.println("third = "+request.getSession(false).getId());
//		   // request.getSession().setAttribute("list", list);
		
	
//		HttpSession session = request.getSession();
//		if(session.isNew()) {
//			System.out.println("new Session");
//		System.out.println(session.getId());
//		session.setAttribute("payers", "list");
//		
//		}else {
//			System.out.println("Old session");
//		
//		}
	
	


		
		
		
		
		
//		WebUtils.setSessionAttribute(request, "parameter", "apple");
		
	
		
		
		 
		
		
		modelAndView.addObject("Payers", list);
		return modelAndView;

	}// end of getAllPayer() method
	
	
	
	
	
	
	
	
	
	  @Transactional
	  @PreAuthorize("hasRole('ADMIN')")
	  @RequestMapping(value = "/EMIDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	  public ModelAndView getEMIDetails(@RequestBody PayerInformation payerInformation, Principal principal) 
	  { 
		  System.out.println(payerInformation);
		  
		  String payerId =payerInformation.getPayerId();
		  String startDate = payerInformation.getStartDate();
		  String endDate = payerInformation.getEndDate();
		  boolean totalData = payerInformation.isTotalData();
		  
		 
	  
		  HashMap<String,Object> hashMap = new HashMap<String,Object>();
		  
		  ModelAndView modelAndView = new ModelAndView("output5");
	  
		  try
		  { 
			  Payer payer = new Payer();
		  
			  if (payerId == "")
			  { 
				  hashMap.put("Invalid","Please provide Payer Account Id");
				  modelAndView.addObject("hashMapObject", hashMap); 
				  return modelAndView;
			  } 
			  else if (startDate == "" && !totalData) 
			  { 
				hashMap.put("Invalid","Please provide Start Date");
				modelAndView.addObject("hashMapObject", hashMap); 
				  return modelAndView;
		  
			  } 
			  else if (endDate == "" && !totalData) {
				  hashMap.put("Invalid","Please provide End Date");
				  modelAndView.addObject("hashMapObject", hashMap); 
				  return modelAndView;
			  }
	  
			  PayerBO payerBO = payerRepository.findByAccountIdAndStatus(payerId, "CURRENT");
			  
			 // System.out.println(payerBO);
			  
			  if (payerBO == null)
			  {
				  hashMap.put("Invalid", "Payer Account Id is not being match. Please provide valid Payer Account Id");
				  modelAndView.addObject("hashMapObject", hashMap); 
				  return modelAndView;
			  }
	  
			  payer.setAadhaar(payerBO.getAadhaar());
			  payer.setAddress(payerBO.getAddress());
			  payer.setAlternativePhoneNumber(payerBO.getAlternativePhoneNumber());
			  payer.setCity(payerBO.getCity()); 
			  payer.setDob(DU.formatStr(payerBO.getDob()));
			  payer.setEmail(payerBO.getEmail());
			  payer.setFullName(payerBO.getFullName());
			  payer.setGender(payerBO.getGender());
			  payer.setPan(payerBO.getPan());
			  payer.setPin(payerBO.getPin());
			  payer.setOrganisation(payerBO.getOrganisation());
			  payer.setParentName(payerBO.getParentName());
			  payer.setPhoneNumber(payerBO.getPhoneNumber());
			  payer.setState(payerBO.getState());
			  payer.setSchemeName(payerBO.getPayerRateOfInterest().getSchemeName());
			  payer.setMerchantId(payerBO.getMerchantId());
			  payer.setAccountId(payerBO.getAccountId());//17
			  
			  ArrayList<Payer> list = new ArrayList<Payer>();
	  
			  if (totalData) 
			  { 
				  hashMap.put("payerDetails", payer);
					  
				  
				   
				   for (PayerLedgerBO payerLedgerBO : payerBO.getPayerRateOfInterest().getPayerLedgers()) 
				   { 
				  
					  Payer payer1=new Payer();
					  
					  payer1.setLoanAmount(payerBO.getPayerRateOfInterest().getLoanAmount());
					  payer1.setLoanTenure(payerBO.getPayerRateOfInterest().getLoanTenure());
					  payer1.setLoanEmiDate(DU.formatStr(payerBO.getPayerRateOfInterest().getLoanEmiDate()));
					  payer1.setEmiMonth(payerLedgerBO.getEmiMonth());
					  payer1.setRateOfInterest(payerLedgerBO.getRateOfInterest());
					  payer1.setInterest(payerLedgerBO.getInterest());
					  payer1.setPrincipal(payerLedgerBO.getPrincipal());
					  payer1.setGst(payerLedgerBO.getGst());
					  payer1.setGstOnPrincipal(payerLedgerBO.getGstOnPrincipal());
					  payer1.setTotalPayble(payerLedgerBO.getTotalPayble());
					  payer1.setPaybleEmi(payerLedgerBO.getPaybleEmi());
					  payer1.setEmiDate(payerLedgerBO.getEmiDate());
					  payer1.setPayStatus(payerLedgerBO.getPayStatus());
					  payer1.setPayMode(payerLedgerBO.getPayMode());
					  payer1.setBalance(payerLedgerBO.getBalance());
					  
					  list.add(payer1);
				   }
				   hashMap.put("ledgerList", list);
			  	}
			  	else 
			  	{
			  		if (payerBO != null && payerBO.getMerchantId().equalsIgnoreCase(principal.getName()))
			  		{
			  			hashMap.put("payerDetails", payer);
	  
			  			List<PayerLedgerBO> payerLedgers =payerLedgerRepository.findBypayerLedgers(payerBO.getId(),
			  					DU.formatStrYMD(DU.parse(startDate, "dd-MM-yyyy")), DU.formatStrYMD(DU.parse(endDate, "dd-MM-yyyy")));
		  			
			  			for (PayerLedgerBO payerLedgerBO : payerLedgers) 
			  			{ 
					  
							  Payer payer1=new Payer();
							  
							  payer1.setLoanAmount(payerBO.getPayerRateOfInterest().getLoanAmount());
							  payer1.setLoanTenure(payerBO.getPayerRateOfInterest().getLoanTenure());
							  payer1.setLoanEmiDate(DU.formatStr(payerBO.getPayerRateOfInterest().getLoanEmiDate()));
							  payer1.setEmiMonth(payerLedgerBO.getEmiMonth());
							  payer1.setRateOfInterest(payerLedgerBO.getRateOfInterest());
							  payer1.setInterest(payerLedgerBO.getInterest());
							  payer1.setPrincipal(payerLedgerBO.getPrincipal());
							  payer1.setGst(payerLedgerBO.getGst());
							  payer1.setGstOnPrincipal(payerLedgerBO.getGstOnPrincipal());
							  payer1.setTotalPayble(payerLedgerBO.getTotalPayble());
							  payer1.setPaybleEmi(payerLedgerBO.getPaybleEmi());
							  payer1.setEmiDate(payerLedgerBO.getEmiDate());
							  payer1.setPayStatus(payerLedgerBO.getPayStatus());
							  payer1.setPayMode(payerLedgerBO.getPayMode());
							  payer1.setBalance(payerLedgerBO.getBalance());
							  
							  list.add(payer1);
						} 
			  			hashMap.put("ledgerList", list);
			  		} 
			  }
			  
			  modelAndView.addObject("hashMapObject", hashMap); 
			  return modelAndView;
			  
	  } catch (Exception e)
		  { 
		  	System.out.println("Exception");
			e.printStackTrace();
			hashMap.put("Exception", e.getMessage());
			modelAndView.addObject("hashMapObject", hashMap);
			return modelAndView;
		  }
	  
	  }
	
	
	
//	
//		@Transactional
//		@PreAuthorize("hasRole('ADMIN')")
//		@RequestMapping(value = "/getPayerInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//		public ResponseEntity<?> getPayerInfo(@RequestParam(required = false) String payerId,
//				@RequestParam(required = false) String startDate, @RequestParam(required = false) String endtDate,
//				@RequestParam(required = false) Boolean totalData, Principal principal) {
//			BasicDBObject successOrFailure = new BasicDBObject();
//			Payer payer = new Payer();
//			try {
//				if (payerId == null) {
//					return new ResponseEntity<>(successOrFailure.append("error", "Please provide valid Payer Account id"),
//							HttpStatus.OK);
//				} else if (startDate == null && !totalData) {
//					return new ResponseEntity<>(successOrFailure.append("error", "Please provide startdate"),
//							HttpStatus.OK);
//				} else if (endtDate == null && !totalData) {
//					return new ResponseEntity<>(successOrFailure.append("error", "Please provide enddate"), HttpStatus.OK);
//				}
//
//				PayerBO pay = payerRepository.findByAccountIdAndStatus(payerId, "CURRENT");
//
//				if (pay == null) {
//					return new ResponseEntity<>(
//							successOrFailure.append("error",
//									"Payer Account id has dosen't match, Please provide valid Payer Account id"),
//							HttpStatus.OK);
//				}
//
//				payer.setAadhaar(pay.getAadhaar());
//				payer.setAddress(pay.getAddress());
//				payer.setAlternativePhoneNumber(pay.getAlternativePhoneNumber());
//				payer.setCity(pay.getCity());
//				payer.setDob(DU.formatStr(pay.getDob()));
//				payer.setEmail(pay.getEmail());
//				payer.setFullName(pay.getFullName());
//				payer.setGender(pay.getGender());
//				payer.setPan(pay.getPan());
//				payer.setPin(pay.getPin());
//				payer.setOrganisation(pay.getOrganisation());
//				payer.setParentName(pay.getParentName());
//				payer.setPhoneNumber(pay.getPhoneNumber());
//				payer.setState(pay.getState());
//				payer.setSchemeName(pay.getPayerRateOfInterest().getSchemeName());
//				payer.setMerchantId(pay.getMerchantId());
//				payer.setAccountId(pay.getAccountId());
//				BasicDBObject object = new BasicDBObject();
//				if (totalData) {
//					object.append("PayerInfo", payer);
//					List<BasicDBObject> ledger = new ArrayList<>();
//
//					for (PayerLedgerBO plbo : pay.getPayerRateOfInterest().getPayerLedgers()) {
//						BasicDBObject lg = new BasicDBObject();
//						lg.append("LoanAmount", pay.getPayerRateOfInterest().getLoanAmount());
//						lg.append("LoanTenure", pay.getPayerRateOfInterest().getLoanTenure());
//						lg.append("LoanEMIDate", pay.getPayerRateOfInterest().getLoanEmiDate());
//						lg.append("Month", plbo.getEmiMonth());
//						lg.append("RateOfInterset", plbo.getRateOfInterset());
//						lg.append("Interset", plbo.getInterset());
//						lg.append("Principal", plbo.getPrincipal());
//						lg.append("Gst", plbo.getGst());
//						lg.append("GstOnPrincipal", plbo.getGstOnPrincipal());
//						lg.append("TotalPayble", plbo.getTotalPayble());
//						lg.append("PaybleEmi", plbo.getPaybleEmi());
//						lg.append("PaybleEmi", plbo.getPaybleEmi());
//						lg.append("EmiDate", DU.formatStr(plbo.getEmiDate()));
//						lg.append("PayStatus", plbo.getPayStatus());
//						lg.append("PayMode", plbo.getPayMode());
//						lg.append("Balance", plbo.getBalance());
//						ledger.add(lg);
//					}
//					object.append("PayerLedgerInfo", ledger);
//					return new ResponseEntity<>(object, HttpStatus.OK);
//
//				} else {
//
//					if (pay != null && pay.getMerchantId().equalsIgnoreCase(principal.getName())) {
//
//						List<PayerLedgerBO> payerLedgers = payerLedgerRepository.findBypayerLedgers(pay.getId(),
//								DU.formatStrYMD(DU.parse(startDate, "dd-MM-yyyy")),
//								DU.formatStrYMD(DU.parse(endtDate, "dd-MM-yyyy")));
//
//						object.append("PayerInfo", payer);
//						List<BasicDBObject> ledger = new ArrayList<>();
//
//						for (PayerLedgerBO plbo : payerLedgers) {
//							BasicDBObject lg = new BasicDBObject();
//							lg.append("LoanAmount", pay.getPayerRateOfInterest().getLoanAmount());
//							lg.append("LoanTenure", pay.getPayerRateOfInterest().getLoanTenure());
//							lg.append("LoanEMIDate", pay.getPayerRateOfInterest().getLoanEmiDate());
//							lg.append("Month", plbo.getEmiMonth());
//							lg.append("RateOfInterset", plbo.getRateOfInterset());
//							lg.append("Interset", plbo.getInterset());
//							lg.append("Principal", plbo.getPrincipal());
//							lg.append("Gst", plbo.getGst());
//							lg.append("GstOnPrincipal", plbo.getGstOnPrincipal());
//							lg.append("TotalPayble", plbo.getTotalPayble());
//							lg.append("PaybleEmi", plbo.getPaybleEmi());
//							lg.append("PaybleEmi", plbo.getPaybleEmi());
//							lg.append("EmiDate", DU.formatStr(plbo.getEmiDate()));
//							lg.append("PayStatus", plbo.getPayStatus());
//							lg.append("PayMode", plbo.getPayMode());
//							lg.append("PayMode", plbo.getPayMode());
//							lg.append("Balance", plbo.getBalance());
//							ledger.add(lg);
//						}
//						object.append("PayerLedgerInfo", ledger);
//					}
//					return new ResponseEntity<>(object, HttpStatus.OK);
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				return new ResponseEntity<>(successOrFailure.append("error", e.getMessage()), HttpStatus.OK);
//			}
//
//		}

	
	
	
	

//	//We can configure our @RequestParam to be optional, though, with the required attribute.
//	@Transactional
//	@PreAuthorize("hasRole('ADMIN')")
//	@RequestMapping(value = "/getPayerInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> getPayerInfo(@RequestParam(required = false) String payerId,
//			@RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endtDate,
//			@RequestParam(required = false) Boolean totalData, Principal principal) {
//		BasicDBObject successOrFailure = new BasicDBObject();
//		Payer payer = new Payer();
//		try {
//			if (payerId == null) {
//				return new ResponseEntity<>(successOrFailure.append("error", "Please provide valid Payer Account id"),
//						HttpStatus.OK);
//			} else if (startDate == null && !totalData) {
//				return new ResponseEntity<>(successOrFailure.append("error", "Please provide startdate"),
//						HttpStatus.OK);
//			} else if (endtDate == null && !totalData) {
//				return new ResponseEntity<>(successOrFailure.append("error", "Please provide enddate"), HttpStatus.OK);
//			}
//
//			PayerBO pay = payerRepository.findByAccountIdAndStatus(payerId, "CURRENT");
//
//			if (pay == null) {
//				return new ResponseEntity<>(
//						successOrFailure.append("error",
//								"Payer Account id has dosen't match, Please provide valid Payer Account id"),
//						HttpStatus.OK);
//			}
//
//			payer.setAadhaar(pay.getAadhaar());
//			payer.setAddress(pay.getAddress());
//			payer.setAlternativePhoneNumber(pay.getAlternativePhoneNumber());
//			payer.setCity(pay.getCity());
//			
//			payer.setDob(DU.formatStr(pay.getDob()));
//			
//			payer.setEmail(pay.getEmail());
//			payer.setFullName(pay.getFullName());
//			payer.setGender(pay.getGender());
//			payer.setPan(pay.getPan());
//			payer.setOrganisation(pay.getOrganisation());
//			payer.setParentName(pay.getParentName());
//			payer.setPhoneNumber(pay.getPhoneNumber());
//			payer.setState(pay.getState());
//			
//			payer.setSchemeName(pay.getPayerRateOfInterest().getSchemeName());
//			
//			payer.setMerchantId(pay.getMerchantId());
//			payer.setAccountId(pay.getAccountId());
//			
//			BasicDBObject object = new BasicDBObject();
//			
//			if (totalData) {
//				
//				object.append("PayerInfo", payer);
//				
//				List<BasicDBObject> ledger = new ArrayList<>();
//
//				for (PayerLedgerBO plbo : pay.getPayerRateOfInterest().getPayerLedgers()) {
//					
//					
//					
//					BasicDBObject lg = new BasicDBObject();
//					
//					lg.append("LoanAmount", pay.getPayerRateOfInterest().getLoanAmount());
//					lg.append("LoanTenure", pay.getPayerRateOfInterest().getLoanTenure());
//					lg.append("Month", plbo.getEmiMonth());
//					lg.append("RateOfInterest", plbo.getRateOfInterset());
//					lg.append("Interest", plbo.getInterset());
//					lg.append("Principal", plbo.getPrincipal());
//					lg.append("Gst", plbo.getGst());
//					lg.append("GstOnPrincipal", plbo.getGstOnPrincipal());
//					lg.append("TotalPayble", plbo.getTotalPayble());
//					//lg.append("PaybleEmi", plbo.getPaybleEmi());
//					lg.append("PaybleEmi", plbo.getPaybleEmi());
//					lg.append("EmiDate", DU.formatStr(plbo.getEmiDate()));
//					lg.append("PayStatus", plbo.getPayStatus());
//					lg.append("PayMode", plbo.getPayMode());
//					ledger.add(lg);
//				}
//				object.append("PayerLedgerInfo", ledger);
//				return new ResponseEntity<>(object, HttpStatus.OK);
//
//			} else {
//
//				if (pay != null && pay.getMerchantId().equalsIgnoreCase(principal.getName())) {
//
//					List<PayerLedgerBO> payerLedgers = payerLedgerRepository.findBypayerLedgers(pay.getId(),
//							DU.formatStrYMD(startDate), DU.formatStrYMD(endtDate));
//System.out.println(DU.formatStrYMD(startDate));
//System.out.println(DU.formatStrYMD(endtDate));
//					
//					for(PayerLedgerBO payerLedgerBO:payerLedgers) {
//					
//					System.out.println(payerLedgerBO);
//					}
//
//					object.append("PayerInfo", payer);
//					List<BasicDBObject> ledger = new ArrayList<>();
//
//					for (PayerLedgerBO plbo : payerLedgers) {
//						System.out.println("www");
//						BasicDBObject lg = new BasicDBObject();
//						lg.append("LoanAmount", pay.getPayerRateOfInterest().getLoanAmount());
//						lg.append("LoanTenure", pay.getPayerRateOfInterest().getLoanTenure());
//						lg.append("Month", plbo.getEmiMonth());
//						lg.append("RateOfInterest", plbo.getRateOfInterset());
//						lg.append("Interest", plbo.getInterset());
//						lg.append("Principal", plbo.getPrincipal());
//						lg.append("Gst", plbo.getGst());
//						lg.append("GstOnPrincipal", plbo.getGstOnPrincipal());
//						lg.append("TotalPayble", plbo.getTotalPayble());
//						lg.append("PaybleEmi", plbo.getPaybleEmi());
//						//lg.append("PaybleEmi", plbo.getPaybleEmi());
//						lg.append("EmiDate", DU.formatStr(plbo.getEmiDate()));
//						lg.append("PayStatus", plbo.getPayStatus());
//						lg.append("PayMode", plbo.getPayMode());
//						ledger.add(lg);
//					}
//					object.append("PayerLedgerInfo", ledger);
//				}
//				return new ResponseEntity<>(object, HttpStatus.OK);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(successOrFailure.append("error", e.getMessage()), HttpStatus.OK);
//		}
//
//	}

//	@Transactional
//	@PreAuthorize("hasRole('ADMIN')")
//	@RequestMapping(value = "/paymentConfirmation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> paymentConfirmation(@RequestParam(required = false) String payMode,
//			@RequestParam(required = false) String payerId, @RequestParam(required = false) String payStatus,
//			@RequestParam(required = false) PayerLedgerBO paymentConfirmation,
//			@RequestParam(required = false) Double payLoanAmount, Principal principal) {
//		BasicDBObject successOrFailure = new BasicDBObject();
//		try {
//			if (payerId == null) {
//				return new ResponseEntity<>(successOrFailure.append("error", "Please provide valid Payer Account id"),
//						HttpStatus.OK);
//			} else if (payMode == null || !verifyPayMode(payMode)) {
//				return new ResponseEntity<>(successOrFailure.append("error", "Please provide valid  Pay Mode"),
//						HttpStatus.OK);
//			} else if (payStatus == null || !verifyPayStatus(payStatus)) {
//				return new ResponseEntity<>(successOrFailure.append("error", "Please provide valid Pay Status"),
//						HttpStatus.OK);
//			} else if (paymentConfirmation == null || paymentConfirmation.getId() < 0) {
//				return new ResponseEntity<>(
//						successOrFailure.append("error", "Please provide valid payment Confirmation id"),
//						HttpStatus.OK);
//			} else if (payLoanAmount == null || payLoanAmount <= 0) {
//				return new ResponseEntity<>(successOrFailure.append("error", "Please provide valid Loan Amount"),
//						HttpStatus.OK);
//			}
//
//			PayerBO pay = payerRepository.findByAccountIdAndStatus(payerId, "CURRENT");
//
//			if (pay == null) {
//				return new ResponseEntity<>(
//						successOrFailure.append("error",
//								"Payer Account id has dosen't match, Please provide valid Payer Account id"),
//						HttpStatus.OK);
//			}
//			PayerLedgerLogBO payerLedgerLog = new PayerLedgerLogBO();
//			payerLedgerLog.setLedgerId(paymentConfirmation.getId());
//			payerLedgerLog.setBalance(paymentConfirmation.getBalance());
//			payerLedgerLog.setCreatedOn(paymentConfirmation.getCreatedOn());
//			payerLedgerLog.setDefaultPayment(paymentConfirmation.getDefaultPaymentt());
//			payerLedgerLog.setEmiDate(paymentConfirmation.getEmiDate());
//			payerLedgerLog.setEmiMonth(paymentConfirmation.getEmiMonth());
//			payerLedgerLog.setFullPayment(paymentConfirmation.getFullPayment());
//			payerLedgerLog.setGst(paymentConfirmation.getGst());
//			payerLedgerLog.setGstOnPrincipal(paymentConfirmation.getGstOnPrincipal());
//			payerLedgerLog.setInterest(paymentConfirmation.getInterest());
//			payerLedgerLog.setPartPayment(paymentConfirmation.getPartPayment());
//			payerLedgerLog.setPaybleEmi(paymentConfirmation.getPaybleEmi());
//			payerLedgerLog.setPayerRateOfInterest(paymentConfirmation.getPayerRateOfInterest());
//			payerLedgerLog.setPayMode(paymentConfirmation.getPayMode());
//			payerLedgerLog.setPayStatus(paymentConfirmation.getPayStatus());
//			payerLedgerLog.setPenalGst(paymentConfirmation.getPenalGst());
//			payerLedgerLog.setPrincipal(paymentConfirmation.getPrincipal());
//			payerLedgerLog.setRateOfInterest(paymentConfirmation.getRateOfInterest());
//			payerLedgerLog.setSchemeName(paymentConfirmation.getSchemeName());
//			payerLedgerLog.setTotalPayble(paymentConfirmation.getTotalPayble());
//			payerLedgerLog.setUpdatedOn(paymentConfirmation.getUpdatedOn());
//			payerLedgerLogRepository.save(payerLedgerLog);
//
//			paymentConfirmation.setPayMode(payMode);
//			paymentConfirmation.setPayStatus(payStatus);
//			paymentConfirmation.setUpdatedOn(new Date());
//			PayerBO payerbo = paymentConfirmation.getPayerRateOfInterest().getPayer();
//			if (payMode.equalsIgnoreCase(Constants.PaymentMode.FULL_PAYMENT) || paymentConfirmation
//					.getPayerRateOfInterest().getLoanTenure() == paymentConfirmation.getEmiMonth()) {
//				payerbo.setClearance(true);
//				payerbo.setUpdatedOn(new Date());
//				payerRepository.save(payerbo);
//				paymentConfirmation.setFullPayment(payLoanAmount);
//			} else if (payMode.equalsIgnoreCase(Constants.PaymentMode.PART_PAYMENT)) {
//				paymentConfirmation.setPartPayment(payLoanAmount);
//			} else if (payMode.equalsIgnoreCase(Constants.PaymentMode.DEFAULT_PAYMENT)) {
//				paymentConfirmation.setDefaultPaymentt(payLoanAmount);
//			} else if (payMode.equalsIgnoreCase(Constants.PaymentMode.MONTHLY_PAYMENT)) {
//				System.out.println(Constants.PaymentMode.MONTHLY_PAYMENT);
//				paymentConfirmation.setTotalPayble(payLoanAmount);
//			}
//
//			payerLedgerRepository.save(paymentConfirmation);
//
//			return new ResponseEntity<>(successOrFailure.append("success", "Payer Confirmation successfully done."),
//					HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(successOrFailure.append("error", e.getMessage()), HttpStatus.OK);
//		}
//
//	}
//
//	public Boolean verifyPayMode(String payMode) {
//		if (payMode.equalsIgnoreCase(Constants.PaymentMode.DEFAULT_PAYMENT)
//				|| payMode.equalsIgnoreCase(Constants.PaymentMode.MONTHLY_PAYMENT)
//				|| payMode.equalsIgnoreCase(Constants.PaymentMode.PART_PAYMENT)
//				|| payMode.equalsIgnoreCase(Constants.PaymentMode.FULL_PAYMENT)) {
//			return true;
//		} else {
//			return false;
//		}
//
//	}
//
//	public Boolean verifyPayStatus(String payStatus) {
//		if ((payStatus.equalsIgnoreCase(Constants.PayStatus.PAID)
//				|| payStatus.equalsIgnoreCase(Constants.PayStatus.FAILURE))) {
//			return true;
//		} else {
//			return false;
//		}
//
//	}

//	@Transactional
//	@PreAuthorize("hasRole('ADMIN')")
//	@RequestMapping(value = "/defaultPayment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> defaultPayment(@RequestParam(required = false) String payerId, Principal principal) {
//		BasicDBObject successOrFailure = new BasicDBObject();
//		Payer payer = new Payer();
//		try {
//			if (payerId == null) {
//				return new ResponseEntity<>(successOrFailure.append("error", "Please provide valid Payer Account id"),
//						HttpStatus.OK);
//			}
//
//			PayerBO pay = payerRepository.findByAccountIdAndStatus(payerId, "CURRENT");
//
//			if (pay == null) {
//				return new ResponseEntity<>(
//						successOrFailure.append("error",
//								"Payer Account id has dosen't match, Please provide valid Payer Account id"),
//						HttpStatus.OK);
//			}
//			payer.setAadhaar(pay.getAadhaar());
//			payer.setAddress(pay.getAddress());
//			payer.setAlternativePhoneNumber(pay.getAlternativePhoneNumber());
//			payer.setCity(pay.getCity());
//			payer.setDob(DU.formatStr(pay.getDob()));
//			payer.setEmail(pay.getEmail());
//			payer.setFullName(pay.getFullName());
//			payer.setGender(pay.getGender());
//			payer.setPan(pay.getPan());
//			payer.setOrganisation(pay.getOrganisation());
//			payer.setParentName(pay.getParentName());
//			payer.setPhoneNumber(pay.getPhoneNumber());
//			payer.setState(pay.getState());
//			payer.setSchemeName(pay.getPayerRateOfInterest().getSchemeName());
//			payer.setAccountId(pay.getAccountId());
//			payer.setMerchantId(principal.getName());
//			int Default_Month = 1;
//
//			if (pay != null && pay.getMerchantId().equalsIgnoreCase(principal.getName())) {
//
//				List<PayerLedgerBO> payerLedgers = pay.getPayerRateOfInterest().getPayerLedgers().stream()
//						.filter(c -> c.getPayStatus().equalsIgnoreCase("PENDING")).collect(Collectors.toList());
//
//				for (PayerLedgerBO payerLedgerBO : payerLedgers) {
//					if (Default_Month == 1) {
//						payer.setPaymentConfirmation(payerLedgerBO.getId());
//						payer.setLoanAmount(pay.getPayerRateOfInterest().getLoanAmount());
//						payer.setLoanTenure(pay.getPayerRateOfInterest().getLoanTenure());
//						payer.setRateOfInterest(payerLedgerBO.getRateOfInterest());
//						payer.setInterest(payerLedgerBO.getInterest());
//						payer.setPrincipal(payerLedgerBO.getPrincipal());
//						payer.setGst(payerLedgerBO.getGst());
//						payer.setGstOnPrincipal(payerLedgerBO.getGstOnPrincipal());
//						payer.setTotalPayble(payerLedgerBO.getTotalPayble());
//						payer.setPaybleEmi(payerLedgerBO.getPaybleEmi());
//						payer.setPenalGst(payerLedgerBO.getPenalGst());
//						payer.setPenalInterest(payerLedgerBO.getPenalInterest());
//						payer.setEmiDate(DU.parse(DU.formatStr(payerLedgerBO.getEmiDate()), "dd-MM-yyyy"));
//						payer.setPayStatus(Constants.PayStatus.PENDING);
//						payer.setPayMode(Constants.PaymentMode.MONTHLY_PAYMENT);
//						payer.setBalance(payerLedgerBO.getBalance());
//						Default_Month++;
//					} else {
//						DefaultPayer defaultPayer = new DefaultPayer();
//						defaultPayer.setLoanAmount(pay.getPayerRateOfInterest().getLoanAmount());
//						defaultPayer.setEmiDate(DU.parse(DU.formatStr(payerLedgerBO.getEmiDate()), "dd-MM-yyyy"));
//						defaultPayer.setRateOfInterset(payerLedgerBO.getRateOfInterest());
//						defaultPayer.setInterset(payerLedgerBO.getInterest());
//						defaultPayer.setPrincipal(payerLedgerBO.getPrincipal());
//						defaultPayer.setGst(payerLedgerBO.getGst());
//						defaultPayer.setGstOnPrincipal(payerLedgerBO.getGstOnPrincipal());
//						defaultPayer.setPaybleEmi(payerLedgerBO.getPaybleEmi());
//						defaultPayer.setTotalPayble(payerLedgerBO.getTotalPayble());
//						defaultPayer.setPenalInterset(pay.getPayerRateOfInterest().getPenalInterest());
//						double pinterset = Math
//								.round(payer.getPaybleEmi() * (pay.getPayerRateOfInterest().getPenalInterest() / 100));
//						defaultPayer.setPenalIntersetAmount(pinterset);
//						defaultPayer.setPenalGst(pay.getPayerRateOfInterest().getPenalGst());
//						double pgst = Math.round(defaultPayer.getPenalIntersetAmount()
//								* (pay.getPayerRateOfInterest().getPenalGst() / 100));
//						defaultPayer.setPenalGstAmount(pgst);
//						defaultPayer.setDefaulttotalPayble(
//								payer.getTotalPayble() + payerLedgerBO.getTotalPayble() + pinterset + pgst);
//						defaultPayer.setPayMode(Constants.PaymentMode.DEFAULT_PAYMENT);
//						defaultPayer.setPayStatus(Constants.PayStatus.PENDING);
//						defaultPayer.setBalance(payerLedgerBO.getBalance());
//						defaultPayer.setPaymentConfirmation(payerLedgerBO.getId());
//						payer.setDefaultPayer(defaultPayer);
//						break;
//					}
//
//				}
//
//			}
//			return new ResponseEntity<>(payer, HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(successOrFailure.append("error", e.getMessage()), HttpStatus.OK);
//		}
//
//	}

//	@Transactional
//	@PreAuthorize("hasRole('ADMIN')")
//	@RequestMapping(value = "/partPayment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> partPayment(@RequestParam(required = false) String payerId, Principal principal) {
//		BasicDBObject successOrFailure = new BasicDBObject();
//		Payer payer = new Payer();
//		try {
//			if (payerId == null) {
//				return new ResponseEntity<>(successOrFailure.append("error", "Please provide valid Payer Account id"),
//						HttpStatus.OK);
//			}
//
//			PayerBO pay = payerRepository.findByAccountIdAndStatus(payerId, "CURRENT");
//
//			if (pay == null) {
//				return new ResponseEntity<>(
//						successOrFailure.append("error",
//								"Payer Account id has dosen't match, Please provide valid Payer Account id"),
//						HttpStatus.OK);
//			}
//			payer.setAadhaar(pay.getAadhaar());
//			payer.setAddress(pay.getAddress());
//			payer.setAlternativePhoneNumber(pay.getAlternativePhoneNumber());
//			payer.setCity(pay.getCity());
//			payer.setDob(DU.formatStr(pay.getDob()));
//			payer.setEmail(pay.getEmail());
//			payer.setFullName(pay.getFullName());
//			payer.setGender(pay.getGender());
//			payer.setPan(pay.getPan());
//			payer.setOrganisation(pay.getOrganisation());
//			payer.setParentName(pay.getParentName());
//			payer.setPhoneNumber(pay.getPhoneNumber());
//			payer.setState(pay.getState());
//			payer.setSchemeName(pay.getPayerRateOfInterest().getSchemeName());
//			payer.setAccountId(pay.getAccountId());
//
//			payer.setMerchantId(principal.getName());
//
//			if (pay != null && pay.getMerchantId().equalsIgnoreCase(principal.getName())) {
//
//				List<PayerLedgerBO> payerLedgers = pay.getPayerRateOfInterest().getPayerLedgers().stream()
//						.filter(c -> c.getPayStatus().equalsIgnoreCase("PENDING")).collect(Collectors.toList());
//
//				for (PayerLedgerBO payerLedgerBO : payerLedgers) {
//					payer.setPaymentConfirmation(payerLedgerBO.getId());
//					payer.setLoanAmount(pay.getPayerRateOfInterest().getLoanAmount());
//					payer.setLoanTenure(pay.getPayerRateOfInterest().getLoanTenure());
//					payer.setRateOfInterest(payerLedgerBO.getRateOfInterest());
//					payer.setInterest(payerLedgerBO.getInterest());
//					payer.setPrincipal(payerLedgerBO.getPrincipal());
//					payer.setGst(payerLedgerBO.getGst());
//					payer.setGstOnPrincipal(payerLedgerBO.getGstOnPrincipal());
//					payer.setTotalPayble(payerLedgerBO.getTotalPayble());
//					payer.setPaybleEmi(payerLedgerBO.getPaybleEmi());
//					payer.setPenalGst(payerLedgerBO.getPenalGst());
//					payer.setPenalInterest(payerLedgerBO.getPenalInterest());
//					payer.setEmiDate(DU.parse(DU.formatStr(payerLedgerBO.getEmiDate()), "dd-MM-yyyy"));
//					payer.setPayMode(Constants.PaymentMode.PART_PAYMENT);
//					payer.setPayStatus(Constants.PayStatus.PENDING);
//					// payer.setBalance(payerLedgerBO.getBalance());
//					break;
//				}
//
//			}
//			return new ResponseEntity<>(payer, HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(successOrFailure.append("error", e.getMessage()), HttpStatus.OK);
//		}
//
//	}

//	@Transactional
//	@PreAuthorize("hasRole('ADMIN')")
//	@RequestMapping(value = "/fullPayment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> fullPayment(@RequestParam(required = false) String payerId, Principal principal) {
//		BasicDBObject successOrFailure = new BasicDBObject();
//		Payer payer = new Payer();
//		try {
//			if (payerId == null) {
//				return new ResponseEntity<>(successOrFailure.append("error", "Please provide valid Payer Account id"),
//						HttpStatus.OK);
//			}
//
//			PayerBO pay = payerRepository.findByAccountIdAndStatus(payerId, "CURRENT");
//
//			if (pay == null) {
//				return new ResponseEntity<>(
//						successOrFailure.append("error",
//								"Payer Account id has dosen't match, Please provide valid Payer Account id"),
//						HttpStatus.OK);
//			}
//			payer.setAadhaar(pay.getAadhaar());
//			payer.setAddress(pay.getAddress());
//			payer.setAlternativePhoneNumber(pay.getAlternativePhoneNumber());
//			payer.setCity(pay.getCity());
//			payer.setDob(DU.formatStr(pay.getDob()));
//			payer.setEmail(pay.getEmail());
//			payer.setFullName(pay.getFullName());
//			payer.setGender(pay.getGender());
//			payer.setPan(pay.getPan());
//			payer.setOrganisation(pay.getOrganisation());
//			payer.setParentName(pay.getParentName());
//			payer.setPhoneNumber(pay.getPhoneNumber());
//			payer.setState(pay.getState());
//			payer.setSchemeName(pay.getPayerRateOfInterest().getSchemeName());
//			payer.setAccountId(pay.getAccountId());
//
//			payer.setMerchantId(principal.getName());
//
//			if (pay != null && pay.getMerchantId().equalsIgnoreCase(principal.getName())) {
//
//				List<PayerLedgerBO> payerLedgers = pay.getPayerRateOfInterest().getPayerLedgers().stream()
//						.filter(c -> c.getPayStatus().equalsIgnoreCase("PENDING")).collect(Collectors.toList());
//
//				for (PayerLedgerBO payerLedgerBO : payerLedgers) {
//					payer.setPaymentConfirmation(payerLedgerBO.getId());
//					payer.setLoanAmount(pay.getPayerRateOfInterest().getLoanAmount());
//					payer.setLoanTenure(pay.getPayerRateOfInterest().getLoanTenure());
//					payer.setRateOfInterest(payerLedgerBO.getRateOfInterest());
//					payer.setInterest(payerLedgerBO.getInterest());
//					payer.setPrincipal(payerLedgerBO.getPrincipal());
//					payer.setGst(payerLedgerBO.getGst());
//					payer.setGstOnPrincipal(payerLedgerBO.getGstOnPrincipal());
//					payer.setTotalPayble(payerLedgerBO.getTotalPayble());
//					payer.setPaybleEmi(payerLedgerBO.getPaybleEmi());
//					payer.setPenalGst(payerLedgerBO.getPenalGst());
//					payer.setPenalInterest(payerLedgerBO.getPenalInterest());
//					payer.setEmiDate(DU.parse(DU.formatStr(payerLedgerBO.getEmiDate()), "dd-MM-yyyy"));
//					payer.setPayMode(Constants.PaymentMode.PART_PAYMENT);
//					payer.setPayStatus(Constants.PayStatus.PENDING);
//					payer.setBalance(payerLedgerBO.getBalance());
//					break;
//				}
//
//			}
//			return new ResponseEntity<>(payer, HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(successOrFailure.append("error", e.getMessage()), HttpStatus.OK);
//		}
//
//	}

	public static String getMd5(String input) {
		try {
			// Static getInstance method is called with hashing SHA
			MessageDigest md = MessageDigest.getInstance("MD5");

			// digest() method called
			// to calculate message digest of an input
			// and return array of byte
			byte[] messageDigest = md.digest(input.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);

			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}

			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			System.out.println("Exception thrown" + " for incorrect algorithm: " + e);
			return null;
		}
	}

	public String getUniqueAccountID(final PayerBO payer) {
		return payer.getCity().toUpperCase().substring(0, 3)
				.concat(String.valueOf(Long.valueOf(this.env.getProperty("payer.unique.ac")) + payer.getId()));
	}

	public Double calcEmi(final double p, final double r, final double n) {
		
		final double R = r / 12.0 / 100.0;
		
		final double e = p * R * Math.pow(1.0 + R, n) / (Math.pow(1.0 + R, n) - 1.0);/*This is the formula for
		 calculating EMI for per month */
		
		return e;
	}

	public void calcEmiAllMonths(final double p, final double r, final double n, final double g,
			PayerRateOfInterestBO payerRateOfInterest, String EMIDate) {
		
		System.out.println("\n.......calcEmiAllMonths() method variables..............");
		
		final double R = r / 12.0 / 100.0;/* R is the rate of interest calculated on monthly basis.*/
		
		System.out.println("R = "+R);
		
		final double G = g / 100.0;
		System.out.println("G = "+G);
		
		double P = p;
		System.out.println("P = "+P);
		
		double e = this.calcEmi(P, r, n);
		
		final double totalInt = (double) Math.round(e * n - p);// formula for total Interest 
		final double totalAmt = (double) Math.round(e * n);//formula for total EMI 
		
		System.out.println("***************************");
		System.out.println(" Principal Loan Amount -> " + P);
		System.out.println(" Rate of Annual Interest -> " + r);
		System.out.println(" Loan Tenure -> " + n);
		System.out.println(" EMI for per month -> " + Math.round(e));
		System.out.println(" Total Interest -> " + totalInt);
		System.out.println(" Total Payable Amount -> " + totalAmt);
		System.out.println("***************************");
		
		double intPerMonth = (double) Math.round(totalInt / n);
		
		LocalDate date = LocalDate.parse(EMIDate);/*Java LocalDate class is an immutable class that represents Date
		 with a default format of yyyy-MM-dd.*/
		
		
		for (double i = 1.0; i <= n; ++i) {

			final PayerLedgerBO payerLedger = new PayerLedgerBO();

			intPerMonth = (double) Math.round(P * R);
			final double priPerMonth = (double) Math.round(e - intPerMonth);
			final double gstOnPri = (double) Math.round(G * priPerMonth);
			final double balance = (double) Math.round(P);
			final double totalPayble = (double) Math.round(priPerMonth + intPerMonth + gstOnPri);
			P -= e - intPerMonth;

			System.out.println(" Month -> " + (int) i);
			System.out.println(" Interest per month -> " + Math.round(intPerMonth));
			System.out.println(" Principal per month -> " + priPerMonth);
			System.out.println(" GST on principal -> " + gstOnPri);
			System.out.println(" Payable EMI -> " + Math.round(priPerMonth + intPerMonth + gstOnPri));
			System.out.println(" EMI Date -> " + date);
			System.out.println(" Balance Principal -> " + Math.round(P));

			System.out.println("***************************");

			date = date.plusMonths(1L);
			e = (double) Math.round(e);

			payerLedger.setPaybleEmi(Double.valueOf(e));
			payerLedger.setBalance(Double.valueOf(balance));
			payerLedger.setEmiDate(DU.asDate(date));
			payerLedger.setRateOfInterest(payerRateOfInterest.getRateOfInterest());
			payerLedger.setSchemeName(payerRateOfInterest.getSchemeName());
			payerLedger.setGst(Double.valueOf(g));
			payerLedger.setGstOnPrincipal(Double.valueOf(gstOnPri));
			payerLedger.setPrincipal(Double.valueOf(priPerMonth));
			payerLedger.setInterest(Double.valueOf(intPerMonth));
			payerLedger.setTotalPayble(Double.valueOf(totalPayble));
			payerLedger.setCreatedOn(new Date());
			payerLedger.setPayerRateOfInterest(payerRateOfInterest);
			payerLedger.setPayStatus("PENDING");
			payerLedger.setEmiMonth((int) i);
			payerLedgerRepository.save(payerLedger);
		}
	}

}
