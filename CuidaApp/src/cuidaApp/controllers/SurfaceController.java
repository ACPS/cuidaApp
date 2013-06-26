package cuidaApp.controllers;

import java.io.IOException;
import java.lang.reflect.Method;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.bitmapfun.util.ImageResizer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Build;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nerdcore.logs.Trace;
import com.nerdcore.utils.ImageUitls;

import cuidaApp.common.CommonGlobals;

@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class SurfaceController extends SurfaceView implements
		SurfaceHolder.Callback {

	private SurfaceHolder mHolder;
	private Camera mCamera;
	private final String TAG = "SurfaceController";
	protected static final int ON_IMAGE_ERROR = 1;
	protected static final int ON_IMAGE_OK = 2;
	

	private Handler handler = new Handler();

	private final Runnable loadCamera = new Runnable() {
		public void run() {
			loadCamera();
		}
	};

	public SurfaceController(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		handler.post(loadCamera);
	}

	public void stop() {
		if (ManagerController.getInstance().isPreviewing()) {
			mCamera.stopPreview();
			ManagerController.getInstance().setPreviewing(false);
			// mCamera.release();
		}
	}

	@SuppressWarnings("deprecation")
	private void loadCamera() {
		try {
			mHolder = getHolder();
			mHolder.addCallback(this);
			// deprecated setting, but required on Android versions prior to 3.0
			mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		} catch (Exception e) {
		}
	}

	public void releaseCamara() {

		if (mCamera != null) {
			ManagerController.getInstance().setPreviewing(false);
			mCamera.stopPreview();
			surfaceDestroyed(mHolder);
		}

		Trace.i(TAG, "releaseCamara isPreviewing: "
				+ ManagerController.getInstance().isPreviewing());

	}

	public void resume() {

		Trace.i(TAG, "resume isPreviewing: "
				+ ManagerController.getInstance().isPreviewing());

		if (mCamera != null) {

			if (!ManagerController.getInstance().isPreviewing()) {
				try {
					mCamera.reconnect();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				mCamera.startPreview();
				ManagerController.getInstance().setPreviewing(true);
			}

		}

	}

	public void open() {
		mCamera = Camera.open();
		mCamera.startPreview();
		ManagerController.getInstance().setPreviewing(true);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mCamera = Camera.open();
			mCamera.setPreviewDisplay(holder);
			//onAutoFocus(true, mCamera);
			
		} catch (IOException e) {
			if (mCamera != null)
				mCamera.release();
			mCamera = null;
		}
	}

	/*
	 * public void surfaceCreated(SurfaceHolder holder) { // The Surface has
	 * been created, now tell the camera where to draw the // preview.
	 * 
	 * Trace.i(TAG, "surfaceCreatded");
	 * 
	 * try {
	 * 
	 * if (mCamera == null) mCamera = Camera.open();
	 * 
	 * if (!ManagerController.getInstance().isPreviewing()) {
	 * mCamera.setPreviewDisplay(holder); mCamera.startPreview();
	 * ManagerController.getInstance().setPreviewing(true); }
	 * 
	 * } catch (IOException e) { Trace.i(TAG, "Error setting camera preview: " +
	 * e.getMessage());
	 * 
	 * }
	 * 
	 * }
	 */

	public void surfaceDestroyed(SurfaceHolder holder) {

		Trace.i(TAG, "surfaceDestroyed is call");

		if (mCamera != null) {
			mCamera.release();
			ManagerController.getInstance().setPreviewing(false);
		}
	}

	protected void setDisplayOrientation(Camera camera, int angle) {

		Method downPolymorphic;

		try {
			downPolymorphic = camera.getClass().getMethod(
					"setDisplayOrientation", new Class[] { int.class });
			if (downPolymorphic != null)
				downPolymorphic.invoke(camera, new Object[] { angle });
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@SuppressLint("NewApi")
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// If your preview can change or rotate, take care of those events here.
		// Make sure to stop the preview before resizing or reformatting it.

		if (mHolder.getSurface() == null) {
			// preview surface does not exist
			return;
		}

		// stop preview before making changes
		try {
			mCamera.stopPreview();
		} catch (Exception e) {
			// ignore: tried to stop a non-existent preview
		}

		// set preview size and make any resize, rotate or
		// reformatting changes here
		Camera.Parameters p = null;

		if (mCamera != null) {
			p = mCamera.getParameters();
		}

		if (mCamera != null) {
			// camera.setDisplayOrientation(90);

			int currentapiVersion = android.os.Build.VERSION.SDK_INT;

			if (currentapiVersion >= 8) {
				setDisplayOrientation(mCamera, 90);
			} else {
				p.set("rotation", 90);
			}
		}

		// start preview with new settings
		try {
			if (mCamera != null) {
				mCamera.setPreviewDisplay(mHolder);
				mCamera.startPreview();
				ManagerController.getInstance().setPreviewing(true);
			} else {
				ManagerController.getInstance().setPreviewing(false);
			}
		} catch (Exception e) {
			ManagerController.getInstance().setPreviewing(false);
			Trace.d(TAG, "Error starting camera preview: " + e.getMessage());
		}
	}

	public void takeAPicAction(final FrameLayout camera_surface,
			final ImageView image_preview, final LinearLayout container,
			final float curX, final float curY, final float curZ) {

		ShutterCallback shutterCallBack = new ShutterCallback() {
			@Override
			public void onShutter() {
				Trace.i(TAG, "onShutter");
			}
		};

		PictureCallback pictureCallBack = new PictureCallback() {
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				Trace.i(TAG, "Photo was taken RAW");
			}
		};

		PictureCallback pictureCallBackJPG = new PictureCallback() {
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {

				Bitmap capturedBitmap = BitmapFactory.decodeByteArray(data, 0,
						data.length);

				String imagePath = ImageUitls.saveOnDisk(capturedBitmap);
				Bitmap imageBitmap = null;

				if (imagePath != null) {

					imageBitmap = ImageResizer.decodeSampledBitmapFromFile(
							imagePath, 640, 480, null);

					// Get image orientation
					Matrix matrix = new Matrix();
					matrix.postRotate(90);

					if ((int) curY == 0) {
						if ((int) curX >= 0) {
							matrix.postRotate(-90);
						} else {
							matrix.postRotate(90);
						}
					} else if ((int) curY < 0) {
						if ((int) curX >= 0) {
							matrix.postRotate(180);
						} else {
							matrix.postRotate(-180);
						}
					}
					
					Camera.Parameters parameters = camera.getParameters();
					float[] focusDistances = new float[3];
					parameters.getFocusDistances(focusDistances);
					float near = focusDistances[Camera.Parameters.FOCUS_DISTANCE_NEAR_INDEX];
					float far = focusDistances[Camera.Parameters.FOCUS_DISTANCE_FAR_INDEX];
					float optimal =	focusDistances[Camera.Parameters.FOCUS_DISTANCE_OPTIMAL_INDEX];
					
					Trace.d("FOCUS INFO", "near: "+near+" far: "+far+" optimal: "+optimal);

					// Generate the small bitmap => 800 x 600
					Bitmap scaled_with_orientation = Bitmap.createBitmap(
							imageBitmap, 0, 0, imageBitmap.getWidth(),
							imageBitmap.getHeight(), matrix, true);
					imageBitmap.recycle();
					 
					image_preview.setImageBitmap(scaled_with_orientation);
					ManagerController.getInstance().setImageTemp(scaled_with_orientation);

					// Detener la cámara
					stop();

					// Ocultar el cargando
					CommonGlobals.hideProgess();

					// Mostrar los elementos
					container.setVisibility(View.VISIBLE);
					image_preview.setVisibility(View.VISIBLE);

					// Detener GPS
					//ListenerGPS.getInstance().stopListener();

					// Obtener coordenadas
//					ManagerController.getInstance().setLatitude(
//							ListenerGPS.getInstance().latitud + "");
//					ManagerController.getInstance().setLongitude(
//							ListenerGPS.getInstance().longitud + "");

					camera_surface.setVisibility(View.GONE);
				}

				// Trace.i(TAG, "Photo was taken JPG");
				// if (data != null) {
				// try {
				//
				// // Get image orientation
				// Matrix matrix = new Matrix();
				// matrix.postRotate(90);
				//
				// if ((int) curY == 0) {
				// if ((int) curX >= 0) {
				// matrix.postRotate(-90);
				// } else {
				// matrix.postRotate(90);
				// }
				// } else if ((int) curY < 0) {
				// if ((int) curX >= 0) {
				// matrix.postRotate(180);
				// } else {
				// matrix.postRotate(-180);
				// }
				// }
				//
				// // Generate the small bitmap => 800 x 600
				// Bitmap scaled_bmp = thumbImage(capturedBitmap, 800, 600);
				// Bitmap scaled_with_orientation = Bitmap.createBitmap(
				// scaled_bmp, 0, 0, scaled_bmp.getWidth(),
				// scaled_bmp.getHeight(), matrix, true);
				//
				// // Save the image and call preview
				//
				// // Trace.i(TAG, scaled_with_orientation+"");
				//
				// ListenerGPS.getInstance().stopListener();
				//
				// ManagerController.getInstance().setLatitude(
				// ListenerGPS.getInstance().latitud + "");
				// ManagerController.getInstance().setLongitude(
				// ListenerGPS.getInstance().longitud + "");
				// // CategoryController.getInstance().activeCategories();
				//
				// camera_surface.setVisibility(View.GONE);
				//
				// Bitmap image = Steganography.encondeMessage(
				// scaled_with_orientation, HashSteganography);
				//
				// ManagerController.getInstance().setImageTemp(image);
				//
				// // it.setVisibility(View.INVISIBLE);
				// Handler handler = new Handler();
				//
				// // ntainer.setVisibility(View.VISIBLE);
				// // image_preview.setVisibility(View.VISIBLE);
				// image_preview.setImageBitmap(image);
				// handler.postDelayed(
				// showImage(container, image_preview), 1000);
				//

				stop();
				//
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
				// }

				CommonGlobals.hideProgess();
				container.setVisibility(View.VISIBLE);
				// image.setVisibility(View.VISIBLE);
			}
		};

		mCamera.takePicture(shutterCallBack, pictureCallBack,
				pictureCallBackJPG);
	}
	
	// private Bitmap thumbImage(Bitmap bmp, int newWidth, int newHeight) {
	// int origWidth = bmp.getWidth();
	// int origHeight = bmp.getHeight();
	// if (newWidth >= origWidth) {
	// newWidth = origWidth;
	// }
	// int tNH = (int) Math.round(((float) origHeight / (float) origWidth)
	// * (float) newWidth);
	// Bitmap scaled = Bitmap.createScaledBitmap(bmp, newWidth, tNH, true);
	// return scaled;
	// }

	// private Runnable showImage(final LinearLayout container,
	// final ImageView image) {
	// Runnable runnable = new Runnable() {
	// @Override
	// public void run() {
	// CommonGlobals.hideProgess();
	// container.setVisibility(View.VISIBLE);
	// image.setVisibility(View.VISIBLE);
	// // wait.setVisibility(View.GONE);
	//
	// }
	// };
	// return runnable;
	// }

}