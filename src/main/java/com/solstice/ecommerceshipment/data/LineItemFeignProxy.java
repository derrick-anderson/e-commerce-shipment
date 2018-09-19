package com.solstice.ecommerceshipment.data;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(name = "e-commerce-order-service", fallback = LineItemFeignFallback.class)
public interface LineItemFeignProxy {

    @RequestMapping(method = RequestMethod.GET, value = "/lines?shipmentId={shipmentId}")
    String getLinesForShipment(@PathVariable("shipmentId") Long shipmentId);
}

@Component
class LineItemFeignFallback implements LineItemFeignProxy{

    @Override
    public String getLinesForShipment(Long shipmentId){
        return "{ \"error\" : \"Service Unavailable, please try again later\"}";
    }
}