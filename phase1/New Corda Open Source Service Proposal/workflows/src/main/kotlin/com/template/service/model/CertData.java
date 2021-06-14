package com.template.service.model;

import org.demoiselle.signer.core.extension.DefaultExtension;
import org.demoiselle.signer.core.extension.DefaultExtensionType;

import java.util.Date;
import java.util.List;

public class CertData {

    @DefaultExtension(type= DefaultExtensionType.CERTIFICATION_AUTHORITY)
private  boolean isCertAuthority;

    @DefaultExtension(type= DefaultExtensionType.AFTER_DATE)
    private Date afterDate;

    @DefaultExtension(type= DefaultExtensionType.BEFORE_DATE)
    private Date beforeDate;

    @DefaultExtension(type= DefaultExtensionType.SERIAL_NUMBER)
    private String serialNumber;

    @DefaultExtension(type= DefaultExtensionType.ISSUER_DN)
    private String issuer;

    @DefaultExtension(type= DefaultExtensionType.SUBJECT_DN)
    private String subject;

    @DefaultExtension(type= DefaultExtensionType.SUBJECT_KEY_IDENTIFIER)
    private String keyIdentifier;

    @DefaultExtension(type= DefaultExtensionType.AUTHORITY_KEY_IDENTIFIER )
    private String auth_id;




@DefaultExtension(type= DefaultExtensionType.CRL_URL)
private List<String> crlURL;


    public boolean isCertAuthority() {
        return isCertAuthority;
    }

    public void setCertAuthority(boolean certAuthority) {
        isCertAuthority = certAuthority;
    }

    public List<String> getCrlURL() {
        return crlURL;
    }

    public void setCrlURL(List<String> crlURL) {
        this.crlURL = crlURL;
    }

    public Date getAfterDate() {
        return afterDate;
    }

    public void setAfterDate(Date afterDate) {
        this.afterDate = afterDate;
    }

    public Date getBeforeDate() {
        return beforeDate;
    }

    public void setBeforeDate(Date beforeDate) {
        this.beforeDate = beforeDate;
    }
//private String serialNumber;


    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getKeyIdentifier() {
        return keyIdentifier;
    }

    public void setKeyIdentifier(String keyIdentifier) {
        this.keyIdentifier = keyIdentifier;
    }

    public String getAuth_id() {
        return auth_id;
    }

    public void setAuth_id(String auth_id) {
        this.auth_id = auth_id;
    }

    @Override
    public String toString() {
        return "CertData{" +
                "isCertAuthority=" + isCertAuthority +
                ", afterDate=" + afterDate +
                ", beforeDate=" + beforeDate +
                ", serialNumber='" + serialNumber + '\'' +
                ", issuer='" + issuer + '\'' +
                ", subject='" + subject + '\'' +
                ", keyIdentifier='" + keyIdentifier + '\'' +
                ", auth_id='" + auth_id + '\'' +
                ", crlURL=" + crlURL +
                '}';
    }
}
