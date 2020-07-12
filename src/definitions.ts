declare module "@capacitor/core" {
  interface PluginRegistry {
    Realm: RealmPlugin;
  }
}

export interface RealmPlugin {
  getConstants(): Promise<any>;
  initialize(options: { appId: string; }): Promise<any>;
  signInAnonymously(): Promise<any>;
  logout(): Promise<any>;
  signInWithEmailPassword(options: { email: string; password: string; }): Promise<any>;
  signInWithApiKey(options: { apiKey: string; }): Promise<any>;
  signInWithJWT(options: { jwtToken: string; }): Promise<any>;
  signInWithGoogle(options: { googleToken: string; }): Promise<any>;
  signInWithFacebook(options: { accessToken: string; }): Promise<any>;
  signInWithApple(options: { tokenId: string; }): Promise<void>;
  registerWithEmailPassword(options: { email: string; password: string; }): Promise<void>;
  confirmEmail(options: { tokenId: string; token: string; }): Promise<void>;
  resetPassword(options: { tokenId: string; token: string; newPassword: string; }): Promise<void>;
  sendPasswordResetEmail(options: { email: string; }): Promise<void>;
}
