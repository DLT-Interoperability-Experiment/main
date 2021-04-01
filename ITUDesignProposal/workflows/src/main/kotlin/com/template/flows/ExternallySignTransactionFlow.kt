package com.template.flows

import com.template.service.ThirdPartyCAService
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.FlowSession

class ExternallySignTransactionFlow(caService: ThirdPartyCAService, counterpartySession: FlowSession): FlowLogic<Unit>() {
    override fun call() {
        // Should receive the necessary LegalPartyReferences from counterpartySession and ask for caService to collect
        // it's signature, to send back to counterpartySession
    }
}
