import React from 'react';
import Layout from '../components/Layout';

const Cuentas = () => {
  return (
    <Layout>
      <h1>Cuentas</h1>
      <input type="text" placeholder="Buscar" />
      <button>Nuevo</button>
      <EntityTableContainer entity="cuentas" />
    </Layout>
  );
};

export default Cuentas;