#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(Realm, "Realm",
           CAP_PLUGIN_METHOD(initialize, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(signInAnonymously, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(signInWithEmailPassword, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(signInWithApiKey, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(signInWithJWT, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(signInWithGoogle, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(signInWithFacebook, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(signInWithApple, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(logout, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(registerWithEmailPassword, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(confirmEmail, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(resetPassword, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(sendPasswordResetEmail, CAPPluginReturnPromise);
)
