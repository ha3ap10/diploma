package ru.netology.diploma.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {

    private String cardFromNumber;
    private String cardToNumber;
    private String cardFromCVV;
    private String cardFromValidTill;
    private Amount amount;
}
