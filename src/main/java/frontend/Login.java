package frontend;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import utilities.BaseDriver;
import utilities.CreateReport;
import utilities.StopSystem;
import utilities.TakeScreenshot;

public class Login {

	BaseDriver baseD = new BaseDriver();
	CreateReport createR = new CreateReport();
	StopSystem stopS = new StopSystem(createR);
	TakeScreenshot takeSS = new TakeScreenshot(baseD);
	SoftAssert softAssert = new SoftAssert();

	String reportName = "Login test report";
	String url = "https://wl003.the777888.com/";
	String screenShotPath = "\\Eclipse WorkSpace Master\\QC_Automation_WEB\\src\\main\\resources\\Screenshots\\";
	String currentUrl;

	@BeforeClass
	public void setupBrowser() {
		createR.generateReport(reportName);
		baseD.setDriverProperty();
	}

	@Test(priority = 0)
	public void goToSite() {
		createR.createTest("Go to site");

		baseD.getDriver().get(url);
		currentUrl = baseD.getDriver().getCurrentUrl();
		if (currentUrl.equals(url)) {
			createR.setExtentTest().info("Site " + currentUrl + " is opened!");
		} else {
			softAssert.assertTrue(false);
			createR.setExtentTest().info("Click Login Option is failed");
			softAssert.assertAll();
		}
	}

	String doNotShowAgainToday;

	@Test(priority = 1)
	public void closeAnnouncement() throws InterruptedException {
		int whileNum = 1;

		while (whileNum <= 3) {
			try {
				if (baseD.getDriver().findElement(By.xpath("(//span[contains(text(),'今日不再显示')])[" + whileNum + "]")).isDisplayed()) {
					createR.createTest("Close Announcement");

					WebElement 今日不再显示 = baseD.getDriver().findElement(By.xpath("(//span[contains(text(),'今日不再显示')])[" + whileNum + "]"));
					doNotShowAgainToday = 今日不再显示.getText();

					if (doNotShowAgainToday.equals("今日不再显示")) {
						createR.setExtentTest().info("Clicked " + doNotShowAgainToday);
						今日不再显示.click();

						if (baseD.getDriver().findElement(By.xpath("(//button[@aria-label='Close'])[" + whileNum + "]")).isDisplayed()) {
							WebElement closeButton = baseD.getDriver().findElement(By.xpath("(//button[@aria-label='Close'])[" + whileNum + "]"));
							closeButton.click();
							Thread.sleep(1000);
							whileNum++;
						}
					}
				}
			} catch (NoSuchElementException e) {
				createR.setExtentTest().info("Close Announcement is skipping");
				throw new SkipException("Close Announcement is skipping");
			}
		}
	}

	String loginOptionButton;

	@Test(priority = 2)
	public void clickLoginOption() throws InterruptedException {
		if (baseD.getDriver().findElement(By.id("header_login")).isDisplayed()) {
			createR.createTest("Click Login Option");

			WebElement 登录Option = baseD.getDriver().findElement(By.id("header_login"));
			loginOptionButton = 登录Option.getText();
			if (loginOptionButton.equals("登录")) {
				createR.setExtentTest().info("Clicked " + loginOptionButton);
				登录Option.click();
				Thread.sleep(1000);
			} else {
				softAssert.assertTrue(false);
				createR.setExtentTest().info("Click Login Option is failed");
				softAssert.assertAll();
			}
		}
	}

	String userID = "qctester01010101";
	String passID = "test123";
	String otp = "123456";
	String attribute = "placeholder";

	String userAttribute;

	@Test(priority = 3)
	public void inputUserID() throws InterruptedException {
		if (baseD.getDriver().findElement(By.xpath("//div[@class='login_popup_container']")).isDisplayed()) {
			createR.createTest("Input user ID");

			WebElement 请输入帐号 = baseD.getDriver().findElement(By.id("username"));
			userAttribute = 请输入帐号.getAttribute(attribute);
			if (userAttribute.equals("请输入帐号")) {
				请输入帐号.clear();
				请输入帐号.sendKeys(userID);
				createR.setExtentTest().info("UserID keyed is " + userID);
			}
		}
	}

	String passAttribute;
	String eyeIconAttribute;
	String attributeClass = "class";

