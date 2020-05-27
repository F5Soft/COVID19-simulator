package entity;

import java.util.ArrayList;

public class Community {
    private final ArrayList<People> residents;
    private final ArrayList<People> visitors;
    private boolean socialDistancing;

    public Community() {
        residents = new ArrayList<>();
        visitors = new ArrayList<>();
    }

    public void residentCome(People resident) {
        residents.add(resident);
    }

    public void residentLeave(People resident) {
        residents.remove(resident);
    }

    public void visitorCome(People visitor) {
        visitors.add(visitor);
    }

    public void visitorLeave(People visitor) {
        visitors.remove(visitor);
    }

    public ArrayList<People> getPeople() {
        ArrayList<People> people = new ArrayList<>();
        people.addAll(residents);
        people.addAll(visitors);
        return people;
    }

    public boolean isSocialDistancing() {
        return socialDistancing;
    }

    public void setSocialDistancing(boolean socialDistancing) {
    }
}
