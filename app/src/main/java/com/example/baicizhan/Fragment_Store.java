package com.example.baicizhan;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import Data.Card;
import Data.Gallery;
import Tool.CardAdapter;

public class Fragment_Store extends Fragment {
    private Banner banner;
    private List<Integer> bannerList;
    private MZBannerView galleryRecycle;
    private List<Integer> galleries = Arrays.asList(new Integer[]{R.drawable.gallery1,R.drawable.gallery2,R.drawable.gallery3,R.drawable.gallery4,
        R.drawable.gallery5,R.drawable.gallery6});
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private GridLayoutManager layoutManager1;
    private GridLayoutManager layoutManager2;
    private GridLayoutManager layoutManager3;
    private GridLayoutManager layoutManager4;
    private CardAdapter cardAdapter1;
    private CardAdapter cardAdapter2;
    private CardAdapter cardAdapter3;
    private CardAdapter cardAdapter4;
    private Card[] goods = {
            new Card(R.drawable.word_machine,"[学霸爆款]百词斩电子单词机卡片机","真人发音背单词神器","¥219"),
            new Card(R.drawable.shuqian,"交通信号磁性书签","5种交通信号图案","¥14.9"),
            new Card(R.drawable.bianqian,"存折系列计划本（日、周、月计划）","把目标”存进“计划本","¥9.9"),
            new Card(R.drawable.tiezhi,"Midori手账专用贴纸书签便签贴","日本顶级文具品牌授权","¥38"),
            new Card(R.drawable.budai,"薄荷阅读-金句杜邦笔袋","把金句背出门","¥109"),
            new Card(R.drawable.cidianbi,"[新品上市]百词斩词典笔扫描笔","备考词典笔","¥519"),
            new Card(R.drawable.xuexiji,"[领一百元券]百词斩扫学机","年度新品2in1扫学机","¥1399"),
            new Card(R.drawable.bidai,"大容量猫窝笔袋","可装60支笔","¥38.9"),
            new Card(R.drawable.sijibeikao,"大学英语四级备考大全套","四级词汇+真题备考包","¥87.9"),
            new Card(R.drawable.bijiben,"蟾宫折桂线状笔记本","手绘插画，全彩内页","¥27.9")};
    private List<Card> goodList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store,container,false);

        initBannerList();
        banner = (Banner) view.findViewById(R.id.banner);
        galleryRecycle = (MZBannerView) view.findViewById(R.id.Gallery);
        recyclerView1 = (RecyclerView) view.findViewById(R.id.recycleView1);
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recycleView2);
        recyclerView3 = (RecyclerView) view.findViewById(R.id.recycleView3);
        recyclerView4 = (RecyclerView) view.findViewById(R.id.recycleView4);
        banner.setAdapter(new BannerImageAdapter<Integer>(bannerList) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                holder.imageView.setImageResource(data);
            }
        });
        banner.setIndicator(new CircleIndicator(getContext()));
        banner.start();
        galleryRecycle.setPages(galleries, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        initRecycleView();
        return view;
    }
    public void initRecycleView(){
        layoutManager1 = new GridLayoutManager(getContext(),2);
        layoutManager2 = new GridLayoutManager(getContext(),2);
        layoutManager3 = new GridLayoutManager(getContext(),2);
        layoutManager4 = new GridLayoutManager(getContext(),2);
        initGoodList();
        cardAdapter1 = new CardAdapter(goodList);
        initGoodList();
        cardAdapter2 = new CardAdapter(goodList);
        initGoodList();
        cardAdapter3 = new CardAdapter(goodList);
        initGoodList();
        cardAdapter4 = new CardAdapter(goodList);
        recyclerView1.setLayoutManager(layoutManager1);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView3.setLayoutManager(layoutManager3);
        recyclerView4.setLayoutManager(layoutManager4);
        recyclerView1.setAdapter(cardAdapter1);
        recyclerView2.setAdapter(cardAdapter2);
        recyclerView3.setAdapter(cardAdapter3);
        recyclerView4.setAdapter(cardAdapter4);
    }

    public void initGoodList(){
        goodList = new ArrayList<>();
        for (int i = 0;i < 6 ;i++){
            Random random = new Random();
            int index = random.nextInt(goods.length);
            goodList.add(goods[index]);
        }
    }
    public void initBannerList(){
        bannerList = new ArrayList<>();
        bannerList.add(R.drawable.xuexiji);
        bannerList.add(R.drawable.cidianbi);
        bannerList.add(R.drawable.word_machine);
        bannerList.add(R.drawable.sijibeikao);
        bannerList.add(R.drawable.bijiben);
        bannerList.add(R.drawable.shuqian);
    }
    public class BannerViewHolder implements MZViewHolder<Integer>{
        ImageView imageView;
        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.gallery_item,null);
            imageView = (ImageView) view.findViewById(R.id.gallery_image);
            return view;
        }
        @Override
        public void onBind(Context context, int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        galleryRecycle.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        galleryRecycle.start();
    }
}
