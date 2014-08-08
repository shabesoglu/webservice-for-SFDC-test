package wsc;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.DeleteResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.Error;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;


public class Main {

	private static int counter = 30;
	private static String baseUrl;
	//private static String[][] orgCredentials = new String[3][3];

	// Org credentials -----------------------------------------------------------------------
	/*
	// Version 6.0.3 (Beta 1) , and sahin.habesoglu@zuora.com
	static final String USERNAME = "zforceactivation+3@3demo.com";
	static final String PASSWORD = "Agile#1234";
	static final String TOKEN = "0A6BPRVoGNMvUqNxj2BvWpQjC";

	// Version 5.91.2 , and sahin.habesoglu@zuora.com
	static final String USERNAME = "sahin.habesoglu+70@zuora.com";
	static final String PASSWORD = "metone1983";
	static final String TOKEN = "purY9JKmhjWuvBSvKyi2FOBOV";


	 */

	// Version 5.90.6 , and sahin.habesoglu+u2@zuora.com
	static  String USERNAME;// = "sahin.habesoglu@zuora.com";
	static  String PASSWORD;// = "Agile#12345";
	static  String TOKEN;// = "UPRyVKiUf2ZV1PQrohNcnVLk4";
	// Org credentials -----------------------------------------------------------------------

	private static String[][] orgCredentials = new String [][]{       { "zforceactivation+3@3demo.com", "Agile#1234" , "0A6BPRVoGNMvUqNxj2BvWpQjC" },// Version latest NextGen , and sahin.habesoglu@zuora.com
		{ "sahin.habesoglu@zuora.com", "Agile#12345" , "UPRyVKiUf2ZV1PQrohNcnVLk4" },// Version 5.100.7 , and sahin.habesoglu+test2@zuora.com
		{ "sahin.habesoglu+70@zuora.com", "metone1983" , "purY9JKmhjWuvBSvKyi2FOBOV" },// Version 5.91.2 , and sahin.habesoglu@zuora.com
		{ "zforceactivation+4@62demo.com", "Agile#1234" , "2YrmlSDfzikzhdAae1XlX43KQ" },// Version 5.90.6 , and sahin.habesoglu+u2@zuora.com //201 quotes sent to billing
		{ "zforceactivation+4@87demo.com" , "Agile#1234" , "I4OCwCndTeKZ5IYiBPD45KHe" },// Version 5.80.1 , and sahin.habesoglu+test1@zuora.com //on December 9, 144 new subscription quotes sent to billing out of 202 new subscription quotes created
		{ "zforceactivation+4@52demo.com" , "Agile#1234" , "VRDQpHRSTyL9EM9gViMgwCjt" }// Version 5.72.1 , and sahin.habesoglu+test1user1@zuora.com //on December 9, 143 new subscription quotes sent to z billing out of 200 new subscription quotes created, and 100 amendment quotes created
	};


	static PartnerConnection connection;
	static com.sforce.soap.TestWebServiceCall.SoapConnection connection2;
	static ConnectorConfig config = new ConnectorConfig();
	static com.sforce.soap.TestWebServiceCall.Connector connector = new com.sforce.soap.TestWebServiceCall.Connector();
	static com.sforce.soap.metadata.Connector metadataConnector = new com.sforce.soap.metadata.Connector();
	static SObject[] contacts;
	static SObject[] opportunities;
	static SObject[] quotes;
	static SObject[] amendmentQuotes;
	static SObject[] accounts;
	final static int objectCount = 50	;

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws ConnectionException, InterruptedException {

		USERNAME = "zforceactivation+5@93demo.com";
		PASSWORD = "Agile#12345";
		TOKEN = "KRoz10qbTB1ut9oOKzVXDDJT";
		//System.out.println(orgCredentials[0][0]+" "+orgCredentials[0][1]+" "+orgCredentials[0][2]);
		//driver = new FirefoxDriver();
		baseUrl = "https://login.salesforce.com";
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);


		//config.setUsername(USERNAME);
		//config.setPassword(PASSWORD+TOKEN);
		//config.setServiceEndpoint("https://na15.salesforce.com/services/Soap/u/23.0/00Di0000000gJOO");
		//config.setTraceMessage(true);

		//try {

		//connection = Connector.newConnection(config);
		//connection2 = connector.newConnection(config);

		// display some current settings
		System.out.println("Auth EndPoint: "+config.getAuthEndpoint());
		System.out.println("Service EndPoint: "+config.getServiceEndpoint());
		System.out.println("Username: "+config.getUsername());
		System.out.println("SessionId: "+config.getSessionId());


