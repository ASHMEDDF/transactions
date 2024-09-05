import React from 'react';
import Layout from '../components/Layout';

const Movimientos = () => {
  return (
    <Layout>
      <h1>Movimientos</h1>
      <input type="text" placeholder="Buscar" />
      <button>Nuevo</button>
      <EntityTableContainer entity="movimientos" />
    </Layout>
  );
};

export default Movimientos;