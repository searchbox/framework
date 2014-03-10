/**
 * ProfileDisseminationPublishInput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService;

public class ProfileDisseminationPublishInput  implements java.io.Serializable {
    private java.lang.String sectorgroup;

    public ProfileDisseminationPublishInput() {
    }

    public ProfileDisseminationPublishInput(
           java.lang.String sectorgroup) {
           this.sectorgroup = sectorgroup;
    }


    /**
     * Gets the sectorgroup value for this ProfileDisseminationPublishInput.
     * 
     * @return sectorgroup
     */
    public java.lang.String getSectorgroup() {
        return sectorgroup;
    }


    /**
     * Sets the sectorgroup value for this ProfileDisseminationPublishInput.
     * 
     * @param sectorgroup
     */
    public void setSectorgroup(java.lang.String sectorgroup) {
        this.sectorgroup = sectorgroup;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProfileDisseminationPublishInput)) return false;
        ProfileDisseminationPublishInput other = (ProfileDisseminationPublishInput) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.sectorgroup==null && other.getSectorgroup()==null) || 
             (this.sectorgroup!=null &&
              this.sectorgroup.equals(other.getSectorgroup())));
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
        if (getSectorgroup() != null) {
            _hashCode += getSectorgroup().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProfileDisseminationPublishInput.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileDisseminationPublishInput"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sectorgroup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "sectorgroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
