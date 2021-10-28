package payer.restservices.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.WebUtils;

import com.sabpaisa.requestprocessing.Encryptor;

import payer.restservices.dto.Payer;
import payer.restservices.dto.Payment;
import payer.restservices.dto.User;
import payer.restservices.modal.PayerBO;
import payer.restservices.modal.PayerLedgerBO;
import payer.restservices.modal.Transaction;
import payer.restservices.repository.PayerLedgerRepository;
import payer.restservices.repository.PayerRepository;
import payer.restservices.repository.TransactionRepository;
import payer.restservices.util.DU;

//@CrossOrigin("https://securepay.sabpaisa.in/")
//@CrossOrigin(origins= {"*"}, maxAge = 3600, allowCredentials = "false" )
@Controller
public class DesignController {

	@Value("${spDomain}")
	private String spDomain;

	@Value("${URLsuccess}")
	private String URLsuccess;

	@Value("${URLfailure}")
	private String URLfailure;

	@Value("${userN}")
	private String username;

	@Value("${password}")
	private String password;

	@Value("${clientCode}")
	private String clientCode;

	@Value("${authKey}")
	private String authKey;

	@Value("${authIV}")
	private String authIV;

	@Value("${auth}")
	private boolean auth;

	@Autowired
	private PayerRepository payerRepository;

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private PayerLedgerRepository payerLedgerRepository;
	
	public String payerId;
	public int emiMonth;

	@RequestMapping("/login")
	public String login(Model model) {

		model.addAttribute("loginData", new User());

		return "login";
	}

	@RequestMapping("/signOut")
	public RedirectView signOut() {
		RedirectView redirectView = new RedirectView("login");

		return redirectView;
	}

	@RequestMapping("/payerCreation")
	public String payerCreation() {
		return "payerCreation";
	}

	@RequestMapping("/monthlyPaymentApi")
	public String monthlyPayment() {
		return "monthlyPaymentApi";
	}

	@RequestMapping("/getPayerApi")
	public String getPayerApi() {

		return "getPayerApi";
	}

