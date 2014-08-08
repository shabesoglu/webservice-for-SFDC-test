package wsc;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import com.sforce.soap.SuperClass.Connector;
import com.sforce.soap.apex.SoapConnection;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.metadata.CustomField;
import com.sforce.soap.metadata.Metadata;
import com.sforce.soap.metadata.MetadataConnection;
import com.sforce.soap.metadata.ReadResult;
import com.sforce.soap.partner.Error;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.SaveResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class CreateCustomFieldUsingMetadataAPITest {
	static  String USERNAME;// = "sahin.habesoglu@zuora.com";
	static  String PASSWORD;// = "Agile#12345";
	static  String TOKEN;// = "UPRyVKiUf2ZV1PQrohNcnVLk4";
	static PartnerConnection partnerConnection;
	final static int objectCount = 200;
	static SObject[] contacts;
	static SObject[] opportunities;
	static SObject[] quotes;
	static SObject[] amendmentQuotes;
	static SObject[] accounts;

	public void test1() throws Exception{
		System.out.println(getWANIP());	
		//createCustomField();
	}



	public static String getWANIP() throws Exception {
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


	public static void createCustomField(com.sforce.soap.metadata.MetadataConnection conn) throws ConnectionException{
		//ConnectorConfig config = new ConnectorConfig();
		//config.setUsername(USERNAME);
		//config.setPassword(PASSWORD+TOKEN);

		//EnterpriseConnection enterpriseConnection = com.sforce.soap.enterprise.Connector.newConnection(config);
		//enterpriseConnection.login(config.getUsername(), config.getPassword());

		//SoapConnection soapConn = com.sforce.soap.apex.Connector.newConnection("","");
		//soapConn.setSessionHeader(config.getSessionId());

		//com.sforce.soap.metadata.MetadataConnection conn = com.sforce.soap.metadata.Connector.newConnection("","");
		//conn.setSessionHeader(config.getSessionId());

		com.sforce.soap.metadata.CustomField cf = new CustomField();
		//cf.setCustomDataType("text");
		for(int i=0; i<=300; i++){
			cf.setLength(10);
			cf.setType(com.sforce.soap.metadata.FieldType.Text);
			cf.setFullName("zqu__Quote__c.MyQuoteCustomField"+i+"__c");
			cf.setLabel("MyQuoteCustomField"+i+"__c");

			Metadata[] md = new Metadata[1];
			md[0] = ((Metadata)cf);
			conn.create(md);
		}
		
		/*
		for(int i=300; i<=600; i++){
			cf.setLength(10);
			cf.setType(com.sforce.soap.metadata.FieldType.Text);
			cf.setFullName("Opportunity.MyOpportunityCustomField"+i+"__c");
			cf.setLabel("MyOpportunityCustomField"+i+"__c");

			Metadata[] md = new Metadata[1];
			md[0] = ((Metadata)cf);
			conn.create(md);
		}
		*/
		//conn.create(metadata)
	}
	
	
	public static void deleteCustomFields(com.sforce.soap.metadata.MetadataConnection conn) throws ConnectionException{
		com.sforce.soap.metadata.CustomField cf = new CustomField();
		String[] fullNames = new String[10];
		int j = 0;
		
		for(int i=300; i<310; i++){
			fullNames[j] = "Opportunity.MyOpportunityCustomField"+i+"__c";
			j++;
		}
		ReadResult rr = conn.readMetadata(com.sforce.soap.metadata.FieldType.Text.name(), fullNames);//partnerConnection.query("SELECT Id FROM Opportunity").getRecords();
		System.out.println(rr.toString());
		
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
			SObject op = partnerConnection.query("SELECT Id FROM Opportunity WHERE Id = '"+ opportunities[i].getId()+"'").getRecords()[0];
			so.setField("zqu__Opportunity__c",op.getField("Id"));
			SObject cont = partnerConnection.query("SELECT Id FROM Contact WHERE Id = '"+ contacts[i].getId()+"'").getRecords()[0];
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
			SaveResult[] saveResults = partnerConnection.create(records);

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
			SaveResult[] saveResults = partnerConnection.create(records);

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
				SObject ac = partnerConnection.query("SELECT Id FROM Account WHERE Id = '"+ accounts[0].getId()+"'").getRecords()[0];
				so.setField("AccountId",ac.getField("Id"));
				records[i] = so;

			}


			// create the records in Salesforce.com
			SaveResult[] saveResults = partnerConnection.create(records);

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
			SaveResult[] saveResults = partnerConnection.create(records);

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




	public static void main(String[] args) throws ConnectionException, InterruptedException {
		USERNAME = "sahin.habesoglu@82demo.com";
		PASSWORD = "Agile#12345";
		TOKEN = "";
		
		ConnectorConfig config = new ConnectorConfig();
		config.setUsername(USERNAME);
		config.setPassword(PASSWORD+TOKEN);

		EnterpriseConnection enterpriseConnection = com.sforce.soap.enterprise.Connector.newConnection(config);
		enterpriseConnection.login(config.getUsername(), config.getPassword());

		//SoapConnection soapConn = com.sforce.soap.apex.Connector.newConnection("","");
		//soapConn.setSessionHeader(config.getSessionId());

		com.sforce.soap.metadata.MetadataConnection conn = com.sforce.soap.metadata.Connector.newConnection("","");
		conn.setSessionHeader(config.getSessionId());
		
		//partnerConnection = com.sforce.soap.partner.Connector.newConnection(config.getUsername(), config.getPassword());

		
		//contacts = createContacts();
		//accounts = createAccounts();
		//opportunities = createOpportunities();
		//createQuotes();
		createCustomField(conn);
		//deleteCustomFields(conn);
	}




}



