<h1>ImageViewer</h1>

!!!!!! 由于https://dl.bintray.com/ 网站被禁用。ImageViewer原始作者没有做相关项目迁移，所以我这边将该项目迁移值jitPack。
原项目地址：https://github.com/albert-lii/ImageViewer

<h2>关于</h2>

图片浏览器，支持图片手势缩放、拖拽等操作，`自定义View`的模式显示，自定义图片加载方式，可自定义索引UI、ProgressView，更加灵活，易于扩展，同时也适用于RecyclerView、ListView的横向和纵向列表模式，最低支持版本为Android 3.0及以上...  

<h2>功能</h2>

- 图片的基本缩放、滑动
- 微信朋友圈图片放大预览
- 微信朋友圈图片拖拽效果
- 今日头条图片拖拽效果
- 自定义图片加加载
- 图片加载进度条
- 可自定义图片索引与图片加载进度UI

<h2>传送门</h2>

- [自定义属性](#1)
- [事件监听器](#2)
- [自定义UI](#3)
- [添加依赖](#4)
- [使用方法](#5)
- [超巨图加载解决方案](#6)

<h2 id="4">添加依赖</h2> 

- Gradle
```Java
   Step 1:

   allprojects {
       repositories {
           ...
           maven { url "https://jitpack.io" }
       }
    }
    
    
   Step 2:
   dependencies {
	        implementation 'com.github.XiaoGe-1996:ImageViewer:1.0.0'
	}
```

<h2>项目演示</h2>

![简单示例][demo-simple]  ![朋友圈][demo-friendcircle]

![横向list][demo-landscape]  ![纵向list][demo-portrait]

<h2 id="1">自定义属性</h2>  

| 属性名 | 描述 |    
| :---- | :---- |    
| ivr_showIndex | 是否显示图片位置 |  
| ivr_playEnterAnim | 是否开启进场动画 | 
| ivr_playExitAnim | 是否开启退场动画 |   
| ivr_duration | 进场与退场动画的执行时间 |    
| ivr_draggable | 是否允许图片拖拽 |    
| ivr_dragMode | 拖拽模式（simple：今日头条效果 | agile：微信朋友圈效果） |  

<h2 id="2">事件监听器</h2>    

| 方法名 | 描述 |  
| :---- | :---- |    
| setOnItemClickListener(OnItemClickListener listener) | item 的单击事件 |
| setOnItemLongListener(OnItemLongPressListener listener) | item 的长按事件 |
| setOnItemChangedListener(OnItemChangedListener listener) | item 的切换事件 |
| setOnDragStatusListener(OnDragStatusListener listener) | 监听图片拖拽状态事件 |
| setOnBrowseStatusListener(OnBrowseStatusListener listener) | 监听图片浏览器状态事件 |
  
<h2 id="3">自定义UI</h2>  

- 自定义索引UI

框架中内置默认索引视图`DefaultIndexUI`，如要替换索引样式，可继承抽象类`IndexUI`,并在使用`watch(...)`方法前,调用下列方法加载自定义的indexUI

```java
loadIndexUI(@NonNull IndexUI indexUI)
```
- 自定义加载进度UI

框架中内置默认加载视图`DefaultProgressUI`，如要替换加载样式，可继承抽象类`ProgressUI`,并在使用`watch(...)`方法前,调用下列方法加载自定义的progressUI

```java
loadProgressUI(@NonNull ProgressUI progressUI)
```

### XML 中添加 ImageViewer
```
  <indi.liyi.viewer.ImageViewer
        android:id="@+id/imageViewer"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
```

### 代码中设置 ImageViewer
一共提供两种配置ImageViewer的方法：

- 方法一：
```java
imageViewer.overlayStatusBar(false) // ImageViewer 是否会占据 StatusBar 的空间
           .imageData(list) // 图片数据
           .bindViewGroup(gridview) // 目标 viewGroup，例如类似朋友圈中的九宫格控件
           .imageLoader(new PhotoLoader()) // 设置图片加载方式
           .playEnterAnim(true) // 是否开启进场动画，默认为true
           .playExitAnim(true) // 是否开启退场动画，默认为true
           .duration(true) // 设置进退场动画时间，默认300
           .showIndex(true) // 是否显示图片索引，默认为true
           .loadIndexUI(indexUI) // 自定义索引样式，内置默认样式
           .loadProgressUI(progressUI) // 自定义图片加载进度样式，内置默认样式
           .watch(position); // 开启浏览
```
此方法是用imageData()配合bindViewGroup()方法，来在内部构建自动构建item的信息模型ViewData，适用于目标ViewGroup类似于朋友圈九宫格控件这类场景，目标ViewGroup如果是ListView这种可重复利用item的控件，则不可用。

- 方法二：
```Java
   imageViewer.overlayStatusBar(false) // ImageViewer 是否会占据 StatusBar 的空间
              .viewData(vdList) // 数据源
              .imageLoader(new PhotoLoader()) // 设置图片加载方式
              .playEnterAnim(true) // 是否开启进场动画，默认为true
              .playExitAnim(true) // 是否开启退场动画，默认为true
              .duration(true) // 设置进退场动画时间，默认300
              .showIndex(true) // 是否显示图片索引，默认为true
              .loadIndexUI(indexUI) // 自定义索引样式，内置默认样式
              .loadProgressUI(progressUI) // 自定义图片加载进度样式，内置默认样式
              .watch(position);
```
此方法直接使用viewData()设置框架所需要的数据源

### Tip：关于点击系统返回键取消图片浏览
如果需要实现点击返回系统返回键关闭浏览,请在Activity中加入以下代码
```java
  /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean b = imageViewer.onKeyDown(keyCode, event);
        if (b) {
            return b;
        }
        return super.onKeyDown(keyCode, event);
    }
```

<h2 id="6">超巨图解决方案</h2>

1. 因为可以自定义图片加载方法，在加载图片前可以先压缩图片
2. 项目内部目前使用的图片缩放控件为PhotoView，可以将PhotoView用以下控件代替：
   - 使用 [SubsamplingScaleImageView](SubsamplingScaleImageView) 代替 PhotoView（推荐）
   - 或者使用 [BigImageView](BigImageView) 代替 PhotoView

-------------------ENd----------------------
