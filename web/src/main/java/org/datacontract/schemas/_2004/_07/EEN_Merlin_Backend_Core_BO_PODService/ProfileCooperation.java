/**
 * ProfileCooperation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService;

public class ProfileCooperation implements java.io.Serializable {
  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationExploitations exploitations;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationIpr ipr;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationPartner partner;

  private java.lang.String plusvalue;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationStagedev stagedev;

  public ProfileCooperation() {
  }

  public ProfileCooperation(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationExploitations exploitations,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationIpr ipr,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationPartner partner,
      java.lang.String plusvalue,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationStagedev stagedev) {
    this.exploitations = exploitations;
    this.ipr = ipr;
    this.partner = partner;
    this.plusvalue = plusvalue;
    this.stagedev = stagedev;
  }

  /**
   * Gets the exploitations value for this ProfileCooperation.
   * 
   * @return exploitations
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationExploitations getExploitations() {
    return exploitations;
  }

  /**
   * Sets the exploitations value for this ProfileCooperation.
   * 
   * @param exploitations
   */
  public void setExploitations(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationExploitations exploitations) {
    this.exploitations = exploitations;
  }

  /**
   * Gets the ipr value for this ProfileCooperation.
   * 
   * @return ipr
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationIpr getIpr() {
    return ipr;
  }

  /**
   * Sets the ipr value for this ProfileCooperation.
   * 
   * @param ipr
   */
  public void setIpr(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationIpr ipr) {
    this.ipr = ipr;
  }

  /**
   * Gets the partner value for this ProfileCooperation.
   * 
   * @return partner
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationPartner getPartner() {
    return partner;
  }

  /**
   * Sets the partner value for this ProfileCooperation.
   * 
   * @param partner
   */
  public void setPartner(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationPartner partner) {
    this.partner = partner;
  }

  /**
   * Gets the plusvalue value for this ProfileCooperation.
   * 
   * @return plusvalue
   */
  public java.lang.String getPlusvalue() {
    return plusvalue;
  }

  /**
   * Sets the plusvalue value for this ProfileCooperation.
   * 
   * @param plusvalue
   */
  public void setPlusvalue(java.lang.String plusvalue) {
    this.plusvalue = plusvalue;
  }

  /**
   * Gets the stagedev value for this ProfileCooperation.
   * 
   * @return stagedev
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationStagedev getStagedev() {
    return stagedev;
  }

  /**
   * Sets the stagedev value for this ProfileCooperation.
   * 
   * @param stagedev
   */
  public void setStagedev(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationStagedev stagedev) {
    this.stagedev = stagedev;
  }

  private java.lang.Object __equalsCalc = null;

  public synchronized boolean equals(java.lang.Object obj) {
    if (!(obj instanceof ProfileCooperation))
      return false;
    ProfileCooperation other = (ProfileCooperation) obj;
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
        && ((this.exploitations == null && other.getExploitations() == null) || (this.exploitations != null && this.exploitations
            .equals(other.getExploitations())))
        && ((this.ipr == null && other.getIpr() == null) || (this.ipr != null && this.ipr
            .equals(other.getIpr())))
        && ((this.partner == null && other.getPartner() == null) || (this.partner != null && this.partner
            .equals(other.getPartner())))
        && ((this.plusvalue == null && other.getPlusvalue() == null) || (this.plusvalue != null && this.plusvalue
            .equals(other.getPlusvalue())))
        && ((this.stagedev == null && other.getStagedev() == null) || (this.stagedev != null && this.stagedev
            .equals(other.getStagedev())));
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
    if (getExploitations() != null) {
      _hashCode += getExploitations().hashCode();
    }
    if (getIpr() != null) {
      _hashCode += getIpr().hashCode();
    }
    if (getPartner() != null) {
      _hashCode += getPartner().hashCode();
    }
    if (getPlusvalue() != null) {
      _hashCode += getPlusvalue().hashCode();
    }
    if (getStagedev() != null) {
      _hashCode += getStagedev().hashCode();
    }
    __hashCodeCalc = false;
    return _hashCode;
  }

  // Type metadata
  private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
      ProfileCooperation.class, true);

  static {
    typeDesc
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileCooperation"));
    org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("exploitations");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "exploitations"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileCooperationExploitations"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("ipr");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "ipr"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileCooperationIpr"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("partner");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "partner"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileCooperationPartner"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("plusvalue");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "plusvalue"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("stagedev");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "stagedev"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileCooperationStagedev"));
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
