import Foundation
import Capacitor
import CxenseSDK

@objc public class PianoDmp: NSObject {
    @objc public func sendPageView(_ call: CAPPluginCall, siteId: String, location: String) {
        do {
            let builder = PageViewEventBuilder.makeBuilder(withName: "eJobs", siteId: siteId)
                .setLocation(loc: location)
            Cxense.reportEvent(try builder.build())

            call.resolve()
        } catch {
            let messageIntro = "Sending piano event failed."
            switch error {
            case IncorrectConfiguration.valueRequired(let cause):
                call.reject("\(messageIntro) Cause: \(cause)")
            case IncorrectConfiguration.invalidParameter(let cause, let recommendation):
                call.reject("\(messageIntro) Cause: \(cause). Recommandation: \(recommendation)")
            case IncorrectConfiguration.conflictingParameters(let cause, let recommendation):
                call.reject("\(messageIntro) Cause: \(cause). Recommandation: \(recommendation)")
            case IncorrectConfiguration.freeDiskSpace:
                call.reject("\(messageIntro) Not enough free disk space")
            default:
                call.reject("\(messageIntro)")
            }
        }

        call.resolve()
    }
}
