package galleryimages.galleryimages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class PhotosActivity extends AppCompatActivity {
    private RecyclerView rvFolder;
    private DetailsRvAdapter adapter;
    private List<VideosModel> videosModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvFolder = (RecyclerView)findViewById(R.id.rvFolder);
        videosModelList = (List<VideosModel>) getIntent().getExtras().getSerializable("videoList");
        adapter = new DetailsRvAdapter(this, videosModelList);
        rvFolder.setAdapter(adapter);
    }
}
