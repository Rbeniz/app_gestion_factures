package org.sid.billingservice.rest.web;


import org.sid.billingservice.rest.entities.Bill;
import org.sid.billingservice.rest.model.Customer;
import org.sid.billingservice.rest.repository.BillRepository;
import org.sid.billingservice.rest.repository.ProductItemRepository;
import org.sid.billingservice.rest.service.CustomerRestClient;
import org.sid.billingservice.rest.service.ProductRestClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class BillingRestController {

    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private ProductRestClient productRestClient;

    public BillingRestController(BillRepository billRepository,
                                 ProductItemRepository productItemRepository,
                                 CustomerRestClient customerRestClient,
                                 ProductRestClient productRestClient) {

        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productRestClient = productRestClient;
    }

    @RequestMapping( value = "/fullBill/{id}" , method = RequestMethod.GET )
    public Bill getBill(@PathVariable(name = "id") Long id){
        Bill bill = billRepository.findById(id).get();
        Customer customer = customerRestClient.getCustomerById(bill.getCustomerId());
        bill.setCustomer(customer);
        bill.getProductItems().forEach( p -> {
            p.setProduct(productRestClient.getProductById(p.getProductId()));

        });

        return bill;

    }

}

