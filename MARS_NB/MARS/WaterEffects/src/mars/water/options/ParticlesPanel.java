/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.water.options;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import mars.water.WaterParticleFilter;
import mars.water.WaterState;

final class ParticlesPanel extends javax.swing.JPanel {

    private final ParticlesOptionsPanelController controller;
    private WaterState state;
    private WaterParticleFilter filter;

    ParticlesPanel(ParticlesOptionsPanelController controller) {
        this.controller = controller;
        initComponents();
        while (WaterState.getInstance() == null);
        state = WaterState.getInstance();
        filter = state.getParticleFilter();
        octaves.setText(String.valueOf(filter.getOctaves()));
        offset.setText(String.valueOf(filter.getOctaveOffset()));
        persistence.setText(String.valueOf(filter.getPersistence()));
        time.setText(String.valueOf(filter.getTimeScale()));
        Vector3f scale = filter.getCoordinateScale();
        coordX.setText(String.valueOf(scale.x));
        coordY.setText(String.valueOf(scale.y));
        coordZ.setText(String.valueOf(scale.z));
        ColorRGBA color = filter.getParticleColor();
        colorR.setText(String.valueOf(color.r));
        colorG.setText(String.valueOf(color.g));
        colorB.setText(String.valueOf(color.b));
        colorA.setText(String.valueOf(color.a));
        intensity.setText(String.valueOf(filter.getMaximumIntensity()));
        falloff.setText(String.valueOf(filter.getFalloff()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        time = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        coordX = new javax.swing.JFormattedTextField();
        coordY = new javax.swing.JFormattedTextField();
        coordZ = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        colorR = new javax.swing.JFormattedTextField();
        colorB = new javax.swing.JFormattedTextField();
        colorG = new javax.swing.JFormattedTextField();
        colorA = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        intensity = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        falloff = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        octaves = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        offset = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        persistence = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        time.setText(org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.time.text")); // NOI18N
        time.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.jLabel1.text")); // NOI18N

        coordX.setText(org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.coordX.text")); // NOI18N
        coordX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coordXActionPerformed(evt);
            }
        });

        coordY.setText(org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.coordY.text")); // NOI18N
        coordY.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coordYActionPerformed(evt);
            }
        });

