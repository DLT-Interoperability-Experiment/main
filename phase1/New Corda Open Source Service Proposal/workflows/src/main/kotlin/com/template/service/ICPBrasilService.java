package com.template.service;

import com.template.repository.ICPBrasilRepository;
import com.template.service.core.ThirdPartyCAValidator;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collection;

public class ICPBrasilService implements ThirdPartyCAValidator {

    ICPBrasilRepository icpBrasilRepository;
    public ICPBrasilService() {
        icpBrasilRepository=new ICPBrasilRepository();

    }

    public void checkCertificate(X509Certificate certificate){

        //Verify Certificate Date (after/before)
        icpBrasilRepository.verifyDateCertificate(certificate);
        //Verify if Certificate is CLR list
        icpBrasilRepository.verifyCLR(certificate);
        //Catch the Certificate chain
        Collection<X509Certificate> certificates = icpBrasilRepository.getCertificateChain(certificate);
        //Verify for every certificate off chain in CLR list
        icpBrasilRepository.verifyAllCLRsOfChain(certificates);
        //Verify after/before Date of every certificate off chain
        icpBrasilRepository.verifyAllDatesOfChain(certificates);
        //TODO verify certificate public key?
    }
    @Override
    public void checkCertificate(Certificate certificate) {

        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream bais = new ByteArrayInputStream(certificate.getEncoded());
            X509Certificate x509 =  (X509Certificate) cf.generateCertificate(bais);
            checkCertificate(x509);
        } catch (CertificateException e) {
            e.printStackTrace();
        }

    }

}
