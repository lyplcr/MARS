package mars.module.auvEditor;

import java.util.prefs.Preferences;

/**
 *
 * @author Christian Friedrich <friedri1 at informatik.uni-luebeck.de>
 * @author Alexander Bigerl <bigerl at informatik.uni-luebeck.de>
 */
final class AUVEditorPanel extends javax.swing.JPanel {

    private final AUVEditorOptionsPanelController controller;

    AUVEditorPanel(AUVEditorOptionsPanelController controller) {
        this.controller = controller;
        initComponents();
        // TODO listen to changes in form fields and call controller.changed()
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        jTextField1 = new javax.swing.JTextField();

        jCheckBox1.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox1, org.openide.util.NbBundle.getMessage(AUVEditorPanel.class, "AUVEditorPanel.jCheckBox1.text")); // NOI18N
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox2.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox2, org.openide.util.NbBundle.getMessage(AUVEditorPanel.class, "AUVEditorPanel.jCheckBox2.text")); // NOI18N

        jCheckBox3.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox3, org.openide.util.NbBundle.getMessage(AUVEditorPanel.class, "AUVEditorPanel.jCheckBox3.text")); // NOI18N

        jCheckBox4.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox4, org.openide.util.NbBundle.getMessage(AUVEditorPanel.class, "AUVEditorPanel.jCheckBox4.text")); // NOI18N

        jCheckBox5.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(jCheckBox5, org.openide.util.NbBundle.getMessage(AUVEditorPanel.class, "AUVEditorPanel.jCheckBox5.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(AUVEditorPanel.class, "AUVEditorPanel.jLabel1.text")); // NOI18N

        jSlider1.setMaximum(10);
        jSlider1.setToolTipText(org.openide.util.NbBundle.getMessage(AUVEditorPanel.class, "AUVEditorPanel.jSlider1.toolTipText")); // NOI18N
        jSlider1.setValue(5);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });
        jSlider1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jSlider1PropertyChange(evt);
            }
        });
        jSlider1.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                jSlider1VetoableChange(evt);
            }
        });

        jTextField1.setEditable(false);
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField1.setText(org.openide.util.NbBundle.getMessage(AUVEditorPanel.class, "AUVEditorPanel.jTextField1.text")); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTextField1InputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        jTextField1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTextField1PropertyChange(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox5)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox3)
                            .addComponent(jCheckBox2)
                            .addComponent(jCheckBox4))))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(61, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        if (jCheckBox1.isSelected()) {
            jCheckBox2.setEnabled(true);
            jCheckBox3.setEnabled(true);
            jCheckBox4.setEnabled(true);
        } else {
            jCheckBox2.setEnabled(false);
            jCheckBox3.setEnabled(false);
            jCheckBox4.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jTextField1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextField1InputMethodTextChanged
    }//GEN-LAST:event_jTextField1InputMethodTextChanged

    private void jSlider1VetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_jSlider1VetoableChange
    }//GEN-LAST:event_jSlider1VetoableChange

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jSlider1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jSlider1PropertyChange
        jTextField1.setText(jSlider1.getValue() + "");
    }//GEN-LAST:event_jSlider1PropertyChange

    private void jTextField1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTextField1PropertyChange
        try {
            int parseInt = Integer.parseInt(jTextField1.getText());
            jSlider1.setValue(parseInt);
        } catch (Exception e) {
            jTextField1.setText("5");
            jSlider1.setValue(5);
        }
    }//GEN-LAST:event_jTextField1PropertyChange

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        jTextField1.setText(jSlider1.getValue() + "");
    }//GEN-LAST:event_jSlider1StateChanged

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
    }//GEN-LAST:event_jTextField1KeyTyped

    void load() {
        // TODO read settings and initialize GUI
        // Example:        
        jCheckBox1.setSelected(Preferences.userNodeForPackage(AUVEditorPanel.class).getBoolean("showGrid", true));
        jCheckBox2.setSelected(Preferences.userNodeForPackage(AUVEditorPanel.class).getBoolean("showXGrid", true));
        jCheckBox3.setSelected(Preferences.userNodeForPackage(AUVEditorPanel.class).getBoolean("showYGrid", true));
        jCheckBox4.setSelected(Preferences.userNodeForPackage(AUVEditorPanel.class).getBoolean("showZGrid", true));
        jCheckBox5.setSelected(Preferences.userNodeForPackage(AUVEditorPanel.class).getBoolean("enableAUVWireframe", true));
        jSlider1.setValue(Preferences.userNodeForPackage(AUVEditorPanel.class).getInt("scaleAxesAndOrb", 5));
        jTextField1.setText(Preferences.userNodeForPackage(AUVEditorPanel.class).get("scaleAxesAndOrb", "5"));
        // or for org.openide.util with API spec. version >= 7.4:
        // someCheckBox.setSelected(NbPreferences.forModule(AUVEditorPanel.class).getBoolean("someFlag", false));
        // or:
        // someTextField.setText(SomeSystemOption.getDefault().getSomeStringProperty());
    }

    void store() {
        // TODO store modified settings
        // Example:
        Preferences.userNodeForPackage(AUVEditorPanel.class).putBoolean("showGrid", jCheckBox1.isSelected());
        Preferences.userNodeForPackage(AUVEditorPanel.class).putBoolean("showXGrid", jCheckBox2.isSelected());
        Preferences.userNodeForPackage(AUVEditorPanel.class).putBoolean("showYGrid", jCheckBox3.isSelected());
        Preferences.userNodeForPackage(AUVEditorPanel.class).putBoolean("showZGrid", jCheckBox4.isSelected());
        Preferences.userNodeForPackage(AUVEditorPanel.class).putBoolean("enableAUVWireframe", jCheckBox5.isSelected());
        Preferences.userNodeForPackage(AUVEditorPanel.class).putInt("scaleAxesAndOrb", jSlider1.getValue());
        // or for org.openide.util with API spec. version >= 7.4:
        // NbPreferences.forModule(AUVEditorPanel.class).putBoolean("someFlag", someCheckBox.isSelected());
        // or:
        // SomeSystemOption.getDefault().setSomeStringProperty(someTextField.getText());
    }

    boolean valid() {
        // TODO check whether form is consistent and complete
        return true;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}