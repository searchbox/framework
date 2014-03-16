/**
 * ProfileKeyword.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService;

public class ProfileKeyword implements java.io.Serializable {
  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Market[] markets;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Nace[] naces;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Technology[] technologies;

  public ProfileKeyword() {
  }

  public ProfileKeyword(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Market[] markets,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Nace[] naces,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Technology[] technologies) {
    this.markets = markets;
    this.naces = naces;
    this.technologies = technologies;
  }

  /**
   * Gets the markets value for this ProfileKeyword.
   * 
   * @return markets
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Market[] getMarkets() {
    return markets;
  }

  /**
   * Sets the markets value for this ProfileKeyword.
   * 
   * @param markets
   */
  public void setMarkets(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Market[] markets) {
    this.markets = markets;
  }

  /**
   * Gets the naces value for this ProfileKeyword.
   * 
   * @return naces
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Nace[] getNaces() {
    return naces;
  }

  /**
   * Sets the naces value for this ProfileKeyword.
   * 
   * @param naces
   */
  public void setNaces(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Nace[] naces) {
    this.naces = naces;
  }

  /**
   * Gets the technologies value for this ProfileKeyword.
   * 
   * @return technologies
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Technology[] getTechnologies() {
    return technologies;
  }

  /**
   * Sets the technologies value for this ProfileKeyword.
   * 
   * @param technologies
   */
  public void setTechnologies(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Technology[] technologies) {
    this.technologies = technologies;
  }

  private java.lang.Object __equalsCalc = null;

  public synchronized boolean equals(java.lang.Object obj) {
    if (!(obj instanceof ProfileKeyword))
      return false;
    ProfileKeyword other = (ProfileKeyword) obj;
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
        && ((this.markets == null && other.getMarkets() == null) || (this.markets != null && java.util.Arrays
            .equals(this.markets, other.getMarkets())))
        && ((this.naces == null && other.getNaces() == null) || (this.naces != null && java.util.Arrays
            .equals(this.naces, other.getNaces())))
        && ((this.technologies == null && other.getTechnologies() == null) || (this.technologies != null && java.util.Arrays
            .equals(this.technologies, other.getTechnologies())));
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
    if (getMarkets() != null) {
      for (int i = 0; i < java.lang.reflect.Array.getLength(getMarkets()); i++) {
        java.lang.Object obj = java.lang.reflect.Array.get(getMarkets(), i);
        if (obj != null && !obj.getClass().isArray()) {
          _hashCode += obj.hashCode();
        }
      }
    }
    if (getNaces() != null) {
      for (int i = 0; i < java.lang.reflect.Array.getLength(getNaces()); i++) {
        java.lang.Object obj = java.lang.reflect.Array.get(getNaces(), i);
        if (obj != null && !obj.getClass().isArray()) {
          _hashCode += obj.hashCode();
        }
      }
    }
    if (getTechnologies() != null) {
      for (int i = 0; i < java.lang.reflect.Array.getLength(getTechnologies()); i++) {
        java.lang.Object obj = java.lang.reflect.Array
            .get(getTechnologies(), i);
        if (obj != null && !obj.getClass().isArray()) {
          _hashCode += obj.hashCode();
        }
      }
    }
    __hashCodeCalc = false;
    return _hashCode;
  }

  // Type metadata
  private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
      ProfileKeyword.class, true);

  static {
    typeDesc
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileKeyword"));
    org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("markets");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "markets"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "market"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    elemField
        .setItemQName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "market"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("naces");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "naces"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "nace"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    elemField
        .setItemQName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "nace"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("technologies");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "technologies"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "technology"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    elemField
        .setItemQName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "technology"));
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
