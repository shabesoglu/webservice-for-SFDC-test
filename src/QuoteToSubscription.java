
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;


public class QuoteToSubscription {
	private static int counter = 100;
	private static WebDriver driver;
	private static String baseUrl;


	static  String USERNAME;// = "sahin.habesoglu@zuora.com";
	static  String PASSWORD;// = "Agile#12345";
	static  String TOKEN;//
	public static void main(String[] args) {

		USERNAME = "zforceactivation+p2@70demo.com";
		PASSWORD = "Agile#12345";
		TOKEN = "ArgYr5gwffpvIGwo0lT6Rcgf";

		//System.out.println(orgCredentials[0][0]+" "+orgCredentials[0][1]+" "+orgCredentials[0][2]);
		driver = new FirefoxDriver();
		baseUrl = "https://login.salesforce.com";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);


		createQuotes3();


	}

	private static void createQuotes3() throws InterruptedException {

		driver.get(baseUrl + "/");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("zforceactivation+4@52demo.com");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("Agile#12345");
		driver.findElement(By.id("Login")).click();
		//driver.findElement(By.xpath("//li[@id='Opportunity_Tab']/a")).click();
		for(int i=1; i<=counter; i++){
			//driver.get(baseUrl + "/001G0000019o9sM");
			//driver.findElement(By.xpath("//td[@id='bodyCell']/div[3]/div/div/div[2]/table/tbody/tr[2]/th/a")).click();
			
		    //driver.findElement(By.xpath("//td[@id='topButtonRow']/input[6]")).click();
		    //driver.findElement(By.xpath("//table[@id='j_id0:j_id2:j_id16:j_id17:j_id41:j_id42:j_id83:accTypeRadio']/tbody/tr/td/input")).click();
		    //driver.findElement(By.xpath("//table[@id='j_id0:j_id2:j_id16:j_id17:j_id41:j_id42:subscriptionSection:newSubTypeRadio']/tbody/tr/td/input")).click();
		    //driver.findElement(By.xpath("//td[@id='j_id0:j_id2:j_id16:j_id17:j_id41:j_id42:j_id91:bottom']/input[2]")).click();
			
			/*
			driver.findElement(By.xpath("//td[@id='topButtonRow']/input[6]")).click();
			driver.findElement(By.xpath("//table[@id='table-billingAccounts']/tbody/tr/td/input")).click();
			driver.findElement(By.xpath("//table[@id='j_id0:j_id2:j_id16:j_id17:j_id41:j_id42:subscriptionSection:subTypeRadio']/tbody/tr/td/input")).click();
			driver.findElement(By.xpath("//table[@id='j_id0:j_id2:j_id16:j_id17:j_id41:j_id42:j_id83:accTypeRadio']/tbody/tr/td/input")).click();
			driver.findElement(By.xpath("//table[@id='j_id0:j_id2:j_id16:j_id17:j_id41:j_id42:subscriptionSection:newSubTypeRadio']/tbody/tr/td/input")).click();
			driver.findElement(By.xpath("//td[@id='j_id0:j_id2:j_id16:j_id17:j_id41:j_id42:j_id91:bottom']/input[2]")).click();
			*/
			
			System.out.println("Counter start= "+i+" , time = "+Calendar.getInstance().getTime() );
		    driver.get(baseUrl + "/001G0000019o9sM");
		    driver.findElement(By.xpath("//td[@id='topButtonRow']/input[6]")).click();
		    // ERROR: Caught exception [ERROR: Unsupported command [waitForPopUp | _self | 30000]]
		    driver.findElement(By.xpath("//table[@id='j_id0:j_id2:j_id16:j_id17:j_id41:j_id42:j_id83:accTypeRadio']/tbody/tr/td/input")).click();
		    driver.findElement(By.xpath("//table[@id='j_id0:j_id2:j_id16:j_id17:j_id41:j_id42:subscriptionSection:newSubTypeRadio']/tbody/tr/td/input")).click();
		    driver.findElement(By.xpath("//table[@id='j_id0:j_id2:j_id16:j_id17:j_id41:j_id42:subscriptionSection:newSubTypeRadio']/tbody/tr/td/input")).click();
		    driver.findElement(By.cssSelector("input.btn")).click();
			
			
			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:0:j_id59']/div[2]/table/tbody/tr[2]/td[2]/div/span/span/a")).click();
			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:0:j_id59']/div[2]/table/tbody/tr[2]/td[2]/div/span/input")).clear();
			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:0:j_id59']/div[2]/table/tbody/tr[2]/td[2]/div/span/input")).sendKeys("2/20/2014");
			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:1:j_id59:j_id60:0:lookupfields:j_id67:j_id68:j_id74']/img")).click();
			// ERROR: Caught exception [ERROR: Unsupported command [waitForPopUp | Popup | 30000]]
			driver.switchTo().window("Popup");
			driver.findElement(By.linkText("Jack Rogers")).click();
			driver.switchTo().window("");
			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:1:j_id59:j_id60:1:lookupfields:j_id67:j_id68:j_id74']/img")).click();
			// ERROR: Caught exception [ERROR: Unsupported command [waitForPopUp | Popup | 30000]]
			driver.switchTo().window("Popup");
			driver.findElement(By.linkText("Jack Rogers")).click();
			driver.switchTo().window("");
			new Select(driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:1:j_id59']/div[2]/table/tbody/tr[2]/td/div/select"))).selectByVisibleText("Credit Card");
			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:2:j_id59']/div[2]/table/tbody/tr/td/div/span/span/a")).click();
			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:2:j_id59']/div[2]/table/tbody/tr/td/div/span/input")).clear();
			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:2:j_id59']/div[2]/table/tbody/tr/td/div/span/input")).sendKeys("2/20/2014");
			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:2:j_id59:j_id60:1:j_id171']/div/table/tbody/tr/td/div/input")).clear();
			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:2:j_id59:j_id60:1:j_id171']/div/table/tbody/tr/td/div/input")).sendKeys("5");
			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:2:j_id59:j_id60:1:j_id171']/div/table/tbody/tr[2]/td/div/input")).clear();
			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:2:j_id59:j_id60:1:j_id171']/div/table/tbody/tr[2]/td/div/input")).sendKeys("5");
			driver.findElement(By.xpath("//td[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:propertyPageBlockButtons:bottom']/input[2]")).click();
			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id17:j_id55:j_id59:0:j_id60:j_id61:j_id76:j_id77:j_id83']/img")).click();
			// ERROR: Caught exception [ERROR: Unsupported command [waitForPopUp | Popup | 30000]]
			driver.switchTo().window("Popup");
		    driver.findElement(By.xpath("//td[@id='listComponent:a0hG00000048hCqIAI:name']/a")).click();

			driver.switchTo().window("");
		    driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id17:j_id55:j_id59:0:j_id60:j_id61:j_id96:j_id97:j_id103']/img")).click();
			// ERROR: Caught exception [ERROR: Unsupported command [waitForPopUp | Popup | 30000]]
			driver.switchTo().window("Popup");
		    driver.findElement(By.xpath("//td[@id='listComponent:a0VG000000ImUSEMA3:name']/a")).click();
			driver.switchTo().window("");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id17:j_id55:j_id372']/input[2]")).click();
			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id17:j_id55:j_id372']/input[3]")).click();
			driver.findElement(By.xpath("//td[@id='topButtonRow']/input[11]")).click();
			driver.findElement(By.xpath("//td[@id='page:previewform:j_id1:j_id2:quotePreviewBlock:j_id80']/input[2]")).click();
			driver.findElement(By.xpath("//span[@id='page:previewform:j_id1:j_id2:j_id34:j_id35:j_id51']/span")).click();
			
			System.out.println("Counter end= "+i+" , time = "+Calendar.getInstance().getTime() );
		}

	}

}
