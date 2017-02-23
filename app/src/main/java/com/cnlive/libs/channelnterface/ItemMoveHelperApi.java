package com.cnlive.libs.channelnterface;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * @author chenshuo
 * @time 2017/2/13  17:05
 * @desc ${TODD}
 */
public interface ItemMoveHelperApi {


    /**
     * 移动排序，点击喜欢和其他频道之间的转换之后的数据
     */

    void onItemMovedAndAddSort(ArrayList<String> arrayListInterest, ArrayList<String> arrayListOther);

    /**
     * 设置喜欢频道的文字   “频道”，“其他”
     */
    void setTextDate(RecyclerView.ViewHolder viewHolderText, int position);

    /**
     * 设置频道图片的数据
     */
    void SetChanelDate(RecyclerView.ViewHolder viewHolderChannel, int position);
    /**
     * 设置xml数据, 创建holder
     */
    RecyclerView.ViewHolder  onCreateViewItemTypeHolder(ViewGroup parent, int viewType);

}

