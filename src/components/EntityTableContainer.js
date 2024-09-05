import React, { useState, useEffect } from 'react';
import Table from '../components/Table';
import { fetchClientes, fetchCuentas, fetchMovimientos } from '../services/api';

const EntityTableContainer = ({ entity }) => {
  const [data, setData] = useState([]);

  useEffect(() => {
    const loadData = async () => {
      let responseData;
      switch (entity) {
        case 'clientes':
          responseData = await fetchClientes();
          break;
        case 'cuentas':
          responseData = await fetchCuentas();
          break;
        case 'movimientos':
          responseData = await fetchMovimientos();
          break;
        default:
          responseData = [];
      }
      setData(responseData);
    };

    loadData();
  }, [entity]);

  const getColumnsForEntity = () => {
    switch (entity) {
      case 'clientes':
        return ['Nombre', 'Apellido', 'Email'];
      case 'cuentas':
        return ['Número de Cuenta', 'Tipo', 'Saldo'];
      case 'movimientos':
        return ['Fecha', 'Descripción', 'Monto'];
      default:
        return [];
    }
  };

  return <Table columns={getColumnsForEntity()} data={data} />;
};

export default EntityTableContainer;