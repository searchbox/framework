/**
 * ProfileCompany.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService;

public class ProfileCompany  implements java.io.Serializable {
    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Certification[] certifications;

    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCompanyCountry country;

    private java.lang.String experience;

    private java.lang.String kind;

    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Language[] languages;

    private java.lang.String since;

    private java.lang.Boolean transnational;

    private java.lang.String turnover;

    public ProfileCompany() {
    }

    public ProfileCompany(
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Certification[] certifications,
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCompanyCountry country,
           java.lang.String experience,
           java.lang.String kind,
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Language[] languages,
           java.lang.String since,
           java.lang.Boolean transnational,
           java.lang.String turnover) {
           this.certifications = certifications;
           this.country = country;
           this.experience = experience;
           this.kind = kind;
           this.languages = languages;
           this.since = since;
           this.transnational = transnational;
           this.turnover = turnover;
    }


    /**
     * Gets the certifications value for this ProfileCompany.
     * 
     * @return certifications
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Certification[] getCertifications() {
        return certifications;
    }


    /**
     * Sets the certifications value for this ProfileCompany.
     * 
     * @param certifications
     */
    public void setCertifications(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Certification[] certifications) {
        this.certifications = certifications;
    }


    /**
     * Gets the country value for this ProfileCompany.
     * 
     * @return country
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCompanyCountry getCountry() {
        return country;
    }


    /**
     * Sets the country value for this ProfileCompany.
     * 
     * @param country
     */
    public void setCountry(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCompanyCountry country) {
        this.country = country;
    }


    /**
     * Gets the experience value for this ProfileCompany.
     * 
     * @return experience
     */
    public java.lang.String getExperience() {
        return experience;
    }


    /**
     * Sets the experience value for this ProfileCompany.
     * 
     * @param experience
     */
    public void setExperience(java.lang.String experience) {
        this.experience = experience;
    }


    /**
     * Gets the kind value for this ProfileCompany.
     * 
     * @return kind
     */
    public java.lang.String getKind() {
        return kind;
    }


    /**
     * Sets the kind value for this ProfileCompany.
     * 
     * @param kind
     */
    public void setKind(java.lang.String kind) {
        this.kind = kind;
    }


    /**
     * Gets the languages value for this ProfileCompany.
     * 
     * @return languages
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Language[] getLanguages() {
        return languages;
    }


    /**
     * Sets the languages value for this ProfileCompany.
     * 
     * @param languages
     */
    public void setLanguages(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.Language[] languages) {
        this.languages = languages;
    }


    /**
     * Gets the since value for this ProfileCompany.
     * 
     * @return since
     */
    public java.lang.String getSince() {
        return since;
    }


    /**
     * Sets the since value for this ProfileCompany.
     * 
     * @param since
     */
    public void setSince(java.lang.String since) {
        this.since = since;
    }


    /**
     * Gets the transnational value for this ProfileCompany.
     * 
     * @return transnational
     */
    public java.lang.Boolean getTransnational() {
        return transnational;
    }


    /**
     * Sets the transnational value for this ProfileCompany.
     * 
     * @param transnational
     */
    public void setTransnational(java.lang.Boolean transnational) {
        this.transnational = transnational;
    }


    /**
     * Gets the turnover value for this ProfileCompany.
     * 
     * @return turnover
     */
    public java.lang.String getTurnover() {
        return turnover;
    }


    /**
     * Sets the turnover value for this ProfileCompany.
     * 
     * @param turnover
     */
    public void setTurnover(java.lang.String turnover) {
        this.turnover = turnover;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProfileCompany)) return false;
        ProfileCompany other = (ProfileCompany) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.certifications==null && other.getCertifications()==null) || 
             (this.certifications!=null &&
              java.util.Arrays.equals(this.certifications, other.getCertifications()))) &&
            ((this.country==null && other.getCountry()==null) || 
             (this.country!=null &&
              this.country.equals(other.getCountry()))) &&
            ((this.experience==null && other.getExperience()==null) || 
             (this.experience!=null &&
              this.experience.equals(other.getExperience()))) &&
            ((this.kind==null && other.getKind()==null) || 
             (this.kind!=null &&
              this.kind.equals(other.getKind()))) &&
            ((this.languages==null && other.getLanguages()==null) || 
             (this.languages!=null &&
              java.util.Arrays.equals(this.languages, other.getLanguages()))) &&
            ((this.since==null && other.getSince()==null) || 
             (this.since!=null &&
              this.since.equals(other.getSince()))) &&
            ((this.transnational==null && other.getTransnational()==null) || 
             (this.transnational!=null &&
              this.transnational.equals(other.getTransnational()))) &&
            ((this.turnover==null && other.getTurnover()==null) || 
             (this.turnover!=null &&
              this.turnover.equals(other.getTurnover())));
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
        if (getCertifications() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCertifications());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCertifications(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getCountry() != null) {
            _hashCode += getCountry().hashCode();
        }
        if (getExperience() != null) {
            _hashCode += getExperience().hashCode();
        }
        if (getKind() != null) {
            _hashCode += getKind().hashCode();
        }
        if (getLanguages() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getLanguages());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getLanguages(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSince() != null) {
            _hashCode += getSince().hashCode();
        }
        if (getTransnational() != null) {
            _hashCode += getTransnational().hashCode();
        }
        if (getTurnover() != null) {
            _hashCode += getTurnover().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProfileCompany.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileCompany"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("certifications");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "certifications"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "certification"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "certification"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("country");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "country"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileCompanyCountry"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("experience");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "experience"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("kind");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "kind"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("languages");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "languages"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "language"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "language"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("since");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "since"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transnational");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "transnational"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("turnover");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "turnover"));
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
