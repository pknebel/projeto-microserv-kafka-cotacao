package org.pedro.mineradora.schedulers;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.pedro.mineradora.messages.KafkaEvents;
import org.pedro.mineradora.services.QuotationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class QuotationScheduler {

    private final Logger LOG = LoggerFactory.getLogger(KafkaEvents.class);

    @Inject
    QuotationService quotationService;

    @Transactional
    @Scheduled(every = "35s", identity = "task-job")
    void schedule(){
        LOG.info("Executing scheduled task");
        quotationService.getCurrengyPrice();
    }

}
