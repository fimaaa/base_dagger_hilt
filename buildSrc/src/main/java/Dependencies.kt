object Modules {
    const val NAVIGATION = ":navigation"
    const val NETWORK = ":network"
    const val LOCAL = ":local"
    const val REPOSITORY = ":repository"

    const val UNIT_TEST = ":library:unittest"
    object Feature {
        const val AUTH = ":feature:auth"
        const val SPLASHSCREEN = ":feature:splashscreen"

        const val MAIN = ":feature:main"

        const val ADMIN = ":feature:admin"
        const val CRYPTO = ":feature:crypto"
        const val EMPLOYEE = ":feature:employee"
        const val FIREBASE_TEST = ":feature:firebasetest"
    }
    object Model {
        const val COMMON = ":model:common"
        const val CRYPTO = ":model:crypto"
        const val EMPLOYEE = ":model:employee"
    }
    object Library {
        const val COMMON = ":library:common"
    }
}

object Release {
    private const val VERSION_MAJOR = 0
    private const val VERSION_MINOR = 0
    private const val VERSION_PATCH = 1
    const val VERSION_CODE: Int = this.VERSION_MAJOR *10000 + this.VERSION_MINOR *100 + VERSION_PATCH
    const val VERSION_APP_NAME: String = "${this.VERSION_MAJOR}.${this.VERSION_MINOR}.$VERSION_PATCH"
    const val MIN_SDK = 21
    const val MAX_SDK = 34

    const val APP_NAME = "BaseDaggerHilt"
}