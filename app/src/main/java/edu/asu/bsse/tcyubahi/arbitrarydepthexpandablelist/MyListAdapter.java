package edu.asu.bsse.tcyubahi.arbitrarydepthexpandablelist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import cyubatres.arbitrarydepthexpandablelist.Item;
import cyubatres.arbitrarydepthexpandablelist.ItemsAdapter;

public class MyListAdapter extends ItemsAdapter {

    List<Item> itemList;
    private int indent = 0;

    private Context context;
    public MyListAdapter(List<?> items, Context context) {
        super(items, context);
        this.context = context;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        LinearLayout container;
        ImageView icon;
        public ItemViewHolder(View v) {
            super(v);
            text = v.findViewById(R.id.text);
            container = v.findViewById(R.id.container);
            icon = v.findViewById(R.id.icon);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(itemView);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder itemViewHolder, final int pos) {
        final ItemViewHolder itemViewHolder1 = (ItemViewHolder) itemViewHolder;
        itemViewHolder1.text.setText(itemList.get(pos).getHeader());
        final Item thisItem = itemList.get(pos);
        int offset = 0;

        for (int i = 0; i < thisItem.getLevel(); i++) {
            offset += indent;
        }

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) itemViewHolder1.container.getLayoutParams();
        params.setMargins(offset, 0,0,0);

        if (!thisItem.hasChildren()) {
            itemViewHolder1.icon.setVisibility(View.GONE);
        } else {
            itemViewHolder1.icon.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int pos) {
        // has no parent is type 0
        // has parent is type 1
        if (itemList.get(pos).hasParent()) {
            return 1;
        } else {
            return 0;
        }
    }

}
