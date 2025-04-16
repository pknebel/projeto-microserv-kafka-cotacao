package org.pedro.mineradora.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.pedro.mineradora.clients.CurrencyPriceClient;
import org.pedro.mineradora.dto.CurrencyPriceDTO;
import org.pedro.mineradora.dto.QuotationDTO;
import org.pedro.mineradora.entities.QuotationEntity;
import org.pedro.mineradora.messages.KafkaEvents;
import org.pedro.mineradora.repositories.QuotationRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@ApplicationScoped
public class QuotationService {

    @Inject
    @RestClient
    CurrencyPriceClient currencyPriceClient;

    @Inject
    QuotationRepository quotationRepository;

    @Inject
    KafkaEvents kafkaEvents;

    public void getCurrengyPrice(){

        CurrencyPriceDTO currencyPriceInfo = currencyPriceClient.getPriceByPair("USD-BRL");

        if(updateCurrentInfoPrice(currencyPriceInfo)){
            kafkaEvents.sendNewKafkaEvent(QuotationDTO
                    .builder()
                    .currencyPrice(new BigDecimal(currencyPriceInfo.getUsdbrl().getBid()))
                    .date(new Date())
                    .build()


            );
        }

    }

    private boolean updateCurrentInfoPrice(CurrencyPriceDTO currencyPriceInfo) {

        BigDecimal currentPrice = new BigDecimal(currencyPriceInfo.getUsdbrl().getBid());
        boolean updatePrice = false;

        List<QuotationEntity> quotationList = quotationRepository.findAll().list();

        if(quotationList.isEmpty()){

            saveQuotation(currencyPriceInfo);
            updatePrice = true;

        } else {

            QuotationEntity lastDollarPrice = quotationList.get(quotationList.size() - 1);

            if(currentPrice.floatValue() > lastDollarPrice.getCurrencyPrice().floatValue()){

                updatePrice = true;
                saveQuotation(currencyPriceInfo);

            }
        }

        return updatePrice;

    }

    private void saveQuotation(CurrencyPriceDTO currencyInfo) {

        QuotationEntity quotation = new QuotationEntity();

        quotation.setDate(new Date());
        quotation.setCurrencyPrice(new BigDecimal(currencyInfo.getUsdbrl().getBid()));
        quotation.setPctChange(currencyInfo.getUsdbrl().getPctChange());
        quotation.setPair("USD-BRL");

        quotationRepository.persist(quotation);

    }

}
