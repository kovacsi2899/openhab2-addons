/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.paradoxalarm.internal.model;

import java.util.Arrays;

import org.openhab.binding.paradoxalarm.internal.util.ParadoxUtil;

/**
 * The {@link PanelType} Enum of all panel types
 *
 * @author Konstantin_Polihronov - Initial contribution
 */
public enum PanelType {
    EVO48,
    EVO192,
    EVOHD,
    SP5500,
    SP6000,
    SP7000,
    MG5000,
    MG5050,
    SP4000,
    SP65,
    UNKNOWN;

    @Override
    public String toString() {
        return this.name();
    }

    public static PanelType parsePanelType(byte[] infoPacket) {
        if (infoPacket == null || infoPacket.length != 37) {
            return PanelType.UNKNOWN;
        }
        byte[] panelTypeBytes = Arrays.copyOfRange(infoPacket, 6, 8);
        String key = "0x" + ParadoxUtil.byteArrayAsString(panelTypeBytes);

        return ParadoxInformationConstants.panelTypes.getOrDefault(key, PanelType.UNKNOWN);
    }

    public static PanelType from(String panelTypeStr) {
        try {
            return PanelType.valueOf(panelTypeStr);
        } catch (Exception e) {
            return PanelType.UNKNOWN;
        }
    }
}
