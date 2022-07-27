package frontend;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
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

public class Register {

	BaseDriver baseD = new BaseDriver();
	CreateReport createR = new CreateReport();
	StopSystem stopS = new StopSystem(createR);
	TakeScreenshot takeSS = new TakeScreenshot(baseD);
	SoftAssert softAssert = new SoftAssert();

	String url = "https://wl003.the777888.com/";
	String screenShotPath = "\\Eclipse WorkSpace Master\\QC_Automation_WEB\\src\\main\\resources\\Screenshots\\";
	String currentUrl;

	@BeforeClass
	public void setupBrowser() {
		createR.generateReport("Register test report");
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
			createR.setExtentTest().fail("<<<<< Step : " + resultOfCaseStatus + " is failed! >>>>>").addScreenCaptureFromPath(screenShotPath + resultOfCaseStatus + ".png");
		}
	}
}