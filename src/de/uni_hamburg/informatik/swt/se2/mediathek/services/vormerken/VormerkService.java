package de.uni_hamburg.informatik.swt.se2.mediathek.services.vormerken;

import java.util.ArrayList;
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
    
    public void merkeVor(Medium medium, Kunde kunde) throws VormerkkarteVollException 
    {
    	Vormerkkarte vormerkkarte = _vormerkKarten.get(medium);
    	vormerkkarte.fuegeKundenHinzu(kunde);
    }
    
    public boolean istVormerkenMoeglich(Medium medium)
    {
    	Vormerkkarte vormerkkarte = _vormerkKarten.get(medium);
    	return vormerkkarte.getAnzahlVormerker() < 3;
        
    }

    public boolean istKundeErsterVormerker(Medium medium, Kunde kunde)
    {
    	Vormerkkarte vormerkkarte = _vormerkKarten.get(medium);
    	ArrayList<Kunde> vormerker = vormerkkarte.getVormerker();
    	return kunde == vormerker.get(0);
    }
    
    public void entferneErsteVormerkung(Medium medium)
    {
    	_vormerkKarten.get(medium).rueckeVormerkungAuf();
    }
}
