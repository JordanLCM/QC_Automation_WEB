package utilities;

public class StopSystem {
	
	CreateReport createR = new CreateReport();
	
	public void createFinalReport() throws InterruptedException {
		createR.setExtentReport().flush();
	}

	public StopSystem(CreateReport createR) {
		this.createR = createR;
	}
}
