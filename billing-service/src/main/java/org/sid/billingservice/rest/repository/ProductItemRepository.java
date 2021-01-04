package org.sid.billingservice.rest.repository;


import org.sid.billingservice.rest.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource
public interface ProductItemRepository extends JpaRepository<ProductItem,Long> {

    public Collection<ProductItem> findBillById(Long id);
}
