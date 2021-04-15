package com.template.service.core

import com.template.service.model.LegalParty
import net.corda.core.crypto.TransactionSignature
import net.corda.core.transactions.SignedTransaction
import java.util.concurrent.Future

interface ThirdPartyCASignatureRequester {

    fun requestSignature(
        reference: LegalParty,
        partiallySignedTransaction: SignedTransaction
    ): Future<TransactionSignature>

}