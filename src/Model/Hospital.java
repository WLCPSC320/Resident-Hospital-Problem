package Model;

import java.util.ArrayList;

public class Hospital {
    private String name;
    private int slots;
    private ArrayList<Resident> preferenceList; // 0 is highest preference
    private Hospital parent;
    private ArrayList<Resident> bookedResidents; // 0 is highest preference

    public Hospital(int slots, String name) {
        this.slots = slots;
        this.name = name;
        parent = null;
        bookedResidents = new ArrayList<>();
    }

    public Hospital(int slots, ArrayList<Resident> preferenceList, Hospital parent, String name) {
        this.slots = slots;
        this.preferenceList = preferenceList;
        this.parent = parent;
        this.name = name;
        bookedResidents = new ArrayList<>();
    }

    // EFFECTS: Returns true if there are no more slots for additional residents
    public Boolean isFull() {
        return bookedResidents.size() >= slots;
    }

    // EFFECTS: Returns the lowest preference resident that is currently booked
    public Resident getLowestResident() {
        if (bookedResidents.size() > 0) {
            return bookedResidents.get(bookedResidents.size() - 1);
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: Adds a resident to bookedResidents if not full maintaining preference order
    public void addResident(Resident r) {
        if (bookedResidents.size() == 0 && slots > 0) {
            bookedResidents.add(r);
        } else if (bookedResidents.size() < slots){
            int index = 0;
            for (Resident resident : bookedResidents) {
                for (Resident pResident : preferenceList) {
                    if (r == pResident) {
                        bookedResidents.add(index, pResident);
                        return;
                    }
                    if (resident == pResident) {
                        index++;
                        break;
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes r from bookedResidents
    public void removeResident(Resident r) {
        bookedResidents.remove(r);
    }

    // MODIFIES: this
    // EFFECTS: removes lowest preference residence from bookedResidents
    public void removeLowestResident() {
        if (bookedResidents.size() != 0) {
            bookedResidents.remove(bookedResidents.size() - 1);
        }
    }

    // REQUIRES: bookedResidents.size() != 0
    // EFFECTS: Returns true if this prefers r over its lowest preferred resident
    public Boolean prefers(Resident r) {
        for (Resident resident : preferenceList) {
            if (resident == r) {
                return true;
            }
            if (resident == getLowestResident()) {
                return false;
            }
        }
        return false; // will never get here
    }

    public ArrayList<Resident> getPreferenceList() {
        return preferenceList;
    }

    public void setPreferenceList(ArrayList<Resident> pL) {
        preferenceList = pL;
    }

    public String getName() {
        return name;
    }

    public int getSlots() {
        return slots;
    }

    public void clearBookedResidents() {
        bookedResidents = new ArrayList<>();
    }

    public Hospital getParent() {
        return parent;
    }

    //REQUIRES: bookedResidents.size() < i
    public void setSlots(int i) {
        slots = i;
    }
}
