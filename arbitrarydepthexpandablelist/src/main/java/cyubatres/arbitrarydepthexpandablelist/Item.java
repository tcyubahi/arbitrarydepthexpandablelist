package cyubatres.arbitrarydepthexpandablelist;

/*
 * Copyright @ 2019 Tresor Cyubahiro
 * @author Tresor Cyubahiro
 * @version April 25, 2019
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.List;

public class Item {
    private Item parent = null;
    private List<Item> children = null;
    private boolean isExpanded = false;
    private String header;
    private int level;
    private int pos;

    public Item(int level) {
        this.level = level;
    }

    public void setParent(Item parent) {
        this.parent = parent;
    }

    public void setChildren(List<Item> children) {
        this.children = children;
        for (Item item: this.children) {
            item.setParent(this);
            item.setLevel(this.level + 1);
        }
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public Item getParent() {
        return parent;
    }

    public List<Item> getChildren() {
        return children;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public String getHeader() {
        return header;
    }

    public int getLevel() {
        return level;
    }

    public int getPos() {
        return pos;
    }

    public boolean hasParent() {
        if (this.parent != null) {
            return true;
        }
        return false;
    }

    public boolean hasChildren () {
        if (children != null && children.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
