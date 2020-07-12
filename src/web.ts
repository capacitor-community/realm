import { WebPlugin } from "@capacitor/core";
import * as RW from "realm-web";

import { RealmPlugin } from "./definitions";

export class RealmWeb extends WebPlugin implements RealmPlugin {
  private app: RW.App;
  private user: RW.User;

  constructor() {
    super({
      name: "Realm",
      platforms: ["web"],
    });
  }
  // TODO: Implement
  /*
  return new Promise((resolve, reject) => {
      
  });
  */
  initialize(options: { appId: string }): Promise<any> {
    return new Promise((resolve, reject) => {
      const { appId } = options || { appId: undefined };
      if (appId === null || appId === undefined) {
        reject("appId property is missing");
        return;
      }

      this.app = new RW.App({ id: appId });
      resolve();
    });
  }

  signInWithEmailPassword(options: {
    email: string;
    password: string;
  }): Promise<any> {
    return new Promise(async (resolve, reject) => {
      if (!this.app) {
        reject(
          "Realm app is not yet initialized. Run initialize before using this method."
        );
        return;
      }

      const { email, password } = options || {
        email: undefined,
        password: undefined,
      };

      if (email === null || email === undefined) {
        reject("email property is missing");
        return;
      }

      if (password === null || password === undefined) {
        reject("email property is missing");
        return;
      }

      const credentials = RW.Credentials.emailPassword(email, password);
      try {
        this.user = await this.app.logIn(credentials);
        resolve();
      } catch (err) {
        reject(err);
      }
    });
  }

  signInWithApiKey(options: { apiKey: string }): Promise<any> {
    return new Promise(async (resolve, reject) => {
      if (!this.app) {
        reject(
          "Realm app is not yet initialized. Run initialize before using this method."
        );
        return;
      }

      const { apiKey } = options || { apiKey: undefined };

      if (apiKey === null || apiKey === undefined) {
        reject("apiKey property is missing");
        return;
      }

      const credentials = RW.Credentials.apiKey(apiKey);
      try {
        this.user = await this.app.logIn(credentials);
        resolve();
      } catch (err) {
        reject(err);
      }
    });
  }

  signInWithJWT(options: { jwtToken: string }): Promise<any> {
    return new Promise(async (resolve, reject) => {
      if (!this.app) {
        reject(
          "Realm app is not yet initialized. Run initialize before using this method."
        );
        return;
      }

      const { jwtToken } = options || { jwtToken: undefined };

      if (jwtToken === null || jwtToken === undefined) {
        reject("jwtToken property is missing");
        return;
      }

      const credentials = RW.Credentials.jwt(jwtToken);
      try {
        this.user = await this.app.logIn(credentials);
        resolve();
      } catch (err) {
        reject(err);
      }
    });
  }

  signInWithGoogle(options: { googleToken: string }): Promise<any> {
    return new Promise(async (resolve, reject) => {
      if (!this.app) {
        reject(
          "Realm app is not yet initialized. Run initialize before using this method."
        );
        return;
      }

      const { googleToken } = options || { jwtToken: undefined };

      if (googleToken === null || googleToken === undefined) {
        reject("googleToken property is missing");
        return;
      }

      const credentials = RW.Credentials.jwt(googleToken);
      try {
        this.user = await this.app.logIn(credentials);
        resolve();
      } catch (err) {
        reject(err);
      }
    });
  }

  signInWithFacebook(options: { accessToken: string }): Promise<any> {
    return new Promise(async (resolve, reject) => {
      if (!this.app) {
        reject(
          "Realm app is not yet initialized. Run initialize before using this method."
        );
        return;
      }

      const { accessToken } = options || { accessToken: undefined };

      if (accessToken === null || accessToken === undefined) {
        reject("accessToken property is missing");
        return;
      }

      const credentials = RW.Credentials.jwt(accessToken);
      try {
        this.user = await this.app.logIn(credentials);
        resolve();
      } catch (err) {
        reject(err);
      }
    });
  }

