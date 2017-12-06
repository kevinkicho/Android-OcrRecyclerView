# Android-OcrRecyclerView

<img src="Android-OcrRecyclerView_img.PNG" width="266">
<ol>
  <li><code>Play-Services-Vision</code> is an Android Library for <code>Google Mobile Vision API</code>.</li>
  <li>This application implements <code>Text Recognition API</code>.</li>
  <li>Application is designed in a way that user can select multiple texts to remember using <code>Floating Action Button</code>, and put them up for display using <code>RecyclerView</code>.</li>
</ol>
<h2>Simple Usage</h2>

```java
public class SimpleScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v(TAG, rawResult.getText()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }
}

```
