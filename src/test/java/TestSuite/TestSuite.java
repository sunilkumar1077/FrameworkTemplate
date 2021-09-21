package TestSuite;

import org.junit.*;
import org.junit.experimental.ParallelComputer;
import org.junit.runner.JUnitCore;

import CentralConfig.DB_UpdateMetrics;
import FrameworkSource.global.ExecutionController;

import FrameworkSource.global.reporter.ReportEvents;
import FrameworkSource.global.reporter.ReportGenerator;
import Tests.*;

public class TestSuite {

	@Test
	public void test() throws Exception {

		ReportGenerator.TestSuiteRun = true;
		Class[] cls= ExecutionController.ExecutionController();
		//Class[] cls= {ConsumerVerbal.class};
		ReportEvents.CaptureScreenShots("true", "false");
		
		//Run tests serially			 
		JUnitCore.runClasses(ParallelComputer.methods(), cls);
		
		//Run tests parallel
		//JUnitCore.runClasses(ParallelComputer.classes(), cls);
		
		//ALMBridge.SendStatus("true");
		ReportGenerator.GenerateSuitReport("true");
		
		
	}

}