	//@ResponseBody
	@RequestMapping("/getAllPayerApi")
	//@ResponseStatus(HttpStatus.CREATED)
	//public String getAllPayerApi(HttpServletRequest request,HttpServletResponse response) {
	//public HttpHeaders getAllPayerApi(HttpServletRequest request,HttpServletResponse response) {
	public RedirectView getAllPayerApi(HttpServletRequest request,HttpServletResponse response) {
	//public ResponseEntity<Object> getAllPayerApi(HttpServletRequest request,HttpServletResponse response) {
	//public void getAllPayerApi(HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException {
		
//		String jwt_token=null;
//		
//		
//		Enumeration<String> headerNames = request.getHeaderNames();  
//        while (headerNames.hasMoreElements()) {  
// 
//        String headerName = headerNames.nextElement(); 
//        String headerValue = request.getHeader(headerName);
//        if(headerName.equalsIgnoreCase("cookie")) {
//   
//        	jwt_token=headerValue;
//        	System.out.println(headerName);
//        	System.out.println(headerValue);
//        	System.out.println(jwt_token);
//        	
//        	String[] strings = jwt_token.split("=");
//        	 
//        	 for(String str:strings) {
//        		 if(str.contains("Bearer")) {
//        		System.out.println(jwt_token=str); 
//        		}
//        	 }
//       }
//     }
		
	
		
//		Cookie[] cookies = request.getCookies();
//		
//		System.out.println(cookies.length);
//		System.out.println(cookies);
//		
//		for(Cookie cookie:cookies) {
//			
//			System.out.println(cookie.getName()+" = "+cookie.getValue());
//			
//		}
//		
		
		
		
		
//		HttpSession session=request.getSession(false);
//		System.out.println("second = "+session.getId());
//        String jwtToken=(String)session.getAttribute("jwtToken"); 
//        System.out.println("Jwt Token = "+jwtToken);
        
        
       
        
        
//        String tokenId=null;
//        Cookie ck[]=request.getCookies();  
//        for(int i=0;i<ck.length;i++){  
//        	
//        	System.out.println(ck[i].getName()+" = "+ck[i].getValue());
//        	
////        	if(ck[i].getName()=="tokenId") {
////        		tokenId=ck[i].getValue();
////        		}
//        	}
//         System.out.println(tokenId);//printing name and value of cookie  
        
        
      
        
        
        
        
        
//        
//        
//        URL urlObj = null;
//		try {
//			urlObj = new URL("http://localhost:8080/payer/getAllPayer");
//		} catch (MalformedURLException e) {
//			
//			e.printStackTrace();
//		}
//        HttpURLConnection con = null;
//		try {
//			con = (HttpURLConnection) urlObj.openConnection();
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}
//        con.setRequestProperty("Authorization", jwt_token);
//        
//       int responseCode = con.getResponseCode();
//        
//        System.out.println(responseCode);
        
        
        
        
//      // HttpSession httpSession = request.getSession();
//       //@SuppressWarnings("unchecked")
//	//ArrayList<Payer> list=(ArrayList<Payer>) httpSession.getAttribute("MY_SESSION_MESSAGES");
//       String list=(String) httpSession.getAttribute("name");
//       System.out.println(list);
////        
//        if(session.isNew()==true) {
//        	System.out.println("new session");
//        System.out.println(session.getId());
//        }
//        else {
//        	System.out.println("old session");
//        	System.out.println(session.getId());
//        	session.getAttribute("payers");
//        	
//        }
//        
        
        
//        HttpSession session2 = request.getSession(false);
//        System.out.println(session2.getId());
//        System.out.println((String)session2.getAttribute("jwtToken"));
//        
//    		System.out.println(sessionId);
//    		if(session.isNew()) {
//    			System.out.println("new session");
//    			
//        	}else {
//        		
//        		System.out.println("old session");
//    			System.out.println(session.getId());
//    			String str = (String)session.getAttribute("payers");
//        		System.out.println(str);
//        		
//        	}
    		
   
    		
        
//        String param = (String) WebUtils.getSessionAttribute(
//        	      request, "parameter");
//		System.out.println(param);
        
        
        
        
        
        
       // session.invalidate();
       
//        HttpSession httpSession = request.getSession();
//        
//        if(httpSession.isNew()==true) {
//        	System.out.println("new session");
//        System.out.println(session.getId());
//        }
//        else {
//        	System.out.println("old session");
//        }
        
        
        
//       HttpSession session1=request.getSession(false);
//       
//      
//       
//        System.out.println("fourth = "+session1.getId());
//        
//        @SuppressWarnings("unchecked")
//		ArrayList<Payer> list=(ArrayList<Payer>)session1.getAttribute("payers");
//        System.out.println(list);
//        
        
        
        
        
//        HttpClient client = HttpClient.newHttpClient();
//
//        // Create HTTP request object
//        HttpRequest req = HttpRequest.newBuilder()
//                .uri(URI.create("http://localhost:8080/payer/getAllPayer"))
//                .GET()
//                .header("Authorization",jwtToken)
//                .header("Content-Type", "application/json")
//                .build();
////        // Send HTTP request
////        HttpResponse<String> res = client.send(req,
////                HttpResponse.BodyHandlers.ofString());
//        
//     // Send HTTP request
//        HttpResponse<String> res = client.send(req,HttpResponse.BodyHandlers.ofString());
//        //System.out.println(res.body());
//        System.out.println("reach");
//        
//        return new ResponseEntity<>(res,HttpStatus.OK);
        
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setStatus(res);
        

        
        
   

        
        
        
        
        
        
        
        
        //session.invalidate();
        
