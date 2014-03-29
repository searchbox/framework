/**
 * ProfileCompanyInput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService;

public class ProfileCompanyInput implements java.io.Serializable {
  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.CertificationInput[] certifications;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.CountryInput clientcountry;

  private java.lang.String experience;

  private java.lang.String kind;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.LanguageInput[] languages;

  private java.lang.Integer since;

  private java.lang.Boolean transnational;

  private java.lang.String turnover;

  public ProfileCompanyInput() {
  }

  public ProfileCompanyInput(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.CertificationInput[] certifications,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.CountryInput clientcountry,
      java.lang.String experience,
      java.lang.String kind,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.LanguageInput[] languages,
      java.lang.Integer since, java.lang.Boolean transnational,
      java.lang.String turnover) {
    this.certifications = certifications;
    this.clientcountry = clientcountry;
    this.experience = experience;
    this.kind = kind;
    this.languages = languages;
    this.since = since;
    this.transnational = transnational;
    this.turnover = turnover;
  }

  /**
   * Gets the certifications value for this ProfileCompanyInput.
   * 
   * @return certifications
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.CertificationInput[] getCertifications() {
    return certifications;
  }

  /**
   * Sets the certifications value for this ProfileCompanyInput.
   * 
   * @param certifications
   */
  public void setCertifications(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.CertificationInput[] certifications) {
    this.certifications = certifications;
  }

  /**
   * Gets the clientcountry value for this ProfileCompanyInput.
   * 
   * @return clientcountry
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.CountryInput getClientcountry() {
    return clientcountry;
  }

  /**
   * Sets the clientcountry value for this ProfileCompanyInput.
   * 
   * @param clientcountry
   */
  public void setClientcountry(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.CountryInput clientcountry) {
    this.clientcountry = clientcountry;
  }

  /**
   * Gets the experience value for this ProfileCompanyInput.
   * 
   * @return experience
   */
  public java.lang.String getExperience() {
    return experience;
  }

  /**
   * Sets the experience value for this ProfileCompanyInput.
   * 
   * @param experience
   */
  public void setExperience(java.lang.String experience) {
    this.experience = experience;
  }

  /**
   * Gets the kind value for this ProfileCompanyInput.
   * 
   * @return kind
   */
  public java.lang.String getKind() {
    return kind;
  }

  /**
   * Sets the kind value for this ProfileCompanyInput.
   * 
   * @param kind
   */
  public void setKind(java.lang.String kind) {
    this.kind = kind;
  }

  /**
   * Gets the languages value for this ProfileCompanyInput.
   * 
   * @return languages
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.LanguageInput[] getLanguages() {
    return languages;
  }

  /**
   * Sets the languages value for this ProfileCompanyInput.
   * 
   * @param languages
   */
  public void setLanguages(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.LanguageInput[] languages) {
    this.languages = languages;
  }

  /**
   * Gets the since value for this ProfileCompanyInput.
   * 
   * @return since
   */
  public java.lang.Integer getSince() {
    return since;
  }

  /**
   * Sets the since value for this ProfileCompanyInput.
   * 
   * @param since
   */
  public void setSince(java.lang.Integer since) {
    this.since = since;
  }

  /**
   * Gets the transnational value for this ProfileCompanyInput.
   * 
   * @return transnational
   */
  public java.lang.Boolean getTransnational() {
    return transnational;
  }

  /**
   * Sets the transnational value for this ProfileCompanyInput.
   * 
   * @param transnational
   */
  public void setTransnational(java.lang.Boolean transnational) {
    this.transnational = transnational;
  }

  /**
   * Gets the turnover value for this ProfileCompanyInput.
   * 
   * @return turnover
   */
  public java.lang.String getTurnover() {
    return turnover;
  }

  /**
   * Sets the turnover value for this ProfileCompanyInput.
   * 
   * @param turnover
   */
  public void setTurnover(java.lang.String turnover) {
    this.turnover = turnover;
  }

  private java.lang.Object __equalsCalc = null;

  public synchronized boolean equals(java.lang.Object obj) {
    if (!(obj instanceof ProfileCompanyInput))
      return false;
    ProfileCompanyInput other = (ProfileCompanyInput) obj;
    if (obj == null)
      return false;
    if (this == obj)
      return true;
    if (__equalsCalc != null) {
      return (__equalsCalc == obj);
    }
    __equalsCalc = obj;
    boolean _equals;
    _equals = true
        && ((this.certifications == null && other.getCertifications() == null) || (this.certifications != null && java.util.Arrays
            .equals(this.certifications, other.getCertifications())))
        && ((this.clientcountry == null && other.getClientcountry() == null) || (this.clientcountry != null && this.clientcountry
            .equals(other.getClientcountry())))
        && ((this.experience == null && other.getExperience() == null) || (this.experience != null && this.experience
            .equals(other.getExperience())))
        && ((this.kind == null && other.getKind() == null) || (this.kind != null && this.kind
            .equals(other.getKind())))
        && ((this.languages == null && other.getLanguages() == null) || (this.languages != null && java.util.Arrays
            .equals(this.languages, other.getLanguages())))
        && ((this.since == null && other.getSince() == null) || (this.since != null && this.since
            .equals(other.getSince())))
        && ((this.transnational == null && other.getTransnational() == null) || (this.transnational != null && this.transnational
            .equals(other.getTransnational())))
        && ((this.turnover == null && other.getTurnover() == null) || (this.turnover != null && this.turnover
            .equals(other.getTurnover())));
    __equalsCalc = null;
    return _equals;
  }

  private boolean __hashCodeCalc = false;

  public synchronized int hashCode() {
    if (__hashCodeCalc) {
      return 0;
    }
    __hashCodeCalc = true;
    int _hashCode = 1;
    if (getCertifications() != null) {
      for (int i = 0; i < java.lang.reflect.Array
          .getLength(getCertifications()); i++) {
        java.lang.Object obj = java.lang.reflect.Array.get(getCertifications(),
            i);
        if (obj != null && !obj.getClass().isArray()) {
          _hashCode += obj.hashCode();
        }
      }
    }
    if (getClientcountry() != null) {
      _hashCode += getClientcountry().hashCode();
    }
    if (getExperience() != null) {
      _hashCode += getExperience().hashCode();
    }
    if (getKind() != null) {
      _hashCode += getKind().hashCode();
    }
    if (getLanguages() != null) {
      for (int i = 0; i < java.lang.reflect.Array.getLength(getLanguages()); i++) {
        java.lang.Object obj = java.lang.reflect.Array.get(getLanguages(), i);
        if (obj != null && !obj.getClass().isArray()) {
          _hashCode += obj.hashCode();
        }
      }
    }
    if (getSince() != null) {
      _hashCode += getSince().hashCode();
    }
    if (getTransnational() != null) {
      _hashCode += getTransnational().hashCode();
    }
    if (getTurnover() != null) {
      _hashCode += getTurnover().hashCode();
    }
    __hashCodeCalc = false;
    return _hashCode;
  }

  // Type metadata
  private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
      ProfileCompanyInput.class, true);

  static {
    typeDesc
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileCompanyInput"));
    org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("certifications");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "certifications"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "certificationInput"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    elemField
        .setItemQName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "certificationInput"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("clientcountry");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "clientcountry"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "countryInput"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("experience");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "experience"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("kind");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "kind"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("languages");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "languages"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "languageInput"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    elemField
        .setItemQName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "languageInput"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("since");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "since"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "int"));
    elemField.setMinOccurs(0);
    elemField.setNillable(false);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("transnational");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "transnational"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "boolean"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("turnover");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "turnover"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
  }

  /**
   * Return type metadata object
   */
  public static org.apache.axis.description.TypeDesc getTypeDesc() {
    return typeDesc;
  }

  /**
   * Get Custom Serializer
   */
  public static org.apache.axis.encoding.Serializer getSerializer(
      java.lang.String mechType, java.lang.Class _javaType,
      javax.xml.namespace.QName _xmlType) {
    return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType,
        typeDesc);
  }

  /**
   * Get Custom Deserializer
   */
  public static org.apache.axis.encoding.Deserializer getDeserializer(
      java.lang.String mechType, java.lang.Class _javaType,
      javax.xml.namespace.QName _xmlType) {
    return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType,
        _xmlType, typeDesc);
  }

}
