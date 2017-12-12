package galleryimages.galleryimages;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoadVideosAsyncTask extends AsyncTask<Void, LoadVideosAsyncTask.ProgressArgument, Boolean> {

    private static final String TAG = LoadVideosAsyncTask.class.getSimpleName();

    private final Context context;
    private AsyncTaskListener asyncTaskListener;
    private Map<String, List<VideosModel>> videoModelMap;

    public LoadVideosAsyncTask(Context context, AsyncTaskListener asyncTaskListener, Map<String, List<VideosModel>> videoModelMap) {
        this.context = context;
        this.asyncTaskListener = asyncTaskListener;
        this.videoModelMap = videoModelMap;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        //Log.d(TAG, "Exception request doInBackground");
        // Get relevant columns for use later.
        String[] projection = {
                MediaStore.Video.VideoColumns._ID,
                MediaStore.Video.VideoColumns.DATA,
                MediaStore.Video.VideoColumns.TITLE,
                MediaStore.Video.VideoColumns.DURATION,
                MediaStore.Video.VideoColumns.DATE_ADDED,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME
        };

        // Return only video metadata.
        Uri queryUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor;

        int column_index_data, column_index_folder_name;

        String absolutePathOfImage = null;

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = context.getContentResolver().query(queryUri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            Log.e("Column", absolutePathOfImage);
            Log.e("Folder", cursor.getString(column_index_folder_name));
            String folderName = cursor.getString(column_index_folder_name);
            List<VideosModel> videoModelList = videoModelMap.get(folderName);
            if (videoModelList == null) {
                videoModelList = new ArrayList<>();
                videoModelMap.put(folderName, videoModelList);
                publishProgress(new ProgressArgument(videoModelMap, folderName, true));
            }
            VideosModel videoModel = new VideosModel();
            videoModel.setFolderName(folderName);
            videoModel.setFilePath(absolutePathOfImage);

            videoModelList.add(videoModel);
            publishProgress(new ProgressArgument(videoModelMap, folderName, false));
            //Thread.sleep(200) is used so that UI thread is updated
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(ProgressArgument... values) {
        //Log.d(TAG, "Exception request onProgressUpdate");
        super.onProgressUpdate(values);
        if (asyncTaskListener != null) {
            asyncTaskListener.onProgressUpdate(values[0].getVideoModel(), values[0].getFolderName(), values[0].isFolder());
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        //Log.d(TAG, "Exception request onPostExecute");
        super.onPostExecute(aBoolean);
        if (asyncTaskListener != null) {
            asyncTaskListener.onTaskCompleted();
        }
    }

    protected static class ProgressArgument {
        private VideosModel videoModel;
        private Map<String, List<VideosModel>> videoModelMap;
        private String folderName;
        private boolean isFolder;

        public ProgressArgument(Map<String, List<VideosModel>> videoModelMap, String folderName, boolean isFolder) {
            this.videoModelMap = videoModelMap;
            this.folderName = folderName;
            this.isFolder = isFolder;
        }

        public VideosModel getVideoModel() {
            return videoModel;
        }

        public String getFolderName() {
            return folderName;
        }

        public boolean isFolder() {
            return isFolder;
        }
    }
}
