package be.bf.android.recetteapp.dal.scopes

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class RoomScope(override val coroutineContext: CoroutineContext = CoroutineName("Room")) :
    CoroutineScope {
}
