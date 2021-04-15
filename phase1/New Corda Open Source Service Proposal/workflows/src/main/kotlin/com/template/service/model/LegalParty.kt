package com.template.service.model

import com.template.model.LegalPartyReference
import java.security.cert.Certificate

data class LegalParty(val reference: LegalPartyReference, val certificate: Certificate, val local: Boolean)
