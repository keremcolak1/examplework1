package de.tum.in.ase.eist;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.jupiter.api.Assertions.*;

import java.time.*;
import java.util.*;

import org.easymock.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(EasyMockExtension.class)
class NavigationServiceTest {

    @TestSubject
    private NavigationService navigationService = new NavigationService();
    @Mock
    private RealTimePositionService realTimePositionServiceMock;

    @Test//Test that getInstructions(...) of NavigationService returns "destination reached" if the current position is equal to the destination.
    public void testNaviCurrent() {
        PEV pev = new EMoped(100,"kerem");
        expect(realTimePositionServiceMock.getX(pev)).andReturn(8);
        expect(realTimePositionServiceMock.getY(pev)).andReturn(5);
        expect(realTimePositionServiceMock.getDirection(pev)).andReturn(Direction.NORTH);
        replay(realTimePositionServiceMock);
        assertEquals("destination reached",navigationService.getInstructions(pev,new Destination(8,5,"cafe")));


    }

    @Test//Test that getInstructions(...) of NavigationService returns "continue" if the destination is straight ahead.
    public void testNaviContinue(){
        PEV pev = new EMoped(100,"kerem");
        expect(realTimePositionServiceMock.getX(pev)).andReturn(4);
        expect(realTimePositionServiceMock.getY(pev)).andReturn(0);
        expect(realTimePositionServiceMock.getDirection(pev)).andReturn(Direction.EAST);
        replay(realTimePositionServiceMock);
        assertEquals("continue",navigationService.getInstructions(pev,new Destination(8,0,"cafe")));


    }


}
