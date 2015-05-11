package de.uni_hamburg.informatik.swt.se2.mediathek.services.vormerken;

import java.util.HashMap;
import java.util.Map;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Kunde;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Vormerkkarte;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;

public class VormerkService
{
    
    private Map<Medium, Vormerkkarte>_vormerkKarten;
    
    public VormerkService() {
        _vormerkKarten = new HashMap<>();
    }
    
    public void merkeVor(Medium medium, Kunde kunde) {
        
    }
    
    public boolean istVormerkenMoeglich(Medium medium)
    {
        return false;
    }

}
