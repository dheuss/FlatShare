package com.flatshare.domain.datatypes.db.common;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

import java.util.List;
/**
 * Created by Arber on 06/12/2016.
 */
public class MatchEntry {

    @PropertyName("appointments_list")
    public List<Long> appointmentsList;

    @PropertyName("appointment_set")
    public boolean appointmentSet;

    @PropertyName("appointment")
    public long appointment;

    @Exclude
    public List<Long> getAppointmentsList() {
        return appointmentsList;
    }

    @Exclude
    public void setAppointmentsList(List<Long> appointmentsList) {
        this.appointmentsList = appointmentsList;
    }

    @Exclude
    public boolean isAppointmentSet() {
        return appointmentSet;
    }

    @Exclude
    public void setAppointmentSet(boolean appointmentSet) {
        this.appointmentSet = appointmentSet;
    }

    @Exclude
    public long getAppointment() {
        return appointment;
    }

    @Exclude
    public void setAppointment(long appointment) {
        this.appointment = appointment;
    }
}
