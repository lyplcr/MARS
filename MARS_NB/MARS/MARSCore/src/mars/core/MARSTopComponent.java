/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.core;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import com.jme3.system.awt.AwtPanel;
import com.jme3.system.awt.AwtPanelsContext;
import com.jme3.system.awt.PaintMode;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;
import java.util.logging.XMLFormatter;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import mars.KeyConfig;
import mars.MARS_Main;
import mars.MARS_Settings;
import mars.PhysicalEnvironment;
import mars.auv.AUV;
import mars.auv.AUV_Manager;
import mars.auv.AUV_Parameters;
import mars.gui.MyVerifier;
import mars.gui.MyVerifierType;
import mars.gui.dnd.SimStateTransferHandler;
import mars.simobjects.SimObjectManager;
import mars.states.SimState;
import mars.xml.ConfigManager;
import mars.xml.XML_JAXB_ConfigReaderWriter;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.MenuBar;
import org.openide.awt.Toolbar;
import org.openide.awt.ToolbarPool;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.Repository;
import org.openide.loaders.DataFolder;
import org.openide.modules.InstalledFileLocator;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.util.RequestProcessor;
import org.openide.util.Utilities;
import org.openide.windows.IOProvider;
import org.openide.windows.WindowManager;

/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//mars.core//MARS//EN",
        autostore = false)
