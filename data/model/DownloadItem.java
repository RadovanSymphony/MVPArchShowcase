package com.mg.kode.kodebrowser.data.model;

import android.net.Uri;


public class DownloadItem {
	
    public enum Type { VIDEO, AUDIO, OTHER }

    public String name;
    public Type type;
    public Uri originalUri;
    public Uri localUri;
    public long sizeBytes;
    public int duration;
    public boolean completed;

    public DownloadItem(String name, Uri originalUri) {
        this.name = name;
        this.originalUri = originalUri;
    }
}