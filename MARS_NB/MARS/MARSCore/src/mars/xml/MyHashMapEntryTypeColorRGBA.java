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
package mars.xml;

import com.jme3.math.ColorRGBA;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Tosik
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class MyHashMapEntryTypeColorRGBA extends MyHashMapEntryType{
    /**
     * 
     */
    @XmlAttribute
    public String key; 
    
    /**
     * 
     */
    @XmlElement
    @XmlJavaTypeAdapter(ColorRGBAAdapter.class)
    public ColorRGBA value;
    
    /**
     * 
     */
    public MyHashMapEntryTypeColorRGBA() {}
    
    /**
     * 
     * @param e
     */
    public MyHashMapEntryTypeColorRGBA(Map.Entry<String,Object> e) {
       key = e.getKey();
       if(e.getValue() instanceof ColorRGBA){
            value = (ColorRGBA)e.getValue();
       }
    }
    
    /**
     * 
     * @return
     */
    public String getKey() {
        return key;
    }
    
    /**
     * 
     * @return
     */
    public String getUnit() {
        return "";
    }

    /**
     * 
     * @return
     */
    public ColorRGBA getValue() {
        return value;
    }
    
    /**
     * 
     * @return
     */
    public Object getObject() {
        return value;
    }
}
