# sabre-pnr-operator
SOAP client handles PNRs (Passenger Name Records), consuming the Sabre web services in order to facilitate and automate travel agency services.

The application periodically retrieves the PNRs and identifies which of them inquire a refund. Then it places the identified PNRs into a different queue.

It provides a RESTful endpoint in order to expose the results of each process.

## SOAP Services 

- [Create Session (SessionCreateRQ)](https://beta.developer.sabre.com/docs/soap_apis/session_management/create_access_token)
- [Close Session (SessionCloseRQ)](https://beta.developer.sabre.com/docs/soap_apis/session_management/close_session)
- [Access Queue (QueueAccessLLSRQ)](https://beta.developer.sabre.com/docs/soap_apis/management/queue/Access_Queue)

## Rest API

Resource URI
```
 /results
```
Sample response:
```json
{
    "scheduled-task" : {
       "session-create":{
           "success":true,
           "status":"approved",
           "description":"Successful creation of session.",
           "message":"Approved response is received from session service.",
           "timestamp":"2019-07-02T02:34:45.945"
       },
       "queue-access":{
           "success":true, 
           "status":"success",
           "description":"Successful access in the queue",
           "message":"Successful response is received from queue access service." ,
           "timestamp":"2019-07-02T02:34:46.796"
       },
       "session-close":{
           "success":true,
           "status":"approved",
           "description":"Successful closing of session.",
           "message":"Approved response is received from session service.",
           "timestamp":"2019-07-02T02:34:47.065"
       }
    }
}
```