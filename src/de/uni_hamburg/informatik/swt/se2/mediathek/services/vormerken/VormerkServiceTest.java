package de.uni_hamburg.informatik.swt.se2.mediathek.services.vormerken;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Datum;
import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.DatumTest;
import de.uni_hamburg.informatik.swt.se2.mediathek.fachwerte.Kundennummer;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Kunde;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.Verleihkarte;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.CD;
import de.uni_hamburg.informatik.swt.se2.mediathek.materialien.medien.Medium;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.kundenstamm.KundenstammService;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.kundenstamm.KundenstammServiceImpl;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.medienbestand.MedienbestandService;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.medienbestand.MedienbestandServiceImpl;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.verleih.ProtokollierException;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.verleih.VerleihService;
import de.uni_hamburg.informatik.swt.se2.mediathek.services.verleih.VerleihServiceImpl;

public class VormerkServiceTest {
	
	private Kunde _kunde;
	private Kunde _kunde2;
	private Kunde _kunde3;
	private Kunde _kunde4;
	private VormerkService _vormerkService;
	private VerleihService _verleihService;
    private List<Medium> _medienListe;
	
	public VormerkServiceTest()
	{
        KundenstammService kundenstamm = new KundenstammServiceImpl(
                new ArrayList<Kunde>());
        
        _kunde = new Kunde(new Kundennummer(123456), "ich", "du");
        _kunde2 = new Kunde(new Kundennummer(666999), "paul", "panter");
        _kunde3 = new Kunde(new Kundennummer(523985), "benfi", "calis");
        _kunde4 = new Kunde(new Kundennummer(234324), "peter", "heini");
        
        kundenstamm.fuegeKundenEin(_kunde);
        kundenstamm.fuegeKundenEin(_kunde2);
        kundenstamm.fuegeKundenEin(_kunde3);
        kundenstamm.fuegeKundenEin(_kunde4);
        
        MedienbestandService medienbestand = new MedienbestandServiceImpl(
                new ArrayList<Medium>());
        Medium medium = new CD("CD1", "baz", "foo", 123);
        medienbestand.fuegeMediumEin(medium);
        medium = new CD("CD2", "baz", "foo", 123);
        medienbestand.fuegeMediumEin(medium);
        medium = new CD("CD3", "baz", "foo", 123);
        medienbestand.fuegeMediumEin(medium);
        medium = new CD("CD4", "baz", "foo", 123);
        medienbestand.fuegeMediumEin(medium);
        medium = new CD("CD5", "baz", "foo", 123);
        medienbestand.fuegeMediumEin(medium);
        _vormerkService = new VormerkService();
        _medienListe = medienbestand.getMedien();
        _verleihService = new VerleihServiceImpl(kundenstamm, medienbestand,
                new ArrayList<Verleihkarte>(), _vormerkService);
	}
	
    @Test
    public void testeNachInitialisierungIstNichtsVorgemerkt() 
    {
        assertTrue(_vormerkService.getVormerker(_medienListe.get(0)) == null);
    }
    
    @Test
    public void testeVormerkenUndStornieren() throws Exception
    {
    	_vormerkService.merkeVor(_medienListe.get(0), _kunde);
    	assertTrue(_vormerkService.getVormerker(_medienListe.get(0)).contains(_kunde));
    	_vormerkService.storniereVormerkung(_medienListe.subList(0, 1), _kunde);
    	assertFalse(_vormerkService.getVormerker(_medienListe.get(0)).contains(_kunde));
    }
    
    @Test
    public void testeMaximalDreiUnterschiedlicheKunden() 
    {
    	Medium medium = _medienListe.get(0);
    	try {
        	_vormerkService.merkeVor(medium, _kunde);
        	_vormerkService.merkeVor(medium, _kunde2);
        	_vormerkService.merkeVor(medium, _kunde3);
        	_vormerkService.merkeVor(medium, _kunde4);
    	} catch (VormerkkarteVollException ex) {
    		System.err.println(ex.getMessage());
    	}
    	assertFalse(_vormerkService.getVormerker(medium).contains(_kunde4));
    }
	
    @Test
    public void testeVomVerleihStatusUnabhaengigeVormerkungMoeglich() throws Exception
    {
    	Medium medium = _medienListe.get(1);
    	_vormerkService.merkeVor(medium, _kunde);
    	assertTrue(_vormerkService.getVormerker(_medienListe.get(1)).contains(_kunde));
    	_verleihService.verleiheAn(_kunde, _medienListe.subList(1, 2), new Datum(1, 2 ,2013));
    	_vormerkService.merkeVor(medium, _kunde2);
       	assertTrue(_vormerkService.getVormerker(_medienListe.get(1)).contains(_kunde2));
    }
    
    @Test
    public void testeAusleiherKannNichtVormerken() throws Exception
    {
    	Medium medium = _medienListe.get(2);
    	_verleihService.verleiheAn(_kunde, _medienListe.subList(2, 3), new Datum(1, 2 ,2013));
    	_vormerkService.merkeVor(medium, _kunde);
    	assertFalse(_vormerkService.getVormerker(medium).contains(_kunde));
    }
    
    @Test
    public void testeAusleihenNurAnErstenVormerker() throws Exception
    {
    	Medium medium = _medienListe.get(3);
    	_vormerkService.merkeVor(medium, _kunde);
    	_verleihService.verleiheAn(_kunde2, _medienListe.subList(3, 4), new Datum(1, 2 ,2013));
    	assertTrue(_verleihService.getEntleiherFuer(medium) == null);
    	_verleihService.verleiheAn(_kunde, _medienListe.subList(3, 4), new Datum(1, 2 ,2013));
    	assertTrue(_verleihService.getEntleiherFuer(medium) == _kunde);
    }
    
    @Test
    public void testeRueckeVorBeiAusleihen() throws Exception
    {
    	Medium medium = _medienListe.get(4);
    	_vormerkService.merkeVor(medium, _kunde);
    	_vormerkService.merkeVor(medium, _kunde2);
    	_vormerkService.merkeVor(medium, _kunde3);
    	
    	_verleihService.verleiheAn(_kunde, _medienListe.subList(4, 5), new Datum(1, 2 ,2013));
    	assertTrue(_vormerkService.getVormerker(medium).get(0) == _kunde2);
    }
}
