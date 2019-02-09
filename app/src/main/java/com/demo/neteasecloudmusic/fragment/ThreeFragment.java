package com.demo.neteasecloudmusic.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.demo.neteasecloudmusic.R;
import com.demo.neteasecloudmusic.gson.event.Event;
import com.demo.neteasecloudmusic.gson.event.Events;
import com.demo.neteasecloudmusic.util.HttpUtil;
import com.demo.neteasecloudmusic.util.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ThreeFragment extends Fragment {

    private final String baseUrl = "http://47.94.88.105:3000/user/event?uid=";
    private static final String ARG_PARAM1 = "userId";
    private static final String ARG_PARAM2 = "param2";
    private AVLoadingIndicatorView avi;
    private int userId;
    private RecyclerView recyclerView;
    private EventListAdapter eventListAdapter;
    private List<Events> eventsList = new ArrayList<>();
    private static final String TAG = "ThreeFragment";
    private LinearLayout zeroEvent;
    private TextView errorMessage;

    public static ThreeFragment newInstance(int userId, String param2) {
        ThreeFragment fragment = new ThreeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, userId);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_three, container, false);
        avi = view.findViewById(R.id.loading_view3);

        zeroEvent = view.findViewById(R.id.ZeroEvent);
        errorMessage = view.findViewById(R.id.Error_Message);
        zeroEvent.setVisibility(View.GONE);

        recyclerView = view.findViewById(R.id.userEvent_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setVisibility(View.GONE);

        avi.show();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        queryUserEvent();

    }

    private void queryUserEvent() {
        if (eventsList.size() > 0) {

            eventListAdapter = new EventListAdapter(eventsList);
            eventListAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(eventListAdapter);

            avi.hide();
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            String url = baseUrl + userId;
            queryUserEventFromServer(url);
        }


    }

    private void queryUserEventFromServer(String url) {

        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "个人动态获取失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                eventsList = Utility.handleEventList(responseText).eventsList;

                for (Events events : eventsList) {
                    Log.d(TAG, "" + events.actInfo.commentThread.resourceTitle);
                }

                if (eventsList.size() > 0) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            queryUserEvent();
                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            showZeroEventView();
                        }
                    });
                }

            }
        });
    }


    private void showZeroEventView() {

        zeroEvent.setVisibility(View.VISIBLE);
        errorMessage.setText("这个人很懒，暂时还没有动态哦");
        avi.hide();
    }


    /**
     * 内部适配器
     */
    class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {


        private Context mContext;
        private List<Events> mEventsList;

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.eventlist_item, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }


        public EventListAdapter(List<Events> eventsList) {
            mEventsList = eventsList;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Events events = eventsList.get(position);
            Glide.with(mContext).load(events.userMessage.avatarUrl).into(holder.userImage);
            holder.userName.setText(events.userMessage.nickname);
            holder.eventContent.setText(events.actInfo.commentThread.resourceTitle);
            holder.eventTime.setText(Utility.handleUnixTimeStamp(events.eventTime));
            StringBuilder result = new StringBuilder();
            if (events.actInfo.commentThread.resourceInfo != null) {
                if (events.actInfo.commentThread.resourceInfo.eventType == 22) {
                    result.append("转发:");
                } else {
                    result.append("分享单曲:");
                }
            } else {
                result.append("转发:");
            }

            holder.eventType.setText(result);

            holder.likeCount.setText("点赞数" + events.actInfo.likedCount);
            Log.d(TAG, "" + events.actInfo.likedCount + "\t" + events.actInfo.shareCount + "\t" + events.actInfo.commentThread);
            holder.shareCount.setText("转发数" + events.actInfo.shareCount);
            holder.commentCount.setText("评论数" + events.actInfo.commentCount);
        }

        @Override
        public int getItemCount() {
            return eventsList.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView userImage;
            TextView userName, eventType, eventTime, eventContent, likeCount, commentCount, shareCount;


            public ViewHolder(View view) {
                super(view);
                userImage = view.findViewById(R.id.user_image);
                userName = view.findViewById(R.id.user_name);
                eventType = view.findViewById(R.id.event_type);
                eventTime = view.findViewById(R.id.event_time);
                eventContent = view.findViewById(R.id.event_content);

                likeCount = view.findViewById(R.id.likeCount);
                shareCount = view.findViewById(R.id.shareCount);
                commentCount = view.findViewById(R.id.commentCount);
            }
        }
    }


}
