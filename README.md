# CyberSource Android SDK

## SDK Installation

Android Studio is preferred because Eclipse will not be supported by Google much longer.

### Android Studio (or Gradle)

Add this line to your app's `build.gradle` inside the `dependencies` section as follows:

```groovy
    dependencies {
        compile 'com.cybersource:inappsdk:+'
    }
```

### Eclipse

1. Download the Android SDK jar file 'cybersource-inapp-android-x.x.x.jar' from 'cybersource.com/inapp-sdk'.
2. Include the `cybersource-inapp-android-x.x.x.jar` into the `libs` folder of your Android application project.

### Certificates
The In-App SDK expects the Android app project to have the server certificates placed in its `assets` folder under a new directory named `certificates`.

1. TEST ENVIRONMENT: Download the `.cer` file from https://mobiletest.ic3.com/mpos/transactionProcessor/
2. PRODUCTION ENVIRONMENT: Download the `.cer` file from https://mobile.ic3.com/mpos/transactionProcessor/

## SDK Usage
After the installation is succesfully complete, perform the following steps to program an Android app with this SDK.

1. To initiate requests with the SDK, create an API client that will make API requests on your behalf. The In-App SDK API client can be built as follows:

```java
// Parameters:
// 1) Context - current context
// 2) InAppSDKApiClient.Environment - CYBS ENVIRONMENT
// 3) API_LOGIN_ID String - merchant's API LOGIN ID 
apiClient = new InAppSDKApiClient.Builder (getActivity(),
                                          InAppSDKApiClient.Environment.ENV_TEST, API_LOGIN_ID) 
                                          .sdkConnectionCallback(this) // receive callbacks for connection results
                                          .sdkApiProdEndpoint(PAYMENTS_PROD_URL) // option to configure PROD Endpoint
                                          .sdkApiTestEndpoint(PAYMENTS_TEST_URL) // option to configure TEST Endpoint
                                          .sdkConnectionTimeout(5000) // optional connection time out in milliseconds
                                          .transactionNamespace(TRANSACT_NAMESPACE) // optional
                                          .build();
```

2. To make the API call, you can create a transaction object as follows:

```java
SDKTransactionObject 
  // Type of transaction object 
  .createTransactionObject(SDKTransactionType.SDK_TRANSACTION_ENCRYPTION)
  // Merchant reference code can be set to anything meaningful
  .merchantReferenceCode("Android_Sample_Code")
  // Card data to be encrypted
  .cardData(prepareTestCardData())
  // Optional billing info
  .billTo(prepareBillingInfo())
  .build();
```

3. A card object can be created as follows:

```java
SDKCardData cardData = new SDKCardData.Builder(ACCOUNT_NUMBER,
                                               EXPIRATION_MONTH, // MM
                                               EXPIRATION_YEAR) // YYYY
                                               .cvNumber(CARD_CVV) // Optional
                                               .type(SDKCardAccountNumberType.PAN) // Optional if unencrypted. If the value is set to a token then it is not optional and must be set to SDKCardType.TOKEN
                                               .build();
```

4. Billing information can be created as follows:

```java
SDKBillTo billTo = new SDKBillTo.Builder()
                .firstName("First Name")
                .lastName("Last Name")
                .postalCode("98052")
                .build();
```

5. When the API client and transaction information are ready, you can make a call to perform a specific API.

```java
// Parameters: 
// 1. InAppSDKApiClient.Api - Type of API to make a request
// 2. SDKTransactionObject - The transaction object for current transaction
// 3. Signature String - Fresh message signature for this transaction. The signature generation should always occur outside of a mobile application, for security reasons.  The sample code shows this process occurring inside the application for simplicity, but that workflow should not be used in production systems.
apiClient.performApi(InAppSDKApiClient.Api.API_ENCRYPTION, transactionObject, generateSignature(transactionObject));
```

6) To get a response back, the activity/fragment should implement the `SDKApiConnectionCallback` interface.

```java
@Override
public void onApiConnectionFinished(SDKGatewayResponse response) 
{ 
  Toast.makeText(getActivity(), 
                 response.getType() + " : " + response.getDecision().toString(),
                 Toast.LENGTH_LONG)
                 .show();
}
```
**OR**

In case of an error:

```java
@Override
public void onErrorReceived(SDKError error) 
{ 
  Toast.makeText(getActivity(), 
                 error.getErrorExtraMessage().toString(),
                 Toast.LENGTH_LONG)
                 .show();
}
```

##Sample Application
We have a sample application which demonstrates the SDK usage:  
   https://github.com/CyberSource/cybersource-android-samples