	@Test(dependsOnMethods = { "inputUserID" })
	public void inputPassID() throws InterruptedException {
		createR.createTest("Input pass ID");

		WebElement 请输入密码 = baseD.getDriver().findElement(By.id("password"));
		passAttribute = 请输入密码.getAttribute(attribute);
		if (passAttribute.equals("请输入密码")) {
			请输入密码.clear();
			请输入密码.sendKeys(passID);
			createR.setExtentTest().info("PassID keyed is " + passID);

			WebElement eyeIcon = baseD.getDriver().findElement(By.xpath("//div[@class='ico ico-eye_close']"));
			eyeIconAttribute = eyeIcon.getAttribute(attributeClass);
			if (eyeIconAttribute.equals("ico ico-eye_close")) {
				eyeIcon.click();
				createR.setExtentTest().info("Clicked eye icon");
				Thread.sleep(500);

				eyeIconAttribute = eyeIcon.getAttribute("class");
				if (eyeIconAttribute.equals("ico ico-eye_open")) {
					eyeIcon.click();
					createR.setExtentTest().info("Clicked eye icon again");
					Thread.sleep(500);
				}
			}
		}
	}

	String otpAttribute;

	@Test(dependsOnMethods = { "inputPassID" })
	public void inputOTP() throws InterruptedException {
		createR.createTest("Input OTP");

		WebElement 请输入验证码 = baseD.getDriver().findElement(By.id("captcha_code"));
		otpAttribute = 请输入验证码.getAttribute(attribute);
		if (otpAttribute.equals("请输入验证码")) {
			请输入验证码.clear();
			请输入验证码.sendKeys(otp);
			createR.setExtentTest().info("Otp keyed is  " + otp);
		}
	}

	String loginButtonAttribute;

	@Test(dependsOnMethods = { "inputOTP" })
	public void clickLoginButton() throws InterruptedException {
		createR.createTest("Click Login Button");

		WebElement 登录Button = baseD.getDriver().findElement(By.id("login_popup_btn"));
		loginButtonAttribute = 登录Button.getAttribute(attributeClass);
		if (loginButtonAttribute.equals("btn btn_major active")) {
			登录Button.click();
			createR.setExtentTest().info("Clicked login button");
		}
	}

	@Test(dependsOnMethods = { "clickLoginButton" })
	public void verifyLogin() throws InterruptedException {
		createR.createTest("Verify Login");

		WebElement qctester01010101 = baseD.getDriver().findElement(By.xpath("//body/header/div[@class='header_top_outside']/div[@class='header_top']/div[@class='header_bottom_right']/div/div[@class='header_bottom_after_login']/div[1]/a[1]"));
		if (qctester01010101.isDisplayed()) {
			createR.setExtentTest().info("Logged in to userID " + userID);
			Thread.sleep(2000);
			takeSS = new TakeScreenshot(baseD);
			takeSS.takeScreenShot(userID + " passed");
			createR.setExtentTest().info("Logged out of userID " + userID).addScreenCaptureFromPath(screenShotPath + userID + " passed" + ".png");
		} else {
			softAssert.assertTrue(false);
			createR.setExtentTest().info("Click Login Option is failed");
			softAssert.assertAll();
		}
	}

	@AfterClass
	public void stopBrowser() throws InterruptedException {
		Thread.sleep(1000);
		baseD.getDriver().quit();
		stopS.createFinalReport();
	}

	@AfterMethod
	public void logCaseStatus(ITestResult result) {
		String resultOfCaseStatus = result.getName();
		if (result.getStatus() == ITestResult.SUCCESS) {
			createR.setExtentTest().pass("<<<<< Step : " + resultOfCaseStatus + " is passed! >>>>>");
		}
		if (result.getStatus() == ITestResult.SKIP) {
			createR.setExtentTest().skip("<<<<< Step : " + resultOfCaseStatus + " is skipped! >>>>>");
		}
		if (result.getStatus() == ITestResult.FAILURE) {
			takeSS = new TakeScreenshot(baseD);
			takeSS.takeScreenShot(resultOfCaseStatus);
			createR.setExtentTest().fail("<<<<< Step : " + resultOfCaseStatus + " is failed! >>>>>").addScreenCaptureFromPath("\\Eclipse WorkSpace Master\\QC_Automation_WEB\\src\\main\\resources\\Screenshots\\" + resultOfCaseStatus + ".png");
		}
	}
}
