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
 * The {@link Partition} Paradox partition.
 * ID is always numeric (1-8 for Evo192)
 *
 * @author Konstantin_Polihronov - Initial contribution
 */
public class Partition extends Entity {

    private static Logger logger = LoggerFactory.getLogger(Partition.class);

    private PartitionState state = new PartitionState();

    public Partition(int id, String label) {
        super(id, label);
    }

    public PartitionState getState() {
        return state;
    }

    public Partition setState(PartitionState state) {
        this.state = state;
        logger.debug("Partition {}:\t{}", getLabel(), getState().getMainState());
        return this;
    }
}