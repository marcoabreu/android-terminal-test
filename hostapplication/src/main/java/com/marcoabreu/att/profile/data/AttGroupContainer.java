package com.marcoabreu.att.profile.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AbreuM on 29.07.2016.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso( { AttActionDevice.class, AttActionHost.class, AttSleep.class, AttWhile.class })
public abstract class AttGroupContainer extends AttComposite {
    @XmlElementRef
    protected List<AttComposite> composites;

    public AttGroupContainer() {
        composites = new ArrayList<>();
    }

    public void addChild(AttComposite attComposite) {
        this.composites.add(attComposite);
    }

    public List<AttComposite> getComposites() {
        return composites;
    }
}
