/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.paradoxalarm.internal.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link Entity} Entity - base abstract class for Paradox entities (Partitions, zones, etc).
 * Extend this class and add entity specific data (states, troubles, etc).
 *
 * @author Konstantin_Polihronov - Initial contribution
 */
public abstract class Entity {
    private static Logger logger = LoggerFactory.getLogger(Entity.class);

    private int id;
    private String label;

    public Entity(int id, String label) {
        this.id = id;
        this.label = label;
        logger.debug("Creating entity with label: {} and ID: {}", label, id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Partition [id=" + id + ", label=" + label;
    }

}