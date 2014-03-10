/**
 * ProfileCooperationPartnerInput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService;

public class ProfileCooperationPartnerInput  implements java.io.Serializable {
    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationPartnershipInput[] partnershiptype;

    private java.lang.String sought;

    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationTaskInput[] task;

    public ProfileCooperationPartnerInput() {
    }

    public ProfileCooperationPartnerInput(
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationPartnershipInput[] partnershiptype,
           java.lang.String sought,
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationTaskInput[] task) {
           this.partnershiptype = partnershiptype;
           this.sought = sought;
           this.task = task;
    }


    /**
     * Gets the partnershiptype value for this ProfileCooperationPartnerInput.
     * 
     * @return partnershiptype
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationPartnershipInput[] getPartnershiptype() {
        return partnershiptype;
    }


    /**
     * Sets the partnershiptype value for this ProfileCooperationPartnerInput.
     * 
     * @param partnershiptype
     */
    public void setPartnershiptype(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationPartnershipInput[] partnershiptype) {
        this.partnershiptype = partnershiptype;
    }


    /**
     * Gets the sought value for this ProfileCooperationPartnerInput.
     * 
     * @return sought
     */
    public java.lang.String getSought() {
        return sought;
    }


    /**
     * Sets the sought value for this ProfileCooperationPartnerInput.
     * 
     * @param sought
     */
    public void setSought(java.lang.String sought) {
        this.sought = sought;
    }


    /**
     * Gets the task value for this ProfileCooperationPartnerInput.
     * 
     * @return task
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationTaskInput[] getTask() {
        return task;
    }


    /**
     * Sets the task value for this ProfileCooperationPartnerInput.
     * 
     * @param task
     */
    public void setTask(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationTaskInput[] task) {
        this.task = task;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProfileCooperationPartnerInput)) return false;
        ProfileCooperationPartnerInput other = (ProfileCooperationPartnerInput) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.partnershiptype==null && other.getPartnershiptype()==null) || 
             (this.partnershiptype!=null &&
              java.util.Arrays.equals(this.partnershiptype, other.getPartnershiptype()))) &&
            ((this.sought==null && other.getSought()==null) || 
             (this.sought!=null &&
              this.sought.equals(other.getSought()))) &&
            ((this.task==null && other.getTask()==null) || 
             (this.task!=null &&
              java.util.Arrays.equals(this.task, other.getTask())));
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
        if (getPartnershiptype() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPartnershiptype());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPartnershiptype(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSought() != null) {
            _hashCode += getSought().hashCode();
        }
        if (getTask() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getTask());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getTask(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProfileCooperationPartnerInput.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileCooperationPartnerInput"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partnershiptype");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "partnershiptype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileCooperationPartnershipInput"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileCooperationPartnershipInput"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sought");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "sought"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("task");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "task"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileCooperationTaskInput"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileCooperationTaskInput"));
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
