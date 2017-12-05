package galleryimages.galleryimages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

public class PhotosActivity extends AppCompatActivity {
    int int_position;
    private RecyclerView rvFolder;
    private ListView lstView;
    DetailsRvAdapter adapter;
//    GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvFolder = (RecyclerView)findViewById(R.id.rvFolder);
        int_position = getIntent().getIntExtra("value", 0);
        adapter = new DetailsRvAdapter(this, MainActivity.al_images, int_position);
        rvFolder.setAdapter(adapter);

//        lstView = (ListView) findViewById(R.id.lstView);
//        lstView.setVisibility(View.VISIBLE);
//        gridViewAdapter = new GridViewAdapter(this, MainActivity.al_images, int_position);
//        lstView.setAdapter(gridViewAdapter);
    }
}
