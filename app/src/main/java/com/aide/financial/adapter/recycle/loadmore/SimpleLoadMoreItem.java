package com.aide.financial.adapter.recycle.loadmore;

import com.aide.financial.R;

public class SimpleLoadMoreItem extends LoadMoreItem {

    @Override
    public int getLayoutId() {
        return R.layout.item_recycler_load_more;
    }

    @Override
    public int getLoadingViewId() {
        return R.id.loading;
    }

    @Override
    public int getLoadFailViewId() {
        return R.id.load_fail;
    }

    @Override
    public int getLoadEndViewId() {
        return R.id.load_end;
    }

}
