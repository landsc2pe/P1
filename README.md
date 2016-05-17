# lesson-gallery
a simple gallery

# todos

### 참고 사이트
* <https://guides.codepath.com/android>

### Activity, Fragment 제대로 구현
* naming 및 패키지 구성
* Layout 구현은 [http://android-developers.blogspot.kr/2015/05/android-design-support-library
.html](http://android-developers.blogspot.kr/2015/05/android-design-support-library.html)을 이용해서 구현하기.
* Fragment backstack 처리하여 Fragment 간 이동 자연스럽게 처리 [http://developer.android.com/training/implementing-navigation/temporal.html](http://developer.android.com/training/implementing-navigation/temporal.html)
* public static Fragment newInstance() pattern으로 프래그먼트 객체 생성.

### RecyclerView
* Early-loading 구현

### MediaStore(멀티미디어를 위한 Android 표준 ContentProvider) 활용한 Gallery 구현
* <http://www.grokkingandroid.com/android-tutorial-content-provider-basics/>
* <http://developer.android.com/guide/topics/providers/content-providers.html>
* <http://developer.android.com/reference/android/provider/package-summary.html>

### 퍼미션 추가하기
* <http://developer.android.com/guide/topics/security/permissions.html>

### 런타임 퍼미션
* <https://medium.com/marojuns-android/%EC%A2%80-%EB%8D%94-%EC%83%9D%EA%B0%81%ED%95%B4%EB%B3%B8-android-m-%EB%A6%AC%EB%B7%B0-13fbb98c9a87#.qz4z1barg>


### Directory 별 thumbnail 매핑?
Map<String, List<String>> directoryMapping; 은 아래와 같은 테이블을 만듬.
+-------------+-------------------------------------------
| Directory   | Images
+-------------+-------------------------------------------
| dcim        | photo1.png, photo2.png
| kakao       | face1.png, face2.png, ourface.png
| ...         |
+-------------+-------------------------------------------

# 읽을 책
* 객체지향 관련된 책
* 디자인 패턴