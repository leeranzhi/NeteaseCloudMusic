package com.demo.neteasecloudmusic.fragment;

import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.demo.neteasecloudmusic.R;
import com.demo.neteasecloudmusic.gson.playList.PlayList;
import com.demo.neteasecloudmusic.gson.user.User;
import com.demo.neteasecloudmusic.util.HttpUtil;
import com.demo.neteasecloudmusic.util.Utility;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class OneFragment extends Fragment {

    private RecyclerView recyclerView;
    private RelativeLayout relativeLayout;
    private PlayListsAdapter playListsAdapter;
    private View view = null;
    private List<PlayList> playLists = new ArrayList<>();
    public User user;
    public static long[] ProvinceCityBirthday;
    int userId;
    private AVLoadingIndicatorView aviLoading;
    private TextView playlistCount;
    private TextView playlistBeSubscribedCount;
    private static final String TAG = "OneFragment";

    public static OneFragment newInstance(int userId) {
        OneFragment fragment = new OneFragment();

        Bundle args = new Bundle();
        args.putInt("userId", userId);

        fragment.setArguments(args);
        Log.d(TAG, "fragment创建视图中！！！！！" + userId);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            userId = bundle.getInt("userId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_one, container, false);
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setVisibility(View.GONE);

        relativeLayout = view.findViewById(R.id.playlistMessage);
        playlistBeSubscribedCount = view.findViewById(R.id.playlistBeSubscribedCount);
        playlistCount = view.findViewById(R.id.playlistCount);
        relativeLayout.setVisibility(View.GONE);

        Log.d(TAG, "此处查看playLists是否为空" + playLists.size());
//        playListsAdapter = new PlayListsAdapter(playLists);
//
//        recyclerView.setAdapter(playListsAdapter);
        Log.d(TAG, "fragment创建视图中！！！！！");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "开始查询用户歌单!!!");

        if (aviLoading == null) {
            aviLoading = view.findViewById(R.id.loading_view);
            aviLoading.show();
        }

        queryPlayList();

    }


    /**
     * 查询内存中的播放列表数据
     */
    private void queryPlayList() {

        if (user != null) {
            playlistBeSubscribedCount.setText("共被收藏" + user.profile.playlistBeSubscribedCount + "次");
            playlistCount.setText("歌单(" + user.profile.playlistCount + ")");
        } else {
            String url = "http://47.94.88.105:3000/user/detail?uid=" + userId;
            queryUserMessageFromServer(url);
        }

        if (playLists.size() > 0) {
            Log.d(TAG, "准备更新数据！！！！");

            playListsAdapter = new PlayListsAdapter(playLists);
            playListsAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(playListsAdapter);

            if (aviLoading != null) {

                aviLoading.hide();
            }
            recyclerView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.VISIBLE);

        } else {
            Log.d(TAG, "准备向服务器请求!!!!");
            String url = "http://music.163.com/api/user/playlist?offset=0&uid=" + userId + "&limit=1000";
            queryPlayListFromServer(url);
        }

    }

    /**
     * 查询用户信息，从服务器上请求
     *
     * @param url
     */
    private void queryUserMessageFromServer(String url) {

        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                user = Utility.handleUserResponse(responseText);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        queryPlayList();
                    }
                });
            }
        });
    }

    /**
     * 查询歌单，从服务器上请求
     */
    private void queryPlayListFromServer(String url) {
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        aviLoading.hide();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                playLists = Utility.handlePlayListResponse(responseText).playLists;

                ProvinceCityBirthday = new long[3];
                ProvinceCityBirthday[0] = playLists.get(0).creator.province;
                ProvinceCityBirthday[1] = playLists.get(0).creator.city;
                ProvinceCityBirthday[2] = playLists.get(0).creator.birthday;
                for (PlayList playList : playLists) {
                    Log.d(TAG, "" + playList.playListName + "\n");
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "准备查看List集合" + playLists.size());
                        queryPlayList();
                    }
                });
            }
        });
    }


    @Override
    public View getView() {
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    /**
     * 内部适配器
     */
    class PlayListsAdapter extends RecyclerView.Adapter<PlayListsAdapter.ViewHolder> {

        private List<PlayList> mPlayLists;
        private Context mContext;

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView playlistImage;
            TextView playlistName, playCount;


            public ViewHolder(View view) {
                super(view);
                playlistImage = (ImageView) view.findViewById(R.id.playlist_image);
                playlistName = (TextView) view.findViewById(R.id.playlist_Name);
                playCount = (TextView) view.findViewById(R.id.play_count);

            }
        }

        public PlayListsAdapter(List<PlayList> playLists) {
            mPlayLists = playLists;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.playlist_item, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            PlayList playList = mPlayLists.get(position);
            Glide.with(mContext).load(playList.ImageUrl).into(holder.playlistImage);
            holder.playlistName.setText(playList.playListName);

            StringBuilder resultCount = new StringBuilder();
            if (playList.playCount > 100000) {
                String s = Long.toString(playList.playCount);
                char[] temp = s.toCharArray();
                int lastIndex = temp.length - 1;

                resultCount.append(playList.trackCount + "首,播放");

                if (temp[lastIndex - 3] == '0') {
                    Log.d(TAG, "此处为0请注意！！！" + s);
                    for (int i = 0; i <= lastIndex - 4; i++) {
                        resultCount.append(temp[i]);
                    }

//                    resultCount = temp[0] + temp[1] + temp[2] + temp[3] + "万次";
                } else {
                    for (int i = 0; i <= lastIndex - 3; i++) {
                        resultCount.append(temp[i]);
                        if (i == lastIndex - 4) {
                            resultCount.append(".");
                        }
                    }
//                    resultCount = temp[0] + temp[1] + temp[2] + temp[3] + "." + temp[4] + "万次";
                }

                resultCount.append("万次");

            } else {
                resultCount = new StringBuilder(playList.trackCount + "首,播放" + playList.playCount + "次");
            }
            holder.playCount.setText(resultCount);
        }

        @Override
        public int getItemCount() {
            return mPlayLists.size();
        }

    }
}
