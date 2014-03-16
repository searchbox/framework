/**
 * ProfileContactInput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService;

public class ProfileContactInput implements java.io.Serializable {
  private java.lang.String partnerid;

  private java.lang.String responsible;

  public ProfileContactInput() {
  }

  public ProfileContactInput(java.lang.String partnerid,
      java.lang.String responsible) {
    this.partnerid = partnerid;
    this.responsible = responsible;
  }

  /**
   * Gets the partnerid value for this ProfileContactInput.
   * 
   * @return partnerid
   */
  public java.lang.String getPartnerid() {
    return partnerid;
  }

  /**
   * Sets the partnerid value for this ProfileContactInput.
   * 
   * @param partnerid
   */
  public void setPartnerid(java.lang.String partnerid) {
    this.partnerid = partnerid;
  }

  /**
   * Gets the responsible value for this ProfileContactInput.
   * 
   * @return responsible
   */
  public java.lang.String getResponsible() {
    return responsible;
  }

  /**
   * Sets the responsible value for this ProfileContactInput.
   * 
   * @param responsible
   */
  public void setResponsible(java.lang.String responsible) {
    this.responsible = responsible;
  }

  private java.lang.Object __equalsCalc = null;

  public synchronized boolean equals(java.lang.Object obj) {
    if (!(obj instanceof ProfileContactInput))
      return false;
    ProfileContactInput other = (ProfileContactInput) obj;
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
        && ((this.partnerid == null && other.getPartnerid() == null) || (this.partnerid != null && this.partnerid
            .equals(other.getPartnerid())))
        && ((this.responsible == null && other.getResponsible() == null) || (this.responsible != null && this.responsible
            .equals(other.getResponsible())));
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
    if (getPartnerid() != null) {
      _hashCode += getPartnerid().hashCode();
    }
    if (getResponsible() != null) {
      _hashCode += getResponsible().hashCode();
    }
    __hashCodeCalc = false;
    return _hashCode;
  }

  // Type metadata
  private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
      ProfileContactInput.class, true);

  static {
    typeDesc
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileContactInput"));
    org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("partnerid");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "partnerid"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("responsible");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "responsible"));
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
