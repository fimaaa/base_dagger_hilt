# Keep data binding classes
-keep class androidx.databinding.** { *; }

# Keep generated data binding classes
-keep class com.feature.baseapp.firebasetest.databinding.** { *; }

# Keep the default constructor for FragmentLoginBinding
-keepclassmembers class com.feature.baseapp.firebasetest.databinding.* {
    <init>(android.view.View);
}

-keep class com.feature.baseapp.firebasetest.databinding.* {
    public static com.feature.baseapp.firebasetest.databinding.* inflate(android.view.LayoutInflater, android.view.ViewGroup, boolean);
}