		//contacts = createContacts();
		//accounts = createAccounts();
		//opportunities = createOpportunities();
		//createQuotes3();
		//addProductsToQuote();

		//createAmendmentQuotes2();

	}


	// create 5 test Accounts
	private static SObject[] createContacts() {

		System.out.println("Creating 5 new test Contacts...");
		SObject[] records = new SObject[objectCount];
		//com.sforce.soap.TestWebServiceCall.Account a;

		try {

			// create 5 test accounts

			for (int i=0;i<objectCount;i++) {
				SObject so = new SObject();
				so.setType("Contact");
				so.setField("FirstName", "Test Contact First Name ");
				so.setField("LastName", "Test Contact LName "+i);
				so.setField("Email", "g@g.com");
				records[i] = so;

			}


			// create the records in Salesforce.com
			SaveResult[] saveResults = connection.create(records);

			// check the returned results for any errors
			for (int i=0; i< saveResults.length; i++) {
				if (saveResults[i].isSuccess()) {
					records[i].setId(saveResults[i].getId());
					System.out.println(i+". Successfully created Contact - Id: " + saveResults[i].getId());
				} else {
					Error[] errors = saveResults[i].getErrors();
					for (int j=0; j< errors.length; j++) {
						System.out.println("ERROR creating record: " + errors[j].getMessage());
					}
				}    
			}

		} catch (Exception e) {
			e.printStackTrace();
		}    

		return records;
	}


	// create 5 test Accounts
	private static SObject[] createOpportunities() {

		System.out.println("Creating 5 new test Opportunities...");
		SObject[] records = new SObject[objectCount];
		//com.sforce.soap.TestWebServiceCall.Account a;

		try {

			// create 5 test accounts

			for (int i=0;i<objectCount;i++) {
				SObject so = new SObject();
				so.setType("Opportunity");
				so.setField("Name", "Test Opportunity "+i);
				so.setField("StageName","Qualification");
				so.setField("CloseDate",Calendar.getInstance().getTime());
				SObject ac = connection.query("SELECT Id FROM Account WHERE Id = '"+ accounts[0].getId()+"'").getRecords()[0];
				so.setField("AccountId",ac.getField("Id"));
				records[i] = so;

			}


			// create the records in Salesforce.com
			SaveResult[] saveResults = connection.create(records);

			// check the returned results for any errors
			for (int i=0; i< saveResults.length; i++) {
				if (saveResults[i].isSuccess()) {
					records[i].setId(saveResults[i].getId());
					System.out.println(i+". Successfully created Opportunity - Id: " + saveResults[i].getId());
				} else {
					Error[] errors = saveResults[i].getErrors();
					for (int j=0; j< errors.length; j++) {
						System.out.println("ERROR creating record: " + errors[j].getMessage());
					}
				}    
			}

		} catch (Exception e) {
			e.printStackTrace();
		}    

		return records;
	}


	// create 5 test Accounts
	private static SObject[] createAccounts() {

		System.out.println("Creating 5 new test Accounts...");
		SObject[] records = new SObject[objectCount];
		//com.sforce.soap.TestWebServiceCall.Account a;

		try {

			// create 5 test accounts

			for (int i=0;i<objectCount;i++) {
				SObject so = new SObject();
				so.setType("Account");
				so.setField("Name", "Test Account "+i);
				records[i] = so;

			}


			// create the records in Salesforce.com
			SaveResult[] saveResults = connection.create(records);

			// check the returned results for any errors
			for (int i=0; i< saveResults.length; i++) {
				if (saveResults[i].isSuccess()) {
					records[i].setId(saveResults[i].getId());
					System.out.println(i+". Successfully created Account - Id: " + saveResults[i].getId());
				} else {
					Error[] errors = saveResults[i].getErrors();
					for (int j=0; j< errors.length; j++) {
						System.out.println("ERROR creating record: " + errors[j].getMessage());
					}
				}    
			}

		} catch (Exception e) {
			e.printStackTrace();
		}    

		return records;
	}


	// create 5 test Quotes
	@SuppressWarnings("deprecation")
	private static SObject[] createQuotes() throws ConnectionException {

		System.out.println("Creating 5 new test Quotes...");
		SObject[] records = new SObject[objectCount];


		// create 5 test accounts
		for (int i=0;i<objectCount;i++) {
			SObject so = new SObject();
			so.setType("zqu__Quote__c");
			so.setField("Name", "Test Quote "+i);
			so.setField("zqu__Currency__c","USD");
			so.setField("zqu__AutoRenew__c",Boolean.FALSE);
			SObject op = connection.query("SELECT Id FROM Opportunity WHERE Id = '"+ opportunities[i].getId()+"'").getRecords()[0];
			so.setField("zqu__Opportunity__c",op.getField("Id"));
			SObject cont = connection.query("SELECT Id FROM Contact WHERE Id = '"+ contacts[i].getId()+"'").getRecords()[0];
			so.setField("zqu__BillToContact__c",cont.getField("Id"));
			so.setField("zqu__SoldToContact__c",cont.getField("Id"));
			so.setField("zqu__InitialTerm__c","12");
			so.setField("zqu__RenewalTerm__c","12");
			so.setField("zqu__PaymentMethod__c","Credit Card");
			so.setField("zqu__ValidUntil__c",Calendar.getInstance().getTime());
			so.setField("zqu__StartDate__c",Calendar.getInstance().getTime());
			so.setField("zqu__SubscriptionTermStartDate__c",Calendar.getInstance().getTime());
			Date future = Calendar.getInstance().getTime();
			future.setYear(Calendar.getInstance().get(Calendar.YEAR) + 2);
			so.setField("zqu__SubscriptionTermEndDate__c",future);
			so.setField("zqu__SubscriptionType__c","New Subscription");
			so.setField("zqu__BillingMethod__c","Both");
			so.setField("zqu__Subscription_Term_Type__c","Termed");
			so.setField("zqu__PaymentTerm__c","Due Upon Receipt");
			//SObject billingAccount = connection.query("select Id, Zuora__Account__c, Zuora__Zuora_Id__c from Zuora__CustomerAccount__c where Id='001G0000019o9sO'").getRecords()[0];
			//so.setField("zqu__ZuoraAccountID__c",billingAccount.getField("Zuora__Zuora_Id__c"));
			so.setField("zqu__ZuoraAccountID__c","new");	
			records[i] = so;
		}
		try {
			// create the records in Salesforce.com
			SaveResult[] saveResults = connection.create(records);

			// check the returned results for any errors
			for (int i=0; i< saveResults.length; i++) {
				if (saveResults[i].isSuccess()) {
					records[i].setId(saveResults[i].getId());
					System.out.println(i+". Successfully created Quote - Id: " + saveResults[i].getId());
				} else {
					Error[] errors = saveResults[i].getErrors();
					for (int j=0; j< errors.length; j++) {
						System.out.println("ERROR creating record: " + errors[j].getMessage());
					}
				}    
			}

		} catch (Exception e) {
			e.printStackTrace();
		}   
		return records;
	}


	// create 5 test Quotes
	@SuppressWarnings("deprecation")
	private static SObject[] createAmendmentQuotes() throws ConnectionException {

		System.out.println("Creating 5 new test Amendment Quotes...");
		SObject[] records = new SObject[objectCount];
		SObject[] quotes = connection.query("select Id from zqu__Quote__c limit " + String.valueOf(objectCount) ).getRecords();

		for (int i=0;i<objectCount;i++) {
			SObject so = new SObject();
			so.setType("zqu__QuoteAmendment__c");
			so.setField("Name", "Test Amendment Quote "+i);
			so.setField("zqu__Quote__c",quotes[i].getField("Id"));

			records[i] = so;
		}
		try {
			// create the records in Salesforce.com
			SaveResult[] saveResults = connection.create(records);

			// check the returned results for any errors
			for (int i=0; i< saveResults.length; i++) {
				if (saveResults[i].isSuccess()) {
					records[i].setId(saveResults[i].getId());
					System.out.println(i+". Successfully created Quote - Id: " + saveResults[i].getId());
				} else {
					Error[] errors = saveResults[i].getErrors();
					for (int j=0; j< errors.length; j++) {
						System.out.println("ERROR creating record: " + errors[j].getMessage());
					}
				}    
			}

		} catch (Exception e) {
			e.printStackTrace();
		}   
		return records;
	}

