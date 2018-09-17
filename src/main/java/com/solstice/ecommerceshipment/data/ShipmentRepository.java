package com.solstice.ecommerceshipment.data;

import com.solstice.ecommerceshipment.domain.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
    List<Shipment> findAllByAccountIdOrderByDeliveryDateDesc(Long accountId);
}
