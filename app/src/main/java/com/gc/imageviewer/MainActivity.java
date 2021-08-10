package com.gc.imageviewer;

import androidx.appcompat.app.AppCompatActivity;
import indi.liyi.viewer.ImageLoader;
import indi.liyi.viewer.ImageViewer;
import indi.liyi.viewer.ViewerStatus;
import indi.liyi.viewer.dragger.DragStatus;
import indi.liyi.viewer.otherui.DefaultIndexUI;
import indi.liyi.viewer.otherui.DefaultProgressUI;

import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageViewer imageViewer = findViewById(R.id.imageViewer);
        imageViewer.overlayStatusBar(true) // ImageViewer 是否会占据 StatusBar 的空间
                .imageData(new ArrayList()) // 数据源
                .imageLoader(new ImageLoader() {
                    @Override
                    public void displayImage(Object src, ImageView imageView, LoadCallback callback) {

                    }
                }) // 设置图片加载方式
                .playEnterAnim(true) // 是否开启进场动画，默认为true
                .playExitAnim(true) // 是否开启退场动画，默认为true
                .showIndex(true) // 是否显示图片索引，默认为true
                .loadIndexUI(new DefaultIndexUI(true)) // 自定义索引样式，内置默认样式
                .draggable(true)
                .loadProgressUI(new DefaultProgressUI()).watch(0);// 自定义图片加载进度样式，内置默认样式
        imageViewer.setOnDragStatusListener(status -> {
            if (status == DragStatus.STATUS_COMPLETE_EXIT) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
        imageViewer.setOnBrowseStatusListener(status -> {
            if (status == ViewerStatus.STATUS_CLOSING) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }
}