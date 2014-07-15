/**
 * 
 * Copyright (c) Microsoft and contributors.  All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

// Warning: This code was generated by a tool.
// 
// Changes to this file may cause incorrect behavior and will be lost if the
// code is regenerated.

package com.microsoft.azure.management.network;

import com.microsoft.azure.AzureHttpStatus;
import com.microsoft.azure.core.ServiceOperations;
import com.microsoft.azure.core.datatype.DatatypeFactoryImpl;
import com.microsoft.azure.core.utils.BOMInputStream;
import com.microsoft.azure.core.utils.StreamUtils;
import com.microsoft.azure.exception.ServiceException;
import com.microsoft.azure.management.network.models.ClientRootCertificateCreateParameters;
import com.microsoft.azure.management.network.models.ClientRootCertificateGetResponse;
import com.microsoft.azure.management.network.models.ClientRootCertificateListResponse;
import com.microsoft.azure.management.network.models.GatewayOperationResponse;
import com.microsoft.azure.tracing.CloudTracing;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
* The Network Management API includes operations for managing the client root
* certificates for your subscription.  (see
* http://msdn.microsoft.com/en-us/library/windowsazure/jj154113.aspx for more
* information)
*/
public class ClientRootCertificateOperationsImpl implements ServiceOperations<NetworkManagementClientImpl>, ClientRootCertificateOperations {
    /**
    * Initializes a new instance of the ClientRootCertificateOperationsImpl
    * class.
    *
    * @param client Reference to the service client.
    */
    ClientRootCertificateOperationsImpl(NetworkManagementClientImpl client) {
        this.client = client;
    }
    
    private NetworkManagementClientImpl client;
    
    /**
    * Gets a reference to the
    * microsoft.windowsazure.management.network.NetworkManagementClientImpl.
    * @return The Client value.
    */
    public NetworkManagementClientImpl getClient() {
        return this.client;
    }
    
    /**
    * The Upload Client Root Certificate operation is used to upload a new
    * client root certificate to Azure.  (see
    * http://msdn.microsoft.com/en-us/library/windowsazure/dn205129.aspx for
    * more information)
    *
    * @param networkName Required. The name of the virtual network for this
    * gateway.
    * @param parameters Required. Parameters supplied to the Upload Client Root
    * Certificate Virtual Network Gateway operation.
    * @return A standard service response including an HTTP status code and
    * request ID.
    */
    @Override
    public Future<GatewayOperationResponse> createAsync(final String networkName, final ClientRootCertificateCreateParameters parameters) {
        return this.getClient().getExecutorService().submit(new Callable<GatewayOperationResponse>() { 
            @Override
            public GatewayOperationResponse call() throws Exception {
                return create(networkName, parameters);
            }
         });
    }
    
