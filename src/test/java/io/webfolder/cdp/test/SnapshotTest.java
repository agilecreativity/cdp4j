/**
 * cdp4j - Chrome DevTools Protocol for Java
 * Copyright © 2017, 2018 WebFolder OÜ (support@webfolder.io)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.webfolder.cdp.test;

import static java.nio.file.Paths.get;
import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.command.DOMSnapshot;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import io.webfolder.cdp.type.domsnapshot.CaptureSnapshotResult;

public class SnapshotTest {

    @Test
    public void test() {
        String path = get("src/test/resources/snapshot.html").toAbsolutePath().toUri().toString();

        Launcher launcher = new Launcher();

        try (SessionFactory factory = launcher.launch(); Session session = factory.create()) {
            session.navigate(path);
            DOMSnapshot snapshot = session.getCommand().getDOMSnapshot();
            snapshot.enable();
            CaptureSnapshotResult result = snapshot.captureSnapshot(Collections.emptyList());
            assertEquals(1, result.getDocuments().size());
            List<List<Double>> textBoxBounds = result.getDocuments().get(0).getTextBoxes().getBounds();
            assertEquals(1, textBoxBounds.size());
            assertEquals(4, textBoxBounds.get(0).size());
        }
    }
}