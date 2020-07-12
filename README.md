<p align="center"><br><img src="https://user-images.githubusercontent.com/236501/85893648-1c92e880-b7a8-11ea-926d-95355b8175c7.png" width="128" height="128" /></p>
<h3 align="center">Realm</h3>
<p align="center"><strong><code>@capacitor-community/realm</code></strong></p>
<p align="center"><strong><code><i>(WORK IN PROGRESS)</i></code></strong></p>
<p align="center">
  Capacitor community plugin for native <a href="https://www.mongodb.com/realm">Realm</a>.
</p>

<p align="center">
  <img src="https://img.shields.io/maintenance/yes/2020?style=flat-square" />
  <a href="https://github.com/capacitor-community/realm/actions?query=workflow%3A%22Test+and+Build+Plugin%22"><img src="https://img.shields.io/github/workflow/status/capacitor-community/realm/Test%20and%20Build%20Plugin?style=flat-square" /></a>
  <a href="https://www.npmjs.com/package/@capacitor-community/realm"><img src="https://img.shields.io/npm/l/@capacitor-community/realm?style=flat-square" /></a>
<br>
  <a href="https://www.npmjs.com/package/@capacitor-community/realm"><img src="https://img.shields.io/npm/dw/@capacitor-community/realm?style=flat-square" /></a>
  <a href="https://www.npmjs.com/package/@capacitor-community/realm"><img src="https://img.shields.io/npm/v/@capacitor-community/realm?style=flat-square" /></a>
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
<a href="#contributors-"><img src="https://img.shields.io/badge/all%20contributors-3-orange?style=flat-square" /></a>
<!-- ALL-CONTRIBUTORS-BADGE:END -->
</p>

## Maintainers

