package com.solstice.ecommerceshipment.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("e-commerce-order-service")
@Component
public interface LineItemFeignProxy {

    @RequestMapping(method = RequestMethod.GET, value = "/lines?shipmentId={shipmentId}")
    String getLinesForShipment(@PathVariable("shipmentId") Long shipmentId);
}
