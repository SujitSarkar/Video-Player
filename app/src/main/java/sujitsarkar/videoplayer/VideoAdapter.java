package sujitsarkar.videoplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoHolder> {
    private Context context;
    ArrayList<File> videoArrayList;

    public VideoAdapter(Context context, ArrayList<File> videoArrayList) {
        this.context = context;
        this.videoArrayList = videoArrayList;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item,viewGroup,false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoHolder holder, int position) {
        holder.video_name.setText(MainActivity.fileArrayList.get(position).getName());
        Bitmap bitmapThumbnail = ThumbnailUtils.createVideoThumbnail(videoArrayList.get(position).getPath(), MediaStore.Images.Thumbnails.MINI_KIND);
        holder.video_thumbnail.setImageBitmap(bitmapThumbnail);

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VideoPlayerActivity.class);
                intent.putExtra("position", holder.getAdapterPosition());
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (videoArrayList.size() > 0){
            return videoArrayList.size();
        }
        else {
            return 1;
        }
    }
}

class VideoHolder extends RecyclerView.ViewHolder{

    ImageView video_thumbnail;
    TextView video_name;
    CardView card_view;

    public VideoHolder(@NonNull View itemView) {
        super(itemView);
        video_thumbnail = itemView.findViewById(R.id.video_thumbnail);
        video_name = itemView.findViewById(R.id.video_name);
        card_view = itemView.findViewById(R.id.card_view);
    }
}