| Maintainer    | GitHub                                      | Social                                           |
| ------------- | ------------------------------------------- | ------------------------------------------------ |
| Priyank Patel | [priyankpat](https://github.com/priyankpat) | [@priyankpat\_](https://twitter.com/priyankpat_) |

## Installation

Using npm:

```bash
npm install @capacitor-community/realm
```

Using yarn:

```bash
yarn add @capacitor-community/realm
```

Sync native files:

```bash
npx cap sync
```

On iOS, no further steps are needed.

On Android, make changes to the following files:

`MainActivity.java`:

```java
import com.getcapacitor.community.realm.Realm;

public class MainActivity extends BridgeActivity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Initializes the Bridge
    this.init(
        savedInstanceState,
        new ArrayList<Class<? extends Plugin>>() {

          {
            // Additional plugins you've installed go here
            // Ex: add(TotallyAwesomePlugin.class);
            add(Realm.class);
          }
        }
      );
  }
}
```

## Supported methods

| Name                      | Android | iOS | Web |
| :------------------------ | :------ | :-- | :-- |
| initialize                | ✅      | ✅  | ✅  |
| signInAnonymously         | ✅      | ✅  | ✅  |
| logout                    | ✅      | ✅  | ✅  |
| signInWithEmailPassword   | ✅      | ✅  | ✅  |
| signInWithApiKey          | ✅      | ✅  | ✅  |
| signInWithJWT             | ✅      | ✅  | ✅  |
| signInWithGoogle          | ✅      | ✅  | ✅  |
| signInWithFacebook        | ✅      | ✅  | ✅  |
| signInWithApple           | ✅      | ✅  | ✅  |
| registerWithEmailPassword | ✅      | ✅  | ✅  |
| confirmEmail              | ✅      | ✅  | ✅  |
| resetPassword             | ✅      | ✅  | ✅  |
| sendPasswordResetEmail    | ✅      | ✅  | ✅  |

## Usage

```typescript
import { Plugins } from "@capacitor/core";

const { Realm } = Plugins;

/**
 * Platform: Android/iOS/Web
 * Initialize Realm with the correct Application Id
 * @params appId - This ID is unique to this application. It is necessary for connecting any client to Realm.
 * @returns void
 */
Realm.initialize({
  appId: '{{APP_ID}}',
});

/**
 * Platform: Android/iOS/Web
 * https://docs.mongodb.com/realm/android/authenticate/#anonymous
 * The anonymous authentication provider enables users to log in to your application with short-term accounts that store no persistent personal information.
 * @params none
 * @returns void
 */
Realm.signInAnonymously();

/**
 * Platform: Android/iOS/Web
 * https://docs.mongodb.com/realm/android/authenticate/#email-password
 * The Email/Password authentication provider enables users to log in to your application with an email username and a password.
 * @params none
 * @returns void
 */
Realm.signInWithEmailPassword({
    email: '',
    password: '',
});

/**
 * Platform: Android/iOS/Web
 * The API authentication provider enables users to log in to your application with an API key.
 * @params none
 * @returns void
 */
Realm.signInWithApiKey({
    apiKey: '',
});

/**
 * Platform: Android/iOS/Web
 * https://docs.mongodb.com/realm/android/authenticate/#android-login-custom-jwt
 * The Custom JWT authentication provider enables users to log in to your application with a custom JSON Web Token.
 * @params jwtToken - custom jwt token
 * @returns void
 */
Realm.signInWithJWT({
    jwtToken: '',
});

/**
 * Platform: Android/iOS/Web
 * https://docs.mongodb.com/realm/android/authenticate/#google-oauth
 * The Google OAuth authentication provider enables users to log in to your application with a custom token provided by Google.
 * @params googleToken - token obtained from google
 * @returns void
 */
Realm.signInWithGoogle({
    googleToken: '',
});

/**
 * Platform: Android/iOS/Web
 * https://docs.mongodb.com/realm/android/authenticate/#facebook-oauth
 * The Facebook OAuth authentication provider enables users to log in to your application with a custom token provided by Facebook.
 * @params accessToken - token obtained from facebook
 * @returns void
 */
Realm.signInWithFacebook({
    accessToken: '',
});

/**
 * Platform: Android/iOS/Web
 * https://docs.mongodb.com/realm/android/authenticate/#sign-in-with-apple
 * The Sign-in with Apple authentication provider enables users to log in to your application with a custom token provided by Apple.
 * @params idToken - token obtained from apple
 * @returns void
 */
Realm.signInWithApple({
    idToken: '',
});

/**
 * Platform: Android/iOS/Web
 * https://docs.mongodb.com/realm/android/authenticate/#log-out
 * The method will log out any user, regardless of the authentication provider.
 * @params none
 * @returns void
 */
Realm.logout();

/**
 * Platform: Android/iOS/Web
 * https://docs.mongodb.com/realm/android/manage-email-password-users/#register-a-new-user-account
 * The method will register a user with email/password authentication provider.
 * @params email - unique email address
 *         password - secure password
 * @returns void
 */
Realm.registerWithEmailPassword({
    email: '',
    password: '',
});

/**
 * Platform: Android/iOS/Web
 * https://docs.mongodb.com/realm/android/manage-email-password-users/#confirm-a-new-user-s-email-address
 * The method will confirm a user with email/password authentication provider.
 * @params token - parameters in the confirmation link sent in the confirmation email
 *         tokenId - parameters in the confirmation link sent in the confirmation email
 * @returns void
 */
Realm.confirmEmail({
    token: '',
    tokenId: '',
});

/**
 * Platform: Android/iOS/Web
 * https://docs.mongodb.com/realm/android/manage-email-password-users/#reset-a-user-s-password
 * The method will confirm a user with email/password authentication provider.
 * @params token - parameters in the confirmation link sent in the confirmation email
 *         tokenId - parameters in the confirmation link sent in the confirmation email
 *         newPassword - new secure password
 * @returns void
 */
Realm.resetPassword({
    token: '',
    tokenId: '',
    newPassword: '',
});

/**
 * Platform: Android/iOS/Web
 * The method will resend password reset email.
 * @params email - email address of account that requires password reset
 * @returns void
 */
Realm.sendPasswordResetEmail({
    email: '',
});

```
