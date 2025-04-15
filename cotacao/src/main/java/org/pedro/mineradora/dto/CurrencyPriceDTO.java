package org.pedro.mineradora.dto;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyPriceDTO {

    public USDBRL usdbrl;

}
