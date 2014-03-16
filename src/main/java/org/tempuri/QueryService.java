/**
 * QueryService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface QueryService extends javax.xml.rpc.Service {
  public java.lang.String getBasicHttpBinding_IPODServiceSOAPAddress();

  public eu.europa.ec.een.tools.services.soap.IPODServiceSOAP getBasicHttpBinding_IPODServiceSOAP()
      throws javax.xml.rpc.ServiceException;

  public eu.europa.ec.een.tools.services.soap.IPODServiceSOAP getBasicHttpBinding_IPODServiceSOAP(
      java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
