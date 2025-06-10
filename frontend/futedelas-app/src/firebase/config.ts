import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";

const devConfig = {
  apiKey: import.meta.env.VITE_FIREBASE_API_KEY,
  authDomain: import.meta.env.VITE_FIREBASE_AUTH_DOMAIN,
  projectId: import.meta.env.VITE_FIREBASE_PROJECT_ID,
  storageBucket: import.meta.env.VITE_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: import.meta.env.VITE_FIREBASE_MESSAGING_SENDER_ID,
  appId: import.meta.env.VITE_FIREBASE_APP_ID,
  measurementId: import.meta.env.VITE_MEASUREMENT_ID
};

export const vapidKey = import.meta.env.VITE_VAPID_KEY;


export const app = initializeApp(devConfig);
const analytics = getAnalytics(app);

export const firebaseConfig = devConfig;
