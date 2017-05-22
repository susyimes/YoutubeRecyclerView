package com.susyimes.youtuberecyclerview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.susyimes.youtuberecyclerview.rxbus.RxBus;
import com.susyimes.youtuberecyclerview.rxbus.SusAction;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;


public class VedioFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView mYoutubeVideoRecyclerView;
    private YoutubeVideoAdapter mYoutubeVideoAdapter;
    List<Integer> liststate;
    private List<YoutubeVideo> list;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;




    public VedioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VedioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VedioFragment newInstance(String param1, String param2) {
        VedioFragment fragment = new VedioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_vedio, container, false);
        mYoutubeVideoRecyclerView= (RecyclerView) v.findViewById(R.id.youtube_recyclerview);
        initData();

        initLayoutManager();
        initAdapter();
        initRxBus();

        return v;
    }

    private void initRxBus() {
        RxBus.getDefault().toObserverable(SusAction.class).subscribe(new Action1<SusAction>() {
            @Override
            public void call(SusAction susAction) {
                Log.i("statefrag",susAction.getAction2()+"");
                liststate.set(Constant.fullscreenposition,0);
                mYoutubeVideoAdapter.notifyDataSetChanged();
                mYoutubeVideoAdapter.notifyItemChanged(Constant.fullscreenposition);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }

    private void initData() {
        list=new ArrayList<>();
        list=VideoDataSource.getVideoList();
    }

    private void initAdapter() {
        Log.i("state","initadapter");
        liststate=new ArrayList<>();
        for (int i=0;i<list.size();i++){
        liststate.add(i,-1);}
        mYoutubeVideoAdapter = new YoutubeVideoAdapter(getContext(),list,getActivity().getSupportFragmentManager(),liststate);
        mYoutubeVideoRecyclerView.setAdapter(mYoutubeVideoAdapter);
    }

    private void initLayoutManager() {
        Log.i("state","initmanager");
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        mYoutubeVideoRecyclerView.setLayoutManager(gridLayoutManager);

    }

    @Override
    public void onDestroy() {
        Log.i("state","ondestroy");
        if (mYoutubeVideoAdapter != null) {
            mYoutubeVideoAdapter.releaseLoaders();
            mYoutubeVideoAdapter = null;
        }
        super.onDestroy();
    }
}