@TopComponent.Description(
        preferredID = "MARSTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS)
@TopComponent.Registration(mode = "editor", openAtStartup = true)
@ActionID(category = "Window", id = "mars.core.MARSTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_MARSAction",
        preferredID = "MARSTopComponent")
@Messages({
    "CTL_MARSAction=MARS",
    "CTL_MARSTopComponent=MARS Window",
    "HINT_MARSTopComponent=This is a MARS window"
})
public final class MARSTopComponent extends TopComponent {

    private static AwtPanel sim_panel, map_panel;
    private static MARS_Main mars;
    
    private AUV_Manager auvManager;
    private SimObjectManager simob_manager;
    private MARS_Settings mars_settings;
    private KeyConfig keyConfig;
    private PhysicalEnvironment penv;
    private ConfigManager configManager; 
    
    private ArrayList<String> auv_name_items = new ArrayList<String>();
    private ArrayList<String> simob_name_items = new ArrayList<String>();
    
    private static int resolution_height = 480;
    private static int resolution_width = 640;
    private static int framelimit = 60;
    private static boolean headless = false;
    
    private final ProgressHandle progr = ProgressHandleFactory.createHandle("Simple task");

    public MARSTopComponent() {
        //set so the popups are shown over the jme3canvas (from buttons for example). they will not get cut any longer
        ToolTipManager ttm = ToolTipManager.sharedInstance();
        ttm.setLightWeightPopupEnabled(false);
        
        //the same as above, heavy/light mixin
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        initComponents();
        setName(Bundle.CTL_MARSTopComponent());
        setToolTipText(Bundle.HINT_MARSTopComponent());
        putClientProperty(TopComponent.PROP_CLOSING_DISABLED, Boolean.TRUE);
    }

    @Override
    public boolean canClose() {
        return false; //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        vector_dialog = new javax.swing.JDialog();
        vectorDialog_Confirm = new javax.swing.JButton();
        Cancel2 = new javax.swing.JButton();
        vectorDialog_x = new javax.swing.JTextField();
        vectorDialog_y = new javax.swing.JTextField();
        vectorDialog_z = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        color_dialog = new javax.swing.JColorChooser();
        float_dialog = new javax.swing.JDialog();
        jLabel4 = new javax.swing.JLabel();
        floatDialog_Confirm = new javax.swing.JButton();
        Cancel3 = new javax.swing.JButton();
        floatDialog_x = new javax.swing.JTextField();
        int_dialog = new javax.swing.JDialog();
        intDialog_x = new javax.swing.JTextField();
        Cancel4 = new javax.swing.JButton();
        intDialog_Confirm = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        string_dialog = new javax.swing.JDialog();
        jLabel6 = new javax.swing.JLabel();
        Cancel5 = new javax.swing.JButton();
        stringDialog_Confirm = new javax.swing.JButton();
        stringDialog_x = new javax.swing.JTextField();
        jme3_window_switcher = new javax.swing.JPopupMenu();
        jme3_view = new javax.swing.JMenu();
        jme3_view_chaseAUV = new javax.swing.JMenu();
        jme3_view_flybycam = new javax.swing.JMenuItem();
        jme3_view_lookAt = new javax.swing.JMenuItem();
        jme3_view_moveCamera = new javax.swing.JMenuItem();
        jme3_view_rotateCamera = new javax.swing.JMenuItem();
        jme3_view_fixed = new javax.swing.JCheckBoxMenuItem();
        jme3_view_parrallel = new javax.swing.JCheckBoxMenuItem();
        jme3_view_debug = new javax.swing.JMenu();
        jme3_splitview = new javax.swing.JMenu();
        split_view = new javax.swing.JMenuItem();
        jme3_mergeview = new javax.swing.JMenu();
        moveCameraDialog = new javax.swing.JDialog();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        moveCameraDialog_r = new javax.swing.JCheckBox();
        moveCameraDialog_x = new javax.swing.JTextField();
        moveCameraDialog_y = new javax.swing.JTextField();
        moveCameraDialog_z = new javax.swing.JTextField();
        rotateCameraDialog = new javax.swing.JDialog();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        rotateCameraDialog_r = new javax.swing.JCheckBox();
        rotateCameraDialog_x = new javax.swing.JTextField();
        rotateCameraDialog_y = new javax.swing.JTextField();
        rotateCameraDialog_z = new javax.swing.JTextField();
        jme3_auv = new javax.swing.JPopupMenu();
        jme3_chase_auv = new javax.swing.JMenuItem();
        jme3_move_auv = new javax.swing.JMenuItem();
        jme3_rotate_auv = new javax.swing.JMenuItem();
        jme3_poke = new javax.swing.JMenuItem();
        jme3_params_auv = new javax.swing.JMenu();
        jme3_debug_auv = new javax.swing.JMenu();
        jme3_debug_auv_pe = new javax.swing.JCheckBoxMenuItem();
        jme3_debug_auv_visualizers = new javax.swing.JCheckBoxMenuItem();
        jme3_debug_auv_centers = new javax.swing.JCheckBoxMenuItem();
        jme3_debug_auv_buoy = new javax.swing.JCheckBoxMenuItem();
        jme3_debug_auv_collision = new javax.swing.JCheckBoxMenuItem();
        jme3_debug_auv_drag = new javax.swing.JCheckBoxMenuItem();
        jme3_debug_auv_wireframe = new javax.swing.JCheckBoxMenuItem();
        jme3_debug_auv_bounding = new javax.swing.JCheckBoxMenuItem();
        jme3_waypoints_auv = new javax.swing.JMenu();
        jme3_waypoints_auv_enable = new javax.swing.JCheckBoxMenuItem();
        jme3_waypoints_auv_visible = new javax.swing.JCheckBoxMenuItem();
        jme3_waypoints_auv_gradient = new javax.swing.JCheckBoxMenuItem();
        jme3_waypoints_auv_reset = new javax.swing.JMenuItem();
        jme3_waypoints_color = new javax.swing.JMenuItem();
        jme3_reset_auv = new javax.swing.JMenuItem();
        jme3_enable_auv = new javax.swing.JCheckBoxMenuItem();
        jme3_delete_auv = new javax.swing.JMenuItem();
        jColorChooser1 = new javax.swing.JColorChooser();
        auv_move_vector_dialog = new javax.swing.JDialog();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        auv_move_vector_dialog_r = new javax.swing.JCheckBox();
        auv_move_vector_dialog_x = new javax.swing.JTextField();
        auv_move_vector_dialog_y = new javax.swing.JTextField();
        auv_move_vector_dialog_z = new javax.swing.JTextField();
        auv_rotate_vector_dialog = new javax.swing.JDialog();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        auv_rotate_vector_dialog_r = new javax.swing.JCheckBox();
        auv_rotate_vector_dialog_x = new javax.swing.JTextField();
        auv_rotate_vector_dialog_y = new javax.swing.JTextField();
        auv_rotate_vector_dialog_z = new javax.swing.JTextField();
        auv_name = new javax.swing.JDialog();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        auv_name_text = new javax.swing.JTextField();
        simob_name = new javax.swing.JDialog();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        simob_name_text = new javax.swing.JTextField();
        JMEPanel = new javax.swing.JPanel();

        vector_dialog.setTitle(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.vector_dialog.title")); // NOI18N
        vector_dialog.setMinimumSize(new java.awt.Dimension(163, 146));

        org.openide.awt.Mnemonics.setLocalizedText(vectorDialog_Confirm, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.vectorDialog_Confirm.text")); // NOI18N
        vectorDialog_Confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vectorDialog_ConfirmActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(Cancel2, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.Cancel2.text")); // NOI18N
        Cancel2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cancel2ActionPerformed(evt);
            }
        });

        vectorDialog_x.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.vectorDialog_x.text")); // NOI18N
        vectorDialog_x.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));
        vectorDialog_x.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vectorDialog_xActionPerformed(evt);
            }
        });

        vectorDialog_y.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.vectorDialog_y.text")); // NOI18N
        vectorDialog_y.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));

        vectorDialog_z.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.vectorDialog_z.text")); // NOI18N
        vectorDialog_z.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel3.text")); // NOI18N

        javax.swing.GroupLayout vector_dialogLayout = new javax.swing.GroupLayout(vector_dialog.getContentPane());
        vector_dialog.getContentPane().setLayout(vector_dialogLayout);
        vector_dialogLayout.setHorizontalGroup(
            vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(vector_dialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(vector_dialogLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(vectorDialog_y))
                    .addGroup(vector_dialogLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(vectorDialog_z))
                    .addGroup(vector_dialogLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(vectorDialog_x)))
                .addContainerGap())
            .addGroup(vector_dialogLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(vectorDialog_Confirm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Cancel2)
                .addGap(0, 11, Short.MAX_VALUE))
        );
        vector_dialogLayout.setVerticalGroup(
            vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, vector_dialogLayout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(vectorDialog_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vectorDialog_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(vectorDialog_z, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vectorDialog_Confirm)
                    .addComponent(Cancel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        float_dialog.setMinimumSize(new java.awt.Dimension(172, 97));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel4.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(floatDialog_Confirm, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.floatDialog_Confirm.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(Cancel3, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.Cancel3.text")); // NOI18N
        Cancel3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cancel3ActionPerformed(evt);
            }
        });

        floatDialog_x.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.floatDialog_x.text")); // NOI18N
        floatDialog_x.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));

        javax.swing.GroupLayout float_dialogLayout = new javax.swing.GroupLayout(float_dialog.getContentPane());
        float_dialog.getContentPane().setLayout(float_dialogLayout);
        float_dialogLayout.setHorizontalGroup(
            float_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(float_dialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(float_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(float_dialogLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(floatDialog_x, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(float_dialogLayout.createSequentialGroup()
                        .addComponent(floatDialog_Confirm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Cancel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        float_dialogLayout.setVerticalGroup(
            float_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(float_dialogLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(float_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(floatDialog_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(float_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(floatDialog_Confirm)
                    .addComponent(Cancel3))
                .addContainerGap())
        );

        int_dialog.setMinimumSize(new java.awt.Dimension(172, 97));

        intDialog_x.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.intDialog_x.text")); // NOI18N
        intDialog_x.setInputVerifier(new MyVerifier( MyVerifierType.INTEGER ));

        org.openide.awt.Mnemonics.setLocalizedText(Cancel4, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.Cancel4.text")); // NOI18N
        Cancel4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cancel4ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(intDialog_Confirm, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.intDialog_Confirm.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel5.text")); // NOI18N

        javax.swing.GroupLayout int_dialogLayout = new javax.swing.GroupLayout(int_dialog.getContentPane());
        int_dialog.getContentPane().setLayout(int_dialogLayout);
        int_dialogLayout.setHorizontalGroup(
            int_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(int_dialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(int_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(int_dialogLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(intDialog_x, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(int_dialogLayout.createSequentialGroup()
                        .addComponent(intDialog_Confirm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Cancel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        int_dialogLayout.setVerticalGroup(
            int_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(int_dialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(int_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(intDialog_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(int_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(intDialog_Confirm)
                    .addComponent(Cancel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        string_dialog.setMinimumSize(new java.awt.Dimension(178, 97));

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel6.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(Cancel5, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.Cancel5.text")); // NOI18N
        Cancel5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Cancel5ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(stringDialog_Confirm, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.stringDialog_Confirm.text")); // NOI18N

        stringDialog_x.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.stringDialog_x.text")); // NOI18N
        stringDialog_x.setInputVerifier(new MyVerifier( MyVerifierType.STRING ));

        javax.swing.GroupLayout string_dialogLayout = new javax.swing.GroupLayout(string_dialog.getContentPane());
        string_dialog.getContentPane().setLayout(string_dialogLayout);
        string_dialogLayout.setHorizontalGroup(
            string_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(string_dialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(string_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(string_dialogLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(stringDialog_x, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(string_dialogLayout.createSequentialGroup()
                        .addComponent(stringDialog_Confirm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Cancel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        string_dialogLayout.setVerticalGroup(
            string_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(string_dialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(string_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(stringDialog_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(string_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stringDialog_Confirm)
                    .addComponent(Cancel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.openide.awt.Mnemonics.setLocalizedText(jme3_view, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_view.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jme3_view_chaseAUV, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_view_chaseAUV.text")); // NOI18N
        jme3_view.add(jme3_view_chaseAUV);

        org.openide.awt.Mnemonics.setLocalizedText(jme3_view_flybycam, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_view_flybycam.text")); // NOI18N
        jme3_view_flybycam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_view_flybycamActionPerformed(evt);
            }
        });
        jme3_view.add(jme3_view_flybycam);

        org.openide.awt.Mnemonics.setLocalizedText(jme3_view_lookAt, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_view_lookAt.text")); // NOI18N
        jme3_view.add(jme3_view_lookAt);

        org.openide.awt.Mnemonics.setLocalizedText(jme3_view_moveCamera, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_view_moveCamera.text")); // NOI18N
        jme3_view_moveCamera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_view_moveCameraActionPerformed(evt);
            }
        });
        jme3_view.add(jme3_view_moveCamera);

        org.openide.awt.Mnemonics.setLocalizedText(jme3_view_rotateCamera, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_view_rotateCamera.text")); // NOI18N
        jme3_view_rotateCamera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_view_rotateCameraActionPerformed(evt);
            }
        });
        jme3_view.add(jme3_view_rotateCamera);

        org.openide.awt.Mnemonics.setLocalizedText(jme3_view_fixed, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_view_fixed.text")); // NOI18N
        jme3_view.add(jme3_view_fixed);

        org.openide.awt.Mnemonics.setLocalizedText(jme3_view_parrallel, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_view_parrallel.text")); // NOI18N
        jme3_view.add(jme3_view_parrallel);

        org.openide.awt.Mnemonics.setLocalizedText(jme3_view_debug, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_view_debug.text")); // NOI18N
        jme3_view.add(jme3_view_debug);

        jme3_window_switcher.add(jme3_view);

        org.openide.awt.Mnemonics.setLocalizedText(jme3_splitview, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_splitview.text")); // NOI18N
        jme3_splitview.setEnabled(false);

        org.openide.awt.Mnemonics.setLocalizedText(split_view, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.split_view.text")); // NOI18N
        split_view.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                split_viewActionPerformed(evt);
            }
        });
        jme3_splitview.add(split_view);

        jme3_window_switcher.add(jme3_splitview);

        org.openide.awt.Mnemonics.setLocalizedText(jme3_mergeview, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_mergeview.text")); // NOI18N
        jme3_mergeview.setEnabled(false);
        jme3_window_switcher.add(jme3_mergeview);

        moveCameraDialog.setMinimumSize(new java.awt.Dimension(166, 193));

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jButton1.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton2, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jButton2.text")); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton3, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jButton3.text")); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel7.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel8.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel9.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(moveCameraDialog_r, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.moveCameraDialog_r.text")); // NOI18N

        moveCameraDialog_x.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.moveCameraDialog_x.text")); // NOI18N
        moveCameraDialog_x.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));
        moveCameraDialog_x.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveCameraDialog_xActionPerformed(evt);
            }
        });

        moveCameraDialog_y.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.moveCameraDialog_y.text")); // NOI18N
        moveCameraDialog_y.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));

        moveCameraDialog_z.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.moveCameraDialog_z.text")); // NOI18N
        moveCameraDialog_z.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));

        javax.swing.GroupLayout moveCameraDialogLayout = new javax.swing.GroupLayout(moveCameraDialog.getContentPane());
        moveCameraDialog.getContentPane().setLayout(moveCameraDialogLayout);
        moveCameraDialogLayout.setHorizontalGroup(
            moveCameraDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(moveCameraDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(moveCameraDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(moveCameraDialogLayout.createSequentialGroup()
                        .addGroup(moveCameraDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(moveCameraDialogLayout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2)))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(moveCameraDialogLayout.createSequentialGroup()
                        .addGroup(moveCameraDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(moveCameraDialogLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(moveCameraDialog_z))
                            .addGroup(moveCameraDialogLayout.createSequentialGroup()
                                .addComponent(moveCameraDialog_r)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(moveCameraDialogLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(moveCameraDialog_x))
                            .addGroup(moveCameraDialogLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(moveCameraDialog_y)))
                        .addContainerGap())))
        );
        moveCameraDialogLayout.setVerticalGroup(
            moveCameraDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, moveCameraDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(moveCameraDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(moveCameraDialog_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(moveCameraDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(moveCameraDialog_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(moveCameraDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(moveCameraDialog_z, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(moveCameraDialog_r)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(moveCameraDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        rotateCameraDialog.setMinimumSize(new java.awt.Dimension(166, 193));

        org.openide.awt.Mnemonics.setLocalizedText(jButton4, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jButton4.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton5, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jButton5.text")); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton6, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jButton6.text")); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel10, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel10.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel11, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel11.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel12, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel12.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(rotateCameraDialog_r, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.rotateCameraDialog_r.text")); // NOI18N

        rotateCameraDialog_x.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.rotateCameraDialog_x.text")); // NOI18N
        rotateCameraDialog_x.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));
        rotateCameraDialog_x.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotateCameraDialog_xActionPerformed(evt);
            }
        });

        rotateCameraDialog_y.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.rotateCameraDialog_y.text")); // NOI18N
        rotateCameraDialog_y.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));

        rotateCameraDialog_z.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.rotateCameraDialog_z.text")); // NOI18N
        rotateCameraDialog_z.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));

        javax.swing.GroupLayout rotateCameraDialogLayout = new javax.swing.GroupLayout(rotateCameraDialog.getContentPane());
        rotateCameraDialog.getContentPane().setLayout(rotateCameraDialogLayout);
        rotateCameraDialogLayout.setHorizontalGroup(
            rotateCameraDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rotateCameraDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rotateCameraDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rotateCameraDialogLayout.createSequentialGroup()
                        .addGroup(rotateCameraDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(rotateCameraDialogLayout.createSequentialGroup()
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton5)))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(rotateCameraDialogLayout.createSequentialGroup()
                        .addGroup(rotateCameraDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(rotateCameraDialogLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rotateCameraDialog_z))
                            .addGroup(rotateCameraDialogLayout.createSequentialGroup()
                                .addComponent(rotateCameraDialog_r)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(rotateCameraDialogLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rotateCameraDialog_x))
                            .addGroup(rotateCameraDialogLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rotateCameraDialog_y)))
                        .addContainerGap())))
        );
        rotateCameraDialogLayout.setVerticalGroup(
            rotateCameraDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rotateCameraDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rotateCameraDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(rotateCameraDialog_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(rotateCameraDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(rotateCameraDialog_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(rotateCameraDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(rotateCameraDialog_z, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rotateCameraDialog_r)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(rotateCameraDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.openide.awt.Mnemonics.setLocalizedText(jme3_chase_auv, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_chase_auv.text")); // NOI18N
        jme3_chase_auv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_chase_auvActionPerformed(evt);
            }
        });
        jme3_auv.add(jme3_chase_auv);

        org.openide.awt.Mnemonics.setLocalizedText(jme3_move_auv, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_move_auv.text")); // NOI18N
        jme3_move_auv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_move_auvActionPerformed(evt);
            }
        });
        jme3_auv.add(jme3_move_auv);

        org.openide.awt.Mnemonics.setLocalizedText(jme3_rotate_auv, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_rotate_auv.text")); // NOI18N
        jme3_rotate_auv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_rotate_auvActionPerformed(evt);
            }
        });
        jme3_auv.add(jme3_rotate_auv);

        org.openide.awt.Mnemonics.setLocalizedText(jme3_poke, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_poke.text")); // NOI18N
        jme3_poke.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_pokeActionPerformed(evt);
            }
        });
        jme3_auv.add(jme3_poke);

        org.openide.awt.Mnemonics.setLocalizedText(jme3_params_auv, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_params_auv.text")); // NOI18N
        jme3_auv.add(jme3_params_auv);

        org.openide.awt.Mnemonics.setLocalizedText(jme3_debug_auv, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_debug_auv.text")); // NOI18N

        jme3_debug_auv_pe.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jme3_debug_auv_pe, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_debug_auv_pe.text")); // NOI18N
        jme3_debug_auv_pe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_debug_auv_peActionPerformed(evt);
            }
        });
        jme3_debug_auv.add(jme3_debug_auv_pe);

        jme3_debug_auv_visualizers.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jme3_debug_auv_visualizers, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_debug_auv_visualizers.text")); // NOI18N
        jme3_debug_auv_visualizers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_debug_auv_visualizersActionPerformed(evt);
            }
        });
        jme3_debug_auv.add(jme3_debug_auv_visualizers);

        jme3_debug_auv_centers.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jme3_debug_auv_centers, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_debug_auv_centers.text")); // NOI18N
        jme3_debug_auv_centers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_debug_auv_centersActionPerformed(evt);
            }
        });
        jme3_debug_auv.add(jme3_debug_auv_centers);

        jme3_debug_auv_buoy.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jme3_debug_auv_buoy, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_debug_auv_buoy.text")); // NOI18N
        jme3_debug_auv_buoy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_debug_auv_buoyActionPerformed(evt);
            }
        });
        jme3_debug_auv.add(jme3_debug_auv_buoy);

        jme3_debug_auv_collision.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jme3_debug_auv_collision, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_debug_auv_collision.text")); // NOI18N
        jme3_debug_auv_collision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_debug_auv_collisionActionPerformed(evt);
            }
        });
        jme3_debug_auv.add(jme3_debug_auv_collision);

        jme3_debug_auv_drag.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jme3_debug_auv_drag, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_debug_auv_drag.text")); // NOI18N
        jme3_debug_auv_drag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_debug_auv_dragActionPerformed(evt);
            }
        });
        jme3_debug_auv.add(jme3_debug_auv_drag);

        jme3_debug_auv_wireframe.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jme3_debug_auv_wireframe, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_debug_auv_wireframe.text")); // NOI18N
        jme3_debug_auv_wireframe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_debug_auv_wireframeActionPerformed(evt);
            }
        });
        jme3_debug_auv.add(jme3_debug_auv_wireframe);

        jme3_debug_auv_bounding.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jme3_debug_auv_bounding, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_debug_auv_bounding.text")); // NOI18N
        jme3_debug_auv_bounding.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_debug_auv_boundingActionPerformed(evt);
            }
        });
        jme3_debug_auv.add(jme3_debug_auv_bounding);

        jme3_auv.add(jme3_debug_auv);

        org.openide.awt.Mnemonics.setLocalizedText(jme3_waypoints_auv, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_waypoints_auv.text")); // NOI18N

        jme3_waypoints_auv_enable.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jme3_waypoints_auv_enable, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_waypoints_auv_enable.text")); // NOI18N
        jme3_waypoints_auv_enable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_waypoints_auv_enableActionPerformed(evt);
            }
        });
        jme3_waypoints_auv.add(jme3_waypoints_auv_enable);

        jme3_waypoints_auv_visible.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jme3_waypoints_auv_visible, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_waypoints_auv_visible.text")); // NOI18N
        jme3_waypoints_auv_visible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_waypoints_auv_visibleActionPerformed(evt);
            }
        });
        jme3_waypoints_auv.add(jme3_waypoints_auv_visible);

        jme3_waypoints_auv_gradient.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jme3_waypoints_auv_gradient, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_waypoints_auv_gradient.text")); // NOI18N
        jme3_waypoints_auv_gradient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_waypoints_auv_gradientActionPerformed(evt);
            }
        });
        jme3_waypoints_auv.add(jme3_waypoints_auv_gradient);

        org.openide.awt.Mnemonics.setLocalizedText(jme3_waypoints_auv_reset, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_waypoints_auv_reset.text")); // NOI18N
        jme3_waypoints_auv_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_waypoints_auv_resetActionPerformed(evt);
            }
        });
        jme3_waypoints_auv.add(jme3_waypoints_auv_reset);

        org.openide.awt.Mnemonics.setLocalizedText(jme3_waypoints_color, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_waypoints_color.text")); // NOI18N
        jme3_waypoints_color.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_waypoints_colorActionPerformed(evt);
            }
        });
        jme3_waypoints_auv.add(jme3_waypoints_color);

        jme3_auv.add(jme3_waypoints_auv);

        org.openide.awt.Mnemonics.setLocalizedText(jme3_reset_auv, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_reset_auv.text")); // NOI18N
        jme3_reset_auv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_reset_auvActionPerformed(evt);
            }
        });
        jme3_auv.add(jme3_reset_auv);

        jme3_enable_auv.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jme3_enable_auv, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_enable_auv.text")); // NOI18N
        jme3_enable_auv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_enable_auvActionPerformed(evt);
            }
        });
        jme3_auv.add(jme3_enable_auv);

        org.openide.awt.Mnemonics.setLocalizedText(jme3_delete_auv, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jme3_delete_auv.text")); // NOI18N
        jme3_delete_auv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jme3_delete_auvActionPerformed(evt);
            }
        });
        jme3_auv.add(jme3_delete_auv);

        auv_move_vector_dialog.setMinimumSize(new java.awt.Dimension(166, 193));

        org.openide.awt.Mnemonics.setLocalizedText(jButton7, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jButton7.text")); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton8, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jButton8.text")); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton9, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jButton9.text")); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel13, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel13.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel14, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel14.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel15, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel15.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(auv_move_vector_dialog_r, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.auv_move_vector_dialog_r.text")); // NOI18N

        auv_move_vector_dialog_x.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.auv_move_vector_dialog_x.text")); // NOI18N
        auv_move_vector_dialog_x.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));
        auv_move_vector_dialog_x.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                auv_move_vector_dialog_xActionPerformed(evt);
            }
        });

        auv_move_vector_dialog_y.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.auv_move_vector_dialog_y.text")); // NOI18N
        auv_move_vector_dialog_y.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));

        auv_move_vector_dialog_z.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.auv_move_vector_dialog_z.text")); // NOI18N
        auv_move_vector_dialog_z.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));

        javax.swing.GroupLayout auv_move_vector_dialogLayout = new javax.swing.GroupLayout(auv_move_vector_dialog.getContentPane());
        auv_move_vector_dialog.getContentPane().setLayout(auv_move_vector_dialogLayout);
        auv_move_vector_dialogLayout.setHorizontalGroup(
            auv_move_vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(auv_move_vector_dialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(auv_move_vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(auv_move_vector_dialogLayout.createSequentialGroup()
                        .addGroup(auv_move_vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(auv_move_vector_dialogLayout.createSequentialGroup()
                                .addComponent(jButton7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton8)))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(auv_move_vector_dialogLayout.createSequentialGroup()
                        .addGroup(auv_move_vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(auv_move_vector_dialogLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(auv_move_vector_dialog_z))
                            .addGroup(auv_move_vector_dialogLayout.createSequentialGroup()
                                .addComponent(auv_move_vector_dialog_r)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(auv_move_vector_dialogLayout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(auv_move_vector_dialog_x))
                            .addGroup(auv_move_vector_dialogLayout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(auv_move_vector_dialog_y)))
                        .addContainerGap())))
        );
        auv_move_vector_dialogLayout.setVerticalGroup(
            auv_move_vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, auv_move_vector_dialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(auv_move_vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(auv_move_vector_dialog_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(auv_move_vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(auv_move_vector_dialog_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(auv_move_vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(auv_move_vector_dialog_z, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(auv_move_vector_dialog_r)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(auv_move_vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        auv_rotate_vector_dialog.setMinimumSize(new java.awt.Dimension(166, 193));

        org.openide.awt.Mnemonics.setLocalizedText(jButton10, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jButton10.text")); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton11, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jButton11.text")); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton12, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jButton12.text")); // NOI18N
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel16, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel16.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel17, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel17.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel18, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel18.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(auv_rotate_vector_dialog_r, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.auv_rotate_vector_dialog_r.text")); // NOI18N

        auv_rotate_vector_dialog_x.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.auv_rotate_vector_dialog_x.text")); // NOI18N
        auv_rotate_vector_dialog_x.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));
        auv_rotate_vector_dialog_x.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                auv_rotate_vector_dialog_xActionPerformed(evt);
            }
        });

        auv_rotate_vector_dialog_y.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.auv_rotate_vector_dialog_y.text")); // NOI18N
        auv_rotate_vector_dialog_y.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));

        auv_rotate_vector_dialog_z.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.auv_rotate_vector_dialog_z.text")); // NOI18N
        auv_rotate_vector_dialog_z.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));

        javax.swing.GroupLayout auv_rotate_vector_dialogLayout = new javax.swing.GroupLayout(auv_rotate_vector_dialog.getContentPane());
        auv_rotate_vector_dialog.getContentPane().setLayout(auv_rotate_vector_dialogLayout);
        auv_rotate_vector_dialogLayout.setHorizontalGroup(
            auv_rotate_vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(auv_rotate_vector_dialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(auv_rotate_vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(auv_rotate_vector_dialogLayout.createSequentialGroup()
                        .addGroup(auv_rotate_vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(auv_rotate_vector_dialogLayout.createSequentialGroup()
                                .addComponent(jButton10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton11)))
                        .addGap(0, 10, Short.MAX_VALUE))
                    .addGroup(auv_rotate_vector_dialogLayout.createSequentialGroup()
                        .addGroup(auv_rotate_vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(auv_rotate_vector_dialogLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(auv_rotate_vector_dialog_z))
                            .addGroup(auv_rotate_vector_dialogLayout.createSequentialGroup()
                                .addComponent(auv_rotate_vector_dialog_r)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(auv_rotate_vector_dialogLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(auv_rotate_vector_dialog_x))
                            .addGroup(auv_rotate_vector_dialogLayout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(auv_rotate_vector_dialog_y)))
                        .addContainerGap())))
        );
        auv_rotate_vector_dialogLayout.setVerticalGroup(
            auv_rotate_vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, auv_rotate_vector_dialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(auv_rotate_vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(auv_rotate_vector_dialog_x, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(auv_rotate_vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(auv_rotate_vector_dialog_y, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(auv_rotate_vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(auv_rotate_vector_dialog_z, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(auv_rotate_vector_dialog_r)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(auv_rotate_vector_dialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10)
                    .addComponent(jButton11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        auv_name.setMinimumSize(new java.awt.Dimension(200, 110));
        auv_name.setModal(true);

        org.openide.awt.Mnemonics.setLocalizedText(jButton13, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jButton13.text")); // NOI18N
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton14, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jButton14.text")); // NOI18N
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel19, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel19.text")); // NOI18N

        auv_name_text.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.auv_name_text.text")); // NOI18N
        auv_name_text.setInputVerifier(new MyVerifier( MyVerifierType.STRING ));
        auv_name_text.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                auv_name_textKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout auv_nameLayout = new javax.swing.GroupLayout(auv_name.getContentPane());
        auv_name.getContentPane().setLayout(auv_nameLayout);
        auv_nameLayout.setHorizontalGroup(
            auv_nameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(auv_nameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(auv_nameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(auv_nameLayout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(auv_name_text, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(auv_nameLayout.createSequentialGroup()
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        auv_nameLayout.setVerticalGroup(
            auv_nameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, auv_nameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(auv_nameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(auv_name_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(auv_nameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton13)
                    .addComponent(jButton14))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        simob_name.setMinimumSize(new java.awt.Dimension(200, 100));
        simob_name.setModal(true);

        org.openide.awt.Mnemonics.setLocalizedText(jButton15, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jButton15.text")); // NOI18N
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jButton16, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jButton16.text")); // NOI18N
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel20, org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.jLabel20.text")); // NOI18N

        simob_name_text.setText(org.openide.util.NbBundle.getMessage(MARSTopComponent.class, "MARSTopComponent.simob_name_text.text")); // NOI18N
        simob_name_text.setInputVerifier(new MyVerifier( MyVerifierType.STRING ));
        simob_name_text.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                simob_name_textKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout simob_nameLayout = new javax.swing.GroupLayout(simob_name.getContentPane());
        simob_name.getContentPane().setLayout(simob_nameLayout);
        simob_nameLayout.setHorizontalGroup(
            simob_nameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(simob_nameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(simob_nameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(simob_nameLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(simob_name_text, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(simob_nameLayout.createSequentialGroup()
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        simob_nameLayout.setVerticalGroup(
            simob_nameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, simob_nameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(simob_nameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(simob_name_text, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(simob_nameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton15)
                    .addComponent(jButton16))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        JMEPanel.setLayout(new javax.swing.BoxLayout(JMEPanel, javax.swing.BoxLayout.LINE_AXIS));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JMEPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(JMEPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void vectorDialog_ConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vectorDialog_ConfirmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vectorDialog_ConfirmActionPerformed

    private void Cancel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cancel2ActionPerformed
        vector_dialog.setVisible(false);
    }//GEN-LAST:event_Cancel2ActionPerformed

    private void vectorDialog_xActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vectorDialog_xActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_vectorDialog_xActionPerformed

    private void Cancel3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cancel3ActionPerformed
        float_dialog.setVisible(false);
    }//GEN-LAST:event_Cancel3ActionPerformed

    private void Cancel4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cancel4ActionPerformed
        int_dialog.setVisible(false);
    }//GEN-LAST:event_Cancel4ActionPerformed

    private void Cancel5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Cancel5ActionPerformed
        string_dialog.setVisible(false);
    }//GEN-LAST:event_Cancel5ActionPerformed

    private void jme3_view_flybycamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_view_flybycamActionPerformed
        mars.getChaseCam().setEnabled(false);         
        mars.getFlyByCamera().setEnabled(true);
    }//GEN-LAST:event_jme3_view_flybycamActionPerformed

    private void jme3_view_moveCameraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_view_moveCameraActionPerformed
        //moveCameraDialog.setLocationRelativeTo(JMEPanel1);
        moveCameraDialog.setVisible(true);
    }//GEN-LAST:event_jme3_view_moveCameraActionPerformed

    private void jme3_view_rotateCameraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_view_rotateCameraActionPerformed
        //rotateCameraDialog.setLocationRelativeTo(JMEPanel1);
        rotateCameraDialog.setVisible(true);
    }//GEN-LAST:event_jme3_view_rotateCameraActionPerformed

    private void split_viewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_split_viewActionPerformed
        Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    simState.splitView();
                }
                return null;
            }
            });
    }//GEN-LAST:event_split_viewActionPerformed

    private void moveCameraDialog_xActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveCameraDialog_xActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_moveCameraDialog_xActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        moveCameraDialog.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    simState.moveCamera(new Vector3f(Float.valueOf(moveCameraDialog_x.getText()), Float.valueOf(moveCameraDialog_y.getText()), Float.valueOf(moveCameraDialog_z.getText())),moveCameraDialog_r.isSelected());
                }
                return null;
            }
        });
        moveCameraDialog.setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    simState.rotateCamera(new Vector3f(Float.valueOf(rotateCameraDialog_x.getText()), Float.valueOf(rotateCameraDialog_y.getText()), Float.valueOf(rotateCameraDialog_z.getText())),rotateCameraDialog_r.isSelected());
                }
                return null;
            }
        });
        rotateCameraDialog.setVisible(false);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void rotateCameraDialog_xActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotateCameraDialog_xActionPerformed
        rotateCameraDialog.setVisible(false);
    }//GEN-LAST:event_rotateCameraDialog_xActionPerformed

    private void jme3_chase_auvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_chase_auvActionPerformed
        Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    simState.chaseSelectedAUV();
                }
                return null;
            }
        });
    }//GEN-LAST:event_jme3_chase_auvActionPerformed

    private void jme3_move_auvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_move_auvActionPerformed
        auv_move_vector_dialog.setTitle("Change position of AUV");
        //auv_move_vector_dialog.setLocationRelativeTo(JMEPanel1);
        auv_move_vector_dialog.setVisible(true);
    }//GEN-LAST:event_jme3_move_auvActionPerformed

    private void jme3_rotate_auvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_rotate_auvActionPerformed
        auv_rotate_vector_dialog.setTitle("Change rotation of AUV");
        //auv_rotate_vector_dialog.setLocationRelativeTo(JMEPanel1);
        auv_rotate_vector_dialog.setVisible(true);
    }//GEN-LAST:event_jme3_rotate_auvActionPerformed

    private void jme3_pokeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_pokeActionPerformed
       Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    simState.pokeSelectedAUV();
                }
                return null;
            }
            });
    }//GEN-LAST:event_jme3_pokeActionPerformed

    private void jme3_reset_auvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_reset_auvActionPerformed
        Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    simState.resetSelectedAUV();
                }
                return null;
            }
        });
    }//GEN-LAST:event_jme3_reset_auvActionPerformed

    private void jme3_enable_auvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_enable_auvActionPerformed
        Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    final boolean selected = jme3_enable_auv.isSelected();
                    simState.enableSelectedAUV(selected);
                }
                Runnable runt = new Runnable() {
                    public void run() {
                        TopComponent tc = WindowManager.getDefault().findTopComponent("MARSTreeTopComponent");
                        MARSTreeTopComponent mtc = (MARSTreeTopComponent) tc;
                        mtc.updateTrees();
                    };
                };
                WindowManager.getDefault().invokeWhenUIReady(runt);
                return null;
            }
        });
        toggleJMenuCheckbox(jme3_enable_auv);
    }//GEN-LAST:event_jme3_enable_auvActionPerformed

    private void jme3_delete_auvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_delete_auvActionPerformed

        final AUV auv = (AUV)auvManager.getSelectedAUV();

        //Custom button text
        Object[] options = {"Yes",
                    "No"};
        int delete = JOptionPane.showOptionDialog(this.getRootPane(),
        "Are you sure you want to delete the auv: " + auv.getName(),
        "AUV deletion",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        options,
        options[1]);
        if(delete == 0){
            Future simStateFuture = mars.enqueue(new Callable() {
                public Void call() throws Exception {
                        if(mars.getStateManager().getState(SimState.class) != null){
                            SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                            auvManager.deregisterAUVNoFuture(auv);
                        }
                    Runnable runt = new Runnable() {
                        public void run() {
                            TopComponent tc = WindowManager.getDefault().findTopComponent("MARSTreeTopComponent");
                            MARSTreeTopComponent mtc = (MARSTreeTopComponent) tc;
                            mtc.updateTrees();
                        };
                    };
                    WindowManager.getDefault().invokeWhenUIReady(runt);
                    return null;
                }
            });
        }
    }//GEN-LAST:event_jme3_delete_auvActionPerformed

    private void jme3_debug_auv_peActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_debug_auv_peActionPerformed
        Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    final boolean selected = jme3_debug_auv_pe.isSelected();
                    simState.debugSelectedAUV(0,selected);
                }
                return null;
            }
        });
        toggleJMenuCheckbox(jme3_debug_auv_pe);
    }//GEN-LAST:event_jme3_debug_auv_peActionPerformed

    private void jme3_debug_auv_visualizersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_debug_auv_visualizersActionPerformed
       Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    final boolean selected = jme3_debug_auv_visualizers.isSelected();
                    simState.debugSelectedAUV(7,selected);
                }
                return null;
            }
        });
        toggleJMenuCheckbox(jme3_debug_auv_pe);
    }//GEN-LAST:event_jme3_debug_auv_visualizersActionPerformed

    private void jme3_debug_auv_centersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_debug_auv_centersActionPerformed
       Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    final boolean selected = jme3_debug_auv_centers.isSelected();
                    simState.debugSelectedAUV(1,selected);
                }
                return null;
            }
        });
        toggleJMenuCheckbox(jme3_debug_auv_centers);
    }//GEN-LAST:event_jme3_debug_auv_centersActionPerformed

    private void jme3_debug_auv_buoyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_debug_auv_buoyActionPerformed
        Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    final boolean selected = jme3_debug_auv_buoy.isSelected();
                    simState.debugSelectedAUV(2,selected);
                }
                return null;
            }
        });
        toggleJMenuCheckbox(jme3_debug_auv_buoy);
    }//GEN-LAST:event_jme3_debug_auv_buoyActionPerformed

    private void jme3_debug_auv_collisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_debug_auv_collisionActionPerformed
        Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    final boolean selected = jme3_debug_auv_collision.isSelected();
                    simState.debugSelectedAUV(3,selected);
                }
                return null;
            }
        });
        toggleJMenuCheckbox(jme3_debug_auv_collision);
    }//GEN-LAST:event_jme3_debug_auv_collisionActionPerformed

    private void jme3_debug_auv_dragActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_debug_auv_dragActionPerformed
        Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    final boolean selected = jme3_debug_auv_drag.isSelected();
                    simState.debugSelectedAUV(4,selected);
                }
                return null;
            }
        });
        toggleJMenuCheckbox(jme3_debug_auv_drag);
    }//GEN-LAST:event_jme3_debug_auv_dragActionPerformed

    private void jme3_debug_auv_wireframeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_debug_auv_wireframeActionPerformed
        Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    final boolean selected = jme3_debug_auv_wireframe.isSelected();
                    simState.debugSelectedAUV(5,selected);
                }
                return null;
            }
        });
        toggleJMenuCheckbox(jme3_debug_auv_wireframe);
    }//GEN-LAST:event_jme3_debug_auv_wireframeActionPerformed

    private void jme3_debug_auv_boundingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_debug_auv_boundingActionPerformed
        Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    final boolean selected = jme3_debug_auv_bounding.isSelected();
                    simState.debugSelectedAUV(6,selected);
                }
                return null;
            }
        });
        toggleJMenuCheckbox(jme3_debug_auv_bounding);
    }//GEN-LAST:event_jme3_debug_auv_boundingActionPerformed

    private void jme3_waypoints_auv_enableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_waypoints_auv_enableActionPerformed
       Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    final boolean selected = jme3_waypoints_auv_enable.isSelected();
                    simState.waypointsSelectedAUV(0,selected);
                }
                return null;
            }
        });
        toggleJMenuCheckbox(jme3_waypoints_auv_enable);
    }//GEN-LAST:event_jme3_waypoints_auv_enableActionPerformed

    private void jme3_waypoints_auv_visibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_waypoints_auv_visibleActionPerformed
        Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    final boolean selected = jme3_waypoints_auv_visible.isSelected();
                    simState.waypointsSelectedAUV(1,selected);
                }
                return null;
            }
        });
        toggleJMenuCheckbox(jme3_waypoints_auv_visible);
    }//GEN-LAST:event_jme3_waypoints_auv_visibleActionPerformed

    private void jme3_waypoints_auv_gradientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_waypoints_auv_gradientActionPerformed
        Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    final boolean selected = jme3_waypoints_auv_gradient.isSelected();
                    simState.waypointsSelectedAUV(3,selected);
                }
                return null;
            }
        });
        toggleJMenuCheckbox(jme3_waypoints_auv_gradient);
    }//GEN-LAST:event_jme3_waypoints_auv_gradientActionPerformed

    private void jme3_waypoints_auv_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_waypoints_auv_resetActionPerformed
        Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    simState.waypointsSelectedAUV(2,true);
                }
                return null;
            }
        });
    }//GEN-LAST:event_jme3_waypoints_auv_resetActionPerformed

    private void jme3_waypoints_colorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jme3_waypoints_colorActionPerformed
        final Color newColor = jColorChooser1.showDialog(
                     this.getRootPane(),
                     "Choose Color for Waypoints",
                     Color.WHITE);
        if(newColor != null){
            Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    simState.waypointsColorSelectedAUV(newColor);
                }
                return null;
            }
            });
        }
    }//GEN-LAST:event_jme3_waypoints_colorActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        auv_move_vector_dialog.setVisible(false);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        auv_move_vector_dialog.setVisible(false);
        Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    simState.moveSelectedAUV(new Vector3f(Float.valueOf(auv_move_vector_dialog_x.getText()), Float.valueOf(auv_move_vector_dialog_y.getText()), Float.valueOf(auv_move_vector_dialog_z.getText())),auv_move_vector_dialog_r.isSelected());
                }
                return null;
            }
        });
    }//GEN-LAST:event_jButton9ActionPerformed

    private void auv_move_vector_dialog_xActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_auv_move_vector_dialog_xActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_auv_move_vector_dialog_xActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        auv_rotate_vector_dialog.setVisible(false);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        auv_move_vector_dialog.setVisible(false);
        Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    simState.rotateSelectedAUV(new Vector3f(Float.valueOf(auv_rotate_vector_dialog_x.getText()), Float.valueOf(auv_rotate_vector_dialog_y.getText()), Float.valueOf(auv_rotate_vector_dialog_z.getText())),auv_rotate_vector_dialog_r.isSelected());
                }
                return null;
            }
        });
    }//GEN-LAST:event_jButton12ActionPerformed

    private void auv_rotate_vector_dialog_xActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_auv_rotate_vector_dialog_xActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_auv_rotate_vector_dialog_xActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
       Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    simState.moveSelectedGhostAUV(new Vector3f(Float.valueOf(auv_move_vector_dialog_x.getText()), Float.valueOf(auv_move_vector_dialog_y.getText()), Float.valueOf(auv_move_vector_dialog_z.getText())));
                }
                return null;
            }
        });
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
         Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                if(mars.getStateManager().getState(SimState.class) != null){
                    SimState simState = (SimState)mars.getStateManager().getState(SimState.class);
                    simState.moveSelectedGhostAUV(new Vector3f(Float.valueOf(auv_rotate_vector_dialog_x.getText()), Float.valueOf(auv_rotate_vector_dialog_x.getText()), Float.valueOf(auv_rotate_vector_dialog_x.getText())));
                }
                return null;
            }
        });
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        auv_name_text.setText("");
        auv_name.setVisible(false);
        auv_name.dispose();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        if(auv_name_text.getInputVerifier().verify(auv_name_text)){
            auv_name.setVisible(false);
            auv_name.dispose();
        }
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        if(simob_name_text.getInputVerifier().verify(simob_name_text)){
            simob_name.setVisible(false);
            simob_name.dispose();
        }
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        simob_name_text.setText("");
        simob_name.setVisible(false);
        simob_name.dispose();
    }//GEN-LAST:event_jButton16ActionPerformed

    private void simob_name_textKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_simob_name_textKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            if(simob_name_text.getInputVerifier().verify(simob_name_text)){
                simob_name.setVisible(false);
            }else{
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }//GEN-LAST:event_simob_name_textKeyPressed

    private void auv_name_textKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_auv_name_textKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            if(auv_name_text.getInputVerifier().verify(auv_name_text)){
                auv_name.setVisible(false);
            }else{
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }//GEN-LAST:event_auv_name_textKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cancel2;
    private javax.swing.JButton Cancel3;
    private javax.swing.JButton Cancel4;
    private javax.swing.JButton Cancel5;
    private javax.swing.JPanel JMEPanel;
    private javax.swing.JDialog auv_move_vector_dialog;
    private javax.swing.JCheckBox auv_move_vector_dialog_r;
    private javax.swing.JTextField auv_move_vector_dialog_x;
    private javax.swing.JTextField auv_move_vector_dialog_y;
    private javax.swing.JTextField auv_move_vector_dialog_z;
    private javax.swing.JDialog auv_name;
    private javax.swing.JTextField auv_name_text;
    private javax.swing.JDialog auv_rotate_vector_dialog;
    private javax.swing.JCheckBox auv_rotate_vector_dialog_r;
    private javax.swing.JTextField auv_rotate_vector_dialog_x;
    private javax.swing.JTextField auv_rotate_vector_dialog_y;
    private javax.swing.JTextField auv_rotate_vector_dialog_z;
    private javax.swing.JColorChooser color_dialog;
    private javax.swing.JButton floatDialog_Confirm;
    private javax.swing.JTextField floatDialog_x;
    private javax.swing.JDialog float_dialog;
    private javax.swing.JButton intDialog_Confirm;
    private javax.swing.JTextField intDialog_x;
    private javax.swing.JDialog int_dialog;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JColorChooser jColorChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPopupMenu jme3_auv;
    private javax.swing.JMenuItem jme3_chase_auv;
    private javax.swing.JMenu jme3_debug_auv;
    private javax.swing.JCheckBoxMenuItem jme3_debug_auv_bounding;
    private javax.swing.JCheckBoxMenuItem jme3_debug_auv_buoy;
    private javax.swing.JCheckBoxMenuItem jme3_debug_auv_centers;
    private javax.swing.JCheckBoxMenuItem jme3_debug_auv_collision;
    private javax.swing.JCheckBoxMenuItem jme3_debug_auv_drag;
    private javax.swing.JCheckBoxMenuItem jme3_debug_auv_pe;
    private javax.swing.JCheckBoxMenuItem jme3_debug_auv_visualizers;
    private javax.swing.JCheckBoxMenuItem jme3_debug_auv_wireframe;
    private javax.swing.JMenuItem jme3_delete_auv;
    private javax.swing.JCheckBoxMenuItem jme3_enable_auv;
    private javax.swing.JMenu jme3_mergeview;
    private javax.swing.JMenuItem jme3_move_auv;
    private javax.swing.JMenu jme3_params_auv;
    private javax.swing.JMenuItem jme3_poke;
    private javax.swing.JMenuItem jme3_reset_auv;
    private javax.swing.JMenuItem jme3_rotate_auv;
    private javax.swing.JMenu jme3_splitview;
    private javax.swing.JMenu jme3_view;
    private javax.swing.JMenu jme3_view_chaseAUV;
    private javax.swing.JMenu jme3_view_debug;
    private javax.swing.JCheckBoxMenuItem jme3_view_fixed;
    private javax.swing.JMenuItem jme3_view_flybycam;
    private javax.swing.JMenuItem jme3_view_lookAt;
    private javax.swing.JMenuItem jme3_view_moveCamera;
    private javax.swing.JCheckBoxMenuItem jme3_view_parrallel;
    private javax.swing.JMenuItem jme3_view_rotateCamera;
    private javax.swing.JMenu jme3_waypoints_auv;
    private javax.swing.JCheckBoxMenuItem jme3_waypoints_auv_enable;
    private javax.swing.JCheckBoxMenuItem jme3_waypoints_auv_gradient;
    private javax.swing.JMenuItem jme3_waypoints_auv_reset;
    private javax.swing.JCheckBoxMenuItem jme3_waypoints_auv_visible;
    private javax.swing.JMenuItem jme3_waypoints_color;
    private javax.swing.JPopupMenu jme3_window_switcher;
    private javax.swing.JDialog moveCameraDialog;
    private javax.swing.JCheckBox moveCameraDialog_r;
    private javax.swing.JTextField moveCameraDialog_x;
    private javax.swing.JTextField moveCameraDialog_y;
    private javax.swing.JTextField moveCameraDialog_z;
    private javax.swing.JDialog rotateCameraDialog;
    private javax.swing.JCheckBox rotateCameraDialog_r;
    private javax.swing.JTextField rotateCameraDialog_x;
    private javax.swing.JTextField rotateCameraDialog_y;
    private javax.swing.JTextField rotateCameraDialog_z;
    private javax.swing.JDialog simob_name;
    private javax.swing.JTextField simob_name_text;
    private javax.swing.JMenuItem split_view;
    private javax.swing.JButton stringDialog_Confirm;
    private javax.swing.JTextField stringDialog_x;
    private javax.swing.JDialog string_dialog;
    private javax.swing.JButton vectorDialog_Confirm;
    private javax.swing.JTextField vectorDialog_x;
    private javax.swing.JTextField vectorDialog_y;
    private javax.swing.JTextField vectorDialog_z;
    private javax.swing.JDialog vector_dialog;
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening

        //set the toolbar positions
        ToolbarPool.getDefault().setConfiguration("MyToolbar");
        
        //redirect sysout to output window
        redirectSystemStreams();
                
        //start actual mars sim
        Thread t = new Thread(new Runnable() {
 
            @Override
            public void run() {
                        try {
                            XML_JAXB_ConfigReaderWriter xml = new XML_JAXB_ConfigReaderWriter();
                            MARS_Settings mars_settings = xml.loadMARS_Settings();
                            resolution_height = mars_settings.getResolution_Height();
                            resolution_width = mars_settings.getResolution_Width();
                            framelimit = mars_settings.getFrameLimit();
                            headless = mars_settings.isHeadless();
                        } catch (Exception ex) {
                            Logger.getLogger(MARS_Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        final AppSettings settings = new AppSettings(true);
                        settings.setWidth(640);
                        settings.setHeight(480);
                        /*settings.setWidth(1280);
                        settings.setHeight(800);
                        settings.setFrameRate(60);
                        settings.setFullscreen(true);*/
                        //settings.setCustomRenderer(mars);
                        settings.setCustomRenderer(AwtPanelsContext.class);
                        //view.setCanvasPanel(settings.getWidth(),settings.getHeight());

                        //JmeSystem.setLowPermissions(true);
                        mars = new MARS_Main();      
                        
                        Runnable runt = new Runnable() {
                                    public void run() {
                                        TopComponent tc = WindowManager.getDefault().findTopComponent("MARSTreeTopComponent");
                                        MARSTreeTopComponent mtc = (MARSTreeTopComponent) tc;
                                        mtc.setMARS(mars);
                                        mars.setTreeTopComp(mtc);
                                        
                                        TopComponent tc2 = WindowManager.getDefault().findTopComponent("MARSTopComponent");
                                        MARSTopComponent mtc2 = (MARSTopComponent) tc2;
                                        mars.setMARSTopComp(mtc2);
                                        
                                        TopComponent tc3 = WindowManager.getDefault().findTopComponent("MARSMapTopComponent");
                                        MARSMapTopComponent mtc3 = (MARSMapTopComponent) tc3;
                                        mtc3.setMARS(mars);
                                        mars.setMARSMapComp(mtc3);
                                        
                                        TopComponent tc4 = WindowManager.getDefault().findTopComponent("MARSLogTopComponent");
                                        MARSLogTopComponent mtc4 = (MARSLogTopComponent) tc4;
                                        mtc4.setMARS(mars);
                                        mars.setMARSLogComp(mtc4);
                                        
                                         //find all toolbars that need mars and set it
                                        Toolbar findToolbar = ToolbarPool.getDefault().findToolbar("Simulation");
                                        Component[] components = findToolbar.getComponents();
                                        if(components[1] instanceof StartSimulationJPanel){
                                            StartSimulationJPanel simToolBarPanel = (StartSimulationJPanel)components[1];
                                            simToolBarPanel.setMars(mars);
                                        }

                                        Toolbar findToolbar2 = ToolbarPool.getDefault().findToolbar("ROS");
                                        Component[] components2 = findToolbar2.getComponents();
                                        if(components2[1] instanceof StartROSJPanel){
                                            StartROSJPanel simToolBarPanel = (StartROSJPanel)components2[1];
                                            simToolBarPanel.setMars(mars);
                                        }

                                        Toolbar findToolbar3 = ToolbarPool.getDefault().findToolbar("Speed");
                                        Component[] components3 = findToolbar3.getComponents();
                                        if(components3[1] instanceof StartSimulationSpeedJPanel){
                                            StartSimulationSpeedJPanel simToolBarPanel = (StartSimulationSpeedJPanel)components3[1];
                                            simToolBarPanel.setMars(mars);
                                        }
                                    };
                                };
                        WindowManager.getDefault().invokeWhenUIReady(runt);
                                
                                
                        mars.setPauseOnLostFocus(false);                  
                        mars.setShowSettings(false);
                        mars.setSettings(settings);
                        //mars.setProgressHandle(progr);
                        
                        if(headless){
                            mars.start(JmeContext.Type.Headless);
                        }else{
                            mars.start();
                            SwingUtilities.invokeLater(new Runnable(){
                                public void run(){
                                    final AwtPanelsContext ctx = (AwtPanelsContext) mars.getContext();

                                    sim_panel = ctx.createPanel(PaintMode.Accelerated);
                                    sim_panel.setPreferredSize(new Dimension(640, 480));
                                    sim_panel.setMinimumSize(new Dimension(640, 480));
                                    //sim_panel.setPreferredSize(new Dimension(1280, 800));
                                    //sim_panel.setMinimumSize(new Dimension(1280, 800));
                                    sim_panel.transferFocus();
                                    ctx.setInputSource(sim_panel);

                                    map_panel = ctx.createPanel(PaintMode.Accelerated);
                                    map_panel.setPreferredSize(new Dimension(256, 256));
                                    map_panel.setMinimumSize(new Dimension(256, 256));

                                    addAWTMainPanel(sim_panel);
                                    
                                    /*JFrame jf = new JFrame();
                                    jf.setLayout(new BoxLayout(jf.getContentPane(), BoxLayout.X_AXIS));
                                    jf.add(sim_panel);
                                    jf.setExtendedState(Frame.MAXIMIZED_BOTH);  
                                    jf.setUndecorated(true);  
                                    jf.setSize(1280, 800);
                                    //jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                    jf.setVisible(true);*/
                                    
                                    //addAWTMapPanel(map_panel);
                                    Runnable run = new Runnable() {
                                        public void run() {
                                            TopComponent tc = WindowManager.getDefault().findTopComponent("MARSMapTopComponent");
                                            MARSMapTopComponent mtc = (MARSMapTopComponent) tc;
                                            mtc.addAWTMapPanel(map_panel);
                                        };
                                    };
                                    WindowManager.getDefault().invokeWhenUIReady(run);
                                    
                                    /*WindowManager.getDefault().invokeWhenUIReady(new Runnable() {
                                        @Override
                                        public void run() {
                                            FileUtil.getConfigObject("Actions/Window/org-netbeans-core-windows-actions-ToggleFullScreenAction.instance", Action.class).actionPerformed(null);
                                        }
                                    });*/

                                    mars.enqueue(new Callable<Void>(){
                                    public Void call(){
                                            mars.getViewPort().setClearFlags(true, true, true);
                                            //app.getGuiViewPort().setEnabled(true);
                                            mars.getFlyByCamera().setDragToRotate(true);
                                            sim_panel.attachTo(true, mars.getViewPort());
                                            //sim_panel.attachTo(false, mars.getViewPort2());
                                            sim_panel.attachTo(true, mars.getGuiViewPort());
                                            map_panel.attachTo(false, mars.getMapViewPort());
                                            return null;
                                        }
                                    });
                                }
                            });
                        }   
                }
        });
        t.start();
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
        //app.stop();
    }
    
    public void addAWTMainPanel(AwtPanel sim_panel){
        this.JMEPanel.add(sim_panel);
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
    
    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {

            @Override
            public void write(int i) throws IOException {
                IOProvider.getDefault().getIO("MARS", false).getOut().print(String.valueOf((char) i));
            }

            @Override
            public void write(byte[] bytes) throws IOException {
                IOProvider.getDefault().getIO("MARS", false).getOut().print(new String(bytes));
            }

            @Override
            public void write(byte[] bytes, int off, int len) throws IOException {
                IOProvider.getDefault().getIO("MARS", false).getOut().print(new String(bytes, off, len));
            }
        };
        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
        StreamHandler handler = new StreamHandler(out, new SimpleFormatter()) {
            @Override
            public void publish(LogRecord record) {
                super.publish(record);
                this.flush();
            }
        };
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.addHandler(handler);
        //logger.log(Level.SEVERE, "TESTESTTEST");
        //Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "TESTESTTEST2");
    }
    
    /**
     * 
     * @param auv_param
     */
    public void initPopUpMenuesForAUV(final AUV_Parameters auv_param){
        EventQueue.invokeLater(new Runnable(){
                @Override
                public void run() {
                    //first the dbug stuff
                    if(auv_param.isDebugPhysicalExchanger()){
                        jme3_debug_auv_pe.setSelected(true);
                    }else{
                        jme3_debug_auv_pe.setSelected(false);
                    }
                    if(auv_param.isDebugDrag()){
                        jme3_debug_auv_drag.setSelected(true);
                    }else{
                        jme3_debug_auv_drag.setSelected(false);
                    }
                    if(auv_param.isDebugWireframe()){
                        jme3_debug_auv_wireframe.setSelected(true);
                    }else{
                        jme3_debug_auv_wireframe.setSelected(false);
                    }
                    if(auv_param.isDebugCenters()){
                        jme3_debug_auv_centers.setSelected(true);
                    }else{
                        jme3_debug_auv_centers.setSelected(false);
                    }
                    if(auv_param.isDebugVisualizers()){
                        jme3_debug_auv_visualizers.setSelected(true);
                    }else{
                        jme3_debug_auv_visualizers.setSelected(false);
                    }
                    if(auv_param.isDebugCollision()){
                        jme3_debug_auv_collision.setSelected(true);
                    }else{
                        jme3_debug_auv_collision.setSelected(false);
                    }
                    if(auv_param.isDebugBuoycancy()){
                        jme3_debug_auv_buoy.setSelected(true);
                    }else{
                        jme3_debug_auv_buoy.setSelected(false);
                    }
                    if(auv_param.isDebugBounding()){
                        jme3_debug_auv_bounding.setSelected(true);
                    }else{
                        jme3_debug_auv_bounding.setSelected(false);
                    }
                    if(auv_param.isWaypoints_enabled()){
                        jme3_waypoints_auv_enable.setSelected(true);
                    }else{
                        jme3_waypoints_auv_enable.setSelected(false);
                    }
                    if(auv_param.isWaypoints_visible()){
                        jme3_waypoints_auv_visible.setSelected(true);
                    }else{
                        jme3_waypoints_auv_visible.setSelected(false);
                    }
                    if(auv_param.isWaypoints_gradient()){
                        jme3_waypoints_auv_gradient.setSelected(true);
                    }else{
                        jme3_waypoints_auv_gradient.setSelected(false);
                    }
                    if(auv_param.isEnabled()){
                        jme3_enable_auv.setSelected(true);
                    }else{
                        jme3_enable_auv.setSelected(false);
                    }
                    
                    //params
                    jme3_params_auv.removeAll();
                    addHashMapMenue(jme3_params_auv, auv_param, auv_param.getAllVariables(),"");

                }
            }
        );
    }
       
    private void toggleJMenuCheckbox(JCheckBoxMenuItem jmenucheck){
        if(jmenucheck.isSelected()){
            jmenucheck.setSelected(true);
        }else{
            jmenucheck.setSelected(false);
        }
    }
    
    private void addHashMapMenue(JMenu jm, final AUV_Parameters auv_param, final HashMap<String, Object> allVariables, final String hashmapname){
        ///sort the hashtable
        SortedSet<String> sortedset= new TreeSet<String>(allVariables.keySet());
        Iterator<String> it = sortedset.iterator();

        while (it.hasNext()) {
            final String elem = it.next();
            
            Object element = (Object)allVariables.get(elem);
                        if(element instanceof Boolean){
                            boolean bool = (Boolean)element;
                            final JCheckBoxMenuItem jcm = new JCheckBoxMenuItem(elem);
                            jcm.setSelected(bool);
                            
                            //listener for changes
                            jcm.addActionListener(new java.awt.event.ActionListener() {
                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                    final boolean selected = jcm.isSelected();
                                    auv_param.updateState(elem,hashmapname);
                                    auv_param.setValue(elem, selected, hashmapname);
                                    toggleJMenuCheckbox(jcm);
                                }
                            });
                                    
                            jm.add(jcm);
                        }else if(element instanceof Vector3f){
                            final Vector3f vec = (Vector3f)element;
                            final JMenuItem jcm = new JMenuItem(elem);
                            
                            //listener for changes
                            jcm.addActionListener(new java.awt.event.ActionListener() {
                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                    vector_dialog.setTitle("Change " + elem);
                                    vector_dialog.setLocationRelativeTo(JMEPanel);
                                    vectorDialog_x.setText(String.valueOf(vec.getX()));
                                    vectorDialog_y.setText(String.valueOf(vec.getY()));
                                    vectorDialog_z.setText(String.valueOf(vec.getZ()));
                                    vectorDialog_x.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));
                                    vectorDialog_y.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));
                                    vectorDialog_z.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));
                                    vectorDialog_Confirm.addActionListener(
                                            new java.awt.event.ActionListener() {
                                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                    auv_param.updateState(elem,hashmapname);
                                                    auv_param.setValue(elem, new Vector3f(Float.valueOf(vectorDialog_x.getText()), Float.valueOf(vectorDialog_y.getText()), Float.valueOf(vectorDialog_z.getText())), hashmapname);
                                                    vector_dialog.setVisible(false);
                                                }
                                            }
                                    );
                                    vector_dialog.setVisible(true);
                                }
                            });
                                    
                            jm.add(jcm);
                        }else if(element instanceof Float){
                            final float flo = (Float)element;
                            final JMenuItem jcm = new JMenuItem(elem);
                            
                            //listener for changes
                            jcm.addActionListener(new java.awt.event.ActionListener() {
                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                    float_dialog.setTitle("Change " + elem);
                                    float_dialog.setLocationRelativeTo(JMEPanel);
                                    floatDialog_x.setText(String.valueOf(flo));
                                    floatDialog_x.setInputVerifier(new MyVerifier( MyVerifierType.FLOAT ));
                                    floatDialog_Confirm.addActionListener(
                                            new java.awt.event.ActionListener() {
                                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                    auv_param.updateState(elem,hashmapname);
                                                    auv_param.setValue(elem, Float.valueOf(floatDialog_x.getText()), hashmapname);
                                                    float_dialog.setVisible(false);
                                                }
                                            }
                                    );
                                    float_dialog.setVisible(true);
                                }
                            });
                                    
                            jm.add(jcm);
                        }else if(element instanceof Integer){
                            final int integ = (Integer)element;
                            final JMenuItem jcm = new JMenuItem(elem);
                            
                            //listener for changes
                            jcm.addActionListener(new java.awt.event.ActionListener() {
                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                    int_dialog.setTitle("Change " + elem);
                                    int_dialog.setLocationRelativeTo(JMEPanel);
                                    intDialog_x.setText(String.valueOf(integ));
                                    intDialog_x.setInputVerifier(new MyVerifier( MyVerifierType.INTEGER ));
                                    intDialog_Confirm.addActionListener(
                                            new java.awt.event.ActionListener() {
                                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                    auv_param.updateState(elem,hashmapname);
                                                    auv_param.setValue(elem, Integer.valueOf(intDialog_x.getText()), hashmapname);
                                                    int_dialog.setVisible(false);
                                                }
                                            }
                                    );
                                    int_dialog.setVisible(true);
                                }
                            });
                                    
                            jm.add(jcm);
                        }else if(element instanceof String){
                            final String st = (String)element;
                            final JMenuItem jcm = new JMenuItem(elem);
                            
                            //listener for changes
                            jcm.addActionListener(new java.awt.event.ActionListener() {
                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                    string_dialog.setTitle("Change " + elem);
                                    string_dialog.setLocationRelativeTo(JMEPanel);
                                    stringDialog_x.setText(st);
                                    stringDialog_x.setInputVerifier(new MyVerifier( MyVerifierType.STRING ));
                                    stringDialog_x.addActionListener(
                                            new java.awt.event.ActionListener() {
                                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                                    auv_param.updateState(elem,hashmapname);
                                                    auv_param.setValue(elem, stringDialog_x.getText(), hashmapname);
                                                    string_dialog.setVisible(false);
                                                }
                                            }
                                    );
                                    string_dialog.setVisible(true);
                                }
                            });
                                    
                            jm.add(jcm);
                        }else if(element instanceof ColorRGBA){
                            final ColorRGBA color = (ColorRGBA)element;
                            final JMenuItem jcm = new JMenuItem(elem);
                            
                            //listener for changes
                            jcm.addActionListener(new java.awt.event.ActionListener() {
                                public void actionPerformed(java.awt.event.ActionEvent evt) {
                                            final Color newColor = color_dialog.showDialog(
                                             getRootPane(),
                                             "Choose Color for " + elem,
                                             new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()));
                                            System.out.println("newColor: " + newColor);
                                            System.out.println("color: " + color);
                                            if(newColor != null){
                                                auv_param.updateState(elem,hashmapname);
                                                auv_param.setValue(elem, new ColorRGBA(newColor.getRed()/255f, newColor.getGreen()/255f, newColor.getBlue()/255f, 0f), hashmapname);
                                            }
                                }
                            });
                                    
                            jm.add(jcm);
                        }else if(element instanceof HashMap){
                            final JMenu jmm = new JMenu(elem);
                            jm.add(jmm);
                            addHashMapMenue(jmm,auv_param,(HashMap<String, Object>)element,elem);
                        }   
            
        }              
    }
    
        /**
     * 
     * @param x
     * @param y
     */
    public void showpopupAUV(final int x, final int y){
        final MARSTopComponent mtc = this;
        EventQueue.invokeLater(new Runnable(){
                @Override
                public void run() {
                    jme3_auv.show(mtc,x,y);
                }
            }
        );
    }
    
        /**
     * 
     */
    public void hideAllPopupWindows(){
        EventQueue.invokeLater(new Runnable(){
                @Override
                public void run() {
                    jme3_window_switcher.setVisible(false);
                }
            }
        );
    }
    
        /**
     * 
     * @param x
     * @param y
     */
    public void showpopupWindowSwitcher(final int x, final int y){
        final MARSTopComponent mtc = this;
        EventQueue.invokeLater(new Runnable(){
                @Override
                public void run() {
                    jme3_window_switcher.show(mtc,x,y);
                }
            }
        );
    }
    
        /**
     * 
     * @param auvManager
     */
    public void setAuv_manager(AUV_Manager auv_manager) {
        this.auvManager = auv_manager;
    }

    public void setSimob_manager(SimObjectManager simob_manager) {
        this.simob_manager = simob_manager;
    }
    
   /**
     * 
     * @param mars_settings
     */
    public void setMarsSettings(MARS_Settings mars_settings){
        this.mars_settings = mars_settings;
    }
    
    /**
     * 
     * @param simauv_settings
     */
    public void setConfigManager(ConfigManager configManager){
        this.configManager = configManager;
    }

    /**
     * 
     * @param keyConfig
     */
    public void setKeyConfig(KeyConfig keyConfig) {
        this.keyConfig = keyConfig;
    }

    /**
     * 
     * @param penv
     */
    public void setPenv(PhysicalEnvironment penv) {
        this.penv = penv;
    }
    
    /**
     * 
     */
    public void initDND(){
        EventQueue.invokeLater(new Runnable(){
                @Override
                public void run() {
                    JMEPanel.setTransferHandler(new SimStateTransferHandler(mars,JMEPanel));
                    getANText().setInputVerifier(new MyVerifier( MyVerifierType.AUV,auvManager,auv_name));
                    getSNText().setInputVerifier(new MyVerifier( MyVerifierType.SIMOB,simob_manager,simob_name));
                }
            }
        );
    }
    
    public JDialog getAN(){
        return auv_name;
    }
    
    public void updateANAutoComplete(){
        auv_name_items.clear();
        auv_name_items.addAll(auvManager.getAUVs().keySet());
        AutoCompleteDecorator.decorate(auv_name_text, auv_name_items, false);
    }
    
    public JDialog getSN(){
        return simob_name;
    }
    
    public void updateSNAutoComplete(){
        simob_name_items.clear();
        simob_name_items.addAll(simob_manager.getSimObjects().keySet());
        AutoCompleteDecorator.decorate(simob_name_text, simob_name_items, false);
    }
    
    public JTextField getANText(){
        return auv_name_text;
    }
    
    public JTextField getSNText(){
        return simob_name_text;
    }
    
        /**
     * 
     */
    public void allowSimInteraction(){
        EventQueue.invokeLater(new Runnable(){
                @Override
                public void run() {
                    Toolbar findToolbar = ToolbarPool.getDefault().findToolbar("Simulation");
                    Component[] components = findToolbar.getComponents();
                    if(components[1] instanceof StartSimulationJPanel){
                        StartSimulationJPanel simToolBarPanel = (StartSimulationJPanel)components[1];
                        simToolBarPanel.allowSimInteraction();
                    }
                    Toolbar findToolbar2 = ToolbarPool.getDefault().findToolbar("Speed");
                    Component[] components2 = findToolbar2.getComponents();
                    if(components2[1] instanceof StartSimulationSpeedJPanel){
                        StartSimulationSpeedJPanel simToolBarPanel = (StartSimulationSpeedJPanel)components2[1];
                        simToolBarPanel.allowSimInteraction();
                    }
                    
                    
                    /*FileObject menu = FileUtil.getConfigFile("Menu");
                    DataFolder findFolder = DataFolder.findFolder(menu);
                    MenuBar menuBar = new MenuBar(findFolder);
                    System.out.println("test");*/
                                            
                    // hacky but there isnt any good way to get the menus in nbp
                    /*JFrame frame = (JFrame) WindowManager.getDefault().getMainWindow();
                    JMenuBar menuBar = frame.getJMenuBar(); 
                    JMenu menu = menuBar.getMenu(0);
                    JPopupMenu popupMenu = menu.getPopupMenu();
                    Component component = popupMenu.getComponent(0);
                    component.setEnabled(false);
                    component.revalidate();

                    FileObject actionsFolder = FileUtil.getConfigFile("Actions");

                    Component component2 = popupMenu.getComponent(1);
                    component2.setEnabled(true);*/

                    /*jButtonLogLoad.setEnabled(true);
                    jButtonLogPause.setEnabled(true);
                    jButtonLogPlay.setEnabled(true);
                    jButtonLogRecord.setEnabled(true);
                    jButtonLogSave.setEnabled(true);
                    jSliderLogTime.setEnabled(true);
                    jButtonLogForward.setEnabled(true);
                    jButtonLogRewind.setEnabled(true);
                    jButtonLogStop.setEnabled(true);*/
                }
            }
        );
    }
    
    /**
     * 
     */
    public void allowStateInteraction(){
        EventQueue.invokeLater(new Runnable(){
                @Override
                public void run() {
                    // hacky but there isnt any good way to get the menus in nbp
                    /*JFrame frame = (JFrame) WindowManager.getDefault().getMainWindow();
                    JMenuBar menuBar = frame.getJMenuBar(); 
                    JMenu menu = menuBar.getMenu(0);
                    JPopupMenu popupMenu = menu.getPopupMenu();
                    Component component = popupMenu.getComponent(0);
                    component.setEnabled(true);

                    Component component2 = popupMenu.getComponent(3);
                    component2.setEnabled(true);*/
                    
                    //maybe with this
                    //FileObject root = Repository.getDefault().getDefaultFileSystem().getRoot();
                }
            }
        );
    }
    
    public void loadSimState(final File f){
        Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                mars.setConfigName(f.getName());
                mars.restartSimState();
                return null;
            }
        });
    }
    
    public void activateFlyByCam(){
        Future simStateFuture = mars.enqueue(new Callable() {
            public Void call() throws Exception {
                mars.getChaseCam().setEnabled(false);         
                mars.getFlyByCamera().setEnabled(true);
                return null;
            }
        });
    }
    
    public void saveConfig(){
        File basePath = InstalledFileLocator.getDefault().locate("config", "mars.core", false);
        File f = new File(basePath.getAbsolutePath() + "/" + configManager.getConfig());
        if(f != null){
            String failure = XML_JAXB_ConfigReaderWriter.saveConfiguration(f, mars_settings, auvManager, simob_manager, keyConfig, penv);
            if(failure != null){
                JOptionPane.showMessageDialog(WindowManager.getDefault().getMainWindow(),
                failure,
                "Error",
                JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(WindowManager.getDefault().getMainWindow(),
                "Could sucessfully create File. Configuration saved.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(WindowManager.getDefault().getMainWindow(),
            "Could not create File!",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void saveConfigTo(File f){
        if(f != null){
            String failure = XML_JAXB_ConfigReaderWriter.saveConfiguration(f, mars_settings, auvManager, simob_manager, keyConfig, penv);
            if(failure != null){
                JOptionPane.showMessageDialog(WindowManager.getDefault().getMainWindow(),
                failure,
                "Error",
                JOptionPane.ERROR_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(WindowManager.getDefault().getMainWindow(),
                "Could sucessfully create File. Configuration saved.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    public void restartSimState(){
        if(mars != null){
            Future simStateFuture = mars.enqueue(new Callable() {
                public Void call() throws Exception {
                    mars.restartSimState();
                    return null;
                }
            });
        }
    }
    
    public void startSimState(){
        if(mars != null){
            mars.startSimState();
        }
    }
    
    /**
     * 
     * @param allow
     */
    public void allowPhysicsInteraction(final boolean allow){
        EventQueue.invokeLater(new Runnable(){
                @Override
                public void run() {
                    Toolbar findToolbar = ToolbarPool.getDefault().findToolbar("Simulation");
                    Component[] components = findToolbar.getComponents();
                    if(components[1] instanceof StartSimulationJPanel){
                        StartSimulationJPanel simToolBarPanel = (StartSimulationJPanel)components[1];
                        simToolBarPanel.allowPhysicsInteraction(allow);
                    }
                }
            }
        );
    }
    
    /**
     * 
     * @param connected
     */
    public void allowServerInteraction(final boolean connected){
        EventQueue.invokeLater(new Runnable(){
                @Override
                public void run() {
                    Toolbar findToolbar = ToolbarPool.getDefault().findToolbar("ROS");
                    Component[] components = findToolbar.getComponents();
                    if(components[1] instanceof StartROSJPanel){
                        StartROSJPanel simToolBarPanel = (StartROSJPanel)components[1];
                        simToolBarPanel.allowServerInteraction(connected);
                    }                  
                }
            }
        );
    }
    
    /**
     * 
     * @param enable
     */
    public void enableServerInteraction(final boolean enable){
        EventQueue.invokeLater(new Runnable(){
                @Override
                public void run() {
                    Toolbar findToolbar = ToolbarPool.getDefault().findToolbar("ROS");
                    Component[] components = findToolbar.getComponents();
                    if(components[1] instanceof StartROSJPanel){
                        StartROSJPanel simToolBarPanel = (StartROSJPanel)components[1];
                        simToolBarPanel.enableServerInteraction(enable);
                    }                 
                }
            }
        );
    }
}