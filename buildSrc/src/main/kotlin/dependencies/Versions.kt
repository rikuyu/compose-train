object Versions {
    const val versionCode: Int = AppVersions.appVersionCode
    const val versionName: String = AppVersions.appVersionName

    const val targetSdkVersion: Int = 33
    const val compileSdkVersion: Int = targetSdkVersion
    const val minSdkVersion: Int = 26

    const val applicationId: String = "com.example.composetrainapp"
}

object AppVersions {
    private const val major: Int = 0
    private const val minor: Int = 0
    private const val build: Int = 1
    private const val minorDigits: Int = 1000
    private const val buildDigits: Int = 10000

    internal const val appVersionCode: Int = major * minorDigits * buildDigits + minor * buildDigits + build
    internal const val appVersionName: String = "$major.$minor"
}