## ActivityResultContracts 包含哪些 `Contract` 呢？

```
StartActivityForResult()
StartIntentSenderForResult()
RequestMultiplePermissions()
RequestPermission()
TakePicturePreview()
TakePicture()
TakeVideo()
PickContact()
GetContent()
GetMultipleContents()
OpenDocument()
OpenMultipleDocuments()
OpenDocumentTree()
CreateDocument()
```

比如，我想要调用手机摄像头去拍摄一张图片，并且得到这张图片的 Bitmap 对象，那么就可以使用 `TakePicturePreview` 这个 Contract。

```
private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        // bitmap from camera
}

takePictureLauncher.launch(null)
```

我怎么知道每种 Contract 要求什么输入参数，以 及Lambda 表达式中返回的参数是什么呢？
这个很简单，只需要看一下这个 Contract 的源码即可。比如 `TakePicturePreview` 的源码如下：

```
public static class TakePicturePreview extends ActivityResultContract<Void, Bitmap> {
    ...
}
```



