  signInWithApple(options: { tokenId: string }): Promise<void> {
    return new Promise(async (resolve, reject) => {
      if (!this.app) {
        reject(
          "Realm app is not yet initialized. Run initialize before using this method."
        );
        return;
      }

      const { tokenId } = options || { tokenId: undefined };

      if (tokenId === null || tokenId === undefined) {
        reject("tokenId property is missing");
        return;
      }

      const credentials = RW.Credentials.jwt(tokenId);
      try {
        this.user = await this.app.logIn(credentials);
        resolve();
      } catch (err) {
        reject(err);
      }
    });
  }

  getConstants(): Promise<any> {
    return new Promise(async (resolve, _reject) => {
      resolve();
    });
  }

  signInAnonymously(): Promise<any> {
    return new Promise(async (resolve, reject) => {
      if (!this.app) {
        reject(
          "Realm app is not yet initialized. Run initialize before using this method."
        );
        return;
      }

      const credentials = RW.Credentials.anonymous();
      try {
        this.user = await this.app.logIn(credentials);
        resolve();
      } catch (err) {
        reject(err);
      }
    });
  }

  logout(): Promise<any> {
    return new Promise(async (resolve, reject) => {
      if (!this.app) {
        reject(
          "Realm app is not yet initialized. Run initialize before using this method."
        );
        return;
      }

      try {
        await this.app.currentUser.logOut();
        this.user = null;
        resolve();
      } catch (err) {
        reject(err);
      }
    });
  }

  registerWithEmailPassword(options: {
    email: string;
    password: string;
  }): Promise<void> {
    return new Promise(async (resolve, reject) => {
      if (!this.app) {
        reject(
          "Realm app is not yet initialized. Run initialize before using this method."
        );
        return;
      }

      const { email, password } = options || {
        email: undefined,
        password: undefined,
      };

      if (email === null || email === undefined) {
        reject("email property is missing");
        return;
      }

      if (password === null || password === undefined) {
        reject("password property is missing");
        return;
      }

      try {
        await this.app.emailPasswordAuth.registerUser(email, password);
        resolve();
      } catch (err) {
        reject(err);
      }
    });
  }

  confirmEmail(options: { tokenId: string; token: string }): Promise<void> {
    return new Promise(async (resolve, reject) => {
      if (!this.app) {
        reject(
          "Realm app is not yet initialized. Run initialize before using this method."
        );
        return;
      }

      const { token, tokenId } = options || {
        token: undefined,
        tokenId: undefined,
      };

      if (token === null || token === undefined) {
        reject("token property is missing");
        return;
      }

      if (tokenId === null || tokenId === undefined) {
        reject("tokenId property is missing");
        return;
      }

      try {
        await this.app.emailPasswordAuth.confirmUser(token, tokenId);
        resolve();
      } catch (err) {
        reject(err);
      }
    });
  }

  resetPassword(options: {
    tokenId: string;
    token: string;
    newPassword: string;
  }): Promise<void> {
    return new Promise(async (resolve, reject) => {
      if (!this.app) {
        reject(
          "Realm app is not yet initialized. Run initialize before using this method."
        );
        return;
      }

      const { token, tokenId, newPassword } = options || {
        token: undefined,
        tokenId: undefined,
        newPassword: undefined,
      };

      if (token === null || token === undefined) {
        reject("token property is missing");
        return;
      }

      if (tokenId === null || tokenId === undefined) {
        reject("tokenId property is missing");
        return;
      }

      if (newPassword === null || newPassword === undefined) {
        reject("newPassword property is missing");
        return;
      }

      try {
        await this.app.emailPasswordAuth.resetPassword(
          token,
          tokenId,
          newPassword
        );
        resolve();
      } catch (err) {
        reject(err);
      }
    });
  }

  sendPasswordResetEmail(options: { email: string }): Promise<void> {
    return new Promise(async (resolve, reject) => {
      if (!this.app) {
        reject(
          "Realm app is not yet initialized. Run initialize before using this method."
        );
        return;
      }

      const { email } = options || { email: undefined };

      if (email === null || email === undefined) {
        reject("email property is missing");
        return;
      }

      try {
        await this.app.emailPasswordAuth.sendResetPasswordEmail(email);
        resolve();
      } catch (err) {
        reject(err);
      }
    });
  }

  get userObj(): RW.User {
    return this.user;
  }
}

const Realm = new RealmWeb();

export { Realm };

import { registerWebPlugin } from "@capacitor/core";
registerWebPlugin(Realm);
