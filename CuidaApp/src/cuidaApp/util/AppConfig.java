package cuidaApp.util;

public class AppConfig {

	public static final String HashSteganography = "$2a$10$8VNezZAIjVQzjbn7D.AKx";

	// WS paths
	private static final String EVENTS_ADD = "events";
	private static final String EVENTS_CATEGORIES = "events/categories";
	private static final String REGISTER_USERS = "create";
	private static final String CONFIRMATION_USERS = "confirmation";
	private static final String SIGNIN_USERS = "signin";
	private static final String RECOVERY_PASSWORD = "recovery";
	private static final String FORWARD_CODE = "forward";
	private static final String CHANGE_PASSWORD = "change";
	private static final String SHOW_USER = "show";
	private static final String SIGN_OUT = "signout";
	private static final String UPDATE_USER = "update";
	private static final String UPDATE_TOKEN = "signin/update";
	private static final String RANKING = "reputation";
	private static final String CATEGORIES="category/findCategoriesByCoordinates/";
	private static final String ACTIVES="category/findAssetsByCategory";
	
	public static final String APP_KEY_BUGS = "e1c65b3eecab64d612dcddfddd0a56c03e6e1c2a";

	// Developer URLs
	private static final String DEV_PUSHERS_API_URL = "http://190.1.137.172:3000/api/pushers/";
	private static final String DEV_USERS_API_URL = "http://190.1.137.172:3000/api/users/";
	private static final String DEV_USERS_CATEGOTY_URL = "http://192.168.0.21:9090/CuidappRestServices/webresources/";

	private static final String DEV_PUSHER_KEY = "dbffc1fe312d8ee21590b3cdbccdfc59e3422402";
	private static final String DEV_PUSHER_APP_KEY = "e64ac8bb776f2c824c1e0bfc43f512cb4aac9012";

	// Production URLs
	private static final String PRO_PUSHERS_API_URL = "http://cuidapp.com:8081/api/pushers/";
	private static final String PRO_USERS_API_URL = "http://cuidapp.com:8081/api/users/";
	private static final String PRO_USERS_CATEGOTY_URL = "http://cuidapp.com:8080/wscuidapp/webresources/";

	//http://cuidapp.com:8080/wscuidapp/webresources/category/findAssetsByCategory
	//
	// private static final String PRO_PUSHERS_API_URL =
	// "http://geoevents.herokuapp.com/api/pushers/";
	// private static final String PRO_USERS_API_URL =
	// "http://geoevents.herokuapp.com/api/users/";
	//
	private static final String PRO_PUSHER_KEY = "a327cd037854852375e4a071eed71f7555d22266";
	private static final String PRO_PUSHER_APP_KEY = "4115dd8cdb8ec300e20ce2f2186bc28975ce8d0d";

	// private static final String PRO_PUSHER_KEY =
	// "f28d86ef971902217945db5fda216d75b2c4a6ef";
	// private static final String PRO_PUSHER_APP_KEY =
	// "8d6901c8f7813c4d15efab65977f86fb2fbd7743";
	//
	// private static final String PRO_PUSHER_KEY =
	// "f28d86ef971902217945db5fda216d75b2c4a6ef";
	// private static final String PRO_PUSHER_APP_KEY =
	// "8d6901c8f7813c4d15efab65977f86fb2fbd7743";
	// APP KEY => 89b0ea9c3293aa3ccf64b1cc72e254d7cc4e3a87
	// IDENTIFICADOR = 2
	// KEY => 6a62f4e09633bd32b4efcac14f694bb0b525f3d4
	// APP TOKEN => 89b0ea9c3293aa3ccf64b1cc72e254d7cc4e3a87

