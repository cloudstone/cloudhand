package com.cloudstone.cloudhand.fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.cloudstone.cloudhand.activity.TableInfoActivity;

import com.cloudstone.cloudhand.data.Table;

public class TableInfoEmptyFragment extends TableInfoBaseFragment {
    @Override
    protected List<Table> getTables() {
        List<Table> tables = new ArrayList<Table>();
        Iterator<Table> it = ((TableInfoActivity)(getActivity())).getTables().iterator();
        while (it.hasNext()) {
            Table table = it.next();
            if(table.getStatus() == 0) {
                tables.add(table);
            }
        }
        return tables;
    }
}
