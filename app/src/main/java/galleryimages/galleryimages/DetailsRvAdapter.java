package galleryimages.galleryimages;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class DetailsRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = FolderRVAdapter.class.getSimpleName();
    private List<VideosModel> videoModelList = new ArrayList<>();
    private Activity context;

    public DetailsRvAdapter(Activity context, List<VideosModel> videoModelList) {
        this.videoModelList = videoModelList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        viewHolder = new DetailsRvAdapter.ViewHolder(layoutInflater.inflate(R.layout.item_row, parent, false));

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DetailsRvAdapter.ViewHolder viewHolder = (DetailsRvAdapter.ViewHolder) holder;
        viewHolder.setData(context, viewHolder, videoModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return videoModelList.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlMain;
        private TextView lblTitle, lblSize;
        private ImageView imgPhoto, imgRightArrow;

        public ViewHolder(View itemView) {
            super(itemView);
            rlMain = (RelativeLayout) itemView.findViewById(R.id.rlMain);
            lblTitle = (TextView) itemView.findViewById(R.id.lblTitle);
            lblSize = (TextView) itemView.findViewById(R.id.lblSize);
            imgPhoto = (ImageView) itemView.findViewById(R.id.imgPhoto);
            imgRightArrow = (ImageView) itemView.findViewById(R.id.imgRightArrow);
        }

        private void setData(final Context context, DetailsRvAdapter.ViewHolder viewHolder, final VideosModel videoModel) {
            lblTitle.setText(""+videoModel.getFilePath().substring(videoModel.getFilePath().lastIndexOf("/")+1));
            lblSize.setVisibility(View.GONE);
            imgRightArrow.setVisibility(View.GONE);

            rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            Glide.with(context).load("file://" + videoModel.getFilePath())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(viewHolder.imgPhoto);
        }
    }
}
