package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import java.util.ArrayList;
import java.util.List;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.vormerken.VormerkkarteVollException;

public class Vormerkkarte
{
	private ArrayList<Kunde> _vormerker;

	/**
	 * Initialisiert eine neue Vormerkkarte.
	 */
    public Vormerkkarte()
    {
        _vormerker = new ArrayList<>();
    }

    /**
     * Fügt einen Kunden zu den Vormerkern hinzu.
     * @param kunde
     * @require kunde != null
     * @throws VormerkkarteVollException
     */
    public void fuegeKundenHinzu(Kunde kunde) throws VormerkkarteVollException
    {
    	assert kunde!= null : "Vorbedingung verletzt: kunde != null";
        if (getAnzahlVormerker() < 3)
        {
            _vormerker.add(kunde);
        }
        else 
        {
            throw new VormerkkarteVollException();
        }
    }
    
    /**
     * Der erste Vormerker wird gelöscht und die anderen Vormerker rücken eine Position auf.
     */
    public void rueckeVormerkungAuf()
    {
    	_vormerker.remove(0);
    }
    
    /**
     * Gibt die Anzahl der Vormerker zurück
     * @return Anzahl Vormerker
     */
    public int getAnzahlVormerker() 
    {
        return _vormerker.size();
    }

    /**
     * Gibt die Liste von Vormerkern zurück.
     * @return Liste von Vormerker
     */
    public ArrayList<Kunde> getVormerker() 
    {
        return _vormerker;
    }
    
    /**
     * Storniert die Vormerkung eines Kunden
     * @param kunde
     * @require kunde != null
     */
    public void nimmVormerkungWeg(Kunde kunde)
    {
    	assert kunde!= null : "Vorbedingung verletzt: kunde != null";
        _vormerker.remove(kunde);
    }
}
