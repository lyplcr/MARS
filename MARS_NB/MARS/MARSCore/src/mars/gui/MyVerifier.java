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
package mars.gui;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import mars.auv.AUV;
import mars.auv.AUV_Manager;
import mars.simobjects.SimObject;
import mars.simobjects.SimObjectManager;

/**
 * A class for checking if the input of a text field is correct.
 *
 * @author Thomas Tosik
 */
public class MyVerifier extends InputVerifier implements ActionListener {

    private int type = MyVerifierType.NONE;
    private AUV_Manager auvManager;
    private SimObjectManager simobManager;
    private JDialog popup;
    private JLabel messageLabel;
    private JLabel image;
    private Point point;
    private Dimension cDim;

    /**
     *
     */
    public MyVerifier() {
        super();
        messageLabel = new JLabel("This name is already taken!");
        image = new JLabel(new ImageIcon(".//Assets/Icons/" + "cancel.png"));
    }

    /**
     *
     * @param type
     */
    public MyVerifier(int type) {
        this();
        this.type = type;
    }

    /**
     *
     * @param type
     * @param auvManager
     * @param parent
     */
    public MyVerifier(int type, AUV_Manager auvManager, JDialog parent) {
        this();
        this.type = type;
        this.auvManager = auvManager;
        popup = new JDialog(parent);
        initComponents();
    }

    /**
     *
     * @param type
     * @param simobManager
     * @param parent
     */
    public MyVerifier(int type, SimObjectManager simobManager, JDialog parent) {
        this();
        this.type = type;
        this.simobManager = simobManager;
        popup = new JDialog(parent);
        initComponents();
    }

    private void initComponents() {
        popup.getContentPane().setLayout(new FlowLayout());
        popup.setUndecorated(true);
        popup.getContentPane().setBackground(new Color(243, 255, 159));
        popup.getContentPane().add(image);
        popup.getContentPane().add(messageLabel);
        popup.setFocusableWindowState(false);
    }

    @Override
    public boolean shouldYieldFocus(JComponent input) {
        boolean inputOK = verify(input);

        if (inputOK) {
            return true;
        } else {
            Toolkit.getDefaultToolkit().beep();
            return false;
        }
    }

    /**
     *
     */
    protected void updateObject() {

    }

    //This method checks input, but should cause no side effects.
    public boolean verify(JComponent input) {
        boolean checkField = checkField(input);
        if (!checkField) {
            input.setBackground(Color.PINK);

            if (popup != null) {
                popup.setSize(0, 0);
                popup.setLocationRelativeTo(input);
                point = popup.getLocation();
                cDim = input.getSize();
                popup.setLocation(point.x - (int) cDim.getWidth() / 2,
                        point.y + (int) cDim.getHeight() / 2);
                popup.pack();
                popup.setVisible(true);
            }

            return false;
        }
        input.setBackground(Color.WHITE);
        if (popup != null) {
            popup.setVisible(false);
        }
        return true;
    }

