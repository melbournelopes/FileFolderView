
package galleryimages.galleryimages;

public  interface AsyncTaskListener<T, P> {
	void onTaskCompleted(T... params);
	void onProgressUpdate(P... params);
}
