export const getCurrentUserId = (): string => {
  const uid = localStorage.getItem('anonymousUserId');
  if (!uid) {
    throw new Error('Usuário não autenticado');
  }
  return uid;
};

export const isUserAuthenticated = (): boolean => {
  return !!localStorage.getItem('anonymousUserId');
};