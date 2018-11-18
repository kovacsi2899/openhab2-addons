/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.paradoxalarm.internal.communication;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link MemoryMap} this keeps Paradox RAM map as cached object.
 * Every record in the list is byte array which contains 64 byte RAM page.
 *
 * @author Konstantin_Polihronov - Initial contribution
 */
public class MemoryMap {
    private List<byte[]> ramCache = new ArrayList<byte[]>();

    public MemoryMap(List<byte[]> ramCache) {
        this.ramCache = ramCache;
    }

    public List<byte[]> getRamCache() {
        return ramCache;
    }

    public void setRamCache(List<byte[]> ramCache) {
        this.ramCache = ramCache;
    }

    public byte[] getElement(int index) {
        return ramCache.get(index);
    }

    public void updateElement(int index, byte[] elementValue) {
        ramCache.set(index, elementValue);
    }
}
