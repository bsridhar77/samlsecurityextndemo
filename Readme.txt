This demo project is a POC for using Spring Security SAML Extensions.

The project integrates with one of 2 Identity Providers  SSOCircle.com (OR) okta



The Identity Provider selection and integration is made using below properties in application-secured.properties.

#Name of idp metadata file, idpType=ssocircle.xml,okta.xml
idpType=okta.xml

#Unique EntityId for the Service Provider Application
entityId=urn:test:member:readyuser





To start the unsecured Spring boot app, we can use as below(OR) run directly from with STS/Eclipse IDE
d:\test>java -jar employee-0.0.1-SNAPSHOT.jar


To start the secured Spring boot app with the secured profile, we can use as below (OR) run directly from with STS/Eclipse IDE
d:\test>java -jar employee-0.0.1-SNAPSHOT.jar --spring.profiles.active=secured


In the com.demo.services.employee.config.WebSecurityConfig Class (https://github.com/bsridhar77/samlsecurityextndemo/blob/master/src/main/java/com/demo/services/employee/config/WebSecurityConfig.java),
line number: 520, has the code to make the session creation policy stateless.

With that line commented out, If I run the App with secured profile it successfully authenticates with
the selected IdP (either ssocircle or okta) and shown me the response.

But, if I uncomment that line out, and then run the app with the secured profile, I see it it
going in a loop to authenticate with IdP again and again.

Possibly, there is no session and so it goes to IdP to authenticate and returns back.
But, in the app there is no session for the next request so it again goes to IdP.

This is the behaviour I see when I uncomment the line : 520.

But, with that one line commented out, It works fine.

Since I do not want to use sessions for my services, I am looking for way to address this issue.



 
