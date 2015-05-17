package de.uni_hamburg.informatik.swt.se2.mediathek.services.vormerken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Kunde;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Vormerkkarte;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.AbstractObservableService;

public class VormerkService extends AbstractObservableService
{
    
    private Map<Medium, Vormerkkarte>_vormerkKarten;
    
    public VormerkService() {
        _vormerkKarten = new HashMap<>();
    }
    
    public void merkeVor(Medium medium, Kunde kunde) throws VormerkkarteVollException 
    {
    	Vormerkkarte vormerkkarte = _vormerkKarten.get(medium);
    	if (vormerkkarte != null) {
    		vormerkkarte.fuegeKundenHinzu(kunde);
    	} else {
    		vormerkkarte = new Vormerkkarte();
    		vormerkkarte.fuegeKundenHinzu(kunde);
    		_vormerkKarten.put(medium, vormerkkarte);
    	}
    	 informiereUeberAenderung();
    }
    
    public boolean istVormerkenMoeglich(Medium medium, Kunde kunde)
    {
    	Vormerkkarte vormerkkarte = _vormerkKarten.get(medium);
    	if (vormerkkarte != null) 
    	{
    		return (vormerkkarte.getAnzahlVormerker() < 3) && !vormerkkarte.getVormerker().contains(kunde);
    	} 
    	else 
    	{
    		return true;
    	}
    }
    
    public boolean istStornierenMoeglich(Medium medium, Kunde kunde) 
    {
    	Vormerkkarte vormerkkarte = _vormerkKarten.get(medium);
    	if (vormerkkarte != null)
    	{
    		return vormerkkarte.getVormerker().contains(kunde);
    	}
    	else 
    	{
    		return false;
    	}
    }
    
    public List<Kunde> getVormerker(Medium medium) 
    {
    	if (_vormerkKarten.get(medium) != null) {
    		return _vormerkKarten.get(medium).getVormerker();
    	}
    	return null;
    }
    
    public boolean istKundeErsterVormerker(List<Medium> medien, Kunde kunde)
    {
    	boolean istErsterVormerker = true;
    	for (Medium medium : medien) {
    		if (_vormerkKarten.get(medium) != null) {
    			ArrayList<Kunde> vormerker = _vormerkKarten.get(medium).getVormerker();
            	if (!(kunde == vormerker.get(0))) {
                	return false;
            	}
    		}
		}
    	return istErsterVormerker;
    }
    
    public void entferneErsteVormerkung(Medium medium)
    {
    	if (_vormerkKarten.get(medium) != null) {
    		_vormerkKarten.get(medium).rueckeVormerkungAuf();
    	}
    }
    
    public void storniereVormerkung(List<Medium> medien, Kunde kunde) 
    {
    	for (Medium medium : medien) {
    		if (_vormerkKarten.get(medium) != null)
    		{
    			_vormerkKarten.get(medium).nimmVormerkungWeg(kunde);
    		}
		}
    	informiereUeberAenderung();
    }
}
