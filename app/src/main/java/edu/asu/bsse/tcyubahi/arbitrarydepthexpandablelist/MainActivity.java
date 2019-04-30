package edu.asu.bsse.tcyubahi.arbitrarydepthexpandablelist;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import cyubatres.arbitrarydepthexpandablelist.CustomRecyclerView;
import cyubatres.arbitrarydepthexpandablelist.Item;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private CustomRecyclerView recyclerView;
    private MyListAdapter itemsAdapter;
    private ArrayList<Item> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        recyclerView = (CustomRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        for (int i = 0; i < 1; i++) {
            Item newItem = new Item(0);
            newItem.setHeader("Item " + i);

            items.add(newItem);
            List<Item> children = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                Item child = new Item(1);
                child.setHeader("Item " + i + "" + j);
                children.add(child);
                List<Item> thirdChildren = new ArrayList<>();
                for (int k = 0; k < 2; k++) {
                    Item thirdChild = new Item(2);
                    thirdChild.setHeader("Item " + i + "" + j + "" + k);
                    thirdChildren.add(thirdChild);
                    List<Item> fourthChildren = new ArrayList<>();
                    for (int m = 0; m < 1; m++) {
                        Item fourthChild = new Item(3);
                        fourthChild.setHeader("Item " + i + "" + j + "" + k + "" + m);
                        fourthChildren.add(fourthChild);
                    }
                    thirdChild.setChildren(fourthChildren);
                }
                child.setChildren(thirdChildren);
            }
            newItem.setChildren(children);
        }

        //
        itemsAdapter = new MyListAdapter(items, context);
        itemsAdapter.setIndent(32);

        recyclerView.setAdapter(itemsAdapter);

    }
}
