# Android-OcrRecyclerView

<img src="Android-OcrRecyclerView_img.PNG" width="266">
<ol>
  <li><code>Play-Services-Vision</code> is an Android Library for <code>Google Mobile Vision API</code>.</li>
  <li>This application implements <code>Text Recognition API</code>.</li>
  <li>Application is designed in a way that user can select multiple texts to remember using <code>Floating Action Button</code>, and put them up for display using <code>RecyclerView</code>.</li>
</ol>
<h2>Simple Usage</h2>
1.) Add the Camera dependency to your build.gradle file.

```xml
compile 'com.google.android.gms:play-services-vision:11.6.2'
```

2.) Android Manifest

```xml
<uses-permission android:name="android.permission.CAMERA"/>
<meta-data android:name="com.google.android.gms.vision.DEPENDENCIES"/>
```

3.) Receive Detections

```java
@Override
public void receiveDetections(Detector.Detections<TextBlock> detections) {
    final SparseArray<TextBlock> items = detections.getDetectedItems();
    if (items.size() != 0){
        textView.post(new Runnable(){
            @Override
            public void run() {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i<items.size(); ++i){
                    TextBlock item = items.valueAt(i);
                    stringBuilder.append(item.getValue());
                    stringBuilder.append("\n");
                }
                read_text = stringBuilder.toString();
                textView.setText(read_text);
            }
        });
    }
}
```

4.) RecyclerView Activity
<ul>
<li>ListItem that is <code>Serializable</code> is passed to RecyclerViewActivity as "data".</li>
  <li>"data", using <code>MyAdapter</code> class, is put into view.</li>
</ul>

5.) BarcodeScanner Activity
<li>Barcode Data is outputted in TextView.</li>

```java
BarcodeDetector detector = new BarcodeDetector
                .Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                .build();
                
if(!detector.isOperational()){
    textView.setText("Could not set up the detector!");
    return;
}

Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
SparseArray<Barcode> barcodes = detector.detect(frame);

Barcode thisCode = barcodes.valueAt(0);
TextView txtView = (TextView) findViewById(R.id.text_view);
txtView.setText(thisCode.rawValue);
```

6.) FaceDetection Activity
<li>Frame is placed around detected face</li>

```java
FaceDetector faceDetector = new FaceDetector
      .Builder(getApplicationContext())
      .setTrackingEnabled(false)
      .build();
      
if(!faceDetector.isOperational()){
    new AlertDialog.Builder(v.getContext()).setMessage("Could not set up the face detector!").show();
    return;
}

Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
SparseArray<Face> faces = faceDetector.detect(frame);

for(int i=0; i<faces.size(); i++) {
    Face thisFace = faces.valueAt(i);
    float x1 = thisFace.getPosition().x;
    float y1 = thisFace.getPosition().y;
    float x2 = x1 + thisFace.getWidth();
    float y2 = y1 + thisFace.getHeight();
    tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, myRectPaint);
}
```
