package graphics;

import graphics.combatpage.CombatPageController;
import graphics.createAccountPage.CreateAccountPageController;
import graphics.forgotPasswordPage.ForgotPasswordPageController;
import graphics.homepage.HomePageController;
import graphics.loginpage.LoginPageController;

// TODO: Auto-generated Javadoc
/**
 * The Class PageCreator.
 * @author Ben Davis, Colin Burdine, David Beggs, Jackson Raffety, Meghan Bibb ,Sam Muller
 */
public class PageCreator{
	
	/** The Constant CREATE_ACCOUNT_PAGE. */
	public static final String CREATE_ACCOUNT_PAGE = "create account";
	
	/** The Constant EDIT_ACCOUNT_PAGE. */
	public static final String EDIT_ACCOUNT_PAGE = "edit account";
	
	/** The Constant FORGOT_PASSWORD_PAGE. */
	public static final String FORGOT_PASSWORD_PAGE = "forgot password";
	
	/** The Constant HOME_PAGE. */
	public static final String HOME_PAGE = "home page";
	
	/** The Constant LOGIN_PAGE. */
	public static final String LOGIN_PAGE = "login page";
	
	/** The Constant MESSAGE_PAGE. */
	public static final String MESSAGE_PAGE = "message page";
	
	/** The Constant PAYMENT_PAGE. */
	public static final String PAYMENT_PAGE = "payment page";
	
	/** The Constant SUPPORT_PAGE. */
	public static final String SUPPORT_PAGE = "support page";
	
	/** The Constant SWIPE_PAGE. */
	public static final String SWIPE_PAGE = "swipe page";
	
	/** The Constant MATCHES_PAGE. */
	public static final String MATCHES_PAGE = "matches page";
	
	/** The Constant PROFILE_PAGE. */
	public static final String PROFILE_PAGE = "profile page";
	
	/** The Constant CREDITS_PAGE. */
	public static final String CREDITS_PAGE = "credits page";

	public static final String COMBAT_PAGE = "combat page";
	
	/**
	 * Gets the page.
	 *
	 * @param pageType the page type
	 * @return the page
	 */
	public static PageController getPage(String pageType) {
		switch(pageType) {
		case LOGIN_PAGE: return new LoginPageController();
		case HOME_PAGE: return new HomePageController();
		case COMBAT_PAGE: return new CombatPageController();
		case CREATE_ACCOUNT_PAGE: return new CreateAccountPageController();
		case FORGOT_PASSWORD_PAGE : return new ForgotPasswordPageController();
		default: return null;
		}
	}

}

