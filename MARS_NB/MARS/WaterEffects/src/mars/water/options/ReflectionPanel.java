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

import mars.water.projectedGrid.WaterGridFilter;
import mars.water.WaterState;
import org.openide.util.NbPreferences;

final class ReflectionPanel extends javax.swing.JPanel {

    private final ReflectionOptionsPanelController controller;
    private WaterState state;
    private WaterGridFilter filter;

    ReflectionPanel(ReflectionOptionsPanelController controller) {
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

        mapSize = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        displacement = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        specular = new javax.swing.JCheckBox();
        shiny = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        sunScale = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();

        mapSize.setText(org.openide.util.NbBundle.getMessage(ReflectionPanel.class, "ReflectionPanel.mapSize.text")); // NOI18N
        mapSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mapSizeActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel1, org.openide.util.NbBundle.getMessage(ReflectionPanel.class, "ReflectionPanel.jLabel1.text")); // NOI18N

        displacement.setText(org.openide.util.NbBundle.getMessage(ReflectionPanel.class, "ReflectionPanel.displacement.text")); // NOI18N
        displacement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displacementActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, org.openide.util.NbBundle.getMessage(ReflectionPanel.class, "ReflectionPanel.jLabel2.text")); // NOI18N

        org.openide.awt.Mnemonics.setLocalizedText(specular, org.openide.util.NbBundle.getMessage(ReflectionPanel.class, "ReflectionPanel.specular.text")); // NOI18N
        specular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specularActionPerformed(evt);
            }
        });

        shiny.setText(org.openide.util.NbBundle.getMessage(ReflectionPanel.class, "ReflectionPanel.shiny.text")); // NOI18N
        shiny.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shinyActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel3, org.openide.util.NbBundle.getMessage(ReflectionPanel.class, "ReflectionPanel.jLabel3.text")); // NOI18N

        sunScale.setText(org.openide.util.NbBundle.getMessage(ReflectionPanel.class, "ReflectionPanel.sunScale.text")); // NOI18N
        sunScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sunScaleActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, org.openide.util.NbBundle.getMessage(ReflectionPanel.class, "ReflectionPanel.jLabel4.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(111, 111, 111)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(shiny, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sunScale, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(mapSize, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(displacement, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(specular))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mapSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(displacement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(specular)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(shiny, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sunScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void displacementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displacementActionPerformed
        filter.setReflectionDisplace(Float.parseFloat(displacement.getText()));
    }//GEN-LAST:event_displacementActionPerformed

    private void specularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specularActionPerformed
        filter.setUseSpecular(specular.isSelected());
    }//GEN-LAST:event_specularActionPerformed

    private void shinyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shinyActionPerformed
        filter.setShininess(Float.parseFloat(shiny.getText()));
    }//GEN-LAST:event_shinyActionPerformed

    private void sunScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sunScaleActionPerformed
        filter.setSunScale(Float.parseFloat(sunScale.getText()));
    }//GEN-LAST:event_sunScaleActionPerformed

    private void mapSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mapSizeActionPerformed
        filter.setReflectionMapSize(Integer.parseInt(mapSize.getText()));
    }//GEN-LAST:event_mapSizeActionPerformed

    void load() {
        filter.setReflectionMapSize(NbPreferences.forModule(ReflectionPanel.class).getInt("ReflectionMapSize", 512));
        filter.setReflectionDisplace(NbPreferences.forModule(ReflectionPanel.class).getFloat("ReflectionDisplacement", 30));
        filter.setUseSpecular(NbPreferences.forModule(ReflectionPanel.class).getBoolean("UseSpecular", true));
        filter.setShininess(NbPreferences.forModule(ReflectionPanel.class).getFloat("Shininess", .7f));
        filter.setSunScale(NbPreferences.forModule(ReflectionPanel.class).getFloat("SunScale", 3));

        mapSize.setText(String.valueOf(filter.getReflectionMapSize()));
        displacement.setText(String.valueOf(filter.getReflectionDisplace()));
        specular.setSelected(filter.isUseSpecular());
        shiny.setText(String.valueOf(filter.getShininess()));
        sunScale.setText(String.valueOf(filter.getSunScale()));
    }

    void store() {
        NbPreferences.forModule(ReflectionPanel.class).putInt("ReflectionMapSize", filter.getReflectionMapSize());
        NbPreferences.forModule(ReflectionPanel.class).putFloat("ReflectionDisplacement", filter.getReflectionDisplace());
        NbPreferences.forModule(ReflectionPanel.class).putBoolean("UseSpecular", filter.isUseSpecular());
        NbPreferences.forModule(ReflectionPanel.class).putFloat("Shininess", filter.getShininess());
        NbPreferences.forModule(ReflectionPanel.class).putFloat("SunScale", filter.getSunScale());
    }

    boolean valid() {
        // TODO check whether form is consistent and complete
        return true;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField displacement;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JFormattedTextField mapSize;
    private javax.swing.JFormattedTextField shiny;
    private javax.swing.JCheckBox specular;
    private javax.swing.JFormattedTextField sunScale;
    // End of variables declaration//GEN-END:variables
}
