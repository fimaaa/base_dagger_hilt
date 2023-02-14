//
// Created by Fiqri Malik Abdul Az on 5/28/2021.
//

#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_network_crypto_di_ExternalDataKt_getStagingBaseUrl(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("https://dummy.restapiexample.com/api/v1/");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_network_crypto_di_ExternalDataKt_getReleaseBaseUrl(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("https://dummy.restapiexample.com/api/v1/");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_network_crypto_di_ExternalDataKt_getDebugBaseUrl(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("https://dummy.restapiexample.com/api/v1/");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_network_crypto_di_ExternalDataKt_getSocketBaseUrlTopTier(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("https://min-api.cryptocompare.com/data/");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_network_crypto_di_ExternalDataKt_getSocketBasicAuth(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("93a3c9336dee6d5b6545f45e717507990e339780998e33ccaa4aa75625a6e661");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_network_crypto_di_ExternalDataKt_getSocketBaseUrlSocketTemp(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("wss://streamer.cryptocompare.com/v2?api_key="); //added basick auth in external Data
}