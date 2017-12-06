package galleryimages.galleryimages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

public class PhotosActivity extends AppCompatActivity {
    private RecyclerView rvFolder;
    private DetailsRvAdapter adapter;
    private int int_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvFolder = (RecyclerView)findViewById(R.id.rvFolder);
        int_position = getIntent().getIntExtra("value", 0);
        adapter = new DetailsRvAdapter(this, MainActivity.al_images, int_position);
        rvFolder.setAdapter(adapter);
    }
}
