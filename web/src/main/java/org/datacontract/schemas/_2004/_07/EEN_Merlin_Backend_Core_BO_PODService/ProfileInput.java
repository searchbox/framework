/**
 * ProfileInput.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService;

public class ProfileInput implements java.io.Serializable {
  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCompanyInput company;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContactInput contact;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContentInput content;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationInput cooperation;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDisseminationInput dissemination;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileEoiInput eoi;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileFileInput[] files;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileKeywordInput keyword;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileProgramInput program;

  private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileReferenceInput reference;

  public ProfileInput() {
  }

  public ProfileInput(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCompanyInput company,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContactInput contact,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContentInput content,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationInput cooperation,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDisseminationInput dissemination,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileEoiInput eoi,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileFileInput[] files,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileKeywordInput keyword,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileProgramInput program,
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileReferenceInput reference) {
    this.company = company;
    this.contact = contact;
    this.content = content;
    this.cooperation = cooperation;
    this.dissemination = dissemination;
    this.eoi = eoi;
    this.files = files;
    this.keyword = keyword;
    this.program = program;
    this.reference = reference;
  }

  /**
   * Gets the company value for this ProfileInput.
   * 
   * @return company
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCompanyInput getCompany() {
    return company;
  }

  /**
   * Sets the company value for this ProfileInput.
   * 
   * @param company
   */
  public void setCompany(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCompanyInput company) {
    this.company = company;
  }

  /**
   * Gets the contact value for this ProfileInput.
   * 
   * @return contact
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContactInput getContact() {
    return contact;
  }

  /**
   * Sets the contact value for this ProfileInput.
   * 
   * @param contact
   */
  public void setContact(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContactInput contact) {
    this.contact = contact;
  }

  /**
   * Gets the content value for this ProfileInput.
   * 
   * @return content
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContentInput getContent() {
    return content;
  }

  /**
   * Sets the content value for this ProfileInput.
   * 
   * @param content
   */
  public void setContent(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileContentInput content) {
    this.content = content;
  }

  /**
   * Gets the cooperation value for this ProfileInput.
   * 
   * @return cooperation
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationInput getCooperation() {
    return cooperation;
  }

  /**
   * Sets the cooperation value for this ProfileInput.
   * 
   * @param cooperation
   */
  public void setCooperation(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileCooperationInput cooperation) {
    this.cooperation = cooperation;
  }

  /**
   * Gets the dissemination value for this ProfileInput.
   * 
   * @return dissemination
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDisseminationInput getDissemination() {
    return dissemination;
  }

  /**
   * Sets the dissemination value for this ProfileInput.
   * 
   * @param dissemination
   */
  public void setDissemination(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileDisseminationInput dissemination) {
    this.dissemination = dissemination;
  }

  /**
   * Gets the eoi value for this ProfileInput.
   * 
   * @return eoi
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileEoiInput getEoi() {
    return eoi;
  }

  /**
   * Sets the eoi value for this ProfileInput.
   * 
   * @param eoi
   */
  public void setEoi(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileEoiInput eoi) {
    this.eoi = eoi;
  }

  /**
   * Gets the files value for this ProfileInput.
   * 
   * @return files
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileFileInput[] getFiles() {
    return files;
  }

  /**
   * Sets the files value for this ProfileInput.
   * 
   * @param files
   */
  public void setFiles(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileFileInput[] files) {
    this.files = files;
  }

  /**
   * Gets the keyword value for this ProfileInput.
   * 
   * @return keyword
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileKeywordInput getKeyword() {
    return keyword;
  }

  /**
   * Sets the keyword value for this ProfileInput.
   * 
   * @param keyword
   */
  public void setKeyword(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileKeywordInput keyword) {
    this.keyword = keyword;
  }

  /**
   * Gets the program value for this ProfileInput.
   * 
   * @return program
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileProgramInput getProgram() {
    return program;
  }

  /**
   * Sets the program value for this ProfileInput.
   * 
   * @param program
   */
  public void setProgram(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileProgramInput program) {
    this.program = program;
  }

  /**
   * Gets the reference value for this ProfileInput.
   * 
   * @return reference
   */
  public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileReferenceInput getReference() {
    return reference;
  }

  /**
   * Sets the reference value for this ProfileInput.
   * 
   * @param reference
   */
  public void setReference(
      org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileReferenceInput reference) {
    this.reference = reference;
  }

  private java.lang.Object __equalsCalc = null;

  public synchronized boolean equals(java.lang.Object obj) {
    if (!(obj instanceof ProfileInput))
      return false;
    ProfileInput other = (ProfileInput) obj;
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
        && ((this.company == null && other.getCompany() == null) || (this.company != null && this.company
            .equals(other.getCompany())))
        && ((this.contact == null && other.getContact() == null) || (this.contact != null && this.contact
            .equals(other.getContact())))
        && ((this.content == null && other.getContent() == null) || (this.content != null && this.content
            .equals(other.getContent())))
        && ((this.cooperation == null && other.getCooperation() == null) || (this.cooperation != null && this.cooperation
            .equals(other.getCooperation())))
        && ((this.dissemination == null && other.getDissemination() == null) || (this.dissemination != null && this.dissemination
            .equals(other.getDissemination())))
        && ((this.eoi == null && other.getEoi() == null) || (this.eoi != null && this.eoi
            .equals(other.getEoi())))
        && ((this.files == null && other.getFiles() == null) || (this.files != null && java.util.Arrays
            .equals(this.files, other.getFiles())))
        && ((this.keyword == null && other.getKeyword() == null) || (this.keyword != null && this.keyword
            .equals(other.getKeyword())))
        && ((this.program == null && other.getProgram() == null) || (this.program != null && this.program
            .equals(other.getProgram())))
        && ((this.reference == null && other.getReference() == null) || (this.reference != null && this.reference
            .equals(other.getReference())));
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
    if (getDissemination() != null) {
      _hashCode += getDissemination().hashCode();
    }
    if (getEoi() != null) {
      _hashCode += getEoi().hashCode();
    }
    if (getFiles() != null) {
      for (int i = 0; i < java.lang.reflect.Array.getLength(getFiles()); i++) {
        java.lang.Object obj = java.lang.reflect.Array.get(getFiles(), i);
        if (obj != null && !obj.getClass().isArray()) {
          _hashCode += obj.hashCode();
        }
      }
    }
    if (getKeyword() != null) {
      _hashCode += getKeyword().hashCode();
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
  private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
      ProfileInput.class, true);

  static {
    typeDesc
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileInput"));
    org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("company");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "company"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileCompanyInput"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("contact");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "contact"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileContactInput"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("content");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "content"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileContentInput"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("cooperation");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "cooperation"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileCooperationInput"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("dissemination");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "dissemination"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileDisseminationInput"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("eoi");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "eoi"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileEoiInput"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("files");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "files"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileFileInput"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    elemField
        .setItemQName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileFileInput"));
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("keyword");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "keyword"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileKeywordInput"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("program");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "program"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileProgramInput"));
    elemField.setMinOccurs(0);
    elemField.setNillable(true);
    typeDesc.addFieldDesc(elemField);
    elemField = new org.apache.axis.description.ElementDesc();
    elemField.setFieldName("reference");
    elemField
        .setXmlName(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "reference"));
    elemField
        .setXmlType(new javax.xml.namespace.QName(
            "http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService",
            "profileReferenceInput"));
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
