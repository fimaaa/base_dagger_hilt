# Keep data binding classes
-keep class androidx.databinding.** { *; }

# Keep generated data binding classes
-keep class com.feature.employee.databinding.** { *; }

# Keep the default constructor for FragmentLoginBinding
-keepclassmembers class com.feature.employee.databinding.* {
    <init>(android.view.View);
}

-keep class com.feature.employee.databinding.* {
    public static com.feature.employee.databinding.* inflate(android.view.LayoutInflater, android.view.ViewGroup, boolean);
}