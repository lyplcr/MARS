/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.testmod.lookuptopcomponent;

import java.util.Collection;
import java.util.Iterator;
import mars.MARS_Main;
import mars.core.CentralLookup;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//mars.testmod.lookuptopcomponent//Lookup//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "LookupTopComponent",
        iconBase = "",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "mars.testmod.lookuptopcomponent.LookupTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_LookupAction",
        preferredID = "LookupTopComponent")
@Messages({
    "CTL_LookupAction=Lookup",
    "CTL_LookupTopComponent=Lookup Window",
    "HINT_LookupTopComponent=This is a Lookup window"
})
public final class LookupTopComponent extends TopComponent implements LookupListener{

    private Lookup.Result<MARS_Main> result = null;
    private MARS_Main mars = null;
    
    public LookupTopComponent() {
        initComponents();
        setName(Bundle.CTL_LookupTopComponent());
        setToolTipText(Bundle.HINT_LookupTopComponent());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        //MARS_Main mars = WindowManager.getDefault().findTopComponent("MARSTopComponent").getLookup().lookup(MARS_Main.class);
         //System.out.println("test: " + mars);
        //result = Utilities.actionsGlobalContext().lookupResult(MARS_Main.class);
        //result.addLookupListener (this);
        
        //register listener
        Lookup.Template template = new Lookup.Template(MARS_Main.class);
        CentralLookup cl = CentralLookup.getDefault();
        result = cl.lookup(template);
        result.addLookupListener(this);
        if(mars == null){// try to get mars, else its the listener
            mars = cl.lookup(MARS_Main.class);
            System.out.println("testnew: " + mars);
        }
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
    
    @Override
    public void resultChanged(LookupEvent le) {
         if(mars == null){//only check if we dont have mars
            System.out.println("LOKKUP!");
            Lookup.Result res = (Lookup.Result) le.getSource(); //this is always an safe cast!
            Collection instances = res.allInstances(); //we get all instances from the lookup

            if (!instances.isEmpty()) {
                Iterator it = instances.iterator();
                while (it.hasNext()) {
                    Object o = it.next();
                    if(o instanceof MARS_Main){//you might wan to use this – better safe than sorry, check if you got what you expected!
                        System.out.println("testM: " + (MARS_Main)o);
                        mars = (MARS_Main)o;
                    }
                }
            }
        }

    }
}
