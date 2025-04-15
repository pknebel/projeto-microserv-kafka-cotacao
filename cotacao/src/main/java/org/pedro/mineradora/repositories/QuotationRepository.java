package org.pedro.mineradora.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.pedro.mineradora.entities.QuotationEntity;

@ApplicationScoped
public class QuotationRepository implements PanacheRepository<QuotationEntity> {



}
