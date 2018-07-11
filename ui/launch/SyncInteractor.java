package com.mg.kode.kodebrowser.ui.launch;


import com.mg.kode.kodebrowser.utils.JobExecutor;

public class SyncInteractor {

    public interface DataLoadedCallback {
        void onLoadComplete(String data);

        void onLoadFailed(String errorMessage);
    }

    public void getDummyData(DataLoadedCallback callback) {
        JobExecutor.execute(() -> {
            try {
                String data = loadDummyData();
                callback.onLoadComplete(data);
            } catch (InterruptedException e) {
                e.printStackTrace();
                callback.onLoadFailed("Failed to load data");
            }

        });
    }

    private String loadDummyData() throws InterruptedException {
        Thread.sleep(3000);
        return "Dummy data";
    }
}
