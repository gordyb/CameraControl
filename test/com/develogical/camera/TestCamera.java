package com.develogical.camera;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Member;

@RunWith(value = JMock.class)
public class TestCamera {

    Mockery context = new Mockery();

    Sensor sensor = context.mock(Sensor.class);
    MemoryCard memoryCard = context.mock(MemoryCard.class);
    Camera camera = new Camera(sensor, memoryCard);

    @Test
    public void switchingTheCameraOnPowersUpTheSensor() {

        context.checking(new Expectations() {{
            exactly(1).of(sensor).powerUp();
        }});

        camera.powerOn();
    }

    @Test
    public void switchingTheCameraOfPowersDownTheSensor() {

        context.checking(new Expectations() {{
            exactly(1).of(sensor).powerDown();
        }});

        camera.powerOff();
    }

    @Test
    public void pressingTheShutterWithThePowerOfDoesNothing() {

        context.checking(new Expectations() {{
            exactly(0).of(sensor);
        }});

        camera.pressShutter();
    }

    @Test
    public void pressingTheShutterWithThePowerOnCopiesDataFromSensorToMemoryCard() {

        final byte[] data = new byte[0];

        context.checking(new Expectations() {{
            ignoring(sensor).powerUp();
            exactly(1).of(sensor).readData();
            will(returnValue(data));
            exactly(1).of(memoryCard).write(data);
        }});

        camera.powerOn();
        camera.pressShutter();
    }


    @Test
    public void cameraDoesNotPowerOffIfWritingToMemory() {

        final byte[] data = new byte[0];

        context.checking(new Expectations() {{
            ignoring(sensor).powerUp();
            ignoring(sensor).readData();
            ignoring(memoryCard).write(data);
            exactly(0).of(sensor).powerDown();
        }});

        camera.powerOn();
        camera.pressShutter();
        camera.powerOff();
    }

    @Test
    public void cameraPowersDownWhenWritingComplete() {

        final byte[] data = new byte[0];

        context.checking(new Expectations() {{
            ignoring(sensor).powerUp();
            ignoring(sensor).readData();
            ignoring(memoryCard).write(data);
            exactly(1).of(sensor).powerDown();
        }});

        camera.powerOn();
        camera.pressShutter();
        camera.powerOff();
        camera.writeComplete();
    }

}