/*
	@SuppressWarnings("deprecation")
	private static void createAmendmentQuotes2() throws ConnectionException {

		driver.get(baseUrl);
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(USERNAME);
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(PASSWORD);
		driver.findElement(By.id("Login")).click();
		SObject[] quotes = connection.query("select zqu__Opportunity__c from zqu__Quote__c where zqu__Status__c = 'Sent to Z-Billing' and CreatedDate = TODAY limit " + String.valueOf(objectCount) ).getRecords();

		for (int i=0;i<objectCount;i++) {
			driver.get(baseUrl + quotes[i].getField("zqu__Opportunity__c").toString());
			driver.findElement(By.name("zqu__quotenew")).click();
			//driver.findElement(By.xpath("//span[2]/input")).click();
			driver.findElement(By.xpath("//span[@id='j_id0:j_id2:j_id16:j_id17:j_id32:j_id33:j_id58:j_id64:0:j_id66']/input")).click();
			driver.findElement(By.xpath("//table[@id='j_id0:j_id2:j_id16:j_id17:j_id32:j_id33:subscriptionSection:subTypeRadio']/tbody/tr[2]/td/input")).click();
			driver.findElement(By.name("j_id0:j_id2:j_id16:j_id17:j_id32:j_id33:j_id93:j_id95")).click();
			driver.findElement(By.xpath("//td[2]/table/tbody/tr/td/span/input")).click();
			driver.findElement(By.xpath("//div[3]/table/tbody/tr/td[2]/input")).click();
			driver.findElement(By.name("zqu__amendment_send_to_z_billing")).click();
			driver.findElement(By.xpath("//span[2]/input")).click();
			driver.findElement(By.xpath("//td[2]/input")).click();
		}

	}
*/

	// updates the 5 newly created Accounts
	private static void updateAccounts() {

		System.out.println("Update the 5 new test Accounts...");
		SObject[] records = new SObject[5];

		try {

			QueryResult queryResults = connection.query("SELECT Id, Name FROM Account ORDER BY " +
					"CreatedDate DESC LIMIT 5");
			if (queryResults.getSize() > 0) {
				for (int i=0;i<queryResults.getRecords().length;i++) {
					SObject so = (SObject)queryResults.getRecords()[i];
					System.out.println("Updating Id: " + so.getId() + " - Name: "+so.getField("Name"));
					// create an sobject and only send fields to update
					SObject soUpdate = new SObject();
					soUpdate.setType("Account");
					soUpdate.setId(so.getId());
					soUpdate.setField("Name", so.getField("Name")+" -- UPDATED");
					records[i] = soUpdate;
					/*
    	    com.sforce.soap.TestWebServiceCall.Account a = new com.sforce.soap.TestWebServiceCall.Account();
            a.setName((String)soUpdate.getField("Name"));
            a.setId(soUpdate.getId());
            System.out.println("endpointttt :"+connection2.getConfig().getAuthEndpoint());

            connection2.makeContact(so.getName().toString(), a);
					 */
				}
			}


			// update the records in Salesforce.com
			/*SaveResult[] saveResults = connection.update(records);

      // check the returned results for any errors
      for (int i=0; i< saveResults.length; i++) {
        if (saveResults[i].isSuccess()) {
          System.out.println(i+". Successfully updated record - Id: " + saveResults[i].getId());
        } else {
          Error[] errors = saveResults[i].getErrors();
          for (int j=0; j< errors.length; j++) {
            System.out.println("ERROR updating record: " + errors[j].getMessage());
          }
        }    
      }*/

		} catch (Exception e) {
			e.printStackTrace();
		}    

	}

	// delete the 5 newly created Account
	private static void deleteAccounts() {

		System.out.println("Deleting the 5 new test Accounts...");
		String[] ids = new String[5];

		try {

			QueryResult queryResults = connection.query("SELECT Id, Name FROM Account ORDER BY " +
					"CreatedDate DESC LIMIT 5");
			if (queryResults.getSize() > 0) {
				for (int i=0;i<queryResults.getRecords().length;i++) {
					SObject so = (SObject)queryResults.getRecords()[i];
					ids[i] = so.getId();
					System.out.println("Deleting Id: " + so.getId() + " - Name: "+so.getField("Name"));
				}
			}


			// delete the records in Salesforce.com by passing an array of Ids
			DeleteResult[] deleteResults = connection.delete(ids);

			// check the results for any errors
			for (int i=0; i< deleteResults.length; i++) {
				if (deleteResults[i].isSuccess()) {
					System.out.println(i+". Successfully deleted record - Id: " + deleteResults[i].getId());
				} else {
					Error[] errors = deleteResults[i].getErrors();
					for (int j=0; j< errors.length; j++) {
						System.out.println("ERROR deleting record: " + errors[j].getMessage());
					}
				}    
			}

		} catch (Exception e) {
			e.printStackTrace();
		}    

	}
