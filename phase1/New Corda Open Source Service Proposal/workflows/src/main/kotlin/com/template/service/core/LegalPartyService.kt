package com.template.service.core

import com.template.model.LegalPartyReference
import com.template.service.model.LegalParty
import java.util.*

interface LegalPartyService {

    fun get(reference: LegalPartyReference): Optional<LegalParty>
    fun add(party: LegalParty)

}