	// URL
	public static String PUSHERS_API_URL;
	public static String EVENT_CATEGORIES_URL;
	public static String ADD_EVENT_URL;
	public static String REGISTER_USERS_URL;
	public static String CONFIRMATION_USERS_URL;
	public static String SIGNIN_USERS_URL;
	public static String RECOVERY_PASSWORD_URL;
	public static String FORWARD_CODE_URL;
	public static String CHANGE_PASSWORD_URL;
	public static String SHOW_USER_URL;
	public static String SIGN_OUT_URL;
	public static String UPDATE_USER_URL;
	public static String UPDATE_TOKEN_URL;
	public static String RANKING_URL;
	public static String CATEGORIES_URL;
	public static String ACTIVES_URL;

	// Developer pusher
	public static String PUSHER_KEY;
	public static String PUSHER_APP_KEY;

	public static void setDeveloperEnviroment() {
		PUSHERS_API_URL = DEV_PUSHERS_API_URL;
		EVENT_CATEGORIES_URL = PUSHERS_API_URL + EVENTS_CATEGORIES;
		ADD_EVENT_URL = PUSHERS_API_URL + EVENTS_ADD;
		PUSHER_KEY = DEV_PUSHER_KEY;
		PUSHER_APP_KEY = DEV_PUSHER_APP_KEY;
		REGISTER_USERS_URL = DEV_USERS_API_URL + REGISTER_USERS;
		CONFIRMATION_USERS_URL = DEV_USERS_API_URL + CONFIRMATION_USERS;
		SIGNIN_USERS_URL = DEV_USERS_API_URL + SIGNIN_USERS;
		RECOVERY_PASSWORD_URL = DEV_USERS_API_URL + RECOVERY_PASSWORD;
		FORWARD_CODE_URL = DEV_USERS_API_URL + FORWARD_CODE;
		CHANGE_PASSWORD_URL = DEV_USERS_API_URL + CHANGE_PASSWORD;
		SHOW_USER_URL = DEV_USERS_API_URL + SHOW_USER;
		SIGN_OUT_URL = DEV_USERS_API_URL + SIGN_OUT;
		UPDATE_USER_URL = DEV_USERS_API_URL + UPDATE_USER;
		UPDATE_TOKEN_URL = DEV_USERS_API_URL + UPDATE_TOKEN;
		RANKING_URL = DEV_USERS_API_URL + RANKING;
		CATEGORIES_URL=DEV_USERS_CATEGOTY_URL+CATEGORIES;
		ACTIVES_URL=DEV_USERS_CATEGOTY_URL+CATEGORIES;
	}

	public static void setProductionEnviroment() {
		PUSHERS_API_URL = PRO_PUSHERS_API_URL;
		EVENT_CATEGORIES_URL = PUSHERS_API_URL + EVENTS_CATEGORIES;
		ADD_EVENT_URL = PUSHERS_API_URL + EVENTS_ADD;
		PUSHER_KEY = PRO_PUSHER_KEY;
		PUSHER_APP_KEY = PRO_PUSHER_APP_KEY;
		REGISTER_USERS_URL = PRO_USERS_API_URL + REGISTER_USERS;
		CONFIRMATION_USERS_URL = PRO_USERS_API_URL + CONFIRMATION_USERS;
		SIGNIN_USERS_URL = PRO_USERS_API_URL + SIGNIN_USERS;
		RECOVERY_PASSWORD_URL = PRO_USERS_API_URL + RECOVERY_PASSWORD;
		FORWARD_CODE_URL = PRO_USERS_API_URL + FORWARD_CODE;
		CHANGE_PASSWORD_URL = PRO_USERS_API_URL + CHANGE_PASSWORD;
		SHOW_USER_URL = PRO_USERS_API_URL + SHOW_USER;
		SIGN_OUT_URL = PRO_USERS_API_URL + SIGN_OUT;
		UPDATE_USER_URL = PRO_USERS_API_URL + UPDATE_USER;
		UPDATE_TOKEN_URL = PRO_USERS_API_URL + UPDATE_TOKEN;
		RANKING_URL = PRO_USERS_API_URL + RANKING;
		CATEGORIES_URL=PRO_USERS_CATEGOTY_URL+CATEGORIES;
		ACTIVES_URL=PRO_USERS_CATEGOTY_URL+ACTIVES;
	}
}
