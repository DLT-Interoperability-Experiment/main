package com.template.repository;

import com.template.service.core.ThirdPartyCAValidator;
import com.template.service.model.CertData;
import org.demoiselle.signer.core.CertificateLoader;
import org.demoiselle.signer.core.CertificateLoaderImpl;
import org.demoiselle.signer.core.CertificateManager;
import org.demoiselle.signer.core.ca.manager.CAManager;
import org.demoiselle.signer.core.exception.CertificateCoreException;
import org.demoiselle.signer.core.exception.CertificateRevocationException;
import org.demoiselle.signer.core.exception.CertificateValidatorCRLException;
import org.demoiselle.signer.core.exception.CertificateValidatorException;
import org.demoiselle.signer.core.validator.CRLValidator;
import org.demoiselle.signer.core.validator.PeriodValidator;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collection;

public class ICPBrasilRepository {


    CAManager camanager;

    public ICPBrasilRepository() {
        camanager = CAManager.getInstance();
    }

    public X509Certificate getCertificate(File certFile){
        CertificateLoader certificateLoader = new CertificateLoaderImpl();
        X509Certificate certificate = certificateLoader.load(certFile);

        return certificate;
    }


    public CertData getData(File certFile){

        CertificateManager cm = new CertificateManager(certFile);
        CertData certificate = cm.load(CertData.class);
        return certificate;
    }

    public CertData getData(X509Certificate certificate){

        CertificateManager cm = new CertificateManager(certificate);
        CertData certdata = cm.load(CertData.class);
        return certdata;
    }


    public void verifyDateCertificate(File certFile) throws CertificateValidatorException {

        X509Certificate certificate =getCertificate(certFile);
        verifyDateCertificate(certificate);
    }

    public void verifyDateCertificate(X509Certificate certificate) throws CertificateValidatorException {

        PeriodValidator periodValidator = new PeriodValidator();
        periodValidator.validate(certificate);
    }

    public void verifyCLR(File certFile) throws CertificateValidatorCRLException, CertificateRevocationException {

        X509Certificate certificate = getCertificate(certFile);
        verifyCLR(certificate);
    }

    public void verifyCLR(X509Certificate certificate) throws CertificateValidatorCRLException, CertificateRevocationException {

        CRLValidator crlValidator = new CRLValidator();
        crlValidator.validate(certificate);
    }

    public boolean verifyChain(File certFile) throws CertificateCoreException {

        X509Certificate certificate =getCertificate(certFile);

        return verifyChain(certificate);

    }

    public boolean verifyChain(X509Certificate certificate) throws CertificateCoreException {

        //get Certificate chain in collection

        Collection<X509Certificate> certificateChain = camanager.getCertificateChain(certificate);

        return camanager.validateRootCAs(certificateChain, certificate);

    }

    public Collection<X509Certificate> getCertificateChain(X509Certificate certificate){

        return camanager.getCertificateChain(certificate);
    }

    public void verifyAllDatesOfChain(Collection<X509Certificate> certificates){

        certificates.forEach(c->{
            verifyDateCertificate(c);

        });


    }

    public void verifyAllCLRsOfChain(Collection<X509Certificate> certificates){

        certificates.forEach(c->{
            verifyCLR(c);

        });

    }


}
