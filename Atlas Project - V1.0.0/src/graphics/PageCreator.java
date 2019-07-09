package graphics;

import graphics.bountypage.BountyPageController;
import graphics.characterSelectionPage.CharacterSelectionController;
import graphics.combatpage.CombatPageController;
import graphics.createAccountPage.CreateAccountPageController;
import graphics.createCharacterPage.CharacterCreationController;
import graphics.forgotPasswordPage.ForgotPasswordPageController;
import graphics.homepage.HomePageController;
import graphics.inventoryPage.InventoryPageController;
import graphics.loginpage.LoginPageController;
import graphics.musicPage.MusicPageController;
import graphics.onlinePage.OnlinePageController;
import graphics.optionsPage.OptionsPageController;
import graphics.profilePage.ProfilePageController;
import graphics.storyPage.StoryPageController;

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

	public static final String OPTIONS = "options page";

	public static final String STORY_MODE = "story mode page";

	public static final String INVENTORY_PAGE = "inventory page";
	
	public static final String ONLINE_PAGE = "online page";
	
	public static final String CHARACTER_SELECTION = "character selection page";
	
	public static final String Bounty_Page = "bounty page";

	public static final String MUSIC_PAGE = "music page";
	public static final String Character_Creation = "creation page";
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
		case INVENTORY_PAGE : return new InventoryPageController();
		case ONLINE_PAGE: return new OnlinePageController();
		case PROFILE_PAGE: return new ProfilePageController();
		case STORY_MODE : return new StoryPageController();
		case OPTIONS: return new OptionsPageController();
		case CHARACTER_SELECTION: return new CharacterSelectionController();
		case Bounty_Page : return new BountyPageController();
		case MUSIC_PAGE : return new MusicPageController();
		case Character_Creation : return new CharacterCreationController();
		default: return null;
		}
	}

}

