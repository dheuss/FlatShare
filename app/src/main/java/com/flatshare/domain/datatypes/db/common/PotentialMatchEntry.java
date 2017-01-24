package com.flatshare.domain.datatypes.db.common;

import com.flatshare.domain.datatypes.enums.DecisionType;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

public class PotentialMatchEntry {

    @PropertyName("potential_match_id")
    public String potentialMatchId;

    @PropertyName("tenant_decision")
    public int tenantDecision;

    @PropertyName("owner_decision")
    public int ownerDecision;

    @PropertyName("nr_roommates_yes")
    public int nrRoommatesYes;

    @PropertyName("nr_roommates_no")
    public int nrRoommatesNo;

    @PropertyName("total_nr_roommates")
    public int totalNrRoommates;

    @PropertyName("timeout_in_days")
    public int timeoutInDays;

    public PotentialMatchEntry(){
        this.tenantDecision = DecisionType.PENDING.getValue();
        this.ownerDecision = DecisionType.PENDING.getValue();
        this.totalNrRoommates = Integer.MAX_VALUE; // exaggerated...
    }

    @Exclude
    public String getPotentialMatchId() {
        return potentialMatchId;
    }

    @Exclude
    public void setPotentialMatchId(String potentialMatchId) {
        this.potentialMatchId = potentialMatchId;
    }

    @Exclude
    public int getTenantDecision() {
        return tenantDecision;
    }

    @Exclude
    public void setTenantDecision(int tenantDecision) {
        this.tenantDecision = tenantDecision;
    }

    @Exclude
    public int getOwnerDecision() {
        return ownerDecision;
    }

    @Exclude
    public void setOwnerDecision(int ownerDecision) {
        this.ownerDecision = ownerDecision;
    }

    @Exclude
    public int getNrRoommatesYes() {
        return nrRoommatesYes;
    }

    @Exclude
    public void setNrRoommatesYes(int nrRoommatesYes) {
        this.nrRoommatesYes = nrRoommatesYes;
    }

    @Exclude
    public int getNrRoommatesNo() {
        return nrRoommatesNo;
    }

    @Exclude
    public void setNrRoommatesNo(int nrRoommatesNo) {
        this.nrRoommatesNo = nrRoommatesNo;
    }

    @Exclude
    public int getTotalNrRoommates() {
        return totalNrRoommates;
    }

    @Exclude
    public void setTotalNrRoommates(int totalNrRoommates) {
        this.totalNrRoommates = totalNrRoommates;
    }

    @Exclude
    public int getTimeoutInDays() {
        return timeoutInDays;
    }

    @Exclude
    public void setTimeoutInDays(int timeoutInDays) {
        this.timeoutInDays = timeoutInDays;
    }
}
