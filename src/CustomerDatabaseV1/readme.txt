INSTRUCTIONS: 
You need to insert the full URL and select the right HTTP method in your REST client to invoke REST API operations.
The default base URL using local host and default port: http://localhost:7800/customerdb/v1. The host and port may be 
different for your Integration Node. You need to validate it using the Properties view when the REST API service is 
deployed to the test Integration service. To view the REST API base URL in the Properties view you need to select the 
REST API root CustomerDatabaseV1 in the Integration Nodes view and select the Properties view on the right side.

To compose the absolute URL to invoke the REST API you need to concatenate the base URL and operation specific relative 
path. You also need to make sure that you invoke the URL using the appropriate HTTP method and passing the right input 
if required.

JSON CUSTOMER INPUT SAMPLE OBJECT:
{
"firstname" : "John",
"lastname" : "Doe",
"address" : "123 Main Street"
}

OPERATION - getAllCustomers
	URL: <base url>/customers
	HTTP Method: GET
	INPUT: none

OPERATION - addCustomer
	URL: <base url>/customers
	HTTP Method: POST  
	INPUT: Customer (see above for an example).

OPERATION - getCustomer
	URL: <base url>/customers/{customerId}
	HTTP Method : GET  
	INPUT: none.

OPERATION - deleteCustomer
	URL: <base url>/customers/{customerId}
	HTTP Method : DELETE  
	INPUT: none.

OPERATION - updateCustomer
	URL: <base url>/customers/{customerId}
	HTTP Method : PUT  
	INPUT: Customer (see above for an example).

