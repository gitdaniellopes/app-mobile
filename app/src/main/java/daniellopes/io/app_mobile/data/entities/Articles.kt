package daniellopes.io.app_mobile.data.entities

import java.io.Serializable

data class Articles(
    val id: String? = "",
    val featured: Boolean?,
    val title: String?,
    val url: String?,
    val imageUrl: String?,
    val newsSite: String?,
    val summary: String?,
    val publishedAt: String?,
    val updatedAt: String?,
    val launches: List<Launches>?,
    val events: List<Events>?,
) : Serializable