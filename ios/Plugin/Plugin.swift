import Foundation
import Capacitor
import RealmSwift

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(Realm)
public class Realm: CAPPlugin {
    
    var app: RealmApp?
    var user: Any?
    
    public override func load() {
        
    }
    
    @objc func initialize(_ call: CAPPluginCall) {
        if !call.hasOption("appId") {
            call.error("appId property is missing")
            return
        }
        
        let appId = call.getString("appId") ?? nil
        self.app = RealmApp(id: appId!)
    }
    
    @objc func signInAnonymously(_ call: CAPPluginCall) {
        if app == nil {
            call.error("Realm app is not yet initialized. Run initialize before using this method.")
            return
        }
        
        let credentials = AppCredentials.anonymous()
        app?.login(withCredential: credentials, completion: { (user, error) in
            guard error == nil else {
                call.error(error!.localizedDescription)
                return
            }
            
            self.user = self.app?.currentUser()
            call.success()
        })
    }
    
    @objc func signInWithEmailPassword(_ call: CAPPluginCall) {
        if app == nil {
            call.error("Realm app is not yet initialized. Run initialize before using this method.")
            return
        }
        
        if !call.hasOption("email") {
            call.error("email property is missing")
            return
        }
        
        if !call.hasOption("password") {
            call.error("password property is missing")
            return
        }
        
        let email = call.getString("email") ?? nil
        let password = call.getString("password") ?? nil
        
        let credentials = AppCredentials(username: email!, password: password!)
        app?.login(withCredential: credentials, completion: { (user, error) in
            guard error == nil else {
                call.error(error!.localizedDescription)
                return
            }
            
            self.user = self.app?.currentUser()
            call.success()
        })
    }
    
    @objc func signInWithApiKey(_ call: CAPPluginCall) {
        if app == nil {
            call.error("Realm app is not yet initialized. Run initialize before using this method.")
            return
        }
        
        if !call.hasOption("apiKey") {
            call.error("email property is missing")
            return
        }
        
        let apiKey = call.getString("apiKey") ?? nil
        
        let credentials = AppCredentials(userAPIKey: apiKey!)
        app?.login(withCredential: credentials, completion: { (user, error) in
            guard error == nil else {
                call.error(error!.localizedDescription)
                return
            }
            
            self.user = self.app?.currentUser()
            call.success()
        })
    }
    
    @objc func signInWithJWT(_ call: CAPPluginCall) {
        if app == nil {
            call.error("Realm app is not yet initialized. Run initialize before using this method.")
            return
        }
        
        if !call.hasOption("jwtToken") {
            call.error("jwtToken property is missing")
            return
        }
        
        let jwtToken = call.getString("jwtToken") ?? nil
        
        let credentials = AppCredentials(jwt: jwtToken!)
        app?.login(withCredential: credentials, completion: { (user, error) in
            guard error == nil else {
                call.error(error!.localizedDescription)
                return
            }
            
            self.user = self.app?.currentUser()
            call.success()
        })
    }
    
    @objc func signInWithGoogle(_ call: CAPPluginCall) {
        if app == nil {
            call.error("Realm app is not yet initialized. Run initialize before using this method.")
            return
        }
        
        if !call.hasOption("googleToken") {
            call.error("jwtToken property is missing")
            return
        }
        
        let googleToken = call.getString("googleToken") ?? nil
        
        let credentials = AppCredentials(googleToken: googleToken!)
        app?.login(withCredential: credentials, completion: { (user, error) in
            guard error == nil else {
                call.error(error!.localizedDescription)
                return
            }
            
            self.user = self.app?.currentUser()
            call.success()
        })
    }
    
    @objc func signInWithFacebook(_ call: CAPPluginCall) {
        if app == nil {
            call.error("Realm app is not yet initialized. Run initialize before using this method.")
            return
        }
        
        if !call.hasOption("accessToken") {
            call.error("jwtToken property is missing")
            return
        }
        
        let accessToken = call.getString("accessToken") ?? nil
        
        let credentials = AppCredentials(facebookToken: accessToken!)
        app?.login(withCredential: credentials, completion: { (user, error) in
            guard error == nil else {
                call.error(error!.localizedDescription)
                return
            }
            
            self.user = self.app?.currentUser()
            call.success()
        })
    }
    
