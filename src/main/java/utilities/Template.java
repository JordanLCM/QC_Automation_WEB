package utilities;

import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class template {

	BaseDriver baseD = new BaseDriver();
	CreateReport createR = new CreateReport();
	StopSystem stopS = new StopSystem(createR);
	TakeScreenshot takeSS = new TakeScreenshot(baseD);
	SoftAssert softAssert = new SoftAssert();

	String url = "https://wl003.the777888.com/";

	@BeforeClass
	public void setupBrowser() {
		createR.generateReport("Open site test report");
		baseD.setDriverProperty();
	}

	@Test(priority = 0)
	public void goToSite() {

	}

	@Test(priority = 3)
	public void skipTest() throws InterruptedException {

	}

	@Test(priority = 2)
	public void failTest() throws InterruptedException {

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
			createR.setExtentTest().fail("<<<<< Step : " + resultOfCaseStatus + " is failed! >>>>>").addScreenCaptureFromPath("\\Users\\Jordan Liu\\git\\QC_Automation_WEB\\src\\main\\resources\\Screenshots\\" + resultOfCaseStatus + ".png");
		}
	}
}
