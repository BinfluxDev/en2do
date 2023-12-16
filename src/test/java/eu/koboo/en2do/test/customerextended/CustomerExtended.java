package eu.koboo.en2do.test.customerextended;

import eu.koboo.en2do.repository.entity.compound.CompoundIndex;
import eu.koboo.en2do.repository.entity.compound.Index;
import eu.koboo.en2do.test.customer.Customer;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter // lombok
@Setter // lombok
@NoArgsConstructor // lombok
@FieldDefaults(level = AccessLevel.PRIVATE) // lombok
@ToString // lombok
@CompoundIndex({@Index("orderStatus"), @Index(value = "lockStatus", ascending = false)}) // en2do
@CompoundIndex({@Index("customerId")}) // en2do
@CompoundIndex(value = {@Index("firstName"), @Index("lastName")}, uniqueIndex = true)  // en2do
public class CustomerExtended extends Customer { // entity extended by another class

    String orderStatus;
    String lockStatus;
}


