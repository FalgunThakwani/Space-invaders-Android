# CSCI 4176/5708 - Summer 2023 - Mobile Computing Group 5 Project

* *Date Created*: 26 July 2023
* *Last Modified*: 29 July 2023

## Group 5 Members
* Arpitkumar Patel – B00927071
* Erxiao Tang - B00904660
* Anirudh Hosur - B00899369
* Falgun Jairaj Thakwani - B00932083

## Group 5 GitLab URL
* https://git.cs.dal.ca/hosur/csci5708-group5-space-invaders.git
* Code has also been uploaded on Brightspace as a zip file

## Group 5 Presentation
- [Slides](https://pitch.com/public/1427ec3a-e302-4e7a-aa63-83dfe0122f47)
- [Video](https://dalu-my.sharepoint.com/:v:/g/personal/ar260217_dal_ca/EcWxEWpPBE1FmoA1BBx9S5oBELBhOq_gjiD98RDG-ircYQ?e=aEljC1)

## Getting Started

### Requirements
* Make sure you have Android Studio installed.
* Install Java SDK 18 on your system.
* Set up an Android Emulator Device, preferably using Pixel 3a with API 33.
* Ensure that your app's minimum Android SDK version is 24.
* Set the target Android SDK version to 33.

### Running the app
1) Unzip the file and extract the code.
2) Launch Android Studio and import the project.
3) Compile the app by building it.
4) Begin the Android Virtual Device or connect your phone to the computer.
5) Execute the app by running it on the virtual device or your connected phone.

## Sources Used

### 1) General help with Kotlin
- “How to Draw a Line in Android with Kotlin,” GeeksforGeeks. https://www.geeksforgeeks.org/how-to-draw-a-line-in-android-with-kotlin/.
- K. Fryer, “Using method -canvas.drawBitmap(bitmap, src, dst, paint),” Stack Overflow. https://stackoverflow.com/questions/9691985/using-method-canvas-drawbitmapbitmap-src-dst-paint
- “Android Developers,” Android Developers, 2018. https://developer.android.com/
- “YouTube,” YouTube, 2023. https://www.youtube.com/
  <br /> <br />

* “The activity lifecycle,” Android Developers. https://developer.android.com/guide/components/activities/activity-lifecycle
* Utilizing Guidelines in ConstraintLayout: Learn how to divide layouts based on guidelines by referring to the official Android documentation for ConstraintLayout. - https://developer.android.com/reference/androidx/constraintlayout/widget/Guideline
* EliteIntegrity, “GitHub - EliteIntegrity/Kotlin-Invaders: Simple Space Invaders clone using Kotlin for Android,” GitHu 2023. https://github.com/EliteIntegrity/Kotlin-Invaders.
* “Game Code School,” YouTube. 2023. [YouTube Video]. Available: https://www.youtube.com/channel/UCY6pRQAXnwviO3dpmV258Ig/video
* “Save simple data with SharedPreferences,” Android Developers. https://developer.android.com/training/data-storage/shared-preferences (accessed Jul. 29, 2023).
* Zim, “Change color on fragment canvas when clicked from Activity,” Stack Overflow. https://stackoverflow.com/questions/50211117/change-color-on-fragment-canvas-when-clicked-from-activity.


### 2) For generating some Layout XMLs:
- OpenAI, “ChatGPT,” chat.openai.com, 2023. https://chat.openai.com/

### 3) Canvas in Kotlin
* “Creating a simple Canvas object · GitBook,” Github.io. https://google-developer-training.github.io/android-developer-advanced-course-practicals/unit-5-advanced-graphics-and-views/lesson-11-canvas/11-1a-p-create-a-simple-canvas/11-1a-p-create-a-simple-canvas.html
* Mayuri Khinvasara, “Exploring Android Canvas Drawing— For Shapes, Bitmaps andCustom views.,” Medium. https://medium.com/android-news/android-canvas-for-drawing-and-custom-views-e1a3e90d468b

* Example:
```kotlin
public void drawRectangle(int x, int y) {
    drawPaint.setColor(Color.RED);
    Rect rectangle = new Rect((int) (x - ((0.8) * RADIUS)), (int) (y - ((0.6) * RADIUS)), (int) (x + ((0.8) * RADIUS)), (int) (y + ((0.6 * RADIUS))));
    canvas.drawRect(rectangle, drawPaint);
}
```

### 4) For Using Copyright Free Images (Open Source)
- Unsplash, “Beautiful Free Images & Pictures | Unsplash.” https://unsplash.com/ (accessed Jul. 29, 2023).
- “Exit Red Icons – Free Download SVG, PNG, GIF,” Icons8. https://icons8.com/icons/set/exit--static--red (accessed Jul. 29, 2023).


## Acknowledgments

* Google
* StackOverflow
* ChatGPT
* The comprehensive Kotlin Documentation
* Professor and dedicated Teaching Assistants for their valuable guidance and mentorship throughout the course.