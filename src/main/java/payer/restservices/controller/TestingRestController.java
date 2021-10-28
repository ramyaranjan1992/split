package payer.restservices.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sabpaisa.requestprocessing.Encryptor;

import payer.restservices.modal.PayerBO;
import payer.restservices.repository.PayerRepository;

//@RestController
@Controller
//@RequestMapping("/test1")
public class TestingRestController {

	@Autowired
	private PayerRepository payerRepository;

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/sampleTest1", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> test1(@RequestParam(required = false) String name) {

		try {
			return new ResponseEntity<>("Sample Test1 Ok ,Success", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping("/defaultPaymentApi")
	public String defaultPaymentApi(Model model) {

		// model.addAttribute("payer", new Payer());

		return "defaultPaymentApi";
	}

}