		//response.addHeader("jwtToken", jwtToken);
		
		
		
//		 while(headerNames.hasMoreElements()){  
//			 System.out.println(headerNames.getClass().); 
//        }   
		
		
		
        
        
        
        
//		HttpHeaders httpHeaders = new HttpHeaders();
		
//		JSONObject tokenObject = new JSONObject();
//		
//	    String token = tokenObject.getString("jwtToken");
//	    
//	    System.out.println(token);
		
//		request.getUserPrincipal();
//		
//		response.
//
//	    httpHeaders.add("Authorization",jwtToken);
//
//	    URI redirectUri = null;
//		try {
//			redirectUri = new URI("http://localhost:8080/payer/getAllPayer");
//		} catch (URISyntaxException e) {
//		
//			e.printStackTrace();
//		}
	    
	   RedirectView redirectView = new RedirectView("/payer/getAllPayer");
	   // httpHeaders.setLocation(redirectUri);
		
	   // return "Welcome";

		return redirectView;
	    
	    //return httpHeaders;
        
        
        
        
        
        
        
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("Authorization", jwtToken);
//        try {
//			//httpHeaders.setLocation(new URI("http://localhost:8080/payer/getAllPayer"));
//			httpHeaders.setLocation(new URI("/payer/getAllPayer"));
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        
        

        
        
        
        
        
        
////        return httpHeaders;
//        URI yahoo = null;
//		try {
//			yahoo = new URI("http://localhost:8080/payer/getAllPayer");
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        HttpHeaders httpHeaders = new HttpHeaders();
//        //httpHeaders.setBearerAuth(jwtToken);
//        //response.setHeader("Authorization", jwtToken);
//
//        httpHeaders.setLocation(yahoo);
//        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
        
        
        
        
        
        
        
