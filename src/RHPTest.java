import Model.Hospital;
import Model.Resident;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class RHPTest {
    private ArrayList<Resident> residentList;
    private ArrayList<Hospital> hospitalList;
    private ArrayList<Hospital> residentpL;
    private ArrayList<Resident> hospitalpL;
    private Resident r1;
    private Resident r2;
    private Resident r3;
    private Hospital h1;
    private Hospital h2;

    @Before
    public void runBefore() {
        r1 = new Resident("Bob");
        r2 = new Resident("Sally");
        r3 = new Resident("Derrek");
        h1 = new Hospital(1, "UBC Hospital");
        h2 = new Hospital(1, "Children's Hospital");
        residentList = new ArrayList<>();
        hospitalList = new ArrayList<>();
        residentpL = new ArrayList<>();
        hospitalpL = new ArrayList<>();
    }

    @Test
    public void testTrivialCase() {
        residentList.add(r1);
        hospitalList.add(h1);
        h1.setPreferenceList(hospitalpL);
        r1.setPreferenceList(residentpL);
        hospitalpL.add(r1);
        residentpL.add(h1);

        RHP rhp = new RHP(residentList, hospitalList);
        rhp.findMatches();
        rhp.printMatches();

        HashSet<Pair<Resident,Hospital>> expected = new HashSet<>();
        Pair<Resident,Hospital> p1 = new Pair<>(r1,h1);
        expected.add(p1);
        assertEquals(rhp.getMatchedPairs(), expected);
    }

    @Test
    public void testSmallCase1() {
        residentList.add(r1);
        residentList.add(r2);
        hospitalList.add(h1);
        hospitalList.add(h2);
        h1.setPreferenceList(hospitalpL);
        h2.setPreferenceList(hospitalpL);
        r1.setPreferenceList(residentpL);
        r2.setPreferenceList(residentpL);
        hospitalpL.add(r1);
        hospitalpL.add(r2);
        residentpL.add(h1);
        residentpL.add(h2);

        RHP rhp = new RHP(residentList, hospitalList);
        rhp.findMatches();
        rhp.printMatches();

        HashSet<Pair<Resident,Hospital>> expected = new HashSet<>();
        Pair<Resident,Hospital> p1 = new Pair<>(r1,h1);
        expected.add(p1);
        Pair<Resident,Hospital> p2 = new Pair<>(r2,h2);
        expected.add(p2);
        assertEquals(rhp.getMatchedPairs(), expected);
    }

    @Test
    public void testSmallCase2() {
        residentList.add(r1);
        residentList.add(r2);
        hospitalList.add(h1);
        hospitalList.add(h2);
        h1.setPreferenceList(hospitalpL);
        h2.setPreferenceList(hospitalpL);
        r1.setPreferenceList(residentpL);
        r2.setPreferenceList(residentpL);
        hospitalpL.add(r2);
        hospitalpL.add(r1);
        residentpL.add(h1);
        residentpL.add(h2);

        RHP rhp = new RHP(residentList, hospitalList);
        rhp.findMatches();
        rhp.printMatches();

        HashSet<Pair<Resident,Hospital>> expected = new HashSet<>();
        Pair<Resident,Hospital> p1 = new Pair<>(r1,h2);
        expected.add(p1);
        Pair<Resident,Hospital> p2 = new Pair<>(r2,h1);
        expected.add(p2);
        assertEquals(rhp.getMatchedPairs(), expected);
    }

    @Test
    public void testBigCase() {
        h2.setSlots(2);
        residentList.add(r1);
        residentList.add(r2);
        residentList.add(r3);
        hospitalList.add(h1);
        hospitalList.add(h2);
        ArrayList<Resident> hospitalpL2 = new ArrayList<>();
        ArrayList<Hospital> residentpL2 = new ArrayList<>();
        h1.setPreferenceList(hospitalpL);
        h2.setPreferenceList(hospitalpL2);
        r1.setPreferenceList(residentpL);
        r2.setPreferenceList(residentpL2);
        r3.setPreferenceList(residentpL2);
        hospitalpL.add(r1);
        hospitalpL.add(r2);
        hospitalpL.add(r3);
        residentpL.add(h1);
        residentpL.add(h2);
        hospitalpL2.add(r2);
        hospitalpL2.add(r1);
        hospitalpL2.add(r3);
        residentpL2.add(h2);
        residentpL2.add(h1);

        RHP rhp = new RHP(residentList, hospitalList);
        rhp.findMatches();
        rhp.printMatches();

        HashSet<Pair<Resident,Hospital>> expected = new HashSet<>();
        Pair<Resident,Hospital> p1 = new Pair<>(r1,h1);
        expected.add(p1);
        Pair<Resident,Hospital> p2 = new Pair<>(r2,h2);
        expected.add(p2);
        Pair<Resident,Hospital> p3 = new Pair<>(r3,h2);
        expected.add(p3);
        assertEquals(rhp.getMatchedPairs(), expected);
    }
}