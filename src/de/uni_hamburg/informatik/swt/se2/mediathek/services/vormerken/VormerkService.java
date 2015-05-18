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
    
    /**
     * Initialisiert den Vormerkservice und erstellt eine Map von Vormerkkarten.
     */
    public VormerkService() {
        _vormerkKarten = new HashMap<>();
    }
    
    /**
     * Lässt einen Kunden ein Medium vormerken.
     * @param medium
     * @param kunde
     * @require medium != null
     * @require kunde != null
     * @throws VormerkkarteVollException
     */
    public void merkeVor(Medium medium, Kunde kunde) throws VormerkkarteVollException 
    {
    	assert kunde!= null : "Vorbedingung verletzt: kunde != null";
    	assert medium != null : "Vorbedingung verletzt: medium != null";
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
    
    /**
     * Prüft ob eine Vormerkung für einen Kunden auf ein bestimmtes Medium möglich ist
     * @param medium
     * @param kunde
     * @require medium != null
     * @require kunde != null
     * @return true, wenn es möglich ist; false, sonst
     */
    public boolean istVormerkenMoeglich(Medium medium, Kunde kunde)
    {
    	assert kunde!= null : "Vorbedingung verletzt: kunde != null";
    	assert medium != null : "Vorbedingung verletzt: medium != null";
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
    
    /**
     * Prüft ob eine Stornierung einer Vormerkung für einen Kunden auf ein bestimmtes Medium möglich ist
     * @param medium
     * @param kunde
     * @require medium != null
     * @require kunde != null
     * @return true, wenn es möglich ist; false, sonst
     */
    public boolean istStornierenMoeglich(Medium medium, Kunde kunde) 
    {
    	assert kunde!= null : "Vorbedingung verletzt: kunde != null";
    	assert medium != null : "Vorbedingung verletzt: medium != null";
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
    
    /**
     * Gibt die Liste der Vormerker für ein bestimmtes Medium zurück.
     * @param medium
     * @require medium != null
     * @return Liste von Vormerkern
     */
    public List<Kunde> getVormerker(Medium medium) 
    {
    	assert medium != null : "Vorbedingung verletzt: medium != null";
    	if (_vormerkKarten.get(medium) != null) {
    		return _vormerkKarten.get(medium).getVormerker();
    	}
    	return null;
    }
    
    /**
     * Prüft, ob der Kunde der erste Vormerker ist.
     * @param medien
     * @param kunde
     * @require medien != null
     * @require kunde != null
     * @return true, wenn es möglich ist; false, sonst
     */
    public boolean istKundeErsterVormerker(List<Medium> medien, Kunde kunde)
    {
    	assert kunde!= null : "Vorbedingung verletzt: kunde != null";
    	assert medien != null : "Vorbedingung verletzt: medien != null";
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
    
    /**
     * Entfernt die erste Vormerkung auf der Vormerkkarte eines Mediums.
     * @param medium
     * @require medium != null
     */
    public void entferneErsteVormerkung(Medium medium)
    {
    	assert medium != null : "Vorbedingung verletzt: medium != null";
    	if (_vormerkKarten.get(medium) != null) {
    		_vormerkKarten.get(medium).rueckeVormerkungAuf();
    	}
    }
    
    /**
     * Storniert die Vormerkung eines Kunden auf ein bestimmtes Medium
     * @param medien
     * @param kunde
     * @require medien != null
     * @require kunde != null
     */
    public void storniereVormerkung(List<Medium> medien, Kunde kunde) 
    {
    	assert kunde!= null : "Vorbedingung verletzt: kunde != null";
    	assert medien != null : "Vorbedingung verletzt: medien != null";
    	for (Medium medium : medien) {
    		if (_vormerkKarten.get(medium) != null)
    		{
    			_vormerkKarten.get(medium).nimmVormerkungWeg(kunde);
    		}
		}
    	informiereUeberAenderung();
    }
}
