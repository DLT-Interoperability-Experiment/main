package com.template.service.core

import java.security.cert.Certificate

interface ThirdPartyCAValidator {

    fun checkCertificate(certificate: Certificate)

}