/**
 * ProfileReferenceInput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService;

public class ProfileReferenceInput implements java.io.Serializable {
  private java.lang.String createdby;

  private java.lang.String internal;

  private java.lang.String type;

  public ProfileReferenceInput() {
  }

  public ProfileReferenceInput(java.lang.String createdby,
      java.lang.String internal, java.lang.String type) {
    this.createdby = createdby;
    this.internal = internal;
    this.type = type;
  }

  /**
   * Gets the createdby value for this ProfileReferenceInput.
   * 
   * @return createdby
   */
  public java.lang.String getCreatedby() {
    return createdby;
  }

  /**
   * Sets the createdby value for this ProfileReferenceInput.
   * 
   * @param createdby
   */
  public void setCreatedby(java.lang.String createdby) {
    this.createdby = createdby;
  }

  /**
   * Gets the internal value for this ProfileReferenceInput.
   * 
   * @return internal
   */
  public java.lang.String getInternal() {
    return internal;
  }

  /**
   * Sets the internal value for this ProfileReferenceInput.
   * 
   * @param internal
   */
  public void setInternal(java.lang.String internal) {
    this.internal = internal;
  }

  /**
   * Gets the type value for this ProfileReferenceInput.
   * 
   * @return type
   */
  public java.lang.String getType() {
    return type;
  }

  /**
   * Sets the type value for this ProfileReferenceInput.
   * 
   * @param type
   */
  public void setType(java.lang.String type) {
    this.type = type;
  }

  private java.lang.Object __equalsCalc = null;

  public synchronized boolean equals(java.lang.Object obj) {
    if (!(obj instanceof ProfileReferenceInput))
      return false;
    ProfileReferenceInput other = (ProfileReferenceInput) obj;
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
        && ((this.createdby == null && other.getCreatedby() == null) || (this.createdby != null && this.createdby
            .equals(other.getCreatedby())))
        && ((this.internal == null && other.getInternal() == null) || (this.internal != null && this.internal
            .equals(other.getInternal())))
        && ((this.type == null && other.getType() == null) || (this.type != null && this.type
            .equals(other.getType())));
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
    if (getCreatedby() != null) {
      _hashCode += getCreatedby().hashCode();
    }
    if (getInternal() != null) {
      _hashCode += getInternal().hashCode();
    }
    if (getType() != null) {
      _hashCode += getType().hashCode();
    }
    __hashCodeCalc = false;
    return _hashCode;
  }

  // Type metadata
  private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
      ProfileReferenceInput.class, true);

  static {
    typeDesc
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileReferenceInput"));
    org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("createdby");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "createdby"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("internal");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "internal"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("type");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "type"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
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
