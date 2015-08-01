package net.datafans.android.common.widget.table;

import java.util.HashMap;
import java.util.Map;

import net.datafans.android.common.R;
import net.datafans.android.common.widget.table.refresh.ListViewAdapter;
import net.datafans.android.common.widget.table.refresh.ListViewListener;
import net.datafans.android.common.widget.table.refresh.RefreshControlType;
import net.datafans.android.common.widget.table.refresh.adapter.BGAListViewAdapter;
import net.datafans.android.common.widget.table.refresh.adapter.BGAListViewAdapter.RefreshType;
import net.datafans.android.common.widget.table.refresh.adapter.DropDownListViewAdapter;
import net.datafans.android.common.widget.table.refresh.adapter.OriginListViewAdapter;
import net.datafans.android.common.widget.table.refresh.adapter.PullDownListViewAdapter;
import net.datafans.android.common.widget.table.refresh.adapter.SwipeRefreshListViewAdapter;
import net.datafans.android.common.widget.table.refresh.adapter.UltraPullToRefreshListViewAdapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TableView<T> implements ListViewListener {

    private Map<RefreshControlType, ListViewAdapter> adapterMap = new HashMap<RefreshControlType, ListViewAdapter>();

    private RefreshControlType refreshType;

    private TableViewAdapter tableViewAdapter;
    private TableViewDataSource<T> dataSource;
    private TableViewDelegate delegate;

    private Map<Integer, Integer> sectionMap = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> rowMap = new HashMap<Integer, Integer>();

    private Context context;

    public Context getContext() {
        return context;
    }

    private boolean enableRefresh = false;
    private boolean enableLoadMore = false;
    private boolean enableAutoLoadMore = false;

    private TableViewStyle style;

    public TableView(Context context, RefreshControlType type) {
        this.context = context;
        refreshType = type;
        init();
    }

    public TableView(Context context, RefreshControlType type,
                     boolean enableRefresh, boolean enableLoadMore,
                     boolean enableAutoLoadMore, TableViewStyle style) {
        this.context = context;
        this.enableRefresh = enableRefresh;
        this.enableLoadMore = enableLoadMore;
        this.enableAutoLoadMore = enableAutoLoadMore;
        this.style = style;
        refreshType = type;
        init();
    }

    public TableView(Context context) {
        this.context = context;
        this.refreshType = RefreshControlType.BGANormal;
        init();
    }

    private void init() {
        tableViewAdapter = new TableViewAdapter();
        initView();
    }

    private ListViewAdapter getAdapter(RefreshControlType type) {
        ListViewAdapter adapter = adapterMap.get(type);
        if (adapter == null) {
            switch (type) {
                case None:
                    adapter = new OriginListViewAdapter(context, tableViewAdapter);
                    break;
                case PullDown:
                    adapter = new PullDownListViewAdapter(context, tableViewAdapter);
                    break;
                case SwipeRefresh:
                    adapter = new SwipeRefreshListViewAdapter(context,
                            tableViewAdapter);
                    break;
                case UltraPullToRefresh:
                    adapter = new UltraPullToRefreshListViewAdapter(context,
                            tableViewAdapter);
                    break;
                case DropDown:
                    adapter = new DropDownListViewAdapter(context, tableViewAdapter);
                    break;
                case BGANormal:
                    adapter = new BGAListViewAdapter(context, tableViewAdapter,
                            RefreshType.Normal);
                    break;
                case BGAMooc:
                    adapter = new BGAListViewAdapter(context, tableViewAdapter,
                            RefreshType.MoocStyle);
                    break;
                case BGAStickiness:
                    adapter = new BGAListViewAdapter(context, tableViewAdapter,
                            RefreshType.Stickiness);
                    break;
                default:
                    break;
            }

            adapter.enableRefresh(enableRefresh);
            adapter.enableLoadMore(enableLoadMore);
            adapter.enableAutoLoadMore(enableAutoLoadMore);

            adapterMap.put(type, adapter);
        }
        return adapter;
    }

    public ListViewAdapter getAdapter() {
        return getAdapter(refreshType);
    }

    public View getView() {
        return getAdapter().getRootView();
    }

    public BaseAdapter getTableViewAdapter() {
        return tableViewAdapter;
    }

    public void reloadData() {
        tableViewAdapter.notifyDataSetChanged();
    }

    private void initView() {

        ListViewAdapter adapter = getAdapter();
        adapter.setListener(this);

        ListView listView = adapter.getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (delegate == null) return;
                int section = getSection(position);
                int row = getRow(position);
                if (style == TableViewStyle.GROUP)
                    row = row -1;
                delegate.onClickRow(section, row);
            }
        });
    }

    private class TableViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (dataSource == null) {
                return 0;
            }
            return getTotalCellCount();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressWarnings("unchecked")
        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {

            int section = getSection(position);
            int row = getRow(position);


            if (style == TableViewStyle.GROUP){


                if (row == 0 || row == dataSource.getRows(section) + 1) {

                    View rootView = LayoutInflater.from(context).inflate(R.layout.table_view_header_footer, null);

                    View topline = rootView.findViewById(R.id.topline);
                    View bottomline = rootView.findViewById(R.id.bottomline);
                    TextView titleView = (TextView) rootView.findViewById(R.id.title);

                    if (row == 0) {
                        topline.setVisibility(View.GONE);
                        bottomline.setVisibility(View.VISIBLE);
                        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dataSource.getSectionHeaderHeight(section));
                        rootView.setLayoutParams(layoutParams);
                        titleView.setText(dataSource.getSectionHeaderTitle(section));

                    } else {
                        topline.setVisibility(View.VISIBLE);
                        bottomline.setVisibility(View.GONE);
                        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dataSource.getSectionFooterHeight(section));
                        rootView.setLayoutParams(layoutParams);
                        titleView.setText(dataSource.getSectionFooterTitle(section));
                    }

                    if (style == TableViewStyle.GROUP){
                        titleView.setVisibility(View.VISIBLE);
                    }else{
                        titleView.setVisibility(View.GONE);
                        topline.setVisibility(View.GONE);
                        bottomline.setVisibility(View.GONE);
                    }

                    return rootView;
                } else {

                    View view = convertView;
                    TableViewCell<T> cell = null;
                    //if (view == null && (view instanceof String)) {
                    if (style == TableViewStyle.GROUP)
                        cell = dataSource.getTableViewCell(section, row - 1);
                    else
                        cell = dataSource.getTableViewCell(section, row);
                    view = cell.getView();
                    view.setTag(cell);

                    Log.e("CELL", "8888");

                    if (row == dataSource.getRows(section)){
                        cell.divider.setVisibility(View.GONE);
                    }else{
                        cell.divider.setVisibility(View.VISIBLE);
                    }

//                } else {
//
//                    Log.e("CELL","9999");
//                    cell = (TableViewCell<T>) view.getTag();
//                }


                    T t = null;
                    if (style == TableViewStyle.GROUP)
                        t = dataSource.getEntity(section, row - 1);
                    else
                        t = dataSource.getEntity(section, row);


                    cell.refresh(t);

                    return view;
                }



            }else{


                View view = convertView;
                TableViewCell<T> cell = null;
                //if (view == null && (view instanceof String)) {
                if (style == TableViewStyle.GROUP)
                    cell = dataSource.getTableViewCell(section, row - 1);
                else
                    cell = dataSource.getTableViewCell(section, row);
                view = cell.getView();
                view.setTag(cell);

                Log.e("CELL", "8888");

//                } else {
//
//                    Log.e("CELL","9999");
//                    cell = (TableViewCell<T>) view.getTag();
//                }


                T t = null;
                if (style == TableViewStyle.GROUP)
                    t = dataSource.getEntity(section, row - 1);
                else
                    t = dataSource.getEntity(section, row);

                cell.divider.setVisibility(View.GONE);


                cell.refresh(t);

                return view;
            }




        }
    }


    private int getTotalCellCount() {
        if (dataSource == null)
            return 0;
        int section = dataSource.getSections();
        if (style == TableViewStyle.PLAIN)
            section = 1;
        int totalCount = 0;
        for (int i = 0; i < section; i++) {
            int row = dataSource.getRows(i);
            if (style == TableViewStyle.GROUP)
            row = row + 2; //加上header和footer两个特殊的row
            for (int j = 0; j < row; j++) {
                sectionMap.put(totalCount + j, i);
                rowMap.put(totalCount + j, j);
            }
            totalCount += row;
        }
        return totalCount;
    }

    private int getSection(int postion) {
        return sectionMap.get(postion);
    }

    private int getRow(int position) {
        return rowMap.get(position);
    }


    public TableViewDataSource<T> getDataSource() {
        return dataSource;
    }

    public void setDataSource(TableViewDataSource<T> dataSource) {
        this.dataSource = dataSource;
    }

    public TableViewDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(TableViewDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onRefresh() {

        if (delegate == null) {
            return;
        }

        delegate.onRefresh();
    }

    @Override
    public void onLoadMore() {
        if (delegate == null) {
            return;
        }

        delegate.onLoadMore();
    }

    public void endRefresh() {
        getAdapter().endRefresh();
    }

    public void endLoadMore() {
        getAdapter().endLoadMore();
    }

    public void loadOver(boolean over) {
        getAdapter().loadOver(over);
    }

    public void hideDivider() {
        getAdapter().getListView().setDivider(null);
    }


}
