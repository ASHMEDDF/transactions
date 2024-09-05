const API_URL = 'https://api.bancopichincha.com';

export const fetchClientes = async () => {
  const response = await fetch(`${API_URL}/clientes`);
  return response.json();
};

export const createCliente = async (cliente) => {
  const response = await fetch(`${API_URL}/clientes`, {
    method: 'POST',
    body: JSON.stringify(cliente),
    headers: { 'Content-Type': 'application/json' },
  });
  return response.json();
};

export const fetchClientes = async () => {
    const response = await fetch(`${API_URL}/clientes`);
    return response.json();
};
  
export const fetchCuentas = async () => {
    const response = await fetch(`${API_URL}/cuentas`);
    return response.json();
};
  
export const fetchMovimientos = async () => {
    const response = await fetch(`${API_URL}/movimientos`);
    return response.json();
};