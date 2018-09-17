package com.solstice.ecommerceshipment.service;

import com.solstice.ecommerceshipment.data.ShipmentRepository;
import com.solstice.ecommerceshipment.domain.Shipment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ShipmentServiceUnitTest {

    @MockBean
    private ShipmentRepository shipmentRepository;

    private ShipmentService shipmentService;

    @Before
    public void setup(){
        shipmentService = new ShipmentService(shipmentRepository);
    }

    @Test
    public void getOneShipment_HappyPath(){
        when(shipmentRepository.getOne(15L)).thenReturn(getMockShipment(15L));

        Shipment foundShipment = shipmentService.getOneShipment(15L);

        assertThat(foundShipment.getShipmentId(), is(15L));
        assertThat(foundShipment.getAccountId(), is(12345L));
        assertThat(foundShipment.getAddressId(), is(12L));
        assertThat(foundShipment.getShippedDate(), is(LocalDate.of(2018,8,1)));
        assertThat(foundShipment.getDeliveryDate(), is(LocalDate.of(2018,8,15)));
        assertThat(foundShipment.getLineItems(), is(nullValue()));
    }

    @Test
    public void getAllShipmentsForAccount_HappyPath(){
        when(shipmentRepository.findAllByAccountIdOrderByDeliveryDateDesc(12345L)).thenReturn(getMockShipmentList());

        List<Shipment> foundShipments = shipmentService.getAllShipmentsForAccount(12345L);
        assertThat(foundShipments.size(), is(5));
        assertThat(foundShipments.get(0).getShipmentId(), is(11L));
        assertThat(foundShipments.get(1).getShipmentId(), is(12L));
        assertThat(foundShipments.get(2).getShipmentId(), is(13L));
        assertThat(foundShipments.get(3).getShipmentId(), is(14L));
        assertThat(foundShipments.get(4).getShipmentId(), is(15L));
    }

    @Test
    public void updateShipment_HappyPath(){
        when(shipmentRepository.getOne(12L)).thenReturn(getMockShipment(12L));
        Shipment shipmentUpdateData = new Shipment(null, null, LocalDate.of(2018,9,1));

        Shipment updatedShipment = shipmentService.updateShipment(12L, shipmentUpdateData);

        assertThat(updatedShipment.getShipmentId(), is(12L));
        assertThat(updatedShipment.getDeliveryDate(), is(LocalDate.of(2018,9,1)));
    }

    @Test
    public void deleteShipment_HappyPath(){
        when(shipmentRepository.getOne(12L)).thenReturn(getMockShipment(12L));

        shipmentService.deleteShipment(12L);

        verify(shipmentRepository, times(1)).deleteById(12L);
    }

    @Test
    public void createShipment_HappyPath(){
        when(shipmentRepository.save(any(Shipment.class))).thenReturn(getMockShipment(15L));
        Shipment toSave = new Shipment(12345L, 12L, null);

        Shipment savedShipment = shipmentService.createShipment(toSave);

        assertThat(savedShipment.getShipmentId(), is(15L));
        assertThat(savedShipment.getAccountId(), is(12345L));
        assertThat(savedShipment.getAddressId(), is(12L));
        assertThat(savedShipment.getShippedDate(), is(LocalDate.of(2018,8,1)));
        assertThat(savedShipment.getDeliveryDate(), is(LocalDate.of(2018,8,15)));
        assertThat(savedShipment.getLineItems(), is(nullValue()));
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
