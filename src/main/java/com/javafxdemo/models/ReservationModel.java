package com.javafxdemo.models;

import java.security.Timestamp;

//This class is based on the Reservation table in the database, however we haven't worked with this one since we had to limit our project to the project requirements.
public class ReservationModel {
    private int idReservation;
    private Timestamp dateOfReservation;
    private Timestamp pickUpAvailDate;
    private Timestamp pickUpExpDate;


    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }


    public Timestamp getDateOfReservation() {
        return dateOfReservation;
    }

    public void setDateOfReservation(Timestamp dateOfReservation) {
        this.dateOfReservation = dateOfReservation;
    }


    public Timestamp getPickUpAvailDate() {
        return pickUpAvailDate;
    }

    public void setPickUpAvailDate(Timestamp pickUpAvailDate) {
        this.pickUpAvailDate = pickUpAvailDate;
    }


    public Timestamp getPickUpExpDate() {
        return pickUpExpDate;
    }

    public void setPickUpExpDate(Timestamp pickUpExpDate) {
        this.pickUpExpDate = pickUpExpDate;
    }


}
