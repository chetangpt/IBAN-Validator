# **IBAN Validator**

This application builds a Restful endpoint to validate an IBAN number.

The end point designed is as below-

- **GET- {server-url}/v1/iban/{ibanId}/validate**

The application is build on Java 11 using 
- SpringBoot 2.6
- Junit 5

It's a simple microservice which can be extended based on the requirement. Currently, the validator supports a limited set of countries.



