package galleryimages.galleryimages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class DetailsRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = FolderRVAdapter.class.getSimpleName();
    ArrayList<VideosModel> al_menu = new ArrayList<>();
    int int_position;
    Activity context;

    public DetailsRvAdapter(Activity context, ArrayList<VideosModel> al_menu, int int_position) {
        this.al_menu = al_menu;
        this.int_position = int_position;
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
//        VideosModel videosModel_ = al_menu.get(position);
        viewHolder.setData(context, al_menu.get(int_position).getAl_imagepath().get(position), viewHolder);
    }

    @Override
    public int getItemCount() {
        return al_menu.get(int_position).getAl_imagepath().size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rlMain;
        private TextView tv_foldern, tv_foldersize;
        private ImageView iv_image;

        public ViewHolder(View itemView) {
            super(itemView);
            rlMain = (RelativeLayout) itemView.findViewById(R.id.rlMain);
            tv_foldern = (TextView) itemView.findViewById(R.id.tv_folder);
            tv_foldersize = (TextView) itemView.findViewById(R.id.tv_folder2);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
        }

        private void setData(final Context context, final String path, DetailsRvAdapter.ViewHolder viewHolder) {
            tv_foldern.setVisibility(View.GONE);
            tv_foldersize.setVisibility(View.GONE);

            rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            Glide.with(context).load("file://" + path)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(viewHolder.iv_image);
        }
    }
}
