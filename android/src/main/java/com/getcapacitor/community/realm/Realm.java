package com.getcapacitor.community.realm;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.facebook.soloader.SoLoader;
import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import fi.iki.elonen.NanoHTTPD;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

@NativePlugin()
public class Realm extends Plugin {

    private static final int DEFAULT_PORT = 8083;
    private static boolean sentAnalytics = false;

    private AndroidWebServer webServer;
    // used to create a native AssetManager in C++ in order to load file from APK
    // Note: We keep a VM reference to the assetManager to prevent its being
    //       garbage collected while the native object is in use.
    //http://developer.android.com/ndk/reference/group___asset.html#gadfd6537af41577735bcaee52120127f4
    @SuppressWarnings("FieldCanBeLocal")
    private AssetManager assetManager;

    private Handler worker;
    private HandlerThread workerThread;
    private App app;
    private User currentUser;

    @Override
    public void load() {
        super.load();

//        SoLoader.init(getContext(), false);
//        SoLoader.loadLibrary("realmreact");

//        assetManager = getContext().getResources().getAssets();
//
//        String fileDir;
//        try {
//            fileDir = getContext().getFilesDir().getCanonicalPath();
//        } catch (IOException e) {
//            throw new IllegalStateException(e);
//        }
//
//        setDefaultRealmFileDirectory(fileDir, assetManager);

        io.realm.Realm.init(this.getContext());
    }

    @Override
    protected void handleOnPause() {
        super.handleOnPause();

        currentUser = null;
    }

    @Override
    protected void handleOnResume() {
        super.handleOnResume();

        if (app != null) {
            currentUser = app.currentUser();
        }
    }

    @PluginMethod()
    public void getConstants(PluginCall call) {
        if (isContextInjected()) {
            // No constants are needed if *not* running in Chrome debug mode.
            call.success();
        }

        startWebServer();

        List<String> hosts;
        if (isRunningOnEmulator()) {
            hosts = Collections.singletonList("localhost");
        } else {
            hosts = getIPAddresses();
        }

        JSObject constants = new JSObject();
        constants.put("debugHosts", hosts);
        constants.put("debugPort", DEFAULT_PORT);
        call.success(constants);
    }

    @PluginMethod()
    public void initialize(PluginCall call) {
        if (!call.hasOption("appId")) {
            call.error("appId property is missing");
            return;
        }

        if (app != null) {
            call.error("realm is already initialized");
            return;
        }

        String appId = call.getString("appId", "");
        app = new App(new AppConfiguration.Builder(appId).build());
    }

    @PluginMethod()
    public void signInAnonymously(final PluginCall call) {
        if (app == null) {
            call.error("Realm app is not yet initialized. Run initialize before using this method.");
            return;
        }

        Credentials credentials = Credentials.anonymous();

        app.loginAsync(credentials, it -> {
            if (it.isSuccess()) {
                currentUser = app.currentUser();
                call.success();
            } else {
                call.error(it.getError().toString());
            }
        });
    }

    @PluginMethod()
    public void signInWithEmailPassword(final PluginCall call) {
        if (app == null) {
            call.error("Realm app is not yet initialized. Run initialize before using this method.");
            return;
        }

        if (!call.hasOption("email")) {
            call.error("email property is missing.");
            return;
        }

        if (!call.hasOption("password")) {
            call.error("password property is missing.");
            return;
        }

        String email = call.getString("email");
        String password = call.getString("password");
        Credentials credentials = Credentials.emailPassword(email, password);

        app.loginAsync(credentials, it -> {
            if (it.isSuccess()) {
                currentUser = app.currentUser();
                call.success();
            } else {
                call.error(it.getError().toString());
            }
        });
    }

    @PluginMethod()
    public void signInWithApiKey(final PluginCall call) {
        if (app == null) {
            call.error("Realm app is not yet initialized. Run initialize before using this method.");
            return;
        }

        if (!call.hasOption("apiKey")) {
            call.error("apiKey property is missing.");
            return;
        }

        String apiKey = call.getString("apiKey");
        Credentials credentials = Credentials.apiKey(apiKey);

        app.loginAsync(credentials, it -> {
            if (it.isSuccess()) {
                currentUser = app.currentUser();
                call.success();
            } else {
                call.error(it.getError().toString());
            }
        });
    }

