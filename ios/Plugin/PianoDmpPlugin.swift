import Foundation
import Capacitor
import CxenseSDK

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(PianoDmpPlugin)
public class PianoDmpPlugin: CAPPlugin {
    private let implementation = PianoDmp()

    override public func load() {
        let username = getConfigValue("username") as? String
        let apiKey = getConfigValue("apiKey") as? String
        let config = Configuration(withUserName: username ?? "", apiKey: apiKey ?? "")

        do {
            try Cxense.initialize(withConfiguration: config)
        } catch {
            NSLog("%s", "An error occurred while trying to initialize Piano")
        }
    }

    @objc func sendPageView(_ call: CAPPluginCall) {
        let siteId = getConfigValue("siteId") as? String
        if siteId == nil || siteId == "" {
            call.reject("Missing required site id parameter")
            return
        }

        implementation.sendPageView(call, siteId: siteId ?? "")
    }
}
