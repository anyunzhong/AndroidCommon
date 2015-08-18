package net.datafans.android.common.widget.table.sectionindex;

import net.datafans.android.common.widget.table.TableViewCell;

import java.util.List;

/**
 * Created by zhonganyun on 15/8/18.
 */
public interface SectionIndexTableViewDataSource<T> {


    List<String> getIndexedTitles();

    List<String> getUnIndexedTitles();

    T getEntity(int index);

    T getUnIndexedEntity(int index);

//    int getSectionHeaderHeight(int section);
//
//    int getSectionFooterHeight(int section);


    TableViewCell<T> getTableViewCell(int section, int row);

    int getItemViewType(int section, int row);

    int getItemViewTypeCount();
}
