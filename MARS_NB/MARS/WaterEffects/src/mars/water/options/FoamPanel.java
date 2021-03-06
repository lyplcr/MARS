/*
 * Copyright (c) 2015, Institute of Computer Engineering, University of Lübeck
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * 
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * 
 * * Neither the name of the copyright holder nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package mars.water.options;

import com.jme3.math.Vector3f;
import mars.water.projectedGrid.WaterGridFilter;
import mars.water.WaterState;
import org.openide.util.NbPreferences;

final class FoamPanel extends javax.swing.JPanel {

    private final FoamOptionsPanelController controller;
    private WaterState state;
    private WaterGridFilter filter;

    FoamPanel(FoamOptionsPanelController controller) {
        this.controller = controller;
        initComponents();
        while (WaterState.getInstance() == null);
        state = WaterState.getInstance();
        filter = state.getWaterFilter();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        enabled = new javax.swing.JCheckBox();
        intensity = new javax.swing.JFormattedTextField();
        intensityLabel = new javax.swing.JLabel();
        hardness = new javax.swing.JFormattedTextField();
        hardnessLabel = new javax.swing.JLabel();
        fade = new javax.swing.JFormattedTextField();
        fadeLabel = new javax.swing.JLabel();
        invisible = new javax.swing.JFormattedTextField();
        invisibleLabel = new javax.swing.JLabel();
        cap = new javax.swing.JFormattedTextField();
        capLabel = new javax.swing.JLabel();
        trail = new javax.swing.JFormattedTextField();
        trailLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(enabled, org.openide.util.NbBundle.getMessage(FoamPanel.class, "FoamPanel.enabled.text")); // NOI18N
        enabled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enabledActionPerformed(evt);
            }
        });

        intensity.setText(org.openide.util.NbBundle.getMessage(FoamPanel.class, "FoamPanel.intensity.text")); // NOI18N
        intensity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intensityActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(intensityLabel, org.openide.util.NbBundle.getMessage(FoamPanel.class, "FoamPanel.intensityLabel.text")); // NOI18N

        hardness.setText(org.openide.util.NbBundle.getMessage(FoamPanel.class, "FoamPanel.hardness.text")); // NOI18N
        hardness.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hardnessActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(hardnessLabel, org.openide.util.NbBundle.getMessage(FoamPanel.class, "FoamPanel.hardnessLabel.text")); // NOI18N

        fade.setText(org.openide.util.NbBundle.getMessage(FoamPanel.class, "FoamPanel.fade.text")); // NOI18N
        fade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fadeActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(fadeLabel, org.openide.util.NbBundle.getMessage(FoamPanel.class, "FoamPanel.fadeLabel.text")); // NOI18N

        invisible.setText(org.openide.util.NbBundle.getMessage(FoamPanel.class, "FoamPanel.invisible.text")); // NOI18N
        invisible.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                invisibleActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(invisibleLabel, org.openide.util.NbBundle.getMessage(FoamPanel.class, "FoamPanel.invisibleLabel.text")); // NOI18N

        cap.setText(org.openide.util.NbBundle.getMessage(FoamPanel.class, "FoamPanel.cap.text")); // NOI18N
        cap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                capActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(capLabel, org.openide.util.NbBundle.getMessage(FoamPanel.class, "FoamPanel.capLabel.text")); // NOI18N

        trail.setText(org.openide.util.NbBundle.getMessage(FoamPanel.class, "FoamPanel.trail.text")); // NOI18N
        trail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                trailActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(trailLabel, org.openide.util.NbBundle.getMessage(FoamPanel.class, "FoamPanel.trailLabel.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(FoamPanel.class, "FoamPanel.jButton1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(enabled)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(capLabel)
                                .addGap(18, 18, 18)
                                .addComponent(cap, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fadeLabel)
                                    .addComponent(hardnessLabel)
                                    .addComponent(invisibleLabel)
                                    .addComponent(trailLabel)
                                    .addComponent(intensityLabel))
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(intensity, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(trail, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(fade, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(invisible, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(hardness, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(enabled)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(intensity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(intensityLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hardness, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hardnessLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fadeLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(invisible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(invisibleLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(capLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(trail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(trailLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void enabledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enabledActionPerformed
        filter.setUseFoam(enabled.isSelected());
    }//GEN-LAST:event_enabledActionPerformed

    private void intensityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intensityActionPerformed
        filter.setFoamIntensity(Float.parseFloat(intensity.getText()));
    }//GEN-LAST:event_intensityActionPerformed

    private void hardnessActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hardnessActionPerformed
        filter.setFoamHardness(Float.parseFloat(hardness.getText()));
    }//GEN-LAST:event_hardnessActionPerformed

    private void fadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fadeActionPerformed
        Vector3f existence = filter.getFoamExistence();
        existence.x = Float.parseFloat(fade.getText());
        filter.setFoamExistence(existence);
    }//GEN-LAST:event_fadeActionPerformed

    private void invisibleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_invisibleActionPerformed
        Vector3f existence = filter.getFoamExistence();
        existence.y = Float.parseFloat(invisible.getText());
        filter.setFoamExistence(existence);
    }//GEN-LAST:event_invisibleActionPerformed

    private void capActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_capActionPerformed
        Vector3f existence = filter.getFoamExistence();
        existence.z = Float.parseFloat(cap.getText());
        filter.setFoamExistence(existence);
    }//GEN-LAST:event_capActionPerformed

    private void trailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_trailActionPerformed
        filter.setTrailLength(Integer.parseInt(trail.getText()));
    }//GEN-LAST:event_trailActionPerformed

    void load() {
        filter.setUseFoam(NbPreferences.forModule(FoamPanel.class).getBoolean("UseFoam", true));
        filter.setFoamIntensity(NbPreferences.forModule(FoamPanel.class).getFloat("FoamIntensity", .5f));
        filter.setFoamHardness(NbPreferences.forModule(FoamPanel.class).getFloat("FoamHardness", 1));
        Vector3f existence = new Vector3f();
        existence.x = NbPreferences.forModule(FoamPanel.class).getFloat("FoamFadeDepth", 1);
        existence.y = NbPreferences.forModule(FoamPanel.class).getFloat("FoamInvisbleDepth", 2.5f);
        existence.z = NbPreferences.forModule(FoamPanel.class).getFloat("FoamCapHeight", .5f);
        filter.setFoamExistence(existence);
        filter.setTrailLength(NbPreferences.forModule(FoamPanel.class).getInt("FoamTrailLength", 200));

        enabled.setSelected(filter.isUseFoam());
        intensity.setText(String.valueOf(filter.getFoamIntensity()));
        hardness.setText(String.valueOf(filter.getFoamHardness()));
        fade.setText(String.valueOf(existence.x));
        invisible.setText(String.valueOf(existence.y));
        cap.setText(String.valueOf(existence.z));
        trail.setText(String.valueOf(filter.getTrailLength()));
    }

    void store() {
        NbPreferences.forModule(FoamPanel.class).putBoolean("UseFoam", filter.isUseFoam());
        NbPreferences.forModule(FoamPanel.class).putFloat("FoamIntensity", filter.getFoamIntensity());
        NbPreferences.forModule(FoamPanel.class).putFloat("FoamHardness", filter.getFoamHardness());
        NbPreferences.forModule(FoamPanel.class).putFloat("FoamFadeDepth", filter.getFoamExistence().x);
        NbPreferences.forModule(FoamPanel.class).putFloat("FoamInvisbleDepth", filter.getFoamExistence().y);
        NbPreferences.forModule(FoamPanel.class).putFloat("FoamCapHeight", filter.getFoamExistence().z);
        NbPreferences.forModule(FoamPanel.class).putInt("FoamTrailLength", filter.getTrailLength());
    }

    boolean valid() {
        // TODO check whether form is consistent and complete
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField cap;
    private javax.swing.JLabel capLabel;
    private javax.swing.JCheckBox enabled;
    private javax.swing.JFormattedTextField fade;
    private javax.swing.JLabel fadeLabel;
    private javax.swing.JFormattedTextField hardness;
    private javax.swing.JLabel hardnessLabel;
    private javax.swing.JFormattedTextField intensity;
    private javax.swing.JLabel intensityLabel;
    private javax.swing.JFormattedTextField invisible;
    private javax.swing.JLabel invisibleLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JFormattedTextField trail;
    private javax.swing.JLabel trailLabel;
    // End of variables declaration//GEN-END:variables
}
