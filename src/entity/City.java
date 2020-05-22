package entity;

import Common.Hospital;

public class City {
    private final Community[] communities;
    private final Hospital hospital;
    private boolean travelRestriction;
    private boolean quarantine;

    public City(Community[] communities, Hospital hospital) {
        this.communities = communities;
        this.hospital = hospital;
    }

    public void visitorCome(People visitor) {
        if (!travelRestriction) {
            int rand = (int) (Math.random() * communities.length);
            communities[rand].visitorCome(visitor);
        }
    }

    public void visitorLeave(People visitor) {
        if (!quarantine) {
            visitor.community.visitorLeave(visitor);
        }
    }

    public boolean isTravelRestriction() {
        return travelRestriction;
    }

    public void setTravelRestriction(boolean travelRestriction) {
        this.travelRestriction = travelRestriction;
    }

    public boolean isQuarantine() {
        return quarantine;
    }

    public void setQuarantine(boolean quarantine) {
        this.quarantine = quarantine;
    }
}
