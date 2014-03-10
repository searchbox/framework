/**
 * ProfileQueryRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService;

public class ProfileQueryRequest  implements java.io.Serializable {
    private java.lang.String contractId;

    private java.lang.String[] countriesForDissemination;

    private java.lang.String deadlineDateAfter;

    private java.lang.String deadlineDateBefore;

    private java.lang.String organisationCountryName;

    private java.lang.String organisationIdentifier;

    private java.lang.String organisationName;

    private java.lang.String password;

    private java.lang.String[] profileTypes;

    private java.lang.String publishedDateAfter;

    private java.lang.String publishedDateBefore;

    private java.lang.String submitDateAfter;

    private java.lang.String submitDateBefore;

    private java.lang.String updateDateAfter;

    private java.lang.String updateDateBefore;

    private java.lang.String username;

    public ProfileQueryRequest() {
    }

    public ProfileQueryRequest(
           java.lang.String contractId,
           java.lang.String[] countriesForDissemination,
           java.lang.String deadlineDateAfter,
           java.lang.String deadlineDateBefore,
           java.lang.String organisationCountryName,
           java.lang.String organisationIdentifier,
           java.lang.String organisationName,
           java.lang.String password,
           java.lang.String[] profileTypes,
           java.lang.String publishedDateAfter,
           java.lang.String publishedDateBefore,
           java.lang.String submitDateAfter,
           java.lang.String submitDateBefore,
           java.lang.String updateDateAfter,
           java.lang.String updateDateBefore,
           java.lang.String username) {
           this.contractId = contractId;
           this.countriesForDissemination = countriesForDissemination;
           this.deadlineDateAfter = deadlineDateAfter;
           this.deadlineDateBefore = deadlineDateBefore;
           this.organisationCountryName = organisationCountryName;
           this.organisationIdentifier = organisationIdentifier;
           this.organisationName = organisationName;
           this.password = password;
           this.profileTypes = profileTypes;
           this.publishedDateAfter = publishedDateAfter;
           this.publishedDateBefore = publishedDateBefore;
           this.submitDateAfter = submitDateAfter;
           this.submitDateBefore = submitDateBefore;
           this.updateDateAfter = updateDateAfter;
           this.updateDateBefore = updateDateBefore;
           this.username = username;
    }


    /**
     * Gets the contractId value for this ProfileQueryRequest.
     * 
     * @return contractId
     */
    public java.lang.String getContractId() {
        return contractId;
    }


    /**
     * Sets the contractId value for this ProfileQueryRequest.
     * 
     * @param contractId
     */
    public void setContractId(java.lang.String contractId) {
        this.contractId = contractId;
    }


    /**
     * Gets the countriesForDissemination value for this ProfileQueryRequest.
     * 
     * @return countriesForDissemination
     */
    public java.lang.String[] getCountriesForDissemination() {
        return countriesForDissemination;
    }


    /**
     * Sets the countriesForDissemination value for this ProfileQueryRequest.
     * 
     * @param countriesForDissemination
     */
    public void setCountriesForDissemination(java.lang.String[] countriesForDissemination) {
        this.countriesForDissemination = countriesForDissemination;
    }


    /**
     * Gets the deadlineDateAfter value for this ProfileQueryRequest.
     * 
     * @return deadlineDateAfter
     */
    public java.lang.String getDeadlineDateAfter() {
        return deadlineDateAfter;
    }


    /**
     * Sets the deadlineDateAfter value for this ProfileQueryRequest.
     * 
     * @param deadlineDateAfter
     */
    public void setDeadlineDateAfter(java.lang.String deadlineDateAfter) {
        this.deadlineDateAfter = deadlineDateAfter;
    }


    /**
     * Gets the deadlineDateBefore value for this ProfileQueryRequest.
     * 
     * @return deadlineDateBefore
     */
    public java.lang.String getDeadlineDateBefore() {
        return deadlineDateBefore;
    }


    /**
     * Sets the deadlineDateBefore value for this ProfileQueryRequest.
     * 
     * @param deadlineDateBefore
     */
    public void setDeadlineDateBefore(java.lang.String deadlineDateBefore) {
        this.deadlineDateBefore = deadlineDateBefore;
    }


    /**
     * Gets the organisationCountryName value for this ProfileQueryRequest.
     * 
     * @return organisationCountryName
     */
    public java.lang.String getOrganisationCountryName() {
        return organisationCountryName;
    }


    /**
     * Sets the organisationCountryName value for this ProfileQueryRequest.
     * 
     * @param organisationCountryName
     */
    public void setOrganisationCountryName(java.lang.String organisationCountryName) {
        this.organisationCountryName = organisationCountryName;
    }


    /**
     * Gets the organisationIdentifier value for this ProfileQueryRequest.
     * 
     * @return organisationIdentifier
     */
    public java.lang.String getOrganisationIdentifier() {
        return organisationIdentifier;
    }


    /**
     * Sets the organisationIdentifier value for this ProfileQueryRequest.
     * 
     * @param organisationIdentifier
     */
    public void setOrganisationIdentifier(java.lang.String organisationIdentifier) {
        this.organisationIdentifier = organisationIdentifier;
    }


    /**
     * Gets the organisationName value for this ProfileQueryRequest.
     * 
     * @return organisationName
     */
    public java.lang.String getOrganisationName() {
        return organisationName;
    }


    /**
     * Sets the organisationName value for this ProfileQueryRequest.
     * 
     * @param organisationName
     */
    public void setOrganisationName(java.lang.String organisationName) {
        this.organisationName = organisationName;
    }


    /**
     * Gets the password value for this ProfileQueryRequest.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this ProfileQueryRequest.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the profileTypes value for this ProfileQueryRequest.
     * 
     * @return profileTypes
     */
    public java.lang.String[] getProfileTypes() {
        return profileTypes;
    }


    /**
     * Sets the profileTypes value for this ProfileQueryRequest.
     * 
     * @param profileTypes
     */
    public void setProfileTypes(java.lang.String[] profileTypes) {
        this.profileTypes = profileTypes;
    }


    /**
     * Gets the publishedDateAfter value for this ProfileQueryRequest.
     * 
     * @return publishedDateAfter
     */
    public java.lang.String getPublishedDateAfter() {
        return publishedDateAfter;
    }


    /**
     * Sets the publishedDateAfter value for this ProfileQueryRequest.
     * 
     * @param publishedDateAfter
     */
    public void setPublishedDateAfter(java.lang.String publishedDateAfter) {
        this.publishedDateAfter = publishedDateAfter;
    }


    /**
     * Gets the publishedDateBefore value for this ProfileQueryRequest.
     * 
     * @return publishedDateBefore
     */
    public java.lang.String getPublishedDateBefore() {
        return publishedDateBefore;
    }


    /**
     * Sets the publishedDateBefore value for this ProfileQueryRequest.
     * 
     * @param publishedDateBefore
     */
    public void setPublishedDateBefore(java.lang.String publishedDateBefore) {
        this.publishedDateBefore = publishedDateBefore;
    }


    /**
     * Gets the submitDateAfter value for this ProfileQueryRequest.
     * 
     * @return submitDateAfter
     */
    public java.lang.String getSubmitDateAfter() {
        return submitDateAfter;
    }


    /**
     * Sets the submitDateAfter value for this ProfileQueryRequest.
     * 
     * @param submitDateAfter
     */
    public void setSubmitDateAfter(java.lang.String submitDateAfter) {
        this.submitDateAfter = submitDateAfter;
    }


    /**
     * Gets the submitDateBefore value for this ProfileQueryRequest.
     * 
     * @return submitDateBefore
     */
    public java.lang.String getSubmitDateBefore() {
        return submitDateBefore;
    }


    /**
     * Sets the submitDateBefore value for this ProfileQueryRequest.
     * 
     * @param submitDateBefore
     */
    public void setSubmitDateBefore(java.lang.String submitDateBefore) {
        this.submitDateBefore = submitDateBefore;
    }


    /**
     * Gets the updateDateAfter value for this ProfileQueryRequest.
     * 
     * @return updateDateAfter
     */
    public java.lang.String getUpdateDateAfter() {
        return updateDateAfter;
    }


    /**
     * Sets the updateDateAfter value for this ProfileQueryRequest.
     * 
     * @param updateDateAfter
     */
    public void setUpdateDateAfter(java.lang.String updateDateAfter) {
        this.updateDateAfter = updateDateAfter;
    }


    /**
     * Gets the updateDateBefore value for this ProfileQueryRequest.
     * 
     * @return updateDateBefore
     */
    public java.lang.String getUpdateDateBefore() {
        return updateDateBefore;
    }


    /**
     * Sets the updateDateBefore value for this ProfileQueryRequest.
     * 
     * @param updateDateBefore
     */
    public void setUpdateDateBefore(java.lang.String updateDateBefore) {
        this.updateDateBefore = updateDateBefore;
    }


    /**
     * Gets the username value for this ProfileQueryRequest.
     * 
     * @return username
     */
    public java.lang.String getUsername() {
        return username;
    }


    /**
     * Sets the username value for this ProfileQueryRequest.
     * 
     * @param username
     */
    public void setUsername(java.lang.String username) {
        this.username = username;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProfileQueryRequest)) return false;
        ProfileQueryRequest other = (ProfileQueryRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.contractId==null && other.getContractId()==null) || 
             (this.contractId!=null &&
              this.contractId.equals(other.getContractId()))) &&
            ((this.countriesForDissemination==null && other.getCountriesForDissemination()==null) || 
             (this.countriesForDissemination!=null &&
              java.util.Arrays.equals(this.countriesForDissemination, other.getCountriesForDissemination()))) &&
            ((this.deadlineDateAfter==null && other.getDeadlineDateAfter()==null) || 
             (this.deadlineDateAfter!=null &&
              this.deadlineDateAfter.equals(other.getDeadlineDateAfter()))) &&
            ((this.deadlineDateBefore==null && other.getDeadlineDateBefore()==null) || 
             (this.deadlineDateBefore!=null &&
              this.deadlineDateBefore.equals(other.getDeadlineDateBefore()))) &&
            ((this.organisationCountryName==null && other.getOrganisationCountryName()==null) || 
             (this.organisationCountryName!=null &&
              this.organisationCountryName.equals(other.getOrganisationCountryName()))) &&
            ((this.organisationIdentifier==null && other.getOrganisationIdentifier()==null) || 
             (this.organisationIdentifier!=null &&
              this.organisationIdentifier.equals(other.getOrganisationIdentifier()))) &&
            ((this.organisationName==null && other.getOrganisationName()==null) || 
             (this.organisationName!=null &&
              this.organisationName.equals(other.getOrganisationName()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.profileTypes==null && other.getProfileTypes()==null) || 
             (this.profileTypes!=null &&
              java.util.Arrays.equals(this.profileTypes, other.getProfileTypes()))) &&
            ((this.publishedDateAfter==null && other.getPublishedDateAfter()==null) || 
             (this.publishedDateAfter!=null &&
              this.publishedDateAfter.equals(other.getPublishedDateAfter()))) &&
            ((this.publishedDateBefore==null && other.getPublishedDateBefore()==null) || 
             (this.publishedDateBefore!=null &&
              this.publishedDateBefore.equals(other.getPublishedDateBefore()))) &&
            ((this.submitDateAfter==null && other.getSubmitDateAfter()==null) || 
             (this.submitDateAfter!=null &&
              this.submitDateAfter.equals(other.getSubmitDateAfter()))) &&
            ((this.submitDateBefore==null && other.getSubmitDateBefore()==null) || 
             (this.submitDateBefore!=null &&
              this.submitDateBefore.equals(other.getSubmitDateBefore()))) &&
            ((this.updateDateAfter==null && other.getUpdateDateAfter()==null) || 
             (this.updateDateAfter!=null &&
              this.updateDateAfter.equals(other.getUpdateDateAfter()))) &&
            ((this.updateDateBefore==null && other.getUpdateDateBefore()==null) || 
             (this.updateDateBefore!=null &&
              this.updateDateBefore.equals(other.getUpdateDateBefore()))) &&
            ((this.username==null && other.getUsername()==null) || 
             (this.username!=null &&
              this.username.equals(other.getUsername())));
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
        if (getContractId() != null) {
            _hashCode += getContractId().hashCode();
        }
        if (getCountriesForDissemination() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCountriesForDissemination());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCountriesForDissemination(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDeadlineDateAfter() != null) {
            _hashCode += getDeadlineDateAfter().hashCode();
        }
        if (getDeadlineDateBefore() != null) {
            _hashCode += getDeadlineDateBefore().hashCode();
        }
        if (getOrganisationCountryName() != null) {
            _hashCode += getOrganisationCountryName().hashCode();
        }
        if (getOrganisationIdentifier() != null) {
            _hashCode += getOrganisationIdentifier().hashCode();
        }
        if (getOrganisationName() != null) {
            _hashCode += getOrganisationName().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getProfileTypes() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProfileTypes());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProfileTypes(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getPublishedDateAfter() != null) {
            _hashCode += getPublishedDateAfter().hashCode();
        }
        if (getPublishedDateBefore() != null) {
            _hashCode += getPublishedDateBefore().hashCode();
        }
        if (getSubmitDateAfter() != null) {
            _hashCode += getSubmitDateAfter().hashCode();
        }
        if (getSubmitDateBefore() != null) {
            _hashCode += getSubmitDateBefore().hashCode();
        }
        if (getUpdateDateAfter() != null) {
            _hashCode += getUpdateDateAfter().hashCode();
        }
        if (getUpdateDateBefore() != null) {
            _hashCode += getUpdateDateBefore().hashCode();
        }
        if (getUsername() != null) {
            _hashCode += getUsername().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProfileQueryRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "ProfileQueryRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contractId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "ContractId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("countriesForDissemination");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "CountriesForDissemination"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deadlineDateAfter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "DeadlineDateAfter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deadlineDateBefore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "DeadlineDateBefore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("organisationCountryName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "OrganisationCountryName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("organisationIdentifier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "OrganisationIdentifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("organisationName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "OrganisationName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "Password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profileTypes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "ProfileTypes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("publishedDateAfter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "PublishedDateAfter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("publishedDateBefore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "PublishedDateBefore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("submitDateAfter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "SubmitDateAfter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("submitDateBefore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "SubmitDateBefore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateDateAfter");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "UpdateDateAfter"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("updateDateBefore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "UpdateDateBefore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("username");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "Username"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
