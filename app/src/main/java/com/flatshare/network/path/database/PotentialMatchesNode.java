package com.flatshare.network.path.database;

/**
 * Created by Arber on 26/12/2016.
 */
public class PotentialMatchesNode {

    private final String rootPath;
    private final String tenantDecision;
    private final String ownerDecision;
    private final String nrRoommatesYes;
    private final String nrRoommatesNo;
    private final String totalNrRoommates;
    private final String timeoutInDays;

    private final String DELIMITER = ":";


    public PotentialMatchesNode(String rootPath, String tenantId, String apartmentId) {
        this.rootPath = rootPath + tenantId + DELIMITER + apartmentId + "/";
        this.tenantDecision = this.rootPath + "tenant_decision/";
        this.ownerDecision = this.rootPath + "owner_decision/";
        this.nrRoommatesYes = this.rootPath + "nr_roommates_yes/";
        this.nrRoommatesNo = this.rootPath + "nr_roommates_no/";
        this.totalNrRoommates = this.rootPath + "total_nr_roommates/";
        this.timeoutInDays = this.rootPath + "timeout_in_days/";
    }

    public String getRootPath() {
        return rootPath;
    }

    public String getTenantDecision() {
        return tenantDecision;
    }

    public String getOwnerDecision() {
        return ownerDecision;
    }

    public String getNrRoommatesYes() {
        return nrRoommatesYes;
    }

    public String getNrRoommatesNo() {
        return nrRoommatesNo;
    }

    public String getTotalNrRoommates() {
        return totalNrRoommates;
    }

    public String getTimeoutInDays() {
        return timeoutInDays;
    }
}
