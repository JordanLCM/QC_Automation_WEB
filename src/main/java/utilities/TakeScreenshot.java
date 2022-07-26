package utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class TakeScreenshot {
	
	BaseDriver baseD = new BaseDriver();

	String screenShotPath = "\\Eclipse-Workspace\\QC_Automation_WEB\\Screenshots\\";

	public void takeScreenShot(String fileName) {
		File screenShot = ((TakesScreenshot) baseD.getDriver()).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenShot, new File(screenShotPath + fileName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TakeScreenshot(BaseDriver baseD) {
		this.baseD = baseD;
	}
}