package frontend;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import utilities.BaseDriver;
import utilities.CreateReport;
import utilities.StopSystem;
import utilities.TakeScreenshot;

public class OpenSite {

	BaseDriver baseD = new BaseDriver();
	CreateReport createR = new CreateReport();
	StopSystem stopS = new StopSystem(createR);
	TakeScreenshot takeSS = new TakeScreenshot(baseD);

	String url = "https://wl003.the777888.com/";

	@BeforeClass
	public void setupBrowser() {
		createR.generateReport("Open site test report");
		baseD.setDriverProperty();
	}

	@Test(priority = 0)
	public void goToSite() {
		createR.createTest("Go to site");
		baseD.getDriver().get(url);
		String currentUrl = baseD.getDriver().getCurrentUrl();
		if (currentUrl.equals(url)) {
			createR.setExtentTest().info("Site " + currentUrl + " is opened!");
		}
	}
	
	@Test(priority = 2)
	public void skipTest() throws InterruptedException {
		createR.createTest("Test skipped");
		throw new SkipException("Skipping");
	}
	
	@Test(priority = 3)
	public void failTest() throws InterruptedException {
		createR.createTest("Test failed");
		Assert.assertEquals(1, 2);
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
			createR.setExtentTest().fail("<<<<< Step : " + resultOfCaseStatus + " is failed! >>>>>");
		}
	}
}
