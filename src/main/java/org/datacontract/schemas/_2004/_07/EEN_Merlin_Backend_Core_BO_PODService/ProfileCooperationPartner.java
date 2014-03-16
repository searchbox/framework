/**
 * ProfileCooperationPartner.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService;

public class ProfileCooperationPartner implements java.io.Serializable {
  private java.lang.String area;

  private java.lang.String sought;

  private java.lang.String task;

  public ProfileCooperationPartner() {
  }

  public ProfileCooperationPartner(java.lang.String area,
      java.lang.String sought, java.lang.String task) {
    this.area = area;
    this.sought = sought;
    this.task = task;
  }

  /**
   * Gets the area value for this ProfileCooperationPartner.
   * 
   * @return area
   */
  public java.lang.String getArea() {
    return area;
  }

  /**
   * Sets the area value for this ProfileCooperationPartner.
   * 
   * @param area
   */
  public void setArea(java.lang.String area) {
    this.area = area;
  }

  /**
   * Gets the sought value for this ProfileCooperationPartner.
   * 
   * @return sought
   */
  public java.lang.String getSought() {
    return sought;
  }

  /**
   * Sets the sought value for this ProfileCooperationPartner.
   * 
   * @param sought
   */
  public void setSought(java.lang.String sought) {
    this.sought = sought;
  }

  /**
   * Gets the task value for this ProfileCooperationPartner.
   * 
   * @return task
   */
  public java.lang.String getTask() {
    return task;
  }

  /**
   * Sets the task value for this ProfileCooperationPartner.
   * 
   * @param task
   */
  public void setTask(java.lang.String task) {
    this.task = task;
  }

  private java.lang.Object __equalsCalc = null;

  public synchronized boolean equals(java.lang.Object obj) {
    if (!(obj instanceof ProfileCooperationPartner))
      return false;
    ProfileCooperationPartner other = (ProfileCooperationPartner) obj;
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
        && ((this.area == null && other.getArea() == null) || (this.area != null && this.area
            .equals(other.getArea())))
        && ((this.sought == null && other.getSought() == null) || (this.sought != null && this.sought
            .equals(other.getSought())))
        && ((this.task == null && other.getTask() == null) || (this.task != null && this.task
            .equals(other.getTask())));
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
    if (getArea() != null) {
      _hashCode += getArea().hashCode();
    }
    if (getSought() != null) {
      _hashCode += getSought().hashCode();
    }
    if (getTask() != null) {
      _hashCode += getTask().hashCode();
    }
    __hashCodeCalc = false;
    return _hashCode;
  }

  // Type metadata
  private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
      ProfileCooperationPartner.class, true);

  static {
    typeDesc
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileCooperationPartner"));
    org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("area");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "area"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("sought");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "sought"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("task");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "task"));
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
