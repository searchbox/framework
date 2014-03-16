/**
 * ProfileDissemination.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService;

public class ProfileDissemination implements java.io.Serializable {
  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDisseminationExport export;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Country[] preferred;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDisseminationPublish publish;

  public ProfileDissemination() {
  }

  public ProfileDissemination(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDisseminationExport export,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Country[] preferred,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDisseminationPublish publish) {
    this.export = export;
    this.preferred = preferred;
    this.publish = publish;
  }

  /**
   * Gets the export value for this ProfileDissemination.
   * 
   * @return export
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDisseminationExport getExport() {
    return export;
  }

  /**
   * Sets the export value for this ProfileDissemination.
   * 
   * @param export
   */
  public void setExport(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDisseminationExport export) {
    this.export = export;
  }

  /**
   * Gets the preferred value for this ProfileDissemination.
   * 
   * @return preferred
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Country[] getPreferred() {
    return preferred;
  }

  /**
   * Sets the preferred value for this ProfileDissemination.
   * 
   * @param preferred
   */
  public void setPreferred(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Country[] preferred) {
    this.preferred = preferred;
  }

  /**
   * Gets the publish value for this ProfileDissemination.
   * 
   * @return publish
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDisseminationPublish getPublish() {
    return publish;
  }

  /**
   * Sets the publish value for this ProfileDissemination.
   * 
   * @param publish
   */
  public void setPublish(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDisseminationPublish publish) {
    this.publish = publish;
  }

  private java.lang.Object __equalsCalc = null;

  public synchronized boolean equals(java.lang.Object obj) {
    if (!(obj instanceof ProfileDissemination))
      return false;
    ProfileDissemination other = (ProfileDissemination) obj;
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
        && ((this.export == null && other.getExport() == null) || (this.export != null && this.export
            .equals(other.getExport())))
        && ((this.preferred == null && other.getPreferred() == null) || (this.preferred != null && java.util.Arrays
            .equals(this.preferred, other.getPreferred())))
        && ((this.publish == null && other.getPublish() == null) || (this.publish != null && this.publish
            .equals(other.getPublish())));
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
    if (getExport() != null) {
      _hashCode += getExport().hashCode();
    }
    if (getPreferred() != null) {
      for (int i = 0; i < java.lang.reflect.Array.getLength(getPreferred()); i++) {
        java.lang.Object obj = java.lang.reflect.Array.get(getPreferred(), i);
        if (obj != null && !obj.getClass().isArray()) {
          _hashCode += obj.hashCode();
        }
      }
    }
    if (getPublish() != null) {
      _hashCode += getPublish().hashCode();
    }
    __hashCodeCalc = false;
    return _hashCode;
  }

  // Type metadata
  private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
      ProfileDissemination.class, true);

  static {
    typeDesc
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileDissemination"));
    org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("export");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "export"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileDisseminationExport"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("preferred");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "preferred"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "country"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    elemField
        .setItemQName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "country"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("publish");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "publish"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileDisseminationPublish"));
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
