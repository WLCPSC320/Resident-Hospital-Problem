package Model;

import java.util.ArrayList;

public class Resident {
    private String name;
    private ArrayList<Hospital> preferenceList; // 0 index is highest preference
    private Hospital assignedHospital;

    public Resident(String name) {
        this.name = name;
        assignedHospital = null;
    }

    public void setPreferenceList(ArrayList<Hospital> pL) {
        preferenceList = pL;
    }

    public ArrayList<Hospital> getPreferenceList() {
        return preferenceList;
    }

    public Boolean isAssigned() {
        return assignedHospital != null;
    }

    public void setHospital(Hospital h) {
        assignedHospital = h;
    }

    public Hospital getHospital() {
        return assignedHospital;
    }

    //REQUIRES: assignedHospital != null
    public Boolean prefers(Hospital h) {
        for (Hospital hospital : preferenceList) {
            if (h == hospital) {
                return true;
            }
            if (assignedHospital == hospital) {
                return false;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }
}
