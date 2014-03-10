package eu.europa.ec.een.tools.services.soap;

public class IPODServiceSOAPProxy implements eu.europa.ec.een.tools.services.soap.IPODServiceSOAP {
  private String _endpoint = null;
  private eu.europa.ec.een.tools.services.soap.IPODServiceSOAP iPODServiceSOAP = null;
  
  public IPODServiceSOAPProxy() {
    _initIPODServiceSOAPProxy();
  }
  
  public IPODServiceSOAPProxy(String endpoint) {
    _endpoint = endpoint;
    _initIPODServiceSOAPProxy();
  }
  
  private void _initIPODServiceSOAPProxy() {
    try {
      iPODServiceSOAP = (new org.tempuri.QueryServiceLocator()).getBasicHttpBinding_IPODServiceSOAP();
      if (iPODServiceSOAP != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iPODServiceSOAP)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iPODServiceSOAP)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iPODServiceSOAP != null)
      ((javax.xml.rpc.Stub)iPODServiceSOAP)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public eu.europa.ec.een.tools.services.soap.IPODServiceSOAP getIPODServiceSOAP() {
    if (iPODServiceSOAP == null)
      _initIPODServiceSOAPProxy();
    return iPODServiceSOAP;
  }
  
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Profile[] getProfiles(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileQueryRequest request) throws java.rmi.RemoteException{
    if (iPODServiceSOAP == null)
      _initIPODServiceSOAPProxy();
    return iPODServiceSOAP.getProfiles(request);
  }
  
  public java.lang.String saveProfile(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileInput request, org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Credentials credentials) throws java.rmi.RemoteException{
    if (iPODServiceSOAP == null)
      _initIPODServiceSOAPProxy();
    return iPODServiceSOAP.saveProfile(request, credentials);
  }
  
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Profile[] getReferences(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileQueryRequest request) throws java.rmi.RemoteException{
    if (iPODServiceSOAP == null)
      _initIPODServiceSOAPProxy();
    return iPODServiceSOAP.getReferences(request);
  }
  
  
}