package com.develogical.camera;

public class Camera {


    private Sensor sensor;

    public Camera(Sensor sensor) {
        this.sensor = sensor;
    }

    public void pressShutter() {
        // not implemented
    }

    public void powerOn() {
        sensor.powerUp();
    }

    public void powerOff() {
        sensor.powerDown();
    }
}

