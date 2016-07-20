This demo project is a POC for using Spring Security SAML Extensions.

The project integrates with one of 2 Identity Providers  SSOCircle.com (OR) okta



The Identity Provider selection and integration is made using below properties in application-secured.properties.

#Name of idp metadata file, idpType=ssocircle.xml,okta.xml
idpType=okta.xml

#Unique EntityId for the Service Provider Application
entityId=urn:test:member:readyuser



To start the Spring boot app, we can use as below(OR) run directly from with STS/Eclipse IDE
d:\test>java -jar employee-0.0.1-SNAPSHOT.jar

To navigate, access , http://localhost:4080/employee , from REST client or directly from browser.
The Service displays hard-coded JSON result of employee records from service method.


To navigate, access , http://localhost:4080/employee , from REST client or directly from browser.

For Local signout, use, http://localhost:4080/saml/logout?local=true

The port is also configured in application-secured.properties and application.properties.





 
