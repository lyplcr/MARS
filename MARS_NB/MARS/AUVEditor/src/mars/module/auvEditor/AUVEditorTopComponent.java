package mars.module.auvEditor;

import com.jme3.renderer.ViewPort;
import com.jme3.system.awt.AwtPanel;
import com.jme3.system.awt.AwtPanelsContext;
import com.jme3.system.awt.PaintMode;
import java.awt.Dimension;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Callable;
import mars.MARS_Main;
import mars.auv.BasicAUV;
import mars.core.CentralLookup;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;

/**
 * Top component which displays something.
 *
 * @author Christian Friedrich <friedri1 at informatik.uni-luebeck.de>
 * @author Alexander Bigerl <bigerl at informatik.uni-luebeck.de>
 */
@ConvertAsProperties(
        dtd = "-//mars.module.auvEditor//AUVEditor//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "AUVEditorTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "mars.module.auvEditor.AUVEditorTopComponent")
//@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_AUVEditorAction",
        preferredID = "AUVEditorTopComponent")
@Messages({
    "CTL_AUVEditorAction=AUVEditor",
    "CTL_AUVEditorTopComponent=AUVEditor Window",
    "HINT_AUVEditorTopComponent=This is a AUVEditor window"
})
public final class AUVEditorTopComponent extends TopComponent implements LookupListener {

    private Lookup.Result<MARS_Main> result = null;
    private MARS_Main mars = null;
    private static AwtPanel auvedpanel;
    private AUVEditorAppState appState;
    private AwtPanelsContext ctx;

    public AUVEditorTopComponent() {
        initComponents();
        setName(Bundle.CTL_AUVEditorTopComponent());
        setToolTipText(Bundle.HINT_AUVEditorTopComponent());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
        //register listener
        Lookup.Template template = new Lookup.Template(MARS_Main.class);
        CentralLookup cl = CentralLookup.getDefault();
        result = cl.lookup(template);
        result.addLookupListener(this);
        System.out.println("LOKKUP3!");
        if (mars == null) {// try to get mars, else its the listener
            System.out.println("LOKKUP4!");
            mars = cl.lookup(MARS_Main.class);
            if (mars != null) {//succesfull lookup?
                initState(mars);
            } else {//no, we failed
            }
        }
    }

    private void initState(final MARS_Main mars) {
        //mars.enqueue(new Callable<Void>() {
        //      public Void call() {
        ctx = (AwtPanelsContext) mars.getContext();
        auvedpanel = ctx.createPanel(PaintMode.Accelerated);
        auvedpanel.setPreferredSize(new Dimension(640, 480));
        auvedpanel.setMinimumSize(new Dimension(640, 480));
        auvedpanel.transferFocus();
        jPanel1.add(auvedpanel);
        appState = new AUVEditorAppState();
        appState.setEnabled(true);
        final ViewPort viewPort = mars.addState(appState);
        ctx.setInputSource(auvedpanel);

        mars.enqueue(new Callable<Void>() {
            public Void call() {
                viewPort.attachScene(appState.getRootNode());
                auvedpanel.attachTo(false, viewPort);
                return null;
            }
        });
        System.out.println("testnew: " + mars);
        //            return null;
        //    }
        //});
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    @Override
    protected void componentShowing() {
        super.componentShowing(); //To change body of generated methods, choose Tools | Templates.
        if (ctx != null) {
            auvedpanel.transferFocus();
            ctx.setInputSource(auvedpanel);
        }
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
        System.out.println("LOKKUP1!");
        if (mars == null) {//only check if we dont have mars
            System.out.println("LOKKUP2!");
            Lookup.Result res = (Lookup.Result) le.getSource(); //this is always an safe cast!
            Collection instances = res.allInstances(); //we get all instances from the lookup

            if (!instances.isEmpty()) {
                Iterator it = instances.iterator();
                while (it.hasNext()) {
                    Object o = it.next();
                    if (o instanceof MARS_Main) {//you might wan to use this – better safe than sorry, check if you got what you expected!
                        System.out.println("testM: " + (MARS_Main) o);
                        mars = (MARS_Main) o;
                        initState(mars);
                    }
                }
            }
        }
    }

    public void setAUV(BasicAUV auv) {
        appState.setAUV(auv);
    }
}
