package utilities;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.ImageHtmlEmail;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener{
	
	public ExtentSparkReporter sparkReporter; //UI of the report
	public ExtentReports extent; //populate the common infor
	public ExtentTest test;  //update report
	
	String repName;
	
	public void onStart(ITestContext context) {
		
		//for time stamp
		/*SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss"); //use to give the the time format and used this to store the history 
		Date dt=new Date();  // date class in java for date
		String currentdatetimestamp=df.format(dt);*/
		
		//instead of writing above line in multiple steps i am writing this into single line
		String timeStamp= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		//giving the report name
		repName="Test-Report-" + timeStamp + ".html";
		sparkReporter=new ExtentSparkReporter(".\\reports\\" + repName);
		
		sparkReporter.config().setDocumentTitle("opencart Automation Report");
		sparkReporter.config().setReportName("opencart Functional Report");
		sparkReporter.config().setTheme(Theme.DARK);
		
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "OpenCart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Moudule", "Customer");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));  //to get the name of the user 
		extent.setSystemInfo("Enviornment", "QA");
		
		String os=context.getCurrentXmlTest().getParameter("os");  // simply directly taking from XML file
		extent.setSystemInfo("Operating System", os);
		
		String browser=context.getCurrentXmlTest().getParameter("browser");  // simply directly taking from XML file
		extent.setSystemInfo("Browser", browser);
		
		List<String> includeGroups = context.getCurrentXmlTest().getIncludedGroups();
		if(!includeGroups.isEmpty())
		{
			extent.setSystemInfo("Groups", includeGroups.toString());
		}
	  }
	
	public void onTestSuccess(ITestResult result) {
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.log(Status.PASS, result.getName() + "Test case PASSED is : ");
	  }
	
	public void onTestFailure(ITestResult result) {
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		
		test.log(Status.FAIL, result.getName() + "Test case FAILED is : ");
		test.log(Status.INFO, result.getThrowable().getMessage());
		
		//attaching ss also
		try {
			String imgPath=new BaseClass().captureScreen(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	  }
	
	public void onTestSkipped(ITestResult result) {
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.log(Status.SKIP,  result.getName() + "got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	
	
	}
	
	public void onFinish(ITestContext context) {
		extent.flush();
		
		//open the report automatically
		String pathOFExtentReport=System.getProperty("user.dir")+"\\reports\\" + repName;
		File extentReport=new File(pathOFExtentReport);
		
		try
		{
			Desktop.getDesktop().browse(extentReport.toURI());  //open the report on browser
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//Send the report automatically to mail when report is genrated
	/*	try
		{
			//converting report in url form
			URL url=new URL("file:///"+System.getProperty("user.dir")+"\\reports\\" + repName);
			//creating email msg (for this you have to add one depency
			//<!-- https://mvnrepository.com/artifact/org.apache.commons/commons-email -->
			ImageHtmlEmail email=new ImageHtmlEmail();
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}*/
	}


}
