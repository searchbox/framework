/**
 * ProfileProgram.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService;

public class ProfileProgram  implements java.io.Serializable {
    private java.lang.String acronym;

    private org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileProgramCall call;

    private java.lang.Boolean coordinator;

    private java.lang.String duration;

    private java.lang.String evaluation;

    private java.lang.String framework;

    private java.lang.String funding;

    private java.lang.String prebudget;

    private java.lang.String programme;

    private java.lang.String subprogramme;

    private java.lang.String url;

    public ProfileProgram() {
    }

    public ProfileProgram(
           java.lang.String acronym,
           org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileProgramCall call,
           java.lang.Boolean coordinator,
           java.lang.String duration,
           java.lang.String evaluation,
           java.lang.String framework,
           java.lang.String funding,
           java.lang.String prebudget,
           java.lang.String programme,
           java.lang.String subprogramme,
           java.lang.String url) {
           this.acronym = acronym;
           this.call = call;
           this.coordinator = coordinator;
           this.duration = duration;
           this.evaluation = evaluation;
           this.framework = framework;
           this.funding = funding;
           this.prebudget = prebudget;
           this.programme = programme;
           this.subprogramme = subprogramme;
           this.url = url;
    }


    /**
     * Gets the acronym value for this ProfileProgram.
     * 
     * @return acronym
     */
    public java.lang.String getAcronym() {
        return acronym;
    }


    /**
     * Sets the acronym value for this ProfileProgram.
     * 
     * @param acronym
     */
    public void setAcronym(java.lang.String acronym) {
        this.acronym = acronym;
    }


    /**
     * Gets the call value for this ProfileProgram.
     * 
     * @return call
     */
    public org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileProgramCall getCall() {
        return call;
    }


    /**
     * Sets the call value for this ProfileProgram.
     * 
     * @param call
     */
    public void setCall(org.datacontract.schemas._2004._07.EEN_Merlin_Backend_Core_BO_PODService.ProfileProgramCall call) {
        this.call = call;
    }


    /**
     * Gets the coordinator value for this ProfileProgram.
     * 
     * @return coordinator
     */
    public java.lang.Boolean getCoordinator() {
        return coordinator;
    }


    /**
     * Sets the coordinator value for this ProfileProgram.
     * 
     * @param coordinator
     */
    public void setCoordinator(java.lang.Boolean coordinator) {
        this.coordinator = coordinator;
    }


    /**
     * Gets the duration value for this ProfileProgram.
     * 
     * @return duration
     */
    public java.lang.String getDuration() {
        return duration;
    }


    /**
     * Sets the duration value for this ProfileProgram.
     * 
     * @param duration
     */
    public void setDuration(java.lang.String duration) {
        this.duration = duration;
    }


    /**
     * Gets the evaluation value for this ProfileProgram.
     * 
     * @return evaluation
     */
    public java.lang.String getEvaluation() {
        return evaluation;
    }


    /**
     * Sets the evaluation value for this ProfileProgram.
     * 
     * @param evaluation
     */
    public void setEvaluation(java.lang.String evaluation) {
        this.evaluation = evaluation;
    }


    /**
     * Gets the framework value for this ProfileProgram.
     * 
     * @return framework
     */
    public java.lang.String getFramework() {
        return framework;
    }


    /**
     * Sets the framework value for this ProfileProgram.
     * 
     * @param framework
     */
    public void setFramework(java.lang.String framework) {
        this.framework = framework;
    }


    /**
     * Gets the funding value for this ProfileProgram.
     * 
     * @return funding
     */
    public java.lang.String getFunding() {
        return funding;
    }


    /**
     * Sets the funding value for this ProfileProgram.
     * 
     * @param funding
     */
    public void setFunding(java.lang.String funding) {
        this.funding = funding;
    }


    /**
     * Gets the prebudget value for this ProfileProgram.
     * 
     * @return prebudget
     */
    public java.lang.String getPrebudget() {
        return prebudget;
    }


    /**
     * Sets the prebudget value for this ProfileProgram.
     * 
     * @param prebudget
     */
    public void setPrebudget(java.lang.String prebudget) {
        this.prebudget = prebudget;
    }


    /**
     * Gets the programme value for this ProfileProgram.
     * 
     * @return programme
     */
    public java.lang.String getProgramme() {
        return programme;
    }


    /**
     * Sets the programme value for this ProfileProgram.
     * 
     * @param programme
     */
    public void setProgramme(java.lang.String programme) {
        this.programme = programme;
    }


    /**
     * Gets the subprogramme value for this ProfileProgram.
     * 
     * @return subprogramme
     */
    public java.lang.String getSubprogramme() {
        return subprogramme;
    }


    /**
     * Sets the subprogramme value for this ProfileProgram.
     * 
     * @param subprogramme
     */
    public void setSubprogramme(java.lang.String subprogramme) {
        this.subprogramme = subprogramme;
    }


    /**
     * Gets the url value for this ProfileProgram.
     * 
     * @return url
     */
    public java.lang.String getUrl() {
        return url;
    }


    /**
     * Sets the url value for this ProfileProgram.
     * 
     * @param url
     */
    public void setUrl(java.lang.String url) {
        this.url = url;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProfileProgram)) return false;
        ProfileProgram other = (ProfileProgram) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.acronym==null && other.getAcronym()==null) || 
             (this.acronym!=null &&
              this.acronym.equals(other.getAcronym()))) &&
            ((this.call==null && other.getCall()==null) || 
             (this.call!=null &&
              this.call.equals(other.getCall()))) &&
            ((this.coordinator==null && other.getCoordinator()==null) || 
             (this.coordinator!=null &&
              this.coordinator.equals(other.getCoordinator()))) &&
            ((this.duration==null && other.getDuration()==null) || 
             (this.duration!=null &&
              this.duration.equals(other.getDuration()))) &&
            ((this.evaluation==null && other.getEvaluation()==null) || 
             (this.evaluation!=null &&
              this.evaluation.equals(other.getEvaluation()))) &&
            ((this.framework==null && other.getFramework()==null) || 
             (this.framework!=null &&
              this.framework.equals(other.getFramework()))) &&
            ((this.funding==null && other.getFunding()==null) || 
             (this.funding!=null &&
              this.funding.equals(other.getFunding()))) &&
            ((this.prebudget==null && other.getPrebudget()==null) || 
             (this.prebudget!=null &&
              this.prebudget.equals(other.getPrebudget()))) &&
            ((this.programme==null && other.getProgramme()==null) || 
             (this.programme!=null &&
              this.programme.equals(other.getProgramme()))) &&
            ((this.subprogramme==null && other.getSubprogramme()==null) || 
             (this.subprogramme!=null &&
              this.subprogramme.equals(other.getSubprogramme()))) &&
            ((this.url==null && other.getUrl()==null) || 
             (this.url!=null &&
              this.url.equals(other.getUrl())));
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
        if (getAcronym() != null) {
            _hashCode += getAcronym().hashCode();
        }
        if (getCall() != null) {
            _hashCode += getCall().hashCode();
        }
        if (getCoordinator() != null) {
            _hashCode += getCoordinator().hashCode();
        }
        if (getDuration() != null) {
            _hashCode += getDuration().hashCode();
        }
        if (getEvaluation() != null) {
            _hashCode += getEvaluation().hashCode();
        }
        if (getFramework() != null) {
            _hashCode += getFramework().hashCode();
        }
        if (getFunding() != null) {
            _hashCode += getFunding().hashCode();
        }
        if (getPrebudget() != null) {
            _hashCode += getPrebudget().hashCode();
        }
        if (getProgramme() != null) {
            _hashCode += getProgramme().hashCode();
        }
        if (getSubprogramme() != null) {
            _hashCode += getSubprogramme().hashCode();
        }
        if (getUrl() != null) {
            _hashCode += getUrl().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProfileProgram.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileProgram"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("acronym");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "acronym"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("call");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "call"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "profileProgramCall"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("coordinator");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "coordinator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("duration");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "duration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("evaluation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "evaluation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("framework");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "framework"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("funding");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "funding"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prebudget");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "prebudget"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("programme");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "programme"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subprogramme");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "subprogramme"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("url");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/EEN.Merlin.Backend.Core.BO.PODService", "url"));
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
