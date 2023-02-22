# Keep data binding classes
-keep class androidx.databinding.** { *; }

# Keep data binding classes
-keep class com.stockbit.crypto.databinding.** { *; }

# Keep the default constructor for FragmentLoginBinding
-keepclassmembers class com.stockbit.crypto.databinding.* {
    <init>(android.view.View);
}

-keep class com.stockbit.crypto.databinding.* {
    public static com.stockbit.crypto.databinding.* inflate(android.view.LayoutInflater, android.view.ViewGroup, boolean);
}