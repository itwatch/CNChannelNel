package com.cnlive.libs.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cnlive.libs.ItemMoveCallBackImpl;
import com.cnlive.libs.R;
import com.cnlive.libs.channelnterface.ItemMoveHelperApi;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * @author chenshuo
 * @time 2017/2/15  16:16
 * @desc ${TODD}
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<String> arrayListTop;
    private ArrayList<String> arrayListBottom;
    private ItemMoveHelperApi itemMoveHelperApi;
    private final ItemMoveCallBackImpl mMoveCallBack;
    private static final int TEXT_HOLDER_TYPE = 0;
    private static final int IMG_HOLDER_TYPE = 1;

    public MyAdapter(RecyclerView recyclerView, Context context, ArrayList<String> arrayListTop,
                     ArrayList<String> arrayListBottom, ItemMoveHelperApi itemMoveHelperApi
    ) {
        this.context = context;
        this.arrayListTop = arrayListTop;
        this.arrayListBottom = arrayListBottom;
        this.itemMoveHelperApi = itemMoveHelperApi;
        mMoveCallBack = new ItemMoveCallBackImpl(itemMoveHelperApi, context, arrayListTop, arrayListBottom, this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(mMoveCallBack);
        touchHelper.attachToRecyclerView(recyclerView);
    }


    public void setNoLongPressDragEnabled(ArrayList<Integer > positionArray){
        mMoveCallBack.setNoLongPressDragEnabled(positionArray);
    }

    /**
     * 不同类型的item换行
     */
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == MyAdapter.TEXT_HOLDER_TYPE
                            ? gridManager.getSpanCount() : MyAdapter.IMG_HOLDER_TYPE;
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0 | position == arrayListTop.size() + 1) {
            return MyAdapter.TEXT_HOLDER_TYPE;
        } else {
            return MyAdapter.IMG_HOLDER_TYPE;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return itemMoveHelperApi.onCreateViewItemTypeHolder(parent, viewType);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMoveCallBack.OnItemClickListener(holder.getAdapterPosition());
            }
        });

        itemMoveHelperApi.setTextDate(holder, position);
        itemMoveHelperApi.SetChanelDate(holder, position);

    }

    @Override
    public int getItemCount() {
        return arrayListTop.size() + 2 + arrayListBottom.size();
    }


    public class AdapterHolder extends RecyclerView.ViewHolder {
        public SimpleDraweeView simpleDraweeView;

        public AdapterHolder(View itemView) {
            super(itemView);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.friend_face_img);

        }
    }

    public class AdapterHolderType extends RecyclerView.ViewHolder {
        public TextView textView;

        public AdapterHolderType(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_type);
        }
    }
}