/*

	private static void addProductsToQuote() throws InterruptedException, ConnectionException{

		for(int i=0; i<2; i++){

			String url = driver.getCurrentUrl();

			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id17:j_id55:j_id59:0:j_id60:j_id61:j_id76:j_id77:j_id83']/img")).click();
		
			driver.switchTo().window("Popup");
			//driver.switchTo().window(newAdwinID);
			//System.out.println(driver.getTitle());

			driver.findElement(By.xpath("//td[@id='listComponent:a0hi0000002N5D8AAK:name']/a")).click();
			//Thread.sleep(3000);
	
			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id17:j_id55:j_id59:0:j_id60:j_id61:j_id96:j_id97:j_id103']/img")).click();


			driver.findElement(By.xpath("//td[@id='listComponent:a0Ai000000CbPtBEAV:name']/a")).click();
			// ERROR: Caught exception [ERROR: Unsupported command [selectWindow | null | ]]
			driver.switchTo().window("");
			//driver.close();


			Thread.sleep(2000);

			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id17:j_id55:j_id372']/input[2]")).click();
			driver.findElement(By.xpath("//td[@id='topButtonRow']/input[11]")).click();
			driver.findElement(By.xpath("//td[@id='page:previewform:j_id1:j_id2:quotePreviewBlock:j_id80']/input[2]")).click();
			driver.findElement(By.id("page:previewform:j_id1:j_id2:j_id34:j_id35:j_id54")).click();
		}
	}


	// Add product for quotes version 5.91.2
	private static void addProductsToQuoteBeforeNextGen__5_91_2() throws InterruptedException, ConnectionException{
		driver.get(baseUrl);
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(USERNAME);
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(PASSWORD);
		driver.findElement(By.id("Login")).click();
		for(int i=0; i<quotes.length; i++){
			driver.get("https://na15.salesforce.com/" + quotes[i].getId());
			driver.findElement(By.name("zqu__productselect")).click();
			driver.findElement(By.xpath("//span[2]/span/img")).click();
			driver.findElement(By.xpath("//div[3]/table/tbody/tr/td[2]/table/tbody/tr/td/span/input")).click();
			driver.findElement(By.name("zqu__submittozuora")).click();
			driver.findElement(By.xpath("//span[2]/input")).click();
			driver.findElement(By.xpath("//td[2]/input")).click();
		}
	}


	@SuppressWarnings("deprecation")
	private static void createQuotes2() throws InterruptedException {
		driver.get(baseUrl + "/");
		driver.findElement(By.id("username")).click();
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("zforceactivation+p2@70demo.com");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("Agile#1234");
		driver.findElement(By.id("Login")).click();
		driver.get(baseUrl + "/006i0000004Kekl");
		driver.findElement(By.xpath("//td[@id='topButtonRow']/input[6]")).click();
		driver.findElement(By.xpath("//table[@id='table-billingAccounts']/tbody/tr/td/input")).click();
		driver.findElement(By.xpath("//table[@id='j_id0:j_id2:j_id16:j_id17:j_id41:j_id42:subscriptionSection:subTypeRadio']/tbody/tr/td/input")).click();
		driver.findElement(By.xpath("//table[@id='table-billingAccounts']/tbody/tr[47]/td/input")).click();
		driver.findElement(By.xpath("//td[@id='j_id0:j_id2:j_id16:j_id17:j_id41:j_id42:j_id91:bottom']/input[2]")).click();
		driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:0:j_id59']/div[2]/table/tbody/tr[2]/td[2]/div/span/span/a")).click();
		driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:0:j_id59']/div[2]/table/tbody/tr[2]/td[2]/div/span/input")).clear();
		driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:0:j_id59']/div[2]/table/tbody/tr[2]/td[2]/div/span/input")).sendKeys("2/18/2014");
		new Select(driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:1:j_id59']/div[2]/table/tbody/tr[2]/td/div/select"))).selectByVisibleText("Credit Card");
		driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:2:j_id59']/div[2]/table/tbody/tr/td/div/span/span/a")).click();
		driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:2:j_id59']/div[2]/table/tbody/tr/td/div/span/input")).clear();
		driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:2:j_id59']/div[2]/table/tbody/tr/td/div/span/input")).sendKeys("2/18/2014");
		driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:2:j_id59:j_id60:1:j_id171']/div/table/tbody/tr/td/div/input")).clear();
		driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:2:j_id59:j_id60:1:j_id171']/div/table/tbody/tr/td/div/input")).sendKeys("6");
		driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:2:j_id59:j_id60:1:j_id171']/div/table/tbody/tr[2]/td/div/input")).clear();
		driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:j_id58:2:j_id59:j_id60:1:j_id171']/div/table/tbody/tr[2]/td/div/input")).sendKeys("6");
		driver.findElement(By.xpath("//td[@id='j_id0:j_id1:j_id15:j_id16:j_id18:propertyComponent:j_id21:propertyPageBlock:propertyPageBlockButtons:bottom']/input[2]")).click();


		driver.findElement(By.name("zqu__productselect")).click();
		driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id17:j_id46:j_id50:0:j_id51:j_id52:j_id67:j_id68:j_id74']/img")).click();
		driver.switchTo().window("Popup");
		driver.findElement(By.xpath("//a")).click();
		driver.switchTo().window("");
		driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id17:j_id46:j_id50:0:j_id51:j_id52:j_id87:j_id88:j_id94']/img")).click();
		driver.switchTo().window("Popup");
		driver.findElement(By.xpath("//a")).click();
		driver.switchTo().window("");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id17:j_id46:j_id363']/input[2]")).click();
		driver.findElement(By.name("zqu__submittozuora")).click();
		driver.findElement(By.xpath("//td[@id='page:previewform:j_id1:j_id2:quotePreviewBlock:j_id80']/input[2]")).click();
		driver.findElement(By.xpath("//span[@id='page:previewform:j_id1:j_id2:j_id34:j_id35:j_id51']/span")).click();



	}



	private static void createQuotes3() throws InterruptedException {

		driver.get(baseUrl + "/");
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys("zforceactivation+p2@70demo.com");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("Agile#1234");
		driver.findElement(By.id("Login")).click();
		//driver.findElement(By.xpath("//li[@id='Opportunity_Tab']/a")).click();
		for(int i=0; i<counter; i++){
			driver.get(baseUrl + "/006i0000004Kekl");
			//driver.findElement(By.xpath("//td[@id='bodyCell']/div[3]/div/div/div[2]/table/tbody/tr[2]/th/a")).click();
			driver.findElement(By.xpath("//td[@id='topButtonRow']/input[6]")).click();
			driver.findElement(By.xpath("//table[@id='table-billingAccounts']/tbody/tr/td/input")).click();
			driver.findElement(By.xpath("//table[@id='j_id0:j_id2:j_id16:j_id17:j_id41:j_id42:subscriptionSection:subTypeRadio']/tbody/tr/td/input")).click();
			driver.findElement(By.xpath("//table[@id='j_id0:j_id2:j_id16:j_id17:j_id41:j_id42:j_id83:accTypeRadio']/tbody/tr/td/input")).click();
			driver.findElement(By.xpath("//table[@id='j_id0:j_id2:j_id16:j_id17:j_id41:j_id42:subscriptionSection:newSubTypeRadio']/tbody/tr/td/input")).click();
			driver.findElement(By.xpath("//td[@id='j_id0:j_id2:j_id16:j_id17:j_id41:j_id42:j_id91:bottom']/input[2]")).click();
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
			driver.findElement(By.xpath("//td[@id='listComponent:a0hi0000002N5DOAA0:name']/a")).click();
			driver.switchTo().window("");
			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id17:j_id55:j_id59:0:j_id60:j_id61:j_id96:j_id97:j_id103']/img")).click();
			// ERROR: Caught exception [ERROR: Unsupported command [waitForPopUp | Popup | 30000]]
			driver.switchTo().window("Popup");
			driver.findElement(By.xpath("//td[@id='listComponent:a0Ai000000CbPtOEAV:name']/a")).click();
			driver.switchTo().window("");
			Thread.sleep(2000);
			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id17:j_id55:j_id372']/input[2]")).click();
			driver.findElement(By.xpath("//div[@id='j_id0:j_id1:j_id15:j_id16:j_id17:j_id55:j_id372']/input[3]")).click();
			driver.findElement(By.xpath("//td[@id='topButtonRow']/input[11]")).click();
			driver.findElement(By.xpath("//td[@id='page:previewform:j_id1:j_id2:quotePreviewBlock:j_id80']/input[2]")).click();
			driver.findElement(By.xpath("//span[@id='page:previewform:j_id1:j_id2:j_id34:j_id35:j_id51']/span")).click();
		}

	}
*/
}
