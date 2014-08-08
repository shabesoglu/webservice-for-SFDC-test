package wsc;

import com.sforce.soap.SuperClass.SoapConnection;
import com.sforce.soap.SuperClass.Connector;
import com.sforce.soap.SuperClass.RequestClass;
import com.sforce.soap.SuperClass.ResponseClass;

import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.sforce.soap.enterprise.*;


public class CallWS {


  static final String USERNAME = "zforceactivation+3@3demo.com";
  static final String PASSWORD = "Agile#12345rHWqn2h9Fgu2BgHtw1MTjVbs";

  static SoapConnection MyWebserviceWSconnection;
  static EnterpriseConnection enterpriseConnection;

  public static void main(String[] args) {

    ConnectorConfig config = new ConnectorConfig();
    config.setUsername(USERNAME);
    config.setPassword(PASSWORD);


    try {

      //create a connection to Enterprise API -- authentication occurs
      enterpriseConnection = com.sforce.soap.enterprise.Connector.newConnection(config);    
      // display some current settings
      System.out.println("Auth EndPoint: "+config.getAuthEndpoint());
      System.out.println("Service EndPoint: "+config.getServiceEndpoint());
      System.out.println("Username: "+config.getUsername());
      System.out.println("SessionId: "+config.getSessionId());


      //create new connection to exportData webservice -- no authentication information is included
      MyWebserviceWSconnection = Connector.newConnection("","");
      //include session Id (obtained from enterprise api) in exportData webservice
      MyWebserviceWSconnection.setSessionHeader(config.getSessionId());
      
      RequestClass reqClass = new RequestClass();
      reqClass.setAccountName("Account Created By Java Program");
      
      ResponseClass resClass = new ResponseClass();
      resClass = MyWebserviceWSconnection.behaviourOfWebService(reqClass);
      System.out.println("Record ID ---"+resClass.getResponseResultID());
      System.out.println("Record Name ---"+resClass.getResponseResultName());
      System.out.println("Record Record Type ---"+resClass.getResponseResultRecordType());

      //String result = MyWebserviceWSconnection.receiveData("test");
      //System.out.println("Result: "+result);


    } catch (ConnectionException e1) {
        e1.printStackTrace();
    }  
  }
}