package galleryimages.galleryimages;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AsyncTaskListener{
    private Map<String, List<VideosModel>> videoModelMap = new HashMap<>();
    private List<String> keyList;
    private FolderRVAdapter folderRVAdapter;
    private RecyclerView rvFolder;
    private static final int REQUEST_PERMISSIONS = 100;
    private LoadVideosAsyncTask loadVideosAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvFolder = (RecyclerView)findViewById(R.id.rvFolder);

        if (folderRVAdapter == null){
            keyList = new ArrayList<>();
            folderRVAdapter = new FolderRVAdapter(this, keyList);
            loadVideos();
        }
        rvFolder.setAdapter(folderRVAdapter);
    }

    public void loadVideos() {
        //Log.d(TAG, "Exception request loadVideos");
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            fetchVideos();
        }
    }

    public void fetchVideos() {
        loadVideosAsync = new LoadVideosAsyncTask(this,this, videoModelMap);
        AsyncTaskUtil.executeAsyncTask(loadVideosAsync,true);
    }

    @Override
    public void onTaskCompleted(Object[] params) {
        if(videoModelMap == null || videoModelMap.isEmpty()){
//            tvNoItemFound.setVisibility(View.VISIBLE);
//            tvNoItemFound.setText(FragmentUtil.getString(this, R.string.no_videos_found_device_msg));
//            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onProgressUpdate(Object[] params) {
        boolean isFolder = (Boolean)params[2];
        String folderName = (String) params[1];
        if(isFolder) {
            keyList.add(folderName);
            folderRVAdapter.setItemList(videoModelMap);
        }else {
            FolderRVAdapter.ViewHolder viewHolder = (FolderRVAdapter.ViewHolder) rvFolder.findViewHolderForAdapterPosition(keyList.indexOf(folderName));
            if(viewHolder != null) {
                viewHolder.updateVideoCount(this, videoModelMap.get(folderName));
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        fetchVideos();
                    } else {
                        Toast.makeText(MainActivity.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

}