    /**
     *
     * @param input
     * @return
     */
    protected boolean checkField(JComponent input) {
        if (input instanceof MyTextField) {
            MyTextField mytext = (MyTextField) input;
            Object obj = mytext.getObject();
            if (obj instanceof Float && ((MyVerifierType.FLOAT == type) || (MyVerifierType.ALL == type))) {
                try {
                    String tmp = mytext.getText();
                    float value = Float.valueOf(tmp);
                    mytext.setText(tmp);
                    mytext.setObject(value);
                    return true;
                } catch (Exception e) {//Something went wrong (most likely we don't have a valid float).
                    return false;
                }
            } else if (obj instanceof Double && ((MyVerifierType.DOUBLE == type) || (MyVerifierType.ALL == type))) {
                try {
                    String tmp = mytext.getText();
                    double value = Double.valueOf(tmp);
                    mytext.setText(tmp);
                    mytext.setObject(value);
                    return true;
                } catch (Exception e) {//Something went wrong (most likely we don't have a valid float).
                    return false;
                }
            } else if (obj instanceof Integer && ((MyVerifierType.VECTOR3F == type) || (MyVerifierType.ALL == type))) {
                try {
                    String tmp = mytext.getText();
                    int value = Integer.valueOf(tmp);
                    mytext.setText(tmp);
                    mytext.setObject(value);
                    return true;
                } catch (Exception e) {//Something went wrong (most likely we don't have a valid integer).
                    return false;
                }
            } else if (obj instanceof Boolean && ((MyVerifierType.VECTOR3F == type) || (MyVerifierType.ALL == type))) {
                try {
                    String tmp = mytext.getText();
                    boolean value = Boolean.valueOf(tmp);
                    mytext.setText(tmp);
                    mytext.setObject(value);
                    return true;
                } catch (Exception e) {//Something went wrong (most likely we don't have a valid Boolean).
                    return false;
                }
            } else if (obj instanceof String && ((MyVerifierType.VECTOR3F == type) || (MyVerifierType.ALL == type))) {
                try {
                    String tmp = mytext.getText();
                    String value = String.valueOf(tmp);
                    mytext.setText(tmp);
                    mytext.setObject(value);
                    return true;
                } catch (Exception e) {//Something went wrong (most likely we don't have a valid string).
                    return false;
                }
            } else if (obj instanceof Vector3f && ((MyVerifierType.VECTOR3F == type) || (MyVerifierType.ALL == type))) {
                try {
                    String tmp = mytext.getText();
                    int firstkomma = tmp.indexOf(",", 0);
                    int secondkomma = tmp.indexOf(",", firstkomma + 1);
                    float x = Float.valueOf(tmp.substring(1, firstkomma));
                    float y = Float.valueOf(tmp.substring(firstkomma + 1, secondkomma));
                    float z = Float.valueOf(tmp.substring(secondkomma + 1, tmp.length() - 1));
                    Vector3f value = new Vector3f(x, y, z);
                    mytext.setText(tmp);
                    mytext.setObject(value);
                    return true;
                } catch (Exception e) {//Something went wrong (most likely we don't have a valid vector/float).
                    return false;
                }
            } else if (obj instanceof ColorRGBA && ((MyVerifierType.VECTOR3F == type) || (MyVerifierType.ALL == type))) {
                try {
                    String tmp = mytext.getText();
                    int firstbracket = tmp.indexOf("[", 0);
                    int firstkomma = tmp.indexOf(",", 0);
                    int secondkomma = tmp.indexOf(",", firstkomma + 1);
                    int thirdkomma = tmp.indexOf(",", secondkomma + 1);
                    float r = Float.valueOf(tmp.substring(firstbracket + 1, firstkomma));
                    float g = Float.valueOf(tmp.substring(firstkomma + 1, secondkomma));
                    float b = Float.valueOf(tmp.substring(secondkomma + 1, thirdkomma));
                    ColorRGBA value = new ColorRGBA(r, g, b, 0f);
                    mytext.setText(tmp);
                    mytext.setObject(value);
                    return true;
                } catch (Exception e) {//Something went wrong (most likely we don't have a valid color/float).
                    return false;
                }
            }
            return false;
        } else if (input instanceof JTextField) {
            JTextField mytext = (JTextField) input;

            if (((MyVerifierType.FLOAT == type) || (MyVerifierType.ALL == type))) {
                try {
                    String tmp = mytext.getText();
                    float value = Float.valueOf(tmp);
                    mytext.setText(tmp);
                    return true;
                } catch (Exception e) {//Something went wrong (most likely we don't have a valid float).
                    return false;
                }
            } else if (((MyVerifierType.DOUBLE == type) || (MyVerifierType.ALL == type))) {
                try {
                    String tmp = mytext.getText();
                    Double value = Double.valueOf(tmp);
                    mytext.setText(tmp);
                    return true;
                } catch (Exception e) {//Something went wrong (most likely we don't have a valid float).
                    return false;
                }
            } else if (((MyVerifierType.INTEGER == type) || (MyVerifierType.ALL == type))) {
                try {
                    String tmp = mytext.getText();
                    int value = Integer.valueOf(tmp);
                    mytext.setText(tmp);
                    return true;
                } catch (Exception e) {//Something went wrong (most likely we don't have a valid integer).
                    return false;
                }
            } else if (((MyVerifierType.BOOLEAN == type) || (MyVerifierType.ALL == type))) {
                try {
                    String tmp = mytext.getText();
                    boolean value = Boolean.valueOf(tmp);
                    mytext.setText(tmp);
                    return true;
                } catch (Exception e) {//Something went wrong (most likely we don't have a valid Boolean).
                    return false;
                }
            } else if (((MyVerifierType.STRING == type) || (MyVerifierType.ALL == type))) {
                try {
                    String tmp = mytext.getText();
                    String value = String.valueOf(tmp);
                    mytext.setText(tmp);
                    return true;
                } catch (Exception e) {//Something went wrong (most likely we don't have a valid string).
                    return false;
                }
            } else if (((MyVerifierType.VECTOR3F == type) || (MyVerifierType.ALL == type))) {
                try {
                    String tmp = mytext.getText();
                    int firstkomma = tmp.indexOf(",", 0);
                    int secondkomma = tmp.indexOf(",", firstkomma + 1);
                    float x = Float.valueOf(tmp.substring(1, firstkomma));
                    float y = Float.valueOf(tmp.substring(firstkomma + 1, secondkomma));
                    float z = Float.valueOf(tmp.substring(secondkomma + 1, tmp.length() - 1));
                    Vector3f value = new Vector3f(x, y, z);
                    mytext.setText(tmp);
                    return true;
                } catch (Exception e) {//Something went wrong (most likely we don't have a valid vector/float).
                    return false;
                }
            } else if (((MyVerifierType.COLORRGBA == type) || (MyVerifierType.ALL == type))) {
                try {
                    String tmp = mytext.getText();
                    int firstbracket = tmp.indexOf("[", 0);
                    int firstkomma = tmp.indexOf(",", 0);
                    int secondkomma = tmp.indexOf(",", firstkomma + 1);
                    int thirdkomma = tmp.indexOf(",", secondkomma + 1);
                    float r = Float.valueOf(tmp.substring(firstbracket + 1, firstkomma));
                    float g = Float.valueOf(tmp.substring(firstkomma + 1, secondkomma));
                    float b = Float.valueOf(tmp.substring(secondkomma + 1, thirdkomma));
                    ColorRGBA value = new ColorRGBA(r, g, b, 0f);
                    mytext.setText(tmp);
                    return true;
                } catch (Exception e) {//Something went wrong (most likely we don't have a valid color/float).
                    return false;
                }
            } else if (((MyVerifierType.AUV == type) || (MyVerifierType.ALL == type))) {
                try {
                    String tmp = mytext.getText();
                    AUV auv = auvManager.getMARSObject(tmp);
                    if (tmp.equals("")) {
                        return false;
                    } else if (auv == null) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception e) {//Something went wrong (most likely we don't have a valid color/float).
                    return false;
                }
            } else if (((MyVerifierType.SIMOB == type) || (MyVerifierType.ALL == type))) {
                try {
                    String tmp = mytext.getText();
                    SimObject simob = simobManager.getMARSObject(tmp);
                    if (tmp.equals("")) {
                        return false;
                    } else if (simob == null) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (Exception e) {//Something went wrong (most likely we don't have a valid color/float).
                    return false;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    //Checks that the amount field is valid.  If it is valid,
    //it returns true; otherwise, returns false.  If the
    //change argument is true, this method sets the
    //value to the minimum or maximum value if necessary and (even if not) sets it to the
    //parsed number so that it looks good -- no letters,
    //for example.
    /**
     *
     * @param change
     * @return
     */
    protected boolean checkAmountField(boolean change) {
        return true;
    }

    public void actionPerformed(ActionEvent e) {
        MyTextField source = (MyTextField) e.getSource();
        //shouldYieldFocus(source); //ignore return value
        verify(source);
        source.selectAll();
    }
}
