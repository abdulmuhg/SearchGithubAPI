package secretymus.id.cermaticodingtest

data class User(
    var login: String,
    var id: Int,
    var node_id: String,
    var avatar_url: String,
    var gravatar_id: String,
    var url: String,
    var html_url: String,
    var type: String
)

data class QuerySearchResult(
    val total_count: Int,
    val incomplete_result: Boolean,
    val items: List<User>
)