    @objc func signInWithApple(_ call: CAPPluginCall) {
        if app == nil {
            call.error("Realm app is not yet initialized. Run initialize before using this method.")
            return
        }
        
        if !call.hasOption("idToken") {
            call.error("jwtToken property is missing")
            return
        }
        
        let idToken = call.getString("idToken") ?? nil
        
        let credentials = AppCredentials(appleToken: idToken!)
        app?.login(withCredential: credentials, completion: { (user, error) in
            guard error == nil else {
                call.error(error!.localizedDescription)
                return
            }
            
            self.user = self.app?.currentUser()
            call.success()
        })
    }
    
    @objc func logout(_ call: CAPPluginCall) {
        if app == nil {
            call.error("Realm app is not yet initialized. Run initialize before using this method.")
            return
        }
        
        app?.logOut(completion: { (error) in
            guard error == nil else {
                call.error(error!.localizedDescription)
                return
            }
            
            call.success()
        })
    }
    
    @objc func registerWithEmailPassword(_ call: CAPPluginCall) {
        if app == nil {
            call.error("Realm app is not yet initialized. Run initialize before using this method.")
            return
        }
        
        if !call.hasOption("email") {
            call.error("email property is missing")
            return
        }
        
        if !call.hasOption("password") {
            call.error("password property is missing")
            return
        }
        
        let client = app!.usernamePasswordProviderClient()
        let email = call.getString("email") ?? nil
        let password = call.getString("password") ?? nil
        client.registerEmail(email!, password: password!) { (error) in
            guard error == nil else {
                call.error(error!.localizedDescription)
                return
            }
            
            call.success()
        }
    }
    
    @objc func confirmEmail(_ call: CAPPluginCall) {
        if app == nil {
            call.error("Realm app is not yet initialized. Run initialize before using this method.")
            return
        }
        
        if !call.hasOption("token") {
            call.error("token property is missing")
            return
        }
        
        if !call.hasOption("tokenId") {
            call.error("tokenId property is missing")
            return
        }
        
        let client = app!.usernamePasswordProviderClient()
        let token = call.getString("token") ?? nil
        let tokenId = call.getString("tokenId") ?? nil
        client.confirmUser(token!, tokenId: tokenId!) { (error) in
            guard error == nil else {
                call.error(error!.localizedDescription)
                return
            }
            
            call.success()
        }
    }
    
    @objc func resetPassword(_ call: CAPPluginCall) {
        if app == nil {
            call.error("Realm app is not yet initialized. Run initialize before using this method.")
            return
        }
        
        if !call.hasOption("newPassword") {
            call.error("newPassword property is missing")
            return
        }
        
        if !call.hasOption("token") {
            call.error("token property is missing")
            return
        }
        
        if !call.hasOption("tokenId") {
            call.error("tokenId property is missing")
            return
        }
        
        let client = app!.usernamePasswordProviderClient()
        let token = call.getString("token") ?? nil
        let tokenId = call.getString("tokenId") ?? nil
        let newPassword = call.getString("newPassword") ?? nil
        client.resetPassword(to: newPassword!, token: token!, tokenId: tokenId!) { (error) in
            guard error == nil else {
                call.error(error!.localizedDescription)
                return
            }
            
            call.success()
        }
    }
    
    @objc func sendResetPasswordEmail(_ call: CAPPluginCall) {
        if app == nil {
            call.error("Realm app is not yet initialized. Run initialize before using this method.")
            return
        }
        
        if !call.hasOption("email") {
            call.error("email property is missing")
            return
        }
        
        let client = app!.usernamePasswordProviderClient()
        let email = call.getString("email") ?? nil
        client.sendResetPasswordEmail(email!) { (error) in
            guard error == nil else {
                call.error(error!.localizedDescription)
                return
            }
            
            call.success()
        }
    }
}
