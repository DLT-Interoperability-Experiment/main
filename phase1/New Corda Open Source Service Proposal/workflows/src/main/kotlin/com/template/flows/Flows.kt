package com.template.flows

import co.paralleluniverse.fibers.Suspendable
import com.template.model.LegalPartyReference
import com.template.service.ThirdPartyCAService
import net.corda.core.flows.*
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

// *********
// * Flows *
// *********
@InitiatingFlow
@StartableByRPC
class Initiator(private val externalLegalParties: Collection<LegalPartyReference>) : FlowLogic<Unit>() {
    override val progressTracker = ProgressTracker()

    @Suspendable
    override fun call() {
        val caService = serviceHub.cordaService(ThirdPartyCAService::class.java)
        val notary = serviceHub.networkMapCache.notaryIdentities.first()
        val txBuilder = TransactionBuilder(notary)

        // Transaction Construction logic goes here
        // ....
        // ....

        // Build session with necessary nodes
        val sessions = serviceHub.networkMapCache.allNodes.map { initiateFlow(it.legalIdentities.first()) }

        val partiallySignedTransaction = serviceHub.signInitialTransaction(txBuilder)

        // Set sessions when also collecting external signatures from other nodes, omit when only local
        val externallySignedTransaction = caService.collectSignatures(sessions, externalLegalParties, partiallySignedTransaction)

        // Collect node signatures
        val fullySignedTransaction = subFlow(CollectSignaturesFlow(externallySignedTransaction, sessions))

        // Send to notary and commit transaction
        subFlow(FinalityFlow(fullySignedTransaction, sessions))
    }
}

@InitiatedBy(Initiator::class)
class Responder(val counterpartySession: FlowSession) : FlowLogic<Unit>() {
    @Suspendable
    override fun call() {
        val caService = serviceHub.cordaService(ThirdPartyCAService::class.java)

        // Sign requested local known external identities
        subFlow(ExternallySignTransactionFlow(caService, counterpartySession))

        // Sign using node certificate
        subFlow(object: SignTransactionFlow(counterpartySession){
            override fun checkTransaction(stx: SignedTransaction) {
            }
        })
        // Accept and commit transaction to ledger
        subFlow(ReceiveFinalityFlow(counterpartySession))
    }
}
