package testCases;

import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import PageObjects.AccountRegistrationPage;
import PageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
	
	@Test(groups= {"Regression", "Master"})
	public void verify_account_registration() throws InterruptedException
	{
		logger.info("******* starting execution *********");
		try {
		HomePage hp =new HomePage(driver);
		hp.clickOnMyAccount();
		hp.clickOnRegister();
		logger.info("******* Home page activity is done *********");
		
		logger.info("******* Account registration activity is started *********");
		AccountRegistrationPage rp=new AccountRegistrationPage(driver);
		rp.setFirstName(randomString().toUpperCase());
		rp.setLastName(randomString().toUpperCase());
		rp.setEmail(randomString()+ "@gmail.com");
		rp.setTelephone(randomNumber());
		
		String password=randomAlphaNumeric();
		rp.setPassword(password);
		rp.setConfirmPassword(password);
		
		rp.setPrivacyPolicy();
		rp.clickContinueBtn();
		logger.info("******* all required field of register page is added *********");
		
		String confirmMsg=rp.getConfirmationMsg();	
		Assert.assertEquals(confirmMsg, "Your Account Has Been Created!");
		}
		catch(Exception e)
		{
			logger.error("Test Failed ...");	
			logger.debug("Debug logs...");
			Assert.fail();
		}
	}
	
}
