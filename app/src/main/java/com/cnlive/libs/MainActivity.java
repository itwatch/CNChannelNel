package com.cnlive.libs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cnlive.libs.adapter.MyAdapter;
import com.cnlive.libs.channelnterface.ItemMoveHelperApi;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements ItemMoveHelperApi {

    private ArrayList<String> arrayListInterest, arrayListOther;
    private LayoutInflater mLayoutInflater;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this, OkHttpImagePipelineConfigFactory
                .newBuilder(this, new OkHttpClient())
                .build());
        mLayoutInflater = LayoutInflater.from(this);
        setContentView(R.layout.activity_main);
        intDate();
        initView();

    }

    private void intDate() {
        arrayListInterest = new ArrayList<>();
        arrayListOther = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            arrayListInterest.add("http://yweb3.cnliveimg.com/img/CMCC_MOVIE/618549134_699022_HSJ1080V.jpg");
            arrayListOther.add("http://yweb3.cnliveimg.com/img/CMCC_MOVIE/618549134_699022_HSJ1080V.jpg");
        }

    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.tv_rceycl);
        myAdapter = new MyAdapter(recyclerView, MainActivity.this, arrayListInterest, arrayListOther, this);
        GridLayoutManager layoutManager1 = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager1);
        recyclerView.setAdapter(myAdapter);
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        myAdapter.setNoLongPressDragEnabled(integers);


    }

    /**
     * 点击，以及排序之后的数据的封装
     */
    @Override
    public void onItemMovedAndAddSort(ArrayList<String> arrayListInterest, ArrayList<String> arrayListOther) {
        Log.v("arrayListInterest", "arrayListInterest=" + arrayListInterest.toString() + "     arrayListOther" + arrayListOther.toString());


    }
    /**
     * 一种ViewHolder的（textView） 的显示（“频道”，“其他”）
     */
    @Override
    public void setTextDate(RecyclerView.ViewHolder viewHolderText, int position) {

        if (viewHolderText instanceof MyAdapter.AdapterHolderType && position == 0) {
            MyAdapter.AdapterHolderType viewHolderText1 = (MyAdapter.AdapterHolderType) viewHolderText;
            viewHolderText1.textView.setText("频道");

        } else if (viewHolderText instanceof MyAdapter.AdapterHolderType && position != 0) {
            MyAdapter.AdapterHolderType viewHolderText1 = (MyAdapter.AdapterHolderType) viewHolderText;
            viewHolderText1.textView.setText("其他");
        }
    }


    /**
     * 另一种ViewHolder的（imageView） 的显示（卡发着可以自行定义xml ViewHolder）
     */

    @Override
    public void SetChanelDate(RecyclerView.ViewHolder viewHolderChannel, int position) {


        if (0 < position && position < arrayListInterest.size() + 1) {
            MyAdapter.AdapterHolder viewHolderText1 = (MyAdapter.AdapterHolder) viewHolderChannel;
            viewHolderText1.simpleDraweeView.setImageURI(arrayListInterest.get(position - 1));
        } else if (position > arrayListInterest.size() + 1) {
            MyAdapter.AdapterHolder viewHolderText1 = (MyAdapter.AdapterHolder) viewHolderChannel;
            viewHolderText1.simpleDraweeView.setImageURI(arrayListOther.get(position - 2 - arrayListInterest.size()));
        }


    }
    /**
     * 绑定holder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewItemTypeHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View inflate = mLayoutInflater.inflate(R.layout.receyceltpye, parent, false);
            return myAdapter.new AdapterHolderType(inflate);

        } else {
            View inflate = mLayoutInflater.inflate(R.layout.receycelhold1, parent, false);
            return myAdapter.new AdapterHolder(inflate);
        }
    }


}
