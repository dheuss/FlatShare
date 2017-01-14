package com.flatshare.network.path.database;

/**
 * Created by Arber on 14/01/2017.
 */

public class MatchesNode {

    private final String rootPath;
    private final String appointmentsList;
    private final String appointmentSet;
    private final String appointment;

    private final String DELIMITER = ":";


    public MatchesNode(String rootPath, String tenantId, String apartmentId) {
        this.rootPath = rootPath + tenantId + DELIMITER + apartmentId + "/";
        this.appointmentsList = this.rootPath + "appointments_list";
        this.appointmentSet = this.rootPath + "appointment_set";
        this.appointment = this.rootPath + "appointment";
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getAppointmentsList() {
        return appointmentsList;
    }

    public String getAppointmentSet() {
        return appointmentSet;
    }

    public String getAppointment() {
        return appointment;
    }
}
