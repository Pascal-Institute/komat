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
    implementation ("com.github.Pascal-Institute:komat:1.9.4")
}

```

## 1. How to use?

### what is 'Element'?

'Element' is a data class for contains various data types to apply komat matrix system.

It aims that it is possible to calculate other data type into matrix system as well as double.

### Available data type in Element

- Double
- Byte
- Boolean

### komat DSL

```kotlin
//Vect : 1D
val vect = Vect(1.0, 2.0, 3.0)

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

```