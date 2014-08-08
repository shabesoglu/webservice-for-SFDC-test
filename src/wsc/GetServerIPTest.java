package wsc;

import java.io.BufferedReader;
import com.sforce.soap.WebServiceClass.SoapConnection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.metadata.ApexClass;
import com.sforce.soap.metadata.CustomField;
import com.sforce.soap.metadata.FieldType;
import com.sforce.soap.metadata.FileProperties;
import com.sforce.soap.metadata.ListMetadataQuery;
import com.sforce.soap.metadata.Metadata;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class GetServerIPTest {


	static String USERNAME = "zforceactivation+3@3demo.com";
	static String PASSWORD = "Agile#1234";
	static String TOKEN = "0A6BPRVoGNMvUqNxj2BvWpQjC";
	static PartnerConnection partnerConnection;
	static com.sforce.soap.metadata.MetadataConnection metadataConnection;
	static SoapConnection customWebServiceConnection;
	static ConnectorConfig config;


	
	@BeforeClass
	public static void init(){
		config = new ConnectorConfig();
		config.setUsername(USERNAME);
		config.setPassword(PASSWORD+TOKEN);

		try {
			//Establish all the connections, and set the session headers.
			com.sforce.soap.enterprise.Connector.newConnection(config);
			metadataConnection = com.sforce.soap.metadata.Connector.newConnection("","");
			//customWebServiceConnection = com.sforce.soap.WebServiceClass.Connector.newConnection("","");
			//partnerConnection = com.sforce.soap.partner.Connector.newConnection("","");
			metadataConnection.setSessionHeader(config.getSessionId());
			//customWebServiceConnection.setSessionHeader(config.getSessionId());
			//partnerConnection.setSessionHeader(config.getSessionId());
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
	}
	 



	@Test
	public void testGetWANIP() {
		try {
			System.out.println(getWANIP());
		} catch (IOException e) {
			e.printStackTrace();
		}	
		System.out.println(getAllCustomFieldsList(metadataConnection));

		setZuoraQuotesConnectionSettings("aaaa","bbbb","cccc");

	}




	/**
	 * This method returns the WAN IP address of this server using Amazon WS in order Salesforce Org to access this server.
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String getWANIP() throws IOException {
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(
					whatismyip.openStream()));
			String ip = in.readLine();
			return ip;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}




	/**
	 * Creates a custom field for the Salesforce object.
	 * 
	 * @param objectType (Ex: "Account", "zqu__Quote__c")
	 * @param customFieldName (Ex: "custom_field_for_account__c")
	 * @param customFieldType (Ex: FieldType.Picklist, FieldType.Text)
	 * @throws ConnectionException
	 */
	public void createCustomField(String objectType, String customFieldName, FieldType customFieldType) throws ConnectionException{
		com.sforce.soap.metadata.CustomField cf = new CustomField();
		//cf.setCustomDataType("text");
		cf.setLength(10);
		cf.setType(customFieldType);
		cf.setFullName(objectType+"."+customFieldName);
		cf.setLabel("MyAcctCustomField__c");

		Metadata[] md = new Metadata[1];
		md[0] = ((Metadata)cf);
		metadataConnection.create(md);
	}




	public void deleteCustomField(String objectType, String customFieldName, FieldType customFieldType) throws ConnectionException{
		com.sforce.soap.metadata.CustomField cf = new CustomField();
		//cf.setCustomDataType("text");
		cf.setLength(10);
		cf.setType(customFieldType);
		cf.setFullName(objectType+"."+customFieldName);
		cf.setLabel("MyAcctCustomField__c");

		Metadata[] md = new Metadata[1];
		md[0] = ((Metadata)cf);
		metadataConnection.delete(md);
	}




	public ArrayList<String> getAllCustomFieldsList(MetadataConnection metadataConnection) {
		ArrayList<String> metadataList = new ArrayList<String>();
		try {
			ListMetadataQuery query = new ListMetadataQuery();
			query.setType("CustomField");
			double asOfVersion = 23.0;
			// Assuming that the SOAP binding has already been established.
			FileProperties[] lmr = metadataConnection.listMetadata(
					new ListMetadataQuery[] {query}, asOfVersion);
			if (lmr != null) {
				for (FileProperties n : lmr) {
					metadataList.add(n.getFullName());
				}
			}
			return metadataList;
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		return null;
	}




	public ArrayList<String> getAllCustomObjectsList(MetadataConnection metadataConnection) {
		ArrayList<String> metadataList = new ArrayList<String>();
		try {
			ListMetadataQuery query = new ListMetadataQuery();
			query.setType("CustomObject");
			double asOfVersion = 23.0;
			// Assuming that the SOAP binding has already been established.
			FileProperties[] lmr = metadataConnection.listMetadata(
					new ListMetadataQuery[] {query}, asOfVersion);
			if (lmr != null) {
				for (FileProperties n : lmr) {
					metadataList.add(n.getFullName());
				}
			}
			return metadataList;
		} catch (ConnectionException ce) {
			ce.printStackTrace();
		}
		return null;
	}



	/**
	 * This method creates a new subscription quote for a new billing account and sends it to Z-Billing.
	 */
	public void sendQuoteToZBilling(){
		try {
			customWebServiceConnection.testSendQuoteToZbilling();
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	
	/**
	 * This method enters the credentials for Zuora API into the Salesforce Org. This is done by Selenium for now because there is no API provided to do this.
	 * 
	 * @param apiURL
	 * @param apiUsername
	 * @param apiPassword
	 */
	public void setZuoraQuotesConnectionSettings(String apiURL, String apiUsername, String apiPassword){
		WebDriver driver = new FirefoxDriver();
		String baseUrl = "https://login.salesforce.com";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "/");
		driver.findElement(By.xpath("//div[@id='usrn']/div/input")).click();
		driver.findElement(By.xpath("//div[@id='usrn']/div/input")).clear();
		driver.findElement(By.xpath("//div[@id='usrn']/div/input")).sendKeys(USERNAME);
		driver.findElement(By.xpath("//div[@id='pswd']/div/input")).clear();
		driver.findElement(By.xpath("//div[@id='pswd']/div/input")).sendKeys(PASSWORD);
		driver.findElement(By.xpath("//div[@id='loginwidget']/form/div[4]/button")).click();

		driver.get(baseUrl + "/apex/ConnectionSettings");
		driver.findElement(By.xpath("//div[@id='userNavButton']/span")).click();
		driver.findElement(By.xpath("//div[@id='userNav-menuItems']/a")).click();
		driver.findElement(By.xpath("//a[@id='Security_icon']/img")).click();
		driver.findElement(By.xpath("//div[@id='Security_child']/div[14]/a")).click();
		driver.findElement(By.xpath("//td[@id='bodyCell']/div[5]/div/div/div/input")).click();
		driver.findElement(By.xpath("//div[@id='ep']/div[2]/div[2]/table/tbody/tr/td[2]/div/input")).clear();
		driver.findElement(By.xpath("//div[@id='ep']/div[2]/div[2]/table/tbody/tr/td[2]/div/input")).sendKeys(apiURL.replace("http://", "").replace(".com", ""));
		driver.findElement(By.xpath("//div[@id='ep']/div[2]/div[2]/table/tbody/tr[2]/td[2]/div/input")).clear();
		driver.findElement(By.xpath("//div[@id='ep']/div[2]/div[2]/table/tbody/tr[2]/td[2]/div/input")).sendKeys(apiURL);
		driver.findElement(By.xpath("//td[@id='bottomButtonRow']/input")).click();

		driver.findElement(By.xpath("//li[@id='01ri0000000GTP6_Tab']/a")).click();
		driver.findElement(By.xpath("//span[@id='j_id0:j_id5']/a")).click();
		driver.findElement(By.xpath("//td[@id='thepage:theform:zclp:j_id28']/input")).click();
		driver.findElement(By.xpath("//div[@id='thepage:theform:zclp:edit_consection']/div/table/tbody/tr/td/div/input")).clear();
		driver.findElement(By.xpath("//div[@id='thepage:theform:zclp:edit_consection']/div/table/tbody/tr/td/div/input")).sendKeys(apiURL);
		driver.findElement(By.xpath("//div[@id='thepage:theform:zclp:edit_consection']/div/table/tbody/tr[2]/td/div/input")).clear();
		driver.findElement(By.xpath("//div[@id='thepage:theform:zclp:edit_consection']/div/table/tbody/tr[2]/td/div/input")).sendKeys(apiUsername);
		driver.findElement(By.xpath("//span[@id='thepage:theform:zclp:edit_consection:j_id38:j_id40']/div/input")).clear();
		driver.findElement(By.xpath("//span[@id='thepage:theform:zclp:edit_consection:j_id38:j_id40']/div/input")).sendKeys(apiPassword);
		driver.findElement(By.xpath("//td[@id='thepage:theform:zclp:j_id28:bottom']/input")).click();
	}





}
