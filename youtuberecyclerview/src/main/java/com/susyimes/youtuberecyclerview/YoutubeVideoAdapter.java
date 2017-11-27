package com.susyimes.youtuberecyclerview;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class YoutubeVideoAdapter extends RecyclerView.Adapter<YoutubeVideoAdapter.ViewHolder> {

    private static final int REQ_START_STANDALONE_PLAYER = 1;

    private final ThumbnailListener mThumbnailListener;
    private OnInitializedListener initializedListener;
    private final Map<YouTubeThumbnailView, YouTubeThumbnailLoader> mThumbnailViewToLoaderMap;

    private List<YoutubeVideo> mYoutubeVideos;
    private YouTubePlayer youTubePlayer;
    FragmentManager fragmentManager;
    private Context mContext;
    private int oldid = 0;
    private View v1, v2;
    private List<Integer> liststate;
    private boolean statefull = false;

    public YoutubeVideoAdapter(Context context, List<YoutubeVideo> youtubeVideos, FragmentManager fragmentManager, List<Integer> liststate) {
        mContext = context;
        mYoutubeVideos = youtubeVideos;
        mThumbnailListener = new ThumbnailListener();
        mThumbnailViewToLoaderMap = new HashMap<>();
        this.fragmentManager = fragmentManager;
        this.liststate = liststate;


    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_youtube_video_item, null, false);
        return new YoutubeVideoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.i("state", "work");
      /*  if (liststate.get(position)!=0) {*/
            final YoutubeVideoViewHolder holder1 = (YoutubeVideoViewHolder) holder;

            YoutubeVideo youtubeVideo = mYoutubeVideos.get(position);
            YouTubeThumbnailView videoThumb = holder1.youTubeThumbnailView;
            YouTubeThumbnailLoader loader = mThumbnailViewToLoaderMap.get(videoThumb);
            if (loader == null) {
                videoThumb.setTag(youtubeVideo.getVideoId());
                videoThumb.initialize(mContext.getString(R.string.YOUTUBE_DEVELOPER_KEY), mThumbnailListener);
            } else {
                videoThumb.setImageResource(R.drawable.loading_thumbnail);
                loader.setVideo(youtubeVideo.getVideoId());
            }
            int newid = (int) (System.currentTimeMillis()%1000000);
            ((YoutubeVideoViewHolder) holder).frameLayout.setId(newid);
            holder1.youtubeVideoPlayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder1.youTubeThumbnailView.setVisibility(View.GONE);
                    holder1.youtubeVideoPlayBtn.setVisibility(View.GONE);

                    if (oldid != 0) {
                        fragmentManager.findFragmentById(oldid);
                        fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById(oldid)).commit();
                        v1.setVisibility(View.VISIBLE);
                        v2.setVisibility(View.VISIBLE);

                    }

                    YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

                    fragmentManager.beginTransaction().add(((YoutubeVideoViewHolder) holder).frameLayout.getId(), youTubePlayerFragment).commit();

                    initializedListener = new OnInitializedListener(position);
                    youTubePlayerFragment.initialize(mContext.getString(R.string.YOUTUBE_DEVELOPER_KEY), initializedListener);
                    oldid = ((YoutubeVideoViewHolder) holder).frameLayout.getId();
                    v1 = holder1.youTubeThumbnailView;
                    v2 = holder1.youtubeVideoPlayBtn;
                }
            });
            if (!holder1.frameLayout.isShown() && liststate.get(position) != 0) {
                Log.i("state", "showbtn");
                holder1.youTubeThumbnailView.setVisibility(View.VISIBLE);
                holder1.youtubeVideoPlayBtn.setVisibility(View.VISIBLE);
            }

        if (liststate.get(position) == 1 && !statefull) {
            Log.i("state", "truescreen");
            youTubePlayer.setFullscreen(true);


        } else if (liststate.get(position) == 0 && statefull) {
            Log.i("state", "falsescreen");
            Log.i("state",Constant.fullscreenposition+"");
            youTubePlayer.setFullscreen(false);

        }

    }

    public void releaseLoaders() {
        for (YouTubeThumbnailLoader loader : mThumbnailViewToLoaderMap.values()) {
            loader.release();
        }
    }


    @Override
    public int getItemCount() {
        return mYoutubeVideos.size();
    }

    class YoutubeVideoViewHolder extends ViewHolder implements View.OnTouchListener {

        ItemClickListener clickListener;
        YouTubeThumbnailView youTubeThumbnailView;
        FrameLayout frameLayout;
        TextView youtubeVideoTitle;
        ImageView youtubeVideoPlayBtn;

        YoutubeVideoViewHolder(View view) {
            super(view);
            youTubeThumbnailView = (YouTubeThumbnailView) view.findViewById(R.id.youtube_thumbnail_view);
            frameLayout = (FrameLayout) view.findViewById(R.id.youtube_fragment_container);
            youtubeVideoTitle = (TextView) view.findViewById(R.id.youtube_video_title);
            youtubeVideoPlayBtn = (ImageView) view.findViewById(R.id.video_play_button);
            view.setOnTouchListener(this);

        }

        void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }


        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    scaleView(youtubeVideoPlayBtn, 1.3f);
                    break;
                case MotionEvent.ACTION_UP:
                    scaleView(youtubeVideoPlayBtn, 1.0f);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    scaleView(youtubeVideoPlayBtn, 1.0f);
                    break;
            }
            return false;
        }

        private void scaleView(View view, float scaleValue) {
            view.clearAnimation();
            view.animate()
                    .scaleX(scaleValue)
                    .scaleY(scaleValue)
                    .setDuration(100)
                    .setDuration(300);
        }
    }


    private final class ThumbnailListener implements
            YouTubeThumbnailView.OnInitializedListener,
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onInitializationSuccess(
                YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
            loader.setOnThumbnailLoadedListener(this);
            view.setImageResource(R.drawable.loading_thumbnail);
            String videoId = (String) view.getTag();
            loader.setVideo(videoId);
            mThumbnailViewToLoaderMap.put(view, loader);
        }

        @Override
        public void onInitializationFailure(
                YouTubeThumbnailView view, YouTubeInitializationResult loader) {
            view.setImageResource(R.drawable.no_thumbnail);
        }

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView view, String videoId) {
        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView view, YouTubeThumbnailLoader.ErrorReason errorReason) {
            view.setImageResource(R.drawable.no_thumbnail);
        }
    }

    private class OnInitializedListener implements YouTubePlayer.OnInitializedListener {
        private YoutubeVideo youtubeVideos;
        private int position;

        public OnInitializedListener(int position) {
            youtubeVideos = mYoutubeVideos.get(position);
            this.position = position;
        }

        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer Player, boolean b) {
            // youTubePlayer.cueVideo(youtubeVideos.getVideoId());
            if (!b) {

            youTubePlayer = Player;

            //youTubePlayer.setFullscreenControlFlags(0);
            youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                @Override
                public void onFullscreen(boolean b) {
                    statefull = b;
                    if (b) {
                        Constant.fullscreen = 1;
                        Constant.fullscreenposition = position;
                        youTubePlayer=Player;
                    }
                }
            });



                //Player.cueVideo(youtubeVideos.getVideoId());
                youTubePlayer.loadVideo(youtubeVideos.getVideoId());
            }

        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

        }
    }

    private interface ItemClickListener {
        void onClick(View view, int position);
    }
}
