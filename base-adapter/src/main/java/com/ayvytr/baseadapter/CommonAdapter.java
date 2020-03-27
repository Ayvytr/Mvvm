package com.ayvytr.baseadapter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 16/4/9.
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T>
{
    //如果需要，再重新加上
//    protected int mLayoutId;

    public CommonAdapter(Context context, int layoutId)
    {
        this(context, layoutId, new ArrayList<T>(0));
    }

    public CommonAdapter(Context context, final int layoutId, List<T> datas)
    {
        super(context, datas);
        mContext = context;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>()
        {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType( T item, int position)
            {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position)
            {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);

}
