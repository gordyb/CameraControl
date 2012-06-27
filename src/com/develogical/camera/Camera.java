package com.develogical.camera;

public class Camera implements WriteListener {

    CameraStatus status = CameraStatus.OFF;

    private enum CameraStatus {
        ON, OFF
    }

    private boolean writing = false;
    private boolean offPending = false;

    private Sensor sensor;
    private MemoryCard memoryCard;

    public Camera(Sensor sensor, MemoryCard memoryCard) {
        this.sensor = sensor;
        this.memoryCard = memoryCard;
    }

    public void pressShutter() {
        if(status == CameraStatus.ON) {
            writing = true;
            memoryCard.write(sensor.readData());
        }
    }

    public void powerOn() {
        sensor.powerUp();
        status = CameraStatus.ON;
    }

    public void powerOff() {
        if(!writing) {
            sensor.powerDown();
            status = CameraStatus.OFF;
        } else {
            offPending = true;
        }
    }

    @Override
    public void writeComplete() {
        writing = false;
        if(offPending) {
            powerOff();
            offPending = false;
        }
    }


}

