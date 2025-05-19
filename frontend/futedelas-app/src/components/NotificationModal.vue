<template>
    <ion-alert :is-open="isOpen" header="Deseja receber notificações sobre as próximas partidas do campeonato?"
        :buttons="alertButtons" @didDismiss="handleDismiss" />
</template>

<script setup lang="ts">
import { IonAlert } from '@ionic/vue';
import { ref, defineProps, defineEmits, onMounted } from 'vue';
import { LocalNotifications } from '@capacitor/local-notifications';
import { PushNotifications, Token, PushNotificationSchema } from '@capacitor/push-notifications';

// Tipos para as props e emisões
interface Props {
    isOpen: boolean;
}

interface Emits {
    (e: 'update:isOpen', value: boolean): void;
    (e: 'confirm'): void;
    (e: 'notification-permission', granted: boolean): void;
    (e: 'notification-error', error: Error): void;
}

const props = defineProps<Props>();
const emits = defineEmits<Emits>();

const alertButtons = [
    {
        text: 'Cancelar',
        role: 'cancel'
    },
    {
        text: 'Confirmar',
        role: 'confirm',
        handler: async () => {
            try {
                await requestNotificationPermission();
                emits('confirm');
            } catch (error) {
                emits('notification-error', error as Error);
            }
        }
    }
];

const handleDismiss = () => {
    emits('update:isOpen', false);
};

// Verifica se está no navegador
const isBrowser = typeof window !== 'undefined';

// Verifica se as APIs de notificação estão disponíveis
const areNotificationsAvailable = isBrowser && ('Notification' in window);

// Verifica se é um dispositivo móvel com Capacitor
const isCapacitor = () => {
    return isBrowser && (window as any).Capacitor !== undefined;
};

// Solicita permissão para notificações
const requestNotificationPermission = async (): Promise<boolean> => {
    if (isCapacitor()) {
        // Configura notificações push para dispositivos móveis
        try {
            // Solicita permissão
            const status = await PushNotifications.requestPermissions();

            if (status.receive === 'granted') {
                await PushNotifications.register();
                setupPushListeners();
                return true;
            }
            return false;
        } catch (error) {
            console.error('Erro ao configurar push notifications:', error);
            throw error;
        }
    } else if (areNotificationsAvailable) {
        // Para navegadores
        const permission = await Notification.requestPermission();
        const granted = permission === 'granted';
        emits('notification-permission', granted);
        return granted;
    }

    return false;
};

// Configura listeners para push notifications (apenas mobile)
const setupPushListeners = () => {
    PushNotifications.addListener('registration', (token: Token) => {
        console.log('Push registration success, token:', token.value);
        // Aqui você enviaria o token para seu servidor
    });

    PushNotifications.addListener('registrationError', (error: any) => {
        console.error('Push registration error:', error);
        emits('notification-error', new Error('Falha no registro de push notifications'));
    });

    PushNotifications.addListener('pushNotificationReceived', (notification: PushNotificationSchema) => {
        console.log('Push received:', notification);
    });

    PushNotifications.addListener('pushNotificationActionPerformed', (notification: any) => {
        console.log('Push action performed:', notification);
    });
};

// Agenda uma notificação local (usando Capacitor ou API do navegador)
const scheduleLocalNotification = async (title: string, body: string, scheduleAt: Date) => {
    if (isCapacitor()) {
        await LocalNotifications.schedule({
            notifications: [
                {
                    title: title,
                    body: body,
                    id: new Date().getTime(),
                    schedule: { at: scheduleAt },
                    sound: 'default',
                    attachments: [],
                    actionTypeId: '',
                    extra: null
                }
            ]
        });
    } else if (areNotificationsAvailable && Notification.permission === 'granted') {
        // Fallback para navegadores
        const now = new Date();
        const timeUntilNotification = scheduleAt.getTime() - now.getTime();

        if (timeUntilNotification > 0) {
            setTimeout(() => {
                new Notification(title, { body });
            }, timeUntilNotification);
        }
    }
};

// Exemplo: Agendar notificação para próxima partida
const scheduleNextMatchNotification = async (matchDetails: { time: Date; teamA: string; teamB: string }) => {
    const title = `Partida ${matchDetails.teamA} vs ${matchDetails.teamB}`;
    const body = `A partida começa em 30 minutos!`;

    // Agenda 30 minutos antes da partida
    const notificationTime = new Date(matchDetails.time.getTime() - 30 * 60000);

    try {
        await scheduleLocalNotification(title, body, notificationTime);
    } catch (error) {
        console.error('Erro ao agendar notificação:', error);
        emits('notification-error', error as Error);
    }
};

// Expõe métodos para o componente pai
defineExpose({
    requestNotificationPermission,
    scheduleLocalNotification,
    scheduleNextMatchNotification
});

// Inicialização
onMounted(() => {
    if (isCapacitor()) {
        // Configura local notifications
        LocalNotifications.requestPermissions();
    }
});
</script>