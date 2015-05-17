package de.uni_hamburg.informatik.swt.se2.mediathek.materialien;

import java.util.ArrayList;
import java.util.List;

import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.vormerken.VormerkkarteVollException;

public class Vormerkkarte
{
    private Medium _medium;
    private ArrayList<Kunde> _vormerker;

    public Vormerkkarte()
    {
        _vormerker = new ArrayList<>();
    }

    public void fuegeKundenHinzu(Kunde kunde) throws VormerkkarteVollException
    {
        if (getAnzahlVormerker() < 3)
        {
            _vormerker.add(kunde);
        }
        else 
        {
            throw new VormerkkarteVollException();
        }
    }
    public void rueckeVormerkungAuf()
    {
    	_vormerker.remove(0);
    }
    
    public int getAnzahlVormerker() 
    {
        return _vormerker.size();
    }

    public ArrayList<Kunde> getVormerker() 
    {
        return _vormerker;
    }
    
    public void nimmVormerkungWeg(Kunde kunde)
    {
        _vormerker.remove(kunde);
    }
}
