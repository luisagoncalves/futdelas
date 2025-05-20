import { LocalNotifications } from '@capacitor/local-notifications';
import { PushNotifications, Token } from '@capacitor/push-notifications';
import { Capacitor } from '@capacitor/core';

type NotificationRequest = {
  title: string;
  body: string;
  data?: Record<string, any>;
  scheduleAt?: Date;
  notificationId?: string;
};

export const useNotificationService = () => {
  // Verifica o ambiente de execução
  const isNative = Capacitor.isNativePlatform();
  const isBrowser = typeof window !== 'undefined';
  const supportsBrowserNotifications = isBrowser && 'Notification' in window;
  
  /**
   * Solicita permissão para notificações
   */
  const requestPermission = async (): Promise<NotificationStatus> => {
    try {
      if (isNative) {
        return await handleNativePermission();
      }
      
      if (supportsBrowserNotifications) {
        return await handleBrowserPermission();
      }
      
      return {
        granted: false,
        error: new Error('Notificações não suportadas neste ambiente')
      };
    } catch (error) {
      return {
        granted: false,
        error: error as Error
      };
    }
  };

  /**
   * Agenda uma notificação
   */
  const schedule = async (request: NotificationRequest): Promise<void> => {
    if (isNative) {
      await scheduleNativeNotification(request);
    } else {
      await scheduleBrowserNotification(request);
    }
  };

  /**
   * Cancela uma notificação agendada
   */
  const cancel = async (notificationId: string): Promise<void> => {
    if (isNative) {
      await LocalNotifications.cancel({ notifications: [{ id: notificationId }] });
    }
    // No browser não há como cancelar notificações programadas
  };

  /**
   * Registra dispositivo no backend para push notifications
   */
  const registerDevice = async (data: {
    token: string;
    platform: string;
    userId: string;
  }): Promise<void> => {
    // Implemente a chamada para sua API
    return fetch('/api/notifications/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data)
    }).then(response => {
      if (!response.ok) {
        throw new Error('Falha ao registrar dispositivo');
      }
    });
  };

  // Métodos privados
  const handleNativePermission = async (): Promise<NotificationStatus> => {
    const pushStatus = await PushNotifications.requestPermissions();
    
    if (pushStatus.receive === 'granted') {
      await PushNotifications.register();
      return { granted: true, type: 'push' };
    }
    
    const localStatus = await LocalNotifications.requestPermissions();
    if (localStatus.display === 'granted') {
      return { granted: true, type: 'local' };
    }
    
    return { granted: false };
  };

  const handleBrowserPermission = async (): Promise<NotificationStatus> => {
    const permission = await Notification.requestPermission();
    return {
      granted: permission === 'granted',
      type: 'browser'
    };
  };

  const scheduleNativeNotification = async (request: NotificationRequest) => {
    if (request.scheduleAt) {
      await LocalNotifications.schedule({
        notifications: [{
          title: request.title,
          body: request.body,
          id: request.notificationId ? parseInt(request.notificationId) : new Date().getTime(),
          schedule: { at: request.scheduleAt },
          extra: request.data
        }]
      });
    } else {
      await LocalNotifications.schedule({
        notifications: [{
          title: request.title,
          body: request.body,
          id: request.notificationId ? parseInt(request.notificationId) : new Date().getTime(),
          extra: request.data
        }]
      });
    }
  };

  const scheduleBrowserNotification = async (request: NotificationRequest) => {
    if (!supportsBrowserNotifications) {
      throw new Error('Notificações não suportadas no navegador');
    }

    if (request.scheduleAt) {
      const now = new Date();
      const timeout = request.scheduleAt.getTime() - now.getTime();
      
      if (timeout > 0) {
        setTimeout(() => {
          new Notification(request.title, {
            body: request.body,
            data: request.data
          });
        }, timeout);
      }
    } else {
      new Notification(request.title, {
        body: request.body,
        data: request.data
      });
    }
  };

  // Métodos auxiliares
  const getPlatform = () => {
    return Capacitor.getPlatform();
  };

  // Configura listeners para push notifications
  if (isNative) {
    PushNotifications.addListener('registration', (token: Token) => {
      console.log('Device registered with token:', token.value);
    });

    PushNotifications.addListener('registrationError', (error: any) => {
      console.error('Registration error:', error);
    });
  }

  return {
    requestPermission,
    schedule,
    cancel,
    registerDevice,
    getPlatform,
    isNative,
    supportsBrowserNotifications
  };
};

export type NotificationService = ReturnType<typeof useNotificationService>;