/**
 * BasicHttpBinding_IPODServiceSOAPStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class BasicHttpBinding_IPODServiceSOAPStub extends
    org.apache.axis.client.Stub implements
    eu.europa.ec.een.tools.services.soap.IPODServiceSOAP {
  private java.util.Vector cachedSerClasses = new java.util.Vector();
  private java.util.Vector cachedSerQNames = new java.util.Vector();
  private java.util.Vector cachedSerFactories = new java.util.Vector();
  private java.util.Vector cachedDeserFactories = new java.util.Vector();

  static org.apache.axis.description.OperationDesc[] _operations;

  static {
    _operations = new org.apache.axis.description.OperationDesc[3];
    _initOperationDesc1();
  }

  private static void _initOperationDesc1() {
    org.apache.axis.description.OperationDesc oper;
    org.apache.axis.description.ParameterDesc param;
    oper = new org.apache.axis.description.OperationDesc();
    oper.setName("GetProfiles");
    param = new org.apache.axis.description.ParameterDesc(
        new javax.xml.namespace.QName(
            "http://een.ec.europa.eu/tools/services/soap", "request"),
        org.apache.axis.description.ParameterDesc.IN,
        new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "ProfileQueryRequest"),
        org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileQueryRequest.class,
        false, false);
    param.setOmittable(true);
    param.setNillable(true);
    oper.addParameter(param);
    oper.setReturnType(new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOfprofile"));
    oper.setReturnClass(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Profile[].class);
    oper.setReturnQName(new javax.xml.namespace.QName(
        "http://een.ec.europa.eu/tools/services/soap", "GetProfilesResult"));
    param = oper.getReturnParamDesc();
    param
        .setItemQName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profile"));
    oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
    oper.setUse(org.apache.axis.constants.Use.LITERAL);
    _operations[0] = oper;

    oper = new org.apache.axis.description.OperationDesc();
    oper.setName("SaveProfile");
    param = new org.apache.axis.description.ParameterDesc(
        new javax.xml.namespace.QName(
            "http://een.ec.europa.eu/tools/services/soap", "request"),
        org.apache.axis.description.ParameterDesc.IN,
        new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileInput"),
        org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileInput.class,
        false, false);
    param.setOmittable(true);
    param.setNillable(true);
    oper.addParameter(param);
    param = new org.apache.axis.description.ParameterDesc(
        new javax.xml.namespace.QName(
            "http://een.ec.europa.eu/tools/services/soap", "credentials"),
        org.apache.axis.description.ParameterDesc.IN,
        new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "credentials"),
        org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Credentials.class,
        false, false);
    param.setOmittable(true);
    param.setNillable(true);
    oper.addParameter(param);
    oper.setReturnType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
    oper.setReturnClass(java.lang.String.class);
    oper.setReturnQName(new javax.xml.namespace.QName(
        "http://een.ec.europa.eu/tools/services/soap", "SaveProfileResult"));
    oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
    oper.setUse(org.apache.axis.constants.Use.LITERAL);
    _operations[1] = oper;

    oper = new org.apache.axis.description.OperationDesc();
    oper.setName("GetReferences");
    param = new org.apache.axis.description.ParameterDesc(
        new javax.xml.namespace.QName(
            "http://een.ec.europa.eu/tools/services/soap", "request"),
        org.apache.axis.description.ParameterDesc.IN,
        new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "ProfileQueryRequest"),
        org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileQueryRequest.class,
        false, false);
    param.setOmittable(true);
    param.setNillable(true);
    oper.addParameter(param);
    oper.setReturnType(new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOfprofile"));
    oper.setReturnClass(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Profile[].class);
    oper.setReturnQName(new javax.xml.namespace.QName(
        "http://een.ec.europa.eu/tools/services/soap", "GetReferencesResult"));
    param = oper.getReturnParamDesc();
    param
        .setItemQName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profile"));
    oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
    oper.setUse(org.apache.axis.constants.Use.LITERAL);
    _operations[2] = oper;

  }

  public BasicHttpBinding_IPODServiceSOAPStub()
      throws org.apache.axis.AxisFault {
    this(null);
  }

  public BasicHttpBinding_IPODServiceSOAPStub(java.net.URL endpointURL,
      javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
    this(service);
    super.cachedEndpoint = endpointURL;
  }

  public BasicHttpBinding_IPODServiceSOAPStub(javax.xml.rpc.Service service)
      throws org.apache.axis.AxisFault {
    if (service == null) {
      super.service = new org.apache.axis.client.Service();
    } else {
      super.service = service;
    }
    ((org.apache.axis.client.Service) super.service)
        .setTypeMappingVersion("1.2");
    java.lang.Class cls;
    javax.xml.namespace.QName qName;
    javax.xml.namespace.QName qName2;
    java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
    java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
    java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
    java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
    java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
    java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
    java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
    java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
    java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
    java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOfcertification");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Certification[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "certification");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "certification");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOfcertificationInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.CertificationInput[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "certificationInput");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "certificationInput");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOfcountry");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Country[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "country");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "country");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOfcountryInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.CountryInput[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "countryInput");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "countryInput");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOflanguage");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Language[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "language");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "language");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOflanguageInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.LanguageInput[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "languageInput");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "languageInput");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOfmarket");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Market[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "market");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "market");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOfmarketInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.MarketInput[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "marketInput");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "marketInput");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOfnace");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Nace[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "nace");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "nace");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOfnaceInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.NaceInput[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "naceInput");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "naceInput");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOfprofile");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Profile[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profile");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profile");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOfprofileCooperationIprInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationIprInput[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCooperationIprInput");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCooperationIprInput");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOfprofileCooperationPartnershipInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationPartnershipInput[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCooperationPartnershipInput");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCooperationPartnershipInput");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOfprofileCooperationTaskInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationTaskInput[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCooperationTaskInput");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCooperationTaskInput");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOfprofileCustompropsItem");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCustompropsItem[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCustompropsItem");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCustompropsItem");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOfprofileFile");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileFile[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileFile");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileFile");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOfprofileFileInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileFileInput[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileFileInput");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileFileInput");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOftechnology");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Technology[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "technology");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "technology");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ArrayOftechnologyInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.TechnologyInput[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "technologyInput");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "technologyInput");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "certification");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Certification.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "certificationInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.CertificationInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "country");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Country.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "countryInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.CountryInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "credentials");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Credentials.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "keyOnlyBase");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.KeyOnlyBase.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "language");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Language.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "languageInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.LanguageInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "market");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Market.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "marketInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.MarketInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "nace");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Nace.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "naceInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.NaceInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profile");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Profile.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCompany");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCompany.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCompanyCountry");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCompanyCountry.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCompanyInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCompanyInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileContact");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContact.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileContactConsortiumcountry");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContactConsortiumcountry.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileContactInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContactInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileContactOrganizationcountry");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContactOrganizationcountry.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileContent");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContent.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileContentInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContentInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCooperation");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperation.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCooperationExploitations");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationExploitations.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCooperationExploitationsExploitation");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationExploitationsExploitation.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCooperationInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCooperationIpr");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationIpr.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCooperationIprInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationIprInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCooperationPartner");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationPartner.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCooperationPartnerInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationPartnerInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCooperationPartnershipInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationPartnershipInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCooperationStagedev");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationStagedev.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCooperationStagedevInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationStagedevInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCooperationTaskInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationTaskInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCustomprops");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCustomprops.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileCustompropsItem");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCustompropsItem.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileDatum");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDatum.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileDissemination");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDissemination.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileDisseminationExport");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDisseminationExport.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileDisseminationInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDisseminationInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileDisseminationPublish");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDisseminationPublish.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileDisseminationPublishInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDisseminationPublishInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileEoi");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileEoi.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileEoiInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileEoiInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileFile");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileFile.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileFileInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileFileInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileFrameworkProgram");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileFrameworkProgram.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileFundingScheme");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileFundingScheme.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileKeyword");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileKeyword.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileKeywordInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileKeywordInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileOriginInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileOriginInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileProgram");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileProgram.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileProgramCall");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileProgramCall.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileProgramInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileProgramInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "ProfileQueryRequest");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileQueryRequest.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileReference");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileReference.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "profileReferenceInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileReferenceInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "technology");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Technology.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
        "technologyInput");
    cachedSerQNames.add(qName);
    cls = org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.TechnologyInput.class;
    cachedSerClasses.add(cls);
    cachedSerFactories.add(beansf);
    cachedDeserFactories.add(beandf);

    qName = new javax.xml.namespace.QName(
        "http://schemas.microsoft.com/2003/10/Serialization/Arrays",
        "ArrayOfstring");
    cachedSerQNames.add(qName);
    cls = java.lang.String[].class;
    cachedSerClasses.add(cls);
    qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema",
        "string");
    qName2 = new javax.xml.namespace.QName(
        "http://schemas.microsoft.com/2003/10/Serialization/Arrays", "string");
    cachedSerFactories
        .add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName,
            qName2));
    cachedDeserFactories
        .add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

  }

  protected org.apache.axis.client.Call createCall()
      throws java.rmi.RemoteException {
    try {
      org.apache.axis.client.Call _call = super._createCall();
      if (super.maintainSessionSet) {
        _call.setMaintainSession(super.maintainSession);
      }
      if (super.cachedUsername != null) {
        _call.setUsername(super.cachedUsername);
      }
      if (super.cachedPassword != null) {
        _call.setPassword(super.cachedPassword);
      }
      if (super.cachedEndpoint != null) {
        _call.setTargetEndpointAddress(super.cachedEndpoint);
      }
      if (super.cachedTimeout != null) {
        _call.setTimeout(super.cachedTimeout);
      }
      if (super.cachedPortName != null) {
        _call.setPortName(super.cachedPortName);
      }
      java.util.Enumeration keys = super.cachedProperties.keys();
      while (keys.hasMoreElements()) {
        java.lang.String key = (java.lang.String) keys.nextElement();
        _call.setProperty(key, super.cachedProperties.get(key));
      }
      // All the type mapping information is registered
      // when the first call is made.
      // The type mapping information is actually registered in
      // the TypeMappingRegistry of the service, which
      // is the reason why registration is only needed for the first call.
      synchronized (this) {
        if (firstCall()) {
          // must set encoding style before registering serializers
          _call.setEncodingStyle(null);
          for (int i = 0; i < cachedSerFactories.size(); ++i) {
            java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
            javax.xml.namespace.QName qName = (javax.xml.namespace.QName) cachedSerQNames
                .get(i);
            java.lang.Object x = cachedSerFactories.get(i);
            if (x instanceof Class) {
              java.lang.Class sf = (java.lang.Class) cachedSerFactories.get(i);
              java.lang.Class df = (java.lang.Class) cachedDeserFactories
                  .get(i);
              _call.registerTypeMapping(cls, qName, sf, df, false);
            } else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
              org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory) cachedSerFactories
                  .get(i);
              org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory) cachedDeserFactories
                  .get(i);
              _call.registerTypeMapping(cls, qName, sf, df, false);
            }
          }
        }
      }
      return _call;
    } catch (java.lang.Throwable _t) {
      throw new org.apache.axis.AxisFault(
          "Failure trying to get the Call object", _t);
    }
  }

  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Profile[] getProfiles(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileQueryRequest request)
      throws java.rmi.RemoteException {
    if (super.cachedEndpoint == null) {
      throw new org.apache.axis.NoEndPointException();
    }
    org.apache.axis.client.Call _call = createCall();
    _call.setOperation(_operations[0]);
    _call.setUseSOAPAction(true);
    _call
        .setSOAPActionURI("http://een.ec.europa.eu/tools/services/soap/IPODServiceSOAP/GetProfiles");
    _call.setEncodingStyle(null);
    _call
        .setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS,
        Boolean.FALSE);
    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new javax.xml.namespace.QName(
        "http://een.ec.europa.eu/tools/services/soap", "GetProfiles"));

    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      java.lang.Object _resp = _call.invoke(new java.lang.Object[] { request });

      if (_resp instanceof java.rmi.RemoteException) {
        throw (java.rmi.RemoteException) _resp;
      } else {
        extractAttachments(_call);
        try {
          return (org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Profile[]) _resp;
        } catch (java.lang.Exception _exception) {
          return (org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Profile[]) org.apache.axis.utils.JavaUtils
              .convert(
                  _resp,
                  org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Profile[].class);
        }
      }
    } catch (org.apache.axis.AxisFault axisFaultException) {
      throw axisFaultException;
    }
  }

  public java.lang.String saveProfile(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileInput request,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Credentials credentials)
      throws java.rmi.RemoteException {
    if (super.cachedEndpoint == null) {
      throw new org.apache.axis.NoEndPointException();
    }
    org.apache.axis.client.Call _call = createCall();
    _call.setOperation(_operations[1]);
    _call.setUseSOAPAction(true);
    _call
        .setSOAPActionURI("http://een.ec.europa.eu/tools/services/soap/IPODServiceSOAP/SaveProfile");
    _call.setEncodingStyle(null);
    _call
        .setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS,
        Boolean.FALSE);
    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new javax.xml.namespace.QName(
        "http://een.ec.europa.eu/tools/services/soap", "SaveProfile"));

    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      java.lang.Object _resp = _call.invoke(new java.lang.Object[] { request,
          credentials });

      if (_resp instanceof java.rmi.RemoteException) {
        throw (java.rmi.RemoteException) _resp;
      } else {
        extractAttachments(_call);
        try {
          return (java.lang.String) _resp;
        } catch (java.lang.Exception _exception) {
          return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(
              _resp, java.lang.String.class);
        }
      }
    } catch (org.apache.axis.AxisFault axisFaultException) {
      throw axisFaultException;
    }
  }

  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Profile[] getReferences(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileQueryRequest request)
      throws java.rmi.RemoteException {
    if (super.cachedEndpoint == null) {
      throw new org.apache.axis.NoEndPointException();
    }
    org.apache.axis.client.Call _call = createCall();
    _call.setOperation(_operations[2]);
    _call.setUseSOAPAction(true);
    _call
        .setSOAPActionURI("http://een.ec.europa.eu/tools/services/soap/IPODServiceSOAP/GetReferences");
    _call.setEncodingStyle(null);
    _call
        .setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
    _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS,
        Boolean.FALSE);
    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
    _call.setOperationName(new javax.xml.namespace.QName(
        "http://een.ec.europa.eu/tools/services/soap", "GetReferences"));

    setRequestHeaders(_call);
    setAttachments(_call);
    try {
      java.lang.Object _resp = _call.invoke(new java.lang.Object[] { request });

      if (_resp instanceof java.rmi.RemoteException) {
        throw (java.rmi.RemoteException) _resp;
      } else {
        extractAttachments(_call);
        try {
          return (org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Profile[]) _resp;
        } catch (java.lang.Exception _exception) {
          return (org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Profile[]) org.apache.axis.utils.JavaUtils
              .convert(
                  _resp,
                  org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Profile[].class);
        }
      }
    } catch (org.apache.axis.AxisFault axisFaultException) {
      throw axisFaultException;
    }
  }

}
