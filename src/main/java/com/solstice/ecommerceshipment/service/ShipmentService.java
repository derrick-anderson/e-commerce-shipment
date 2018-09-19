package com.solstice.ecommerceshipment.service;

import com.solstice.ecommerceshipment.data.LineItemFeignProxy;
import com.solstice.ecommerceshipment.data.ShipmentRepository;
import com.solstice.ecommerceshipment.domain.Shipment;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ShipmentService {

    private ShipmentRepository shipmentRepository;

    private LineItemFeignProxy lineItemFeignProxy;

    public ShipmentService(ShipmentRepository shipmentRepository, LineItemFeignProxy lineItemFeignProxy) {
        this.shipmentRepository = shipmentRepository;
        this.lineItemFeignProxy = lineItemFeignProxy;
    }

    public Shipment getOneShipment(Long shipmentId) {
        return shipmentRepository.findById(shipmentId).get();
    }

    public List<Shipment> getAllShipmentsForAccount(Long accountId) {
        List<Shipment> theseShipments =  shipmentRepository.findAllByAccountIdOrderByDeliveryDateDesc(accountId);
            theseShipments.forEach(
                shipment -> shipment.setLineItems(lineItemFeignProxy.getLinesForShipment(shipment.getShipmentId()))
        );
        return theseShipments;
    }

    public Shipment updateShipment(Long shipmentId, Shipment shipmentUpdateData) {
        Shipment shipmentToUpdate = getOneShipment(shipmentId);
        if(shipmentToUpdate != null){
            if(shipmentUpdateData.getAccountId() != null){
                shipmentToUpdate.setAccountId(shipmentUpdateData.getAccountId());
            }
            if(shipmentUpdateData.getAddressId() != null){
                shipmentToUpdate.setAddressId(shipmentUpdateData.getAccountId());
            }
            if(shipmentUpdateData.getDeliveryDate() != null){
                shipmentToUpdate.setDeliveryDate(shipmentUpdateData.getDeliveryDate());
            }
            shipmentRepository.save(shipmentToUpdate);
            return shipmentToUpdate;
        }else throw new EntityNotFoundException();
    }

    public void deleteShipment(Long shipmentId) {
        Shipment foundShipment = getOneShipment(shipmentId);
        if(foundShipment != null){
            shipmentRepository.deleteById(shipmentId);
        }else throw new EntityNotFoundException();
    }

    public Shipment createShipment(Shipment toSave) {
        return shipmentRepository.save(toSave);
    }

    private String getLinesForShipment(Long shipmentId){
        return lineItemFeignProxy.getLinesForShipment(shipmentId);
    }
}
