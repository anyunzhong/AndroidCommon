package net.datafans.android.test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import net.datafans.android.common.widget.controller.SectionIndexTableViewController;
import net.datafans.android.common.widget.table.TableViewCell;
import net.datafans.android.test.R;

import java.util.ArrayList;
import java.util.List;

public class TestSectionTableViewController extends SectionIndexTableViewController<String> {

    private List<String> indexedTitles = new ArrayList<>();

    private List<String> unIndexedTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        indexedTitles.add("Allen");
        indexedTitles.add("Allen1984");
        indexedTitles.add("中国");
        indexedTitles.add("Yanhua");


        unIndexedTitles.add("新的朋友");
        unIndexedTitles.add("群组");

        super.onCreate(savedInstanceState);



    }

    @Override
    protected String getNavTitle() {
        return "分组表格";
    }

    @Override
    public List<String> getIndexedTitles() {
        return indexedTitles;
    }

    @Override
    public List<String> getUnIndexedTitles() {
        return unIndexedTitles;
    }

    @Override
    public String getEntity(int index) {
        return indexedTitles.get(index);
    }

    @Override
    public void onClickRow(int index) {
        Log.d("CLICK index", ""+index);
    }

    @Override
    public TableViewCell<String> getTableViewCell(int section, int row) {
        return new SectionTableViewCell(R.layout.section_table_item, LayoutInflater.from(this));
    }

    @Override
    public int getItemViewType(int section, int row) {
        return 0;
    }

    @Override
    public int getItemViewTypeCount() {
        return 1;
    }


    @Override
    public String getUnIndexedEntity(int index) {
        return unIndexedTitles.get(index);
    }

    @Override
    public void onClickUnIndexedRow(int index) {
        Log.d("CLICK unindex", ""+index);
    }
}
