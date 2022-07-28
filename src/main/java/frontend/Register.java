package frontend;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import utilities.BaseDriver;
import utilities.CreateReport;
import utilities.GenerateRandomNumbers;
import utilities.StopSystem;
import utilities.TakeScreenshot;

public class Register {

	BaseDriver baseD = new BaseDriver();
	CreateReport createR = new CreateReport();
	StopSystem stopS = new StopSystem(createR);
	TakeScreenshot takeSS = new TakeScreenshot(baseD);
	SoftAssert softAssert = new SoftAssert();
	GenerateRandomNumbers generateRN = new GenerateRandomNumbers();

	String reportName = "Register test report";
	String url = "https://wl003.the777888.com/";
	String screenShotPath = "\\Eclipse WorkSpace Master\\QC_Automation_WEB\\src\\main\\resources\\Screenshots\\";
	String currentUrl;
	
	int genNum = generateRN.generateRandomNumbers();

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
			createR.setExtentTest().info("Go to site is failed");
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

	String registerOptionButton;

	@Test(priority = 2)
	public void clickRegisterOption() throws InterruptedException {
		if (baseD.getDriver().findElement(By.id("header_register")).isDisplayed()) {
			createR.createTest("Click register option");

			WebElement 注册Button = baseD.getDriver().findElement(By.id("header_register"));
			registerOptionButton = 注册Button.getText();
			if (registerOptionButton.equals("注册")) {
				createR.setExtentTest().info("Clicked " + registerOptionButton);
				注册Button.click();
				Thread.sleep(1000);
			} else {
				softAssert.assertTrue(false);
				createR.setExtentTest().info("Click register option is failed");
				softAssert.assertAll();
			}
		}
	}

	String userID = "qctester";
	String passID = "test123";
	String referralID = "refer123";
	String otp = "123456";
	String attribute = "placeholder";

	String userAttribute;

	@Test(dependsOnMethods = { "clickRegisterOption" })
	public void inputUserID() throws InterruptedException {
		if (baseD.getDriver().findElement(By.xpath("//div[@class='register_popup_container']")).isDisplayed()) {
			createR.createTest("Input user ID");

			WebElement 请输入账号 = baseD.getDriver().findElement(By.id("r_username"));
			userAttribute = 请输入账号.getAttribute(attribute);
			if (userAttribute.equals("请输入账号（4-16 位英文及数字）")) {
				createR.setExtentTest().info("Clicked " + userAttribute);
				请输入账号.clear();
				请输入账号.sendKeys(userID + genNum);
				createR.setExtentTest().info("UserID keyed is " + userID + genNum);
			}
		}
	}

	String passAttribute;
	String eyeIconAttribute;
	String attributeClass = "class";

	@Test(dependsOnMethods = { "inputUserID" })
	public void inputPassID() throws InterruptedException {
		createR.createTest("Input pass ID");

		WebElement 请输入密码 = baseD.getDriver().findElement(By.id("r_password"));
		passAttribute = 请输入密码.getAttribute(attribute);
		if (passAttribute.equals("请输入密码（6-15 位英文及数字）")) {
			createR.setExtentTest().info("Clicked " + passAttribute);
			请输入密码.clear();
			请输入密码.sendKeys(passID);
			createR.setExtentTest().info("PassID keyed is " + passID);

			WebElement eyeIcon = baseD.getDriver().findElement(By.xpath("//div[@class='toggle_password']//div[@class='ico ico-eye_close']"));
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

	String dobAttribute;

	@Test(priority = 5, dependsOnMethods = { "inputPassID" })
	public void inputDateOfBirth() throws InterruptedException {
		if (baseD.getDriver().findElement(By.id("r_dob")).isDisplayed()) {
			try {
				createR.createTest("Input date of birth");

				WebElement 出生日期 = baseD.getDriver().findElement(By.id("r_dob"));
				dobAttribute = 出生日期.getAttribute(attribute);
				if (dobAttribute.equals("出生日期")) {
					createR.setExtentTest().info("Clicked " + dobAttribute);
					出生日期.click();

					WebElement yearSelection = baseD.getDriver().findElement(By.xpath("//div[@class='drp-calendar left single']//select[@class='yearselect']"));
					yearSelection.click();
					Select select = new Select(yearSelection);
					Thread.sleep(500);
					select.selectByValue("1994");

					WebElement daySelection = baseD.getDriver().findElement(By.xpath("(//td[contains(text(),'1')])[1]"));
					daySelection.click();
				}
			} catch (NoSuchElementException e) {
				createR.setExtentTest().info("Input date of birth is skipping");
				throw new SkipException("Input date of birth is skipping");
			}
		}
	}

	String referralAttribute;

	@Test(priority = 4, dependsOnMethods = { "inputCaptcha" })
	public void inputReferral() throws InterruptedException {
		createR.createTest("Input referral");

		WebElement 请输入推荐码 = baseD.getDriver().findElement(By.id("referral_code"));
		referralAttribute = 请输入推荐码.getAttribute(attribute);
		if (referralAttribute.equals("请输入推荐码（非必填）")) {
			createR.setExtentTest().info("Clicked " + referralAttribute);
			请输入推荐码.clear();
			请输入推荐码.sendKeys(referralID);
			createR.setExtentTest().info("Referral keyed is " + referralID);
			Thread.sleep(1000);
			请输入推荐码.clear();
		}
	}

	String captchaAttribute;

	@Test(priority = 3, dependsOnMethods = { "inputPassID" })
	public void inputCaptcha() throws InterruptedException {
		createR.createTest("Input captcha");

		WebElement 请输入验证码 = baseD.getDriver().findElement(By.id("ipt_code3"));
		captchaAttribute = 请输入验证码.getAttribute(attribute);
		if (captchaAttribute.equals("请输入验证码")) {
			createR.setExtentTest().info("Clicked " + captchaAttribute);
			请输入验证码.clear();
			请输入验证码.sendKeys(otp);
			createR.setExtentTest().info("Captcha keyed is " + otp);
		}
	}

	String registerAttribute;

	@Test(priority = 6, dependsOnMethods = { "inputCaptcha" })
	public void clickRegisterButton() throws InterruptedException {
		createR.createTest("Click register button");

		WebElement 注册Button = baseD.getDriver().findElement(By.id("register_btn"));
		registerAttribute = 注册Button.getAttribute(attributeClass);
		if (registerAttribute.equals("btn btn_major active")) {
			createR.setExtentTest().info("Clicked " + registerAttribute);
			注册Button.click();
			createR.setExtentTest().info("Clicked register button");
		}
	}

	@Test(dependsOnMethods = { "clickRegisterButton" })
	public void verifyRegister() throws InterruptedException {
		baseD.getDriver().manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		createR.createTest("Verify register");
		
		WebElement qctester = baseD.getDriver().findElement(By.xpath("//body/header/div[@class='header_top_outside']/div[@class='header_top']/div[@class='header_bottom_right']/div/div[@class='header_bottom_after_login']/div[1]/a[1]"));
		if (qctester.isDisplayed()) {
			createR.setExtentTest().info("Registered userID " + userID + genNum);
			Thread.sleep(2000);
			takeSS = new TakeScreenshot(baseD);
			takeSS.takeScreenShot(userID + genNum + " passed");
			createR.setExtentTest().info("Registered userID " + userID).addScreenCaptureFromPath("\\Eclipse WorkSpace Master\\QC_Automation_WEB\\src\\main\\resources\\Screenshots\\" + userID  + genNum + " passed" + ".png");
		} else {
			softAssert.assertTrue(false);
			createR.setExtentTest().info("Register failed");
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