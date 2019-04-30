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

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

// Put back to abstract

public abstract class ItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Item> itemList;
    private Context context;
    private int indent = 0;

    public ItemsAdapter(List<?> items, Context context) {
        if (!(items.get(0) instanceof Item)) {
            throw new IllegalArgumentException("All Items have to be extending Item");
        }
        this.itemList = (List<Item>) items;
        this.context = context;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public List<Item> getItemList () {
        return itemList;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }
}
