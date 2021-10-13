package de.tum.in.ase.eist;

import static org.junit.jupiter.api.Assertions.*;

import java.time.*;
import java.util.*;

import org.junit.jupiter.api.*;

class RiderTest {


    @Test //Test that the method rent() creates a new Rental object with correct dates and PEV, also the Rental object is added to the rentals list.
    public void testRent(){
        var from = LocalDateTime.of(2028, 11, 28, 15, 32);
        var to = LocalDateTime.of(2028, 11, 28, 16, 32);
        DriversLicense driversLicensetest = new DriversLicense(LocalDateTime.of(2025, 11, 26, 15, 53), "yrrrrrr");
        PEV eBiketest = new EBike(100, "hey");
        Rider testRider = new Rider("hello", 28, true, driversLicensetest);
        testRider.rent(eBiketest, from, to);
        assertEquals(eBiketest, testRider.getRentals().get(0).getRentedPEV());
        assertEquals(from, testRider.getRentals().get(0).getFrom());
        assertEquals(to, testRider.getRentals().get(0).getTo());
        assertEquals(1, testRider.getRentals().size());
        /*Rider rider = new Rider("kerem",20,true,null);
        PEV pev = new EMoped(100, "kerem");
        pev.rent(LocalDateTime.now(),LocalDateTime.now().plusHours(1),rider);
        Rental expected = new Rental(LocalDateTime.now(),LocalDateTime.now().plusHours(1),pev,rider);
        assertEquals(expected.getRentedPEV(),pev);

        assertEquals(pev.getRentals(),expected.getRentedPEV().getRentals());*/

    }

    @Test //test that a Rental object cannot be added to the rider's rentals list manually using the methods of the List interface
    public void listTest(){
        Rider rider = new Rider("kerem",17,true,null);
        PEV pev = new EMoped(100, "kerem");
        Rental rental = new Rental(LocalDateTime.now(),LocalDateTime.now().plusHours(1),pev,rider);
        assertThrows(UnsupportedOperationException.class, () ->rider.getRentals().add(rental));


    }
}
