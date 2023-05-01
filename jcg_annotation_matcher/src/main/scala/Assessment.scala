/**
 *
 *
 * @author Michael Reif
 */
trait Assessment {
    def combine(other: Assessment) : Assessment
    override def toString: String
    def shortNotation : String
}

trait SoundnessAssessment extends Assessment {
    def isSound: Boolean
    def isUnsound: Boolean
}

trait PrecisionAssessment extends Assessment {
    def isPrecise: Boolean
    def isImprecise: Boolean
}

object Sound extends SoundnessAssessment {
    override def isSound: Boolean = true
    override def isUnsound: Boolean = false
    override def combine(other: Assessment): Assessment = other
    override def toString: String = "Sound"
    override def shortNotation: String = "S"
}

object Imprecise extends PrecisionAssessment {
    override def isPrecise: Boolean = false
    override def isImprecise: Boolean = true
    override def combine(other: Assessment): Assessment = Imprecise
    override def toString: String = "Imprecise"
    override def shortNotation: String = "I"
}

object Precise extends PrecisionAssessment {
    override def isPrecise: Boolean = true
    override def isImprecise: Boolean = false
    override def combine(other: Assessment): Assessment = {
        other match {
            case NoTests => Precise
            case _ => other
        }
    }
    override def toString: String = "Precise"
    override def shortNotation: String = "P"
}

object NoTests extends PrecisionAssessment {
    override def isPrecise: Boolean = false
    override def isImprecise: Boolean = false
    override def combine(other: Assessment): Assessment = other
    override def toString: String = "NoPrecisionTests"
    override def shortNotation: String = "NT"
}

object Unsound extends SoundnessAssessment {
    override def isSound: Boolean = false
    override def isUnsound: Boolean = true
    override def combine(other: Assessment): Assessment = Unsound
    override def toString: String = "Unsound"
    override def shortNotation: String = "U"
}

object Error extends Assessment {
    def isSound: Boolean = false
    def isUnsound: Boolean = false
    def isPrecise: Boolean = false
    def isImprecise: Boolean = false
    override def combine(other: Assessment): Assessment = Error
    override def toString: String = "Error"
    override def shortNotation: String = "E"
}