package payer.restservices.util;

public interface Constants {

	public static final String SUCCESS = "Success";

	public interface FormatDates {
		public static final String DATEFORMAT = "dd/MM/yyyy";
		public static final String DATEFORMAT2 = "dd-MM-yyyy";
	}

	public interface PaymentMode {

		public static final String MONTHLY_PAYMENT = "MONTHLY";

		public static final String DEFAULT_PAYMENT = "DEFAULT";

		public static final String PART_PAYMENT = "PART";

		public static final String FULL_PAYMENT = "FULL";

	}
	public interface PayStatus {

		public static final String PAID = "PAID";
		public static final String PENDING = "PENDING";
		public static final String FAILURE = "FAILURE";

	}

	public interface LoginException {

		public static final String USER_NOT_EXIST = "Invalid Username";

		public static final String DEVICE_ID_NOT_FOUND = "Unable to get Device ID";

		public static final String DEVICE_ID_ALREADY_EXIST = "Already Logged in another Device please Logout and try again";

		public static final String INVALID_ROLE_PERMISSION = "Sorry, user does not have permission! to login";

		public static final String APP_NAME_NOT_FOUND = "Unable to get AppName";

		public static final String CONTACT_PASSWORD_NULL = "Password is not genrated please contact campus principle";

	}

}
