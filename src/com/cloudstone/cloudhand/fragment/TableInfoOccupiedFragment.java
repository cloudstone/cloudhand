package com.cloudstone.cloudhand.fragment;

import com.cloudstone.cloudhand.data.Table;

/**
 * 
 * @author xhc
 *
 */
public class TableInfoOccupiedFragment extends TableInfoBaseFragment {
    @Override
    protected boolean filterTable(Table table) {
        return table.getStatus() > 0;
    }
}
