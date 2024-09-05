import React from 'react';
import Layout from '../components/Layout';

const Clientes = () => {
  return (
    <Layout>
      <h1>Clientes</h1>
      <input type="text" placeholder="Buscar" />
      <button>Nuevo</button>
      <EntityTableContainer entity="clientes" />
    </Layout>
  );
};

export default Clientes;