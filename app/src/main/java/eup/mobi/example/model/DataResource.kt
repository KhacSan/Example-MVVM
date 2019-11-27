package eup.mobi.example.model

class DataResource<T>(val status: Status, val data: T? = null, val message: String? = null) {

    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T): DataResource<T> {
            return DataResource(Status.SUCCESS, data, null)
        }

        fun <T> success(data: T, message: String): DataResource<T> {
            return DataResource(Status.SUCCESS, data, message)
        }

        fun <T> error(message: String): DataResource<T> {
            return DataResource(Status.ERROR, null, message)
        }

        fun <T> loading(message: String): DataResource<T> {
            return DataResource(Status.LOADING, null, message)
        }
    }
}
