# komat
[![](https://jitpack.io/v/Pascal-Institute/komat.svg)](https://jitpack.io/#Pascal-Institute/komat)
[![](https://jitpack.io/v/Pascal-Institute/komat/month.svg)](https://jitpack.io/#Pascal-Institute/komat)

## 0. How to import? (Please Check Latest Version)

### build.gradle.kts
```kotlin
repositories {
    maven{
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation ("com.github.Pascal-Institute:komat:1.8.7")
}

```

## 1. How to use?
```kotlin

//Vect : 1D
//Mat : 2D 
val mat = mat{
    v(1,2,3)
    v(4,5,6)
}

//Cube : 3D
val cube = cube{
    +mat
    +mat
    +mat
}

//Hyper : 4D
```