        coordZ.setText(org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.coordZ.text")); // NOI18N
        coordZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coordZActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.jLabel2.text")); // NOI18N

        colorR.setText(org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.colorR.text")); // NOI18N
        colorR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorRActionPerformed(evt);
            }
        });

        colorB.setText(org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.colorB.text")); // NOI18N
        colorB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorBActionPerformed(evt);
            }
        });

        colorG.setText(org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.colorG.text")); // NOI18N
        colorG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorGActionPerformed(evt);
            }
        });

        colorA.setText(org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.colorA.text")); // NOI18N
        colorA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorAActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.jLabel3.text")); // NOI18N

        intensity.setText(org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.intensity.text")); // NOI18N
        intensity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intensityActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.jLabel4.text")); // NOI18N

        falloff.setText(org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.falloff.text")); // NOI18N
        falloff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                falloffActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.jLabel5.text")); // NOI18N

        octaves.setText(org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.octaves.text")); // NOI18N
        octaves.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                octavesActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.jLabel6.text")); // NOI18N

        offset.setText(org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.offset.text")); // NOI18N
        offset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                offsetActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.jLabel7.text")); // NOI18N

        persistence.setText(org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.persistence.text")); // NOI18N
        persistence.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                persistenceActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.jLabel8.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(jButton1, org.openide.util.NbBundle.getMessage(ParticlesPanel.class, "ParticlesPanel.jButton1.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(colorR, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(colorG, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(colorB, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(colorA, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(coordX, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(coordY, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(coordZ, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(intensity, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(falloff, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(octaves, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(offset, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(persistence, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton1))
                .addContainerGap(191, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(coordX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(coordY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(coordZ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(colorR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(intensity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(falloff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(octaves, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(offset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(persistence, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(109, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void octavesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_octavesActionPerformed
        filter.setOctaves(Integer.parseInt(octaves.getText()));
    }//GEN-LAST:event_octavesActionPerformed

    private void offsetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_offsetActionPerformed
        filter.setOctaveOffset(Integer.parseInt(offset.getText()));
    }//GEN-LAST:event_offsetActionPerformed

    private void persistenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_persistenceActionPerformed
        filter.setPersistence(Float.parseFloat(persistence.getText()));
    }//GEN-LAST:event_persistenceActionPerformed

    private void timeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeActionPerformed
        filter.setTimeScale(Float.parseFloat(time.getText()));
    }//GEN-LAST:event_timeActionPerformed

    private void coordYActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coordYActionPerformed
        Vector3f scale = filter.getCoordinateScale();
        scale.y = Float.parseFloat(coordY.getText());
        filter.setCoordinateScale(scale);
    }//GEN-LAST:event_coordYActionPerformed

    private void coordZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coordZActionPerformed
        Vector3f scale = filter.getCoordinateScale();
        scale.z = Float.parseFloat(coordZ.getText());
        filter.setCoordinateScale(scale);
    }//GEN-LAST:event_coordZActionPerformed

    private void coordXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coordXActionPerformed
        Vector3f scale = filter.getCoordinateScale();
        scale.x = Float.parseFloat(coordX.getText());
        filter.setCoordinateScale(scale);
    }//GEN-LAST:event_coordXActionPerformed

    private void colorRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorRActionPerformed
        ColorRGBA color = filter.getParticleColor();
        color.r = Float.parseFloat(colorR.getText());
        filter.setParticleColor(color);
    }//GEN-LAST:event_colorRActionPerformed

    private void colorGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorGActionPerformed
        ColorRGBA color = filter.getParticleColor();
        color.g = Float.parseFloat(colorG.getText());
        filter.setParticleColor(color);
    }//GEN-LAST:event_colorGActionPerformed

    private void colorBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorBActionPerformed
        ColorRGBA color = filter.getParticleColor();
        color.b = Float.parseFloat(colorB.getText());
        filter.setParticleColor(color);
    }//GEN-LAST:event_colorBActionPerformed

    private void colorAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorAActionPerformed
        ColorRGBA color = filter.getParticleColor();
        color.a = Float.parseFloat(colorA.getText());
        filter.setParticleColor(color);
    }//GEN-LAST:event_colorAActionPerformed

    private void intensityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intensityActionPerformed
        filter.setMaximumIntensity(Float.parseFloat(intensity.getText()));
    }//GEN-LAST:event_intensityActionPerformed

    private void falloffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_falloffActionPerformed
        filter.setFalloff(Float.parseFloat(falloff.getText()));
    }//GEN-LAST:event_falloffActionPerformed

    void load() {
        // TODO read settings and initialize GUI
        // Example:        
        // someCheckBox.setSelected(Preferences.userNodeForPackage(ParticlesPanel.class).getBoolean("someFlag", false));
        // or for org.openide.util with API spec. version >= 7.4:
        // someCheckBox.setSelected(NbPreferences.forModule(ParticlesPanel.class).getBoolean("someFlag", false));
        // or:
        // someTextField.setText(SomeSystemOption.getDefault().getSomeStringProperty());
    }

    void store() {
        // TODO store modified settings
        // Example:
        // Preferences.userNodeForPackage(ParticlesPanel.class).putBoolean("someFlag", someCheckBox.isSelected());
        // or for org.openide.util with API spec. version >= 7.4:
        // NbPreferences.forModule(ParticlesPanel.class).putBoolean("someFlag", someCheckBox.isSelected());
        // or:
        // SomeSystemOption.getDefault().setSomeStringProperty(someTextField.getText());
    }

    boolean valid() {
        // TODO check whether form is consistent and complete
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField colorA;
    private javax.swing.JFormattedTextField colorB;
    private javax.swing.JFormattedTextField colorG;
    private javax.swing.JFormattedTextField colorR;
    private javax.swing.JFormattedTextField coordX;
    private javax.swing.JFormattedTextField coordY;
    private javax.swing.JFormattedTextField coordZ;
    private javax.swing.JFormattedTextField falloff;
    private javax.swing.JFormattedTextField intensity;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JFormattedTextField octaves;
    private javax.swing.JFormattedTextField offset;
    private javax.swing.JFormattedTextField persistence;
    private javax.swing.JFormattedTextField time;
    // End of variables declaration//GEN-END:variables
}
