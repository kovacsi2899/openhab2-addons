/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.paradoxalarm.internal.handlers;

import static org.openhab.binding.paradoxalarm.internal.handlers.ParadoxAlarmBindingConstants.*;

import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.thing.Thing;
import org.openhab.binding.paradoxalarm.internal.exceptions.ParadoxBindingException;
import org.openhab.binding.paradoxalarm.internal.model.ParadoxPanel;
import org.openhab.binding.paradoxalarm.internal.model.Zone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link ParadoxZoneHandler} Handler that updates states of paradox zones from the cache.
 *
 * @author Konstantin_Polihronov - Initial contribution
 */
public class ParadoxZoneHandler extends EntityBaseHandler {

    private final Logger logger = LoggerFactory.getLogger(ParadoxZoneHandler.class);

    public ParadoxZoneHandler(@NonNull Thing thing) {
        super(thing);
    }

    @Override
    protected void updateEntity() {
        int index = calculateEntityIndex();
        try {
            List<Zone> zones = ParadoxPanel.getInstance().getZones();
            Zone zone = zones.get(index);
            if (zone != null) {
                updateState(ZONE_LABEL_CHANNEL_UID, new StringType(zone.getLabel()));
                updateState(ZONE_IS_OPENED_CHANNEL_UID, OpenClosedType.from(zone.getZoneState().isOpened()));
                updateState(ZONE_IS_TAMPERED_CHANNEL_UID, OpenClosedType.from(zone.getZoneState().isTampered()));
                updateState(ZONE_HAS_LOW_BATTERY_CHANNEL_UID, OpenClosedType.from(zone.getZoneState().hasLowBattery()));
            }
        } catch (ParadoxBindingException e) {
            logger.error("Unable to update zone {} due to missing ParadoxPanel. Exception: {}",
                    String.valueOf(index + 1), e);
        }
    }
}
