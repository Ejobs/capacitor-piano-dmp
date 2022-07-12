import Foundation
import Capacitor
import CxenseSDK

@objc public class PianoDmp: NSObject {
    @objc public func sendPageView(_ call: CAPPluginCall, siteId: String) {
        do {
            let location = call.getString("location") ?? ""

            if location == "" {
                call.reject("Missing required location parameter")
                return
            }

            let builder = PageViewEventBuilder.makeBuilder(withName: "PageView", siteId: siteId)
                .setLocation(loc: location)
            for (key, paramRaw) in call.getObject("userParams", JSObject.init()) {
                if let param = paramRaw as? String {
                    _ = builder.addUserProfileParameter(forKey: key, withValue: param)
                }
            }
            for (key, paramRaw) in call.getObject("customParams", JSObject.init()) {
                if let param = paramRaw as? String {
                    _ = builder.addCustomParameter(forKey: key, withValue: param)
                }
            }

            Cxense.reportEvent(try builder.build())
            print("Logged piano event for location: \(location)")

            call.resolve()
        } catch {
            print("Error occured while transmiting piano event")
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
