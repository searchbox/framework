/**
 * QueryServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class QueryServiceLocator extends org.apache.axis.client.Service implements org.tempuri.QueryService {

    public QueryServiceLocator() {
    }


    public QueryServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public QueryServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IPODServiceSOAP
    private java.lang.String BasicHttpBinding_IPODServiceSOAP_address = "http://een.ec.europa.eu/tools/services/podv6/QueryService.svc/soap";

    public java.lang.String getBasicHttpBinding_IPODServiceSOAPAddress() {
        return BasicHttpBinding_IPODServiceSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BasicHttpBinding_IPODServiceSOAPWSDDServiceName = "BasicHttpBinding_IPODServiceSOAP";

    public java.lang.String getBasicHttpBinding_IPODServiceSOAPWSDDServiceName() {
        return BasicHttpBinding_IPODServiceSOAPWSDDServiceName;
    }

    public void setBasicHttpBinding_IPODServiceSOAPWSDDServiceName(java.lang.String name) {
        BasicHttpBinding_IPODServiceSOAPWSDDServiceName = name;
    }

    public eu.europa.ec.een.tools.services.soap.IPODServiceSOAP getBasicHttpBinding_IPODServiceSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IPODServiceSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IPODServiceSOAP(endpoint);
    }

    public eu.europa.ec.een.tools.services.soap.IPODServiceSOAP getBasicHttpBinding_IPODServiceSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.BasicHttpBinding_IPODServiceSOAPStub _stub = new org.tempuri.BasicHttpBinding_IPODServiceSOAPStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IPODServiceSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IPODServiceSOAPEndpointAddress(java.lang.String address) {
        BasicHttpBinding_IPODServiceSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (eu.europa.ec.een.tools.services.soap.IPODServiceSOAP.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.BasicHttpBinding_IPODServiceSOAPStub _stub = new org.tempuri.BasicHttpBinding_IPODServiceSOAPStub(new java.net.URL(BasicHttpBinding_IPODServiceSOAP_address), this);
                _stub.setPortName(getBasicHttpBinding_IPODServiceSOAPWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("BasicHttpBinding_IPODServiceSOAP".equals(inputPortName)) {
            return getBasicHttpBinding_IPODServiceSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "QueryService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_IPODServiceSOAP"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BasicHttpBinding_IPODServiceSOAP".equals(portName)) {
            setBasicHttpBinding_IPODServiceSOAPEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
