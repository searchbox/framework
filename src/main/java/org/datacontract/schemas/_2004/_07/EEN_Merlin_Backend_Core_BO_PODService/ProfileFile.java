/**
 * ProfileFile.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService;

public class ProfileFile implements java.io.Serializable {
  private java.lang.String caption;

  private byte[] data;

  private java.lang.String mimetype;

  private java.lang.String name;

  public ProfileFile() {
  }

  public ProfileFile(java.lang.String caption, byte[] data,
      java.lang.String mimetype, java.lang.String name) {
    this.caption = caption;
    this.data = data;
    this.mimetype = mimetype;
    this.name = name;
  }

  /**
   * Gets the caption value for this ProfileFile.
   * 
   * @return caption
   */
  public java.lang.String getCaption() {
    return caption;
  }

  /**
   * Sets the caption value for this ProfileFile.
   * 
   * @param caption
   */
  public void setCaption(java.lang.String caption) {
    this.caption = caption;
  }

  /**
   * Gets the data value for this ProfileFile.
   * 
   * @return data
   */
  public byte[] getData() {
    return data;
  }

  /**
   * Sets the data value for this ProfileFile.
   * 
   * @param data
   */
  public void setData(byte[] data) {
    this.data = data;
  }

  /**
   * Gets the mimetype value for this ProfileFile.
   * 
   * @return mimetype
   */
  public java.lang.String getMimetype() {
    return mimetype;
  }

  /**
   * Sets the mimetype value for this ProfileFile.
   * 
   * @param mimetype
   */
  public void setMimetype(java.lang.String mimetype) {
    this.mimetype = mimetype;
  }

  /**
   * Gets the name value for this ProfileFile.
   * 
   * @return name
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * Sets the name value for this ProfileFile.
   * 
   * @param name
   */
  public void setName(java.lang.String name) {
    this.name = name;
  }

  private java.lang.Object __equalsCalc = null;

  public synchronized boolean equals(java.lang.Object obj) {
    if (!(obj instanceof ProfileFile))
      return false;
    ProfileFile other = (ProfileFile) obj;
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
        && ((this.caption == null && other.getCaption() == null) || (this.caption != null && this.caption
            .equals(other.getCaption())))
        && ((this.data == null && other.getData() == null) || (this.data != null && java.util.Arrays
            .equals(this.data, other.getData())))
        && ((this.mimetype == null && other.getMimetype() == null) || (this.mimetype != null && this.mimetype
            .equals(other.getMimetype())))
        && ((this.name == null && other.getName() == null) || (this.name != null && this.name
            .equals(other.getName())));
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
    if (getCaption() != null) {
      _hashCode += getCaption().hashCode();
    }
    if (getData() != null) {
      for (int i = 0; i < java.lang.reflect.Array.getLength(getData()); i++) {
        java.lang.Object obj = java.lang.reflect.Array.get(getData(), i);
        if (obj != null && !obj.getClass().isArray()) {
          _hashCode += obj.hashCode();
        }
      }
    }
    if (getMimetype() != null) {
      _hashCode += getMimetype().hashCode();
    }
    if (getName() != null) {
      _hashCode += getName().hashCode();
    }
    __hashCodeCalc = false;
    return _hashCode;
  }

  // Type metadata
  private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
      ProfileFile.class, true);

  static {
    typeDesc
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileFile"));
    org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("caption");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "caption"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("data");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "data"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "base64Binary"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("mimetype");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "mimetype"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("name");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "name"));
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
