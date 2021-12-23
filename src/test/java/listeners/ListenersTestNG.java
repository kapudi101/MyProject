package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import framework.Utilities;

public class ListenersTestNG implements ITestListener {

	Utilities utilities = new Utilities();

	@Override
	public void onStart(ITestContext result) {
		System.out.println("Test execution started : " + result.getName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("Test passed " + result.getName());
		try {
			utilities.takeSnapShot("onTestSuccess" + utilities.timereturn() + ".png");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("Test failed " + result.getName());
        try {
            utilities.takeSnapShot("onTestFailure" + utilities.timereturn() + ".png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("Test skipped " + result.getName());
	}

	@Override
	public void onFinish(ITestContext result) {
		System.out.println("Test execution finished : " + result.getName());

	}
}
