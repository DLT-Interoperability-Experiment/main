package com.template.service

import com.template.model.LegalPartyReference
import net.corda.core.flows.FlowSession
import net.corda.core.node.services.CordaService
import net.corda.core.serialization.SingletonSerializeAsToken
import net.corda.core.transactions.SignedTransaction
import java.security.PublicKey

@CordaService
class ThirdPartyCAService : SingletonSerializeAsToken() {
    init {
        // Check database, create tables if necessary
        // Check for pending work
        // Resubmit pending work
        // Start callback Service
    }

    fun collectSignatures(sessions: List<FlowSession>, externalLegalParties: Collection<LegalPartyReference>,
                          partiallySignedTransaction: SignedTransaction): SignedTransaction {
        // Should check to see which external Parties is known to this node and run a command to collect it's
        // signature remotely
        TODO()
    }

    fun collectSignatures(externalLegalParties: Collection<LegalPartyReference>,
                          partiallySignedTransaction: SignedTransaction): SignedTransaction {
        // Should check to see which external Parties is known to this node and run a command to collect it's
        // signature remotely
        TODO()
    }

    fun addLegalPartyReference(reference: LegalPartyReference, certificate: PublicKey) {

    }

    fun addLegalPartyReferenceWithAccount(reference: LegalPartyReference, certificate: PublicKey, Account) {

    }

}