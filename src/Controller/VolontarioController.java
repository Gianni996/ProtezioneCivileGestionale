package Controller;


import Controller.Compiti.*;
import Model.Volontario;
import View.BasicFrameView;
import View.UtenteSinistraView;
import View.VolontarioDView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Controller per la Home del volontario
 */
public class VolontarioController{

    private BasicFrameView basicframe;
    private int DatiPersonali;
    private Volontario Utente;
    private VolontarioDView Dview;
    private UtenteSinistraView Sview;
    private ArrayList<String> BROADCAST;
    private ArrayList<String> MESSAGGI;

    /*COSTRUTTORI*/

    /*costruttore vuoto*/
    public VolontarioController() {

        return;

    }

    public VolontarioController(BasicFrameView frame, Volontario utente){

        basicframe = frame;
        DatiPersonali = 0;
        Utente = utente;

        if(!Primoaccesso()){
            InizializzaGUI();
        }

    }

    /**
     * Metodo che controlla se l utente è la prima volta che effettua l accesso come volontario
     *
     * @return true  primo acccesso
     * @return false non primo accesso
     */
    private boolean Primoaccesso(){

        boolean controllo = false;
        if(Utente.getPrimoaccesso().equals("si")) {

            controllo = true;
            AnagraficaController controller;
            controller = new AnagraficaController(basicframe, VolontarioController.this, Utente);

            basicframe.Message("Ciao sei appena diventato un Volontario!\nPer completa la tua iscrizione " +
                    "completa la sezione D di seguito riportata");

        }

        return controllo;

    }

    /**
     * Inizializza la home del volontario
     */
    public void InizializzaGUI(){

        Dview = new VolontarioDView();
        Sview = new UtenteSinistraView();

        Sview.VisibilitaEvolvi(false);

        Dview.setNOMEVOLabel(Utente.getNome());
        Dview.setCOGNOMEVOLabel(Utente.getCognome());
        Dview.setSTATOLabel(Utente.getStato());
        Dview.setRUOLOVOLabel(Utente.getRuolo());


        //selezione broadcast
        BROADCAST = Utente.getBROADCAST();

        if(BROADCAST.size() !=0)
                Dview.setBroadcast(BROADCAST);


        basicframe.setdestra(Dview.getIntermedio0());
        basicframe.setsinistra(Sview.getIntermedio0());

        //selezione messaggi
        MESSAGGI = Utente.getMESSAGGI();

        if(MESSAGGI.size() !=0){

            Dview.seteMessaggi(MESSAGGI);
            for(String messaggio: MESSAGGI) {

                //pone la lettura del messaggio a si
                String[] appoggio = new String[3];

                appoggio[0] = "messaggi";
                appoggio[1] = "letto";
                appoggio[2] = "si";

                if(Utente.UpdateSQL(appoggio))
                    System.out.print("ok");
            }

            basicframe.Message("Hai "+MESSAGGI.size()+" messaggi! Vedili nella sezione messaggi");
        }

        Selezionecompiti();

        VolontarioControllerListener();
    }

    /**
     * Controlla quali compiti possiede il volontaruio e setta i bottoni dei compiti di conseguenza(visibili o non visibili)
     */
    private void Selezionecompiti(){

        if(Utente.getArchivista().equals("si")) {

            Dview.VisibilitaArchivistaButton(true);
            ArchivistaListner();

        }

        if(Utente.getAdd_giunta().equals("si")) {

            Dview.VisibilitaGiuntaButton(true);
            Add_GiuntaListener();

        }

        if(Utente.getReferenteinformatico().equals("si")) {

            Dview.VisibilitaReferenteInformaticoButton(true);
            ReferenteInformaticoListner();

        }

        if(Utente.getRuolo().equals("Cordinatore") || Utente.getRuolo().equals("Vicecordinatore")) {

            Dview.VisibilitaMasterChiefButton(true);
            MasterChiefListner();

        }

        if(Utente.getRuolo().equals("Admin") ){

            Dview.VisibilitaMCPButton(true);
            MCPListener();

        }
    }

    /**
     * Ascolto delle azioni del volontario
     * Botton:Datipersonali,logout,home,cambiastato
     */
    private void VolontarioControllerListener(){

        /*DatiPersonali*/
        JButton DatiPersonaliButton = Sview.getDatiPersonaliButton();
        DatiPersonaliButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (DatiPersonali == 0){

                    DatiPersonali = 1;

                    AnagraficaController datipersonali;
                    datipersonali = new AnagraficaController(basicframe, Dview, Utente);
                }

            }

        });

        /*Logout*/
        JButton Logout = Sview.getLogoutButton();
        Logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                LogoutAction();
            }

        });

        JButton Home = Sview.getHomeButton();
        Home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DatiPersonali = 0;
                basicframe.setdestra(Dview.getIntermedio0());

            }

        });

        JButton cambiastato = Dview.getCambiaButton();
        cambiastato.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                CambiaStatoController controller;
                controller = new CambiaStatoController(basicframe , Utente, Dview);

            }

        });

    }

    /*
     * A seguire tutti i listener dei vari compiti
     *  Archivista
     *  Referente informatico
     *  Master chief
     *  Add Giunta
     */
    private void ArchivistaListner(){

        JButton Archivista = Dview.getArchivistaButton();
        Archivista.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Controller.Compiti.Archivista controller;
                controller = new Archivista(basicframe, Utente);

            }

        });
    }

    private void ReferenteInformaticoListner() {

        JButton ReferenteInformatico = Dview.getReferenteInformaticoButton();
        ReferenteInformatico.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Referenteinformatico controller;
                controller = new Referenteinformatico(basicframe);

            }

        });
    }

    private void MasterChiefListner(){

        JButton MasterChief = Dview.getMaster_ChiefButton();
        MasterChief.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MC controller;
                controller = new MC(basicframe, Utente);

            }

        });
    }

    private void Add_GiuntaListener(){

        JButton Add_Giunta = Dview.getAddGiuntaComunaleButton();
        Add_Giunta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Add_Giunta controller;
                controller = new Add_Giunta(basicframe, Utente);

            }

        });
    }

    private void MCPListener(){

        JButton mcp = Dview.getMaster_Chief_PlusButton();
        mcp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MCP controller;
                controller = new MCP(basicframe, Utente, Dview);

            }

        });
    }

    private void LogoutAction(){

        if(basicframe.OpotionalMessage("Vuoi davvero uscire?") == 0) {
            LoginController loginController = new LoginController(basicframe);
        }

    }

    @Override
    public String toString() {
        return "VolontarioController{" +
                "basicframe=" + basicframe +
                ", DatiPersonali=" + DatiPersonali +
                ", Utente=" + Utente +
                ", Dview=" + Dview +
                ", Sview=" + Sview +
                ", BROADCAST=" + BROADCAST +
                ", MESSAGGI=" + MESSAGGI +
                '}';
    }

    //se hanno lo stesso pannello di destra e di sinistra allora "controllano" la medesima GUI
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VolontarioController that = (VolontarioController) o;

        if (Dview != null ? !Dview.equals(that.Dview) : that.Dview != null) return false;
        return Sview != null ? Sview.equals(that.Sview) : that.Sview == null;
    }

}
