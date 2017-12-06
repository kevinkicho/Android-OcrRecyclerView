# Android-OcrRecyclerView

<img src="Android-OcrRecyclerView_img.PNG" width="266">
<ol>
  <li><code>Play-Services-Vision</code> is an Android Library for <code>Google Mobile Vision API</code>.</li>
  <li>This application implements <code>Text Recognition API</code>.</li>
  <li>Application is designed in a way that user can select multiple texts to remember using <code>Floating Action Button</code>, and put them up for display using <code>RecyclerView</code>.</li>
</ol>
<h2>Simple Usage</h2>
```java
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraView = (SurfaceView)findViewById(R.id.surface_view);
        textView = (TextView)findViewById(R.id.text_view);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        btn = (Button)findViewById(R.id.button_recycler_view);

TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplication()).build();
        if(!textRecognizer.isOperational()){
            Log.w("MainActivity","Detector dependencies are not yet available.");
        }
        else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280,1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try{
                        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionId);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

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
            });
        }
```
