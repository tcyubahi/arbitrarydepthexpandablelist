package edu.asu.bsse.tcyubahi.arbitrarydepthexpandablelist;

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

        //Initialize adapter
        itemsAdapter = new MyListAdapter(items, context);
        itemsAdapter.setIndent(32);

        recyclerView.setAdapter(itemsAdapter);

    }
}
