package com.solstice.ecommerceshipment.controller;

import com.solstice.ecommerceshipment.domain.Shipment;
import com.solstice.ecommerceshipment.service.ShipmentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(secure = false)
public class ShipmentControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShipmentService shipmentService;

    private ShipmentController shipmentController;

    @Before
    public void setup(){
        shipmentController = new ShipmentController(shipmentService);
    }

    @Test
    public void getOneShipment_HappyPath() throws Exception {
        when(shipmentService.getOneShipment(12L)).thenReturn(getMockShipment(12L));

        mockMvc.perform(get("/shipments/12"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.shipmentId", is(12)))
                .andExpect(jsonPath("$.shippedDate", is("2018-08-01")));
    }

    @Test
    public void getAllShipmentsForAccount_HappyPath() throws Exception{
        when(shipmentService.getAllShipmentsForAccount(12345L)).thenReturn(getMockShipmentList());

        mockMvc.perform(get("/shipments?accountId=12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].shipmentId", is(11)))
                .andExpect(jsonPath("$[1].shipmentId", is(12)))
                .andExpect(jsonPath("$[2].shipmentId", is(13)))
                .andExpect(jsonPath("$[3].shipmentId", is(14)))
                .andExpect(jsonPath("$[4].shipmentId", is(15)));
    }

    @Test
    public void updateShipment_HappyPath() throws Exception{
        when(shipmentService.updateShipment(anyLong(), any(Shipment.class))).thenReturn(getMockShipment(10L));

        mockMvc.perform(put("/shipments/10")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"addressId\": 15}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.addressId", is(12)));
    }

    @Test
    public void createShipment_HappyPath() throws Exception{
        when(shipmentService.createShipment(any(Shipment.class))).thenReturn(getMockShipment(1L));

        mockMvc.perform(post("/shipments")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("\"accountId\": 12, \"addressId\": 15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.shipmentId", is(1)));
    }

    @Test
    public void deleteShipment_HappyPath() throws Exception{
        mockMvc.perform(delete("/shipments/15"))
                .andExpect(status().isNoContent());

        verify(shipmentService, times(1)).deleteShipment(15L);
    }

    //Helper Methods
    private Shipment getMockShipment(Long shipmentId){
        Shipment mockShipment = new Shipment(12345L, 12L, LocalDate.of(2018,8,15));
        mockShipment.setShippedDate(LocalDate.of(2018, 8, 1));
        mockShipment.setShipmentId(shipmentId);
        return mockShipment;
    }

    private List<Shipment> getMockShipmentList(){
        return new ArrayList<Shipment>(){{
            add(getMockShipment(11L));
            add(getMockShipment(12L));
            add(getMockShipment(13L));
            add(getMockShipment(14L));
            add(getMockShipment(15L));
        }};
    }
}
