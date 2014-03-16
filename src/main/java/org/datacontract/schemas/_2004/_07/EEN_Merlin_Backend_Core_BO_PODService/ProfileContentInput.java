/**
 * ProfileContentInput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService;

public class ProfileContentInput implements java.io.Serializable {
  private java.lang.String description;

  private java.lang.String summary;

  private java.lang.String title;

  public ProfileContentInput() {
  }

  public ProfileContentInput(java.lang.String description,
      java.lang.String summary, java.lang.String title) {
    this.description = description;
    this.summary = summary;
    this.title = title;
  }

  /**
   * Gets the description value for this ProfileContentInput.
   * 
   * @return description
   */
  public java.lang.String getDescription() {
    return description;
  }

  /**
   * Sets the description value for this ProfileContentInput.
   * 
   * @param description
   */
  public void setDescription(java.lang.String description) {
    this.description = description;
  }

  /**
   * Gets the summary value for this ProfileContentInput.
   * 
   * @return summary
   */
  public java.lang.String getSummary() {
    return summary;
  }

  /**
   * Sets the summary value for this ProfileContentInput.
   * 
   * @param summary
   */
  public void setSummary(java.lang.String summary) {
    this.summary = summary;
  }

  /**
   * Gets the title value for this ProfileContentInput.
   * 
   * @return title
   */
  public java.lang.String getTitle() {
    return title;
  }

  /**
   * Sets the title value for this ProfileContentInput.
   * 
   * @param title
   */
  public void setTitle(java.lang.String title) {
    this.title = title;
  }

  private java.lang.Object __equalsCalc = null;

  public synchronized boolean equals(java.lang.Object obj) {
    if (!(obj instanceof ProfileContentInput))
      return false;
    ProfileContentInput other = (ProfileContentInput) obj;
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
        && ((this.description == null && other.getDescription() == null) || (this.description != null && this.description
            .equals(other.getDescription())))
        && ((this.summary == null && other.getSummary() == null) || (this.summary != null && this.summary
            .equals(other.getSummary())))
        && ((this.title == null && other.getTitle() == null) || (this.title != null && this.title
            .equals(other.getTitle())));
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
    if (getDescription() != null) {
      _hashCode += getDescription().hashCode();
    }
    if (getSummary() != null) {
      _hashCode += getSummary().hashCode();
    }
    if (getTitle() != null) {
      _hashCode += getTitle().hashCode();
    }
    __hashCodeCalc = false;
    return _hashCode;
  }

  // Type metadata
  private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
      ProfileContentInput.class, true);

  static {
    typeDesc
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileContentInput"));
    org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("description");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "description"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("summary");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "summary"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "string"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("title");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "title"));
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
