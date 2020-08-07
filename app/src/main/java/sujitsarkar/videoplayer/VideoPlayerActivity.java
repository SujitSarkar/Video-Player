package sujitsarkar.videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends AppCompatActivity {

    VideoView my_player;
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        my_player = (VideoView) findViewById(R.id.my_player);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        position = getIntent().getIntExtra("position", -1);
        
        VideoPlayer();

    }

    private void VideoPlayer() {
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(my_player);

        my_player.setMediaController(mediaController);
        my_player.setVideoPath(String.valueOf(MainActivity.fileArrayList.get(position)));
        my_player.requestFocus();

        my_player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                my_player.start();
            }
        });

        my_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                my_player.setVideoPath(String.valueOf(MainActivity.fileArrayList.get(position = position+1)));
                my_player.start();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        my_player.stopPlayback();
    }
}