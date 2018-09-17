package com.solstice.ecommerceshipment.controller;

import com.solstice.ecommerceshipment.domain.Shipment;
import com.solstice.ecommerceshipment.service.ShipmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ShipmentController {

    private ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping("/shipments/{shipmentId}")
    public Shipment getOneShipment(@PathVariable("shipmentId") Long shipmentId){
        return shipmentService.getOneShipment(shipmentId);
    }

    @GetMapping("/shipments")
    public List<Shipment> getAllShipmentsForAccount(@RequestParam("accountId") Long accountId){
        return shipmentService.getAllShipmentsForAccount(accountId);
    }

    @PutMapping("/shipments/{shipmentId}")
    public Shipment updateShipment(@PathVariable("shipmentId") Long shipmentId, Shipment shipmentUpdate){
        return shipmentService.updateShipment(shipmentId, shipmentUpdate);
    }

    @PostMapping("/shipments")
    public Shipment createShipment(Shipment shipmentToSave){
        return shipmentService.createShipment(shipmentToSave);
    }

    @DeleteMapping("/shipments/{shipmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShipment(@PathVariable("shipmentId") Long shipmentId){
        shipmentService.deleteShipment(shipmentId);
    }
}
