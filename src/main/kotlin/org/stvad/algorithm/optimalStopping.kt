package org.stvad.algorithm

import com.github.debop.kodatimes.secondDuration
import com.github.debop.kodatimes.seconds
import org.joda.time.Duration

fun optimalThreshold(totalTime: Duration) =
        (totalTime.seconds() * 0.37).toLong().secondDuration()