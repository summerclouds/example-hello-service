/**
 * Copyright (C) 2020 Mike Hummel (mh@mhus.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.summerclouds.example;

import java.net.URL;
import java.util.UUID;

import org.summerclouds.common.db.DbComfortableObject;
import org.summerclouds.common.db.annotations.DbEntity;
import org.summerclouds.common.db.annotations.DbPersistent;
import org.summerclouds.common.db.annotations.DbPrimaryKey;

@DbEntity
public class PageEntry extends DbComfortableObject {

    @DbPrimaryKey private UUID id;
    @DbPersistent private String linkName;
    @DbPersistent private URL linkDestination;

    public UUID getId() {
        return id;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public URL getLinkDestination() {
        return linkDestination;
    }

    public void setLinkDestination(URL linkDestination) {
        this.linkDestination = linkDestination;
    }

}
