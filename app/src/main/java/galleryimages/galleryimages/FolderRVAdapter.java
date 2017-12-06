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

/**
 * Created by BORN PC on 12/5/2017.
 */

public class FolderRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = FolderRVAdapter.class.getSimpleName();
    ArrayList<VideosModel> al_menu = new ArrayList<>();
    Activity context;

    public FolderRVAdapter(Activity context, ArrayList<VideosModel> al_menu) {
        this.al_menu = al_menu;
        this.context = context;
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
        VideosModel videosModel_ = al_menu.get(position);
        viewHolder.setData(context, videosModel_, viewHolder, position);
    }

    @Override
    public int getItemCount() {
        return al_menu.size();
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

        private void setData(final Context context, final VideosModel al_menu, ViewHolder viewHolder, final int position) {
            tv_foldern.setText(al_menu.getStr_folder());
            tv_foldersize.setText(""+al_menu.getAl_imagepath().size()+" Items");
            rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context.getApplicationContext(), PhotosActivity.class);
                    intent.putExtra("value", position);
                    context.startActivity(intent);
                }
            });

            Glide.with(context).load("file://" + al_menu.getAl_imagepath().get(0))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(viewHolder.iv_image);
        }
    }
}
