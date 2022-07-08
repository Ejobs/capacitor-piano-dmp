import Foundation

@objc public class PianoDmp: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