    @PluginMethod()
    public void signInWithJWT(final PluginCall call) {
        if (app == null) {
            call.error("Realm app is not yet initialized. Run initialize before using this method.");
            return;
        }

        if (!call.hasOption("jwtToken")) {
            call.error("jwtToken property is missing.");
            return;
        }

        String jwtToken = call.getString("jwtToken");
        Credentials credentials = Credentials.apiKey(jwtToken);

        app.loginAsync(credentials, it -> {
            if (it.isSuccess()) {
                currentUser = app.currentUser();
                call.success();
            } else {
                call.error(it.getError().toString());
            }
        });
    }

    @PluginMethod()
    public void signInWithGoogle(final PluginCall call) {
        if (app == null) {
            call.error("Realm app is not yet initialized. Run initialize before using this method.");
            return;
        }

        if (!call.hasOption("googleToken")) {
            call.error("googleToken property is missing.");
            return;
        }

        String googleToken = call.getString("googleToken");
        Credentials credentials = Credentials.apiKey(googleToken);

        app.loginAsync(credentials, it -> {
            if (it.isSuccess()) {
                currentUser = app.currentUser();
                call.success();
            } else {
                call.error(it.getError().toString());
            }
        });
    }

    @PluginMethod()
    public void signInWithFacebook(final PluginCall call) {
        if (app == null) {
            call.error("Realm app is not yet initialized. Run initialize before using this method.");
            return;
        }

        if (!call.hasOption("accessToken")) {
            call.error("accessToken property is missing.");
            return;
        }

        String accessToken = call.getString("accessToken");
        Credentials credentials = Credentials.apiKey(accessToken);

        app.loginAsync(credentials, it -> {
            if (it.isSuccess()) {
                currentUser = app.currentUser();
                call.success();
            } else {
                call.error(it.getError().toString());
            }
        });
    }

    @PluginMethod()
    public void signInWithApple(final PluginCall call) {
        if (app == null) {
            call.error("Realm app is not yet initialized. Run initialize before using this method.");
            return;
        }

        if (!call.hasOption("idToken")) {
            call.error("idToken property is missing.");
            return;
        }

        String idToken = call.getString("idToken");
        Credentials credentials = Credentials.apiKey(idToken);

        app.loginAsync(credentials, it -> {
            if (it.isSuccess()) {
                currentUser = app.currentUser();
                call.success();
            } else {
                call.error(it.getError().toString());
            }
        });
    }

    @PluginMethod()
    public void logout(final PluginCall call) {
        if (app == null) {
            call.error("Realm app is not yet initialized. Run initialize before using this method.");
            return;
        }

        if (currentUser == null) {
            call.error("no authenticated user found");
            return;
        }

        currentUser.logOutAsync(it -> {
            if (it.isSuccess()) {
                call.success();
            } else {
                call.error(it.getError().toString());
            }
        });
    }

    @PluginMethod()
    public void registerWithEmailPassword(final PluginCall call) {
        if (app == null) {
            call.error("Realm app is not yet initialized. Run initialize before using this method.");
            return;
        }

        if (!call.hasOption("email")) {
            call.error("email property is missing.");
            return;
        }

        if (!call.hasOption("password")) {
            call.error("password property is missing.");
            return;
        }

        String email = call.getString("email");
        String password = call.getString("password");
        app.getEmailPasswordAuth().registerUserAsync(email, password, it -> {
            if (it.isSuccess()) {
                call.success();
            } else {
                call.error(it.getError().toString());
            }
        });
    }

    @PluginMethod()
    public void confirmEmail(final PluginCall call) {
        if (app == null) {
            call.error("Realm app is not yet initialized. Run initialize before using this method.");
            return;
        }

        if (!call.hasOption("token")) {
            call.error("token property is missing.");
            return;
        }

        if (!call.hasOption("tokenId")) {
            call.error("tokenId property is missing.");
            return;
        }

        String token = call.getString("token");
        String tokenId = call.getString("tokenId");
        app.getEmailPasswordAuth().confirmUserAsync(token, tokenId, it -> {
           if (it.isSuccess()) {
               call.success();
           } else {
               call.error(it.getError().toString());
           }
        });
    }

