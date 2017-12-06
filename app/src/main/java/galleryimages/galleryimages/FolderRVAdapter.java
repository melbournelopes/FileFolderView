package galleryimages.galleryimages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BORN PC on 12/5/2017.
 */

public class FolderRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = FolderRVAdapter.class.getSimpleName();
    private Map<String, List<VideosModel>> videoModelMap = new HashMap<>();
    private List<String> keyList;
    Activity context;

    public FolderRVAdapter(Activity context, Map<String, List<VideosModel>> videoModelMap) {
        this.videoModelMap = videoModelMap;
        this.context = context;
        keyList = new ArrayList<String>(videoModelMap.keySet());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        viewHolder = new ViewHolder(layoutInflater.inflate(R.layout.item_row, parent, false));

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        String folderName = keyList.get(position);
        viewHolder.setData(context, videoModelMap.get(folderName), folderName, viewHolder, position);
    }

    @Override
    public int getItemCount() {
        return keyList.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlMain;
        private TextView lblTitle, lblSize;
        private ImageView imgPhoto;

        public ViewHolder(View itemView) {
            super(itemView);
            rlMain = (RelativeLayout) itemView.findViewById(R.id.rlMain);
            lblTitle = (TextView) itemView.findViewById(R.id.lblTitle);
            lblSize = (TextView) itemView.findViewById(R.id.lblSize);
            imgPhoto = (ImageView) itemView.findViewById(R.id.imgPhoto);
        }

        private void setData(final Context context, final List<VideosModel> videoModelList, final String folderName, ViewHolder viewHolder, final int position) {
            lblTitle.setText(folderName);
            lblSize.setText(""+videoModelList.size()+" Items");
            rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context.getApplicationContext(), PhotosActivity.class);
                    Bundle bd = new Bundle();
                    bd.putSerializable("videoList", (Serializable) videoModelList);
                    intent.putExtras(bd);
                    context.startActivity(intent);
                }
            });

            Glide.with(context).load("file://" + videoModelList.get(0).getFilePath())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(viewHolder.imgPhoto);
        }
    }
}
