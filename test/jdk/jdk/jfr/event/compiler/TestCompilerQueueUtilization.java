/*
 * Copyright (c) 2023, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package jdk.jfr.event.compiler;

import java.util.List;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.EventNames;
import jdk.test.lib.jfr.Events;

/**
 * @test
 * @requires vm.flagless
 * @requires vm.hasJFR
 * @requires vm.compMode!="Xint"
 * @library /test/lib
 * @run main/othervm jdk.jfr.event.compiler.TestCompilerQueueUtilization
 */
public class TestCompilerQueueUtilization {
    private final static String EVENT_NAME = EventNames.CompilerQueueUtilization;

    public static void main(String[] args) throws Exception {
        try (Recording recording = new Recording()) {
            recording.enable(EVENT_NAME);
            recording.start();
            recording.stop();

            List<RecordedEvent> events = Events.fromRecording(recording);
            Events.hasEvents(events);
            for (RecordedEvent event : events) {
                System.out.println("Event:" + event);
                Events.assertField(event, "compiler").notEmpty();
                Events.assertField(event, "addedRate").atLeast(0L);
                Events.assertField(event, "removedRate").atLeast(0L);
                Events.assertField(event, "queueSize").atLeast(0L);
                Events.assertField(event, "peakQueueSize").atLeast(0L);
                Events.assertField(event, "addedCount").atLeast(0L);
                Events.assertField(event, "removedCount").atLeast(0L);
                Events.assertField(event, "totalAddedCount").atLeast(0L);
                Events.assertField(event, "totalRemovedCount").atLeast(0L);
                Events.assertField(event, "compilerThreadCount").atLeast(0);
            }
        }
    }
}
