/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mars.auvtree.nodes;

import java.awt.datatransfer.DataFlavor;
import mars.auv.AUV;

/**
 *
 * @author Thomas Tosik <tosik at iti.uni-luebeck.de>
 */
public class AUVFlavor extends DataFlavor {

    /**
     *
     */
    public static final DataFlavor CUSTOMER_FLAVOR = new AUVFlavor();

    /**
     *
     */
    public AUVFlavor() {
        super(AUV.class, "AUV");
    }
}