package galleryimages.galleryimages;

import java.io.Serializable;

public class VideosModel implements Serializable{
    private String folderName;
    private String filePath;

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