       // response.setHeader("Authorization", jwtToken);
        
//        response.addHeader("Authorization", jwtToken);
//        
//        System.out.println("header = "+request.getHeader("Authorization"));
//        
//        try {
//			response.sendRedirect("http://localhost:8080/payer/getAllPayer");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
        
        

//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
//        response.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, X-Auth-Token, X-Csrf-Token, WWW-Authenticate, Authorization");
//        response.setHeader("Access-Control-Allow-Credentials", "false");
//        response.setHeader("Access-Control-Max-Age", "3600");
        
       
        //response.addHeader("Authorization", jwtToken);
//       response.setHeader("Authorization", jwtToken);
//     
//        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/payer/getAllPayer");
//        try {
//			requestDispatcher.forward(request, response);
//		} catch (ServletException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        
        
        
        
	}

	


	@RequestMapping("/getEMIDetails")
	public String getEMIDetails() {

		return "getEMIDetails";
	}
	
	
	
	@RequestMapping( value = "/paymentApi",method = RequestMethod.POST)
	public ModelAndView paymentApi(@ModelAttribute Payment payment) {
		
		ModelAndView modelAndView = new ModelAndView("payment");
		
		System.out.println(payment.getPayerId());
		System.out.println(payment.getName());
		
		return modelAndView;
	}
	
	
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/paymentGateway", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public RedirectView paymentGateway(@ModelAttribute Transaction transaction) {
		
		payerId = transaction.getPayerId();//here we are getting current payerId of client
		
		emiMonth= Integer.parseInt(transaction.getEmiMonth());
		
		String spURL = null; String firstName=null; String lastName=null;

		try {

			//transactionRepository.save(transaction);

		
			String txnId = String.valueOf(new Random().nextInt(1000000)); // Transaction ID (Mandatory)
			System.out.println(txnId);

			String txnAmt = transaction.getAmount(); // Transaction Amount entered by client (Mandatory)
		
			String fullName = transaction.getName();
			String[] words=fullName.split("\\s");//splits the string based on whitespace
			
			
			firstName=words[0]; // Payer's First Name (Optional)
			lastName=words[1];// Payer's Last Name (Optional)
			 
			String mobileNumber = transaction.getMobileNumber();// Payer's Contact Number (Optional)
			String email = transaction.getEmail();// Payer's Email Address (Optional)
			String address = transaction.getAddress();//Payer's Address (Optional)

			spURL = "?clientName=" + clientCode + "&usern=" + username + "&pass=" + password + "&amt=" + txnAmt
					+ "&txnId=" + txnId + "&firstName=" + firstName + "&lstName=" + lastName + "&contactNo="
					+ mobileNumber + "&Email=" + email + "&Add=" + address + "&ru=" + URLsuccess
					+ "&failureURL=" + URLfailure;
			System.out.println(spURL);

			spURL = Encryptor.encrypt(authKey, authIV, spURL);
			spURL = spURL.replace("+", "%2B");
			spURL = "?query=" + spURL + "&clientName=" + clientCode;
			spURL = spDomain + spURL;

		

		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println(e1);
		 }
	
		RedirectView redirectView = new RedirectView(spURL);
		return redirectView;

	}

	
	@RequestMapping(value = "/payerservices/response",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	//public ModelAndView response(@RequestParam("query") String query) {
	public ModelAndView response(@RequestParam String query) {

		
		
		ModelAndView modelAndView = new ModelAndView("redirectPage");
		
		Transaction transaction = new Transaction();

		try {
			String decText = Encryptor.decrypt(authKey, authIV, query);

			System.out.println(decText);
			System.out.println("\n\n");
			
			
			/* response Data*/
			String ar[]=decText.split("&");
			for(String str:ar)
				System.out.println(str);

			String arr[] = decText.split("&");
			
			String[] var= new String[arr.length];
			String[] val=new String[arr.length];
			
			int temp=0;
			
			for(String str:arr) {
				
				String[] split = str.split("=");
				
				for(int x=0; x<split.length; x++) {
					
					if(x==0) {
						var[temp]=split[x];
					}
					if(x==1){
						val[temp]=split[x];
					}
				}
				temp++;
			}
			
			
			
			
			for(int x=0; x<var.length; x++) {
				
				if(var[x].equalsIgnoreCase("pgRespCode")) {
					transaction.setPgRespCode(val[x]);
				}
				
				if(var[x].equalsIgnoreCase("PGTxnNo")) {
					transaction.setPgTxnNo(val[x]);
				}
				
				if(var[x].equalsIgnoreCase("SabPaisaTxId")) {
					transaction.setSabPaisaTxId(val[x]);
				}
				
				if(var[x].equalsIgnoreCase("issuerRefNo")) {
					transaction.setIssuerRefNo(val[x]);
				}
				
				if(var[x].equalsIgnoreCase("authIdCode")) {
					transaction.setAuthIdCode(val[x]);
				}
				
				if(var[x].equalsIgnoreCase("amount")) {
					transaction.setAmount(val[x]);
				}
				
				if(var[x].equalsIgnoreCase("clientTxnId")) {
					transaction.setClientTxnId(val[x]);
				}
				
				if(var[x].equalsIgnoreCase("firstName")) {
					transaction.setFirstName(val[x]);
				}
				
				if(var[x].equalsIgnoreCase("lastName")) {
					transaction.setLastName(val[x]);
				}
				
				if(var[x].equalsIgnoreCase("payMode")) {
					transaction.setPayMode(val[x]);
					
					
					PayerBO payerBO = payerRepository.findByAccountIdAndStatus(payerId, "CURRENT");
					List<PayerLedgerBO> payerLedgers = payerBO.getPayerRateOfInterest().getPayerLedgers();
					
					for(PayerLedgerBO payerLedgerBO:payerLedgers) {
						
						if(payerLedgerBO.getEmiMonth()==emiMonth) {
							payerLedgerBO.setPayMode(val[x]);
							payerLedgerBO.setPayStatus("PAID");
							payerLedgerRepository.save(payerLedgerBO);
						}
						
					}
					
					
				}
				
				if(var[x].equalsIgnoreCase("email")) {
					transaction.setEmail(val[x]);
				}
				
				if(var[x].equalsIgnoreCase("mobileNo")) {
					transaction.setMobileNumber(val[x]);
				}
				
				
				if(var[x].equalsIgnoreCase("spRespCode")) {
					transaction.setSpRespCode(val[x]);
				}
				
				if(var[x].equalsIgnoreCase("clientCode")) {
					transaction.setClientCode(val[x]);
				}
				
				if(var[x].equalsIgnoreCase("transDate")) {
					transaction.setTransDate(val[x]);
				}
				
				if(var[x].equalsIgnoreCase("spRespStatus")) {
					transaction.setSpRespStatus(val[x]);
				}
				
				
				if(var[x].equalsIgnoreCase("challanNo")) {
					transaction.setChallanNo(val[x]);
				}
				
				
				if(var[x].equalsIgnoreCase("reMsg")) {
					transaction.setResMsg(val[x]);
				}
				
				
				if(var[x].equalsIgnoreCase("orgTxnAmount")) {
					transaction.setOrgTxnAmount(val[x]);
				}
				
				if(var[x].equalsIgnoreCase("programId")) {
					transaction.setProgramId(val[x]);
				}
				
				if(var[x].equalsIgnoreCase("Add")) {
					transaction.setAddress(val[x]);
				}
				
		   }
			
			modelAndView.addObject("Transaction", transaction);
			
			transactionRepository.save(transaction);

	
			
			
			for (String str : arr)
				System.out.println(str);
			
		} catch (Exception e2) {
			e2.printStackTrace();
			System.out.println(e2);
		}
		return modelAndView;
	}	

	
	
	
	
	
	
	
@RequestMapping("/getPayerInformation")
	public String getPayerInformation() {

		return "getPayerInformation";
	}
	
	
	

	

//	@Transactional
//	@PreAuthorize("hasRole('ADMIN')")
//	
////	@CrossOrigin
//	@RequestMapping(value = "/paymentGateway", method = RequestMethod.POST, produces = "application/json")
//	public RedirectView paymentGateway(@RequestBody Transaction transaction, Principal principal,
//			HttpServletResponse response) {
//		System.out.println("welcomw to sabpaisa");
//		String spURL = null;
//
//		try {
//
//			transactionRepository.save(transaction);
//
//			/*
//			 * PayerBO pay = payerRepository.findByAccountIdAndStatus("FAR10125",
//			 * "CURRENT");
//			 * 
//			 * String fullName = pay.getFullName(); String[]
//			 * words=fullName.split("\\s");//splits the string based on whitespace
//			 * 
//			 * 
//			 * String firstName=null; String lastName=null;
//			 * 
//			 * firstName=words[0];// lastName=words[1];//
//			 * 
//			 * Long phoneNumber = pay.getPhoneNumber(); String contactNumber =
//			 * Long.toString(phoneNumber);//
//			 * 
//			 * String email = pay.getEmail();//
//			 * 
//			 * String amount="3";
//			 */
//
//			// HttpServletResponse response1 = null;//Initialize your ServletResponse Object
//			// Here
//
////		   String spDomain =spDomain; //URL provided by SabPaisa (Mandatory)
////		   String username = "nishant.jha_2885"; //Username provided by Sabpaisa (Mandatory)
////		   String password = "SIPL1_SP2885"; //Password provided by Sabpaisa (Mandatory)
//			String txnId = String.valueOf(new Random().nextInt(1000000)); // Transaction ID (Mandatory)
////		   String clientCode ="SIPL1"; //Client Code Provided by Sabpaisa 
////		   String authKey ="rMnggTKFvmGx8y1z"; //Authentication Key Provided By Sabpaisa 
////		   String authIV ="0QvWIQBSz4AX0VoH"; //Authentication IV Provided by Sabpaisa
//
//			String txnAmt = transaction.getAmount(); // Transaction Amount (Mandatory)
//
//			// String URLsuccess = "http://localhost:8080/payerservice/response"; //Return
//			// URL upon successful transaction
//
//			// String URLfailure ="http://localhost:8080/payerservice/response"; //Return
//			// URL upon failed Transaction (Optional)
//
//			String payerFirstName = transaction.getPayerFirstName(); // Payer's First Name (Optional)
//			String payerLastName = transaction.getPayerLastName(); // Payer's Last Name (Optional)
//			String payerContact = transaction.getMobileNumber(); // Payer's Contact Number (Optional)
//			String payerEmail = transaction.getPayerEmail(); // Payer's Email Address (Optional)
//
////		  String payerAddress = "sec-87,delhi"; //Payer's Address (Optional)
//
////		  boolean auth = false; //Set this bit if authentication is enabled
//
//			spURL = "?clientName=" + clientCode + "&usern=" + username + "&pass=" + password + "&amt=" + txnAmt
//					+ "&txnId=" + txnId + "&firstName=" + payerFirstName + "&lstName=" + payerLastName + "&contactNo="
//					+ payerContact + "&Email=" + payerEmail + "&Add=" + payerAddress + "&ru=" + URLsuccess
//					+ "&failureURL=" + URLfailure;
//			System.out.println(spURL);
//
//			System.out.println("Welcome to redir");
//
//			spURL = Encryptor.encrypt(authKey, authIV, spURL);
//			spURL = spURL.replace("+", "%2B");
//			spURL = "?query=" + spURL + "&clientName=" + clientCode;
//			spURL = spDomain + spURL;
//
//			System.out.println("Welcome to r");
//
//			// response.sendRedirect(spURL);
//
//		} catch (Exception e1) {
//			e1.printStackTrace();
//			System.out.println(e1);
//		}
//
//		// return "redirect:"+spURL;
//		System.out.println("Welcome to redirect");
//		// ModelAndView mav = new ModelAndView("redirect:"+spURL);
//		RedirectView redirectView = new RedirectView(spURL);
//		return redirectView;
//
//	}
//
//	@RequestMapping("/redirect")
//	public ModelAndView redirect(@RequestParam("query") String query) {
//
//		ModelAndView modelAndView = new ModelAndView("redirectPage");
//
//		try {
//			String decText = Encryptor.decrypt(authKey, authIV, query);
//
//			System.out.println(decText);
//			System.out.println("\n");
//
//			String arr[] = decText.split("&");
//			for (String str : arr)
//				System.out.println(str);
//		} catch (Exception e2) {
//			e2.printStackTrace();
//			System.out.println(e2);
//		}
//		return modelAndView;
//	}
	
	
	
	
		
	
	 @RequestMapping(value = "/formTest", method =RequestMethod.GET)
	  public ModelAndView formTest() { 
		  
		  ModelAndView modelAndView = new ModelAndView("formTest");
		  
		  modelAndView.addObject("payerId", "FAR10120");
		  modelAndView.addObject("name", "Nasir");
		  
		  
		  return modelAndView;
		  }

	 
	
	@RequestMapping(value = "/modelTest",method = RequestMethod.POST,produces =MediaType.APPLICATION_JSON_VALUE)
	public ModelAndView modelTest(@ModelAttribute Payment payment) {
		
		ModelAndView modelAndView = new ModelAndView("modelTest");
		
		// for Model class, same functioning will be happened
		
		return modelAndView;
	}
	
	
	
	
	
	
	
	
	 //playing with thymeleaf variable 
	
	  @RequestMapping(value= "/variableTest",method = RequestMethod.GET)
	  public ModelAndView variableTest() {
		  ModelAndView modelAndView = new ModelAndView("variableTest");
	  User user = new User();
	  user.setErrorMsg("NullPointerException");
	  user.setMerchant("1323232");
	  user.setPwd("123456");
	  user.setToken("kfgksdbmfhsdbvhhDSBjhdbj,hbjhVXCHJfhsxchvdhs");
	  user.setResponse("Validation Success");
	  modelAndView.addObject("userObject",user);
	  modelAndView.addObject("name", "nasir");
	  return modelAndView; 
	  }
	 
}
