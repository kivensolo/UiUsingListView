//
// Created by kviensolo on 2019/12/16.
//

#include <jni.h>
#include <string>

extern "C" {
    //方法名格式: Java_包名_类名_Java需要调用的方法名，对于包名，包名中的 . 要改成 _ ，_ 要改成 _1
    JNIEXPORT jstring JNICALL Java_com_MainActivity_stringFromJNI(
            JNIEnv *env,
            jobject /* this */) {
        std::string hello = "Hello from C++";
        return env->NewStringUTF(hello.c_str());
    }

    JNIEXPORT jint JNICALL Java_com_MainActivity_intFromJNI(
            JNIEnv *env,
            jobject) {
        return 1 << 8;
    }
}