    @PluginMethod()
    public void resetPassword(final PluginCall call) {
        if (app == null) {
            call.error("Realm app is not yet initialized. Run initialize before using this method.");
            return;
        }

        if (!call.hasOption("newPassword")) {
            call.error("newPassword property is missing.");
            return;
        }

        if (!call.hasOption("token")) {
            call.error("token property is missing.");
            return;
        }

        if (!call.hasOption("tokenId")) {
            call.error("tokenId property is missing.");
            return;
        }

        String token = call.getString("token");
        String tokenId = call.getString("tokenId");
        String newPassword = call.getString("newPassword");
        app.getEmailPasswordAuth().resetPasswordAsync(token, tokenId, newPassword, it -> {
            if (it.isSuccess()) {
                call.success();
            } else {
                call.error(it.getError().toString());
            }
        });
    }

    @PluginMethod()
    public void sendPasswordResetEmail(final PluginCall call) {
        if (app == null) {
            call.error("Realm app is not yet initialized. Run initialize before using this method.");
            return;
        }

        if (!call.hasOption("email")) {
            call.error("email property is missing.");
            return;
        }

        String email = call.getString("email");
        app.getEmailPasswordAuth().sendResetPasswordEmailAsync(email, it -> {
            if (it.isSuccess()) {
                call.success();
            } else {
                call.error(it.getError().toString());
            }
        });
    }

    @Override
    protected void handleOnDestroy() {
        super.handleOnDestroy();

        currentUser = null;
    }

    private static boolean isRunningOnEmulator() {
        // Check if running in Genymotion or on the stock emulator.
        return Build.FINGERPRINT.contains("vbox") || Build.FINGERPRINT.contains("generic");
    }

    private List<String> getIPAddresses() {
        ArrayList<String> ipAddresses = new ArrayList<String>();

        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (address.isLoopbackAddress() || address.isLinkLocalAddress() || address.isAnyLocalAddress()) {
                        continue;
                    }

                    ipAddresses.add(address.getHostAddress());
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return ipAddresses;
    }

    private void startWebServer() {
        setupChromeDebugModeRealmJsContext();
        startWorker();

        webServer = new AndroidWebServer(DEFAULT_PORT, getContext());
        try {
            webServer.start();
            Log.i("Realm", "Starting the debugging WebServer, Host: " + webServer.getHostname() + " Port: " + webServer.getListeningPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startWorker() {
        workerThread = new HandlerThread("MyHandlerThread");
        workerThread.start();
        worker = new Handler(workerThread.getLooper());
        worker.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean stop = tryRunTask();
                if (!stop) {
                    worker.postDelayed(this, 10);
                }
            }
        }, 10);
    }

    private void stopWebServer() {
        if (webServer != null) {
            Log.i("Realm", "Stopping the webserver");
            webServer.stop();
        }

        if (workerThread != null) {
            workerThread.quit();
            workerThread = null;
        }
    }

    class AndroidWebServer extends NanoHTTPD {
        private Context context;

        public AndroidWebServer(int port, Context context) {
            super(port);
            this.context = context;
        }

        public AndroidWebServer(String hostname, int port, Context context) {
            super(hostname, port);
            this.context = context;
        }

        @Override
        public Response serve(IHTTPSession session) {
            final String cmdUri = session.getUri();
            final HashMap<String, String> map = new HashMap<String, String>();
            try {
                session.parseBody(map);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ResponseException e) {
                e.printStackTrace();
            }
            final String json = map.get("postData");
            if (json == null) {
                Response response = newFixedLengthResponse("");
                response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
                return response;
            }
            final String jsonResponse = processChromeDebugCommand(cmdUri, json);

            Response response = newFixedLengthResponse(jsonResponse);
            response.addHeader("Access-Control-Allow-Origin", "http://localhost:8081");
            return response;
        }
    }

    // return true if the Realm API was injected (return false when running in Chrome Debug)
    private native boolean isContextInjected();

    // clear the flag set when injecting Realm API
    private native void clearContextInjectedFlag();

    // fileDir: path of the internal storage of the application
    private native void setDefaultRealmFileDirectory(String fileDir, AssetManager assets);

    // responsible for creating the rpcServer that will accept the chrome Websocket command
    private native long setupChromeDebugModeRealmJsContext();

    // this receives one command from Chrome debug then return the processing we should post back
    private native String processChromeDebugCommand(String cmd, String args);

    // this receives one command from Chrome debug then return the processing we should post back
    private native boolean tryRunTask();
}
