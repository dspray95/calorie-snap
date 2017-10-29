package uk.ac.yorksj.spray.david.caloriesnap;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.IOException;

import static android.R.attr.data;
import static android.content.ContentValues.TAG;

/**
 * Created by david on 27/10/17.
 */

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private Context context;

    private Toast toastCameraError;

    public CameraView(Context context, Camera camera) {
        super(context);
        this.camera = camera;
        this.context = context;

        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);

        toastCameraError.makeText(context, context.getString(R.string.err_set_preview_display), Toast.LENGTH_LONG);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //Tell the camera to draw the preview in holder
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
            toastCameraError.show();
            }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

        if (surfaceHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // handle size/rotation/format changes
        // start preview with new settings
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();

        } catch (Exception e){
            toastCameraError.show();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
