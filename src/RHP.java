import Model.Hospital;
import Model.Resident;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.ListIterator;

public class RHP {
    private ArrayList<Resident> residentList;
    private ArrayList<Hospital> hospitalList;
    private HashSet<Pair<Resident, Hospital>> matchedPairs;
    private ArrayList<Resident> notAssignedResidents;
    private ArrayList<Hospital> notFullHospitals;

    //REQUIRES: slots in all hospitals combined equal to resident count
    public RHP(ArrayList<Resident> residentList, ArrayList<Hospital> hospitalList) {
        this.residentList = residentList;
        this.hospitalList = hospitalList;
    }

    // Residents pick hospitals
    public HashSet<Pair<Resident,Hospital>> findMatches() {
        HashSet<Pair<Resident,Hospital>> retval = new HashSet<>();
        if (residentList.size() == 0) {
            matchedPairs = retval;
            return retval;
        }

        ArrayList<Hospital> smpHospitalList;
        smpHospitalList = reduceHospitalList();

        initializeEngaged(smpHospitalList);

        hospitalsPick();

        smpToRHP();

        retval = generateMatches();
        matchedPairs = retval;
        return retval;
    }

    public void printMatches() {
        if (matchedPairs == null) {
            return;
        }
        if (matchedPairs.size() == 0) {
            return;
        }
        for (Pair<Resident,Hospital> pair : matchedPairs) {
            System.out.println(pair.getKey().getName() + " is assigned to " + pair.getValue().getName());
        }
    }

    private ArrayList<Hospital> reduceHospitalList() {
        ArrayList<Hospital> retval = new ArrayList<>();
        for (Hospital hospital : hospitalList) {
            int slots = hospital.getSlots();
            for (int i = 0; i < slots; i++) {
                Hospital temp = new Hospital(1, hospital.getPreferenceList(), hospital, "temp");
                retval.add(temp);
            }
        }
        for (Resident resident : residentList) {
            ArrayList<Hospital> temp = new ArrayList<>();
            for (Hospital pHospital : resident.getPreferenceList()) {
                for (Hospital hospital : retval) {
                    if (hospital.getParent() == pHospital) {
                        temp.add(hospital);
                    }
                }
            }
            resident.setPreferenceList(temp);
        }
        return retval;
    }

    private void initializeEngaged(ArrayList<Hospital> hList) {
        notAssignedResidents = new ArrayList<>();
        for (Resident r :residentList) {
            notAssignedResidents.add(r);
            r.setHospital(null);
        }
        notFullHospitals = new ArrayList<>();
        for (Hospital h : hList) {
            notFullHospitals.add(h);
            h.clearBookedResidents();
        }
    }

    private void hospitalsPick() {
        ListIterator<Hospital> iter = notFullHospitals.listIterator();
        while (iter.hasNext()) {
            Hospital h = iter.next();
            for (Resident r : h.getPreferenceList()) {
                if (!r.isAssigned()) {
                    h.addResident(r);
                    r.setHospital(h);
                    break;
                } else if (r.isAssigned()) {
                    if (r.prefers(h)) {
                        Hospital temp = r.getHospital();
                        temp.clearBookedResidents();
                        iter.add(temp);
                        h.addResident(r);
                        r.setHospital(h);
                        break;
                    }
                }
            }
        }
    }

    private void smpToRHP() {
        for (Resident r : residentList) {
            r.setHospital(r.getHospital().getParent());
            ArrayList<Hospital> temp = new ArrayList<>();
            for (Hospital hospital : r.getPreferenceList()) {
                if (!isParentAdded(temp, hospital.getParent())) {
                    temp.add(hospital.getParent());
                }
            }
            r.setPreferenceList(temp);
        }
    }

    private Boolean isParentAdded(ArrayList<Hospital> added, Hospital h) {
        for (Hospital hospital : added) {
            if (hospital == h) {
                return true;
            }
        }
        return false;
    }

    private HashSet<Pair<Resident,Hospital>> generateMatches() {
        HashSet<Pair<Resident, Hospital>> retval = new HashSet<>();
        for (Resident r : residentList) {
            Pair<Resident,Hospital> temp = new Pair<>(r, r.getHospital());
            retval.add(temp);
        }
        return retval;
    }

    public HashSet<Pair<Resident,Hospital>> getMatchedPairs() {
        return matchedPairs;
    }
}
