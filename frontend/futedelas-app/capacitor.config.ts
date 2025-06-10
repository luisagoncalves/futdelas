import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'io.ionic.starter',
  appName: 'futedelas-app',
  webDir: 'dist',
  plugins: {
    Firebase: {
      apiKey: process.env.VITE_FIREBASE_API_KEY,
    }
  }
};

export default config;
