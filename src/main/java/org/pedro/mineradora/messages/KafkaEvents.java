package org.pedro.mineradora.messages;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.pedro.mineradora.dto.QuotationDTO;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@ApplicationScoped
public class KafkaEvents {

    private final Logger LOG = LoggerFactory.getLogger(KafkaEvents.class);

    @Channel("quotation-channel")
    Emitter<QuotationDTO> quotationRequestEmitter;

    public void sendNewKafkaEvent(QuotationDTO quotation) {

        LOG.info("Sending quotation to Kafka topic");
        quotationRequestEmitter.send(quotation).toCompletableFuture().join();

    }
}