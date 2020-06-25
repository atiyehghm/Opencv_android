# Opencv_android
In this project we're going to work with opencv in android. we'll start by installation then some simple projects and importing some sample projects from opencv documentation.

## what is opencv? and for what kind of applications is used?
OpenCV is an open source software library for computer vision, machine learning and image proccessing.it can aprox. do all the functionaities of image processing like identifying faces, objects and handwritings in images, producing high resolution images and so on.you can read more in this [link](https://opencv.org/about/) and [this](https://en.wikipedia.org/wiki/OpenCV)

## installation
1. Go to [the opencv site](https://opencv.org). go to releases and find the latest version of opencv and click on android.(it's recommended to download the version that is the latest version according to the version of your android studio.For example my android studio version is 3.5.3 so i downloaded 3.4.10)

![alt step1](https://github.com/atiyehghm/Opencv_android/blob/master/README.md_images/1_installation.png)

2. Once the download finished unzip it in some location.(for those who are on windows, `c:\program files` is recommended. and for those on mac and linux, `Users/yourname/android/opencv-android-sdk` is recommended.

3. Start a new project in android studio. then from the menu bar select `new -> import module`.

![alt step3](https://github.com/atiyehghm/Opencv_android/blob/master/README.md_images/2_installation.png)

4. Select the java file in opencv-sdk and click next and then finish.

![alt step4](https://github.com/atiyehghm/Opencv_android/blob/master/README.md_images/3_installation.png)

5. After adding the java file you will get the following error.click on `remove minSdkVersion and sync project` and then click on `Do refactor` to fix this error. This error basically happens because the minSdkversion of the opencv librery is different from the version in your app's androidManifest.

![alt step5](https://github.com/atiyehghm/Opencv_android/blob/master/README.md_images/4_installation.png)

6. Go to the `file -> project structure -> dependencies -> All modules -> + ->module dependency -> app` and then mark `opencv3410`.

![alt step6](https://github.com/atiyehghm/Opencv_android/blob/master/README.md_images/6_installation.png)

7.From `gradle scripts`, open `build.gradle(app)`. make sure the version of `compileSdkVersion` and `targetSdkVersion` are same.(in my case they are both 29)

![alt step7](https://github.com/atiyehghm/Opencv_android/blob/master/README.md_images/7_installation.png)

8.From `gradle scripts`, open `build.gradle(opencvlibrary)`.change the version of `compileSdkVersion` and `targetSdkVersion` to the number that you checked in perivious step.(in my case, 29).then click on `sync now`.

![alt step8](https://github.com/atiyehghm/Opencv_android/blob/master/README.md_images/8_installation.png)

9.The next step is adding JNI libraries to our app.this is basically because we want to use native c++ APIs of opencv in our app.Right click on app then `new -> folder -> JNI folder`. then mark `change folder location` and write `src/main/jniLibs` for the location.

![alt step9-1](https://github.com/atiyehghm/Opencv_android/blob/master/README.md_images/9_installation.png)

![alt step9-2](https://github.com/atiyehghm/Opencv_android/blob/master/README.md_images/10_installation.png)

10.Copy the contents of `native` folder in `opencv- sdk` and paste then in `jniLibs`.

![alt step10](https://github.com/atiyehghm/Opencv_android/blob/master/README.md_images/11_installation.png)

11. Now integrating opencv library in android project is done.write this code below in `MainActivity` method to make sure you've added opencv correctly.

```
private static String TAG = "Main Activity";
static {
        if (OpenCVLoader.initDebug()){
            Log.i(TAG, "******************************Opencv is added successfullu!");
        }
        else
            Log.i(TAG, "^^^^^^^^^^^^^^^^^^^^^^^^^^^^Opencv is not working properly!!");
    }
 ```

## OpenCV packages
After creating an opencv android project, let's take a look at packages within opencvLibrary

![alt packages](https://github.com/atiyehghm/Opencv_android/blob/master/README.md_images/packages.png)

| package name  | description   |
| ------------- |:-------------:|
| Android       | The main purpose of this package is to make opencv usable on android devices.               |
| calib3d       | If you are capturing an object, this package can determine the 3D coordinates of the object.|
| core          | It provides the base functionality of opencv. For example, it does the mathematical or matrix operations or some implemented algorithms.(Note that it does not do any image processing.)|
|features2d     | It's a package for 2D image feature detectors and decriptor extrcators.                     |  
|dnn            | This is a package for deep neural network algorithms.                                       |
|imgcodecs      | This package loads images from disc or writes on disc. This is used in those applications that don't get the image from camera. It's used for reading single or multiple images of different formats.|
|imgproc        | It has all the functions for manipulating images. you can use it to apply effects, blur image, write text on image and so on.|
|ml             | This package is for machine learning(artificial intelligence). It has some training algorithms for computer vision.|
|osgi           | This package provides an easy way to load opencv's native libraries from java bundle.       |
|photo          | It's used for image enhancement. You can apply manipulations like setting the contrast, hue, saturation, etc.|
|utils          | It's used for converting image data from one mathematical format to another.                |
|video          | It does video processing like applying filters, tracking objects and more.                  |
|videoio        | It is used for reading and writing video files of any format.                               |

## using opencv camera
In order to using many applications of opencv you'll be needing opencv camera. In this section, we're going to learn how to use opencv camera in a few steps.
        1. Giving the permisions.
        2. Defining a layout for our app and a view for the camera.
        3. Checking whether or not opencv is loaded.
        4. Implementing the main methods of the activity.
        
1. Open the `AndroidManifest.xml` then give the permissions for the app to use the camera and the autofocus and front camera, too.

```
    <uses-permission android:name="android.permission.CAMERA"/>
    <uses-feature android:name="android.hardware.camera" android:required="false"/>
    <uses-feature android:name="android.hardware.camera.autofocus" android:required="false"/>
    <uses-feature android:name="android.hardware.camera.front" android:required="false"/>
    <uses-feature android:name="android.hardware.camera.front.autofocus" android:required="false"/>
```
[optional] You can allow the camera optimize the dimensions of the device in use by adding the code bellow to `AndroidManifest.xml`.
```
   <supports-screens   android:resizeable="true"
        android:smallScreens="true"
        android:normalScreens="true"
        android:largeScreens="true"
        android:anyDensity="true" />
```

2. open the `main_activity.xml` and define a `JavaCameraview`.Give it the id, height, width.
```
<org.opencv.android.JavaCameraView
        android:id="@+id/appCameraView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```
Then, you should connect JavaCameraView to its view and make it visible.For that matter you should define a JavaCamera view in `MainActivity`:
```
        JavaCameraView javaCameraView;
```
then allocate a view for it and make it visible in `onCreate` method.
```
        javaCameraView = findViewById(R.id.appCameraView);
        javaCameraView.setVisibility(SurfaceView.VISIBLE);
        javaCameraView.setCvCameraViewListener(MainActivity.this);
```
Here, we defined a `viewListener` and set it to `MainActivity` so we should make our `MainActivity` implement `CvCameraViewListener2`. This interface basically has three methods that should be implemented:

`public abstract Mat onCameraFrame (CameraBridgeViewBase.CvCameraViewFrame inputFrame)` --> This method is invoked when we need to give the camera a frame.It returns the frame that should be displayed on the screen.
`public abstract void onCameraViewStarted (int width, int height)` --> This method is for when the camera preview is started. 
After invoking this method the frames start to come to the user via the onCameraFrame() callback.
`public abstract void onCameraViewStopped ()` -->  This method is for when the camera preview is stopped and then we have no frames to be delivered.

Here are implemntions for this app:

```
   @Override
    public void onCameraViewStarted(int width, int height) {
        mRGBA = new Mat(height, width, CvType.CV_8UC4);
    }

    @Override
    public void onCameraViewStopped() {
        mRGBA.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRGBA = inputFrame.rgba();
        mRGBAT = mRGBA.t();
        Core.flip(mRGBA.t(), mRGBAT, 1);
        Imgproc.resize(mRGBAT, mRGBAT, mRGBA.size());
        return mRGBAT;
    }
```
also we should define `mRGBA` and `mRGBAT` in the `MainActivity`. These `Mat` objects are used for keeping the frames that camera delivers to the user.what we do in `onCameraFrame` is first, we get the frame and store its rgba values in an array (`mRGBA`) then we use its transpose for when user flips the phone and we store this value in `mRGBAT` and return this value.

3. Now, we want our app to communicate with android phone, This happens by implementing `BaseLoaderCallback`. if opencv was successfully loaded on our phone we get `SUCCESS` status.
```
 BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(MainActivity.this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case BaseLoaderCallback.SUCCESS: {
                    javaCameraView.enableView();
                    break;
                }
                default: {
                    super.onManagerConnected(status);
                    break;
                }
            }
        }
    };
```
4.We want our camera app to work on different stages of activity lifecycle so we define activity lifecycle methods to make our camera work properly.
```
   @Override
    protected void onDestroy() {
        super.onDestroy();
        if(javaCameraView != null){
            javaCameraView.disableView();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(javaCameraView != null){
            javaCameraView.disableView();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()){
            Log.i(TAG, "******************************Opencv is added successfullu!");
            baseLoaderCallback.onManagerConnected(BaseLoaderCallback.SUCCESS);
        }
        else {
            Log.i(TAG, "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^Opencv is not working properly!!");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, baseLoaderCallback);
        }
    }
```
Here, in `onResume` method, if opencv was loaded properly we give a `SUCCESS` status to `BaseLoaderCallback`.
____

## Face Detection App
In order to understand better, we write a face detection app using the haarcascades algorithm with this library.
Face detection using Haar feature-based cascade classifiers is a machine learning based approach where a cascade function is trained from a lot of images that they may have faces in them or not. It is then used to detect faces in other images. OpenCV provide pre-learnt cascade for Haar algorithm.
For this, you need to do all the steps above (includes integrate project with openCV library, set camera permission and add `javaCameraView` with id of `appCameraView` in `main_activity.xml` file ).

Because in this app we want to use the haarcascades algorithm for detecting faces, we need a xml file that exists in the openCV library. In the openCV library go to `sdk > etc > haarcascades` and then copy `haarcascade_frontalface_alt2.xml` file, then in `resource` project folder(`res`), create `android resource directory` with `raw` resource type and optional name (e.g: raw) and paste copied file here.

After that, in `MainActivity` and in its `onCreate` method, create an object from the `JavaCameraView` class to connect it to its view and then write code for load openCV. As mentioned above, if this library loads properly a `SUCCESS` status passes to `BaseLoaderCallback`. Here this callback is `baseCallback` which will be defined later. Next, in `MainActivity` we should implement `CameraBridgeViewBase.CvCameraViewListener2` interface and call `setCvCameraViewListener` method on `javaCameraView` object.

```
javaCameraView = (JavaCameraView) findViewById(R.id.appCameraView);

if (!OpenCVLoader.initDebug()){
    OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, baseCallback);
}
else {
    try {
        baseCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
javaCameraView.setCvCameraViewListener(this);
        
```

By implementing `CameraBridgeViewBase.CvCameraViewListener2` interface we should override `onCameraViewStarted`, `onCameraViewStopped` and `onCameraFrame` method.
As explained above, in the first two methods we create and release `Mat` objects called `mRgba` and `mGray`. `mRgba`stores the rgbscale and `mGray` stores the grayscale image of the camera output. In third method those `Mat` objects initialized with bitmap matrix of camera frame, then they must be processed and generate output. 

```
private Mat mRgba, mGray;

@Override
public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat();
        mGray = new Mat();
        
}

@Override
public void onCameraViewStopped() {
        mRgba.release();
        mGray.release();
}

@Override
public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        mGray = inputFrame.gray();

        //detect face

        return mRgba;
}
        
```
We later replace`detect face` part with process for detect faces.
Now we implement `baseCallback` which is `BaseLoaderCallback` and override `onManagerConnected` methode in it. `onManagerConnected` received `status` argument, when this status is `SUCCESS` we must load `haarcascade` from `xml` file in resources and pass it to `CascadeClassifier` class.
We load the cascade file from project resource to our application using `InputStream` and `FileOutputStream` as shown below. We create a new folder `cascade` and copy the content of cascade file to a new file in that folder. The reason why we copy and save at the same time is to bring the file from our project directory into phone's filesystem.
Next. Create a new `CascadeClassifier` object called `faceDetector` which will be used for detect faces and if it doesn't created successfully we assign null to this object.
Next step is to get our camera preview ready.

```
private BaseLoaderCallback baseCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) throws IOException {
            switch (status){
                case LoaderCallbackInterface.SUCCESS:{
                    InputStream inputStream = getResources().openRawResource(R.raw.haarcascade_frontalface_alt2);
                    File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                    casoFile = new File(cascadeDir, "haarcascade_frontalface_alt2.xml");
                    FileOutputStream fileOutputStream = new FileOutputStream(casoFile);
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1){
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }
                    inputStream.close();
                    fileOutputStream.close();

                    faceDetector = new CascadeClassifier(casoFile.getAbsolutePath());

                    if (faceDetector.empty()){
                        faceDetector = null;
                    }
                    javaCameraView.enableView();
                }
                break;
                default:
                    super.onManagerConnected(status);
            }
        }
};

```

So far we initialized openCV in our project and loaded cascade classifire in to the application. Now we should implement `detect face` part in `onCameraFrame` method.
Here we create `MatOfRect` object which stores the rectangle that that bound the faces in the frame. Then we call a function called `detectMultiScale` on our `CascadeClassifier` object to detect faces. This function takes in a grayscale image and if there is any faces in the frame, it returns rectangles that bound the faces.
After we called this function, we have an array of faces and we should iterate throught it and draw rectangle for each detected faces. For this we use `rectangle(Mat img, Point pt1, Point pt2, Scalar color)` method of `Imgproc` class. `Imgproc` is class related to image processing.
`rectangle` method takes in `Mat` object, two points for start and end and a color. Here we used it with red color (rgb = (255, 0, 0)).

```
@Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();
        mGray = inputFrame.gray();

        //detect face

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(mGray, faceDetections);
        for(Rect rect: faceDetections.toArray()){
            Imgproc.rectangle(mRgba, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(255,0,0));
        }
        return mRgba;
}
```
