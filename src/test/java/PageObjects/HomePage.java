package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage{

	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy (xpath="//span[normalize-space()='My Account']")
	WebElement linkOfMyAccount;
	
	@FindBy (xpath="//a[normalize-space()='Register']")
	WebElement linkOfRegistration;
	
	@FindBy (xpath="//a[text()='Login']")
	WebElement linkOfLogin;
	
	public void clickOnMyAccount()
	{
		linkOfMyAccount.click();
	}
	
	public void clickOnRegister()
	{
		linkOfRegistration.click();
	}
	
	public void clickOnLogin()
	{
		linkOfLogin.click();
	}
	



}
