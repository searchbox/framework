/**
 * ProfileContact.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService;

public class ProfileContact implements java.io.Serializable {
  private java.lang.String consortium;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContactConsortiumcountry consortiumcountry;

  private java.lang.String email;

  private java.lang.String fullname;

  private java.lang.String organization;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContactOrganizationcountry organizationcountry;

  private java.lang.String partnerid;

  private java.lang.String phone;

  public ProfileContact() {
  }

  public ProfileContact(
      java.lang.String consortium,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContactConsortiumcountry consortiumcountry,
      java.lang.String email,
      java.lang.String fullname,
      java.lang.String organization,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContactOrganizationcountry organizationcountry,
      java.lang.String partnerid, java.lang.String phone) {
    this.consortium = consortium;
    this.consortiumcountry = consortiumcountry;
    this.email = email;
    this.fullname = fullname;
    this.organization = organization;
    this.organizationcountry = organizationcountry;
    this.partnerid = partnerid;
    this.phone = phone;
  }

  /**
   * Gets the consortium value for this ProfileContact.
   * 
   * @return consortium
   */
  public java.lang.String getConsortium() {
    return consortium;
  }

  /**
   * Sets the consortium value for this ProfileContact.
   * 
   * @param consortium
   */
  public void setConsortium(java.lang.String consortium) {
    this.consortium = consortium;
  }

  /**
   * Gets the consortiumcountry value for this ProfileContact.
   * 
   * @return consortiumcountry
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContactConsortiumcountry getConsortiumcountry() {
    return consortiumcountry;
  }

  /**
   * Sets the consortiumcountry value for this ProfileContact.
   * 
   * @param consortiumcountry
   */
  public void setConsortiumcountry(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContactConsortiumcountry consortiumcountry) {
    this.consortiumcountry = consortiumcountry;
  }

  /**
   * Gets the email value for this ProfileContact.
   * 
   * @return email
   */
  public java.lang.String getEmail() {
    return email;
  }

  /**
   * Sets the email value for this ProfileContact.
   * 
   * @param email
   */
  public void setEmail(java.lang.String email) {
    this.email = email;
  }

  /**
   * Gets the fullname value for this ProfileContact.
   * 
   * @return fullname
   */
  public java.lang.String getFullname() {
    return fullname;
  }

  /**
   * Sets the fullname value for this ProfileContact.
   * 
   * @param fullname
   */
  public void setFullname(java.lang.String fullname) {
    this.fullname = fullname;
  }

  /**
   * Gets the organization value for this ProfileContact.
   * 
   * @return organization
   */
  public java.lang.String getOrganization() {
    return organization;
  }

  /**
   * Sets the organization value for this ProfileContact.
   * 
   * @param organization
   */
  public void setOrganization(java.lang.String organization) {
    this.organization = organization;
  }

  /**
   * Gets the organizationcountry value for this ProfileContact.
   * 
   * @return organizationcountry
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContactOrganizationcountry getOrganizationcountry() {
    return organizationcountry;
  }

  /**
   * Sets the organizationcountry value for this ProfileContact.
   * 
   * @param organizationcountry
   */
  public void setOrganizationcountry(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContactOrganizationcountry organizationcountry) {
    this.organizationcountry = organizationcountry;
  }

  /**
   * Gets the partnerid value for this ProfileContact.
   * 
   * @return partnerid
   */
  public java.lang.String getPartnerid() {
    return partnerid;
  }

  /**
   * Sets the partnerid value for this ProfileContact.
   * 
   * @param partnerid
   */
  public void setPartnerid(java.lang.String partnerid) {
    this.partnerid = partnerid;
  }

  /**
   * Gets the phone value for this ProfileContact.
   * 
   * @return phone
   */
  public java.lang.String getPhone() {
    return phone;
  }

  /**
   * Sets the phone value for this ProfileContact.
   * 
   * @param phone
   */
  public void setPhone(java.lang.String phone) {
    this.phone = phone;
  }

  private java.lang.Object __equalsCalc = null;

  public synchronized boolean equals(java.lang.Object obj) {
    if (!(obj instanceof ProfileContact))
      return false;
    ProfileContact other = (ProfileContact) obj;
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
        && ((this.consortium == null && other.getConsortium() == null) || (this.consortium != null && this.consortium
            .equals(other.getConsortium())))
        && ((this.consortiumcountry == null && other.getConsortiumcountry() == null) || (this.consortiumcountry != null && this.consortiumcountry
            .equals(other.getConsortiumcountry())))
        && ((this.email == null && other.getEmail() == null) || (this.email != null && this.email
            .equals(other.getEmail())))
        && ((this.fullname == null && other.getFullname() == null) || (this.fullname != null && this.fullname
            .equals(other.getFullname())))
        && ((this.organization == null && other.getOrganization() == null) || (this.organization != null && this.organization
            .equals(other.getOrganization())))
        && ((this.organizationcountry == null && other.getOrganizationcountry() == null) || (this.organizationcountry != null && this.organizationcountry
            .equals(other.getOrganizationcountry())))
        && ((this.partnerid == null && other.getPartnerid() == null) || (this.partnerid != null && this.partnerid
            .equals(other.getPartnerid())))
        && ((this.phone == null && other.getPhone() == null) || (this.phone != null && this.phone
            .equals(other.getPhone())));
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
    if (getConsortium() != null) {
      _hashCode += getConsortium().hashCode();
    }
    if (getConsortiumcountry() != null) {
      _hashCode += getConsortiumcountry().hashCode();
    }
    if (getEmail() != null) {
      _hashCode += getEmail().hashCode();
    }
    if (getFullname() != null) {
      _hashCode += getFullname().hashCode();
    }
    if (getOrganization() != null) {
      _hashCode += getOrganization().hashCode();
    }
    if (getOrganizationcountry() != null) {
      _hashCode += getOrganizationcountry().hashCode();
    }
    if (getPartnerid() != null) {
      _hashCode += getPartnerid().hashCode();
    }
    if (getPhone() != null) {
      _hashCode += getPhone().hashCode();
    }
    __hashCodeCalc = false;
    return _hashCode;
  }

  // Type metadata
  private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
      ProfileContact.class, true);

  static {
    typeDesc
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileContact"));
    org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("consortium");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "consortium"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("consortiumcountry");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "consortiumcountry"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileContactConsortiumcountry"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("email");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "email"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("fullname");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "fullname"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("organization");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "organization"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("organizationcountry");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "organizationcountry"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileContactOrganizationcountry"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("partnerid");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "partnerid"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("phone");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "phone"));
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
