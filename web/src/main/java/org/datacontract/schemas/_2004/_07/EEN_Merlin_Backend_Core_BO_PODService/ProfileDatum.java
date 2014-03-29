/**
 * ProfileDatum.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService;

public class ProfileDatum implements java.io.Serializable {
  private java.util.Calendar deadline;

  private java.util.Calendar submit;

  private java.util.Calendar update;

  public ProfileDatum() {
  }

  public ProfileDatum(java.util.Calendar deadline, java.util.Calendar submit,
      java.util.Calendar update) {
    this.deadline = deadline;
    this.submit = submit;
    this.update = update;
  }

  /**
   * Gets the deadline value for this ProfileDatum.
   * 
   * @return deadline
   */
  public java.util.Calendar getDeadline() {
    return deadline;
  }

  /**
   * Sets the deadline value for this ProfileDatum.
   * 
   * @param deadline
   */
  public void setDeadline(java.util.Calendar deadline) {
    this.deadline = deadline;
  }

  /**
   * Gets the submit value for this ProfileDatum.
   * 
   * @return submit
   */
  public java.util.Calendar getSubmit() {
    return submit;
  }

  /**
   * Sets the submit value for this ProfileDatum.
   * 
   * @param submit
   */
  public void setSubmit(java.util.Calendar submit) {
    this.submit = submit;
  }

  /**
   * Gets the update value for this ProfileDatum.
   * 
   * @return update
   */
  public java.util.Calendar getUpdate() {
    return update;
  }

  /**
   * Sets the update value for this ProfileDatum.
   * 
   * @param update
   */
  public void setUpdate(java.util.Calendar update) {
    this.update = update;
  }

  private java.lang.Object __equalsCalc = null;

  public synchronized boolean equals(java.lang.Object obj) {
    if (!(obj instanceof ProfileDatum))
      return false;
    ProfileDatum other = (ProfileDatum) obj;
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
        && ((this.deadline == null && other.getDeadline() == null) || (this.deadline != null && this.deadline
            .equals(other.getDeadline())))
        && ((this.submit == null && other.getSubmit() == null) || (this.submit != null && this.submit
            .equals(other.getSubmit())))
        && ((this.update == null && other.getUpdate() == null) || (this.update != null && this.update
            .equals(other.getUpdate())));
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
    if (getDeadline() != null) {
      _hashCode += getDeadline().hashCode();
    }
    if (getSubmit() != null) {
      _hashCode += getSubmit().hashCode();
    }
    if (getUpdate() != null) {
      _hashCode += getUpdate().hashCode();
    }
    __hashCodeCalc = false;
    return _hashCode;
  }

  // Type metadata
  private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
      ProfileDatum.class, true);

  static {
    typeDesc
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileDatum"));
    org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("deadline");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "deadline"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "dateTime"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("submit");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "submit"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "dateTime"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("update");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "update"));
    elemField.setXmlType(new javax.xml.namespace.QName(
        "http://www.w3.org/2001/XMLSchema", "dateTime"));
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
