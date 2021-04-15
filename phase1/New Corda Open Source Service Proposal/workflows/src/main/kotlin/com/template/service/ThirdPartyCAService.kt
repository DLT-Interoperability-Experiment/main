package com.template.service

import com.template.model.LegalPartyReference
import com.template.service.core.DatabaseManager
import com.template.service.core.Injector
import com.template.service.core.LegalPartyService
import com.template.service.core.RequestProcessingQueue
import com.template.service.model.LegalParty
import net.corda.core.contracts.requireThat
import net.corda.core.crypto.TransactionSignature
import net.corda.core.flows.FlowSession
import net.corda.core.node.services.CordaService
import net.corda.core.serialization.SingletonSerializeAsToken
import net.corda.core.transactions.SignedTransaction
import java.security.cert.Certificate

@CordaService
class ThirdPartyCAService : SingletonSerializeAsToken() {
    companion object {
        // Build injector and entity graph
        val injector: Injector = TODO()
    }

    private val databaseManager: DatabaseManager = injector.getInstance(DatabaseManager::class.java)
    private val requestProcessingQueue = injector.getInstance(RequestProcessingQueue::class.java)
    private val legalPartyService = injector.getInstance(LegalPartyService::class.java)

    init {
        // Check database, create tables if necessary
        databaseManager.validateIntegrity() // We need to move database managing logic to LegalPartyService
        requestProcessingQueue.start()
        // Start callback Service
        // Check for pending work
        // Resubmit pending work
    }

    fun collectSignatures(
        sessions: List<FlowSession>, externalLegalParties: Collection<LegalPartyReference>,
        partiallySignedTransaction: SignedTransaction
    ): SignedTransaction {
        // Should check to see which external Parties is known to this node and run a command to collect it's
        // signature remotely

        val legalParties = externalLegalParties.map { legalPartyService.get(it) }
        val localLegalParties = legalParties.filter { it.isPresent }.map { it.get() }
        val remoteLegalParties = legalParties.filter { !it.isPresent }.map { it.get() }

        val localSignatures = localLegalParties.map {
            requestProcessingQueue.addSignatureRequest(
                it,
                partiallySignedTransaction
            )
        }.map { it.get() }

        // We need to make sure that we can start flows inside of the service to be able to collect remote signatures here,
        // or if we need to move this logic the flow
        val remoteSignatures: Collection<TransactionSignature> = remoteLegalParties.map { TODO("Signature Collection Magic") }

        return partiallySignedTransaction + localSignatures + remoteSignatures
    }

    fun collectSignatures(
        externalLegalParties: Collection<LegalPartyReference>,
        partiallySignedTransaction: SignedTransaction
    ): SignedTransaction {
        // Should check to see which external Parties is known to this node and fail when some LegalParty is missing
        val legalParties = externalLegalParties.map { legalPartyService.get(it) }
        requireThat {
            "All parties should be present." using legalParties.all { it.isPresent }
        }

        return partiallySignedTransaction + legalParties.map {
            requestProcessingQueue.addSignatureRequest(
                it.get(),
                partiallySignedTransaction
            )
        }.map { it.get() }
    }

    fun addLegalPartyReference(reference: LegalPartyReference, certificate: Certificate, local: Boolean) {
        legalPartyService.add(LegalParty(reference, certificate, local))
    }

// This method needs to be created as a decorator when using Corda Account
//    fun addLegalPartyReferenceWithAccount(reference: LegalPartyReference, certificate: PublicKey, Account) {
//
//    }

}