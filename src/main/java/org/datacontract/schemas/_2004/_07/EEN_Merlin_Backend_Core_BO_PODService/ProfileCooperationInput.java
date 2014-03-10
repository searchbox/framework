/**
 * ProfileCooperationInput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService;

public class ProfileCooperationInput  implements java.io.Serializable {
    private java.lang.String advino;

    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationIprInput[] ipr;

    private java.lang.String iprcomments;

    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationPartnerInput partner;

    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileOriginInput profileorigin;

    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationStagedevInput stagedev;

    private java.lang.String stagedevcomments;

    private java.lang.String techspec;

    public ProfileCooperationInput() {
    }

    public ProfileCooperationInput(
           java.lang.String advino,
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationIprInput[] ipr,
           java.lang.String iprcomments,
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationPartnerInput partner,
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileOriginInput profileorigin,
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationStagedevInput stagedev,
           java.lang.String stagedevcomments,
           java.lang.String techspec) {
           this.advino = advino;
           this.ipr = ipr;
           this.iprcomments = iprcomments;
           this.partner = partner;
           this.profileorigin = profileorigin;
           this.stagedev = stagedev;
           this.stagedevcomments = stagedevcomments;
           this.techspec = techspec;
    }


    /**
     * Gets the advino value for this ProfileCooperationInput.
     * 
     * @return advino
     */
    public java.lang.String getAdvino() {
        return advino;
    }


    /**
     * Sets the advino value for this ProfileCooperationInput.
     * 
     * @param advino
     */
    public void setAdvino(java.lang.String advino) {
        this.advino = advino;
    }


    /**
     * Gets the ipr value for this ProfileCooperationInput.
     * 
     * @return ipr
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationIprInput[] getIpr() {
        return ipr;
    }


    /**
     * Sets the ipr value for this ProfileCooperationInput.
     * 
     * @param ipr
     */
    public void setIpr(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationIprInput[] ipr) {
        this.ipr = ipr;
    }


    /**
     * Gets the iprcomments value for this ProfileCooperationInput.
     * 
     * @return iprcomments
     */
    public java.lang.String getIprcomments() {
        return iprcomments;
    }


    /**
     * Sets the iprcomments value for this ProfileCooperationInput.
     * 
     * @param iprcomments
     */
    public void setIprcomments(java.lang.String iprcomments) {
        this.iprcomments = iprcomments;
    }


    /**
     * Gets the partner value for this ProfileCooperationInput.
     * 
     * @return partner
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationPartnerInput getPartner() {
        return partner;
    }


    /**
     * Sets the partner value for this ProfileCooperationInput.
     * 
     * @param partner
     */
    public void setPartner(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationPartnerInput partner) {
        this.partner = partner;
    }


    /**
     * Gets the profileorigin value for this ProfileCooperationInput.
     * 
     * @return profileorigin
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileOriginInput getProfileorigin() {
        return profileorigin;
    }


    /**
     * Sets the profileorigin value for this ProfileCooperationInput.
     * 
     * @param profileorigin
     */
    public void setProfileorigin(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileOriginInput profileorigin) {
        this.profileorigin = profileorigin;
    }


    /**
     * Gets the stagedev value for this ProfileCooperationInput.
     * 
     * @return stagedev
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationStagedevInput getStagedev() {
        return stagedev;
    }


    /**
     * Sets the stagedev value for this ProfileCooperationInput.
     * 
     * @param stagedev
     */
    public void setStagedev(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationStagedevInput stagedev) {
        this.stagedev = stagedev;
    }


    /**
     * Gets the stagedevcomments value for this ProfileCooperationInput.
     * 
     * @return stagedevcomments
     */
    public java.lang.String getStagedevcomments() {
        return stagedevcomments;
    }


    /**
     * Sets the stagedevcomments value for this ProfileCooperationInput.
     * 
     * @param stagedevcomments
     */
    public void setStagedevcomments(java.lang.String stagedevcomments) {
        this.stagedevcomments = stagedevcomments;
    }


    /**
     * Gets the techspec value for this ProfileCooperationInput.
     * 
     * @return techspec
     */
    public java.lang.String getTechspec() {
        return techspec;
    }


    /**
     * Sets the techspec value for this ProfileCooperationInput.
     * 
     * @param techspec
     */
    public void setTechspec(java.lang.String techspec) {
        this.techspec = techspec;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProfileCooperationInput)) return false;
        ProfileCooperationInput other = (ProfileCooperationInput) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.advino==null && other.getAdvino()==null) || 
             (this.advino!=null &&
              this.advino.equals(other.getAdvino()))) &&
            ((this.ipr==null && other.getIpr()==null) || 
             (this.ipr!=null &&
              java.util.Arrays.equals(this.ipr, other.getIpr()))) &&
            ((this.iprcomments==null && other.getIprcomments()==null) || 
             (this.iprcomments!=null &&
              this.iprcomments.equals(other.getIprcomments()))) &&
            ((this.partner==null && other.getPartner()==null) || 
             (this.partner!=null &&
              this.partner.equals(other.getPartner()))) &&
            ((this.profileorigin==null && other.getProfileorigin()==null) || 
             (this.profileorigin!=null &&
              this.profileorigin.equals(other.getProfileorigin()))) &&
            ((this.stagedev==null && other.getStagedev()==null) || 
             (this.stagedev!=null &&
              this.stagedev.equals(other.getStagedev()))) &&
            ((this.stagedevcomments==null && other.getStagedevcomments()==null) || 
             (this.stagedevcomments!=null &&
              this.stagedevcomments.equals(other.getStagedevcomments()))) &&
            ((this.techspec==null && other.getTechspec()==null) || 
             (this.techspec!=null &&
              this.techspec.equals(other.getTechspec())));
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
        if (getAdvino() != null) {
            _hashCode += getAdvino().hashCode();
        }
        if (getIpr() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIpr());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIpr(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getIprcomments() != null) {
            _hashCode += getIprcomments().hashCode();
        }
        if (getPartner() != null) {
            _hashCode += getPartner().hashCode();
        }
        if (getProfileorigin() != null) {
            _hashCode += getProfileorigin().hashCode();
        }
        if (getStagedev() != null) {
            _hashCode += getStagedev().hashCode();
        }
        if (getStagedevcomments() != null) {
            _hashCode += getStagedevcomments().hashCode();
        }
        if (getTechspec() != null) {
            _hashCode += getTechspec().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProfileCooperationInput.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileCooperationInput"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("advino");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "advino"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ipr");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "ipr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileCooperationIprInput"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileCooperationIprInput"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("iprcomments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "iprcomments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partner");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "partner"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileCooperationPartnerInput"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profileorigin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileorigin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileOriginInput"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stagedev");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "stagedev"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileCooperationStagedevInput"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stagedevcomments");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "stagedevcomments"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("techspec");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "techspec"));
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
