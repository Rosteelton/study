package differentTasks.quill

import java.time.Instant

final case class SimStateView(simId: String,
                                deviceId: String,
                                userId: Option[String],
                                createdAt: Instant,
                                version: Long)