    /**
    * The Upload Client Root Certificate operation is used to upload a new
    * client root certificate to Azure.  (see
    * http://msdn.microsoft.com/en-us/library/windowsazure/dn205129.aspx for
    * more information)
    *
    * @param networkName Required. The name of the virtual network for this
    * gateway.
    * @param parameters Required. Parameters supplied to the Upload Client Root
    * Certificate Virtual Network Gateway operation.
    * @throws MalformedURLException Thrown in case of an invalid request URL
    * @throws ProtocolException Thrown if invalid request method
    * @throws ServiceException Thrown if an unexpected response is found.
    * @throws IOException Signals that an I/O exception of some sort has
    * occurred
    * @throws XmlPullParserException This exception is thrown to signal XML
    * Pull Parser related faults.
    * @throws InterruptedException Thrown when a thread is waiting, sleeping,
    * or otherwise occupied, and the thread is interrupted, either before or
    * during the activity. Occasionally a method may wish to test whether the
    * current thread has been interrupted, and if so, to immediately throw
    * this exception. The following code can be used to achieve this effect:
    * @throws ExecutionException Thrown when attempting to retrieve the result
    * of a task that aborted by throwing an exception. This exception can be
    * inspected using the Throwable.getCause() method.
    * @throws ServiceException Thrown if the server returned an error for the
    * request.
    * @return A standard service response including an HTTP status code and
    * request ID.
    */
    @Override
    public GatewayOperationResponse create(String networkName, ClientRootCertificateCreateParameters parameters) throws MalformedURLException, ProtocolException, ServiceException, IOException, XmlPullParserException, InterruptedException, ExecutionException {
        // Validate
        if (networkName == null) {
            throw new NullPointerException("networkName");
        }
        if (parameters == null) {
            throw new NullPointerException("parameters");
        }
        if (parameters.getCertificate() == null) {
            throw new NullPointerException("parameters.Certificate");
        }
        
        // Tracing
        boolean shouldTrace = CloudTracing.getIsEnabled();
        String invocationId = null;
        if (shouldTrace) {
            invocationId = Long.toString(CloudTracing.getNextInvocationId());
            HashMap<String, Object> tracingParameters = new HashMap<String, Object>();
            tracingParameters.put("networkName", networkName);
            tracingParameters.put("parameters", parameters);
            CloudTracing.enter(invocationId, this, "createAsync", tracingParameters);
        }
        
        // Construct URL
        String url = "/" + (this.getClient().getCredentials().getSubscriptionId() != null ? this.getClient().getCredentials().getSubscriptionId().trim() : "") + "/services/networking/" + networkName.trim() + "/gateway/clientrootcertificates";
        String baseUrl = this.getClient().getBaseUri().toString();
        // Trim '/' character from the end of baseUrl and beginning of url.
        if (baseUrl.charAt(baseUrl.length() - 1) == '/') {
            baseUrl = baseUrl.substring(0, (baseUrl.length() - 1) + 0);
        }
        if (url.charAt(0) == '/') {
            url = url.substring(1);
        }
        url = baseUrl + "/" + url;
        url = url.replace(" ", "%20");
        
        // Create HTTP transport objects
        URL serverAddress = new URL(url);
        HttpURLConnection httpRequest = ((HttpURLConnection) serverAddress.openConnection());
        httpRequest.setRequestMethod("POST");
        httpRequest.setDoOutput(true);
        
        // Set Headers
        httpRequest.setRequestProperty("Content-Type", "application/xml");
        httpRequest.setRequestProperty("x-ms-version", "2014-05-01");
        
        // Set Credentials
        this.getClient().getCredentials().processRequest(httpRequest);
        
        // Serialize Request
        String requestContent = parameters.getCertificate();
        httpRequest.setRequestProperty("Content-Type", "application/xml");
        
        // Send Request
        try {
            httpRequest.setFixedLengthStreamingMode(requestContent.getBytes().length);
            httpRequest.getOutputStream().write(requestContent.getBytes());
            int statusCode = httpRequest.getResponseCode();
            if (statusCode != AzureHttpStatus.ACCEPTED) {
                ServiceException ex = ServiceException.createFromXml(requestContent, httpRequest.getResponseMessage(), httpRequest.getResponseCode(), httpRequest.getContentType(), httpRequest.getInputStream());
                if (shouldTrace) {
                    CloudTracing.error(invocationId, ex);
                }
                throw ex;
            }
            
            // Create Result
            GatewayOperationResponse result = null;
            // Deserialize Response
            InputStream responseContent = httpRequest.getInputStream();
            result = new GatewayOperationResponse();
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new InputStreamReader(new BOMInputStream(responseContent)));
            
            int eventType = xmlPullParser.getEventType();
            while ((eventType == XmlPullParser.END_DOCUMENT) != true) {
                if (eventType == XmlPullParser.START_TAG && "GatewayOperationAsyncResponse".equals(xmlPullParser.getName()) && "http://schemas.microsoft.com/windowsazure".equals(xmlPullParser.getNamespace())) {
                    while ((eventType == XmlPullParser.END_TAG && "GatewayOperationAsyncResponse".equals(xmlPullParser.getName()) && "http://schemas.microsoft.com/windowsazure".equals(xmlPullParser.getNamespace())) != true) {
                        if (eventType == XmlPullParser.START_TAG && "ID".equals(xmlPullParser.getName()) && "http://schemas.microsoft.com/windowsazure".equals(xmlPullParser.getNamespace())) {
                            while ((eventType == XmlPullParser.END_TAG && "ID".equals(xmlPullParser.getName()) && "http://schemas.microsoft.com/windowsazure".equals(xmlPullParser.getNamespace())) != true) {
                                String idInstance;
                                if (eventType == XmlPullParser.TEXT) {
                                    idInstance = xmlPullParser.getText();
                                    result.setOperationId(idInstance);
                                }
                                
                                eventType = xmlPullParser.next();
                            }
                        }
                        
                        eventType = xmlPullParser.next();
                    }
                }
                
                eventType = xmlPullParser.next();
            }
            
            result.setStatusCode(statusCode);
            result.setRequestId(httpRequest.getHeaderField("x-ms-request-id"));
            
            if (shouldTrace) {
                CloudTracing.exit(invocationId, result);
            }
            return result;
        } finally {
            if (httpRequest != null) {
                httpRequest.disconnect();
            }
        }
    }
    
    /**
    * The Delete Client Root Certificate operation deletes a previously
    * uploaded client root certificate from Azure.  (see
    * http://msdn.microsoft.com/en-us/library/windowsazure/dn205128.aspx for
    * more information)
    *
    * @param networkName Required. The name of the virtual network for this
    * gateway.
    * @param certificateThumbprint Required. The X509 certificate thumbprint.
    * @return A standard service response including an HTTP status code and
    * request ID.
    */
    @Override
    public Future<GatewayOperationResponse> deleteAsync(final String networkName, final String certificateThumbprint) {
        return this.getClient().getExecutorService().submit(new Callable<GatewayOperationResponse>() { 
            @Override
            public GatewayOperationResponse call() throws Exception {
                return delete(networkName, certificateThumbprint);
            }
         });
    }
    
    /**
    * The Delete Client Root Certificate operation deletes a previously
    * uploaded client root certificate from Azure.  (see
    * http://msdn.microsoft.com/en-us/library/windowsazure/dn205128.aspx for
    * more information)
    *
    * @param networkName Required. The name of the virtual network for this
    * gateway.
    * @param certificateThumbprint Required. The X509 certificate thumbprint.
    * @throws MalformedURLException Thrown in case of an invalid request URL
    * @throws ProtocolException Thrown if invalid request method
    * @throws ServiceException Thrown if an unexpected response is found.
    * @throws IOException Signals that an I/O exception of some sort has
    * occurred
    * @throws XmlPullParserException This exception is thrown to signal XML
    * Pull Parser related faults.
    * @throws InterruptedException Thrown when a thread is waiting, sleeping,
    * or otherwise occupied, and the thread is interrupted, either before or
    * during the activity. Occasionally a method may wish to test whether the
    * current thread has been interrupted, and if so, to immediately throw
    * this exception. The following code can be used to achieve this effect:
    * @throws ExecutionException Thrown when attempting to retrieve the result
    * of a task that aborted by throwing an exception. This exception can be
    * inspected using the Throwable.getCause() method.
    * @throws ServiceException Thrown if the server returned an error for the
    * request.
    * @return A standard service response including an HTTP status code and
    * request ID.
    */
    @Override
    public GatewayOperationResponse delete(String networkName, String certificateThumbprint) throws MalformedURLException, ProtocolException, ServiceException, IOException, XmlPullParserException, InterruptedException, ExecutionException {
        // Validate
        if (networkName == null) {
            throw new NullPointerException("networkName");
        }
        if (certificateThumbprint == null) {
            throw new NullPointerException("certificateThumbprint");
        }
        
        // Tracing
        boolean shouldTrace = CloudTracing.getIsEnabled();
        String invocationId = null;
        if (shouldTrace) {
            invocationId = Long.toString(CloudTracing.getNextInvocationId());
            HashMap<String, Object> tracingParameters = new HashMap<String, Object>();
            tracingParameters.put("networkName", networkName);
            tracingParameters.put("certificateThumbprint", certificateThumbprint);
            CloudTracing.enter(invocationId, this, "deleteAsync", tracingParameters);
        }
        
        // Construct URL
        String url = "/" + (this.getClient().getCredentials().getSubscriptionId() != null ? this.getClient().getCredentials().getSubscriptionId().trim() : "") + "/services/networking/" + networkName.trim() + "/gateway/clientrootcertificates/" + certificateThumbprint.trim();
        String baseUrl = this.getClient().getBaseUri().toString();
        // Trim '/' character from the end of baseUrl and beginning of url.
        if (baseUrl.charAt(baseUrl.length() - 1) == '/') {
            baseUrl = baseUrl.substring(0, (baseUrl.length() - 1) + 0);
        }
        if (url.charAt(0) == '/') {
            url = url.substring(1);
        }
        url = baseUrl + "/" + url;
        url = url.replace(" ", "%20");
        
        // Create HTTP transport objects
        URL serverAddress = new URL(url);
        HttpURLConnection httpRequest = ((HttpURLConnection) serverAddress.openConnection());
        httpRequest.setRequestMethod("DELETE");
        
        // Set Headers
        httpRequest.setRequestProperty("Content-Type", "application/xml");
        httpRequest.setRequestProperty("x-ms-version", "2014-05-01");
        
        // Set Credentials
        this.getClient().getCredentials().processRequest(httpRequest);
        
        // Send Request
        try {
            int statusCode = httpRequest.getResponseCode();
            if (statusCode != AzureHttpStatus.OK) {
                ServiceException ex = ServiceException.createFromXml(null, httpRequest.getResponseMessage(), httpRequest.getResponseCode(), httpRequest.getContentType(), httpRequest.getInputStream());
                if (shouldTrace) {
                    CloudTracing.error(invocationId, ex);
                }
                throw ex;
            }
            
            // Create Result
            GatewayOperationResponse result = null;
            // Deserialize Response
            InputStream responseContent = httpRequest.getInputStream();
            result = new GatewayOperationResponse();
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new InputStreamReader(new BOMInputStream(responseContent)));
            
            int eventType = xmlPullParser.getEventType();
            while ((eventType == XmlPullParser.END_DOCUMENT) != true) {
                if (eventType == XmlPullParser.START_TAG && "GatewayOperationAsyncResponse".equals(xmlPullParser.getName()) && "http://schemas.microsoft.com/windowsazure".equals(xmlPullParser.getNamespace())) {
                    while ((eventType == XmlPullParser.END_TAG && "GatewayOperationAsyncResponse".equals(xmlPullParser.getName()) && "http://schemas.microsoft.com/windowsazure".equals(xmlPullParser.getNamespace())) != true) {
                        if (eventType == XmlPullParser.START_TAG && "ID".equals(xmlPullParser.getName()) && "http://schemas.microsoft.com/windowsazure".equals(xmlPullParser.getNamespace())) {
                            while ((eventType == XmlPullParser.END_TAG && "ID".equals(xmlPullParser.getName()) && "http://schemas.microsoft.com/windowsazure".equals(xmlPullParser.getNamespace())) != true) {
                                String idInstance;
                                if (eventType == XmlPullParser.TEXT) {
                                    idInstance = xmlPullParser.getText();
                                    result.setOperationId(idInstance);
                                }
                                
                                eventType = xmlPullParser.next();
                            }
                        }
                        
                        eventType = xmlPullParser.next();
                    }
                }
                
                eventType = xmlPullParser.next();
            }
            
            result.setStatusCode(statusCode);
            result.setRequestId(httpRequest.getHeaderField("x-ms-request-id"));
            
            if (shouldTrace) {
                CloudTracing.exit(invocationId, result);
            }
            return result;
        } finally {
            if (httpRequest != null) {
                httpRequest.disconnect();
            }
        }
    }
    
    /**
    * The Get Client Root Certificate operation returns the public portion of a
    * previously uploaded client root certificate in a base-64-encoded format
    * from Azure.  (see
    * http://msdn.microsoft.com/en-us/library/windowsazure/dn205127.aspx for
    * more information)
    *
    * @param networkName Required. The name of the virtual network for this
    * gateway.
    * @param certificateThumbprint Required. The X509 certificate thumbprint.
    * @return Response to the Get Client Root Certificate operation.
    */
    @Override
    public Future<ClientRootCertificateGetResponse> getAsync(final String networkName, final String certificateThumbprint) {
        return this.getClient().getExecutorService().submit(new Callable<ClientRootCertificateGetResponse>() { 
            @Override
            public ClientRootCertificateGetResponse call() throws Exception {
                return get(networkName, certificateThumbprint);
            }
         });
    }
    
    /**
    * The Get Client Root Certificate operation returns the public portion of a
    * previously uploaded client root certificate in a base-64-encoded format
    * from Azure.  (see
    * http://msdn.microsoft.com/en-us/library/windowsazure/dn205127.aspx for
    * more information)
    *
    * @param networkName Required. The name of the virtual network for this
    * gateway.
    * @param certificateThumbprint Required. The X509 certificate thumbprint.
    * @throws MalformedURLException Thrown in case of an invalid request URL
    * @throws ProtocolException Thrown if invalid request method
    * @throws ServiceException Thrown if an unexpected response is found.
    * @throws IOException Signals that an I/O exception of some sort has
    * occurred
    * @throws XmlPullParserException This exception is thrown to signal XML
    * Pull Parser related faults.
    * @throws DatatypeConfigurationException Invalid datatype configuration
    * @return Response to the Get Client Root Certificate operation.
    */
    @Override
    public ClientRootCertificateGetResponse get(String networkName, String certificateThumbprint) throws MalformedURLException, ProtocolException, ServiceException, IOException, XmlPullParserException, DatatypeConfigurationException {
        // Validate
        if (networkName == null) {
            throw new NullPointerException("networkName");
        }
        if (certificateThumbprint == null) {
            throw new NullPointerException("certificateThumbprint");
        }
        
        // Tracing
        boolean shouldTrace = CloudTracing.getIsEnabled();
        String invocationId = null;
        if (shouldTrace) {
            invocationId = Long.toString(CloudTracing.getNextInvocationId());
            HashMap<String, Object> tracingParameters = new HashMap<String, Object>();
            tracingParameters.put("networkName", networkName);
            tracingParameters.put("certificateThumbprint", certificateThumbprint);
            CloudTracing.enter(invocationId, this, "getAsync", tracingParameters);
        }
        
        // Construct URL
        String url = "/" + (this.getClient().getCredentials().getSubscriptionId() != null ? this.getClient().getCredentials().getSubscriptionId().trim() : "") + "/services/networking/" + networkName.trim() + "/gateway/clientrootcertificates/" + certificateThumbprint.trim();
        String baseUrl = this.getClient().getBaseUri().toString();
        // Trim '/' character from the end of baseUrl and beginning of url.
        if (baseUrl.charAt(baseUrl.length() - 1) == '/') {
            baseUrl = baseUrl.substring(0, (baseUrl.length() - 1) + 0);
        }
        if (url.charAt(0) == '/') {
            url = url.substring(1);
        }
        url = baseUrl + "/" + url;
        url = url.replace(" ", "%20");
        
        // Create HTTP transport objects
        URL serverAddress = new URL(url);
        HttpURLConnection httpRequest = ((HttpURLConnection) serverAddress.openConnection());
        httpRequest.setRequestMethod("GET");
        httpRequest.setDoInput(true);
        
        // Set Headers
        httpRequest.setRequestProperty("x-ms-version", "2014-05-01");
        
        // Set Credentials
        this.getClient().getCredentials().processRequest(httpRequest);
        
        // Send Request
        try {
            int statusCode = httpRequest.getResponseCode();
            if (statusCode != AzureHttpStatus.OK) {
                ServiceException ex = ServiceException.createFromXml(null, httpRequest.getResponseMessage(), httpRequest.getResponseCode(), httpRequest.getContentType(), httpRequest.getInputStream());
                if (shouldTrace) {
                    CloudTracing.error(invocationId, ex);
                }
                throw ex;
            }
            
            // Create Result
            ClientRootCertificateGetResponse result = null;
            // Deserialize Response
            InputStream responseContent = httpRequest.getInputStream();
            result = new ClientRootCertificateGetResponse();
            result.setCertificate(StreamUtils.toString(responseContent));
            
            result.setStatusCode(statusCode);
            result.setRequestId(httpRequest.getHeaderField("x-ms-request-id"));
            
            if (shouldTrace) {
                CloudTracing.exit(invocationId, result);
            }
            return result;
        } finally {
            if (httpRequest != null) {
                httpRequest.disconnect();
            }
        }
    }
    
    /**
    * The List Client Root Certificates operation returns a list of all the
    * client root certificates that are associated with the specified virtual
    * network in Azure.  (see
    * http://msdn.microsoft.com/en-us/library/windowsazure/dn205130.aspx for
    * more information)
    *
    * @param networkName Required. The name of the virtual network for this
    * gateway.
    * @return The response for the List Client Root Certificates operation.
    */
    @Override
    public Future<ClientRootCertificateListResponse> listAsync(final String networkName) {
        return this.getClient().getExecutorService().submit(new Callable<ClientRootCertificateListResponse>() { 
            @Override
            public ClientRootCertificateListResponse call() throws Exception {
                return list(networkName);
            }
         });
    }
    
    /**
    * The List Client Root Certificates operation returns a list of all the
    * client root certificates that are associated with the specified virtual
    * network in Azure.  (see
    * http://msdn.microsoft.com/en-us/library/windowsazure/dn205130.aspx for
    * more information)
    *
    * @param networkName Required. The name of the virtual network for this
    * gateway.
    * @throws MalformedURLException Thrown in case of an invalid request URL
    * @throws ProtocolException Thrown if invalid request method
    * @throws ServiceException Thrown if an unexpected response is found.
    * @throws IOException Signals that an I/O exception of some sort has
    * occurred
    * @throws XmlPullParserException This exception is thrown to signal XML
    * Pull Parser related faults.
    * @throws DatatypeConfigurationException Invalid datatype configuration
    * @return The response for the List Client Root Certificates operation.
    */
    @Override
    public ClientRootCertificateListResponse list(String networkName) throws MalformedURLException, ProtocolException, ServiceException, IOException, XmlPullParserException, DatatypeConfigurationException {
        // Validate
        if (networkName == null) {
            throw new NullPointerException("networkName");
        }
        
        // Tracing
        boolean shouldTrace = CloudTracing.getIsEnabled();
        String invocationId = null;
        if (shouldTrace) {
            invocationId = Long.toString(CloudTracing.getNextInvocationId());
            HashMap<String, Object> tracingParameters = new HashMap<String, Object>();
            tracingParameters.put("networkName", networkName);
            CloudTracing.enter(invocationId, this, "listAsync", tracingParameters);
        }
        
        // Construct URL
        String url = "/" + (this.getClient().getCredentials().getSubscriptionId() != null ? this.getClient().getCredentials().getSubscriptionId().trim() : "") + "/services/networking/" + networkName.trim() + "/gateway/clientrootcertificates";
        String baseUrl = this.getClient().getBaseUri().toString();
        // Trim '/' character from the end of baseUrl and beginning of url.
        if (baseUrl.charAt(baseUrl.length() - 1) == '/') {
            baseUrl = baseUrl.substring(0, (baseUrl.length() - 1) + 0);
        }
        if (url.charAt(0) == '/') {
            url = url.substring(1);
        }
        url = baseUrl + "/" + url;
        url = url.replace(" ", "%20");
        
        // Create HTTP transport objects
        URL serverAddress = new URL(url);
        HttpURLConnection httpRequest = ((HttpURLConnection) serverAddress.openConnection());
        httpRequest.setRequestMethod("GET");
        httpRequest.setDoInput(true);
        
        // Set Headers
        httpRequest.setRequestProperty("Content-Type", "application/xml");
        httpRequest.setRequestProperty("x-ms-version", "2014-05-01");
        
        // Set Credentials
        this.getClient().getCredentials().processRequest(httpRequest);
        
        // Send Request
        try {
            int statusCode = httpRequest.getResponseCode();
            if (statusCode != AzureHttpStatus.OK) {
                ServiceException ex = ServiceException.createFromXml(null, httpRequest.getResponseMessage(), httpRequest.getResponseCode(), httpRequest.getContentType(), httpRequest.getInputStream());
                if (shouldTrace) {
                    CloudTracing.error(invocationId, ex);
                }
                throw ex;
            }
            
            // Create Result
            ClientRootCertificateListResponse result = null;
            // Deserialize Response
            InputStream responseContent = httpRequest.getInputStream();
            result = new ClientRootCertificateListResponse();
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new InputStreamReader(new BOMInputStream(responseContent)));
            
            int eventType = xmlPullParser.getEventType();
            while ((eventType == XmlPullParser.END_DOCUMENT) != true) {
                if (eventType == XmlPullParser.START_TAG && "ClientRootCertificates".equals(xmlPullParser.getName()) && "http://schemas.microsoft.com/windowsazure".equals(xmlPullParser.getNamespace())) {
                    while ((eventType == XmlPullParser.END_TAG && "ClientRootCertificates".equals(xmlPullParser.getName()) && "http://schemas.microsoft.com/windowsazure".equals(xmlPullParser.getNamespace())) != true) {
                        if (eventType == XmlPullParser.START_TAG && "ClientRootCertificate".equals(xmlPullParser.getName()) && "http://schemas.microsoft.com/windowsazure".equals(xmlPullParser.getNamespace())) {
                            ClientRootCertificateListResponse.ClientRootCertificate clientRootCertificateInstance = new ClientRootCertificateListResponse.ClientRootCertificate();
                            result.getClientRootCertificates().add(clientRootCertificateInstance);
                            
                            while ((eventType == XmlPullParser.END_TAG && "ClientRootCertificate".equals(xmlPullParser.getName()) && "http://schemas.microsoft.com/windowsazure".equals(xmlPullParser.getNamespace())) != true) {
                                if (eventType == XmlPullParser.START_TAG && "ExpirationTime".equals(xmlPullParser.getName()) && "http://schemas.microsoft.com/windowsazure".equals(xmlPullParser.getNamespace())) {
                                    while ((eventType == XmlPullParser.END_TAG && "ExpirationTime".equals(xmlPullParser.getName()) && "http://schemas.microsoft.com/windowsazure".equals(xmlPullParser.getNamespace())) != true) {
                                        Calendar expirationTimeInstance;
                                        if (eventType == XmlPullParser.TEXT) {
                                            expirationTimeInstance = DatatypeFactoryImpl.newInstance().newXMLGregorianCalendar(xmlPullParser.getText()).toGregorianCalendar();
                                            clientRootCertificateInstance.setExpirationTime(expirationTimeInstance);
                                        }
                                        
                                        eventType = xmlPullParser.next();
                                    }
                                }
                                
                                if (eventType == XmlPullParser.START_TAG && "Subject".equals(xmlPullParser.getName()) && "http://schemas.microsoft.com/windowsazure".equals(xmlPullParser.getNamespace())) {
                                    while ((eventType == XmlPullParser.END_TAG && "Subject".equals(xmlPullParser.getName()) && "http://schemas.microsoft.com/windowsazure".equals(xmlPullParser.getNamespace())) != true) {
                                        String subjectInstance;
                                        if (eventType == XmlPullParser.TEXT) {
                                            subjectInstance = xmlPullParser.getText();
                                            clientRootCertificateInstance.setSubject(subjectInstance);
                                        }
                                        
                                        eventType = xmlPullParser.next();
                                    }
                                }
                                
                                if (eventType == XmlPullParser.START_TAG && "Thumbprint".equals(xmlPullParser.getName()) && "http://schemas.microsoft.com/windowsazure".equals(xmlPullParser.getNamespace())) {
                                    while ((eventType == XmlPullParser.END_TAG && "Thumbprint".equals(xmlPullParser.getName()) && "http://schemas.microsoft.com/windowsazure".equals(xmlPullParser.getNamespace())) != true) {
                                        String thumbprintInstance;
                                        if (eventType == XmlPullParser.TEXT) {
                                            thumbprintInstance = xmlPullParser.getText();
                                            clientRootCertificateInstance.setThumbprint(thumbprintInstance);
                                        }
                                        
                                        eventType = xmlPullParser.next();
                                    }
                                }
                                
                                eventType = xmlPullParser.next();
                            }
                        }
                        
                        eventType = xmlPullParser.next();
                    }
                    
                    eventType = xmlPullParser.next();
                }
                
                eventType = xmlPullParser.next();
            }
            
            result.setStatusCode(statusCode);
            result.setRequestId(httpRequest.getHeaderField("x-ms-request-id"));
            
            if (shouldTrace) {
                CloudTracing.exit(invocationId, result);
            }
            return result;
        } finally {
            if (httpRequest != null) {
                httpRequest.disconnect();
            }
        }
    }
}
