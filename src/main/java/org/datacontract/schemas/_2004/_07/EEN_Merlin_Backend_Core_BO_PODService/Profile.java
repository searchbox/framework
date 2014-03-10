/**
 * Profile.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService;

public class Profile  implements java.io.Serializable {
    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCompany company;

    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContact contact;

    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContent content;

    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperation cooperation;

    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCustomprops customprops;

    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDatum datum;

    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDissemination dissemination;

    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileEoi eoi;

    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileFile[] files;

    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileKeyword keyword;

    private java.lang.String[] partnerships;

    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileProgram program;

    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileReference reference;

    public Profile() {
    }

    public Profile(
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCompany company,
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContact contact,
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContent content,
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperation cooperation,
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCustomprops customprops,
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDatum datum,
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDissemination dissemination,
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileEoi eoi,
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileFile[] files,
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileKeyword keyword,
           java.lang.String[] partnerships,
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileProgram program,
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileReference reference) {
           this.company = company;
           this.contact = contact;
           this.content = content;
           this.cooperation = cooperation;
           this.customprops = customprops;
           this.datum = datum;
           this.dissemination = dissemination;
           this.eoi = eoi;
           this.files = files;
           this.keyword = keyword;
           this.partnerships = partnerships;
           this.program = program;
           this.reference = reference;
    }


    /**
     * Gets the company value for this Profile.
     * 
     * @return company
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCompany getCompany() {
        return company;
    }


    /**
     * Sets the company value for this Profile.
     * 
     * @param company
     */
    public void setCompany(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCompany company) {
        this.company = company;
    }


    /**
     * Gets the contact value for this Profile.
     * 
     * @return contact
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContact getContact() {
        return contact;
    }


    /**
     * Sets the contact value for this Profile.
     * 
     * @param contact
     */
    public void setContact(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContact contact) {
        this.contact = contact;
    }


    /**
     * Gets the content value for this Profile.
     * 
     * @return content
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContent getContent() {
        return content;
    }


    /**
     * Sets the content value for this Profile.
     * 
     * @param content
     */
    public void setContent(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContent content) {
        this.content = content;
    }


    /**
     * Gets the cooperation value for this Profile.
     * 
     * @return cooperation
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperation getCooperation() {
        return cooperation;
    }


    /**
     * Sets the cooperation value for this Profile.
     * 
     * @param cooperation
     */
    public void setCooperation(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperation cooperation) {
        this.cooperation = cooperation;
    }


    /**
     * Gets the customprops value for this Profile.
     * 
     * @return customprops
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCustomprops getCustomprops() {
        return customprops;
    }


    /**
     * Sets the customprops value for this Profile.
     * 
     * @param customprops
     */
    public void setCustomprops(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCustomprops customprops) {
        this.customprops = customprops;
    }


    /**
     * Gets the datum value for this Profile.
     * 
     * @return datum
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDatum getDatum() {
        return datum;
    }


    /**
     * Sets the datum value for this Profile.
     * 
     * @param datum
     */
    public void setDatum(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDatum datum) {
        this.datum = datum;
    }


    /**
     * Gets the dissemination value for this Profile.
     * 
     * @return dissemination
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDissemination getDissemination() {
        return dissemination;
    }


    /**
     * Sets the dissemination value for this Profile.
     * 
     * @param dissemination
     */
    public void setDissemination(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDissemination dissemination) {
        this.dissemination = dissemination;
    }


    /**
     * Gets the eoi value for this Profile.
     * 
     * @return eoi
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileEoi getEoi() {
        return eoi;
    }


    /**
     * Sets the eoi value for this Profile.
     * 
     * @param eoi
     */
    public void setEoi(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileEoi eoi) {
        this.eoi = eoi;
    }


    /**
     * Gets the files value for this Profile.
     * 
     * @return files
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileFile[] getFiles() {
        return files;
    }


    /**
     * Sets the files value for this Profile.
     * 
     * @param files
     */
    public void setFiles(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileFile[] files) {
        this.files = files;
    }


    /**
     * Gets the keyword value for this Profile.
     * 
     * @return keyword
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileKeyword getKeyword() {
        return keyword;
    }


    /**
     * Sets the keyword value for this Profile.
     * 
     * @param keyword
     */
    public void setKeyword(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileKeyword keyword) {
        this.keyword = keyword;
    }


    /**
     * Gets the partnerships value for this Profile.
     * 
     * @return partnerships
     */
    public java.lang.String[] getPartnerships() {
        return partnerships;
    }


    /**
     * Sets the partnerships value for this Profile.
     * 
     * @param partnerships
     */
    public void setPartnerships(java.lang.String[] partnerships) {
        this.partnerships = partnerships;
    }


    /**
     * Gets the program value for this Profile.
     * 
     * @return program
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileProgram getProgram() {
        return program;
    }


    /**
     * Sets the program value for this Profile.
     * 
     * @param program
     */
    public void setProgram(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileProgram program) {
        this.program = program;
    }


    /**
     * Gets the reference value for this Profile.
     * 
     * @return reference
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileReference getReference() {
        return reference;
    }


    /**
     * Sets the reference value for this Profile.
     * 
     * @param reference
     */
    public void setReference(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileReference reference) {
        this.reference = reference;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Profile)) return false;
        Profile other = (Profile) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.company==null && other.getCompany()==null) || 
             (this.company!=null &&
              this.company.equals(other.getCompany()))) &&
            ((this.contact==null && other.getContact()==null) || 
             (this.contact!=null &&
              this.contact.equals(other.getContact()))) &&
            ((this.content==null && other.getContent()==null) || 
             (this.content!=null &&
              this.content.equals(other.getContent()))) &&
            ((this.cooperation==null && other.getCooperation()==null) || 
             (this.cooperation!=null &&
              this.cooperation.equals(other.getCooperation()))) &&
            ((this.customprops==null && other.getCustomprops()==null) || 
             (this.customprops!=null &&
              this.customprops.equals(other.getCustomprops()))) &&
            ((this.datum==null && other.getDatum()==null) || 
             (this.datum!=null &&
              this.datum.equals(other.getDatum()))) &&
            ((this.dissemination==null && other.getDissemination()==null) || 
             (this.dissemination!=null &&
              this.dissemination.equals(other.getDissemination()))) &&
            ((this.eoi==null && other.getEoi()==null) || 
             (this.eoi!=null &&
              this.eoi.equals(other.getEoi()))) &&
            ((this.files==null && other.getFiles()==null) || 
             (this.files!=null &&
              java.util.Arrays.equals(this.files, other.getFiles()))) &&
            ((this.keyword==null && other.getKeyword()==null) || 
             (this.keyword!=null &&
              this.keyword.equals(other.getKeyword()))) &&
            ((this.partnerships==null && other.getPartnerships()==null) || 
             (this.partnerships!=null &&
              java.util.Arrays.equals(this.partnerships, other.getPartnerships()))) &&
            ((this.program==null && other.getProgram()==null) || 
             (this.program!=null &&
              this.program.equals(other.getProgram()))) &&
            ((this.reference==null && other.getReference()==null) || 
             (this.reference!=null &&
              this.reference.equals(other.getReference())));
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
        if (getCompany() != null) {
            _hashCode += getCompany().hashCode();
        }
        if (getContact() != null) {
            _hashCode += getContact().hashCode();
        }
        if (getContent() != null) {
            _hashCode += getContent().hashCode();
        }
        if (getCooperation() != null) {
            _hashCode += getCooperation().hashCode();
        }
        if (getCustomprops() != null) {
            _hashCode += getCustomprops().hashCode();
        }
        if (getDatum() != null) {
            _hashCode += getDatum().hashCode();
        }
        if (getDissemination() != null) {
            _hashCode += getDissemination().hashCode();
        }
        if (getEoi() != null) {
            _hashCode += getEoi().hashCode();
        }
        if (getFiles() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFiles());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFiles(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getKeyword() != null) {
            _hashCode += getKeyword().hashCode();
        }
        if (getPartnerships() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPartnerships());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPartnerships(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProgram() != null) {
            _hashCode += getProgram().hashCode();
        }
        if (getReference() != null) {
            _hashCode += getReference().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Profile.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profile"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("company");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "company"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileCompany"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contact");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "contact"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileContact"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("content");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "content"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileContent"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cooperation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "cooperation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileCooperation"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customprops");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "customprops"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileCustomprops"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("datum");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "datum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileDatum"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dissemination");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "dissemination"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileDissemination"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eoi");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "eoi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileEoi"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("files");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "files"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileFile"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileFile"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("keyword");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "keyword"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileKeyword"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("partnerships");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "partnerships"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("program");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "program"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileProgram"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "reference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileReference"));
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
