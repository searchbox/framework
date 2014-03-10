/**
 * IPODServiceSOAP.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package eu.europa.ec.een.tools.services.soap;

public interface IPODServiceSOAP extends java.rmi.Remote {
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Profile[] getProfiles(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileQueryRequest request) throws java.rmi.RemoteException;
    public java.lang.String saveProfile(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileInput request, org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Credentials credentials) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Profile[] getReferences(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileQueryRequest request) throws java.rmi.RemoteException;
}
