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
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public class CustomRecyclerView extends RecyclerView implements OnItemClickListener {

    private Context context;
    private ItemsAdapter itemsAdapter;
    private boolean isToogleItemOnClick = true;
    private RecyclerItemClickListener recyclerItemClickListener;
    private OnItemClickListener onItemClickListener;


    public void setOnItemClick(OnItemClickListener onItemClick) {
        this.onItemClickListener = onItemClick;
    }

    public void setToogleItemOnClick(boolean toogleItemOnClick) {
        isToogleItemOnClick = toogleItemOnClick;
    }

    public CustomRecyclerView (Context context) {
        super(context);
        this.context = context;
        init(context);
    }

    public CustomRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public CustomRecyclerView(Context context, AttributeSet attributeSet, int style) {
        super(context, attributeSet, style);
        init(context);
    }

    private void init(Context context) {
        recyclerItemClickListener = new RecyclerItemClickListener(context);
        recyclerItemClickListener.setOnItemClick(this);
        addOnItemTouchListener(recyclerItemClickListener);
    }

    public void removeItemClickListeners () {
        if (recyclerItemClickListener != null) {
            removeOnItemTouchListener(recyclerItemClickListener);
        }
    }

    @Override
    public void setAdapter(android.support.v7.widget.RecyclerView.Adapter  adapter) {
        if (!(adapter instanceof ItemsAdapter)) {
            throw new IllegalStateException("Adapter must be of class ItemsAdapter");
        }
        itemsAdapter = (ItemsAdapter) adapter;
        super.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, Item clickedItem, int pos) {
        if (isToogleItemOnClick) {
            toggleItemsGroup(pos);
        }
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(view, clickedItem,pos);
        }
    }

    private int getExpandedPos(int level) {
        List<Item> list = itemsAdapter.getItemList();
        for (Item item: list) {
            if (level == item.getLevel() && item.isExpanded()) {
                return list.indexOf(item);
            }
        }
        return -1;
    }

    private int getItemsForRemoval(Item selectedItem) {
        return selectedItem.getChildren().size();
    }

    public void toggleItemsGroup(int pos) {
        if (!itemsAdapter.itemList.get(pos).hasChildren()) {
            return;
        }

        List<Item> list = itemsAdapter.getItemList();
        Item clickedItem = list.get(pos);

            if (clickedItem.isExpanded()) {
                clickedItem.setExpanded(false);
                removeAllChildren(clickedItem.getChildren());
                removePreviousItems(list,pos, clickedItem.getChildren().size() > 0? clickedItem.getChildren().size(): 0);
            } else {
                if (clickedItem.isExpanded()) {
                    removePreviousItems(list, pos, clickedItem.getChildren().size() > 0? clickedItem.getChildren().size(): 0);
                    addItems(clickedItem, list, clickedItem.getPos());
                } else {
                    addItems(clickedItem, list, pos);
                }
            }
    }

    public void removeAllChildren(List<Item> list) {
        for (Item item: list) {
            if (item.isExpanded()) {
                item.setExpanded(false);
                removeAllChildren(item.getChildren());
                removePreviousItems(itemsAdapter.itemList, item.getPos(), item.getChildren().size());
            }
        }
    }

    private void resetPos() {
        int pos = 0;
        for (Item item: itemsAdapter.getItemList()) {
            item.setPos(pos++);
        }
    }

    private void removePreviousItems(List<Item> previousList, int pos, int numItemsAdded) {
        for (int i = 0; i < numItemsAdded; i++) {
            toggleIcon(pos+1);
            previousList.remove(pos + 1);
        }
        itemsAdapter.setItemList(previousList);
        itemsAdapter.notifyItemRangeRemoved(pos + 1, numItemsAdded);
        toggleIcon(pos);
        resetPos();
    }

    private void addItems(Item clickedItem, List<Item> list, int pos) {
        if (clickedItem.hasChildren()) {
            toggleIcon(pos);
            list.addAll(pos + 1, clickedItem.getChildren());
            clickedItem.setExpanded(true);
            itemsAdapter.setItemList(list);
            itemsAdapter.notifyItemRangeInserted(pos+1, clickedItem.getChildren().size());
            resetPos();
        }
    }

    public void toggleIcon (int pos) {

    }

    private final class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private OnItemClickListener onItemClickListener;

        void setOnItemClick(OnItemClickListener onItemClick) {
            this.onItemClickListener = onItemClick;
        }

        RecyclerItemClickListener(Context context) {
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && gestureDetector.onTouchEvent(e)) {
                childView.performClick();
                int pos = view.getChildAdapterPosition(childView);
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(childView, itemsAdapter.getItemList().get(pos), pos);
                    toggleIcon(pos);
                }
                return isToogleItemOnClick;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean arg) {

        }
    }

}
