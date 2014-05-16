/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.core;

import mars.CommunicationDeviceEvent;
import mars.CommunicationDeviceEventType;
import mars.gui.plot.PhysicalExchangerListener;
import java.util.Calendar;
import javax.swing.text.DefaultCaret;
import mars.sensors.CommunicationDevice;
import mars.sensors.UnderwaterModem;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//mars.core//MARSUnderwaterModem//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "MARSUnderwaterModemTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = false)
@ActionID(category = "Window", id = "mars.core.MARSUnderwaterModemTopComponent")
//@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_MARSUnderwaterModemAction",
        preferredID = "MARSUnderwaterModemTopComponent")
@Messages({
    "CTL_MARSUnderwaterModemAction=MARSUnderwaterModem",
    "CTL_MARSUnderwaterModemTopComponent=MARSUnderwaterModem Window",
    "HINT_MARSUnderwaterModemTopComponent=This is a MARSUnderwaterModem window"
})
public final class MARSUnderwaterModemTopComponent extends TopComponent {

    private CommunicationDevice comDev;
    
    /**
     *
     */
    public MARSUnderwaterModemTopComponent() {
        initComponents();
        setName(Bundle.CTL_MARSUnderwaterModemTopComponent());
        setToolTipText(Bundle.HINT_MARSUnderwaterModemTopComponent());
    }
    
    /**
     *
     * @param comDev
     */
    public MARSUnderwaterModemTopComponent(CommunicationDevice comDev) {
        this.comDev = comDev;
        initComponents();
        setName(Bundle.CTL_MARSUnderwaterModemTopComponent());
        setToolTipText(Bundle.HINT_MARSUnderwaterModemTopComponent());
        initListener();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        in = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        out = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        in.setColumns(20);
        in.setRows(5);
        DefaultCaret caret2 = (DefaultCaret)out.getCaret();
        caret2.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        jScrollPane1.setViewportView(in);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(MARSUnderwaterModemTopComponent.class, "MARSUnderwaterModemTopComponent.jLabel1.text")); // NOI18N

        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        out.setColumns(20);
        out.setRows(5);
        DefaultCaret caret = (DefaultCaret)out.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        jScrollPane2.setViewportView(out);

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(MARSUnderwaterModemTopComponent.class, "MARSUnderwaterModemTopComponent.jLabel2.text")); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(89, 89, 89))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 386, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea in;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea out;
    // End of variables declaration//GEN-END:variables
    /**
     *
     */
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    /**
     *
     */
    @Override
    public void componentClosed() {
        if(comDev != null){
            comDev.removeAdListener(null);
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
    
    void initListener(){
        class ComplainingAdListener implements PhysicalExchangerListener{
            @Override public void onNewData( CommunicationDeviceEvent e ) {
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(e.getTime());
                if(e.getType() == CommunicationDeviceEventType.OUT){
                    out.append(c.get(Calendar.HOUR) +":"+ c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) + ":" + c.get(Calendar.MILLISECOND) + ":" + e.getMsg() + "\n");
                }else{
                    in.append(c.get(Calendar.HOUR) +":"+ c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) + ":" + c.get(Calendar.MILLISECOND) + ":" + e.getMsg() + "\n");
                }
            }
        }

        comDev.addAdListener( new ComplainingAdListener() );
    }
}
