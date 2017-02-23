package com.cnlive.libs;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.cnlive.libs.adapter.MyAdapter;
import com.cnlive.libs.channelnterface.ItemMoveHelperApi;

import java.util.ArrayList;

/**
 * @author chenshuo
 * @time 2017/2/16  16:05
 * @desc ${TODD}
 */
public class ItemMoveCallBackImpl extends ItemTouchHelper.Callback {

    private ItemMoveHelperApi mHelperApi;
    private Context context;
    private ArrayList<String> arrayListInterest, arrayListOther;
    private RecyclerView.ViewHolder viewHolder;
    private MyAdapter myAdapter;
    private ArrayList<Integer> positionArray;
    private ArrayList<Integer> positionArrayNoClick;
    private  Vibrator vibrator;
    private int mTime=0;



    public ItemMoveCallBackImpl(ItemMoveHelperApi helperApi,
                                Context context,
                                ArrayList<String> arrayListInterest,
                                ArrayList<String> arrayListOther,
                                MyAdapter myAdapter) {
        this.mHelperApi = helperApi;
        this.context = context;
        this.arrayListInterest = arrayListInterest;
        this.arrayListOther = arrayListOther;
        this.myAdapter = myAdapter;
        vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
    }


    /**
     * 点击事件此处是被用来动用（解决点击事件，和排序数据的转换）
     */
    public void OnItemClickListener(int position) {
        if (null != positionArrayNoClick && positionArrayNoClick.contains(position)) {

        } else {
            if (position > (arrayListInterest.size() + 1)) {
                String stringArrayListOther = arrayListOther.get(position - 2 - arrayListInterest.size());
                arrayListOther.remove(position - 2 - arrayListInterest.size());
                arrayListInterest.add(stringArrayListOther);
                myAdapter.notifyDataSetChanged();

            } else if (0 < position && position < arrayListInterest.size() + 1) {
                String item = arrayListInterest.get(position - 1);
                arrayListInterest.remove(position - 1);
                arrayListOther.add(item);
                myAdapter.notifyItemRemoved(position);
                myAdapter.notifyItemChanged(position, myAdapter.getItemCount());

            }
        }

        mHelperApi.onItemMovedAndAddSort(arrayListInterest, arrayListOther);
    }

    /**
     * 是否左右滑动删除某一item
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }


    /**
     * 是否允许长按
     */
    @Override
    public boolean isLongPressDragEnabled() {
        boolean isLongPressDrag = 0 < viewHolder.getAdapterPosition() && viewHolder.getAdapterPosition() < arrayListInterest.size() + 1;
        if (null != positionArray) {
            if (isLongPressDrag && !positionArray.contains(viewHolder.getAdapterPosition())) {
                vibrator.vibrate(mTime);

            }
            return isLongPressDrag && !positionArray.contains(viewHolder.getAdapterPosition());
        } else {
            vibrator.vibrate(mTime);
            return isLongPressDrag;
        }
    }

    /**
     * 移动时触发，移动到不同的位置触发事件
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        if (mHelperApi != null && 0 < target.getAdapterPosition() && target.getAdapterPosition() < arrayListInterest.size() + 1) {
            String stringArrayListIntersting = arrayListInterest.get(viewHolder.getAdapterPosition() - 1);
            arrayListInterest.remove(viewHolder.getAdapterPosition() - 1);
            arrayListInterest.add(target.getAdapterPosition() - 1, stringArrayListIntersting);
            myAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            mHelperApi.onItemMovedAndAddSort(arrayListInterest, arrayListOther);
        }


        return false;
    }


    /***
     * 移动删除某一项时此方法触发
     */

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    /**
     * 移动的方式，(排序dragFlags，上下左右等)，（移动删除swipeFlags）
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        this.viewHolder = viewHolder;
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }




    /**
     * 设置振动器时间
     */
    public void setVibratorTime(int mTime) {
        this.mTime=mTime;
    }


    /**
     * 设置不可点击的position
     */
    public void setNoClickEnable(ArrayList<Integer> positionArrayNoClick) {
        this.positionArrayNoClick = positionArrayNoClick;

    }

    /**
     * 设置不可长按拖拽position
     */
    public void setNoLongPressDragEnabled(ArrayList<Integer> positionArray) {
        this.positionArray = positionArray;
    }

}
